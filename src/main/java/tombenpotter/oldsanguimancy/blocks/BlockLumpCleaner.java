package tombenpotter.oldsanguimancy.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import tombenpotter.oldsanguimancy.Sanguimancy;
import tombenpotter.oldsanguimancy.tiles.TileBloodCleaner;
import tombenpotter.sanguimancy.util.RandomUtils;

public class BlockLumpCleaner extends BlockContainer {

    public IIcon frontOffIcon, frontOnIcon, bottomIcon, topOffIcon, topOnIcon;

    public BlockLumpCleaner(Material material) {
        super(material);
        setHardness(5.0F);
        setCreativeTab(Sanguimancy.tabSanguimancy);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister ir) {
        this.blockIcon = ir.registerIcon(Sanguimancy.texturePath + ":LumpCleaner");
        this.frontOffIcon = ir.registerIcon(Sanguimancy.texturePath + ":LumpCleaner_Inactive");
        this.frontOnIcon = ir.registerIcon(Sanguimancy.texturePath + ":LumpCleaner_Active");
        this.bottomIcon = ir.registerIcon(Sanguimancy.texturePath + ":LumpCleaner_Bottom");
        this.topOffIcon = ir.registerIcon(Sanguimancy.texturePath + ":LumpCleaner_Top_Off");
        this.topOnIcon = ir.registerIcon(Sanguimancy.texturePath + ":LumpCleaner_Top_On");
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileBloodCleaner();
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block par5, int par6) {
        RandomUtils.dropItems(world, x, y, z);
        world.removeTileEntity(x, y, z);
        super.breakBlock(world, x, y, z, par5, par6);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        TileBloodCleaner fluidHandler = (TileBloodCleaner) world.getTileEntity(x, y, z);
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
        player.openGui(Sanguimancy.instance, 0, world, x, y, z);
        return true;
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        int l = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        if (l == 0) {
            world.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }
        if (l == 1) {
            world.setBlockMetadataWithNotify(x, y, z, 5, 2);
        }
        if (l == 2) {
            world.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }
        if (l == 3) {
            world.setBlockMetadataWithNotify(x, y, z, 4, 2);
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side) {
        int meta = access.getBlockMetadata(x, y, z);
        if (access.getTileEntity(x, y, z) != null && access.getTileEntity(x, y, z) instanceof TileBloodCleaner) {
            TileBloodCleaner tile = (TileBloodCleaner) access.getTileEntity(x, y, z);
            if (tile.isActive) {
                return side == 1 ? this.topOnIcon : (side == 0 ? this.bottomIcon : (side != meta ? this.blockIcon : this.frontOnIcon));
            } else {
                return side == 1 ? this.topOffIcon : (side == 0 ? this.bottomIcon : (side != meta ? this.blockIcon : this.frontOffIcon));
            }
        }
        return super.getIcon(access, x, y, z, side);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (side < 1) {
            return this.bottomIcon;
        }
        if (side == 1) {
            return this.topOffIcon;
        }
        if (side == 3) {
            return this.frontOffIcon;
        }
        return this.blockIcon;
    }
}
