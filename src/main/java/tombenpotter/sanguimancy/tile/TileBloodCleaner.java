package tombenpotter.sanguimancy.tile;

import WayofTime.alchemicalWizardry.AlchemicalWizardry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;
import tombenpotter.sanguimancy.api.tile.TileBaseSidedInventory;
import tombenpotter.sanguimancy.recipes.RecipeBloodCleanser;

public class TileBloodCleaner extends TileBaseSidedInventory implements IFluidHandler {

    public int capacity;
    public int ticksLeft;
    public int maxTicks;
    public FluidTank tank;
    public boolean isActive;

    public TileBloodCleaner() {
        slots = new ItemStack[2];
        capacity = FluidContainerRegistry.BUCKET_VOLUME * 16;
        maxTicks = 150;
        tank = new FluidTank(new FluidStack(AlchemicalWizardry.lifeEssenceFluid, 0), capacity);
        isActive = false;
        customNBTTag = new NBTTagCompound();
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
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("ticksLeft", ticksLeft);
        tagCompound.setBoolean("isActive", isActive);
        tank.writeToNBT(tagCompound);
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
    public String getInventoryName() {
        return "Lump Cleaner";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
        return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && entityPlayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        if (slot == 0) return true;
        else return false;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int var1) {
        ForgeDirection dir = ForgeDirection.getOrientation(var1);
        switch (dir) {
            case DOWN:
            case UP:
            case NORTH:
            case SOUTH:
            case EAST:
            case WEST:
                return new int[]{0, 1};
            default:
                return new int[]{};
        }
    }
}
