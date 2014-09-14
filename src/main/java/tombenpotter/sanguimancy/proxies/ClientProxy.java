package tombenpotter.sanguimancy.proxies;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import tombenpotter.sanguimancy.client.render.RenderAltarDiviner;
import tombenpotter.sanguimancy.client.render.RenderChickenMinion;
import tombenpotter.sanguimancy.client.render.RenderItemAltarDiviner;
import tombenpotter.sanguimancy.entity.EntityChickenMinion;
import tombenpotter.sanguimancy.registry.BlocksRegistry;
import tombenpotter.sanguimancy.util.ClientEventHandler;

public class ClientProxy extends CommonProxy {

    @Override
    public void load() {
        registerRenders();
        FMLCommonHandler.instance().bus().register(new ClientEventHandler());
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
    }

    @Override
    public void registerRenders() {
        ClientRegistry.bindTileEntitySpecialRenderer(tombenpotter.sanguimancy.tile.TileAltarDiviner.class, new RenderAltarDiviner());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlocksRegistry.altarDiviner), new RenderItemAltarDiviner());
        RenderingRegistry.registerEntityRenderingHandler(EntityChickenMinion.class, new RenderChickenMinion(new ModelChicken(), 1.0F));
    }
}
