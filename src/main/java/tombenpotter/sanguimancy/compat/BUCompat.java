package tombenpotter.sanguimancy.compat;

import bloodutils.api.compact.Category;
import bloodutils.api.compact.Entry;
import bloodutils.api.entries.*;
import bloodutils.api.enums.EnumType;
import bloodutils.api.registries.EntryRegistry;
import net.minecraft.util.StatCollector;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.registry.RecipesRegistry;
import tombenpotter.sanguimancy.util.RandomUtils;

import java.util.HashMap;

public class BUCompat {
    public static HashMap<String, Entry> sanguimancyMap = new HashMap<String, Entry>();

    public static Category categorySanguimancy;

    public static Entry playerSacrificers;
    public static Entry soulCorruptionReader;
    public static Entry altarEmitter;
    public static Entry altarDiviner;
    public static Entry soulTransferrer;
    public static Entry corruptionCrystallizer;
    public static Entry drillOfTheDead;
    public static Entry vulcanosFrigius;
    public static Entry greatDeletion;
    public static Entry enlightenment;
    public static Entry timberman;
    public static Entry filler;
    public static Entry sacrificeMagic;

    public static void createCategories() {
        categorySanguimancy = new Category(Sanguimancy.name, RandomUtils.SanguimancyItemStacks.attunnedPlayerSacrificer, EnumType.ITEM);
        EntryRegistry.registerCategories(categorySanguimancy);
    }

