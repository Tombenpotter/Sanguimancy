package tombenpotter.sanguimancy.tile;

import WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipe;
import WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipeRegistry;
import WayofTime.alchemicalWizardry.common.tileEntity.TEAltar;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
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
            for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
                if (worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ) != null && worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ) instanceof TEAltar) {
                    checkBloodAndMoveItems(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
                }
            }
        }
    }

    public void checkBloodAndMoveItems(int x, int y, int z) {
        TEAltar tile = (TEAltar) worldObj.getTileEntity(x, y, z);
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
                        this.setInventorySlotContents(0, null);
                        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                        tile.startCycle();
                    } else if (tile.getStackInSlot(0).getItem() == stack.getItem() && tile.getStackInSlot(0).stackSize + stack.stackSize <= 64) {
                        int s1 = tile.getStackInSlot(0).stackSize;
                        tile.getStackInSlot(0).stackSize = s1 + stack.stackSize;
                        this.setInventorySlotContents(0, null);
                        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                    }
                    worldObj.markBlockForUpdate(x, y, z);
                }
            }
        }
    }
}
