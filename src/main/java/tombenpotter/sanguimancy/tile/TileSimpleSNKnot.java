package tombenpotter.sanguimancy.tile;

import net.minecraft.nbt.NBTTagCompound;

public class TileSimpleSNKnot extends TileBaseSNKnot {

    public String knotOwner;
    private NBTTagCompound custoomNBTTag;
    public boolean knotActive = true;

    public TileSimpleSNKnot() {
        custoomNBTTag = new NBTTagCompound();
    }

    @Override
    public boolean isSNKnotactive() {
        return knotActive;
    }

    @Override
    public void setKnotActive(boolean isActive) {
        this.knotActive = isActive;
    }

    @Override
    public String getSNKnotOwner() {
        return knotOwner;
    }

    @Override
    public void setSNKnotOwner(String owner) {
        this.knotOwner = owner;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        knotOwner = tagCompound.getString("knotOwner");
        knotActive = tagCompound.getBoolean("knotActive");
        custoomNBTTag = tagCompound.getCompoundTag("customNBTTag");
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setString("knotOwner", knotOwner);
        tagCompound.setBoolean("knotActive", knotActive);
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

    @Override
    public boolean isSNKnot() {
        return true;
    }
}
