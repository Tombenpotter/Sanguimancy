package tombenpotter.oldsanguimancy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tombenpotter.oldsanguimancy.compat.computercraft.PeripheralProvider;
import tombenpotter.oldsanguimancy.compat.waila.WailaCompatRegistry;
import tombenpotter.oldsanguimancy.network.PacketHandler;
import tombenpotter.oldsanguimancy.proxies.CommonProxy;
import tombenpotter.oldsanguimancy.registry.*;
import tombenpotter.oldsanguimancy.util.ConfigHandler;
import tombenpotter.oldsanguimancy.util.EventHandler;
import tombenpotter.oldsanguimancy.util.RandomUtils;
import tombenpotter.oldsanguimancy.util.enums.ModList;
import tombenpotter.oldsanguimancy.util.teleporting.TeleportingQueue;

@Mod(modid = Sanguimancy.modid, name = Sanguimancy.name, version = Sanguimancy.version, dependencies = Sanguimancy.depend, guiFactory = "tombenpotter.sanguimancy.client.gui.ConfigGuiFactory")
public class Sanguimancy {

    public static final String modid = "Sanguimancy";
    public static final String name = "Sanguimancy";
    public static final String texturePath = "sanguimancy";
    public static final String clientProxy = "tombenpotter.sanguimancy.proxies.ClientProxy";
    public static final String commonProxy = "tombenpotter.sanguimancy.proxies.CommonProxy";
    public static final String depend = "required-after:AWWayofTime;" + "required-after:guideapi;" + "after:Waila";
    public static final String channel = "Sanguimancy";
    public static final String version = "@VERSION@";
    public static boolean isTTLoaded = false;
    public static boolean isIguanaTweaksLoaded = false;
    public static boolean isAprilFools = false;
    public static Logger logger = LogManager.getLogger(Sanguimancy.name);
    public static CreativeTabs tabSanguimancy = new CreativeTabs("tab" + Sanguimancy.modid) {
        @Override
        public ItemStack getIconItemStack() {
            return new ItemStack(ItemsRegistry.playerSacrificer, 1, 0);
        }

        @Override
        public Item getTabIconItem() {
            return ItemsRegistry.playerSacrificer;
        }
    };

    @SidedProxy(clientSide = clientProxy, serverSide = commonProxy)
    public static CommonProxy proxy;
    @Mod.Instance(Sanguimancy.modid)
    public static Sanguimancy instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        instance = this;
        ConfigHandler.init(event.getSuggestedConfigurationFile());
        RandomUtils.addOreDictColors();
        TileRegistry.registerTEs();
        BlocksRegistry.registerBlocks();
        ItemsRegistry.registerItems();
        RecipesRegistry.registerShapedRecipes();
        RecipesRegistry.registerOrbRecipes();
        EntitiesRegistry.registerEntities();
        PotionsRegistry.potionPreInit();
        if (ModList.computercraft.isLoaded()) PeripheralProvider.register();
        RandomUtils.createSNDimension();
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        proxy.load();
        RitualRegistry.registerRituals();
        RecipesRegistry.registerAltarRecipes();
        RecipesRegistry.registerAlchemyRecipes();
        RecipesRegistry.registerBindingRecipes();
        PotionsRegistry.registerPotions();
        FMLCommonHandler.instance().bus().register(new EventHandler());
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        FMLCommonHandler.instance().bus().register(TeleportingQueue.getInstance());
        PacketHandler.registerPackets();
        if (Loader.isModLoaded("Waila")) {
            WailaCompatRegistry.register();
        }
        NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
    }

    @Mod.EventHandler
    public void imcCallback(FMLInterModComms.IMCEvent event) {
        for (final FMLInterModComms.IMCMessage imcMessage : event.getMessages()) {
            MessageRegistry.registerMessage(imcMessage.key, imcMessage);
        }
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        RecipesRegistry.registerCustomModRecipes();
        SanguimancyGuide.registerGuide();
        proxy.postLoad();
        isTTLoaded = Loader.isModLoaded("ThaumicTinkerer");
        isIguanaTweaksLoaded = Loader.isModLoaded("IguanaTweaksTConstruct");
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        RandomUtils.setLogToPlank();
        RandomUtils.setOreLumpList();
        RandomUtils.setTranspositionBlockBlacklist();
        RandomUtils.setTeleposerBlacklist();
    }
}
