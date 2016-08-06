package tombenpotter.sanguimancy.util;

import WayofTime.bloodmagic.registry.ModItems;
import com.google.common.base.Strings;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.oredict.OreDictionary;
import tombenpotter.oldsanguimancy.registry.ItemsRegistry;
import tombenpotter.oldsanguimancy.world.WorldProviderSoulNetworkDimension;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.api.objects.MapKey;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class RandomUtils {

    public static HashMap<String, Integer> oreDictColor = new HashMap<String, Integer>();
    public static Item.ToolMaterial corruptedMaterial = EnumHelper.addToolMaterial("corruptedToolMaterial", Integer.MAX_VALUE, 9000, 32, 10, 32);
    public static HashMap<MapKey, ItemStack> logToPlank = new HashMap<MapKey, ItemStack>();
    public static ArrayList<ItemStack> oreLumpList = new ArrayList<ItemStack>();

    public static void dropItems(World world, BlockPos pos) {
        Random rand = new Random();
        TileEntity tileEntity = world.getTileEntity(pos);
        if (!(tileEntity instanceof IInventory)) return;
        IInventory inventory = (IInventory) tileEntity;
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack item = inventory.getStackInSlot(i);
            if (item != null && item.stackSize > 0) {
                float rx = rand.nextFloat() * 0.8F + 0.1F;
                float ry = rand.nextFloat() * 0.8F + 0.1F;
                float rz = rand.nextFloat() * 0.8F + 0.1F;
                EntityItem entityItem = new EntityItem(world, pos.getX() + rx, pos.getY() + ry, pos.getZ() + rz, new ItemStack(item.getItem(), item.stackSize, item.getItemDamage()));
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
        if (!world.isRemote) {
            float f = 0.7F;
            float d0 = world.rand.nextFloat() * f + (1.0F - f) * 0.5F;
            float d1 = world.rand.nextFloat() * f + (1.0F - f) * 0.5F;
            float d2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5F;
            EntityItem entityitem = new EntityItem(world, x + d0, y + d1, z + d2, stack);
            entityitem.setPickupDelay(1);
            if (stack.hasTagCompound()) {
                entityitem.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
            }
            world.spawnEntityInWorld(entityitem);
            return entityitem;
        }
        return null;
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
        ItemStack container = player.getHeldItem(EnumHand.MAIN_HAND);
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
                handler.drain(fluid.amount, true);
                container.stackSize--;
                if (container.stackSize <= 0) {
                    container = null;
                }
            } else {
                handler.drain(fluid.amount, true);
            }
            return true;
        }
        return false;
    }

    //Shamelessly ripped off of CoFH Lib
    public static boolean fillHandlerWithContainer(World world, IFluidHandler handler, EntityPlayer player) {
        ItemStack container = player.getHeldItem(EnumHand.MAIN_HAND);
        FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(container);
        if (fluid != null) {
            if (handler.fill(fluid, false) == fluid.amount || player.capabilities.isCreativeMode) {
                if (world.isRemote) {
                    return true;
                }
                handler.fill(fluid, true);
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

    public static void unbindItemStack(ItemStack stack) {
        checkAndSetCompound(stack);
        if (stack.getTagCompound().hasKey("ownerName") && !stack.getTagCompound().getString("ownerName").equals("")) {
            stack.getTagCompound().setString("ownerName", "");
        }
    }

    public static String getItemOwner(ItemStack stack) {
        checkAndSetCompound(stack);
        try {
            return stack.getTagCompound().getString("ownerName");
        } catch (NullPointerException e) {
            return "";
        }
    }

    public static void createSNDimension() {
        int dimID = ConfigHandler.snDimID;
        if (!DimensionManager.isDimensionRegistered(dimID)) {
            WorldProviderSoulNetworkDimension provider = new WorldProviderSoulNetworkDimension();
            provider.setDimension(dimID);
            DimensionType type = DimensionType.register("SN_DIMENSION", "_sndim", dimID, provider.getClass(), true);
            DimensionManager.registerDimension(dimID, type);
        }
    }

    public static void dropBlockDropsWithFortune(World world, IBlockState state, BlockPos pos, int fortune) {
        if (state.getBlock() != null && state.getBlock().getDrops(world, pos, state, 0) != null) {
            for (ItemStack stack : state.getBlock().getDrops(world, pos, state, fortune)) {
                dropItemStackInWorld(world, pos.getX(), pos.getY(), pos.getZ(), stack.copy());
            }
        }
    }

    public static void dropSilkDrops(World world, IBlockState state, BlockPos pos) {
        if (state.getBlock() != null && state.getBlock().canSilkHarvest(world, pos, state, null)) {
            ItemStack copyStack = new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state)).copy();
            dropItemStackInWorld(world, pos.getX(), pos.getY(), pos.getZ(), copyStack);
        } else {
            dropBlockDropsWithFortune(world, state, pos, 0);
        }
    }

    public static void dropSmeltDrops(World world, IBlockState state, BlockPos pos) {
        if (state.getBlock().getDrops(world, pos, state, 0) != null) {
            for (ItemStack stack : state.getBlock().getDrops(world, pos, state, 0)) {
                ItemStack copyStack = stack.copy();
                if (FurnaceRecipes.instance().getSmeltingResult(copyStack) == null) {
                    dropBlockDropsWithFortune(world, state, pos, 0);
                } else {
                    ItemStack output = FurnaceRecipes.instance().getSmeltingResult(stack).copy();
                    dropItemStackInWorld(world, pos.getX(), pos.getY(), pos.getZ(), output);
                }
            }
        }
    }

    public static ArrayList<String> getItemStackName(ItemStack stack) {
        ArrayList<String> list = new ArrayList<String>();
        for (int id : OreDictionary.getOreIDs(stack)) {
            list.add(OreDictionary.getOreName(id));
        }
        return list;
    }

    public static void setLogToPlank() {
        getCraftingRecipeForOreDictItem("plankWood", logToPlank);
    }

    public static void getCraftingRecipeForOreDictItem(String ore, HashMap<MapKey, ItemStack> map) {
        List<ItemStack> arrayList = OreDictionary.getOres(ore);
        for (Object o : CraftingManager.getInstance().getRecipeList()) {
            IRecipe recipe = (IRecipe) o;
            ItemStack output = recipe.getRecipeOutput();
            if (output != null) {
                boolean match = false;
                for (ItemStack plank : arrayList) {
                    if (OreDictionary.itemMatches(plank, output, false)) {
                        match = true;
                        break;
                    }
                }
                if (match) {
                    ItemStack log = null;
                    if (recipe instanceof ShapedRecipes) {
                        log = ((ShapedRecipes) recipe).recipeItems[0];
                    } else if (recipe instanceof ShapelessRecipes) {
                        log = (ItemStack) ((ShapelessRecipes) recipe).recipeItems.get(0);
                    } else {
                        //Some weird crafting type
                    }
                    ItemStack plank = output.copy();
                    plank.stackSize = 1;
                    if (log != null) {
                        map.put(new MapKey(log.copy()), plank);
                    }
                }
            }
        }
    }

    public static NBTTagCompound getModTag(EntityPlayer player, String modName) {
        NBTTagCompound tag = player.getEntityData();
        NBTTagCompound persistTag;
        if (tag.hasKey(EntityPlayer.PERSISTED_NBT_TAG)) persistTag = tag.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
        else {
            persistTag = new NBTTagCompound();
            tag.setTag(EntityPlayer.PERSISTED_NBT_TAG, persistTag);
        }
        NBTTagCompound modTag;
        if (persistTag.hasKey(modName)) {
            modTag = persistTag.getCompoundTag(modName);
        } else {
            modTag = new NBTTagCompound();
            persistTag.setTag(modName, modTag);
        }
        return modTag;
    }

    public static void setOreLumpList() {
        for (String ore : OreDictionary.getOreNames()) {
            if (!Strings.isNullOrEmpty(ore) && ore.startsWith("ore")) {
                String output = ore.substring(3);
                if (!OreDictionary.getOres(ore).isEmpty() && !OreDictionary.getOres("ingot" + output).isEmpty()) {
                    ItemStack stack = new ItemStack(ItemsRegistry.oreLump);
                    checkAndSetCompound(stack);
                    stack.getTagCompound().setString("ore", output);
                    oreLumpList.add(stack);
                }
            }
        }
    }

    public static ItemStack getOrbForLevel(int orbLevel) {
        switch (orbLevel) {
            default:
                return new ItemStack(ModItems.bloodOrb);
            case 1:
                return new ItemStack(ModItems.bloodOrb, 1);
            case 2:
                return new ItemStack(ModItems.bloodOrb, 2);
            case 3:
                return new ItemStack(ModItems.bloodOrb, 3);
            case 4:
                return new ItemStack(ModItems.bloodOrb, 4);
            case 5:
                return new ItemStack(ModItems.bloodOrb, 5);
            case 6:
                return new ItemStack(ModItems.bloodOrb, 6);
        }
    }
}
