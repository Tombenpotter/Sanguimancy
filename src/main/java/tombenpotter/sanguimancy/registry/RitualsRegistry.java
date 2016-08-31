package tombenpotter.sanguimancy.registry;

import WayofTime.bloodmagic.api.registry.RitualRegistry;
import tombenpotter.sanguimancy.rituals.RitualDrillOfTheDead;
import tombenpotter.sanguimancy.rituals.RitualLighting;
import tombenpotter.sanguimancy.rituals.RitualTrash;
import tombenpotter.sanguimancy.rituals.RitualVulcanosFrigius;
import tombenpotter.sanguimancy.util.ConfigHandler;

public class RitualsRegistry {

    public static void registerRituals() {
        RitualRegistry.registerRitual(new RitualDrillOfTheDead(), ConfigHandler.enableDrillOfTheDead);
        RitualRegistry.registerRitual(new RitualVulcanosFrigius(), ConfigHandler.enableVulcanosFrigius);
        RitualRegistry.registerRitual(new RitualTrash(), ConfigHandler.enableTrash);
        RitualRegistry.registerRitual(new RitualLighting(), ConfigHandler.enableIllumination);
    }
}
