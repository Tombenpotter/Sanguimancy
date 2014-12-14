package tombenpotter.sanguimancy.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.client.particle.EntityColoredFlameFX;
import tombenpotter.sanguimancy.tile.TileSimpleSNBranch;
import tombenpotter.sanguimancy.api.BlockPostition;
import tombenpotter.sanguimancy.api.soulNetworkManifestation.ISNComponent;

import java.util.Random;

public class BlockSimpleSNBranch extends BlockContainer {

    float pixel = 1F / 16F;

    public BlockSimpleSNBranch(Material material) {
        super(material);
        setHardness(5.0F);
        setCreativeTab(Sanguimancy.tabSanguimancy);
        setBlockBounds(0.25F, 0.25F, 0.25F, 0.75F, 0.75F, 0.75F);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileSimpleSNBranch();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister ri) {
        this.blockIcon = ri.registerIcon(Sanguimancy.texturePath + ":SimpleSoulBranch");
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {
        ISNComponent branch = (ISNComponent) world.getTileEntity(x, y, z);
        if (!branch.getComponentsInNetwork().hashMap.isEmpty()) {
            for (BlockPostition postition : branch.getComponentsInNetwork().hashMap.keySet()) {
                ISNComponent component = (ISNComponent) postition.getTile(world);
                component.onNetworkUpdate(new BlockPostition(x, y, z));
            }
        }
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        TileSimpleSNBranch tile = (TileSimpleSNBranch) world.getTileEntity(x, y, z);
        if (!tile.getSNKnots().isEmpty()) {
            EntityFX fire = new EntityColoredFlameFX(world, x + 0.5, y + 0.5, z + 0.5, 0, 0, 0, 255, 72, 0);
            Minecraft.getMinecraft().effectRenderer.addEffect(fire);
        }
    }
}