package tombenpotter.sanguimancy.api.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import tombenpotter.sanguimancy.api.objects.ICustomNBTTag;

import javax.annotation.Nullable;

public class TileBase extends TileEntity implements ICustomNBTTag {

    public NBTTagCompound customNBTTag;
    public int cooldown;

    public TileBase() {
        customNBTTag = new NBTTagCompound();
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        customNBTTag = tagCompound.getCompoundTag("customNBTTag");
        cooldown = tagCompound.getInteger("cooldown");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setTag("customNBTTag", customNBTTag);
        tagCompound.setInteger("cooldown", cooldown);
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

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        SPacketUpdateTileEntity packet = new SPacketUpdateTileEntity(pos, 0, getUpdateTag());
        return packet;
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        NBTTagCompound nbt = pkt.getNbtCompound();
        readFromNBT(nbt);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound tag = super.getUpdateTag();
        readFromNBT(tag);
        return tag;
    }

    @Override
    public void markDirty() {
        super.markDirty(); // Mark dirty for gamesave
        if (worldObj.isRemote) {
            return;
        }
        this.worldObj.notifyBlockUpdate(pos, worldObj.getBlockState(pos), worldObj.getBlockState(pos), 3); // Update block + TE via Network
    }

    public boolean noCooldown() {
        if (cooldown > 0) {
            cooldown--;
            return false;
        }
        return true;
    }

    public void markForUpdate() {
        worldObj.notifyBlockUpdate(pos, worldObj.getBlockState(pos), worldObj.getBlockState(pos), 3);
    }
}
