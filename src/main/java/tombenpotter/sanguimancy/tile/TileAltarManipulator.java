package tombenpotter.sanguimancy.tile;

import WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipe;
import WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipeRegistry;
import WayofTime.alchemicalWizardry.common.tileEntity.TEAltar;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import tombenpotter.sanguimancy.api.tile.TileBaseSidedInventory;

public class TileAltarManipulator extends TileBaseSidedInventory {

    public TileAltarManipulator() {
        slots = new ItemStack[2];
        customNBTTag = new NBTTagCompound();
    }

    @Override
    public String getInventoryName() {
        return "Altar Manipulator";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void updateEntity() {
        if (worldObj.getTileEntity(xCoord, yCoord - 1, zCoord) != null && worldObj.getTileEntity(xCoord, yCoord - 1, zCoord) instanceof TEAltar) {
            TEAltar altar = (TEAltar) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
            ItemStack stack = getStackInSlot(0);
            if (altar.getStackInSlot(0) != null) moveItemsFromAltar(altar);
            if (getStackInSlot(0) != null) insertItemInAltar(altar, stack);
        }
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        if (worldObj.getTileEntity(xCoord, yCoord - 1, zCoord) != null && worldObj.getTileEntity(xCoord, yCoord - 1, zCoord) instanceof TEAltar) {
            TEAltar altar = (TEAltar) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
            return slot == 0 && canInsertItemInAltar(altar, stack);
        }
        return false;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return slot == 1;
    }

    public boolean canInsertItemInAltar(TEAltar altar, ItemStack stack) {
        if (stack != null && altar != null) {
            int tier = altar.getTier();
            if (AltarRecipeRegistry.isRequiredItemValid(stack, tier)) {
                int containedBlood = altar.getCurrentBlood();
                AltarRecipe recipe = AltarRecipeRegistry.getAltarRecipeForItemAndTier(stack, tier);
                int bloodRequired = recipe.liquidRequired;
                if (altar.getStackInSlot(0) == null && bloodRequired * stack.stackSize <= containedBlood) {
                    return true;
                } else if (altar.getStackInSlot(0) != null && altar.getStackInSlot(0).isItemEqual(stack) &&
                        altar.getStackInSlot(0).stackSize + stack.stackSize <= altar.getInventoryStackLimit()
                        && altar.getStackInSlot(0).stackSize + stack.stackSize <= stack.getMaxStackSize()
                        && (altar.getStackInSlot(0).stackSize + stack.stackSize) * bloodRequired <= containedBlood) {
                    return true;
                }
            }
        }
        return false;
    }

    public void insertItemInAltar(TEAltar altar, ItemStack stack) {
        int tier = altar.getTier();
        if (AltarRecipeRegistry.isRequiredItemValid(stack, tier)) {
            int containedBlood = altar.getCurrentBlood();
            AltarRecipe recipe = AltarRecipeRegistry.getAltarRecipeForItemAndTier(stack, tier);
            int bloodRequired = recipe.liquidRequired;
            if (altar.getStackInSlot(0) == null) {
                if (bloodRequired * stack.stackSize <= containedBlood) {
                    altar.setInventorySlotContents(0, stack);
                    this.setInventorySlotContents(0, null);
                    worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                    altar.startCycle();
                }
            } else if (altar.getStackInSlot(0) != null && altar.getStackInSlot(0).isItemEqual(stack) &&
                    altar.getStackInSlot(0).stackSize + stack.stackSize <= altar.getInventoryStackLimit()
                    && altar.getStackInSlot(0).stackSize + stack.stackSize <= stack.getMaxStackSize()
                    && (altar.getStackInSlot(0).stackSize + stack.stackSize) * bloodRequired <= containedBlood) {
                int s1 = altar.getStackInSlot(0).stackSize;
                altar.getStackInSlot(0).stackSize = s1 + stack.stackSize;
                this.setInventorySlotContents(0, null);
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                worldObj.markBlockForUpdate(altar.xCoord, altar.yCoord, altar.zCoord);
            }
        }
    }

    public void moveItemsFromAltar(TEAltar altar) {
        ItemStack stack = altar.getStackInSlot(0).copy();
        if (altar.getProgress() <= 0) {
            if (getStackInSlot(1) == null) {
                this.setInventorySlotContents(1, stack);
                altar.setInventorySlotContents(0, null);
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                worldObj.markBlockForUpdate(altar.xCoord, altar.yCoord, altar.zCoord);
            } else if (getStackInSlot(1) != null && getStackInSlot(1).isItemEqual(stack) &&
                    getStackInSlot(1).stackSize + stack.stackSize <= stack.getMaxStackSize()) {
                getStackInSlot(1).stackSize += stack.stackSize;
                altar.setInventorySlotContents(0, null);
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                worldObj.markBlockForUpdate(altar.xCoord, altar.yCoord, altar.zCoord);
            }
        }
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
