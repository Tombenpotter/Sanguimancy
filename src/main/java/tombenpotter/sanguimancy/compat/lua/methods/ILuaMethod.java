package tombenpotter.sanguimancy.compat.lua.methods;

import net.minecraft.tileentity.TileEntity;

public interface ILuaMethod {
    public String getMethodName();

    public abstract Object[] call(TileEntity te, Object[] args) throws Exception;

    public String getArgs();

    public String[] getDetails();
}