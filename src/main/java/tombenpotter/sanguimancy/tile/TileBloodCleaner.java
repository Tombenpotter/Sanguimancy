package tombenpotter.sanguimancy.tile;

import WayofTime.alchemicalWizardry.AlchemicalWizardry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;
import tombenpotter.sanguimancy.recipes.RecipeBloodCleanser;
import tombenpotter.sanguimancy.util.interfaces.ICustomNBTTag;

public class TileBloodCleaner extends TileEntity implements ISidedInventory, IFluidHandler, ICustomNBTTag {

    public ItemStack[] inventory;
    public int capacity;
    public int ticksLeft;
    public int maxTicks;
    public FluidTank tank;
    public boolean isActive;
    private NBTTagCompound custoomNBTTag;

    public TileBloodCleaner() {
        inventory = new ItemStack[2];
        capacity = FluidContainerRegistry.BUCKET_VOLUME * 16;
        maxTicks = 150;
        tank = new FluidTank(new FluidStack(AlchemicalWizardry.lifeEssenceFluid, 0), capacity);
        isActive = false;
        custoomNBTTag = new NBTTagCompound();
    }

    @Override
    public void updateEntity() {
        if (getStackInSlot(0) != null && canBloodClean()) {
            if (ticksLeft >= maxTicks) {
                bloodClean();
                ticksLeft = 0;
                worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            } else {
                ticksLeft++;
            }
            isActive = true;
        } else {
            ticksLeft = 0;
            isActive = false;
            worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        }
    }

    public void bloodClean() {
        if (canBloodClean()) {
            ItemStack ouput = RecipeBloodCleanser.getRecipe(getStackInSlot(0)).fOutput.copy();
            if (getStackInSlot(1) == null) {
                setInventorySlotContents(1, ouput);
            } else if (getStackInSlot(1).isItemEqual(RecipeBloodCleanser.getRecipe(getStackInSlot(0)).fOutput.copy())) {
                getStackInSlot(1).stackSize += ouput.stackSize;
            }
            decrStackSize(0, RecipeBloodCleanser.getRecipe(getStackInSlot(0)).fInput.stackSize);
            drain(ForgeDirection.UNKNOWN, FluidContainerRegistry.BUCKET_VOLUME, true);
        }
    }

    public boolean canBloodClean() {
        if (getStackInSlot(0) == null) {
            return false;
        } else {
            ItemStack input = getStackInSlot(0);
            if (!RecipeBloodCleanser.isRecipeValid(input)) {
                return false;
            }
            if (tank.getFluid() == null) {
                return false;
            }
            if (tank.getFluidAmount() < FluidContainerRegistry.BUCKET_VOLUME) {
                return false;
            }
            RecipeBloodCleanser recipe = RecipeBloodCleanser.getRecipe(input);
            ItemStack output = recipe.fOutput.copy();
            if (getStackInSlot(1) == null) {
                return true;
            }
            if (!getStackInSlot(1).isItemEqual(output)) {
                return false;
            }
            if (!(tank.getFluid().amount >= FluidContainerRegistry.BUCKET_VOLUME)) {
                return false;
            }
            int result = getStackInSlot(1).stackSize + output.stackSize;
            return result <= getInventoryStackLimit() && result <= getStackInSlot(1).getMaxStackSize();
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        ticksLeft = tagCompound.getInteger("ticksLeft");
        isActive = tagCompound.getBoolean("isActive");
        tank.readFromNBT(tagCompound);
        NBTTagList nbttaglist = tagCompound.getTagList("Items", 10);
        this.inventory = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");
            if (b0 >= 0 && b0 < this.inventory.length) {
                this.inventory[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
        custoomNBTTag = tagCompound.getCompoundTag("customNBTTag");
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("ticksLeft", ticksLeft);
        tagCompound.setBoolean("isActive", isActive);
        tank.writeToNBT(tagCompound);
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.inventory.length; ++i) {
            if (this.inventory[i] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                this.inventory[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        tagCompound.setTag("Items", nbttaglist);
        tagCompound.setTag("customNBTTag", custoomNBTTag);
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (resource.fluidID == AlchemicalWizardry.lifeEssenceFluid.getID()) {
            worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            return tank.fill(resource, doFill);
        }
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        return tank.drain(resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxEmpty, boolean doDrain) {
        worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        return tank.drain(maxEmpty, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return this.tank.getFluid().isFluidEqual(new FluidStack(AlchemicalWizardry.lifeEssenceFluid, 0));
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return true;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[]{tank.getInfo()};
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int var1) {
        ForgeDirection dir = ForgeDirection.getOrientation(var1);
        switch (dir) {
            case DOWN:
                return new int[]{0};
            case UP:
                return new int[]{6};
            case NORTH:
            case SOUTH:
            case EAST:
            case WEST:
                return new int[]{1, 2, 3, 4, 5};
            default:
                return new int[]{};
        }
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        if (slot == 0 && isItemValidForSlot(0, stack)) return true;
        else return false;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        if (slot == 1) return true;
        return false;
    }

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int par1, int par2) {
        if (inventory[par1] != null) {
            ItemStack itemstack;

            if (inventory[par1].stackSize <= par2) {
                itemstack = inventory[par1];
                inventory[par1] = null;
                return itemstack;
            } else {
                itemstack = inventory[par1].splitStack(par2);

                if (inventory[par1].stackSize == 0) {
                    inventory[par1] = null;
                }

                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int par1) {
        if (inventory[par1] != null) {
            ItemStack itemstack = inventory[par1];
            inventory[par1] = null;
            return itemstack;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
        inventory[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public String getInventoryName() {
        return "Lump Cleaner";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
        return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && entityPlayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        if (slot == 0) return true;
        else return false;
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
        this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
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
