package tombenpotter.bloodWizardry.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import tombenpotter.bloodWizardry.tile.TileAltarEmitter;
import tombenpotter.bloodWizardry.tile.TileAltarDiviner;

public class TERegistry {

    public static void registerTEs() {
        GameRegistry.registerTileEntity(TileAltarDiviner.class, "TileAltarDiviner");
        GameRegistry.registerTileEntity(TileAltarEmitter.class, "TileAltarEmitter");
    }
}
