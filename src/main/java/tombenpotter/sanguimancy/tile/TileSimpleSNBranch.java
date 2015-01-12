package tombenpotter.sanguimancy.tile;

import net.minecraft.nbt.NBTTagCompound;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.api.objects.BlockPostition;
import tombenpotter.sanguimancy.api.tile.TileBaseSNBranch;

public class TileSimpleSNBranch extends TileBaseSNBranch {

    private NBTTagCompound custoomNBTTag;

    public TileSimpleSNBranch() {
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

    @Override
    public boolean isSNKnot() {
        return false;
    }

    @Override
    public void onNetworkUpdate(BlockPostition originalPosition) {
        Sanguimancy.proxy.addLinkingEffects(this, worldObj, xCoord, yCoord, zCoord);
    }

    @Override
    public void updateEntity() {
        if (worldObj.getWorldTime() % 100 == 0) {
            Sanguimancy.proxy.addLinkingEffects(this, worldObj, xCoord, yCoord, zCoord);
        }
    }
}
