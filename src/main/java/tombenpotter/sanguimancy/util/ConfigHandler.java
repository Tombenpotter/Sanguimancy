package tombenpotter.sanguimancy.util;

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
    public static boolean enableQuarry;
    public static boolean enablePortal;
    public static int snDimID;
    public static int addHeartPotionID;
    public static int removeHeartPotionID;
    public static boolean messagesWhenCorruptionEffect;

    public static void init(File file) {
        config = new Configuration(file);
        syncConfig();
    }

    public static void load() {
        config.load();
    }

    public static void syncConfig() {
        config.addCustomCategoryComment(balancing, "Balancing tweaks");
        config.addCustomCategoryComment(features, "Allows disabling of almost all features of the mod.");
        config.addCustomCategoryComment(rituals, "All things pertaining to rituals");

        // Rituals
        enableDrillOfTheDead = config.get(rituals, "enableDrillOfTheDead", true).getBoolean(enableDrillOfTheDead);
        enableVulcanosFrigius = config.get(rituals, "enableVulcanosFrigius", true).getBoolean(enableVulcanosFrigius);
        enableTrash = config.get(rituals, "enableTrash", true).getBoolean(enableTrash);
        enableIllumination = config.get(rituals, "enableIllumination", true).getBoolean(enableIllumination);
        enableFelling = config.get(rituals, "enableFelling", true).getBoolean(enableFelling);
        enablePlacer = config.get(rituals, "enablePlacer", true).getBoolean(enablePlacer);
        enablePump = config.get(rituals, "enablePump", true).getBoolean(enablePump);
        enableQuarry = config.get(rituals, "enableQuarry", true).getBoolean(enableQuarry);
        enablePortal = config.get(rituals, "enablePortal", true).getBoolean(enablePortal);

        //Features
        snDimID = config.get(features, "soulNetworkDimensionID", 42).getInt(snDimID);
        addHeartPotionID = config.get(features, "addHeartPotionID", 100).getInt(addHeartPotionID);
        removeHeartPotionID = config.get(features, "removeHeartPotionID", 101).getInt(removeHeartPotionID);
        messagesWhenCorruptionEffect = config.get(features, "messageWhenCorruptionEffect", true).getBoolean(messagesWhenCorruptionEffect);

        config.save();
    }
}
