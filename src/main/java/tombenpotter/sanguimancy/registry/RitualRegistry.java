package tombenpotter.sanguimancy.registry;

import WayofTime.alchemicalWizardry.AlchemicalWizardry;
import WayofTime.alchemicalWizardry.api.rituals.Rituals;
import tombenpotter.sanguimancy.entity.EntityChickenMinion;
import tombenpotter.sanguimancy.entity.EntityPlayerPointer;
import tombenpotter.sanguimancy.rituals.RitualEffectDrillOfTheDead;
import tombenpotter.sanguimancy.rituals.RitualEffectLighting;
import tombenpotter.sanguimancy.rituals.RitualEffectObsidian;
import tombenpotter.sanguimancy.rituals.RitualEffectTrash;

public class RitualRegistry {

    public static void registerRituals() {
        Rituals.registerRitual("wellOfTheDeadRitual", 1, 50000, new RitualEffectDrillOfTheDead(), "Drill of the Dead");
        Rituals.registerRitual("vulcanosFrigiusRitual", 1, 250, new RitualEffectObsidian(), "The Vulcanos Frigius");
        Rituals.registerRitual("trashRitual", 1, 50, new RitualEffectTrash(), "The Great Deletion");
        Rituals.registerRitual("illuminationRitual", 1, 300, new RitualEffectLighting(), "The Enlightement");
    }

    public static void addToWoSBlacklist() {
        AlchemicalWizardry.wellBlacklist.add(EntityPlayerPointer.class);
        AlchemicalWizardry.wellBlacklist.add(EntityChickenMinion.class);
    }
}
