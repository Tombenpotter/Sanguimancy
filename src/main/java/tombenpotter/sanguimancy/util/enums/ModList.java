package tombenpotter.sanguimancy.util.enums;

import net.minecraftforge.fml.common.Loader;

public enum ModList {
    opencomputers(Names.OPENCOMPUTERS),
    computercraft(Names.COMPUTERCRAFT);

    private boolean loaded;

    ModList(String modId) {
        loaded = Loader.isModLoaded(modId);
    }

    public boolean isLoaded() {
        return loaded;
    }

    public class Names {
        public static final String OPENCOMPUTERS = "OpenComputers";
        public static final String COMPUTERCRAFT = "ComputerCraft";
    }
}
