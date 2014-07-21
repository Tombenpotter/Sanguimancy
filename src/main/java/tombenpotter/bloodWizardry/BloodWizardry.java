package tombenpotter.bloodWizardry;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import tombenpotter.bloodWizardry.proxies.CommonProxy;
import tombenpotter.bloodWizardry.registry.BlocksRegistry;
import tombenpotter.bloodWizardry.registry.RecipesRegistry;
import tombenpotter.bloodWizardry.registry.RitualRegistry;
import tombenpotter.bloodWizardry.registry.TERegistry;

@Mod(modid = BloodWizardry.modid, name = BloodWizardry.name, version = "0.3.0", dependencies = "required-after:AWWayofTime")
public class BloodWizardry {

    public static final String modid = "BloodWizardry";
    public static final String name = "Blood Wizardry: Alchemical Magic";
    public static final String texturePath = "bloodwizardry";

    @SidedProxy(clientSide = "tombenpotter.bloodWizardry.proxies.ClientProxy", serverSide = "tombenpotter.bloodWizardry.proxies.CommonProxy")
    public static CommonProxy proxy;
    @Mod.Instance(BloodWizardry.modid)
    public static BloodWizardry instance;

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
