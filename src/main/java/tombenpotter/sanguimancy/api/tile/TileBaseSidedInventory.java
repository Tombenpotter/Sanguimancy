package tombenpotter.sanguimancy.api.tile;

import net.minecraft.inventory.ISidedInventory;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileBaseSidedInventory extends TileBaseInventory implements ISidedInventory {

    @Override
    public int[] getAccessibleSlotsFromSide(int var1) {
        ForgeDirection dir = ForgeDirection.getOrientation(var1);
        switch (dir) {
            case DOWN:
                return new int[]{0};
            case UP:
                return new int[]{6};
            case NORTH:
            case SOUTH:
            case EAST:
            case WEST:
                return new int[]{1, 2, 3, 4, 5};
            default:
                return new int[]{};
        }
    }
}
