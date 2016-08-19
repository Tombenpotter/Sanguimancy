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
import tombenpotter.sanguimancy.tiles.TileAltarDiviner;
import tombenpotter.sanguimancy.util.RandomUtils;

import javax.annotation.Nullable;

public class BlockAltarDiviner extends BlockContainer {

    public BlockAltarDiviner(Material material) {
        super(material);
        setHardness(5.0F);
        setCreativeTab(Sanguimancy.tabSanguimancy);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileAltarDiviner();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileAltarDiviner tile = (TileAltarDiviner) world.getTileEntity(pos);
        if (player.getHeldItem(EnumHand.MAIN_HAND) == null && tile.getInventory(null).getStackInSlot(0) != null) {
            ItemStack stack = tile.getInventory(null).getStackInSlot(0);
            tile.getInventory(null).extractItem(0, stack.stackSize, false);
            player.inventory.addItemStackToInventory(stack);

            world.notifyBlockUpdate(pos, state, state, 3);
        } else if (player.getHeldItem(EnumHand.MAIN_HAND) != null && tile.getInventory(null).getStackInSlot(0) == null) {
            ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND);
            tile.getInventory(null).insertItem(0, stack.copy(), false);

            if (!player.capabilities.isCreativeMode) {
                for (int i = 0; i < stack.stackSize; i++) {
                    if (stack.stackSize <= 0) {
                        player.inventory.deleteStack(stack);
                    } else {
                        player.getHeldItem(EnumHand.MAIN_HAND).stackSize--;
                    }
                }
            }
            world.notifyBlockUpdate(pos, state, state, 3);
        }
        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        RandomUtils.dropItems(world, pos);
        super.breakBlock(world, pos, state);
    }
}
