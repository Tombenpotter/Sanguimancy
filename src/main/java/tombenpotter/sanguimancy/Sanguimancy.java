package tombenpotter.sanguimancy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tombenpotter.sanguimancy.compat.computercraft.PeripheralProvider;
//import tombenpotter.sanguimancy.compat.waila.WailaCompatRegistry;
import tombenpotter.sanguimancy.network.PacketHandler;
import tombenpotter.sanguimancy.proxy.CommonProxy;
import tombenpotter.sanguimancy.registry.*;
import tombenpotter.sanguimancy.util.ConfigHandler;
import tombenpotter.sanguimancy.util.EventHandler;
import tombenpotter.sanguimancy.util.RandomUtils;
import tombenpotter.sanguimancy.util.enums.ModList;

@Mod(modid = Sanguimancy.modid, name = Sanguimancy.name, version = Sanguimancy.version, dependencies = Sanguimancy.depend, guiFactory = "tombenpotter.sanguimancy.client.gui.ConfigGuiFactory")
public class Sanguimancy {
	static {
		FluidRegistry.enableUniversalBucket();
	}
	
    public static final String modid = "Sanguimancy";
    public static final String name = "Sanguimancy";
    public static final String texturePath = "sanguimancy";
    public static final String clientProxy = "tombenpotter.sanguimancy.proxies.ClientProxy";
    public static final String commonProxy = "tombenpotter.sanguimancy.proxies.CommonProxy";
    public static final String depend = "required-after:AWWayofTime;" + "required-after:guideapi;";
    public static final String channel = "Sanguimancy";
    public static final String version = "@VERSION@";
    public static boolean isTTLoaded = false;
    public static boolean isIguanaTweaksLoaded = false;
    public static boolean isAprilFools = false;
    public static Logger logger = LogManager.getLogger(Sanguimancy.name);

    public static CreativeTabs creativeTab = new CreativeTabs("tab" + modid) {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(Items.APPLE);
        }
    };

    @SidedProxy(clientSide = clientProxy, serverSide = commonProxy)
    public static CommonProxy proxy;
    @Mod.Instance(Sanguimancy.modid)
    public static Sanguimancy instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ConfigHandler.init(event.getSuggestedConfigurationFile());
        RandomUtils.addOreDictColors();
        TileRegistry.registerTEs();
        BlocksRegistry.registerBlocks();
        ItemsRegistry.registerItems();
        RecipesRegistry.registerShapedRecipes();
        RecipesRegistry.registerOrbRecipes();
        EntitiesRegistry.registerEntities();
        PotionsRegistry.registerPotions();
        if (ModList.computercraft.isLoaded()) PeripheralProvider.register();
//        RandomUtils.createSNDimension();
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        proxy.load();
        RitualsRegistry.registerRituals();
        RecipesRegistry.registerAltarRecipes();
        RecipesRegistry.registerBindingRecipes();
        FMLCommonHandler.instance().bus().register(new EventHandler());
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        PacketHandler.registerPackets();
//        if (Loader.isModLoaded("Waila")) {
//            WailaCompatRegistry.register();
//        }
        NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        RecipesRegistry.registerCustomModRecipes();
        SanguimancyGuide.buildBook();
        proxy.postLoad();
        isTTLoaded = Loader.isModLoaded("ThaumicTinkerer");
        isIguanaTweaksLoaded = Loader.isModLoaded("IguanaTweaksTConstruct");
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        RandomUtils.setLogToPlank();
    }
}
