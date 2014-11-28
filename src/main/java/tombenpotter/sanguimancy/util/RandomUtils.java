package tombenpotter.sanguimancy.util;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.registry.BlocksRegistry;
import tombenpotter.sanguimancy.registry.ItemsRegistry;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

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

    public static void writeLog(String string, String fileName) {
        try {
            File log = new File(String.valueOf(DimensionManager.getCurrentSaveRootDirectory()) + "/" + Sanguimancy.texturePath + "/" + fileName);
            if (!log.exists()) {
                if (log.getParentFile().mkdir()) {
                    if (log.createNewFile()) {
                        Sanguimancy.logger.info("Creating " + fileName + " in " + String.valueOf(DimensionManager.getCurrentSaveRootDirectory()));
                    }
                } else if (log.createNewFile()) {
                    Sanguimancy.logger.info("Creating " + fileName + " in " + String.valueOf(DimensionManager.getCurrentSaveRootDirectory()));
                } else {
                    throw new IOException("Failed to create directory " + log.getParent());
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

    public static void fireEvent(Event event) {
        MinecraftForge.EVENT_BUS.post(event);
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
        public static ItemStack bloodAmulet = new ItemStack(ItemsRegistry.bloodAmulet);

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
        public static ItemStack bloodstoneStairs = new ItemStack(BlocksRegistry.bloodStoneStairs);
        public static ItemStack largeBloodstoneStairs = new ItemStack(BlocksRegistry.largeBloodStoneStairs);
        public static ItemStack bloodstoneSlab = new ItemStack(BlocksRegistry.bloodstoneSlab);
        public static ItemStack largeBloodstoneSlab = new ItemStack(BlocksRegistry.largeBloodstoneSlab);
        public static ItemStack doubleBloodstoneSlab = new ItemStack(BlocksRegistry.doubleBloodstoneSlab);
        public static ItemStack doubleLargeBloodstoneSlab = new ItemStack(BlocksRegistry.doubleLargeBloodstoneSlab);
    }
}
