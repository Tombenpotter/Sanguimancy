package tombenpotter.sanguimancy.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.tile.TileAltarManipulator;
import tombenpotter.sanguimancy.util.RandomUtils;

public class BlockAltarManipulator extends BlockContainer {

    public BlockAltarManipulator(Material material) {
        super(material);
        setHardness(5.0F);
        setCreativeTab(Sanguimancy.tabSanguimancy);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileAltarManipulator();
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block par5, int par6) {
        RandomUtils.dropItems(world, x, y, z);
        world.removeTileEntity(x, y, z);
        super.breakBlock(world, x, y, z, par5, par6);
    }
}
