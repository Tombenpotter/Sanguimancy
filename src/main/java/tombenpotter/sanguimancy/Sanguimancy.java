package tombenpotter.sanguimancy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import tombenpotter.sanguimancy.compat.BUCompat;
import tombenpotter.sanguimancy.network.PacketHandler;
import tombenpotter.sanguimancy.proxies.CommonProxy;
import tombenpotter.sanguimancy.registry.*;
import tombenpotter.sanguimancy.util.EventHandler;
import tombenpotter.sanguimancy.util.RandomUtils;

@Mod(modid = Sanguimancy.modid, name = Sanguimancy.name, version = Sanguimancy.version, dependencies = "required-after:AWWayofTime ; after:BloodUtils ; after:Waila")
public class Sanguimancy {

    public static final String modid = "Sanguimancy";
    public static final String name = "Sanguimancy";
    public static final String texturePath = "sanguimancy";
    public static final String clientProxy = "tombenpotter.sanguimancy.proxies.ClientProxy";
    public static final String commonProxy = "tombenpotter.sanguimancy.proxies.CommonProxy";
    public static final String channel = "Sanguimancy";
    public static final String version = "1.1.7";
    public static boolean isTTLoaded = false;
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
        RandomUtils.addOreDictColors();
        TileRegistry.registerTEs();
        BlocksRegistry.registerBlocks();
        ItemsRegistry.registerItems();
        RecipesRegistry.registerShapedRecipes();
        RecipesRegistry.registerOrbRecipes();
        EntitiesRegistry.registerEntities();
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        proxy.load();
        RitualRegistry.registerRituals();
        RecipesRegistry.registerAltarRecipes();
        FMLCommonHandler.instance().bus().register(new EventHandler());
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        PacketHandler.registerPackets();
        if (Loader.isModLoaded("Waila")) {
            FMLInterModComms.sendMessage("Waila", "register", "tombenpotter.sanguimancy.compat.WailaCorruptionCrystallizer.register");
            FMLInterModComms.sendMessage("Waila", "register", "tombenpotter.sanguimancy.compat.WailaAltarDiviner.register");
            FMLInterModComms.sendMessage("Waila", "register", "tombenpotter.sanguimancy.compat.WailaAltarEmitter.register");
        }
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        if (Loader.isModLoaded("BloodUtils")) {
            BUCompat.createCategories();
            BUCompat.createEntries();
        }
        RecipesRegistry.registercorruptionRecipes();
        isTTLoaded = Loader.isModLoaded("ThaumicTinkerer");
    }
}