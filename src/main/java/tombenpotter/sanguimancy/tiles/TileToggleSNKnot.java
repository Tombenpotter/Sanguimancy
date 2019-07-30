package tombenpotter.sanguimancy.tiles;

import net.minecraft.nbt.NBTTagCompound;

public class TileToggleSNKnot extends TileSimpleSNKnot {

    public boolean isConsideredKnot = true;

    @Override
    public boolean isSNKnot() {
        return isConsideredKnot;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        isConsideredKnot = tagCompound.getBoolean("isConsideredKnot");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setBoolean("isConsideredKnot", isConsideredKnot);
        return tagCompound;
    }

    @Override
    public void update() {
        if (world.getBlockPowerInput(xCoord, yCoord, zCoord) > 0) {
            isConsideredKnot = false;
            world.markBlockForUpdate(xCoord, yCoord, zCoord);
        } else {
            isConsideredKnot = true;
            world.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }
}