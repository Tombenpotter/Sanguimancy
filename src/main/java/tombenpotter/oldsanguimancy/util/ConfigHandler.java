package tombenpotter.oldsanguimancy.util;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigHandler {

    public static Configuration config;

    // Sections to add to the config
    public static String balancing = "balancing";
    public static String features = "features";
    public static String rituals = "rituals";

    // Options in the config
    // Rituals
    public static boolean enableDrillOfTheDead;
    public static boolean enableVulcanosFrigius;
    public static boolean enableTrash;
    public static boolean enableIllumination;
    public static boolean enableFelling;
    public static boolean enablePlacer;
    public static boolean enablePump;
    public static boolean enablePortal;
    public static boolean enableAltarBuilder;
    public static boolean serverMessagesWhenCorruptionEffect;
    public static boolean playerMessageWhenCorruptionEffect;
    public static boolean enableTelepositionBlacklist;
    public static int snDimID;
    public static int addHeartPotionID;
    public static int removeHeartPotionID;
    public static int minimumToolCorruption;
    public static int transpositionSigilCost;
    public static String[] transpositionSigilBlacklist;
    public static boolean addItemsOnFirstLogin;
    public static String[] teleposerBlacklist;
    public static boolean renderSillyAprilFish;
    public static boolean noPlayerDamageforDoD;

    public static void init(File file) {
        config = new Configuration(file);
        syncConfig();
    }

    public static void load() {
        config.load();
    }

    public static void syncConfig() {
        config.addCustomCategoryComment(balancing, "Balancing tweaks");
        config.addCustomCategoryComment(features, "Configuring some mod features");
        config.addCustomCategoryComment(rituals, "All things pertaining to rituals");

        // Rituals
        enableDrillOfTheDead = config.getBoolean("enableDrillOfTheDead", rituals, true, "Enable the Drill of the Dead");
        enableVulcanosFrigius = config.getBoolean("enableVulcanosFrigius", rituals, true, "Enable the Vulcanos Frigius");
        enableTrash = config.getBoolean("enableTrash", rituals, true, "Enable the Great Deletion");
        enableIllumination = config.getBoolean("enableIllumination", rituals, true, "Enable the Enlightenment");
        enableFelling = config.getBoolean("enableFelling", rituals, true, "Enable the Timberman");
        enablePlacer = config.getBoolean("enablePlacer", rituals, true, "Enable the Filler");
        enablePump = config.getBoolean("enablePump", rituals, true, "Enable the Hymn of Syphoning");
        enablePortal = config.getBoolean("enablePortal", rituals, true, "Enable the Gate of the Fold");
        enableAltarBuilder = config.getBoolean("enableAltarBuilder", rituals, true, "Enable the Builder");

        //Features
        snDimID = config.getInt("soulNetworkDimensionID", features, 42, 2, 500, "The ID of the Soul Network Dimension");
        addHeartPotionID = config.getInt("addHeartPotionID", features, 115, 20, 127, "The ID of the Add Heart Potion");
        removeHeartPotionID = config.getInt("removeHeartPotionID", features, 116, 20, 127, "The ID of the Remove  Heart Potion");
        serverMessagesWhenCorruptionEffect = config.getBoolean("serverMessageWhenCorruptionEffect", features, true, "Send a message to the whole server when a corruption effect occurs");
        playerMessageWhenCorruptionEffect = config.getBoolean("playerMessageWhenCorruptionEffect", features, false, "Send a message to the player when a corruption effect occurs");
        addItemsOnFirstLogin = config.getBoolean("addItemsOnFirstLogin", features, true, "Give the player a guide and a Soul Chunk claimer on first login");
        enableTelepositionBlacklist = config.getBoolean("canTeleposeManifestations", features, true, "Allow the teleposing of the SN Manifestations");
        renderSillyAprilFish = config.getBoolean("renderSillyAprilFish", features, true, "Render the Silly Flapping Fish or not");
        noPlayerDamageforDoD = config.getBoolean("noPlayerDamageForDoD", features, true, "Disable Player damage for the Drill of the Dead");

        //Balance
        minimumToolCorruption = config.getInt("minimumCorruptionForTools", balancing, 200, 0, 100000, "The Corruption Level needed to get to the full capabilities of the tools");
        transpositionSigilCost = config.getInt("transpositionSigilCost", balancing, 7500, 0, 100000, "The transposition sigil cost");
        transpositionSigilBlacklist = config.getStringList("transpositionSigilBlacklist", balancing, new String[]{"minecraft:bedrock:0"}, "Blacklist for the transposition sigil.\nSyntax is: modid:name:meta\nmodid = Unique ID of the block's mod.\nname = The registered blockname.\nmeta = Metadata of the block. If none is shown, use 0.");
        teleposerBlacklist = config.getStringList("teleposerBlacklist", balancing, new String[]{"Sanguimancy:BlockItemSNPart:0", "Sanguimancy:BlockRitualRepresentation:0"}, "Blacklist for the teleposer.\nSyntax is: modid:name:meta\nmodid = Unique ID of the block's mod.\nname = The registered blockname.\nmeta = Metadata of the block. If none is shown, use 0.");

        config.save();
    }
}
