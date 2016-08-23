package tombenpotter.sanguimancy.blocks;

import WayofTime.bloodmagic.block.BlockLifeEssence;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.tiles.TileBloodCleaner;
import tombenpotter.sanguimancy.util.RandomUtils;

import javax.annotation.Nullable;

public class BlockBloodCleaner extends BlockContainer {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;


    public BlockBloodCleaner(Material material) {
        super(material);
        setHardness(5.0F);
        setCreativeTab(Sanguimancy.creativeTab);
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        this.setDefaultFacing(world, pos, state);
    }

    public void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            IBlockState iblockstate = worldIn.getBlockState(pos.north());
            IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
            IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
            IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock()) {
                enumfacing = EnumFacing.SOUTH;
            } else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock()) {
                enumfacing = EnumFacing.NORTH;
            } else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock()) {
                enumfacing = EnumFacing.EAST;
            } else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock()) {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }

    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileBloodCleaner();
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        RandomUtils.dropItems(world, pos);

        super.breakBlock(world, pos, state);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileBloodCleaner tile = (TileBloodCleaner) world.getTileEntity(pos);
        if (RandomUtils.fillHandlerWithContainer(world, tile.getTank(null), player)) {
            tile.markForUpdate();
            return true;
        }
        if (RandomUtils.fillContainerFromHandler(world, tile.getTank(null), player, new FluidStack(BlockLifeEssence.getLifeEssence(), Fluid.BUCKET_VOLUME))) {
            tile.markForUpdate();
            return true;
        }
        if (FluidContainerRegistry.isContainer(heldItem)) {
            tile.markForUpdate();
            return true;
        }
        player.openGui(Sanguimancy.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }
}
