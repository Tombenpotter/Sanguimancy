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
import tombenpotter.sanguimancy.registry.GuideRegistry;
import tombenpotter.sanguimancy.tile.*;
import tombenpotter.sanguimancy.util.EventHandler;

public class ClientProxy extends CommonProxy {

    @Override
    public void load() {
        registerRenders();
        FMLCommonHandler.instance().bus().register(new EventHandler.ClientEventHandler());
    }

    @Override
    public void postLoad() {
        GuideRegistry.createEntries();
    }

    @Override
    public void registerRenders() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileAltarDiviner.class, new RenderAltarDiviner());
        ClientRegistry.bindTileEntitySpecialRenderer(TileCorruptionCrystallizer.class, new RenderCorruptionCrystallizer());
        RenderingRegistry.registerBlockHandler(BlocksRegistry.bloodTank.getRenderType(), new RenderBloodTank());
        ClientRegistry.bindTileEntitySpecialRenderer(TileItemSNPart.class, new RenderBoundItem());
        ClientRegistry.bindTileEntitySpecialRenderer(TileBloodInterface.class, new RenderBloodInterface());
        ClientRegistry.bindTileEntitySpecialRenderer(TileSimpleSNBranch.class, new RenderSoulBranch());

        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlocksRegistry.altarDiviner), new RenderAltarDiviner());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlocksRegistry.corruptionCrystallizer), new RenderCorruptionCrystallizer());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlocksRegistry.bloodTank), new RenderBloodTank());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlocksRegistry.boundItem), new RenderBoundItem());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlocksRegistry.bloodInterface), new RenderBloodInterface());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlocksRegistry.simpleBranch), new RenderSoulBranch());

        RenderingRegistry.registerEntityRenderingHandler(EntityChickenMinion.class, new RenderChickenMinion(new ModelChicken(), 1.0F));
        RenderingRegistry.registerEntityRenderingHandler(EntityPlayerPointer.class, new RenderPlayerPointer());

    }
}
