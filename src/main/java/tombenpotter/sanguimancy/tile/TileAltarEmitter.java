package tombenpotter.sanguimancy.tile;

import WayofTime.alchemicalWizardry.common.block.BlockAltar;
import WayofTime.alchemicalWizardry.common.tileEntity.TEAltar;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import tombenpotter.sanguimancy.util.interfaces.ICustomNBTTag;

public class TileAltarEmitter extends TileEntity implements ICustomNBTTag {

    public int bloodAsked;
    public boolean isOverBloodAsked;
    private NBTTagCompound custoomNBTTag;

    public TileAltarEmitter() {
        custoomNBTTag = new NBTTagCompound();
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            if (worldObj.getBlock(xCoord + 1, yCoord, zCoord + 1) != null && worldObj.getTileEntity(xCoord + 1, yCoord, zCoord + 1) != null && worldObj.getBlock(xCoord + 1, yCoord, zCoord + 1) instanceof BlockAltar && worldObj.getTileEntity(xCoord + 1, yCoord, zCoord + 1) instanceof TEAltar) {
                TEAltar tile = (TEAltar) worldObj.getTileEntity(xCoord + 1, yCoord, zCoord + 1);
                Block block = worldObj.getBlock(xCoord, yCoord, zCoord);
                int containedBlood = tile.getCurrentBlood();
                if (containedBlood >= bloodAsked && bloodAsked > 0) {
                    isOverBloodAsked = true;
                    worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, block);
                } else {
                    isOverBloodAsked = false;
                    worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, block);
                }
            } else if (worldObj.getBlock(xCoord - 1, yCoord, zCoord - 1) != null && worldObj.getTileEntity(xCoord - 1, yCoord, zCoord - 1) != null && worldObj.getBlock(xCoord - 1, yCoord, zCoord - 1) instanceof BlockAltar && worldObj.getTileEntity(xCoord - 1, yCoord, zCoord - 1) instanceof TEAltar) {
                TEAltar tile = (TEAltar) worldObj.getTileEntity(xCoord - 1, yCoord, zCoord - 1);
                Block block = worldObj.getBlock(xCoord, yCoord, zCoord);
                int containedBlood = tile.getCurrentBlood();
                if (containedBlood >= bloodAsked && bloodAsked > 0) {
                    isOverBloodAsked = true;
                    worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, block);
                } else {
                    isOverBloodAsked = false;
                    worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, block);
                }
            } else if (worldObj.getBlock(xCoord + 1, yCoord, zCoord - 1) != null && worldObj.getTileEntity(xCoord + 1, yCoord, zCoord - 1) != null && worldObj.getBlock(xCoord + 1, yCoord, zCoord - 1) instanceof BlockAltar && worldObj.getTileEntity(xCoord + 1, yCoord, zCoord - 1) instanceof TEAltar) {
                TEAltar tile = (TEAltar) worldObj.getTileEntity(xCoord + 1, yCoord, zCoord - 1);
                Block block = worldObj.getBlock(xCoord, yCoord, zCoord);
                int containedBlood = tile.getCurrentBlood();
                if (containedBlood >= bloodAsked && bloodAsked > 0) {
                    isOverBloodAsked = true;
                    worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, block);
                } else {
                    isOverBloodAsked = false;
                    worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, block);
                }
            } else if (worldObj.getBlock(xCoord - 1, yCoord, zCoord + 1) != null && worldObj.getTileEntity(xCoord - 1, yCoord, zCoord + 1) != null && worldObj.getBlock(xCoord - 1, yCoord, zCoord + 1) instanceof BlockAltar && worldObj.getTileEntity(xCoord - 1, yCoord, zCoord + 1) instanceof TEAltar) {
                TEAltar tile = (TEAltar) worldObj.getTileEntity(xCoord - 1, yCoord, zCoord + 1);
                Block block = worldObj.getBlock(xCoord, yCoord, zCoord);
                int containedBlood = tile.getCurrentBlood();
                if (containedBlood >= bloodAsked && bloodAsked > 0) {
                    isOverBloodAsked = true;
                    worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, block);
                } else {
                    isOverBloodAsked = false;
                    worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, block);
                }
            }
        }

    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        bloodAsked = tagCompound.getInteger("bloodAsked");
        isOverBloodAsked = tagCompound.getBoolean("isBloodOverAsked");
        custoomNBTTag = tagCompound.getCompoundTag("customNBTTag");
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("bloodAsked", bloodAsked);
        tagCompound.setBoolean("isBloodOverAsked", isOverBloodAsked);
        tagCompound.setTag("customNBTTag", custoomNBTTag);
    }


    @Override
    public final Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        writeToNBT(nbt);
        S35PacketUpdateTileEntity packet = new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbt);
        return packet;
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        NBTTagCompound nbt = pkt.func_148857_g();
        readFromNBT(nbt);
    }

    @Override
    public void markDirty() {
        super.markDirty(); // Mark dirty for gamesave
        if (worldObj.isRemote) {
            return;
        }
        this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord); // Update block + TE via Network
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
