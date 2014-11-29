package tombenpotter.sanguimancy.compat.lua.methods;

import dan200.computercraft.api.lua.LuaException;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class LuaGetStackInSlot extends LuaMethod {
    public LuaGetStackInSlot() {
        super("getStackInSlot");
    }

    @Override
    public Object[] call(TileEntity te, Object[] args) throws LuaException, InterruptedException {
        if (te instanceof IInventory && args != null && args.length == 1 && args[0] instanceof Number) {
            ItemStack stack = ((IInventory) te).getStackInSlot(((Number) args[0]).intValue());
            if (stack != null) {
                return new Object[]{LuaHelper.stackToMap(stack)};
            }
        }
        return new Object[0];
    }

    @Override
    public String[] getDetails() {
        return new String[0];
    }
}
