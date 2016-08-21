package tombenpotter.oldsanguimancy.proxies;


import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tombenpotter.oldsanguimancy.old.ded.*;
import tombenpotter.oldsanguimancy.old.ded.snManifestation.ISNComponent;
import tombenpotter.oldsanguimancy.client.particle.EntityColoredFlameFX;
import tombenpotter.oldsanguimancy.client.render.*;
import tombenpotter.oldsanguimancy.entity.EntityChickenMinion;
import tombenpotter.sanguimancy.tiles.TileAltarDiviner;
import tombenpotter.sanguimancy.registry.BlocksRegistry;
import tombenpotter.sanguimancy.tiles.TileAltarManipulator;
import tombenpotter.sanguimancy.tiles.TileBloodInterface;
import tombenpotter.sanguimancy.tiles.TileCorruptionCrystallizer;
import tombenpotter.sanguimancy.util.EventHandler;

public class ClientProxy extends CommonProxy {

    @Override
    public void preLoad() {
    }

    @Override
    public void load() {
        registerRenders();
        FMLCommonHandler.instance().bus().register(new EventHandler.ClientEventHandler());
        MinecraftForge.EVENT_BUS.register(new EventHandler.ClientEventHandler());
    }

    @Override
    public void postLoad() {
    }

    @Override
    public void registerRenders() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileAltarDiviner.class, new RenderAltarDiviner());
        ClientRegistry.bindTileEntitySpecialRenderer(TileCorruptionCrystallizer.class, new RenderCorruptionCrystallizer());
        RenderingRegistry.registerBlockHandler(BlocksRegistry.bloodTank.getRenderType(), new RenderBloodTank());
        ClientRegistry.bindTileEntitySpecialRenderer(TileItemSNPart.class, new RenderBoundItem());
        ClientRegistry.bindTileEntitySpecialRenderer(TileBloodInterface.class, new RenderBloodInterface());
        ClientRegistry.bindTileEntitySpecialRenderer(TileSimpleSNBranch.class, new RenderSoulBranch());
        ClientRegistry.bindTileEntitySpecialRenderer(TileRitualSNPart.class, new RenderRitualRepresentation());
        ClientRegistry.bindTileEntitySpecialRenderer(TileAltarManipulator.class, new RenderAltarManipulator());

        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlocksRegistry.altarDiviner), new RenderAltarDiviner());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlocksRegistry.corruptionCrystallizer), new RenderCorruptionCrystallizer());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlocksRegistry.bloodTank), new RenderBloodTank());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlocksRegistry.boundItem), new RenderBoundItem());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlocksRegistry.bloodInterface), new RenderBloodInterface());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlocksRegistry.simpleBranch), new RenderSoulBranch());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlocksRegistry.ritualRepresentation), new RenderRitualRepresentation());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlocksRegistry.altarManipulator), new RenderAltarManipulator());

        RenderingRegistry.registerEntityRenderingHandler(EntityChickenMinion.class, new RenderChickenMinion(new ModelChicken(), 1.0F));
        RenderingRegistry.registerEntityRenderingHandler(EntityPlayerPointer.class, new RenderPlayerPointer());

    }

    @Override
    @SideOnly(Side.CLIENT)
    public EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }

    @SideOnly(Side.CLIENT)
    public void addLinkingEffects(ISNComponent component, World world, double x, double y, double z) {
        if (!component.getSNKnots().isEmpty()) {
            double dx = x + 0.5;
            double dy = y + 0.5;
            double dz = z + 0.5;
            for (int i = 0; i < component.getAdjacentISNComponents().length; i++) {
                if (component.getAdjacentISNComponents()[i] != null) {
                    for (float j = 0; j <= ForgeDirection.VALID_DIRECTIONS[i].offsetX; j += 0.1) {
                        EntityFX particle = new EntityColoredFlameFX(world, dx + j, dy, dz, 0, 0, 0, 160, 160, 160, 100);
                        FMLClientHandler.instance().getClient().effectRenderer.addEffect(particle);
                    }
                    for (float j = 0; j <= ForgeDirection.VALID_DIRECTIONS[i].offsetY; j += 0.1) {
                        EntityFX particle = new EntityColoredFlameFX(world, dx, dy + j, dz, 0, 0, 0, 160, 160, 160, 100);
                        FMLClientHandler.instance().getClient().effectRenderer.addEffect(particle);
                    }
                    for (float j = 0; j <= ForgeDirection.VALID_DIRECTIONS[i].offsetZ; j += 0.1) {
                        EntityFX particle = new EntityColoredFlameFX(world, dx, dy, dz + j, 0, 0, 0, 160, 160, 160, 100);
                        FMLClientHandler.instance().getClient().effectRenderer.addEffect(particle);
                    }
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void addColoredFlameEffects(World world, double x, double y, double z, double movX, double movY, double movZ, int red, int green, int blue) {
        EntityColoredFlameFX particle = new EntityColoredFlameFX(world, x, y, z, movX, movY, movZ, red, green, blue);
        FMLClientHandler.instance().getClient().effectRenderer.addEffect(particle);
    }

    @Override
    public World getClientWorld() {
        return Minecraft.getMinecraft().theWorld;
    }
}
