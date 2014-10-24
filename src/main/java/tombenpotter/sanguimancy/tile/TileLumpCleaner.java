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
import net.minecraftforge.oredict.OreDictionary;
import tombenpotter.sanguimancy.registry.ItemsRegistry;
import tombenpotter.sanguimancy.util.RandomUtils;

public class TileLumpCleaner extends TileEntity implements ISidedInventory, IFluidTank, IFluidHandler {

    public ItemStack[] inventory;
    public FluidStack fluid;
    public FluidStack fluidInput;
    public FluidStack fluidOutput;
    public int capacity;
    public int buffer;
    public int ticksLeft;
    public int maxTicks;

    public TileLumpCleaner() {
        inventory = new ItemStack[2];
        fluid = new FluidStack(AlchemicalWizardry.lifeEssenceFluid, 0);
        fluidInput = new FluidStack(AlchemicalWizardry.lifeEssenceFluid, 0);
        fluidOutput = new FluidStack(AlchemicalWizardry.lifeEssenceFluid, 0);
        capacity = FluidContainerRegistry.BUCKET_VOLUME * 16;
        buffer = FluidContainerRegistry.BUCKET_VOLUME * 16;
        maxTicks = 150;
    }

    @Override
    public void updateEntity() {
        if (getStackInSlot(0) != null && getStackInSlot(0).getItem() == ItemsRegistry.oreLump && fluidInput != null && fluidInput.amount >= FluidContainerRegistry.BUCKET_VOLUME) {
            if (ticksLeft >= maxTicks) {
                ItemStack stack = getStackInSlot(0);
                RandomUtils.checkAndSetCompound(stack);
                if (!stack.stackTagCompound.getString("ore").equals("")) {
                    String material = stack.stackTagCompound.getString("ore");
                    if (getStackInSlot(1) == null) {
                        setInventorySlotContents(1, OreDictionary.getOres("ingot" + material).get(0).copy());
                        decrStackSize(0, 1);
                        drain(FluidContainerRegistry.BUCKET_VOLUME, true);
                        ticksLeft = 0;
                        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                    } else if (getStackInSlot(1).stackSize < 64 && getStackInSlot(1).isItemEqual(OreDictionary.getOres("ingot" + material).get(0))) {
                        ItemStack output = getStackInSlot(1);
                        output.stackSize = output.stackSize + 1;
                        decrStackSize(0, 1);
                        drain(FluidContainerRegistry.BUCKET_VOLUME, true);
                        ticksLeft = 0;
                        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                    }
                }
            } else {
                ticksLeft++;
            }
        } else if (worldObj.getWorldTime() % 20 == 0) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        tagCompound.setInteger("ticksLeft", ticksLeft);
        NBTTagList nbttaglist = tagCompound.getTagList("Items", 10);
        this.inventory = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.inventory.length) {
                this.inventory[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
        if (!tagCompound.hasKey("Empty")) {
            FluidStack fluid = this.fluid.loadFluidStackFromNBT(tagCompound);
            if (fluid != null) {
                setMainFluid(fluid);
            }
            FluidStack fluidOut = new FluidStack(AlchemicalWizardry.lifeEssenceFluid, tagCompound.getInteger("outputAmount"));
            if (fluidOut != null) {
                setOutputFluid(fluidOut);
            }
            FluidStack fluidIn = new FluidStack(AlchemicalWizardry.lifeEssenceFluid, tagCompound.getInteger("inputAmount"));
            if (fluidIn != null) {
                setInputFluid(fluidIn);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        ticksLeft = tagCompound.getInteger("ticksLeft");
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
        if (fluid != null) {
            fluid.writeToNBT(tagCompound);
        } else {
            tagCompound.setString("Empty", "");
        }
        if (fluidOutput != null) {
            tagCompound.setInteger("outputAmount", fluidOutput.amount);
        }
        if (fluidInput != null) {
            tagCompound.setInteger("inputAmount", fluidInput.amount);
        }
    }

    public void setMainFluid(FluidStack fluid) {
        this.fluid = fluid;
    }

    public void setOutputFluid(FluidStack fluid) {
        this.fluidOutput = fluid;
    }

    public void setInputFluid(FluidStack fluid) {
        this.fluidInput = fluid;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (resource == null) return 0;
        resource = resource.copy();
        int totalUsed = 0;
        int used = this.fill(resource, doFill);
        resource.amount -= used;
        totalUsed += used;
        return totalUsed;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (resource == null) return null;
        if (!resource.isFluidEqual(fluidOutput)) return null;
        return drain(from, resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxEmpty, boolean doDrain) {
        return this.drain(maxEmpty, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return this.fluidInput != null && this.fluid.getFluid().equals(fluidInput.getFluid());
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return true;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        FluidTank compositeTank = new FluidTank(capacity);
        compositeTank.setFluid(fluid);
        return new FluidTankInfo[]{compositeTank.getInfo()};
    }

    @Override
    public FluidStack getFluid() {
        return fluid;
    }

    @Override
    public int getFluidAmount() {
        if (fluid == null) return 0;
        else return fluid.amount;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public FluidTankInfo getInfo() {
        return new FluidTankInfo(this);
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        TileEntity tile = this;
        if (resource == null) {
            return 0;
        }
        if (resource.fluidID != (new FluidStack(AlchemicalWizardry.lifeEssenceFluid, 1)).fluidID) {
            return 0;
        }
        if (!doFill) {
            if (fluidInput == null) {
                return Math.min(buffer, resource.amount);
            }
            if (!fluidInput.isFluidEqual(resource)) {
                return 0;
            }
            return Math.min(buffer - fluidInput.amount, resource.amount);
        }
        if (fluidInput == null) {
            fluidInput = new FluidStack(resource, Math.min(buffer, resource.amount));
            if (tile != null) {
                FluidEvent.fireEvent(new FluidEvent.FluidFillingEvent(fluidInput, tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord, this, resource.amount));
            }
            return fluidInput.amount;
        }
        if (!fluidInput.isFluidEqual(resource)) {
            return 0;
        }
        int filled = buffer - fluidInput.amount;
        if (resource.amount < filled) {
            fluidInput.amount += resource.amount;
            filled = resource.amount;
        } else {
            fluidInput.amount = buffer;
        }
        if (tile != null) {
            FluidEvent.fireEvent(new FluidEvent.FluidFillingEvent(fluidInput, tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord, this, resource.amount));
        }
        return filled;
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        if (fluidInput == null) {
            return null;
        }
        int drained = maxDrain;
        if (fluidInput.amount < drained) {
            drained = fluidInput.amount;
        }
        FluidStack stack = new FluidStack(fluidInput, drained);
        if (doDrain) {
            fluidInput.amount -= drained;
            if (fluidInput.amount <= 0) {
                fluidInput = null;
            }
            if (this != null) {
                FluidEvent.fireEvent(new FluidEvent.FluidDrainingEvent(fluidInput, this.worldObj, this.xCoord, this.yCoord, this.zCoord, this, maxDrain));
            }
        }
        if (fluidInput == null) {
            fluidInput = new FluidStack(AlchemicalWizardry.lifeEssenceFluid, 0);
        }
        if (worldObj != null) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
        return stack;
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
}
