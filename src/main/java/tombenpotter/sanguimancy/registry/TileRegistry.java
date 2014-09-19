package tombenpotter.sanguimancy.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import tombenpotter.sanguimancy.tile.TileAltarDiviner;
import tombenpotter.sanguimancy.tile.TileAltarEmitter;
import tombenpotter.sanguimancy.tile.TileIllusion;
import tombenpotter.sanguimancy.tile.TileSacrificeTransfer;

public class TileRegistry {

    public static void registerTEs() {
        GameRegistry.registerTileEntity(TileAltarDiviner.class, "TileAltarDiviner");
        GameRegistry.registerTileEntity(TileAltarEmitter.class, "TileAltarEmitter");
        GameRegistry.registerTileEntity(TileSacrificeTransfer.class, "TileSacrificeTransfer");
        GameRegistry.registerTileEntity(TileIllusion.class, "TileIllusion");
    }
}
