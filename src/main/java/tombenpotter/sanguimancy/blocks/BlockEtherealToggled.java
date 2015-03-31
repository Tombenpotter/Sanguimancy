package tombenpotter.sanguimancy.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.api.block.BlockCamouflage;

import java.util.List;

public class BlockEtherealToggled extends BlockCamouflage {

    public BlockEtherealToggled(Material material) {
        super(material);
        setHardness(5.0F);
        setCreativeTab(Sanguimancy.tabSanguimancy);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister ir) {
        this.blockIcon = ir.registerIcon(Sanguimancy.texturePath + ":EtherealToggledBlock");
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List list, Entity entity) {
        if (world.getStrongestIndirectPower(x, y, z) > 0 || world.getBlockPowerInput(x, y, z) > 0 || entity.isSneaking()) {
            super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
        } else {
            return;
        }
    }

    @Override
    public boolean getBlocksMovement(IBlockAccess access, int x, int y, int z) {
        return false;
    }

    @Override
    public boolean isNormalCube() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getMobilityFlag() {
        return 2;
    }

    @Override
    public boolean shouldCheckWeakPower(IBlockAccess world, int x, int y, int z, int side) {
        return true;
    }
}
