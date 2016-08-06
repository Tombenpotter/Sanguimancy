package tombenpotter.sanguimancy.util;

import WayofTime.bloodmagic.api.event.ItemBindEvent;
import WayofTime.bloodmagic.api.event.RitualEvent;
import WayofTime.bloodmagic.api.event.SoulNetworkEvent;
import WayofTime.bloodmagic.api.saving.SoulNetwork;
import amerifrance.guideapi.api.GuideRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.lwjgl.opengl.GL11;
import tombenpotter.oldsanguimancy.Sanguimancy;
import tombenpotter.oldsanguimancy.api.soulCorruption.SoulCorruption;
import tombenpotter.oldsanguimancy.api.soulCorruption.SoulCorruptionHelper;
import tombenpotter.oldsanguimancy.registry.BlocksRegistry;
import tombenpotter.oldsanguimancy.registry.ItemsRegistry;
import tombenpotter.oldsanguimancy.registry.SanguimancyGuide;
import tombenpotter.oldsanguimancy.tile.TileCamouflageBound;
import tombenpotter.oldsanguimancy.tile.TileItemSNPart;
import tombenpotter.oldsanguimancy.tile.TileRitualSNPart;
import tombenpotter.oldsanguimancy.util.BoundItemState;
import tombenpotter.oldsanguimancy.util.ChunkIntPairSerializable;
import tombenpotter.oldsanguimancy.util.singletons.BoundItems;
import tombenpotter.oldsanguimancy.util.singletons.ClaimedChunks;
import tombenpotter.sanguimancy.network.PacketHandler;
import tombenpotter.sanguimancy.network.events.EventCorruptedInfusion;
import tombenpotter.sanguimancy.network.packets.PacketSyncCorruption;

import java.util.ArrayList;

public class EventHandler {

    public EventHandler() {
    }

