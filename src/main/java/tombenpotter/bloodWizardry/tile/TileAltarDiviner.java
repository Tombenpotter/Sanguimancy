package tombenpotter.bloodWizardry.tile;

import WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipe;
import WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipeRegistry;
import WayofTime.alchemicalWizardry.common.block.BlockAltar;
import WayofTime.alchemicalWizardry.common.tileEntity.TEAltar;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileAltarDiviner extends TileEntity implements IInventory {

    private ItemStack[] slots;

    public TileAltarDiviner() {
        slots = new ItemStack[1];
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
        return "Altar Diviner";
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
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer var1) {
        return true;
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
        super.markDirty(); // Mark dirty for gamesave
        if (worldObj.isRemote) {
            return;
        }
        this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord); // Update block + TE via Network
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            if (worldObj.getTileEntity(this.xCoord, this.yCoord + 1, this.zCoord) != null) {
                TEAltar tile = (TEAltar) worldObj.getTileEntity(this.xCoord, this.yCoord + 1, this.zCoord);
                if (worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord) instanceof BlockAltar) {
                    if (tile instanceof TEAltar) {
                        int tier = tile.getTier();
                        if (getStackInSlot(0) != null) {
                            ItemStack stack = getStackInSlot(0);
                            if (AltarRecipeRegistry.isRequiredItemValid(stack, tier)) {
                                int containedBlood = tile.getCurrentBlood();
                                AltarRecipe recipe = AltarRecipeRegistry.getAltarRecipeForItemAndTier(stack, tier);
                                int bloodRequired = recipe.liquidRequired;
                                if (bloodRequired * stack.stackSize <= containedBlood) {
                                    if (tile.getStackInSlot(0) == null) {
                                        tile.setInventorySlotContents(0, stack);
                                        setInventorySlotContents(0, null);
                                        tile.markDirty();
                                        worldObj.markBlockForUpdate(((TEAltar) tile).xCoord, ((TEAltar) tile).yCoord, ((TEAltar) tile).zCoord);
                                        worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
                                    } else if (tile.getStackInSlot(0).getItem() == stack.getItem() && tile.getStackInSlot(0).stackSize + stack.stackSize <= 64) {
                                        int s1 = tile.getStackInSlot(0).stackSize;
                                        tile.getStackInSlot(0).stackSize = s1 + stack.stackSize;
                                        setInventorySlotContents(0, null);
                                        worldObj.markBlockForUpdate(((TEAltar) tile).xCoord, ((TEAltar) tile).yCoord, ((TEAltar) tile).zCoord);
                                        worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
                                    }
                                    worldObj.markBlockForUpdate(this.xCoord, this.yCoord + 1, this.zCoord);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
