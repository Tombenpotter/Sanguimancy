package tombenpotter.sanguimancy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tombenpotter.sanguimancy.compat.BUCompat;
import tombenpotter.sanguimancy.compat.computercraft.PeripheralProvider;
import tombenpotter.sanguimancy.network.PacketHandler;
import tombenpotter.sanguimancy.proxies.CommonProxy;
import tombenpotter.sanguimancy.registry.*;
import tombenpotter.sanguimancy.util.ConfigHandler;
import tombenpotter.sanguimancy.util.EventHandler;
import tombenpotter.sanguimancy.util.ModList;
import tombenpotter.sanguimancy.util.RandomUtils;

@Mod(modid = Sanguimancy.modid, name = Sanguimancy.name, version = Sanguimancy.version, dependencies = Sanguimancy.depend, guiFactory = "tombenpotter.sanguimancy.client.gui.ConfigGuiFactory")
public class Sanguimancy {

    public static final String modid = "Sanguimancy";
    public static final String name = "Sanguimancy";
    public static final String texturePath = "sanguimancy";
    public static final String clientProxy = "tombenpotter.sanguimancy.proxies.ClientProxy";
    public static final String commonProxy = "tombenpotter.sanguimancy.proxies.CommonProxy";
    public static final String depend = "required-after:AWWayofTime;" + "after:BloodUtils;" + "after:Waila";
    public static final String channel = "Sanguimancy";
    public static final String version = "1.1.8";
    public static boolean isTTLoaded = false;
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
        if (ModList.computercraft.isLoaded()) PeripheralProvider.register();
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        proxy.load();
        RitualRegistry.registerRituals();
        RecipesRegistry.registerAltarRecipes();
        FMLCommonHandler.instance().bus().register(new EventHandler());
        MinecraftForge.EVENT_BUS.register(new EventHandler.ClientEventHandler());
        PacketHandler.registerPackets();
        if (Loader.isModLoaded("Waila")) {
            FMLInterModComms.sendMessage("Waila", "register", "tombenpotter.sanguimancy.compat.WailaCorruptionCrystallizer.register");
            FMLInterModComms.sendMessage("Waila", "register", "tombenpotter.sanguimancy.compat.WailaAltarDiviner.register");
            FMLInterModComms.sendMessage("Waila", "register", "tombenpotter.sanguimancy.compat.WailaAltarEmitter.register");
            FMLInterModComms.sendMessage("Waila", "register", "tombenpotter.sanguimancy.compat.WailaBloodTank.register");
        }
        NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        RecipesRegistry.registerCustomModRecipes();
        if (Loader.isModLoaded("BloodUtils")) {
            BUCompat.createCategories();
            BUCompat.createEntries();
        }
        isTTLoaded = Loader.isModLoaded("ThaumicTinkerer");
    }
}