package tombenpotter.sanguimancy.tile;

import net.minecraft.nbt.NBTTagCompound;

public class TileKnot extends TileBaseSNKnot {

    public String knotOwner;
    private NBTTagCompound custoomNBTTag;

    public TileKnot() {
        custoomNBTTag = new NBTTagCompound();
    }

    @Override
    public String getSNKnotOwner() {
        return knotOwner;
    }

    @Override
    public void setSNKnotOwner(String owner) {
        this.knotOwner = owner;
    }

    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        knotOwner = tagCompound.getString("knotOwner");
        custoomNBTTag = tagCompound.getCompoundTag("customNBTTag");
    }

    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setString("knotOwner", knotOwner);
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
