package tombenpotter.sanguimancy.registry;

import WayofTime.alchemicalWizardry.api.rituals.Rituals;
import net.minecraft.util.StatCollector;
import tombenpotter.sanguimancy.rituals.RitualEffectDrillOfTheDead;
import tombenpotter.sanguimancy.rituals.RitualEffectLighting;
import tombenpotter.sanguimancy.rituals.RitualEffectObsidian;
import tombenpotter.sanguimancy.rituals.RitualEffectTrash;

public class RitualRegistry {

    public static void registerRituals() {
        Rituals.registerRitual("wellOfTheDeadRitual", 1, 50000, new RitualEffectDrillOfTheDead(), StatCollector.translateToLocal("ritual.Sanguimancy.drill.dead"));
        Rituals.registerRitual("vulcanosFrigiusRitual", 1, 250, new RitualEffectObsidian(), StatCollector.translateToLocal("ritual.Sanguimancy.vulcanos.frigius"));
        Rituals.registerRitual("trashRitual", 1, 50, new RitualEffectTrash(), StatCollector.translateToLocal("ritual.Sanguimancy.great.deletion"));
        Rituals.registerRitual("illuminationRitual", 1, 300, new RitualEffectLighting(), StatCollector.translateToLocal("ritual.Sanguimancy.enlightenment"));
    }
}
