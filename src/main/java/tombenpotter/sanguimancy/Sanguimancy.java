package tombenpotter.sanguimancy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tombenpotter.oldsanguimancy.proxies.CommonProxy;

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

    public static CreativeTabs creativeTab = new CreativeTabs("tab" + modid) {
        @Override
        public Item getTabIconItem() {
            return Items.APPLE;
        }
    };

    @SidedProxy(clientSide = clientProxy, serverSide = commonProxy)
    public static CommonProxy proxy;
    @Mod.Instance(Sanguimancy.modid)
    public static Sanguimancy instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }
}
