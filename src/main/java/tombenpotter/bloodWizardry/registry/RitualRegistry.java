package tombenpotter.bloodWizardry.registry;

import WayofTime.alchemicalWizardry.api.rituals.Rituals;
import tombenpotter.bloodWizardry.rituals.RitualEffectDrillOfTheDead;
import tombenpotter.bloodWizardry.rituals.RitualEffectLighting;
import tombenpotter.bloodWizardry.rituals.RitualEffectObsidian;
import tombenpotter.bloodWizardry.rituals.RitualEffectTrash;

public class RitualRegistry {

    public static void registerRituals() {
        Rituals.registerRitual("wellOfTheDeadRitual", 1, 50000, new RitualEffectDrillOfTheDead(), "Drill of the Dead");
        Rituals.registerRitual("vulcanosFrigiusRitual", 1, 250, new RitualEffectObsidian(), "The Vulcanos Frigius");
        Rituals.registerRitual("trashRitual", 1, 50, new RitualEffectTrash(), "The Great Deletion");
        Rituals.registerRitual("illuminationRitual", 1, 300, new RitualEffectLighting(), "The Enlightement");
    }
}
