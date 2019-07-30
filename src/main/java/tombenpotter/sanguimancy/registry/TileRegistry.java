package tombenpotter.sanguimancy.registry;

import net.minecraftforge.fml.common.registry.GameRegistry;
import tombenpotter.sanguimancy.tiles.*;

public class TileRegistry {

    public static void registerTEs() {
        GameRegistry.registerTileEntity(TileAltarEmitter.class, "TileAltarEmitter");
        GameRegistry.registerTileEntity(TileAltarDiviner.class, "TileAltarDiviner");
        GameRegistry.registerTileEntity(TileAltarManipulator.class, "TileAltarManipulator");
        GameRegistry.registerTileEntity(TileBloodInterface.class, "TileBloodInterface");
        GameRegistry.registerTileEntity(TileSacrificeTransfer.class, "TileSacrificeTransfer");
        GameRegistry.registerTileEntity(TileBloodCleaner.class, "TileBloodCleaner");
    }
}
