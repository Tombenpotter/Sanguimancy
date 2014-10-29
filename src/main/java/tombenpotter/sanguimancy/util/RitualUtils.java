package tombenpotter.sanguimancy.util;

import WayofTime.alchemicalWizardry.common.Int3;
import WayofTime.alchemicalWizardry.common.spell.complex.effect.SpellHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;

public class RitualUtils {

    public static class TimbermanUtils {
        public static ArrayList<Int3> getHarvestablesInArea(World world, int x, int y, int z, int mutliplier) {
            ArrayList<Int3> blocks = new ArrayList<Int3>();
            for (int j = -4 * mutliplier; j <= 4 * mutliplier; j++) {
                for (int i = -4 * mutliplier; i <= 4 * mutliplier; i++) {
                    for (int k = -4 * mutliplier; k <= 4 * mutliplier; k++) {
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
        public static FluidStack pumpFluid(World world, int x, int y, int z, boolean drain) {
            Fluid fluid = FluidRegistry.lookupFluidForBlock(world.getBlock(x, y, z));
            if (fluid != null && FluidRegistry.isFluidRegistered(fluid) && world.getBlockMetadata(x, y, z) == 0) {
                if (drain) world.setBlockToAir(x, y, z);
                return new FluidStack(fluid, FluidContainerRegistry.BUCKET_VOLUME);
            }
            return null;
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
}
