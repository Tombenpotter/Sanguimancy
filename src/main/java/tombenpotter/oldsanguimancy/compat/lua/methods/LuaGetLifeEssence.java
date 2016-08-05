package tombenpotter.oldsanguimancy.compat.lua.methods;

import net.minecraft.tileentity.TileEntity;
import tombenpotter.oldsanguimancy.tile.TileBloodInterface;

public class LuaGetLifeEssence extends LuaMethod {
    public LuaGetLifeEssence() {
        super("getLifeEssence");
    }

    @Override
    public Object[] call(TileEntity te, Object[] args) {
        if (te instanceof TileBloodInterface) {
            return new Integer[]{((TileBloodInterface) te).getLifeEssence()};
        }
        return new Object[0];
    }

    @Override
    public String[] getDetails() {
        return new String[0];
    }
}
