package tombenpotter.sanguimancy.registry;

import net.minecraftforge.fml.common.registry.GameRegistry;
import tombenpotter.sanguimancy.tiles.*;

public class TileRegistry {

    public static void registerTEs() {
        GameRegistry.registerTileEntity(TileAltarEmitter.class, "TileAltarEmitter");
        GameRegistry.registerTileEntity(TileAltarDiviner.class, "TileAltarDiviner");
        GameRegistry.registerTileEntity(TileAltarManipulator.class, "TileAltarManipulator");
        GameRegistry.registerTileEntity(TileIllusion.class, "TileIllusion");
        GameRegistry.registerTileEntity(TileBloodInterface.class, "TileBloodInterface");
        GameRegistry.registerTileEntity(TileSacrificeTransfer.class, "TileSacrificeTransfer");
        GameRegistry.registerTileEntity(TileCorruptionCrystallizer.class, "TileCorruptionCrystallizer");

        /*
        GameRegistry.registerTileEntity(TileBloodCleaner.class, "TileBloodCleaner");
        GameRegistry.registerTileEntity(TileCamouflage.class, "TileCamouflage");
        GameRegistry.registerTileEntity(TileCamouflageBound.class, "TileCamouflageBound");
        */
    }
}
