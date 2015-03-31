package tombenpotter.sanguimancy.tile;

import WayofTime.alchemicalWizardry.common.block.BlockAltar;
import WayofTime.alchemicalWizardry.common.tileEntity.TEAltar;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import tombenpotter.sanguimancy.api.tile.TileBase;

public class TileAltarEmitter extends TileBase {

    public int bloodAsked;
    public boolean isOverBloodAsked;

    public TileAltarEmitter() {
        customNBTTag = new NBTTagCompound();
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
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("bloodAsked", bloodAsked);
        tagCompound.setBoolean("isBloodOverAsked", isOverBloodAsked);
    }
}
