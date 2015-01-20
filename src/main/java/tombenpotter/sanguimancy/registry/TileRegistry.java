package tombenpotter.sanguimancy.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import tombenpotter.sanguimancy.tile.*;

public class TileRegistry {

    public static void registerTEs() {
        GameRegistry.registerTileEntity(TileAltarDiviner.class, "TileAltarDiviner");
        GameRegistry.registerTileEntity(TileAltarEmitter.class, "TileAltarEmitter");
        GameRegistry.registerTileEntity(TileSacrificeTransfer.class, "TileSacrificeTransfer");
        GameRegistry.registerTileEntity(TileIllusion.class, "TileIllusion");
        GameRegistry.registerTileEntity(TileCorruptionCrystallizer.class, "TileCorruptionCrystallizer");
        GameRegistry.registerTileEntity(TileBloodCleaner.class, "TileBloodCleaner");
        GameRegistry.registerTileEntity(TileBloodTank.class, "TileBloodTank");
        GameRegistry.registerTileEntity(TileDimensionalPortal.class, "TileDimensionalPortal");
        GameRegistry.registerTileEntity(TileItemSNPart.class, "TileItemSNPart");
        GameRegistry.registerTileEntity(TileBloodInterface.class, "TileBloodInterface");
        GameRegistry.registerTileEntity(TileSimpleSNKnot.class, "TileSimpleSNKnot");
        GameRegistry.registerTileEntity(TileSimpleSNBranch.class, "TileSimpleSNBranch");
        GameRegistry.registerTileEntity(TileToggleSNKnot.class, "TileToggleSNKnot");
        GameRegistry.registerTileEntity(TileRitualSNPart.class, "TileRitualSNPart");
        GameRegistry.registerTileEntity(TileEthereal.class, "TileEthereal");
    }
}
