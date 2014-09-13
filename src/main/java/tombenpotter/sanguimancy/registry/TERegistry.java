package tombenpotter.sanguimancy.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import tombenpotter.sanguimancy.tile.TileAltarDiviner;
import tombenpotter.sanguimancy.tile.TileAltarEmitter;
import tombenpotter.sanguimancy.tile.TileSacrificeTransfer;

public class TERegistry {

    public static void registerTEs() {
        GameRegistry.registerTileEntity(TileAltarDiviner.class, "TileAltarDiviner");
        GameRegistry.registerTileEntity(TileAltarEmitter.class, "TileAltarEmitter");
        GameRegistry.registerTileEntity(TileSacrificeTransfer.class, "TileSacrificeTransfer");
    }
}
