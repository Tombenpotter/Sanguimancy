package tombenpotter.sanguimancy.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.tile.TilePart;
import tombenpotter.sanguimancy.util.BlockPostition;
import tombenpotter.sanguimancy.util.interfaces.ISNPart;

public class BlockTestPart extends BlockContainer {

    public BlockTestPart(Material p_i45386_1_) {
        super(p_i45386_1_);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TilePart();
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {
        if (!world.isRemote) {
            ISNPart tile = (ISNPart) world.getTileEntity(x, y, z);
            if (!world.isBlockIndirectlyGettingPowered(x, y, z)) {
                world.scheduleBlockUpdate(x, y, z, this, 4);
            } else if (world.isBlockIndirectlyGettingPowered(x, y, z)) {
                for (BlockPostition postition : tile.getSNKnots()) {
                    if (postition != null) {
                        if (world.isAirBlock(postition.x, postition.y + 1, postition.z)) {
                            world.setBlock(postition.x, postition.y + 1, postition.z, Blocks.diamond_block);
                        }
                    }
                }
            }
        }
    }
}
