package tombenpotter.sanguimancy.api.tiles;

import net.minecraft.nbt.NBTTagCompound;

public class TileCamouflage extends TileBase {

    public int block = 0;
    public int metadata = 0;

    public TileCamouflage() {
        customNBTTag = new NBTTagCompound();
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        this.block = tagCompound.getInteger("blockID");
        this.metadata = tagCompound.getInteger("metadata");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("blockID", this.block);
        tagCompound.setInteger("metadata", this.metadata);
		return tagCompound;
    }
}