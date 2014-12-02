package tombenpotter.sanguimancy.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import tombenpotter.sanguimancy.util.BlockPostition;
import tombenpotter.sanguimancy.util.interfaces.ICustomNBTTag;
import tombenpotter.sanguimancy.util.interfaces.ISNBranch;
import tombenpotter.sanguimancy.util.interfaces.ISNKnot;

public abstract class TileBaseSNBranch extends TileEntity implements ISNBranch, ICustomNBTTag {

    @Override
    public BlockPostition getSNKnot() {
        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
            if (worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ) instanceof ISNKnot) {
                return new BlockPostition(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
            }
        }
        for (BlockPostition postition : getAdjacentBranches()) {
            if (postition != null) {
                ISNBranch branch = (ISNBranch) postition.getTile(worldObj);
                if (branch.hasSNKnot()) return branch.getSNKnot();
            }
        }
        return null;
    }

    @Override
    public boolean hasSNKnot() {
        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
            if (worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ) instanceof ISNKnot) {
                return true;
            }
        }
        for (BlockPostition postition : getAdjacentBranches()) {
            if (postition != null) {
                ISNBranch branch = (ISNBranch) postition.getTile(worldObj);
                if (branch.hasSNKnot()) return branch.hasSNKnot();
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
