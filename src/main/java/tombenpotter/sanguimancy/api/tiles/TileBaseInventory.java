package tombenpotter.sanguimancy.api.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public abstract class TileBaseInventory extends TileBase implements ICapabilityProvider {

    protected ItemStackHandler inventory;

    public TileBaseInventory(int size) {
        super();
        inventory = new ItemStackHandler(size);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T) inventory;
        }
        return super.getCapability(capability, facing);
    }

    public ItemStackHandler getInventory(EnumFacing facing) {
        return (ItemStackHandler) getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);

        inventory.deserializeNBT(tagCompound.getCompoundTag("inventory"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);

        tagCompound.setTag("inventory", inventory.serializeNBT());

        return tagCompound;
    }
}
