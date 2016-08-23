package tombenpotter.sanguimancy.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tombenpotter.oldsanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.tiles.TileAltarManipulator;
import tombenpotter.sanguimancy.util.RandomUtils;
import tombenpotter.sanguimancy.util.SanguimancyItemStacks;

import javax.annotation.Nullable;

public class BlockAltarManipulator extends BlockContainer {

    public BlockAltarManipulator(Material material) {
        super(material);
        setHardness(5.0F);
        setCreativeTab(Sanguimancy.tabSanguimancy);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileAltarManipulator();
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        RandomUtils.dropItems(world, pos);
        super.breakBlock(world, pos, state);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileAltarManipulator tile = (TileAltarManipulator) world.getTileEntity(pos);
        if (player.getHeldItem(EnumHand.MAIN_HAND) == null && tile.getInventory(null).getStackInSlot(2) != null) {
            ItemStack stack = tile.getInventory(null).getStackInSlot(2);
            tile.getInventory(null).extractItem(2, stack.stackSize, false);
            player.inventory.addItemStackToInventory(stack);
            tile.markForUpdate();

            return true;
        } else if (player.getHeldItem(EnumHand.MAIN_HAND) != null && player.getHeldItem(EnumHand.MAIN_HAND).isItemEqual(SanguimancyItemStacks.sanguineShifter) && tile.getInventory(null).getStackInSlot(2) == null) {
            ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND).copy();
            stack.stackSize = 1;
            tile.getInventory(null).insertItem(2, stack, false);
            tile.sideToOutput = side.getIndex();
            if (!player.capabilities.isCreativeMode) {
                for (int i = 0; i < stack.stackSize; i++) {
                    if (stack.stackSize <= 0) {
                        player.inventory.deleteStack(stack);
                    } else {
                        player.getHeldItem(EnumHand.MAIN_HAND).stackSize--;
                    }
                }
            }
            tile.markForUpdate();

            return true;
        }
        return false;
    }
}
