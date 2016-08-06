package tombenpotter.sanguimancy.compat.lua.methods;

import net.minecraft.tileentity.TileEntity;
import tombenpotter.oldsanguimancy.tile.TileBloodInterface;

public class LuaGetOrbMax extends LuaMethod {
    public LuaGetOrbMax() {
        super("getOrbMaximum");
    }

    @Override
    public Object[] call(TileEntity te, Object[] args) {
        if (te instanceof TileBloodInterface) {
            return new Integer[]{((TileBloodInterface) te).getOrbMax()};
        }
        return new Object[0];
    }

    @Override
    public String[] getDetails() {
        return new String[0];
    }
}
