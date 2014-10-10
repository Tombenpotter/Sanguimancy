package tombenpotter.sanguimancy.registry;

import WayofTime.alchemicalWizardry.AlchemicalWizardry;
import WayofTime.alchemicalWizardry.api.rituals.Rituals;
import net.minecraft.util.StatCollector;
import tombenpotter.sanguimancy.entity.EntityChickenMinion;
import tombenpotter.sanguimancy.entity.EntityPlayerPointer;
import tombenpotter.sanguimancy.rituals.*;

public class RitualRegistry {

    public static void registerRituals() {
        Rituals.registerRitual("drillOfTheDeadRitual", 2, 50000, new RitualEffectDrillOfTheDead(), StatCollector.translateToLocal("ritual.Sanguimancy.drill.dead"));
        Rituals.registerRitual("vulcanosFrigiusRitual", 1, 250, new RitualEffectObsidian(), StatCollector.translateToLocal("ritual.Sanguimancy.vulcanos.frigius"));
        Rituals.registerRitual("trashRitual", 1, 50, new RitualEffectTrash(), StatCollector.translateToLocal("ritual.Sanguimancy.trash"));
        Rituals.registerRitual("illuminationRitual", 1, 300, new RitualEffectLighting(), StatCollector.translateToLocal("ritual.Sanguimancy.illumination"));
        Rituals.registerRitual("fellingRitual", 1, 500, new RitualEffectFelling(), StatCollector.translateToLocal("ritual.Sanguimancy.feller"));
        Rituals.registerRitual("placerRitual", 1, 500, new RitualEffectPlacer(), StatCollector.translateToLocal("ritual.Sanguimancy.placer"));
    }

    public static void addToWoSBlacklist() {
        AlchemicalWizardry.wellBlacklist.add(EntityPlayerPointer.class);
        AlchemicalWizardry.wellBlacklist.add(EntityChickenMinion.class);
    }
}
