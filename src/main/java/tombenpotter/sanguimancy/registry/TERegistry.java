package tombenpotter.sanguimancy.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import tombenpotter.sanguimancy.tile.TileAltarEmitter;
import tombenpotter.sanguimancy.tile.TileAltarDiviner;

public class TERegistry {

    public static void registerTEs() {
        GameRegistry.registerTileEntity(TileAltarDiviner.class, "TileAltarDiviner");
        GameRegistry.registerTileEntity(TileAltarEmitter.class, "TileAltarEmitter");
    }
}
