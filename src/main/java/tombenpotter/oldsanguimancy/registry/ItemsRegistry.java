package tombenpotter.oldsanguimancy.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import tombenpotter.oldsanguimancy.items.*;
import tombenpotter.oldsanguimancy.items.corrupted.ItemCorruptedAxe;
import tombenpotter.oldsanguimancy.items.corrupted.ItemCorruptedPickaxe;
import tombenpotter.oldsanguimancy.items.corrupted.ItemCorruptedShovel;
import tombenpotter.oldsanguimancy.items.corrupted.ItemCorruptedSword;
import tombenpotter.oldsanguimancy.old.ded.ItemTelepositionSigil;
import tombenpotter.oldsanguimancy.old.ded.ItemTranspositionSigil;
import tombenpotter.sanguimancy.util.RandomUtils;

public class ItemsRegistry {

    public static Item playerSacrificer;
    public static Item soulCorruptionTest;
    public static Item corruptionCatalyst;
    public static Item oreLump;
    public static Item bloodAmulet;
    public static Item wand;
    public static Item chunkClaimer;
    public static Item corruptedSword;
    public static Item corruptedPickaxe;
    public static Item corruptedShovel;
    public static Item corruptedAxe;
    public static Item resource;
    public static Item soulTransporter;
    public static Item telepositionSigil;
    public static Item transpositionSigil;

    public static void registerItems() {
        playerSacrificer = new ItemPlayerSacrificer();
        GameRegistry.registerItem(playerSacrificer, "playerSacrificer");

        soulCorruptionTest = new ItemSoulCorruptionTest();
        GameRegistry.registerItem(soulCorruptionTest, "soulCorruptionTest");

        corruptionCatalyst = new ItemCorruptionCatalyst();
        GameRegistry.registerItem(corruptionCatalyst, "corruptionCatalist");

        oreLump = new ItemOreLump();
        GameRegistry.registerItem(oreLump, "oreLump");

        bloodAmulet = new ItemBloodAmulet();
        GameRegistry.registerItem(bloodAmulet, "bloodAmulet");

        wand = new ItemWand();
        GameRegistry.registerItem(wand, "wand");

        chunkClaimer = new ItemChunkClaimer();
        GameRegistry.registerItem(chunkClaimer, "chunkClaimer");

        corruptedSword = new ItemCorruptedSword(32);
        GameRegistry.registerItem(corruptedSword, "corruptedSword");

        corruptedPickaxe = new ItemCorruptedPickaxe(RandomUtils.corruptedMaterial);
        GameRegistry.registerItem(corruptedPickaxe, "corruptedPickaxe");

        corruptedShovel = new ItemCorruptedShovel(RandomUtils.corruptedMaterial);
        GameRegistry.registerItem(corruptedShovel, "corruptedShovel");

        corruptedAxe = new ItemCorruptedAxe(RandomUtils.corruptedMaterial);
        GameRegistry.registerItem(corruptedAxe, "corruptedAxe");

        resource = new ItemResource();
        GameRegistry.registerItem(resource, "resource");

        soulTransporter = new ItemSoulTransporter();
        GameRegistry.registerItem(soulTransporter, "soulTransporter");

        telepositionSigil = new ItemTelepositionSigil();
        GameRegistry.registerItem(telepositionSigil, "telepositionSigil");

        transpositionSigil = new ItemTranspositionSigil();
        GameRegistry.registerItem(transpositionSigil, "transpositionSigil");
    }
}
