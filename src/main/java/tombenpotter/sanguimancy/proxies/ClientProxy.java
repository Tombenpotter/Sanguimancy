package tombenpotter.sanguimancy.proxies;

import cpw.mods.fml.client.registry.ClientRegistry;
import tombenpotter.sanguimancy.client.render.RenderAltarDiviner;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerRenders() {
        ClientRegistry.bindTileEntitySpecialRenderer(tombenpotter.sanguimancy.tile.TileAltarDiviner.class, new RenderAltarDiviner());
    }
}