    public static void syncCorruption(EntityPlayer player) {
        SoulCorruption data = SoulCorruption.get(player);
        if (data != null) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            data.saveNBTData(tagCompound);
            PacketHandler.INSTANCE.sendTo(new PacketSyncCorruption(player, tagCompound), (EntityPlayerMP) player);
        }
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Sanguimancy.modid)) {
            ConfigHandler.syncConfig();
            Sanguimancy.logger.info(I18n.format("info." + Sanguimancy.modid + ".console.config.refresh"));
        }
    }

    @SubscribeEvent
    public void onPlayerSacrificed(LivingDeathEvent event) {
        if (event.getEntity() != null && !event.getEntity().worldObj.isRemote) {
            if (event.getEntity() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) event.getEntity();
                String owner = player.getName();
                int currentEssence = SoulNetwork.getCurrentEssence(owner);
                if (event.getSource().getEntity() != null && event.getSource().getEntity() instanceof EntityPlayer) {
                    EntityPlayer perpetrator = (EntityPlayer) event.getSource().getEntity();
                    ItemStack attunedStack = new ItemStack(ItemsRegistry.playerSacrificer, 1, 1);
                    if (perpetrator.inventory.hasItemStack(attunedStack)) {
                        perpetrator.inventory.deleteStack(attunedStack);
                        ;
                        ItemStack focusedStack = new ItemStack(ItemsRegistry.playerSacrificer, 1, 2);
                        EnergyItems.checkAndSetItemOwner(focusedStack, owner);
                        focusedStack.getTagCompound().setInteger("bloodStolen", currentEssence);
                        focusedStack.getTagCompound().setString("thiefName", perpetrator.getName());
                        perpetrator.inventory.addItemStackToInventory(focusedStack);
                        SoulNetworkHandler.setCurrentEssence(owner, 0);
                        SoulCorruptionHelper.incrementCorruption(player);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerJoinWorld(EntityJoinWorldEvent event) {
        if (event.getEntity() != null && !event.getEntity().worldObj.isRemote && event.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            NBTTagCompound tag = RandomUtils.getModTag(player, Sanguimancy.modid);
            if (!tag.getBoolean("hasInitialChunkClaimer") && ConfigHandler.addItemsOnFirstLogin) {
                player.addChatComponentMessage(new TextComponentString(I18n.format("chat.Sanguimancy.intial.claimer")));
                if (!player.inventory.addItemStackToInventory(SanguimancyItemStacks.chunkClaimer.copy())) {
                    RandomUtils.dropItemStackInWorld(player.worldObj, player.posX, player.posY, player.posZ, SanguimancyItemStacks.chunkClaimer.copy());
                }
                tag.setBoolean("hasInitialChunkClaimer", true);
            }

            if (!tag.getBoolean("hasInitialGuide") && ConfigHandler.addItemsOnFirstLogin) {
                if (!player.inventory.addItemStackToInventory(GuideRegistry.getItemStackForBook(SanguimancyGuide.sanguimancyGuide).copy())) {
                    RandomUtils.dropItemStackInWorld(player.worldObj, player.posX, player.posY, player.posZ, (GuideRegistry.getItemStackForBook(SanguimancyGuide.sanguimancyGuide).copy()));
                }
                tag.setBoolean("hasInitialGuide", true);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (SoulCorruptionHelper.isCorruptionLower(event.player, 2000)) {
            if (SoulCorruptionHelper.isCorruptionOver(event.player, 10)) {
                SoulCorruptionHelper.spawnChickenFollower(event.player);
            }
            if (SoulCorruptionHelper.isCorruptionOver(event.player, 40)) {
                SoulCorruptionHelper.killGrass(event.player);
            }
            if (SoulCorruptionHelper.isCorruptionOver(event.player, 60)) {
                SoulCorruptionHelper.hurtAndHealAnimals(event.player);
            }
            if (SoulCorruptionHelper.isCorruptionOver(event.player, 100)) {
                SoulCorruptionHelper.spawnIllusion(event.player);
            }
            if (SoulCorruptionHelper.isCorruptionOver(event.player, 150)) {
                SoulCorruptionHelper.randomTeleport(event.player);
            }
            if (SoulCorruptionHelper.isCorruptionOver(event.player, 200)) {
                SoulCorruptionHelper.loseHeart(event.player);
            }
        }
        if (SoulCorruptionHelper.isCorruptionOver(event.player, 1300)) {
            event.player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 1, 1));
        }
        if (SoulCorruptionHelper.isCorruptionOver(event.player, 1600)) {
            event.player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 1, 1));
        }
        if (SoulCorruptionHelper.isCorruptionOver(event.player, 1900)) {
            event.player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 1, 1));
        }
        if (SoulCorruptionHelper.isCorruptionOver(event.player, 2100)) {
            event.player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 1, 1));
        }
        if (SoulCorruptionHelper.isCorruptionOver(event.player, 2400)) {
            event.player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 1, 1));
        }

        if (!event.player.worldObj.isRemote && event.player.worldObj.getTotalWorldTime() % 200 == 0) {
            syncCorruption(event.player);
        }
    }

    @SubscribeEvent
    public void onPlayerAttack(AttackEntityEvent event) {
        if (event.getEntityPlayer() != null && event.getTarget() != null && event.getTarget() instanceof EntityLivingBase) {
            EntityLivingBase target = (EntityLivingBase) event.getTarget();
            if (SoulCorruptionHelper.isCorruptionOver(event.getEntityPlayer(), 30))
                SoulCorruptionHelper.addWither(target);
        }
    }

    @SubscribeEvent
    public void onRitualActivation(RitualEvent.RitualActivatedEvent event) {
        if (event.player != null) {
            if (SoulCorruptionHelper.isCorruptionOver(event.player, 50) && event.player.worldObj.rand.nextInt(10) == 0) {
                event.setResult(Event.Result.DENY);
            }
        }
    }

    @SubscribeEvent
    public void onChunkForce(ForgeChunkManager.ForceChunkEvent event) {
        if (!Loader.isModLoaded("loaderlist")) {
            RandomUtils.writeLog(event.getTicket().getModId() + " forcing the loading of the chunk at x= " + String.valueOf(event.getLocation().getCenterXPos()) + " and z=" + String.valueOf(event.getLocation().getCenterZPosition()) + " in dimension " + String.valueOf(event.getTicket().world.provider.getDimension()), "ChunkloadingLog.txt");
        }
    }

    @SubscribeEvent
    public void onChunkUnforce(ForgeChunkManager.UnforceChunkEvent event) {
        if (!Loader.isModLoaded("loaderlist")) {
            RandomUtils.writeLog(event.getTicket().getModId() + " unforcing the loading of the chunk at x= " + String.valueOf(event.getLocation().getCenterXPos()) + " and z=" + String.valueOf(event.getLocation().getCenterZPosition()) + " in dimension " + String.valueOf(event.getTicket().world.provider.getDimension()), "ChunkloadingLog.txt");
        }
    }

    @SubscribeEvent
    public void onItemAddedToSN(ItemBindEvent event) {
        if (!event.player.worldObj.isRemote) {
            if (event.player.inventory.hasItemStack(SanguimancyItemStacks.etherealManifestation)) {
                int dimID = ConfigHandler.snDimID;
                WorldServer dimWorld = MinecraftServer.getServer.worldServerForDimension(dimID);
                if (ClaimedChunks.getClaimedChunks().getLinkedChunks(event.player.getName()) != null) {
                    for (ChunkIntPairSerializable chunkInt : ClaimedChunks.getClaimedChunks().getLinkedChunks(event.player.getName())) {
                        BlockPos basePos = dimWorld.getTopSolidOrLiquidBlock(new BlockPos((chunkInt.chunkXPos << 4) + (dimWorld.rand.nextInt(16)), 0, (chunkInt.chunkZPos << 4) + (dimWorld.rand.nextInt(16)))).up(2);
                        if (basePos.getY() >= 128) {
                            continue;
                        }
                        BoundItemState boundItemState = new BoundItemState(basePos, dimID, true);
                        String name = String.valueOf(dimID) + basePos.toString() + event.itemStack.getUnlocalizedName() + event.itemStack.getDisplayName() + event.itemStack.getItemDamage() + event.player.getName();
                        if (dimWorld.isAirBlock(basePos)) {
                            RandomUtils.checkAndSetCompound(event.itemStack);
                            if (BoundItems.getBoundItems().addItem(name, boundItemState)) {
                                dimWorld.setBlockState(basePos, BlocksRegistry.boundItem.getDefaultState());
                                event.itemStack.getTagCompound().setString("SavedItemName", name);
                                if (dimWorld.getTileEntity(basePos) != null && dimWorld.getTileEntity(basePos) instanceof TileItemSNPart) {
                                    TileItemSNPart tile = (TileItemSNPart) dimWorld.getTileEntity(basePos);
                                    tile.setInventorySlotContents(0, event.itemStack.copy());
                                    tile.getCustomNBTTag().setString("SavedItemName", name);
                                    dimWorld.notifyBlockUpdate(basePos, dimWorld.getBlockState(basePos), dimWorld.getBlockState(basePos), 3);
                                }
                            }
                        }
                        event.player.addChatComponentMessage(new TextComponentString(I18n.format("chat.Sanguimancy.added.success")));
                        dimWorld.notifyBlockUpdate(basePos, dimWorld.getBlockState(basePos), dimWorld.getBlockState(basePos), 3);
                        break;
                    }
                }

                event.player.inventory.consumeInventoryItem(SanguimancyItemStacks.etherealManifestation);
            }
        }
    }

    @SubscribeEvent
    public void onItemDrainNetwork(SoulNetworkEvent.ItemDrainNetworkEvent event) {
        if (!event.player.worldObj.isRemote && event.itemStack != null) {
            if (event.itemStack.getTagCompound().hasKey("SavedItemName")) {
                String name = event.itemStack.getTagCompound().getString("SavedItemName");
                if (!BoundItems.getBoundItems().hasKey(name)) {
                    event.player.addChatComponentMessage(new TextComponentString(I18n.format("chat.Sanguimancy.removed")));
                    RandomUtils.unbindItemStack(event.itemStack);
                    event.setResult(Event.Result.DENY);
                } else if (!BoundItems.getBoundItems().getLinkedLocation(name).activated) {
                    event.player.addChatComponentMessage(new TextComponentString(I18n.format("chat.Sanguimancy.deactivated")));
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }
    /*
    @SubscribeEvent
    public void onItemAddToNetwork(PlayerAddToNetworkEvent event) {
        if (!event.player.worldObj.isRemote && event.itemStack != null) {
            if (event.itemStack.stackTagCompound.hasKey("SavedItemName")) {
                String name = event.itemStack.stackTagCompound.getString("SavedItemName");
                if (!BoundItems.getBoundItems().hasKey(name)) {
                    event.player.addChatComponentMessage(new TextComponentString(I18n.format("chat.Sanguimancy.removed")));
                    event.setResult(Event.Result.DENY);
                } else if (!BoundItems.getBoundItems().getLinkedLocation(name).activated) {
                    event.player.addChatComponentMessage(new TextComponentString(I18n.format("chat.Sanguimancy.deactivated")));
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }
    */

    @SubscribeEvent
    public void onRitualStart(RitualEvent.RitualActivatedEvent event) {
        if (!event.player.worldObj.isRemote) {
            if (event.player.inventory.hasItemStack(SanguimancyItemStacks.etherealManifestation)) {
                int dimID = ConfigHandler.snDimID;
                WorldServer dimWorld = MinecraftServer.getServer().worldServerForDimension(dimID);
                if (ClaimedChunks.getClaimedChunks().getLinkedChunks(event.player.getName()) != null) {
                    for (ChunkIntPairSerializable chunkInt : ClaimedChunks.getClaimedChunks().getLinkedChunks(event.player.getName())) {
                        int baseX = (chunkInt.chunkXPos << 4) + (dimWorld.rand.nextInt(16));
                        int baseZ = (chunkInt.chunkZPos << 4) + (dimWorld.rand.nextInt(16));
                        int baseY = dimWorld.getTopSolidOrLiquidBlock(baseX, baseZ) + 2;
                        if (baseY >= 128) {
                            continue;
                        }
                        if (dimWorld.isAirBlock(baseX, baseY, baseZ)) {
                            dimWorld.setBlock(baseX, baseY, baseZ, BlocksRegistry.ritualRepresentation);
                            if (dimWorld.getTileEntity(baseX, baseY, baseZ) != null && dimWorld.getTileEntity(baseX, baseY, baseZ) instanceof TileRitualSNPart) {
                                TileRitualSNPart part = (TileRitualSNPart) dimWorld.getTileEntity(baseX, baseY, baseZ);
                                part.xRitual = event.mrs.getXCoord();
                                part.yRitual = event.mrs.getYCoord();
                                part.zRitual = event.mrs.getZCoord();
                                dimWorld.markBlockForUpdate(baseX, baseY, baseZ);
                            }
                        }
                        event.player.addChatComponentMessage(new TextComponentString(I18n.format("chat.Sanguimancy.added.success")));
                        break;
                    }
                }
                event.player.inventory.consumeInventoryItem(SanguimancyItemStacks.etherealManifestation.getItem());
            }
        }
    }

    @SubscribeEvent
    public void onPlayerCorruptedInfusionAesthetic(EventCorruptedInfusion.EventPlayerCorruptedInfusion event) {
        double posX = event.entityPlayer.posX;
        double posY = event.entityPlayer.posY;
        double posZ = event.entityPlayer.posZ;
        event.entityPlayer.worldObj.playSoundEffect((double) ((float) posX + 0.5F), (double) ((float) posY + 0.5F), (double) ((float) posZ + 0.5F), "random.fizz", 0.5F, 2.6F + (event.entityPlayer.worldObj.rand.nextFloat() - event.entityPlayer.worldObj.rand.nextFloat()) * 0.8F);
        SpellHelper.sendIndexedParticleToAllAround(event.entityPlayer.worldObj, posX, posY, posZ, 20, event.entityPlayer.worldObj.provider.dimensionId, 4, posX, posY, posZ);
    }

    @SubscribeEvent
    public void onBreakBoundTile(BlockEvent.BreakEvent event) {
        if (event.getWorld().getTileEntity(event.getPos()) != null && event.getWorld().getTileEntity(event.getPos()) instanceof TileCamouflageBound) {
            TileCamouflageBound tile = (TileCamouflageBound) event.getWorld().getTileEntity(event.getPos());
            if (tile.getOwnersList() == null) tile.setOwnersList(new ArrayList<String>());
            if (!tile.getOwnersList().isEmpty() && !tile.getOwnersList().contains(event.getPlayer().getName())) {
                event.getPlayer().addChatComponentMessage(new TextComponentString(I18n.format("info.Sanguimancy.tooltip.wrong.player")));
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onTranspositionSigilLightning(EntityStruckByLightningEvent event) {
        if (event.getLightning().getEntityData() != null && event.getLightning().getEntityData().getBoolean("isTranspositionSigilBolt")) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onCreateEntity(EntityEvent.EntityConstructing event) {
        if (event.getEntity() instanceof EntityPlayer && SoulCorruption.get((EntityPlayer) event.getEntity()) == null) {
            SoulCorruption.create((EntityPlayer) event.getEntity());
        }
    }

    @SubscribeEvent
    public void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        syncCorruption(event.player);
    }

    @SubscribeEvent
    public void onPlayerSpawn(PlayerEvent.PlayerRespawnEvent event) {
        syncCorruption(event.player);
    }

    @SubscribeEvent
    public void onPlayerClone(net.minecraftforge.event.entity.player.PlayerEvent.Clone event) {
        NBTTagCompound tagCompound = new NBTTagCompound();
        SoulCorruption.get(event.getOriginal()).saveNBTData(tagCompound);
        SoulCorruption.get(event.getEntityPlayer()).saveNBTData(tagCompound);
        syncCorruption(event.getEntityPlayer());
    }

    @SubscribeEvent
    public void onDigWithCorruptedTool(net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed event) {
        if (event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND) != null) {
            ItemStack stack = event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND);
            if (stack.getItem() == ItemsRegistry.corruptedAxe || stack.getItem() == ItemsRegistry.corruptedShovel || stack.getItem() == ItemsRegistry.corruptedPickaxe) {
                int corruption = SoulCorruptionHelper.getCorruptionLevel(event.getEntityPlayer());
                event.setNewSpeed(event.getOriginalSpeed() * (corruption / ConfigHandler.minimumToolCorruption));
            }
        }
    }

    public static class ClientEventHandler {
        /*
        public static KeyBinding keySearchPlayer = new KeyBinding(I18n.format("key.Sanguimancy.search"), Keyboard.KEY_F, Sanguimancy.modid);
        public ClientEventHandler() {ClientRegistry.registerKeyBinding(keySearchPlayer);}
        @SubscribeEvent
        public void onKeyInput(InputEvent.KeyInputEvent event) {if (keySearchPlayer.isPressed()) {PacketHandler.INSTANCE.sendToServer(new PacketPlayerSearch());}
        */

        private static float renderTicks;
        private static long tickTime = 0L;

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        //This code is very much inspired by the one in ProfMobius' Waila mod
        public void onSanguimancyItemTooltip(ItemTooltipEvent event) {
            ItemStack stack = event.getItemStack();

            if (stack != null) {
                GameRegistry.UniqueIdentifier id = GameRegistry.findUniqueIdentifierFor(stack.getItem());
                if (id != null && id.modId.equals(Sanguimancy.modid) && stack.getTagCompound() != null && stack.getTagCompound().hasKey("ownerName")) {
                    if (GuiScreen.isShiftKeyDown()) {
                        event.getToolTip().add(I18n.format("info.Sanguimancy.tooltip.owner") + ": " + stack.getTagCompound().getString("ownerName"));
                    }
                }
            }
        }

        @SubscribeEvent
        public void onRenderPlayerSpecialAntlers(RenderPlayerEvent.Specials.Post event) {
            String names[] = {"Tombenpotter", "TehNut", "WayofFlowingTime", "Jadedcat", "Kris1432", "Drullkus", "TheOrangeGenius", "Direwolf20", "Pahimar", "ValiarMarcus", "Alex_hawks", "StoneWaves", "DemoXin", "insaneau"};
            for (String name : names) {
                if (event.getEntityPlayer().getName().equalsIgnoreCase(name)) {
                    GL11.glPushMatrix();
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(Sanguimancy.texturePath + ":textures/items/Wand.png"));
                    GL11.glTranslatef(0.0F, -0.95F, -0.125F);
                    Tessellator tesselator = Tessellator.getInstance();

                    GL11.glPushMatrix();
                    GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(-5.0F, 0.0F, 1.0F, 0.0F);
                    tesselator.startDrawingQuads();
                    tesselator.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
                    tesselator.addVertexWithUV(0.0D, 1.0D, 0.0D, 0.0D, 1.0D);
                    tesselator.addVertexWithUV(1.0D, 1.0D, 0.0D, 1.0D, 1.0D);
                    tesselator.addVertexWithUV(1.0D, 0.0D, 0.0D, 1.0D, 0.0D);
                    tesselator.draw();
                    GL11.glPopMatrix();

                    GL11.glPushMatrix();
                    GL11.glRotatef(5.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(20.0F, 0.0F, 1.0F, 0.0F);
                    tesselator.startDrawingQuads();
                    tesselator.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
                    tesselator.addVertexWithUV(0.0D, 1.0D, 0.0D, 0.0D, 1.0D);
                    tesselator.addVertexWithUV(-1.0D, 1.0D, 0.0D, 1.0D, 1.0D);
                    tesselator.addVertexWithUV(-1.0D, 0.0D, 0.0D, 1.0D, 0.0D);
                    tesselator.draw();
                    GL11.glPopMatrix();
                    GL11.glPopMatrix();
                }
            }
        }

        @SubscribeEvent
        public void onRenderTick(TickEvent.RenderTickEvent event) {
            renderTicks = event.renderTickTime;
        }

        @SubscribeEvent
        public void onClientTick(TickEvent.ClientTickEvent event) {
            tickTime += 1L;
        }

        private float render() {
            return (float) tickTime + renderTicks;
        }

        @SubscribeEvent
        public void onRenderPlayerFish(RenderPlayerEvent.Specials.Post event) {
            if (ConfigHandler.renderSillyAprilFish && Sanguimancy.isAprilFools) {
                GL11.glPushMatrix();
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(Sanguimancy.texturePath + ":textures/items/AprilFish.png"));
                GL11.glTranslatef(0F, -0.95F, 0F);
                Tessellator tesselator = Tessellator.getInstance();

                float flap = (1.0F + (float) Math.cos(render() / 4.0F)) * 13.0F;

                GL11.glPushMatrix();
                GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(-flap, 0.0F, 1.0F, 0.0F);
                tesselator.startDrawingQuads();
                tesselator.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
                tesselator.addVertexWithUV(0.0D, 1.0D, 0.0D, 0.0D, 1.0D);
                tesselator.addVertexWithUV(1.0D, 1.0D, 0.0D, 1.0D, 1.0D);
                tesselator.addVertexWithUV(1.0D, 0.0D, 0.0D, 1.0D, 0.0D);
                tesselator.draw();
                GL11.glPopMatrix();

                GL11.glPushMatrix();
                GL11.glRotatef(flap, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(20.0F, 0.0F, 1.0F, 0.0F);
                tesselator.startDrawingQuads();
                tesselator.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
                tesselator.addVertexWithUV(0.0D, 1.0D, 0.0D, 0.0D, 1.0D);
                tesselator.addVertexWithUV(-1.0D, 1.0D, 0.0D, 1.0D, 1.0D);
                tesselator.addVertexWithUV(-1.0D, 0.0D, 0.0D, 1.0D, 0.0D);
                tesselator.draw();
                GL11.glPopMatrix();
                GL11.glPopMatrix();
            }
        }

        @SubscribeEvent
        public void prePlayerRender(RenderPlayerEvent.Pre event) {
            if (SoulCorruptionHelper.getClientPlayerCorruption() >= 20) {
                GL11.glPushMatrix();
                GL11.glDisable(2929);
                GL11.glColor3f(255, 0, 0);
                GL11.glPopMatrix();
            }
        }

        @SubscribeEvent
        public void postPlayerRender(RenderPlayerEvent.Post event) {
            if (SoulCorruptionHelper.getClientPlayerCorruption() >= 20) {
                GL11.glPushMatrix();
                GL11.glEnable(2929);
                GL11.glPopMatrix();
            }
        }
    }
}
