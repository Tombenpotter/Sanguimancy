package tombenpotter.sanguimancy.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.api.objects.BlockPostition;
import tombenpotter.sanguimancy.api.snManifestation.ISNComponent;
import tombenpotter.sanguimancy.tile.TileSimpleSNBranch;

public class BlockSimpleSNBranch extends BlockContainer {

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
}