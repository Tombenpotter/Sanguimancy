package tombenpotter.oldsanguimancy.old.ded;

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
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setBoolean("isConsideredKnot", isConsideredKnot);
    }

    @Override
    public void updateEntity() {
        if (worldObj.getBlockPowerInput(xCoord, yCoord, zCoord) > 0) {
            isConsideredKnot = false;
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        } else {
            isConsideredKnot = true;
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }
}
