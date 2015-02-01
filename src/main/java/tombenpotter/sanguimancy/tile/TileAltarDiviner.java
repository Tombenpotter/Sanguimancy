package tombenpotter.sanguimancy.tile;

import WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipe;
import WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipeRegistry;
import WayofTime.alchemicalWizardry.common.tileEntity.TEAltar;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import tombenpotter.sanguimancy.api.tile.TileBaseInventory;

public class TileAltarDiviner extends TileBaseInventory {

    public TileAltarDiviner() {
        slots = new ItemStack[1];
        custoomNBTTag = new NBTTagCompound();
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
    public void updateEntity() {
        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
            if (worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ) != null && worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ) instanceof TEAltar) {
                TEAltar tile = (TEAltar) worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
                if (getStackInSlot(0) != null) {
                    checkBloodAndMoveItems(tile);
                } else if (getStackInSlot(0) == null && tile.getStackInSlot(0) != null) {
                    moveItemFromAltar(tile);
                }
            }
        }
    }

    public void checkBloodAndMoveItems(TEAltar tile) {
        int tier = tile.getTier();
        ItemStack stack = getStackInSlot(0).copy();
        if (AltarRecipeRegistry.isRequiredItemValid(stack, tier)) {
            int containedBlood = tile.getCurrentBlood();
            int altarCapacity = tile.getCapacity();
            AltarRecipe recipe = AltarRecipeRegistry.getAltarRecipeForItemAndTier(stack, tier);
            int bloodRequired = recipe.liquidRequired;
            if (bloodRequired * stack.stackSize <= containedBlood) {
                if (tile.getStackInSlot(0) == null) {
                    tile.setInventorySlotContents(0, stack);
                    this.setInventorySlotContents(0, null);
                    worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                    tile.startCycle();
                }
            }
            if (tile.getStackInSlot(0) != null && tile.getStackInSlot(0).isItemEqual(stack) &&
                    tile.getStackInSlot(0).stackSize + stack.stackSize <= tile.getInventoryStackLimit()
                    && tile.getStackInSlot(0).stackSize + stack.stackSize <= stack.getMaxStackSize()
                    && (tile.getStackInSlot(0).stackSize + stack.stackSize) * bloodRequired <= containedBlood) {
                int s1 = tile.getStackInSlot(0).stackSize;
                tile.getStackInSlot(0).stackSize = s1 + stack.stackSize;
                this.setInventorySlotContents(0, null);
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            }
        }
    }

    public void moveItemFromAltar(TEAltar tile) {
        ItemStack stack = tile.getStackInSlot(0).copy();
        if (!tile.isActive() && tile.getProgress() <= 0) {
            this.setInventorySlotContents(0, stack);
            tile.setInventorySlotContents(0, null);
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            worldObj.markBlockForUpdate(tile.xCoord, tile.yCoord, tile.zCoord);
        }
    }
}
