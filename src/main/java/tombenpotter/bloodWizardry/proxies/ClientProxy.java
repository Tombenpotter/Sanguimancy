package tombenpotter.bloodWizardry.proxies;

import cpw.mods.fml.client.registry.ClientRegistry;
import tombenpotter.bloodWizardry.client.render.RenderAltarDiviner;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerRenders() {
        ClientRegistry.bindTileEntitySpecialRenderer(tombenpotter.bloodWizardry.tile.TileAltarDiviner.class, new RenderAltarDiviner());
    }
}
