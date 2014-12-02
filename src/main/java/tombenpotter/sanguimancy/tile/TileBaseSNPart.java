package tombenpotter.sanguimancy.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import tombenpotter.sanguimancy.util.BlockPostition;
import tombenpotter.sanguimancy.util.interfaces.ICustomNBTTag;
import tombenpotter.sanguimancy.util.interfaces.ISNBranch;
import tombenpotter.sanguimancy.util.interfaces.ISNPart;

public abstract class TileBaseSNPart extends TileEntity implements ISNPart, ICustomNBTTag {

    @Override
    public BlockPostition getSNKnot() {
        for (BlockPostition postition : getAdjacentBranches()) {
            if (postition != null) {
                ISNBranch branch = (ISNBranch) postition.getTile(worldObj);
                return branch.getSNKnot();
            }
        }
        return null;
    }

    @Override
    public boolean hasSNKnot() {
        for (BlockPostition postition : getAdjacentBranches()) {
            if (postition != null) {
                ISNBranch branch = (ISNBranch) postition.getTile(worldObj);
                return branch.hasSNKnot();
            }
        }
        return false;
    }

    @Override
    public BlockPostition[] getAdjacentBranches() {
        int i = 0;
        BlockPostition[] adjacentBranches = new BlockPostition[6];
        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
            if (worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ) instanceof ISNBranch) {
                adjacentBranches[i] = new BlockPostition(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
            } else {
                adjacentBranches[i] = null;
            }
            i++;
        }
        return adjacentBranches;
    }
}
