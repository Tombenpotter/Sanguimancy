package tombenpotter.sanguimancy.proxies;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import tombenpotter.sanguimancy.client.render.*;
import tombenpotter.sanguimancy.entity.EntityChickenMinion;
import tombenpotter.sanguimancy.entity.EntityPlayerPointer;
import tombenpotter.sanguimancy.registry.BlocksRegistry;
import tombenpotter.sanguimancy.tile.TileAltarDiviner;
import tombenpotter.sanguimancy.tile.TileCorruptionCrystallizer;
import tombenpotter.sanguimancy.util.EventHandler;

public class ClientProxy extends CommonProxy {

    @Override
    public void load() {
        registerRenders();
        FMLCommonHandler.instance().bus().register(new EventHandler.ClientEventHandler());
    }

    @Override
    public void registerRenders() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileAltarDiviner.class, new RenderAltarDiviner());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlocksRegistry.altarDiviner), new RenderItemAltarDiviner());
        RenderingRegistry.registerEntityRenderingHandler(EntityChickenMinion.class, new RenderChickenMinion(new ModelChicken(), 1.0F));
        RenderingRegistry.registerEntityRenderingHandler(EntityPlayerPointer.class, new RenderPlayerPointer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileCorruptionCrystallizer.class, new RenderCorruptionCrystallizer());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlocksRegistry.corruptionCrystallizer), new RenderItemCorruptionCrystallizer());
        RenderingRegistry.registerBlockHandler(BlocksRegistry.bloodTank.getRenderType(), new RenderBloodTank());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlocksRegistry.bloodTank), new RenderBloodTank());
    }
}
