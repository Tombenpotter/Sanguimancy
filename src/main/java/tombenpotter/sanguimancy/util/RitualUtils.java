package tombenpotter.sanguimancy.util;

import WayofTime.alchemicalWizardry.ModBlocks;
import WayofTime.alchemicalWizardry.common.Int3;
import WayofTime.alchemicalWizardry.common.spell.complex.effect.SpellHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;

public class RitualUtils {

    public static class TimbermanUtils {
        public static ArrayList<Int3> getHarvestablesInArea(World world, int x, int y, int z, int multiplier) {
            ArrayList<Int3> blocks = new ArrayList<Int3>();
            for (int j = -4 * multiplier; j <= 4 * multiplier; j++) {
                for (int i = -4 * multiplier; i <= 4 * multiplier; i++) {
                    for (int k = -4 * multiplier; k <= 4 * multiplier; k++) {
                        if (!world.isAirBlock(x + i, y + j, z + k) && (world.getBlock(x + i, y + j, z + k).isWood(world, x + i, y + j, z + k) || world.getBlock(x + i, y + j, z + k).isLeaves(world, x + i, y + j, z + k))) {
                            blocks.add(new Int3(x + i, y + j, z + k));
                        }
                    }
                }
            }
            return blocks;
        }
    }

    public static class PumpUtils {
        public static ArrayList<Int3> getPumpablesInArea(World world, FluidStack fluid, int x, int y, int z, int multiplier) {
            ArrayList<Int3> blocks = new ArrayList<Int3>();
            for (int j = -16 * multiplier; j <= 16 * multiplier; j++) {
                for (int i = -16 * multiplier; i <= 16 * multiplier; i++) {
                    for (int k = -16 * multiplier; k <= 16 * multiplier; k++) {
                        if (!world.isAirBlock(x + i, y + j, z + k) && world.getBlock(x + i, y + j, z + k) == FluidRegistry.getFluid(fluid.fluidID).getBlock() && world.getBlockMetadata(x + i, y + j, z + k) == 0) {
                            blocks.add(new Int3(x + i, y + j, z + k));
                        }
                    }
                }
            }
            return blocks;
        }
    }

    public static class QuarryUtils {
        public static ArrayList<Int3> getBlocksInArea(World world, int x, int y, int z, int multiplier) {
            ArrayList<Int3> blocks = new ArrayList<Int3>();
            for (int j = 0; j <= 32 * multiplier; j++) {
                for (int i = -16 * multiplier; i <= 16 * multiplier; i++) {
                    for (int k = -16 * multiplier; k <= 16 * multiplier; k++) {
                        if (!world.isAirBlock(x + i, y + j, z + k) && world.getBlock(x + i, y + j, z + k).getBlockHardness(world, x + i, y + j, z + k) >= 0) {
                            if (!(world.getBlock(x + i, y + j, z + k) == ModBlocks.blockMasterStone) && !(world.getBlock(x + i, y + j, z + k) == ModBlocks.ritualStone) && !(world.getTileEntity(x + i, y + j, z + k) instanceof IInventory)) {
                                blocks.add(new Int3(x + i, y + j, z + k));
                            }
                        }
                    }
                }
            }
            return blocks;
        }
    }

    public static void placeInInventory(Block block, World world, int x, int y, int z, IInventory tile) {
        if (block.getDrops(world, x, y, z, world.getBlockMetadata(x, y, z), 0) != null) {
            for (ItemStack stack : block.getDrops(world, x, y, z, world.getBlockMetadata(x, y, z), 0)) {
                ItemStack copyStack = stack.copy();
                SpellHelper.insertStackIntoInventory(copyStack, tile);
                if (copyStack.stackSize > 0) {
                    world.spawnEntityInWorld(new EntityItem(world, x + 0.4, y + 2, z + 0.5, copyStack));
                }
            }
        }
    }

    public static void silkPlaceInInventory(Block block, World world, int x, int y, int z, IInventory tile) {
        if (block.canSilkHarvest(world, null, x, y, z, world.getBlockMetadata(x, y, z))) {
            ItemStack copyStack = new ItemStack(block, 1, world.getBlockMetadata(x, y, z)).copy();
            SpellHelper.insertStackIntoInventory(copyStack, tile);
            if (copyStack.stackSize > 0) {
                world.spawnEntityInWorld(new EntityItem(world, x + 0.4, y + 2, z + 0.5, copyStack));
            }
        }
    }

    public static void smeltPlaceInInventory(Block block, World world, int x, int y, int z, IInventory tile) {
        if (block.getDrops(world, x, y, z, world.getBlockMetadata(x, y, z), 0) != null) {
            for (ItemStack stack : block.getDrops(world, x, y, z, world.getBlockMetadata(x, y, z), 0)) {
                ItemStack copyStack = stack.copy();
                if (FurnaceRecipes.smelting().getSmeltingResult(copyStack) == null) {
                    SpellHelper.insertStackIntoInventory(copyStack, tile);
                    if (copyStack.stackSize > 0) {
                        world.spawnEntityInWorld(new EntityItem(world, x + 0.4, y + 2, z + 0.5, copyStack));
                    }
                } else {
                    ItemStack output = FurnaceRecipes.smelting().getSmeltingResult(stack).copy();
                    SpellHelper.insertStackIntoInventory(output, tile);
                    if (output.stackSize > 0) {
                        world.spawnEntityInWorld(new EntityItem(world, x + 0.4, y + 2, z + 0.5, output));
                    }
                }
            }
        }
    }

    public static int getRangeMultiplier(boolean hasTerrae, boolean hasOrbisTerrae) {
        int multiplier = 1;
        if (hasTerrae) {
            if (hasOrbisTerrae) {
                multiplier = 8;
            } else {
                multiplier = 2;
            }
        } else {
            if (hasOrbisTerrae) {
                multiplier = 4;
            }
        }
        return multiplier;
    }
}