    public static void createEntries() {
        playerSacrificers = new Entry(new IEntry[]{new EntryItemText(RandomUtils.SanguimancyItemStacks.unattunedPlayerSacrificer, "sacrificingStones"), new EntryCraftingRecipe(RecipesRegistry.unattunedPlayerSacrificer), new EntryItemText(RandomUtils.SanguimancyItemStacks.attunnedPlayerSacrificer, "sacrificingStones"), new EntryAltarRecipe(RecipesRegistry.attunedPlayerSacrificer), new EntryItemText(RandomUtils.SanguimancyItemStacks.focusedPlayerSacrificer, "sacrificingStones")}, "§9" + StatCollector.translateToLocal("Player Sacrificing Stones"), 1);
        EntryRegistry.registerEntry(categorySanguimancy, sanguimancyMap, playerSacrificers);
        soulCorruptionReader = new Entry(new IEntry[]{new EntryItemText(RandomUtils.SanguimancyItemStacks.corruptionReader, "corruptionReader"), new EntryCraftingRecipe(RecipesRegistry.corruptionReader)}, "§9" + StatCollector.translateToLocal("item.Sanguimancy.soulCorruption.reader.name"), 1);
        EntryRegistry.registerEntry(categorySanguimancy, sanguimancyMap, soulCorruptionReader);

        soulTransferrer = new Entry(new IEntry[]{new EntryItemText(RandomUtils.SanguimancyItemStacks.sacrificeTransferrer, "soulTransferrer"), new EntryCraftingRecipe(RecipesRegistry.sacrificeTransferrer)}, "§3" + StatCollector.translateToLocal("tile.Sanguimancy.sacrificeTransfer.name"), 1);
        EntryRegistry.registerEntry(categorySanguimancy, sanguimancyMap, soulTransferrer);
        altarEmitter = new Entry(new IEntry[]{new EntryItemText(RandomUtils.SanguimancyItemStacks.altarEmitter, "altarEmitter"), new EntryText("altarEmitter"), new EntryCraftingRecipe(RecipesRegistry.altarEmitter)}, "§3" + StatCollector.translateToLocal("tile.Sanguimancy.altarEmitter.name"), 1);
        EntryRegistry.registerEntry(categorySanguimancy, sanguimancyMap, altarEmitter);
        altarDiviner = new Entry(new IEntry[]{new EntryItemText(RandomUtils.SanguimancyItemStacks.altarDiviner, "altarDiviner"), new EntryText("altarDiviner"), new EntryAltarRecipe(RecipesRegistry.altarDiviner)}, "§3" + StatCollector.translateToLocal("tile.Sanguimancy.altarDiviner.name"), 1);
        EntryRegistry.registerEntry(categorySanguimancy, sanguimancyMap, altarDiviner);
        corruptionCrystallizer = new Entry(new IEntry[]{new EntryItemText(RandomUtils.SanguimancyItemStacks.corruptionCrystallizer, "corruptionCrystallizer"), new EntryCraftingRecipe(RecipesRegistry.corruptionCrystallizer), new BUEntryCorruptionRecipe(RecipesRegistry.corruptedDemonShard)}, "§3" + StatCollector.translateToLocal("tile.Sanguimancy.corruptionCrystallizer.name"), 1);
        EntryRegistry.registerEntry(categorySanguimancy, sanguimancyMap, corruptionCrystallizer);

        drillOfTheDead = new Entry(new IEntry[]{new EntryText("drillOfTheDead"), new EntryText("drillOfTheDead")}, "§1Ritual: " + StatCollector.translateToLocal("ritual.Sanguimancy.drill.dead"), 1);
        EntryRegistry.registerEntry(categorySanguimancy, sanguimancyMap, drillOfTheDead);
        vulcanosFrigius = new Entry(new IEntry[]{new EntryText("vulcanosFrigius")}, "§1Ritual: " + StatCollector.translateToLocal("ritual.Sanguimancy.vulcanos.frigius"), 1);
        EntryRegistry.registerEntry(categorySanguimancy, sanguimancyMap, vulcanosFrigius);
        greatDeletion = new Entry(new IEntry[]{new EntryText("greatDeletion")}, "§1Ritual: " + StatCollector.translateToLocal("ritual.Sanguimancy.trash"), 1);
        EntryRegistry.registerEntry(categorySanguimancy, sanguimancyMap, greatDeletion);
        enlightenment = new Entry(new IEntry[]{new EntryText("enlightenment")}, "§1Ritual: " + StatCollector.translateToLocal("ritual.Sanguimancy.illumination"), 1);
        EntryRegistry.registerEntry(categorySanguimancy, sanguimancyMap, enlightenment);
        timberman = new Entry(new IEntry[]{new EntryText("timberman")}, "§1Ritual: " + StatCollector.translateToLocal("ritual.Sanguimancy.feller"), 1);
        EntryRegistry.registerEntry(categorySanguimancy, sanguimancyMap, timberman);
        filler = new Entry(new IEntry[]{new EntryText("filler")}, "§1Ritual: " + StatCollector.translateToLocal("ritual.Sanguimancy.placer"), 1);
        EntryRegistry.registerEntry(categorySanguimancy, sanguimancyMap, filler);

        sacrificeMagic = new Entry(new IEntry[]{new EntryText("sacrificeMagic"), new EntryText("sacrificeMagic"), new EntryText("sacrificeMagic"), new EntryText("sacrificeMagic"), new EntryText("sacrificeMagic"), new EntryText("sacrificeMagic"), new EntryText("sacrificeMagic"), new EntryText("sacrificeMagic"), new EntryText("sacrificeMagic"),
                new EntryImage(Sanguimancy.texturePath + ":textures/screenshots/CompleteCrystallizerMultiblock.png", 854, 480, "sacrificeMagic"), new EntryImage(Sanguimancy.texturePath + ":textures/screenshots/CrystallizerMultiblock.png", 854, 480, "sacrificeMagic"), new EntryImage(Sanguimancy.texturePath + ":textures/screenshots/WaterCrystallizerMultiblock.png", 854, 480, "sacrificeMagic"),
                new EntryImage(Sanguimancy.texturePath + ":textures/screenshots/LavaCrystallizerMultiblock.png", 854, 480, "sacrificeMagic"), new EntryText("sacrificeMagic"), new EntryText("sacrificeMagic"), new EntryText("sacrificeMagic")}, "§4" + StatCollector.translateToLocal("bu.entryName.corruption"), 1);
        EntryRegistry.registerEntry(categorySanguimancy, sanguimancyMap, sacrificeMagic);
    }
}
