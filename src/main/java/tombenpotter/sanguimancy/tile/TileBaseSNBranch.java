package tombenpotter.sanguimancy.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import tombenpotter.sanguimancy.util.BlockPostition;
import tombenpotter.sanguimancy.util.BoolAndBlockPosList;
import tombenpotter.sanguimancy.util.interfaces.ICustomNBTTag;
import tombenpotter.sanguimancy.util.interfaces.ISNBranch;
import tombenpotter.sanguimancy.util.interfaces.ISNComponent;
import tombenpotter.sanguimancy.util.interfaces.ISNKnot;

public abstract class TileBaseSNBranch extends TileEntity implements ISNBranch, ICustomNBTTag {

    @Override
    public BoolAndBlockPosList getAdjacentComponents(BoolAndBlockPosList blockPosList) {
        for (BlockPostition postition : getAdjacentSNComponents()) {
            if (postition != null) {
                if (postition.getTile(worldObj) instanceof ISNKnot && !blockPosList.hashMap.containsKey(postition)) {
                    blockPosList.hashMap.put(postition, true);
                    return blockPosList;
                } else if (postition.getTile(worldObj) instanceof ISNComponent && !blockPosList.hashMap.containsKey(postition)) {
                    blockPosList.hashMap.put(postition, false);
                    ISNComponent component = (ISNComponent) postition.getTile(worldObj);
                    component.getAdjacentComponents(blockPosList);
                    return blockPosList;
                }
            }
        }
        return blockPosList;
    }

    public BlockPostition[] getAdjacentSNComponents() {
        int i = 0;
        BlockPostition[] adjacentBranches = new BlockPostition[6];
        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
            if (worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ) instanceof ISNComponent) {
                adjacentBranches[i] = new BlockPostition(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
            } else {
                adjacentBranches[i] = null;
            }
            i++;
        }
        return adjacentBranches;
    }
}
