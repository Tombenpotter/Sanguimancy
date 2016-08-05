package tombenpotter.oldsanguimancy.old.ded;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import tombenpotter.oldsanguimancy.Sanguimancy;
import tombenpotter.oldsanguimancy.util.RandomUtils;

import java.util.ArrayList;
import java.util.List;

public class BlockBloodTank extends BlockContainer {

    public static int renderId = 10000;
    public static int[] capacities = {16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65336, 131072, 262144, 524288};

    public BlockBloodTank(Material material) {
        super(material);
        setHardness(5.0F);
        setCreativeTab(Sanguimancy.tabSanguimancy);
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
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        TileBloodTank fluidHandler = (TileBloodTank) world.getTileEntity(x, y, z);
        if (RandomUtils.fillHandlerWithContainer(world, fluidHandler, player)) {
            world.markBlockForUpdate(x, y, z);
            return true;
        }
        if (RandomUtils.fillContainerFromHandler(world, fluidHandler, player, fluidHandler.tank.getFluid())) {
            world.markBlockForUpdate(x, y, z);
            return true;
        }
        if (FluidContainerRegistry.isContainer(player.getCurrentEquippedItem())) {
            world.markBlockForUpdate(x, y, z);
            return true;
        }
        return super.onBlockActivated(world, x, y, z, player, p_149727_6_, p_149727_7_, p_149727_8_, p_149727_9_);
    }

    @Override
    public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {
        this.dropBlockAsItem(world, x, y, z, meta, 0);
        super.onBlockHarvested(world, x, y, z, meta, player);
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> list = new ArrayList();
        if (world.getTileEntity(x, y, z) instanceof TileBloodTank) {
            TileBloodTank tile = (TileBloodTank) world.getTileEntity(x, y, z);
            ItemStack drop = new ItemStack(this, 1, metadata);
            NBTTagCompound tag = new NBTTagCompound();
            tile.writeToNBT(tag);
            drop.stackTagCompound = tag;
            list.add(drop);
        }
        return list;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack stack) {
        if (world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof TileBloodTank) {
            TileBloodTank tile = (TileBloodTank) world.getTileEntity(x, y, z);
            NBTTagCompound tag = stack.getTagCompound();
            if (tag != null) {
                tile.readFromNBT(tag);
            }
        }
    }

    @Override
    public int getRenderBlockPass() {
        return 1;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getRenderType() {
        return renderId;
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
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
    public void getSubBlocks(Item id, CreativeTabs tab, List list) {
        for (int i = 0; i <= 15; i++) {
            list.add(new ItemStack(id, 1, i));
        }
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
    }
}
