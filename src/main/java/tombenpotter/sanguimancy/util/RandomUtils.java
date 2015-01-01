package tombenpotter.sanguimancy.util;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.oredict.OreDictionary;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.api.objects.MapKey;
import tombenpotter.sanguimancy.registry.BlocksRegistry;
import tombenpotter.sanguimancy.registry.ItemsRegistry;
import tombenpotter.sanguimancy.world.WorldProviderSoulNetworkDimension;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class RandomUtils {

    public static HashMap<String, Integer> oreDictColor = new HashMap<String, Integer>();
    public static Item.ToolMaterial corruptedMaterial = EnumHelper.addToolMaterial("corruptedToolMaterial", Integer.MAX_VALUE, 9000, 32, 10, 32);
    public static HashMap<MapKey, ItemStack> logToPlank = new HashMap<MapKey, ItemStack>();

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

    public static void unbindItemStack(ItemStack stack) {
        checkAndSetCompound(stack);
        if (stack.stackTagCompound.hasKey("ownerName") && !stack.stackTagCompound.getString("ownerName").equals("")) {
            stack.stackTagCompound.setString("ownerName", "");
        }
    }

    public static String getItemOwner(ItemStack stack) {
        checkAndSetCompound(stack);
        try {
            return stack.stackTagCompound.getString("ownerName");
        } catch (NullPointerException e) {
            return "";
        }
    }

    public static void createSNDimension() {
        int dimID = ConfigHandler.snDimID;
        if (!DimensionManager.isDimensionRegistered(dimID)) {
            WorldProviderSoulNetworkDimension provider = new WorldProviderSoulNetworkDimension();
            provider.setDimension(dimID);
            DimensionManager.registerProviderType(dimID, provider.getClass(), true);
            DimensionManager.registerDimension(dimID, dimID);
        }
    }

    public static void dropBlockDropsWithFortune(World world, Block block, int x, int y, int z, int metadata, int fortune) {
        if (block != null && block.getDrops(world, x, y, z, world.getBlockMetadata(x, y, z), 0) != null) {
            for (ItemStack stack : block.getDrops(world, x, y, z, metadata, fortune)) {
                dropItemStackInWorld(world, x, y, z, stack.copy());
            }
        }
    }

    public static void dropSilkDrops(World world, Block block, int x, int y, int z, int metadata) {
        if (block != null && block.canSilkHarvest(world, null, x, y, z, metadata)) {
            ItemStack copyStack = new ItemStack(block, 1, world.getBlockMetadata(x, y, z)).copy();
            dropItemStackInWorld(world, x, y, z, copyStack);
        } else {
            dropBlockDropsWithFortune(world, block, x, y, z, metadata, 0);
        }
    }

    public static void dropSmeltDrops(World world, Block block, int x, int y, int z, int metadata) {
        if (block.getDrops(world, x, y, z, world.getBlockMetadata(x, y, z), 0) != null) {
            for (ItemStack stack : block.getDrops(world, x, y, z, world.getBlockMetadata(x, y, z), 0)) {
                ItemStack copyStack = stack.copy();
                if (FurnaceRecipes.smelting().getSmeltingResult(copyStack) == null) {
                    dropBlockDropsWithFortune(world, block, x, y, z, metadata, 0);
                } else {
                    ItemStack output = FurnaceRecipes.smelting().getSmeltingResult(stack).copy();
                    dropItemStackInWorld(world, x, y, z, output);
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

    //Rendering code taken and adapted from Alex_Hawk's Sanguine Extras mod. He made it, and helped me debug my modifications!
    public static void renderBlock(RenderWorldLastEvent event, EntityPlayer player, World world, Block block, int metadata, int x, int y, int z) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y + 1, z));
        double px = player.lastTickPosX + (player.posX - player.lastTickPosX) * event.partialTicks;
        double py = player.lastTickPosY + (player.posY - player.lastTickPosY) * event.partialTicks;
        double pz = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.partialTicks;
        double minX = x - px;
        double minY = y - py;
        double minZ = z - pz;
        double maxX = minX + 1;
        double maxY = minY + 1;
        double maxZ = minZ + 1;
        float textureMinU;
        float textureMinV;
        float textureMaxU;
        float textureMaxV;
        Minecraft.getMinecraft().renderEngine.bindTexture(Minecraft.getMinecraft().renderEngine.getResourceLocation(0));
        {
            textureMinU = block.getIcon(0, metadata).getMinU();
            textureMinV = block.getIcon(0, metadata).getMinV();
            textureMaxU = block.getIcon(0, metadata).getMaxU();
            textureMaxV = block.getIcon(0, metadata).getMaxV();
            tessellator.addVertexWithUV(minX, minY, minZ, textureMinU, textureMinV);
            tessellator.addVertexWithUV(maxX, minY, minZ, textureMaxU, textureMinV);
            tessellator.addVertexWithUV(maxX, minY, maxZ, textureMaxU, textureMaxV);
            tessellator.addVertexWithUV(minX, minY, maxZ, textureMinU, textureMaxV);
        }
        {
            textureMinU = block.getIcon(1, metadata).getMinU();
            textureMinV = block.getIcon(1, metadata).getMinV();
            textureMaxU = block.getIcon(1, metadata).getMaxU();
            textureMaxV = block.getIcon(1, metadata).getMaxV();
            tessellator.addVertexWithUV(minX, maxY, maxZ, textureMinU, textureMaxV);
            tessellator.addVertexWithUV(maxX, maxY, maxZ, textureMaxU, textureMaxV);
            tessellator.addVertexWithUV(maxX, maxY, minZ, textureMaxU, textureMinV);
            tessellator.addVertexWithUV(minX, maxY, minZ, textureMinU, textureMinV);
        }
        {
            textureMinU = block.getIcon(2, metadata).getMinU();
            textureMinV = block.getIcon(2, metadata).getMinV();
            textureMaxU = block.getIcon(2, metadata).getMaxU();
            textureMaxV = block.getIcon(2, metadata).getMaxV();
            tessellator.addVertexWithUV(maxX, minY, minZ, textureMinU, textureMaxV);
            tessellator.addVertexWithUV(minX, minY, minZ, textureMaxU, textureMaxV);
            tessellator.addVertexWithUV(minX, maxY, minZ, textureMaxU, textureMinV);
            tessellator.addVertexWithUV(maxX, maxY, minZ, textureMinU, textureMinV);
        }
        {
            textureMinU = block.getIcon(3, metadata).getMinU();
            textureMinV = block.getIcon(3, metadata).getMinV();
            textureMaxU = block.getIcon(3, metadata).getMaxU();
            textureMaxV = block.getIcon(3, metadata).getMaxV();
            tessellator.addVertexWithUV(minX, minY, maxZ, textureMinU, textureMaxV);
            tessellator.addVertexWithUV(maxX, minY, maxZ, textureMaxU, textureMaxV);
            tessellator.addVertexWithUV(maxX, maxY, maxZ, textureMaxU, textureMinV);
            tessellator.addVertexWithUV(minX, maxY, maxZ, textureMinU, textureMinV);
        }
        {
            textureMinU = block.getIcon(4, metadata).getMinU();
            textureMinV = block.getIcon(4, metadata).getMinV();
            textureMaxU = block.getIcon(4, metadata).getMaxU();
            textureMaxV = block.getIcon(4, metadata).getMaxV();
            tessellator.addVertexWithUV(minX, minY, minZ, textureMinU, textureMaxV);
            tessellator.addVertexWithUV(minX, minY, maxZ, textureMaxU, textureMaxV);
            tessellator.addVertexWithUV(minX, maxY, maxZ, textureMaxU, textureMinV);
            tessellator.addVertexWithUV(minX, maxY, minZ, textureMinU, textureMinV);
        }
        {
            textureMinU = block.getIcon(5, metadata).getMinU();
            textureMinV = block.getIcon(5, metadata).getMinV();
            textureMaxU = block.getIcon(5, metadata).getMaxU();
            textureMaxV = block.getIcon(5, metadata).getMaxV();
            tessellator.addVertexWithUV(maxX, minY, maxZ, textureMinU, textureMaxV);
            tessellator.addVertexWithUV(maxX, minY, minZ, textureMaxU, textureMaxV);
            tessellator.addVertexWithUV(maxX, maxY, minZ, textureMaxU, textureMinV);
            tessellator.addVertexWithUV(maxX, maxY, maxZ, textureMinU, textureMinV);
        }
        tessellator.draw();
    }

    public static void setLogToPlank() {
        ArrayList<ItemStack> arrayList = OreDictionary.getOres("plankWood");
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
                        logToPlank.put(new MapKey(log.copy()), plank);
                    }
                }
            }
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
        public static ItemStack bloodAmulet = new ItemStack(ItemsRegistry.bloodAmulet);
        public static ItemStack chunkClaimer = new ItemStack(ItemsRegistry.chunkClaimer);
        public static ItemStack corruptedSword = new ItemStack(ItemsRegistry.corruptedSword);
        public static ItemStack corruptedPickaxe = new ItemStack(ItemsRegistry.corruptedPickaxe);
        public static ItemStack corruptedShovel = new ItemStack(ItemsRegistry.corruptedShovel);

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
        public static ItemStack boundItem = new ItemStack(BlocksRegistry.boundItem);
        public static ItemStack simpleBranch = new ItemStack(BlocksRegistry.simpleBranch);
        public static ItemStack simpleKnot = new ItemStack(BlocksRegistry.simpleKnot);
        public static ItemStack toggleKnot = new ItemStack(BlocksRegistry.toggleKnot);
    }
}
