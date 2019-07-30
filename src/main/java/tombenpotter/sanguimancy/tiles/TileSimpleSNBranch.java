package tombenpotter.sanguimancy.tiles;

import net.minecraft.nbt.NBTTagCompound;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.api.tile.TileBaseSNBranch;

public class TileSimpleSNBranch extends TileBaseSNBranch {

    private NBTTagCompound customNBTTag;

    public TileSimpleSNBranch() {
        customNBTTag = new NBTTagCompound();
    }

    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        customNBTTag = tagCompound.getCompoundTag("customNBTTag");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setTag("customNBTTag", customNBTTag);
        return tagCompound;
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
    public void onNetworkUpdate(BlockPos originalPosition) {
        Sanguimancy.proxy.addLinkingEffects(this, world, xCoord, yCoord, zCoord);
    }

    @Override
    public void update() {
        if (world.getWorldTime() % 100 == 0) {
            Sanguimancy.proxy.addLinkingEffects(this, world, xCoord, yCoord, zCoord);
        }
    }
}