package tombenpotter.sanguimancy.util;

import WayofTime.alchemicalWizardry.api.event.ItemBindEvent;
import WayofTime.alchemicalWizardry.api.event.ItemDrainNetworkEvent;
import WayofTime.alchemicalWizardry.api.event.PlayerAddToNetworkEvent;
import WayofTime.alchemicalWizardry.api.event.RitualActivatedEvent;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import WayofTime.alchemicalWizardry.common.items.EnergyItems;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.network.EventCorruptedInfusion;
import tombenpotter.sanguimancy.network.PacketHandler;
import tombenpotter.sanguimancy.network.PacketPlayerSearch;
import tombenpotter.sanguimancy.registry.BlocksRegistry;
import tombenpotter.sanguimancy.registry.ItemsRegistry;
import tombenpotter.sanguimancy.tile.TileItemSNPart;
import tombenpotter.sanguimancy.util.singletons.BoundItems;
import tombenpotter.sanguimancy.util.singletons.ClaimedChunks;

public class EventHandler {

    public EventHandler() {
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equals(Sanguimancy.modid)) {
            ConfigHandler.syncConfig();
            Sanguimancy.logger.info(StatCollector.translateToLocal("info." + Sanguimancy.modid + ".console.config.refresh"));
        }
    }

    @SubscribeEvent
    public void onPlayerSacrificed(LivingDeathEvent event) {
        if (event.entity != null && !event.entity.worldObj.isRemote) {
            if (event.entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) event.entity;
                String owner = player.getCommandSenderName();
                int currentEssence = SoulNetworkHandler.getCurrentEssence(owner);
                if (event.source.getEntity() != null && event.source.getEntity() instanceof EntityPlayer) {
                    EntityPlayer perpetrator = (EntityPlayer) event.source.getEntity();
                    ItemStack attunedStack = new ItemStack(ItemsRegistry.playerSacrificer, 1, 1);
                    if (perpetrator.inventory.hasItemStack(attunedStack)) {
                        perpetrator.inventory.consumeInventoryItem(attunedStack.getItem());
                        ItemStack focusedStack = new ItemStack(ItemsRegistry.playerSacrificer, 1, 2);
                        EnergyItems.checkAndSetItemOwner(focusedStack, owner);
                        focusedStack.stackTagCompound.setInteger("bloodStolen", currentEssence);
                        focusedStack.stackTagCompound.setString("thiefName", perpetrator.getCommandSenderName());
                        perpetrator.inventory.addItemStackToInventory(focusedStack);
                        SoulNetworkHandler.setCurrentEssence(owner, 0);
                        NBTTagCompound tag = SoulCorruptionHelper.getModTag(player, Sanguimancy.modid);
                        SoulCorruptionHelper.incrementCorruption(tag);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerJoinWorld(EntityJoinWorldEvent event) {
        if (!event.entity.worldObj.isRemote && event.entity != null && event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;
            NBTTagCompound tag = SoulCorruptionHelper.getModTag(player, Sanguimancy.modid);
            if (SoulCorruptionHelper.getCorruptionLevel(tag) > 0) return;
            else SoulCorruptionHelper.negateCorruption(tag);
            if (!tag.getBoolean("hasInitialChunkClaimer")) {
                player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("chat.Sanguimancy.intial.claimer")));
                if (!player.inventory.addItemStackToInventory(RandomUtils.SanguimancyItemStacks.chunkClaimer.copy())) {
                    RandomUtils.dropItemStackInWorld(player.worldObj, player.posX, player.posY, player.posZ, RandomUtils.SanguimancyItemStacks.chunkClaimer.copy());
                }
                tag.setBoolean("hasInitialChunkClaimer", true);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        NBTTagCompound tag = SoulCorruptionHelper.getModTag(event.player, Sanguimancy.modid);
        if (SoulCorruptionHelper.isCorruptionOver(tag, 10)) SoulCorruptionHelper.spawnChickenFollower(event.player);
        if (SoulCorruptionHelper.isCorruptionOver(tag, 25)) SoulCorruptionHelper.killGrass(event.player);
        if (SoulCorruptionHelper.isCorruptionOver(tag, 40)) SoulCorruptionHelper.hurtAndHealAnimals(event.player);
        if (SoulCorruptionHelper.isCorruptionOver(tag, 50)) SoulCorruptionHelper.spawnIllusion(event.player);
        if (SoulCorruptionHelper.isCorruptionOver(tag, 70)) SoulCorruptionHelper.randomTeleport(event.player);
    }

    @SubscribeEvent
    public void onPlayerAttack(AttackEntityEvent event) {
        if (event.entityPlayer != null && event.target != null && event.target instanceof EntityLivingBase) {
            NBTTagCompound tag = SoulCorruptionHelper.getModTag(event.entityPlayer, Sanguimancy.modid);
            EntityLivingBase target = (EntityLivingBase) event.target;
            if (SoulCorruptionHelper.isCorruptionOver(tag, 30)) SoulCorruptionHelper.addWither(target);
        }
    }

    @SubscribeEvent
    public void onCorruptedInfusion(EventCorruptedInfusion.EventPlayerCorruptedInfusion event) {
    }

    @SubscribeEvent
    public void onRitualActivation(RitualActivatedEvent event) {
        if (event.player != null) {
            NBTTagCompound tag = SoulCorruptionHelper.getModTag(event.player, Sanguimancy.modid);
            if (SoulCorruptionHelper.isCorruptionOver(tag, 15) && event.player.worldObj.rand.nextInt(10) == 0) {
                event.setResult(Event.Result.DENY);
            }
        }
    }

    @SubscribeEvent
    public void onChunkForce(ForgeChunkManager.ForceChunkEvent event) {
        if (!Loader.isModLoaded("loaderlist")) {
            RandomUtils.writeLog(event.ticket.getModId() + " forcing the loading of the chunk at x= " + String.valueOf(event.location.getCenterXPos()) + " and z=" + String.valueOf(event.location.getCenterZPosition()) + " in dimension " + String.valueOf(event.ticket.world.provider.dimensionId), "ChunkloadingLog.txt");
        }
    }

    @SubscribeEvent
    public void onChunkUnforce(ForgeChunkManager.UnforceChunkEvent event) {
        if (!Loader.isModLoaded("loaderlist")) {
            RandomUtils.writeLog(event.ticket.getModId() + " unforcing the loading of the chunk at x= " + String.valueOf(event.location.getCenterXPos()) + " and z=" + String.valueOf(event.location.getCenterZPosition()) + " in dimension " + String.valueOf(event.ticket.world.provider.dimensionId), "ChunkloadingLog.txt");
        }
    }

    @SubscribeEvent
    public void onItemAddedToSN(ItemBindEvent event) {
        if (!event.player.worldObj.isRemote) {
            int dimID = ConfigHandler.snDimID;
            WorldServer dimWorld = MinecraftServer.getServer().worldServerForDimension(dimID);
            if (ClaimedChunks.getClaimedChunks().getLinkedChunks(event.player.getCommandSenderName()) != null) {
                for (ChunkIntPairSerializable chunkInt : ClaimedChunks.getClaimedChunks().getLinkedChunks(event.player.getCommandSenderName())) {
                    int baseX = (chunkInt.chunkXPos << 4) + (dimWorld.rand.nextInt(16));
                    int baseZ = (chunkInt.chunkZPos << 4) + (dimWorld.rand.nextInt(16));
                    int baseY = dimWorld.getTopSolidOrLiquidBlock(baseX, baseZ) + 2;
                    if (baseY >= 128) {
                        continue;
                    }
                    BoundItemState boundItemState = new BoundItemState(baseX, baseY, baseZ, dimID, true);
                    String name = String.valueOf(dimID) + String.valueOf(baseX) + String.valueOf(baseY) + String.valueOf(baseZ) + event.itemStack.getUnlocalizedName() + event.itemStack.getDisplayName() + event.itemStack.getItemDamage() + event.player.getCommandSenderName();
                    if (dimWorld.isAirBlock(baseX, baseY, baseZ)) {
                        RandomUtils.checkAndSetCompound(event.itemStack);
                        if (BoundItems.getBoundItems().addItem(name, boundItemState)) {
                            dimWorld.setBlock(baseX, baseY, baseZ, BlocksRegistry.boundItem);
                            event.itemStack.stackTagCompound.setString("SavedItemName", name);
                            if (dimWorld.getTileEntity(baseX, baseY, baseZ) != null && dimWorld.getTileEntity(baseX, baseY, baseZ) instanceof TileItemSNPart) {
                                TileItemSNPart tile = (TileItemSNPart) dimWorld.getTileEntity(baseX, baseY, baseZ);
                                tile.setInventorySlotContents(0, event.itemStack.copy());
                                tile.getCustomNBTTag().setString("SavedItemName", name);
                                dimWorld.markBlockForUpdate(baseX, baseY, baseZ);
                            }
                        }
                    }
                    event.player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("chat.Sanguimancy.added.success")));
                    break;
                }
            } else {
                event.player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("chat.Sanguimancy.added.fail")));
                event.setResult(Event.Result.DENY);
            }

        }
    }

    @SubscribeEvent
    public void onItemDrainNetwork(ItemDrainNetworkEvent event) {
        if (!event.player.worldObj.isRemote && event.itemStack != null) {
            if (event.itemStack.stackTagCompound.hasKey("SavedItemName")) {
                String name = event.itemStack.stackTagCompound.getString("SavedItemName");
                if (!BoundItems.getBoundItems().hasKey(name)) {
                    event.player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("chat.Sanguimancy.removed")));
                    RandomUtils.unbindItemStack(event.itemStack);
                    event.setResult(Event.Result.DENY);
                } else if (!BoundItems.getBoundItems().getLinkedLocation(name).activated) {
                    event.player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("chat.Sanguimancy.deactivated")));
                    event.setResult(Event.Result.DENY);
                }
            } else {
                event.setResult(Event.Result.DENY);
            }
        }
    }

    @SubscribeEvent
    public void onItemAddToNetwork(PlayerAddToNetworkEvent event) {
        if (!event.player.worldObj.isRemote && event.itemStack != null) {
            if (event.itemStack.stackTagCompound.hasKey("SavedItemName")) {
                String name = event.itemStack.stackTagCompound.getString("SavedItemName");
                if (!BoundItems.getBoundItems().hasKey(name)) {
                    event.player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("chat.Sanguimancy.removed")));
                    event.setResult(Event.Result.DENY);
                } else if (!BoundItems.getBoundItems().getLinkedLocation(name).activated) {
                    event.player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("chat.Sanguimancy.deactivated")));
                    event.setResult(Event.Result.DENY);
                }
            } else {
                event.setResult(Event.Result.DENY);
            }
        }
    }

    public static class ClientEventHandler {
        public static KeyBinding keySearchPlayer = new KeyBinding(StatCollector.translateToLocal("key.Sanguimancy.search"), Keyboard.KEY_F, Sanguimancy.modid);

        public ClientEventHandler() {
            ClientRegistry.registerKeyBinding(keySearchPlayer);
        }

        @SubscribeEvent
        public void onKeyInput(InputEvent.KeyInputEvent event) {
            if (keySearchPlayer.isPressed()) {
                PacketHandler.INSTANCE.sendToServer(new PacketPlayerSearch());
            }
        }

        @SubscribeEvent
        public void onRenderPlayerSpecialAntlers(RenderPlayerEvent.Specials.Post event) {
            String names[] = {"Tombenpotter", "Speedynutty68", "WayofFlowingTime", "Jadedcat", "Kris1432", "Drullkus", "TheOrangeGenius", "Direwolf20", "Pahimar", "ValiarMarcus", "Alex_hawks"};
            for (String name : names) {
                if (event.entityPlayer.getCommandSenderName().equalsIgnoreCase(name)) {
                    GL11.glPushMatrix();
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    event.renderer.modelBipedMain.bipedBody.render(0.1F);
                    Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(Sanguimancy.texturePath + ":textures/items/Wand.png"));
                    GL11.glTranslatef(0.0F, -0.95F, -0.125F);
                    Tessellator tesselator = Tessellator.instance;
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
    }
}
