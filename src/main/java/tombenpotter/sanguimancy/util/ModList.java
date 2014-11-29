package tombenpotter.sanguimancy.util;

import cpw.mods.fml.common.Loader;

public enum ModList {
    opencomputers(Names.OPENCOMPUTERS),
    computercraft(Names.COMPUTERCRAFT);

    private String ID;
    private boolean loaded;

    ModList(String modId)
    {
        ID = modId;
        loaded = Loader.isModLoaded(modId);
    }

    public boolean isLoaded()
    {
        return loaded;
    }

    public class Names
    {
        public static final String OPENCOMPUTERS = "OpenComputers";
        public static final String COMPUTERCRAFT = "ComputerCraft";
    }
}
