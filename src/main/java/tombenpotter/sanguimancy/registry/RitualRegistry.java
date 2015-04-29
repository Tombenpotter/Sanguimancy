package tombenpotter.sanguimancy.registry;

import WayofTime.alchemicalWizardry.AlchemicalWizardry;
import WayofTime.alchemicalWizardry.api.rituals.Rituals;
import net.minecraft.util.StatCollector;
import tombenpotter.sanguimancy.entity.EntityChickenMinion;
import tombenpotter.sanguimancy.entity.EntityPlayerPointer;
import tombenpotter.sanguimancy.rituals.*;
import tombenpotter.sanguimancy.util.ConfigHandler;

import java.util.ArrayList;

public class RitualRegistry {

    public static void registerRituals() {
        if (ConfigHandler.enableDrillOfTheDead)
            Rituals.registerRitual("drillOfTheDeadRitual", 2, 50000, new RitualEffectDrillOfTheDead(), StatCollector.translateToLocal("ritual.Sanguimancy.drill.dead"));
        if (ConfigHandler.enableVulcanosFrigius)
            Rituals.registerRitual("vulcanosFrigiusRitual", 1, 250, new RitualEffectCobble(), StatCollector.translateToLocal("ritual.Sanguimancy.vulcanos.frigius"));
        if (ConfigHandler.enableTrash)
            Rituals.registerRitual("trashRitual", 1, 50, new RitualEffectTrash(), StatCollector.translateToLocal("ritual.Sanguimancy.trash"));
        if (ConfigHandler.enableIllumination)
            Rituals.registerRitual("illuminationRitual", 1, 300, new RitualEffectLighting(), StatCollector.translateToLocal("ritual.Sanguimancy.illumination"));
        if (ConfigHandler.enableFelling)
            Rituals.registerRitual("fellingRitual", 1, 500, new RitualEffectFelling(), StatCollector.translateToLocal("ritual.Sanguimancy.feller"));
        if (ConfigHandler.enablePlacer)
            Rituals.registerRitual("placerRitual", 1, 500, new RitualEffectPlacer(), StatCollector.translateToLocal("ritual.Sanguimancy.placer"));
        if (ConfigHandler.enablePump)
            Rituals.registerRitual("pumpRitual", 1, 750, new RitualEffectPump(), StatCollector.translateToLocal("ritual.Sanguimancy.pump"));
        // Rituals.registerRitual("quarryRitual", 2, 10000, new RitualEffectQuarry(), StatCollector.translateToLocal("ritual.Sanguimancy.quarry"));
        if (ConfigHandler.enablePortal)
            Rituals.registerRitual("portalRitual", 1, 15000, new RitualEffectPortal(), StatCollector.translateToLocal("ritual.Sanguimancy.portal"));
        if(ConfigHandler.enableAltarBuilder)
            Rituals.registerRitual("altarBuilderRitual", 1, 450, new RitualEffectAltarBuilder(), StatCollector.translateToLocal("ritual.Sanguimancy.altar.builder"));
    }

    public static void addToWoSBlacklist() {
        if (AlchemicalWizardry.wellBlacklist == null) AlchemicalWizardry.wellBlacklist = new ArrayList<Class>();
        AlchemicalWizardry.wellBlacklist.add(EntityPlayerPointer.class);
        AlchemicalWizardry.wellBlacklist.add(EntityChickenMinion.class);
    }
}
