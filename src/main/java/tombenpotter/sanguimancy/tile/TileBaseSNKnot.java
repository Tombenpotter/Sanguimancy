package tombenpotter.sanguimancy.tile;

import net.minecraft.tileentity.TileEntity;
import tombenpotter.sanguimancy.util.BoolAndBlockPosList;
import tombenpotter.sanguimancy.util.interfaces.ICustomNBTTag;
import tombenpotter.sanguimancy.util.interfaces.ISNKnot;

public abstract class TileBaseSNKnot extends TileEntity implements ISNKnot, ICustomNBTTag {

    @Override
    public boolean isSNKnotactive() {
        if (!worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
            return true;
        } else if (worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
            return false;
        }
        return true;
    }

    @Override
    public BoolAndBlockPosList getAdjacentComponents(BoolAndBlockPosList blockPosList) {
        return null;
    }
}
