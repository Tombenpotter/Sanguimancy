package tombenpotter.sanguimancy.blocks;

import WayofTime.bloodmagic.api.orb.IBloodOrb;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.tiles.TileBloodInterface;
import tombenpotter.sanguimancy.util.RandomUtils;

import javax.annotation.Nullable;

public class BlockBloodInterface extends BlockContainer {

    public BlockBloodInterface(Material material) {
        super(material);
        this.setCreativeTab(Sanguimancy.creativeTab);
        this.setHardness(2.5F);
        this.setSoundType(SoundType.STONE);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileEntity te = world.getTileEntity(pos);

        if (te != null && te instanceof TileBloodInterface) {
            TileBloodInterface bloodInterface = (TileBloodInterface) te;

            if (!(heldItem.getItem() instanceof IBloodOrb)) {
                return true;
            }

            if (!world.isRemote) {
                ItemStack oldStack = bloodInterface.getInventory(null).getStackInSlot(0);
                bloodInterface.getInventory(null).insertItem(0, heldItem, false);
                player.inventory.setInventorySlotContents(player.inventory.currentItem, oldStack);
            }
            return true;
        }

        return false;
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        if (!player.capabilities.isCreativeMode)
            RandomUtils.dropItems(world, pos);
        return super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        RandomUtils.dropItems(world, pos);
        super.breakBlock(world, pos, state);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileBloodInterface();
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(IBlockState blockState, World world, BlockPos pos) {
        TileEntity te = world.getTileEntity(pos);
        if (te != null && te instanceof TileBloodInterface) {
            TileBloodInterface bloodInterface = (TileBloodInterface) te;
            return bloodInterface.getComparatorLevel();
        }
        return 0;
    }
}
