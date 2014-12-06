package tombenpotter.sanguimancy.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import tombenpotter.sanguimancy.util.BlockPostition;
import tombenpotter.sanguimancy.util.BoolAndBlockPosList;
import tombenpotter.sanguimancy.util.interfaces.ICustomNBTTag;
import tombenpotter.sanguimancy.util.interfaces.ISNComponent;
import tombenpotter.sanguimancy.util.interfaces.ISNKnot;

public abstract class TileBaseSNKnot extends TileEntity implements ISNKnot, ICustomNBTTag {

    @Override
    public BoolAndBlockPosList getComponentsInNetwork() {
        BoolAndBlockPosList blockPosList = new BoolAndBlockPosList();
        for (BlockPostition postition : getAdjacentISNComponents()) {
            if (postition != null) {
                if (!blockPosList.hashMap.containsKey(postition) && postition.getTile(worldObj) != null && postition.getTile(worldObj) instanceof ISNKnot) {
                    ISNKnot knot = (ISNKnot) postition.getTile(worldObj);
                    if (knot.isSNKnotactive()) blockPosList.hashMap.put(postition, knot.isSNKnot());
                } else if (!blockPosList.hashMap.containsKey(postition) && postition.getTile(worldObj) != null && postition.getTile(worldObj) instanceof ISNComponent) {
                    ISNComponent component = (ISNComponent) postition.getTile(worldObj);
                    blockPosList.hashMap.put(postition, component.isSNKnot());
                    component.getAdjacentComponents(new BlockPostition(this.xCoord, this.yCoord, this.zCoord), blockPosList);
                }
            }
        }
        return blockPosList;
    }

    @Override
    public BoolAndBlockPosList getAdjacentComponents(BlockPostition originalPosition, BoolAndBlockPosList blockPosList) {
        for (BlockPostition postition : getAdjacentISNComponents()) {
            if (postition != null) {
                if (!blockPosList.hashMap.containsKey(postition) && postition.getTile(worldObj) != null && postition.getTile(worldObj) instanceof ISNKnot) {
                    ISNKnot knot = (ISNKnot) postition.getTile(worldObj);
                    if (knot.isSNKnotactive()) blockPosList.hashMap.put(postition, knot.isSNKnot());
                } else if (!blockPosList.hashMap.containsKey(postition) && postition.getTile(worldObj) != null && postition.getTile(worldObj) instanceof ISNComponent) {
                    ISNComponent component = (ISNComponent) postition.getTile(worldObj);
                    blockPosList.hashMap.put(postition, component.isSNKnot());
                    component.getAdjacentComponents(new BlockPostition(this.xCoord, this.yCoord, this.zCoord), blockPosList);
                }
            }
        }
        return blockPosList;
    }

    @Override
    public BlockPostition[] getAdjacentISNComponents() {
        int i = 0;
        BlockPostition[] adjacentBranches = new BlockPostition[6];
        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
            if (worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ) != null && worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ) instanceof ISNComponent) {
                adjacentBranches[i] = new BlockPostition(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
            } else {
                adjacentBranches[i] = null;
            }
            i++;
        }
        return adjacentBranches;
    }
}
