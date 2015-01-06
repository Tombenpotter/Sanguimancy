package tombenpotter.sanguimancy.registry;

import WayofTime.alchemicalWizardry.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
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
import java.util.List;

public class GuideRegistry {
    public static HashMap<String, Entry> lore = new HashMap<String, Entry>();
    public static HashMap<String, Entry> items = new HashMap<String, Entry>();
    public static HashMap<String, Entry> blocks = new HashMap<String, Entry>();
    public static HashMap<String, Entry> rituals = new HashMap<String, Entry>();

    public static Category categorySanguimancyLore, categorySanguimancyItems, categorySanguimancyBlocks, categorySanguimancyRituals;

    public static Entry playerSacrificers, soulCorruptionReader, bloodAmulet, chunkClaimer, corruptedDemonShard, corruptionCatalyst, oreLump, wand, corruptedAxe, corruptedPickaxe, corruptedShovel, corruptedSword;
    public static Entry altarEmitter, altarDiviner, soulTransferrer, corruptionCrystallizer, bloodInterface, decorativeBlocks, bloodTank, manifestations, bloodCleanser;
    public static Entry drillOfTheDead, vulcanosFrigius, greatDeletion, enlightenment, timberman, filler, portal, pump;
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

        ArrayList<IEntry> corruptedPickaxeEntries = new ArrayList<IEntry>();
        corruptedPickaxeEntries.addAll(entriesForLongText("guide.Sanguimancy.entry.corruptedPickaxe", RandomUtils.SanguimancyItemStacks.corruptedPickaxe));
        //TODO; Add a crafting recipe for the pickaxe: corruptedPickaxeEntries.add(new EntryCraftingRecipe())
        corruptedPickaxe = new Entry(corruptedPickaxeEntries, "\u00A79" + StatCollector.translateToLocal("item.Sanguimancy.corruptedPickaxe.name"), 1);
        EntryRegistry.registerEntry(categorySanguimancyItems, items, corruptedPickaxe);

        ArrayList<IEntry> corruptedShovelEntries = new ArrayList<IEntry>();
        corruptedShovelEntries.addAll(entriesForLongText("guide.Sanguimancy.entry.corruptedShovel", RandomUtils.SanguimancyItemStacks.corruptedShovel));
        //TODO; Add a crafting recipe for the shovel: corruptedShovelEntries.add(new EntryCraftingRecipe())
        corruptedShovel = new Entry(corruptedShovelEntries, "\u00A79" + StatCollector.translateToLocal("item.Sanguimancy.corruptedShovel.name"), 1);
        EntryRegistry.registerEntry(categorySanguimancyItems, items, corruptedShovel);

