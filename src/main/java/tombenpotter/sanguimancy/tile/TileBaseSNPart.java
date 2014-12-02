package tombenpotter.sanguimancy.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import tombenpotter.sanguimancy.util.BlockPostition;
import tombenpotter.sanguimancy.util.BoolAndBlockPosList;
import tombenpotter.sanguimancy.util.interfaces.ICustomNBTTag;
import tombenpotter.sanguimancy.util.interfaces.ISNComponent;
import tombenpotter.sanguimancy.util.interfaces.ISNKnot;
import tombenpotter.sanguimancy.util.interfaces.ISNPart;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class TileBaseSNPart extends TileEntity implements ISNPart, ICustomNBTTag {

    @Override
    public BoolAndBlockPosList getComponentsInNetwork() {
        BoolAndBlockPosList blockPosList = new BoolAndBlockPosList();
        for (BlockPostition postition : getAdjacentComponents()) {
            if (postition != null) {
                if (postition.getTile(worldObj) instanceof ISNKnot && !blockPosList.hashMap.containsKey(postition)) {
                    blockPosList.hashMap.put(postition, true);
                    return blockPosList;
                } else if (postition.getTile(worldObj) instanceof ISNComponent && !blockPosList.hashMap.containsKey(postition)) {
                    ISNComponent component = (ISNComponent) postition.getTile(worldObj);
                    blockPosList.hashMap.putAll(component.getAdjacentComponents().hashMap);
                }
            }
        }
        return blockPosList;
    }

    @Override
    public ArrayList<BlockPostition> getSNKnots() {
        ArrayList<BlockPostition> list = new ArrayList<BlockPostition>();
        HashMap<BlockPostition, Boolean> map = getComponentsInNetwork().hashMap;
        for (BlockPostition postition : map.keySet()) {
            if (map.get(postition)) list.add(postition);
        }
        return list;
    }

    @Override
    public BlockPostition[] getAdjacentComponents() {
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
