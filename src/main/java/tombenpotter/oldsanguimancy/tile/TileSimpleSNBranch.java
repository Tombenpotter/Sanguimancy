package tombenpotter.oldsanguimancy.tile;

import net.minecraft.nbt.NBTTagCompound;
import tombenpotter.oldsanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.api.objects.BlockPostition;
import tombenpotter.oldsanguimancy.api.tile.TileBaseSNBranch;

public class TileSimpleSNBranch extends TileBaseSNBranch {

    private NBTTagCompound customNBTTag;

    public TileSimpleSNBranch() {
        customNBTTag = new NBTTagCompound();
    }

    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        customNBTTag = tagCompound.getCompoundTag("customNBTTag");
    }

    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setTag("customNBTTag", customNBTTag);
    }

    @Override
    public NBTTagCompound getCustomNBTTag() {
        return customNBTTag;
    }

    @Override
    public void setCustomNBTTag(NBTTagCompound tag) {
        customNBTTag = tag;
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
