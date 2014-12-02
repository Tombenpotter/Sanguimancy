package tombenpotter.sanguimancy.tile;

import net.minecraft.nbt.NBTTagCompound;

public class TileBranch extends TileBaseSNBranch {

    private NBTTagCompound custoomNBTTag;

    public TileBranch() {
        custoomNBTTag = new NBTTagCompound();
    }

    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        custoomNBTTag = tagCompound.getCompoundTag("customNBTTag");
    }

    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setTag("customNBTTag", custoomNBTTag);
    }

    @Override
    public NBTTagCompound getCustomNBTTag() {
        return custoomNBTTag;
    }

    @Override
    public void setCustomNBTTag(NBTTagCompound tag) {
        custoomNBTTag = tag;
    }
}
