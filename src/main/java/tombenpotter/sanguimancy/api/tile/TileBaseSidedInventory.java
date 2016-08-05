package tombenpotter.sanguimancy.api.tile;

import com.google.common.collect.Lists;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.ArrayList;

public abstract class TileBaseSidedInventory extends TileBaseInventory {

    public ArrayList<EnumFacing> sidesAllowed;

    public TileBaseSidedInventory(int size, EnumFacing... sidesAllowed) {
        super(size);

        this.sidesAllowed = Lists.newArrayList(sidesAllowed);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && sidesAllowed.contains(facing)) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && sidesAllowed.contains(facing)) {
            return (T) inventory;
        }
        return super.getCapability(capability, facing);
    }
}
