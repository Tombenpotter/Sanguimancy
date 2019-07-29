package tombenpotter.sanguimancy.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.tiles.TileBloodTank;
import tombenpotter.sanguimancy.util.RandomUtils;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public class BlockBloodTank extends BlockContainer {

    public static int renderId = 10000;
    public static int[] capacities = {16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65336, 131072, 262144, 524288};

    public BlockBloodTank(Material material) {
        super(material);
        setHardness(5.0F);
        setCreativeTab(Sanguimancy.creativeTab);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileBloodTank(capacities[metadata]);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister ir) {
        this.blockIcon = ir.registerIcon(Sanguimancy.texturePath + ":BloodTank");
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileBloodTank fluidHandler = (TileBloodTank) world.getTileEntity(pos);
        if (RandomUtils.fillHandlerWithContainer(world, fluidHandler, player)) {
            world.markBlockForUpdate(hitX, hitY, hitZ);
            return true;
        }
        if (RandomUtils.fillContainerFromHandler(world, fluidHandler, player, fluidHandler.tank.getFluid())) {
            world.markBlockForUpdate(hitX, hitY, hitZ);
            return true;
        }
        if (FluidContainerRegistry.isContainer(player.getCurrentEquippedItem())) {
            world.markBlockForUpdate(hitX, hitY, hitZ);
            return true;
        }
        return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        this.dropBlockAsItem(world, pos, state, 0);
        super.onBlockHarvested(world, pos, state, player);
    }

    @Override
    public void getDrops(@Nonnull NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        if (world.getTileEntity(pos) instanceof TileBloodTank) {
            TileBloodTank tile = (TileBloodTank) world.getTileEntity(pos);
            ItemStack drop = new ItemStack(this, 1);
            NBTTagCompound tag = new NBTTagCompound();
            tile.writeToNBT(tag);
            drop.setTagCompound(tag);
            drops.add(drop);
        }
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileBloodTank) {
            TileBloodTank tile = (TileBloodTank) world.getTileEntity(pos);
            NBTTagCompound tag = stack.getTagCompound();
            if (tag != null) {
                tile.readFromNBT(tag);
            }
        }
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileBloodTank) {
            TileBloodTank tank = (TileBloodTank) tile;
            FluidStack fluid = tank.tank.getFluid();
            if (fluid != null)
                return fluid.getFluid().getLuminosity(fluid);
        }
        return 0;
    }

    @Override
    public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileBloodTank) {
            TileBloodTank tank = (TileBloodTank) tile;
            FluidStack fluid = tank.tank.getFluid();
            if (fluid != null)
                return fluid.getFluid().getColor(fluid);
        }
        return 0xFFFFFF;
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        int meta = 0;
        if (side == 6 || side == 7) {
            meta = 1;
            TileEntity tile = world.getTileEntity(x, y, z);
            if (tile instanceof TileBloodTank) {
                TileBloodTank tank = (TileBloodTank) tile;
                FluidStack fluid = tank.tank.getFluid();
                if (fluid != null) return fluid.getFluid().getIcon(fluid);
            }
        }
        return getIcon(side, meta);
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item id, CreativeTabs tab, List<ItemStack> list) {
        for (int i = 0; i <= 15; i++) {
            list.add(new ItemStack(id, 1, i));
        }
    }

    @Override
    public int damageDropped(IBlockState state) {
        return 0;
    }
}