package tombenpotter.sanguimancy.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.tile.TilePart;

public class BlockTestPart extends BlockContainer {

    public BlockTestPart(Material p_i45386_1_) {
        super(p_i45386_1_);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TilePart();
    }
}
