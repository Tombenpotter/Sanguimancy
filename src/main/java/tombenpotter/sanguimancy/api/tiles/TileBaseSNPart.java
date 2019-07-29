package tombenpotter.sanguimancy.api.tiles;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.ForgeDirection;
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
            for (BlockPos pos : getAdjacentISNComponents()) {
                if (pos != null) {
                    if (!blockPosList.hashMap.containsKey(pos) && pos.getTile(world) != null && pos.getTile(world) instanceof ISNKnot) {
                        ISNKnot knot = (ISNKnot) pos.getTile(world);
                        blockPosList.hashMap.put(pos, new SNKNotBoolean(knot.isSNKnot(), knot.isSNKnotactive()));
                    } else if (!blockPosList.hashMap.containsKey(pos) && pos.getTile(world) != null && pos.getTile(world) instanceof ISNComponent) {
                        ISNComponent component = (ISNComponent) pos.getTile(world);
                        blockPosList.hashMap.put(pos, new SNKNotBoolean(component.isSNKnot(), false));
                        component.getAdjacentComponents(this.getPos(), blockPosList);
                    }
                }
            }
        }
        return blockPosList;
    }

    @Override
    public BoolAndBlockPosList getAdjacentComponents(BlockPos originalPosition, BoolAndBlockPosList blockPosList) {
        for (BlockPos pos : getAdjacentISNComponents()) {
            if (pos != null) {
                if (!blockPosList.hashMap.containsKey(pos) && pos.getTile(world) != null && pos.getTile(world) instanceof ISNKnot) {
                    ISNKnot knot = (ISNKnot) pos.getTile(world);
                    blockPosList.hashMap.put(pos, new SNKNotBoolean(knot.isSNKnot(), knot.isSNKnotactive()));
                } else if (!blockPosList.hashMap.containsKey(pos) && pos.getTile(world) != null && pos.getTile(world) instanceof ISNComponent) {
                    ISNComponent component = (ISNComponent) pos.getTile(world);
                    blockPosList.hashMap.put(pos, new SNKNotBoolean(component.isSNKnot(), false));
                    component.getAdjacentComponents(this.getPos(), blockPosList);
                }
            }
        }
        return blockPosList;
    }

    @Override
    public BlockPos[] getAdjacentISNComponents() {
        int i = 0;
        BlockPos[] adjacentBranches = new BlockPos[6];
        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
        	BlockPos curPos = this.getPos();
            if (world.getTileEntity(curPos.getX() + direction.offsetX, curPos.getY() + direction.offsetY, curPos.getZ() + direction.offsetZ) != null && world.getTileEntity(curPos.getX() + direction.offsetX, curPos.getY() + direction.offsetY, curPos.getZ() + direction.offsetZ) instanceof ISNComponent) {
                adjacentBranches[i] = new BlockPos(curPos.getX() + direction.offsetX, curPos.getY() + direction.offsetY, curPos.getZ() + direction.offsetZ);
            } else {
                adjacentBranches[i] = null;
            }
            i++;
        }
        return adjacentBranches;
    }
}