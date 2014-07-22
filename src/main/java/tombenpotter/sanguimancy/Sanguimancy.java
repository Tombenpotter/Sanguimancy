package tombenpotter.sanguimancy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import tombenpotter.sanguimancy.proxies.CommonProxy;
import tombenpotter.sanguimancy.registry.BlocksRegistry;
import tombenpotter.sanguimancy.registry.RecipesRegistry;
import tombenpotter.sanguimancy.registry.RitualRegistry;
import tombenpotter.sanguimancy.registry.TERegistry;

@Mod(modid = Sanguimancy.modid, name = Sanguimancy.name, version = "0.4.0", dependencies = "required-after:AWWayofTime")
public class Sanguimancy {

    public static final String modid = "Sanguimancy";
    public static final String name = "Sanguimancy";
    public static final String texturePath = "sanguimancy";

    @SidedProxy(clientSide = "tombenpotter.sanguimancy.proxies.ClientProxy", serverSide = "tombenpotter.sanguimancy.proxies.CommonProxy")
    public static CommonProxy proxy;
    @Mod.Instance(Sanguimancy.modid)
    public static Sanguimancy instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        TERegistry.registerTEs();
        BlocksRegistry.registerBlocks();
        RecipesRegistry.registerShapedRecipes();
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        proxy.load();
        RitualRegistry.registerRituals();
        RecipesRegistry.registerAltarRecipes();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }
}