        ArrayList<IEntry> corruptedSwordEntries = new ArrayList<IEntry>();
        corruptedSwordEntries.addAll(entriesForLongText("guide.Sanguimancy.entry.corruptedSword", RandomUtils.SanguimancyItemStacks.corruptedSword));
        //TODO; Add a crafting recipe for the Sword: corruptedSwordEntries.add(new EntryCraftingRecipe())
        corruptedSword = new Entry(corruptedSwordEntries, "\u00A79" + StatCollector.translateToLocal("item.Sanguimancy.corruptedSword.name"), 1);
        EntryRegistry.registerEntry(categorySanguimancyItems, items, corruptedSword);
    }

    public static void createBlockEntries() {
        ArrayList<IEntry> altarDivinerEntries = new ArrayList<IEntry>();
        altarDivinerEntries.addAll(entriesForLongText("guide.Sanguimancy.entry.altarDiviner", RandomUtils.SanguimancyItemStacks.altarDiviner));
        altarDivinerEntries.add(new EntryAltarRecipe(RecipesRegistry.altarDiviner));
        altarDiviner = new Entry(altarDivinerEntries, "\u00A73" + StatCollector.translateToLocal("tile.Sanguimancy.altarDiviner.name"), 1);
        EntryRegistry.registerEntry(categorySanguimancyBlocks, blocks, altarDiviner);

        ArrayList<IEntry> altarEmitterEntries = new ArrayList<IEntry>();
        altarEmitterEntries.addAll(entriesForLongText("guide.Sanguimancy.entry.altarEmitter", RandomUtils.SanguimancyItemStacks.altarEmitter));
        altarEmitterEntries.add(new EntryCraftingRecipe(RecipesRegistry.altarEmitter));
        altarEmitter = new Entry(altarEmitterEntries, "\u00A73" + StatCollector.translateToLocal("tile.Sanguimancy.altarEmitter.name"), 1);
        EntryRegistry.registerEntry(categorySanguimancyBlocks, blocks, altarEmitter);

        ArrayList<IEntry> bloodInterfaceEntries = new ArrayList<IEntry>();
        bloodInterfaceEntries.addAll(entriesForLongText("guide.Sanguimancy.entry.bloodInterface", RandomUtils.SanguimancyItemStacks.bloodInterface));
        bloodInterfaceEntries.add(new EntryCraftingRecipe(RecipesRegistry.bloodInterface));
        bloodInterface = new Entry(bloodInterfaceEntries, "\u00A73" + StatCollector.translateToLocal("tile.Sanguimancy.interface.name"), 1);
        EntryRegistry.registerEntry(categorySanguimancyBlocks, blocks, bloodInterface);

        ArrayList<IEntry> decorativeBlocksEntries = new ArrayList<IEntry>();
        decorativeBlocksEntries.add(new EntryCraftingRecipe(RecipesRegistry.bloodstoneSlab));
        decorativeBlocksEntries.add(new EntryCraftingRecipe(RecipesRegistry.largeBloodstoneSlab));
        decorativeBlocksEntries.add(new EntryCraftingRecipe(RecipesRegistry.bloodstoneStairs));
        decorativeBlocksEntries.add(new EntryCraftingRecipe(RecipesRegistry.largeBloodstoneStairs));
        decorativeBlocks = new Entry(decorativeBlocksEntries, "\u00A73" + StatCollector.translateToLocal("guide.Sanguimancy.entryName.decorativeBlocks"), 1);
        EntryRegistry.registerEntry(categorySanguimancyBlocks, blocks, decorativeBlocks);

        ArrayList<IEntry> bloodTankEntries = new ArrayList<IEntry>();
        bloodTankEntries.addAll(entriesForLongText("guide.Sanguimancy.entry.bloodTank", RandomUtils.SanguimancyItemStacks.bloodTank));
        bloodTankEntries.add(new EntryCraftingRecipe(RecipesRegistry.bloodTank));
        bloodTank = new Entry(bloodTankEntries, "\u00A73" + StatCollector.translateToLocal("tile.Sanguimancy.bloodTank.name"), 1);
        EntryRegistry.registerEntry(categorySanguimancyBlocks, blocks, bloodTank);

        ArrayList<IEntry> corruptionCrystallizerEntries = new ArrayList<IEntry>();
        corruptionCrystallizerEntries.addAll(entriesForLongText("guide.Sanguimancy.entry.corruptionCrystallizer", RandomUtils.SanguimancyItemStacks.corruptionCrystallizer));
        corruptionCrystallizerEntries.add(new EntryCraftingRecipe(RecipesRegistry.corruptionCrystallizer));
        corruptionCrystallizer = new Entry(corruptionCrystallizerEntries, "\u00A73" + StatCollector.translateToLocal("tile.Sanguimancy.corruptionCrystallizer.name"), 1);
        EntryRegistry.registerEntry(categorySanguimancyBlocks, blocks, corruptionCrystallizer);

        ArrayList<IEntry> manifestationsEntries = new ArrayList<IEntry>();
        manifestationsEntries.addAll(entriesForLongText("guide.Sanguimancy.entry.itemManifestation", RandomUtils.SanguimancyItemStacks.boundItem));
        manifestationsEntries.addAll(entriesForLongText("guide.Sanguimancy.entry.ritualManifestation", RandomUtils.SanguimancyItemStacks.ritualRepresentation));
        manifestationsEntries.addAll(entriesForLongText("guide.Sanguimancy.entry.soulBranch", RandomUtils.SanguimancyItemStacks.simpleBranch));
        manifestationsEntries.add(new EntryCraftingRecipe(RecipesRegistry.simpleBranch));
        manifestationsEntries.addAll(entriesForLongText("guide.Sanguimancy.entry.simpleKnot", RandomUtils.SanguimancyItemStacks.simpleKnot));
        manifestationsEntries.add(new EntryCraftingRecipe(RecipesRegistry.simpleKnot));
        manifestationsEntries.addAll(entriesForLongText("guide.Sanguimancy.entry.toggledKnot", RandomUtils.SanguimancyItemStacks.toggleKnot));
        manifestationsEntries.add(new EntryCraftingRecipe(RecipesRegistry.toggledKnot));
        manifestations = new Entry(manifestationsEntries, "\u00A73" + StatCollector.translateToLocal("guide.Sanguimancy.entryName.manifestations"), 1);
        EntryRegistry.registerEntry(categorySanguimancyBlocks, blocks, manifestations);

        ArrayList<IEntry> bloodCleanserEntries = new ArrayList<IEntry>();
        bloodCleanserEntries.addAll(entriesForLongText("guide.Sanguimancy.entry.bloodCleanser", RandomUtils.SanguimancyItemStacks.lumpCleaner));
        bloodCleanserEntries.add(new EntryCraftingRecipe(RecipesRegistry.lumpCleaner));
        bloodCleanser = new Entry(bloodCleanserEntries, "\u00A73" + StatCollector.translateToLocal("tile.Sanguimancy.lumpCleaner.name"), 1);
        EntryRegistry.registerEntry(categorySanguimancyBlocks, blocks, bloodCleanser);

        ArrayList<IEntry> soulTransferrerEntries = new ArrayList<IEntry>();
        soulTransferrerEntries.addAll(entriesForLongText("guide.Sanguimancy.entry.soulTransferrer", RandomUtils.SanguimancyItemStacks.sacrificeTransferrer));
        soulTransferrerEntries.add(new EntryCraftingRecipe(RecipesRegistry.sacrificeTransferrer));
        soulTransferrer = new Entry(soulTransferrerEntries, "\u00A73" + StatCollector.translateToLocal("tile.Sanguimancy.sacrificeTransfer.name"), 1);
        EntryRegistry.registerEntry(categorySanguimancyBlocks, blocks, soulTransferrer);
    }

    public static void createRitualEntries() {

        ArrayList<IEntry> drillOfTheDeadEntries = new ArrayList<IEntry>();
        drillOfTheDeadEntries.addAll(entriesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.drillOfTheDead")));
        drillOfTheDead = new Entry(drillOfTheDeadEntries, "\u00A71Ritual: " + StatCollector.translateToLocal("ritual.Sanguimancy.drill.dead"), 1);
        EntryRegistry.registerEntry(categorySanguimancyRituals, rituals, drillOfTheDead);

        ArrayList<IEntry> timbermanEntries = new ArrayList<IEntry>();
        timbermanEntries.addAll(entriesForLongText("guide.Sanguimancy.entry.timberman"));
        timberman = new Entry(timbermanEntries, "\u00A71Ritual: " + StatCollector.translateToLocal("ritual.Sanguimancy.feller"), 1);
        EntryRegistry.registerEntry(categorySanguimancyRituals, rituals, timberman);

        ArrayList<IEntry> enlightenmentEntries = new ArrayList<IEntry>();
        enlightenmentEntries.addAll(entriesForLongText("guide.Sanguimancy.entry.enlightenment"));
        enlightenment = new Entry(enlightenmentEntries, "\u00A71Ritual: " + StatCollector.translateToLocal("ritual.Sanguimancy.illumination"), 1);
        EntryRegistry.registerEntry(categorySanguimancyRituals, rituals, enlightenment);

        ArrayList<IEntry> vulcanosFrigiusEntries = new ArrayList<IEntry>();
        vulcanosFrigiusEntries.addAll(entriesForLongText("guide.Sanguimancy.entry.vulcanosFrigius"));
        vulcanosFrigius = new Entry(vulcanosFrigiusEntries, "\u00A71Ritual: " + StatCollector.translateToLocal("ritual.Sanguimancy.vulcanos.frigius"), 1);
        EntryRegistry.registerEntry(categorySanguimancyRituals, rituals, vulcanosFrigius);

        ArrayList<IEntry> fillerEntries = new ArrayList<IEntry>();
        fillerEntries.addAll(entriesForLongText("guide.Sanguimancy.entry.filler"));
        filler = new Entry(fillerEntries, "\u00A71Ritual: " + StatCollector.translateToLocal("ritual.Sanguimancy.placer"), 1);
        EntryRegistry.registerEntry(categorySanguimancyRituals, rituals, filler);

        ArrayList<IEntry> portalEntries = new ArrayList<IEntry>();
        portalEntries.addAll(entriesForLongText("guide.Sanguimancy.entry.portal.1"));
        portalEntries.add(new EntryImage(Sanguimancy.texturePath + ":textures/screenshots/PortalExample.png", 854, 480, "portal.picture"));
        portalEntries.addAll(entriesForLongText("guide.Sanguimancy.entry.portal.2"));
        portal = new Entry(portalEntries, "\u00A71Ritual: " + StatCollector.translateToLocal("ritual.Sanguimancy.portal"), 1);
        EntryRegistry.registerEntry(categorySanguimancyRituals, rituals, portal);

        ArrayList<IEntry> greatDeletionEntries = new ArrayList<IEntry>();
        greatDeletionEntries.addAll(entriesForLongText("guide.Sanguimancy.entry.greatDeletion"));
        greatDeletion = new Entry(greatDeletionEntries, "\u00A71Ritual: " + StatCollector.translateToLocal("ritual.Sanguimancy.trash"), 1);
        EntryRegistry.registerEntry(categorySanguimancyRituals, rituals, greatDeletion);
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
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        List<String> list = fontRenderer.listFormattedStringToWidth(StatCollector.translateToLocal(string), 2100);
        ArrayList<IEntry> entryTextArrayList = new ArrayList<IEntry>();
        for (String s : list) entryTextArrayList.add(new EntryText(s, false));
        return entryTextArrayList;
    }

    public static ArrayList<IEntry> entriesForLongText(String string, ItemStack stack) {
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        List<String> list = fontRenderer.listFormattedStringToWidth(StatCollector.translateToLocal(string), 2100);
        ArrayList<IEntry> entryTextArrayList = new ArrayList<IEntry>();
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) entryTextArrayList.add(new EntryItemText(stack, list.get(i), false));
            else entryTextArrayList.add(new EntryText(list.get(i), false));
        }
        return entryTextArrayList;
    }
}