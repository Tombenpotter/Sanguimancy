package tombenpotter.sanguimancy.compat.lua.events;

import tombenpotter.sanguimancy.tile.TileBloodInterface;
import net.minecraft.tileentity.TileEntity;

public class LuaOrbMaxed extends LuaEvent{

    private boolean prevLess = false;

    public LuaOrbMaxed() {
        super("orb_limit");
    }

    @Override
    public boolean checkEvent(TileEntity te) {
        TileBloodInterface bloodInterface = (TileBloodInterface)te;
        if (bloodInterface!=null)
        {
            if (bloodInterface.getLifeEssence() == bloodInterface.getOrbMax()) {
                if (prevLess) {
                    announce(te, prevLess);
                    prevLess = false;
                    return true;
                }
            }
            else {
                if (!prevLess) {
                    announce(te, prevLess);
                    prevLess = true;
                    return true;
                }
            }
        }
        return false;
    }
}
