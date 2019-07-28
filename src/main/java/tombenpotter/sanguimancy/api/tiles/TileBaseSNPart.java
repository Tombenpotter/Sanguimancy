package tombenpotter.sanguimancy.api.tiles;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.ForgeDirection;
import tombenpotter.sanguimancy.api.objects.BlockPostition;
import tombenpotter.sanguimancy.api.objects.BoolAndBlockPosList;
import tombenpotter.sanguimancy.api.objects.ICustomNBTTag;
import tombenpotter.sanguimancy.api.objects.SNKNotBoolean;
import tombenpotter.sanguimancy.api.snManifestation.ISNComponent;
import tombenpotter.sanguimancy.api.snManifestation.ISNKnot;
import tombenpotter.sanguimancy.api.snManifestation.ISNPart;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class TileBaseSNPart extends TileEntity implements ISNPart, ICustomNBTTag {

    @Override
    public ArrayList<BlockPos> getSNKnots() {
        ArrayList<BlockPos> list = new ArrayList<BlockPos>();
        HashMap<BlockPos, SNKNotBoolean> map = getComponentsInNetwork().hashMap;
        for (BlockPos postition : map.keySet()) {
            if (map.get(postition).isSNKnotActive && map.get(postition).isSNKnot) list.add(postition);
        }
        return list;
    }

    @Override
    public BoolAndBlockPosList getComponentsInNetwork() {
        BoolAndBlockPosList blockPosList = new BoolAndBlockPosList();
        if (getAdjacentISNComponents() != null) {
            for (BlockPos postition : getAdjacentISNComponents()) {
                if (postition != null) {
                    if (!blockPosList.hashMap.containsKey(postition) && postition.getTile(world) != null && postition.getTile(world) instanceof ISNKnot) {
                        ISNKnot knot = (ISNKnot) postition.getTile(world);
                        blockPosList.hashMap.put(postition, new SNKNotBoolean(knot.isSNKnot(), knot.isSNKnotactive()));
                    } else if (!blockPosList.hashMap.containsKey(postition) && postition.getTile(world) != null && postition.getTile(world) instanceof ISNComponent) {
                        ISNComponent component = (ISNComponent) postition.getTile(world);
                        blockPosList.hashMap.put(postition, new SNKNotBoolean(component.isSNKnot(), false));
                        component.getAdjacentComponents(new BlockPos(this.xCoord, this.yCoord, this.zCoord), blockPosList);
                    }
                }
            }
        }
        return blockPosList;
    }

    @Override
    public BoolAndBlockPosList getAdjacentComponents(BlockPostition originalPosition, BoolAndBlockPosList blockPosList) {
        for (BlockPos postition : getAdjacentISNComponents()) {
            if (postition != null) {
                if (!blockPosList.hashMap.containsKey(postition) && postition.getTile(world) != null && postition.getTile(world) instanceof ISNKnot) {
                    ISNKnot knot = (ISNKnot) postition.getTile(world);
                    blockPosList.hashMap.put(postition, new SNKNotBoolean(knot.isSNKnot(), knot.isSNKnotactive()));
                } else if (!blockPosList.hashMap.containsKey(postition) && postition.getTile(world) != null && postition.getTile(world) instanceof ISNComponent) {
                    ISNComponent component = (ISNComponent) postition.getTile(world);
                    blockPosList.hashMap.put(postition, new SNKNotBoolean(component.isSNKnot(), false));
                    component.getAdjacentComponents(new BlockPos(this.xCoord, this.yCoord, this.zCoord), blockPosList);
                }
            }
        }
        return blockPosList;
    }

    @Override
    public BlockPos[] getAdjacentISNComponents() {
        int i = 0;
        BlockPos[] adjacentBranches = new BlockPostition[6];
        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
            if (world.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ) != null && world.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ) instanceof ISNComponent) {
                adjacentBranches[i] = new BlockPos(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
            } else {
                adjacentBranches[i] = null;
            }
            i++;
        }
        return adjacentBranches;
    }
}