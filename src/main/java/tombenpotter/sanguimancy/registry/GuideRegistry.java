package tombenpotter.sanguimancy.registry;

import WayofTime.alchemicalWizardry.ModBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.api.bloodutils.api.compact.Category;
import tombenpotter.sanguimancy.api.bloodutils.api.compact.Entry;
import tombenpotter.sanguimancy.api.bloodutils.api.entries.*;
import tombenpotter.sanguimancy.api.bloodutils.api.registries.EntryRegistry;
import tombenpotter.sanguimancy.recipes.RecipeBloodCleanser;
import tombenpotter.sanguimancy.recipes.RecipeCorruptedInfusion;
import tombenpotter.sanguimancy.util.RandomUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class GuideRegistry {
    public static HashMap<String, Entry> lore = new HashMap<String, Entry>();
    public static HashMap<String, Entry> items = new HashMap<String, Entry>();
    public static HashMap<String, Entry> blocks = new HashMap<String, Entry>();
    public static HashMap<String, Entry> rituals = new HashMap<String, Entry>();

    public static Category categorySanguimancyLore, categorySanguimancyItems, categorySanguimancyBlocks, categorySanguimancyRituals;

    public static Entry playerSacrificers, soulCorruptionReader, bloodAmulet, chunkClaimer, corruptedDemonShard, corruptionCatalyst, oreLump, wand, corruptedAxe, corruptedPickaxe, corruptedShovel, corruptedSword;
    public static Entry altarEmitter, altarDiviner, soulTransferrer, corruptionCrystallizer;
    public static Entry drillOfTheDead, vulcanosFrigius, greatDeletion, enlightenment, timberman, filler;
    public static Entry sacrificeMagic, soulCorruption, soulProtection, corruptionApplications;

    public static void createCategories() {
        categorySanguimancyLore = new Category(StatCollector.translateToLocal("guide.Sanguimancy.category.lore"), RandomUtils.SanguimancyItemStacks.attunnedPlayerSacrificer);
        EntryRegistry.registerCategories(categorySanguimancyLore);
        categorySanguimancyItems = new Category(StatCollector.translateToLocal("guide.Sanguimancy.category.items"), RandomUtils.SanguimancyItemStacks.corruptedSword);
        EntryRegistry.registerCategories(categorySanguimancyItems);
        categorySanguimancyBlocks = new Category(StatCollector.translateToLocal("guide.Sanguimancy.category.blocks"), RandomUtils.SanguimancyItemStacks.lumpCleaner);
        EntryRegistry.registerCategories(categorySanguimancyBlocks);
        categorySanguimancyRituals = new Category(StatCollector.translateToLocal("guide.Sanguimancy.category.rituals"), new ItemStack(ModBlocks.blockMasterStone));
        EntryRegistry.registerCategories(categorySanguimancyRituals);
    }

    public static void createEntries() {
        createLoreEntries();
        createItemEntries();
        createBlockEntries();
        createRitualEntries();
    }

    public static void createItemEntries() {
        ArrayList<IEntry> bloodAmuletEntries = new ArrayList<IEntry>();
        bloodAmuletEntries.addAll(entriesForLongText("guide.Sanguimancy.entry.bloodAmulet", RandomUtils.SanguimancyItemStacks.bloodAmulet));
        bloodAmuletEntries.add(new EntryCraftingRecipe(RecipesRegistry.bloodAmulet));
        bloodAmulet = new Entry(bloodAmuletEntries, "\u00A79" + StatCollector.translateToLocal("item.Sanguimancy.bloodAmulet.name"), 1);
        EntryRegistry.registerEntry(categorySanguimancyItems, items, bloodAmulet);

        ArrayList<IEntry> chunkClaimerEntries = new ArrayList<IEntry>();
        chunkClaimerEntries.addAll(entriesForLongText("guide.Sanguimancy.entry.chunkClaimer", RandomUtils.SanguimancyItemStacks.chunkClaimer));
        chunkClaimerEntries.add(new EntryCraftingRecipe(RecipesRegistry.chunkClaimer));
        chunkClaimer = new Entry(chunkClaimerEntries, "\u00A79" + StatCollector.translateToLocal("item.Sanguimancy.chunkClaimer.name"), 1);
        EntryRegistry.registerEntry(categorySanguimancyItems, items, chunkClaimer);

        ArrayList<IEntry> corruptedDemonShardEntries = new ArrayList<IEntry>();
        corruptedDemonShardEntries.addAll(entriesForLongText("guide.Sanguimancy.entry.corruptedDemonShard", RandomUtils.SanguimancyItemStacks.corruptedDemonShard));
        corruptedDemonShardEntries.add(new EntryCorruptionRecipe(RecipesRegistry.corruptedDemonShard));
        corruptedDemonShard = new Entry(corruptedDemonShardEntries, "\u00A79" + StatCollector.translateToLocal("item.Sanguimancy.corruptedDemonShard.name"), 1);
        EntryRegistry.registerEntry(categorySanguimancyItems, items, corruptedDemonShard);

        ArrayList<IEntry> corruptionCatalystEntries = new ArrayList<IEntry>();
        corruptionCatalystEntries.addAll(entriesForLongText("guide.Sanguimancy.entry.corruptionCatalyst.1", RandomUtils.SanguimancyItemStacks.corruptionCatalist));
        corruptionCatalystEntries.add(new EntryAltarRecipe(RecipesRegistry.corruptionCatalyst));
        corruptionCatalystEntries.addAll(entriesForLongText("guide.Sanguimancy.entry.corruptionCatalyst.2"));
        for (RecipeCorruptedInfusion r : RecipeCorruptedInfusion.getAllRecipes()) {
            corruptionCatalystEntries.add(new EntryCorruptionRecipe(r));
        }
        corruptionCatalyst = new Entry(corruptionCatalystEntries, "\u00A79" + StatCollector.translateToLocal("item.Sanguimancy.corruptionCatalist.name"), 1);
        EntryRegistry.registerEntry(categorySanguimancyItems, items, corruptionCatalyst);

        ArrayList<IEntry> corruptionReaderEntries = new ArrayList<IEntry>();
        corruptionReaderEntries.addAll(entriesForLongText("guide.Sanguimancy.entry.corruptionReader", RandomUtils.SanguimancyItemStacks.corruptionReader));
        corruptionReaderEntries.add(new EntryCraftingRecipe(RecipesRegistry.corruptionReader));
        soulCorruptionReader = new Entry(corruptionReaderEntries, "\u00A79" + StatCollector.translateToLocal("item.Sanguimancy.soulCorruption.reader.name"), 1);
        EntryRegistry.registerEntry(categorySanguimancyItems, items, soulCorruptionReader);

        ArrayList<IEntry> oreLumpEntries = new ArrayList<IEntry>();
        oreLumpEntries.addAll(entriesForLongText("guide.Sanguimancy.entry.oreLump", RandomUtils.SanguimancyItemStacks.oreLump));
        for (RecipeCorruptedInfusion r : RecipesRegistry.oreLumpRecipes) {
            oreLumpEntries.add(new EntryCorruptionRecipe(r));
        }
        for (RecipeBloodCleanser r : RecipesRegistry.oreLumpCleansing) {
            oreLumpEntries.add(new EntryBloodCleanserRecipe(r));
        }
        oreLump = new Entry(oreLumpEntries, "\u00A79" + StatCollector.translateToLocal("item.Sanguimancy.oreLump.name"), 1);
        EntryRegistry.registerEntry(categorySanguimancyItems, items, oreLump);

        ArrayList<IEntry> playerSacrificingStonesEntries = new ArrayList<IEntry>();
        playerSacrificingStonesEntries.addAll(entriesForLongText("guide.Sanguimancy.entry.sacrificingStones.1", RandomUtils.SanguimancyItemStacks.unattunedPlayerSacrificer));
        playerSacrificingStonesEntries.add(new EntryCraftingRecipe(RecipesRegistry.unattunedPlayerSacrificer));
        playerSacrificingStonesEntries.addAll(entriesForLongText("guide.Sanguimancy.entry.sacrificingStones.2", RandomUtils.SanguimancyItemStacks.attunnedPlayerSacrificer));
        playerSacrificingStonesEntries.add(new EntryAltarRecipe(RecipesRegistry.attunedPlayerSacrificer));
        playerSacrificingStonesEntries.addAll(entriesForLongText("guide.Sanguimancy.entry.sacrificingStones.3", RandomUtils.SanguimancyItemStacks.focusedPlayerSacrificer));
        playerSacrificers = new Entry(playerSacrificingStonesEntries, "\u00A79" + StatCollector.translateToLocal("Player Sacrificing Stones"), 1);
        EntryRegistry.registerEntry(categorySanguimancyItems, items, playerSacrificers);

        ArrayList<IEntry> wandEntries = new ArrayList<IEntry>();
        wandEntries.addAll(entriesForLongText("guide.Sanguimancy.entry.wand", RandomUtils.SanguimancyItemStacks.wand));
        wandEntries.add(new EntryCraftingRecipe(RecipesRegistry.wand));
        wand = new Entry(wandEntries, "\u00A79" + StatCollector.translateToLocal("item.Sanguimancy.spellWand.name"), 1);
        EntryRegistry.registerEntry(categorySanguimancyItems, items, wand);

        ArrayList<IEntry> corruptedAxeEntries = new ArrayList<IEntry>();
        corruptedAxeEntries.addAll(entriesForLongText("guide.Sanguimancy.entry.corruptedAxe", RandomUtils.SanguimancyItemStacks.corruptedAxe));
        //TODO; Add a crafting recipe for the axe: corruptedAxeEntries.add(new EntryCraftingRecipe())
        corruptedAxe = new Entry(corruptedAxeEntries, "\u00A79" + StatCollector.translateToLocal("item.Sanguimancy.corruptedAxe.name"), 1);
        EntryRegistry.registerEntry(categorySanguimancyItems, items, corruptedAxe);
    }

    public static void createBlockEntries() {
        soulTransferrer = new Entry(new IEntry[]{new EntryItemText(RandomUtils.SanguimancyItemStacks.sacrificeTransferrer, "soulTransferrer"), new EntryCraftingRecipe(RecipesRegistry.sacrificeTransferrer)}, "\u00A73" + StatCollector.translateToLocal("tile.Sanguimancy.sacrificeTransfer.name"), 1);
        EntryRegistry.registerEntry(categorySanguimancyBlocks, blocks, soulTransferrer);

        altarEmitter = new Entry(new IEntry[]{new EntryItemText(RandomUtils.SanguimancyItemStacks.altarEmitter, "altarEmitter"), new EntryText("altarEmitter"), new EntryCraftingRecipe(RecipesRegistry.altarEmitter)}, "\u00A73" + StatCollector.translateToLocal("tile.Sanguimancy.altarEmitter.name"), 1);
        EntryRegistry.registerEntry(categorySanguimancyBlocks, blocks, altarEmitter);

        altarDiviner = new Entry(new IEntry[]{new EntryItemText(RandomUtils.SanguimancyItemStacks.altarDiviner, "altarDiviner"), new EntryText("altarDiviner"), new EntryAltarRecipe(RecipesRegistry.altarDiviner)}, "\u00A73" + StatCollector.translateToLocal("tile.Sanguimancy.altarDiviner.name"), 1);
        EntryRegistry.registerEntry(categorySanguimancyBlocks, blocks, altarDiviner);

        corruptionCrystallizer = new Entry(new IEntry[]{new EntryItemText(RandomUtils.SanguimancyItemStacks.corruptionCrystallizer, "corruptionCrystallizer"), new EntryCraftingRecipe(RecipesRegistry.corruptionCrystallizer), new EntryCorruptionRecipe(RecipesRegistry.corruptedDemonShard)}, "\u00A73" + StatCollector.translateToLocal("tile.Sanguimancy.corruptionCrystallizer.name"), 1);
        EntryRegistry.registerEntry(categorySanguimancyBlocks, blocks, corruptionCrystallizer);
    }

    public static void createRitualEntries() {
        drillOfTheDead = new Entry(entriesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.drillOfTheDead")), "\u00A71Ritual: " + StatCollector.translateToLocal("ritual.Sanguimancy.drill.dead"), 1);
        EntryRegistry.registerEntry(categorySanguimancyRituals, rituals, drillOfTheDead);

        vulcanosFrigius = new Entry(new IEntry[]{new EntryText("vulcanosFrigius")}, "\u00A71Ritual: " + StatCollector.translateToLocal("ritual.Sanguimancy.vulcanos.frigius"), 1);
        EntryRegistry.registerEntry(categorySanguimancyRituals, rituals, vulcanosFrigius);

        greatDeletion = new Entry(new IEntry[]{new EntryText("greatDeletion")}, "\u00A71Ritual: " + StatCollector.translateToLocal("ritual.Sanguimancy.trash"), 1);
        EntryRegistry.registerEntry(categorySanguimancyRituals, rituals, greatDeletion);

        enlightenment = new Entry(new IEntry[]{new EntryText("enlightenment")}, "\u00A71Ritual: " + StatCollector.translateToLocal("ritual.Sanguimancy.illumination"), 1);
        EntryRegistry.registerEntry(categorySanguimancyRituals, rituals, enlightenment);

        timberman = new Entry(new IEntry[]{new EntryText("timberman")}, "\u00A71Ritual: " + StatCollector.translateToLocal("ritual.Sanguimancy.feller"), 1);
        EntryRegistry.registerEntry(categorySanguimancyRituals, rituals, timberman);

        filler = new Entry(new IEntry[]{new EntryText("filler")}, "\u00A71Ritual: " + StatCollector.translateToLocal("ritual.Sanguimancy.placer"), 1);
        EntryRegistry.registerEntry(categorySanguimancyRituals, rituals, filler);
    }

    public static void createLoreEntries() {
        sacrificeMagic = new Entry(entriesForLongText("guide.Sanguimancy.entry.sacrificeMagic"), "\u00A74" + StatCollector.translateToLocal("guide.Sanguimancy.entryName.sacrificeMagic"), 1);
        EntryRegistry.registerEntry(categorySanguimancyLore, lore, sacrificeMagic);

        soulCorruption = new Entry(entriesForLongText("guide.Sanguimancy.entry.corruption"), "\u00A74" + StatCollector.translateToLocal("guide.Sanguimancy.entryName.corruption"), 1);
        EntryRegistry.registerEntry(categorySanguimancyLore, lore, soulCorruption);

        ArrayList<IEntry> protectionEntries = new ArrayList<IEntry>();
        protectionEntries.addAll(entriesForLongText("guide.Sanguimancy.entry.protection.1"));
        protectionEntries.add(new EntryImage(Sanguimancy.texturePath + ":textures/screenshots/CompleteCrystallizerMultiblock.png", 854, 480, "protection.picture.1"));
        protectionEntries.add(new EntryImage(Sanguimancy.texturePath + ":textures/screenshots/CrystallizerMultiblock.png", 854, 480, "protection.picture.2"));
        protectionEntries.add(new EntryImage(Sanguimancy.texturePath + ":textures/screenshots/WaterCrystallizerMultiblock.png", 854, 480, "protection.picture.3"));
        protectionEntries.add(new EntryImage(Sanguimancy.texturePath + ":textures/screenshots/LavaCrystallizerMultiblock.png", 854, 480, "protection.picture.4"));
        protectionEntries.addAll(entriesForLongText("guide.Sanguimancy.entry.protection.2"));
        soulProtection = new Entry(protectionEntries, "\u00A74" + StatCollector.translateToLocal("guide.Sanguimancy.entryName.protection"), 1);
        EntryRegistry.registerEntry(categorySanguimancyLore, lore, soulProtection);

        corruptionApplications = new Entry(entriesForLongText("guide.Sanguimancy.entry.applications"), "\u00A74" + StatCollector.translateToLocal("guide.Sanguimancy.entryName.apllications"), 1);
        EntryRegistry.registerEntry(categorySanguimancyLore, lore, corruptionApplications);
    }

    public static ArrayList<IEntry> entriesForLongText(String string) {
        ArrayList<String> list = RandomUtils.parseString(StatCollector.translateToLocal(string), 410);
        ArrayList<IEntry> entryTextArrayList = new ArrayList<IEntry>();
        for (String s : list) entryTextArrayList.add(new EntryText(s, false));
        return entryTextArrayList;
    }

    public static ArrayList<IEntry> entriesForLongText(String string, ItemStack stack) {
        ArrayList<String> list = RandomUtils.parseString(StatCollector.translateToLocal(string), 410);
        ArrayList<IEntry> entryTextArrayList = new ArrayList<IEntry>();
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) entryTextArrayList.add(new EntryItemText(stack, list.get(i), false));
            else entryTextArrayList.add(new EntryText(list.get(i), false));
        }
        return entryTextArrayList;
    }
}