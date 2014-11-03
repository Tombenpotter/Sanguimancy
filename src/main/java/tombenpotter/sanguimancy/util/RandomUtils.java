package tombenpotter.sanguimancy.util;

import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import com.google.common.collect.Lists;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S06PacketUpdateHealth;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S1FPacketSetExperience;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.registry.BlocksRegistry;
import tombenpotter.sanguimancy.registry.ItemsRegistry;
import tombenpotter.sanguimancy.tile.TileDimensionalPortal;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class RandomUtils {

    public static HashMap<String, Integer> oreDictColor = new HashMap<String, Integer>();

    public static void dropItems(World world, int x, int y, int z) {
        Random rand = new Random();
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (!(tileEntity instanceof IInventory)) return;
        IInventory inventory = (IInventory) tileEntity;
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack item = inventory.getStackInSlot(i);
            if (item != null && item.stackSize > 0) {
                float rx = rand.nextFloat() * 0.8F + 0.1F;
                float ry = rand.nextFloat() * 0.8F + 0.1F;
                float rz = rand.nextFloat() * 0.8F + 0.1F;
                EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z + rz, new ItemStack(item.getItem(), item.stackSize, item.getItemDamage()));
                if (item.hasTagCompound()) {
                    entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
                }
                float factor = 0.05F;
                entityItem.motionX = rand.nextGaussian() * factor;
                entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                entityItem.motionZ = rand.nextGaussian() * factor;
                world.spawnEntityInWorld(entityItem);
                item.stackSize = 0;
            }
        }
    }

    public static EntityItem dropItemStackInWorld(World world, double x, double y, double z, ItemStack stack) {
        float f = 0.7F;
        float d0 = world.rand.nextFloat() * f + (1.0F - f) * 0.5F;
        float d1 = world.rand.nextFloat() * f + (1.0F - f) * 0.5F;
        float d2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5F;
        EntityItem entityitem = new EntityItem(world, x + d0, y + d1, z + d2, stack);
        entityitem.delayBeforeCanPickup = 1;
        if (stack.hasTagCompound()) {
            entityitem.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
        }
        world.spawnEntityInWorld(entityitem);
        return entityitem;
    }

    public static NBTTagCompound checkAndSetCompound(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            NBTTagCompound tag = new NBTTagCompound();
            stack.setTagCompound(tag);
            return tag;
        }
        return null;
    }

    public static String capitalizeFirstLetter(String string) {
        return Character.toUpperCase(string.charAt(0)) + string.substring(1);
    }

    public static void addOreDictColors() {
        oreDictColor.put("Gold", new Color(255, 255, 0).getRGB());
        oreDictColor.put("Iron", new Color(255, 204, 204).getRGB());
        oreDictColor.put("Copper", new Color(204, 102, 51).getRGB());
        oreDictColor.put("Tin", new Color(135, 154, 168).getRGB());
        oreDictColor.put("Lead", new Color(102, 102, 153).getRGB());
        oreDictColor.put("Ardite", new Color(255, 102, 0).getRGB());
        oreDictColor.put("Cobalt", new Color(0, 60, 255).getRGB());
        oreDictColor.put("Nickel", new Color(204, 204, 204).getRGB());
        oreDictColor.put("Silver", new Color(187, 189, 184).getRGB());
        oreDictColor.put("Platinum", new Color(30, 208, 243).getRGB());
        oreDictColor.put("Aluminium", new Color(198, 206, 130).getRGB());
        oreDictColor.put("Aluminum", new Color(198, 206, 130).getRGB());
        oreDictColor.put("Uranium", new Color(90, 159, 50).getRGB());
    }

    //Shamelessly ripped off of CoFH Lib
    public static boolean fillContainerFromHandler(World world, IFluidHandler handler, EntityPlayer player, FluidStack tankFluid) {
        ItemStack container = player.getCurrentEquippedItem();
        if (FluidContainerRegistry.isEmptyContainer(container)) {
            ItemStack returnStack = FluidContainerRegistry.fillFluidContainer(tankFluid, container);
            FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(returnStack);
            if (fluid == null || returnStack == null) {
                return false;
            }
            if (!player.capabilities.isCreativeMode) {
                if (container.stackSize == 1) {
                    container = container.copy();
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, returnStack);
                } else if (!player.inventory.addItemStackToInventory(returnStack)) {
                    return false;
                }
                handler.drain(ForgeDirection.UNKNOWN, fluid.amount, true);
                container.stackSize--;
                if (container.stackSize <= 0) {
                    container = null;
                }
            } else {
                handler.drain(ForgeDirection.UNKNOWN, fluid.amount, true);
            }
            return true;
        }
        return false;
    }

    //Shamelessly ripped off of CoFH Lib
    public static boolean fillHandlerWithContainer(World world, IFluidHandler handler, EntityPlayer player) {
        ItemStack container = player.getCurrentEquippedItem();
        FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(container);
        if (fluid != null) {
            if (handler.fill(ForgeDirection.UNKNOWN, fluid, false) == fluid.amount || player.capabilities.isCreativeMode) {
                if (world.isRemote) {
                    return true;
                }
                handler.fill(ForgeDirection.UNKNOWN, fluid, true);
                if (!player.capabilities.isCreativeMode) {
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, RandomUtils.consumeItem(container));
                }
                return true;
            }
        }
        return false;
    }

    //Shamelessly ripped off of CoFH Lib
    public static ItemStack consumeItem(ItemStack stack) {
        Item item = stack.getItem();
        boolean largerStack = stack.stackSize > 1;
        if (largerStack) {
            stack.stackSize -= 1;
        }
        if (item.hasContainerItem(stack)) {
            ItemStack ret = item.getContainerItem(stack);
            if (ret == null) {
                return null;
            }
            if (ret.isItemStackDamageable() && ret.getItemDamage() > ret.getMaxDamage()) {
                ret = null;
            }
            return ret;
        }
        return largerStack ? stack : null;
    }

    public static boolean areStacksEqual(ItemStack[] stack1, ItemStack[] stack2, boolean exactAmountAndNBT) {
        if (stack1.length != stack2.length) {
            return false;
        }
        if (exactAmountAndNBT) {
            for (int i = 0; i < stack1.length; i++) {
                if (!(stack1[i] == stack2[i])) {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < stack1.length; i++) {
                if (!stack1[i].isItemEqual(stack2[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    public static String addStringToEnd(String input, String toAdd) {
        return input + toAdd;
    }

    public static Entity teleportEntitySameDim(int x, int y, int z, Entity entity, String name) {
        if (entity != null) {
            if (entity.timeUntilPortal <= 0) {
                if (entity instanceof EntityPlayer) {
                    EntityPlayerMP player = (EntityPlayerMP) entity;
                    player.setPositionAndUpdate(x, y, z);
                    player.worldObj.updateEntityWithOptionalForce(player, false);
                    player.playerNetServerHandler.sendPacket(new S06PacketUpdateHealth(player.getHealth(), player.getFoodStats().getFoodLevel(), player.getFoodStats().getSaturationLevel()));
                    player.timeUntilPortal = 150;
                    SoulNetworkHandler.syphonFromNetwork(name, 1000);
                    return player;
                } else {
                    WorldServer world = (WorldServer) entity.worldObj;
                    if (entity != null) {
                        entity.setPosition(x, y, z);
                        entity.timeUntilPortal = 150;
                    }
                    world.resetUpdateEntityTick();
                    SoulNetworkHandler.syphonFromNetwork(name, 1000);
                    return entity;
                }
            }
        }
        return null;
    }

    //Adapated from Enhanced Portals 3 code
    public static Entity teleportEntityToDim(World oldWorld, World newWorld, int x, int y, int z, Entity entity, String name) {
        if (entity != null && oldWorld != null && newWorld != null) {
            if (entity.timeUntilPortal <= 0) {
                WorldServer oldWorldServer = (WorldServer) oldWorld;
                WorldServer newWorldServer = (WorldServer) newWorld;
                if (entity instanceof EntityPlayer) {
                    EntityPlayerMP player = (EntityPlayerMP) entity;
                    if (!player.worldObj.isRemote) {
                        player.worldObj.theProfiler.startSection("portal");
                        player.worldObj.theProfiler.startSection("changeDimension");
                        ServerConfigurationManager config = player.mcServer.getConfigurationManager();
                        player.closeScreen();
                        player.dimension = newWorldServer.provider.dimensionId;
                        player.playerNetServerHandler.sendPacket(new S07PacketRespawn(player.dimension, player.worldObj.difficultySetting, newWorldServer.getWorldInfo().getTerrainType(), player.theItemInWorldManager.getGameType()));
                        oldWorldServer.removeEntity(player);
                        player.isDead = false;
                        player.setLocationAndAngles(x, y, z, player.rotationYaw, player.rotationPitch);
                        newWorldServer.spawnEntityInWorld(player);
                        player.setWorld(newWorldServer);
                        config.func_72375_a(player, oldWorldServer);
                        player.playerNetServerHandler.setPlayerLocation(x, y, z, entity.rotationYaw, entity.rotationPitch);
                        player.theItemInWorldManager.setWorld(newWorldServer);
                        config.updateTimeAndWeatherForPlayer(player, newWorldServer);
                        config.syncPlayerInventory(player);
                        player.worldObj.theProfiler.endSection();
                        oldWorldServer.resetUpdateEntityTick();
                        newWorldServer.resetUpdateEntityTick();
                        player.worldObj.theProfiler.endSection();
                        for (Iterator<PotionEffect> potion = player.getActivePotionEffects().iterator(); potion.hasNext(); ) {
                            player.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(player.getEntityId(), (PotionEffect) potion.next()));
                        }
                        player.playerNetServerHandler.sendPacket(new S1FPacketSetExperience(player.experience, player.experienceTotal, player.experienceLevel));
                        FMLCommonHandler.instance().firePlayerChangedDimensionEvent(player, oldWorldServer.provider.dimensionId, player.dimension);
                        player.timeUntilPortal = 150;
                    }
                    player.worldObj.theProfiler.endSection();
                    SoulNetworkHandler.syphonFromNetwork(name, 10000);
                    return player;
                } else {
                    NBTTagCompound tag = new NBTTagCompound();
                    entity.writeToNBTOptional(tag);
                    entity.setDead();
                    Entity teleportedEntity = EntityList.createEntityFromNBT(tag, newWorldServer);
                    if (teleportedEntity != null) {
                        teleportedEntity.setLocationAndAngles(x, y, z, entity.rotationYaw, entity.rotationPitch);
                        teleportedEntity.forceSpawn = true;
                        newWorldServer.spawnEntityInWorld(teleportedEntity);
                        teleportedEntity.setWorld(newWorldServer);
                        teleportedEntity.timeUntilPortal = 150;
                    }
                    oldWorldServer.resetUpdateEntityTick();
                    newWorldServer.resetUpdateEntityTick();
                    SoulNetworkHandler.syphonFromNetwork(name, 10000);
                    return teleportedEntity;
                }
            }
        }
        return null;
    }

    public static void writeLog(String string, String fileName) {
        try {
            File log = new File(String.valueOf(DimensionManager.getCurrentSaveRootDirectory()) + "/" + fileName);
            if (!log.exists()) {
                if (log.createNewFile()) {
                    Sanguimancy.logger.info("Creating " + fileName + " in " + String.valueOf(DimensionManager.getCurrentSaveRootDirectory()));
                }
            }
            FileWriter fileWriter = new FileWriter(log.getAbsoluteFile(), true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(string);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            Sanguimancy.logger.error(fileName + " was not found in " + String.valueOf(DimensionManager.getCurrentSaveRootDirectory()));
        }
    }

    public static class SanguimancyItemStacks {

        // Items
        public static ItemStack unattunedPlayerSacrificer = new ItemStack(ItemsRegistry.playerSacrificer, 1, 0);
        public static ItemStack attunnedPlayerSacrificer = new ItemStack(ItemsRegistry.playerSacrificer, 1, 1);
        public static ItemStack focusedPlayerSacrificer = new ItemStack(ItemsRegistry.playerSacrificer, 1, 2);
        public static ItemStack wayToDie = new ItemStack(ItemsRegistry.playerSacrificer, 1, 3);
        public static ItemStack addCorruption = new ItemStack(ItemsRegistry.soulCorruptionTest, 1, 0);
        public static ItemStack removeCorruption = new ItemStack(ItemsRegistry.soulCorruptionTest, 1, 1);
        public static ItemStack negateCorruption = new ItemStack(ItemsRegistry.soulCorruptionTest, 1, 2);
        public static ItemStack corruptionReader = new ItemStack(ItemsRegistry.soulCorruptionTest, 1, 3);
        public static ItemStack corruptedDemonShard = new ItemStack(ItemsRegistry.corruptedDemonShard);
        public static ItemStack corruptionCatalist = new ItemStack(ItemsRegistry.corruptionCatalyst);
        public static ItemStack oreLump = new ItemStack(ItemsRegistry.oreLump);

        // Blocks
        public static ItemStack altarEmitter = new ItemStack(BlocksRegistry.altarEmitter);
        public static ItemStack altarDiviner = new ItemStack(BlocksRegistry.altarDiviner);
        public static ItemStack sacrificeTransferrer = new ItemStack(BlocksRegistry.sacrificeTransfer);
        public static ItemStack diamondOreIllusion = new ItemStack(BlocksRegistry.illusion, 1, 0);
        public static ItemStack diamondBlockIllusion = new ItemStack(BlocksRegistry.illusion, 1, 1);
        public static ItemStack glowstoneIllusion = new ItemStack(BlocksRegistry.illusion, 1, 2);
        public static ItemStack netherrackIllusion = new ItemStack(BlocksRegistry.illusion, 1, 3);
        public static ItemStack quartzOreIllusion = new ItemStack(BlocksRegistry.illusion, 1, 4);
        public static ItemStack endStoneIllusion = new ItemStack(BlocksRegistry.illusion, 1, 5);
        public static ItemStack pinkWoolIllusion = new ItemStack(BlocksRegistry.illusion, 1, 6);
        public static ItemStack lavaIllusion = new ItemStack(BlocksRegistry.illusion, 1, 7);
        public static ItemStack jackOLanternIllusion = new ItemStack(BlocksRegistry.illusion, 1, 8);
        public static ItemStack bedrockIllusion = new ItemStack(BlocksRegistry.illusion, 1, 9);
        public static ItemStack obsidianIllusion = new ItemStack(BlocksRegistry.illusion, 1, 10);
        public static ItemStack glassIllusion = new ItemStack(BlocksRegistry.illusion, 1, 11);
        public static ItemStack snowIllusion = new ItemStack(BlocksRegistry.illusion, 1, 12);
        public static ItemStack melonIllusion = new ItemStack(BlocksRegistry.illusion, 1, 13);
        public static ItemStack goldBlockIllusion = new ItemStack(BlocksRegistry.illusion, 1, 14);
        public static ItemStack clayIllusion = new ItemStack(BlocksRegistry.illusion, 1, 15);
        public static ItemStack corruptionCrystallizer = new ItemStack(BlocksRegistry.corruptionCrystallizer);
        public static ItemStack lumpCleaner = new ItemStack(BlocksRegistry.lumpCleaner);
        public static ItemStack bloodTank = new ItemStack(BlocksRegistry.bloodTank);
    }

    public static class ChunkloadingUtils implements ForgeChunkManager.OrderedLoadingCallback {
        @Override
        public void ticketsLoaded(java.util.List<ForgeChunkManager.Ticket> tickets, World world) {
            for (ForgeChunkManager.Ticket ticket : tickets) {
                int x = ticket.getModData().getInteger("tileX");
                int y = ticket.getModData().getInteger("tileY");
                int z = ticket.getModData().getInteger("tileZ");
                if (world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof TileDimensionalPortal) {
                    ((TileDimensionalPortal) world.getTileEntity(x, y, z)).requestTicket();
                }
            }
        }

        @Override
        public java.util.List<ForgeChunkManager.Ticket> ticketsLoaded(java.util.List<ForgeChunkManager.Ticket> tickets, World world, int maxTicketCount) {
            java.util.List<ForgeChunkManager.Ticket> validTickets = Lists.newArrayList();
            for (ForgeChunkManager.Ticket ticket : tickets) {
                int x = ticket.getModData().getInteger("tileX");
                int y = ticket.getModData().getInteger("tileY");
                int z = ticket.getModData().getInteger("tileZ");
                if (world.getTileEntity(x, y, z) instanceof TileDimensionalPortal)
                    validTickets.add(ticket);
            }
            return validTickets;
        }

        public static void unforceChunks(ForgeChunkManager.Ticket chunkTicket) {
            if (chunkTicket != null) {
                Set<ChunkCoordIntPair> chunks = chunkTicket.getChunkList();
                if (chunks.size() == 0) return;
                for (ChunkCoordIntPair c : chunks) ForgeChunkManager.unforceChunk(chunkTicket, c);
            }
        }

        public static void forceChunks(ForgeChunkManager.Ticket chunkTicket, TileEntity tile) {
            if (chunkTicket != null) {
                Set<ChunkCoordIntPair> chunks = chunkTicket.getChunkList();
                int x = tile.xCoord >> 4;
                int z = tile.zCoord >> 4;
                ChunkCoordIntPair chunkPair = new ChunkCoordIntPair(x, z);
                if (!chunks.contains(chunkPair)) ForgeChunkManager.forceChunk(chunkTicket, chunkPair);
            }
        }
    }
}
