package tombenpotter.sanguimancy.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.tile.TileSimpleSNKnot;
import tombenpotter.sanguimancy.util.interfaces.ISNComponent;

public class BlockKnotActivator extends Block {

    public BlockKnotActivator(Material material) {
        super(material);
        setHardness(5.0F);
        setCreativeTab(Sanguimancy.tabSanguimancy);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister ir) {
        this.blockIcon = ir.registerIcon(Sanguimancy.texturePath + ":SNKnotActivator");
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {
        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
            if (world.getTileEntity(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ) != null && world.getTileEntity(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ) instanceof TileSimpleSNKnot) {
                TileSimpleSNKnot knot = (TileSimpleSNKnot) world.getTileEntity(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ);
                if (world.getBlockPowerInput(x, y, z) > 0) knot.setKnotActive(false);
                else knot.setKnotActive(true);
                ISNComponent component = (ISNComponent) world.getTileEntity(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ);
                component.onNetworkUpdate();
                world.markBlockForUpdate(x, y, z);
            }
        }
    }
}
