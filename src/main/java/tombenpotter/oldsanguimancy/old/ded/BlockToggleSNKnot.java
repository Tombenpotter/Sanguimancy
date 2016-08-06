package tombenpotter.oldsanguimancy.old.ded;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tombenpotter.oldsanguimancy.Sanguimancy;

public class BlockToggleSNKnot extends BlockSimpleSNKnot {

    public BlockToggleSNKnot(Material material) {
        super(material);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileToggleSNKnot();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister ri) {
        this.blockIcon = ri.registerIcon(Sanguimancy.texturePath + ":ToggleSNKnot");
    }
}
