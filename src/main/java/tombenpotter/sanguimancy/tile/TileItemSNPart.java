package tombenpotter.sanguimancy.tile;

import WayofTime.alchemicalWizardry.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import tombenpotter.sanguimancy.util.BlockPostition;
import tombenpotter.sanguimancy.util.BoundItemState;
import tombenpotter.sanguimancy.util.enums.EnumSNType;
import tombenpotter.sanguimancy.util.interfaces.ISNKnot;
import tombenpotter.sanguimancy.util.singletons.BoundItems;

public class TileItemSNPart extends TileBaseSNPart implements IInventory {

    public ItemStack[] slots;
    private NBTTagCompound custoomNBTTag;

    public TileItemSNPart() {
        slots = new ItemStack[1];
        custoomNBTTag = new NBTTagCompound();
    }

    public int getSizeInventory() {
        return this.slots.length;
    }

    public ItemStack getStackInSlot(int par1) {
        return this.slots[par1];
    }

    public ItemStack decrStackSize(int par1, int par2) {
        if (this.slots[par1] != null) {
            ItemStack itemstack;

            if (this.slots[par1].stackSize <= par2) {
                itemstack = this.slots[par1];
                this.slots[par1] = null;
                return itemstack;
            } else {
                itemstack = this.slots[par1].splitStack(par2);

                if (this.slots[par1].stackSize == 0) {
                    this.slots[par1] = null;
                }

                return itemstack;
            }
        } else {
            return null;
        }
    }

    public ItemStack getStackInSlotOnClosing(int par1) {
        if (this.slots[par1] != null) {
            ItemStack itemstack = this.slots[par1];
            this.slots[par1] = null;
            return itemstack;
        } else {
            return null;
        }
    }

    public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
        this.slots[par1] = par2ItemStack;
        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public String getInventoryName() {
        return "Bound Item";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        NBTTagList nbttaglist = tagCompound.getTagList("Items", 10);
        this.slots = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.slots.length) {
                this.slots[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
        custoomNBTTag = tagCompound.getCompoundTag("customNBTTag");
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.slots.length; ++i) {
            if (this.slots[i] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                this.slots[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        tagCompound.setTag("Items", nbttaglist);
        tagCompound.setTag("customNBTTag", custoomNBTTag);
    }

    public boolean onBlockRightClicked(EntityPlayer player, ItemStack stack) {
        if (stack != null && getStackInSlot(0) != null) {
            if (stack.isItemEqual(new ItemStack(ModItems.divinationSigil)) || stack.isItemEqual(new ItemStack(ModItems.itemSeerSigil))) {
                if (!worldObj.isRemote) {
                    player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("chat.Sanguimancy.bound.item") + ": " + getStackInSlot(0).getDisplayName()));
                }
                return true;
            }
        }
        return false;
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        return false;
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack var2) {
        return slot == 0;
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
        super.markDirty();
        if (worldObj.isRemote) return;
        this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    @Override
    public EnumSNType getType() {
        return EnumSNType.ITEM;
    }

    @Override
    public boolean isSNKnot() {
        return false;
    }

    @Override
    public void onNetworkUpdate(BlockPostition originalPosition) {
        if (!getSNKnots().isEmpty()) {
            for (BlockPostition postition : getSNKnots()) {
                ISNKnot knot = (ISNKnot) postition.getTile(worldObj);
                BoundItems.getBoundItems().removeItem(getCustomNBTTag().getString("SavedItemName"));
                BoundItems.getBoundItems().addItem(getCustomNBTTag().getString("SavedItemName"), new BoundItemState(xCoord, yCoord, zCoord, worldObj.provider.dimensionId, knot.isSNKnotactive()));
            }
        } else {
            BoundItems.getBoundItems().removeItem(getCustomNBTTag().getString("SavedItemName"));
            BoundItems.getBoundItems().addItem(getCustomNBTTag().getString("SavedItemName"), new BoundItemState(xCoord, yCoord, zCoord, worldObj.provider.dimensionId, false));
        }
    }

    public void disablePart(Boolean bool) {
        BoundItems.getBoundItems().removeItem(getCustomNBTTag().getString("SavedItemName"));
        BoundItems.getBoundItems().addItem(getCustomNBTTag().getString("SavedItemName"), new BoundItemState(xCoord, yCoord, zCoord, worldObj.provider.dimensionId, bool));
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
