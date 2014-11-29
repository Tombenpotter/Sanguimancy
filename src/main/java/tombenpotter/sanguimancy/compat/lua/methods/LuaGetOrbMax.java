package tombenpotter.sanguimancy.compat.lua.methods;

import tombenpotter.sanguimancy.tile.TileBloodInterface;
import net.minecraft.tileentity.TileEntity;

public class LuaGetOrbMax extends LuaMethod{
    public LuaGetOrbMax() {
        super("getOrbMaximum");
    }

    @Override
    public Object[] call(TileEntity te, Object[] args) {
        if (te instanceof TileBloodInterface)
        {
            return new Integer[]{((TileBloodInterface)te).getOrbMax()};
        }
        return new Object[0];
    }

    @Override
    public String[] getDetails() {
        return new String[0];
    }
}
