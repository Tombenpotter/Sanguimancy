package tombenpotter.sanguimancy.compat.lua.events;

import dan200.computercraft.api.peripheral.IComputerAccess;
import li.cil.oc.api.machine.Context;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.Optional;
import tombenpotter.sanguimancy.api.tiles.TileComputerBase;
import tombenpotter.sanguimancy.util.enums.ModList;

public abstract class LuaEvent {

    String name;

    public LuaEvent(String name) {
        this.name = name;
    }

    public abstract boolean checkEvent(TileEntity te);

    public void announce(TileEntity te, Object... message) {
        if (!(te instanceof TileComputerBase)) return;
        TileComputerBase cTE = (TileComputerBase) te;
        if (ModList.computercraft.isLoaded())
            computerCraftAnnounce(cTE, message);
        if (ModList.opencomputers.isLoaded())
            openComputersAnnounce(cTE, message);
    }

    @Optional.Method(modid = ModList.Names.COMPUTERCRAFT)
    public void computerCraftAnnounce(TileComputerBase te, Object... message) {
        for (Object computer : te.getComputers()) {
            ((IComputerAccess) computer).queueEvent(name, message);
        }
    }

    @Optional.Method(modid = ModList.Names.OPENCOMPUTERS)
    public void openComputersAnnounce(TileComputerBase te, Object... message) {
        for (Object context : te.getContext()) {
            ((Context) context).signal(name, message);
        }
    }
}
