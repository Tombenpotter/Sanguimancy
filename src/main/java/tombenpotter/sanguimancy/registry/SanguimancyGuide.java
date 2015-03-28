package tombenpotter.sanguimancy.registry;


import WayofTime.alchemicalWizardry.ModBlocks;
import amerifrance.guideapi.api.GuideRegistry;
import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.abstraction.IPage;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.api.util.PageHelper;
import amerifrance.guideapi.categories.CategoryItemStack;
import amerifrance.guideapi.entries.EntryUniText;
import amerifrance.guideapi.pages.PageIRecipe;
import amerifrance.guideapi.pages.PageUnlocImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.api.guide.PageAltarRecipe;
import tombenpotter.sanguimancy.api.guide.PageCorruptionRecipe;
import tombenpotter.sanguimancy.api.guide.PageOrbRecipe;
import tombenpotter.sanguimancy.recipes.RecipeCorruptedInfusion;
import tombenpotter.sanguimancy.util.ConfigHandler;
import tombenpotter.sanguimancy.util.SanguimancyItemStacks;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SanguimancyGuide {

    public static Book sanguimancyGuide;
    public static List<CategoryAbstract> categories = new ArrayList<CategoryAbstract>();

    public static void registerGuide() {
        createLoreEntries();
        createItemEntries();
        createRitualEntries();
        createBlockEntries();
        sanguimancyGuide = new Book(categories, "guide.Sanguimancy.book.title", "guide.Sanguimancy.welcomeMessage", "guide.Sanguimancy.book.name", new Color(190, 10, 0));
        GuideRegistry.registerBook(sanguimancyGuide);
    }

    public static void createLoreEntries() {
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        List<EntryAbstract> entries = new ArrayList<EntryAbstract>();

        ArrayList<IPage> sacrificeMagicPages = new ArrayList<IPage>();
        sacrificeMagicPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.sacrificeMagic"), fontRenderer));
        entries.add(new EntryUniText(sacrificeMagicPages, "guide.Sanguimancy.entryName.sacrificeMagic"));

        ArrayList<IPage> soulCorruptionPages = new ArrayList<IPage>();
        soulCorruptionPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.corruption"), fontRenderer));
        entries.add(new EntryUniText(soulCorruptionPages, "guide.Sanguimancy.entryName.corruption"));

        ArrayList<IPage> protectionPages = new ArrayList<IPage>();
        protectionPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.protection.1"), fontRenderer));
        protectionPages.add(new PageUnlocImage("protection.picture.1", new ResourceLocation(Sanguimancy.texturePath + ":textures/screenshots/CompleteCrystallizerMultiblock.png"), true));
        protectionPages.add(new PageUnlocImage("protection.picture.2", new ResourceLocation(Sanguimancy.texturePath + ":textures/screenshots/CrystallizerMultiblock.png"), true));
        protectionPages.add(new PageUnlocImage("protection.picture.3", new ResourceLocation(Sanguimancy.texturePath + ":textures/screenshots/WaterCrystallizerMultiblock.png"), true));
        protectionPages.add(new PageUnlocImage("protection.picture.4", new ResourceLocation(Sanguimancy.texturePath + ":textures/screenshots/LavaCrystallizerMultiblock.png"), true));
        protectionPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.protection.2"), fontRenderer));
        entries.add(new EntryUniText(protectionPages, "guide.Sanguimancy.entryName.protection"));

        ArrayList<IPage> corruptionApplicationsPages = new ArrayList<IPage>();
        corruptionApplicationsPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.applications"), fontRenderer));
        entries.add(new EntryUniText(corruptionApplicationsPages, "guide.Sanguimancy.entryName.apllications"));

        categories.add(new CategoryItemStack(entries, "guide.Sanguimancy.category.lore", SanguimancyItemStacks.attunnedPlayerSacrificer));
    }

    public static void createItemEntries() {
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        List<EntryAbstract> entries = new ArrayList<EntryAbstract>();

        ArrayList<IPage> bloodAmuletPages = new ArrayList<IPage>();
        bloodAmuletPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.bloodAmulet"), fontRenderer, SanguimancyItemStacks.bloodAmulet));
        bloodAmuletPages.add(new PageOrbRecipe(RecipesRegistry.bloodAmulet));
        entries.add(new EntryUniText(bloodAmuletPages, "item.Sanguimancy.bloodAmulet.name"));

        ArrayList<IPage> chunkClaimerPages = new ArrayList<IPage>();
        chunkClaimerPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.chunkClaimer"), fontRenderer, SanguimancyItemStacks.chunkClaimer));
        chunkClaimerPages.add(new PageIRecipe(RecipesRegistry.chunkClaimer));
        entries.add(new EntryUniText(chunkClaimerPages, "item.Sanguimancy.chunkClaimer.name"));

        ArrayList<IPage> craftingItemsPages = new ArrayList<IPage>();
        craftingItemsPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.craftingItems"), fontRenderer));
        craftingItemsPages.add(new PageCorruptionRecipe(RecipesRegistry.corruptedDemonShard));
        craftingItemsPages.add(new PageAltarRecipe(RecipesRegistry.imbuedStick));
        craftingItemsPages.add(new PageIRecipe(RecipesRegistry.corruptedMineral));
        entries.add(new EntryUniText(craftingItemsPages, "guide.Sanguimancy.entryName.craftingItems"));

        ArrayList<IPage> corruptionCatalystPages = new ArrayList<IPage>();
        corruptionCatalystPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.corruptionCatalyst.1"), fontRenderer, SanguimancyItemStacks.corruptionCatalist));
        corruptionCatalystPages.add(new PageAltarRecipe(RecipesRegistry.corruptionCatalyst));
        corruptionCatalystPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.corruptionCatalyst.2"), fontRenderer));
        for (RecipeCorruptedInfusion r : RecipeCorruptedInfusion.getAllRecipes()) {
            corruptionCatalystPages.add(new PageCorruptionRecipe(r));
        }
        entries.add(new EntryUniText(corruptionCatalystPages, "item.Sanguimancy.corruptionCatalist.name"));

        ArrayList<IPage> corruptionReaderPages = new ArrayList<IPage>();
        corruptionReaderPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.corruptionReader"), fontRenderer, SanguimancyItemStacks.corruptionReader));
        corruptionReaderPages.add(new PageIRecipe(RecipesRegistry.corruptionReader));
        entries.add(new EntryUniText(corruptionReaderPages, "item.Sanguimancy.soulCorruption.reader.name"));

        ArrayList<IPage> oreLumpPages = new ArrayList<IPage>();
        oreLumpPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.oreLump"), fontRenderer, SanguimancyItemStacks.oreLump));
        for (RecipeCorruptedInfusion r : RecipesRegistry.oreLumpRecipes) {
            oreLumpPages.add(new PageCorruptionRecipe(r));
        }
        //for (RecipeBloodCleanser r : RecipesRegistry.oreLumpCleansing) {
        //    oreLumpPages.add(new EntryBloodCleanserRecipe(r));
        // }
        entries.add(new EntryUniText(oreLumpPages, "item.Sanguimancy.oreLump.name"));

        ArrayList<IPage> playerSacrificingStonesPages = new ArrayList<IPage>();
        playerSacrificingStonesPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.sacrificingStones.1"), fontRenderer, SanguimancyItemStacks.unattunedPlayerSacrificer));
        playerSacrificingStonesPages.add(new PageOrbRecipe(RecipesRegistry.unattunedPlayerSacrificer));
        playerSacrificingStonesPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.sacrificingStones.2"), fontRenderer, SanguimancyItemStacks.attunnedPlayerSacrificer));
        playerSacrificingStonesPages.add(new PageAltarRecipe(RecipesRegistry.attunedPlayerSacrificer));
        playerSacrificingStonesPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.sacrificingStones.3"), fontRenderer, SanguimancyItemStacks.focusedPlayerSacrificer));
        entries.add(new EntryUniText(playerSacrificingStonesPages, "Player Sacrificing Stones"));

        ArrayList<IPage> wandPages = new ArrayList<IPage>();
        wandPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.wand"), fontRenderer, SanguimancyItemStacks.wand));
        wandPages.add(new PageIRecipe(RecipesRegistry.wand));
        entries.add(new EntryUniText(wandPages, "item.Sanguimancy.spellWand.name"));

        ArrayList<IPage> corruptedAxePages = new ArrayList<IPage>();
        corruptedAxePages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.corruptedAxe"), fontRenderer, SanguimancyItemStacks.corruptedAxe));
        corruptedAxePages.add(new PageIRecipe(RecipesRegistry.corruptedAxe));
        entries.add(new EntryUniText(corruptedAxePages, "item.Sanguimancy.corruptedAxe.name"));

        ArrayList<IPage> corruptedPickaxePages = new ArrayList<IPage>();
        corruptedPickaxePages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.corruptedPickaxe"), fontRenderer, SanguimancyItemStacks.corruptedPickaxe));
        corruptedPickaxePages.add(new PageIRecipe(RecipesRegistry.corruptedPickaxe));
        entries.add(new EntryUniText(corruptedPickaxePages, "item.Sanguimancy.corruptedPickaxe.name"));

        ArrayList<IPage> corruptedShovelPages = new ArrayList<IPage>();
        corruptedShovelPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.corruptedShovel"), fontRenderer, SanguimancyItemStacks.corruptedShovel));
        corruptedShovelPages.add(new PageIRecipe(RecipesRegistry.corruptedShovel));
        entries.add(new EntryUniText(corruptedShovelPages, "item.Sanguimancy.corruptedShovel.name"));

        ArrayList<IPage> corruptedSwordPages = new ArrayList<IPage>();
        corruptedSwordPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.corruptedSword"), fontRenderer, SanguimancyItemStacks.corruptedSword));
        corruptedSwordPages.add(new PageIRecipe(RecipesRegistry.corruptedSword));
        entries.add(new EntryUniText(corruptedSwordPages, "item.Sanguimancy.corruptedSword.name"));

        ArrayList<IPage> soulTranporterPages = new ArrayList<IPage>();
        soulTranporterPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.soulTransporter"), fontRenderer, SanguimancyItemStacks.soulTransporter));
        soulTranporterPages.add(new PageOrbRecipe(RecipesRegistry.soulTransporter));
        entries.add(new EntryUniText(soulTranporterPages, "item.Sanguimancy.soulTransporter.name"));

        ArrayList<IPage> telepositionSigilIPages = new ArrayList<IPage>();
        telepositionSigilIPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.telepositionSigil"), fontRenderer, SanguimancyItemStacks.telepositionSigil));
        telepositionSigilIPages.add(new PageOrbRecipe(RecipesRegistry.telepositionSigil));
        entries.add(new EntryUniText(telepositionSigilIPages, "item.Sanguimancy.telepositionSigil.name"));

        ArrayList<IPage> transpositionSigilPages = new ArrayList<IPage>();
        transpositionSigilPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.transpositionSigil"), fontRenderer, SanguimancyItemStacks.transpositionSigil));
        transpositionSigilPages.add(new PageOrbRecipe(RecipesRegistry.transpositionSigil));
        entries.add(new EntryUniText(transpositionSigilPages, "item.Sanguimancy.transpositionSigil.name"));

        categories.add(new CategoryItemStack(entries, "guide.Sanguimancy.category.items", SanguimancyItemStacks.corruptedSword));
    }

    public static void createRitualEntries() {
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        List<EntryAbstract> entries = new ArrayList<EntryAbstract>();

        if (ConfigHandler.enableDrillOfTheDead) {
            ArrayList<IPage> drillOfTheDeadPages = new ArrayList<IPage>();
            drillOfTheDeadPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.drillOfTheDead"), fontRenderer));
            entries.add(new EntryUniText(drillOfTheDeadPages, "ritual.Sanguimancy.drill.dead"));
        }

        if (ConfigHandler.enableFelling) {
            ArrayList<IPage> timbermanPages = new ArrayList<IPage>();
            timbermanPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.timberman"), fontRenderer));
            entries.add(new EntryUniText(timbermanPages, "ritual.Sanguimancy.feller"));
            ;
        }

        if (ConfigHandler.enableIllumination) {
            ArrayList<IPage> enlightenmentPages = new ArrayList<IPage>();
            enlightenmentPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.enlightenment"), fontRenderer));
            entries.add(new EntryUniText(enlightenmentPages, "ritual.Sanguimancy.illumination"));
        }

        if (ConfigHandler.enableVulcanosFrigius) {
            ArrayList<IPage> vulcanosFrigiusPages = new ArrayList<IPage>();
            vulcanosFrigiusPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.vulcanosFrigius"), fontRenderer));
            entries.add(new EntryUniText(vulcanosFrigiusPages, "ritual.Sanguimancy.vulcanos.frigius"));
        }

        if (ConfigHandler.enablePlacer) {
            ArrayList<IPage> fillerPages = new ArrayList<IPage>();
            fillerPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.filler"), fontRenderer));
            entries.add(new EntryUniText(fillerPages, "ritual.Sanguimancy.placer"));
        }

        if (ConfigHandler.enablePortal) {
            ArrayList<IPage> portalPages = new ArrayList<IPage>();
            portalPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.portal.1"), fontRenderer));
            portalPages.add(new PageUnlocImage("guide.Sanguimancy.entry.portal.picture", new ResourceLocation(Sanguimancy.texturePath + ":textures/screenshots/PortalExample.png"), true));
            portalPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.portal.2"), fontRenderer));
            entries.add(new EntryUniText(portalPages, "ritual.Sanguimancy.portal"));
        }

        if (ConfigHandler.enableTrash) {
            ArrayList<IPage> greatDeletionPages = new ArrayList<IPage>();
            greatDeletionPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.greatDeletion"), fontRenderer));
            entries.add(new EntryUniText(greatDeletionPages, "ritual.Sanguimancy.trash"));
        }

        categories.add(new CategoryItemStack(entries, "guide.Sanguimancy.category.rituals", new ItemStack(ModBlocks.blockMasterStone)));
    }

    public static void createBlockEntries() {
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        List<EntryAbstract> entries = new ArrayList<EntryAbstract>();

        ArrayList<IPage> altarEmitterPages = new ArrayList<IPage>();
        altarEmitterPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.altarEmitter"), fontRenderer, SanguimancyItemStacks.altarEmitter));
        altarEmitterPages.add(new PageIRecipe(RecipesRegistry.altarEmitter));
        entries.add(new EntryUniText(altarEmitterPages, "tile.Sanguimancy.altarEmitter.name"));

        ArrayList<IPage> altarDivinerPages = new ArrayList<IPage>();
        altarDivinerPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.altarDiviner"), fontRenderer, SanguimancyItemStacks.altarDiviner));
        altarDivinerPages.add(new PageAltarRecipe(RecipesRegistry.altarDiviner));
        entries.add(new EntryUniText(altarDivinerPages, "tile.Sanguimancy.altarDiviner.name"));

        ArrayList<IPage> altarManipulatorPages = new ArrayList<IPage>();
        altarManipulatorPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.altarManipulator"), fontRenderer, SanguimancyItemStacks.altarManipulator));
        altarManipulatorPages.add(new PageOrbRecipe(RecipesRegistry.altarManipulator));
        altarManipulatorPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.sanguineShifter"), fontRenderer, SanguimancyItemStacks.sanguineShifter));
        altarManipulatorPages.add(new PageOrbRecipe(RecipesRegistry.sanguineShifter));
        entries.add(new EntryUniText(altarManipulatorPages, "tile.Sanguimancy.altarManipulator.name"));

        ArrayList<IPage> bloodInterfacePages = new ArrayList<IPage>();
        bloodInterfacePages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.bloodInterface"), fontRenderer, SanguimancyItemStacks.bloodInterface));
        bloodInterfacePages.add(new PageIRecipe(RecipesRegistry.bloodInterface));
        entries.add(new EntryUniText(bloodInterfacePages, "tile.Sanguimancy.interface.name"));

        ArrayList<IPage> decorativeBlocksPages = new ArrayList<IPage>();
        decorativeBlocksPages.add(new PageIRecipe(RecipesRegistry.bloodstoneSlab));
        decorativeBlocksPages.add(new PageIRecipe(RecipesRegistry.largeBloodstoneSlab));
        decorativeBlocksPages.add(new PageIRecipe(RecipesRegistry.bloodstoneStairs));
        decorativeBlocksPages.add(new PageIRecipe(RecipesRegistry.largeBloodstoneStairs));
        entries.add(new EntryUniText(decorativeBlocksPages, "guide.Sanguimancy.entryName.decorativeBlocks"));

        ArrayList<IPage> bloodTankPages = new ArrayList<IPage>();
        bloodTankPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.bloodTank"), fontRenderer, SanguimancyItemStacks.bloodTank));
        bloodTankPages.add(new PageOrbRecipe(RecipesRegistry.bloodTank));
        entries.add(new EntryUniText(bloodTankPages, "tile.Sanguimancy.bloodTank.name"));

        ArrayList<IPage> corruptionCrystallizerPages = new ArrayList<IPage>();
        corruptionCrystallizerPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.corruptionCrystallizer"), fontRenderer, SanguimancyItemStacks.corruptionCrystallizer));
        corruptionCrystallizerPages.add(new PageOrbRecipe(RecipesRegistry.corruptionCrystallizer));
        entries.add(new EntryUniText(corruptionCrystallizerPages, "tile.Sanguimancy.corruptionCrystallizer.name"));

        ArrayList<IPage> manifestationsPages = new ArrayList<IPage>();
        manifestationsPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.itemManifestation"), fontRenderer, SanguimancyItemStacks.boundItem));
        manifestationsPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.ritualManifestation"), fontRenderer, SanguimancyItemStacks.ritualRepresentation));
        manifestationsPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.etherealManifestation"), fontRenderer, SanguimancyItemStacks.etherealManifestation));
        manifestationsPages.add(new PageAltarRecipe(RecipesRegistry.etherealManifestation));
        manifestationsPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.soulBranch"), fontRenderer, SanguimancyItemStacks.simpleBranch));
        manifestationsPages.add(new PageIRecipe(RecipesRegistry.simpleBranch));
        manifestationsPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.simpleKnot"), fontRenderer, SanguimancyItemStacks.simpleKnot));
        manifestationsPages.add(new PageIRecipe(RecipesRegistry.simpleKnot));
        manifestationsPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.toggledKnot"), fontRenderer, SanguimancyItemStacks.toggleKnot));
        manifestationsPages.add(new PageIRecipe(RecipesRegistry.toggledKnot));
        entries.add(new EntryUniText(manifestationsPages, "guide.Sanguimancy.entryName.manifestations"));

        ArrayList<IPage> bloodCleanserPages = new ArrayList<IPage>();
        bloodCleanserPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.bloodCleanser"), fontRenderer, SanguimancyItemStacks.lumpCleaner));
        bloodCleanserPages.add(new PageOrbRecipe(RecipesRegistry.lumpCleaner));
        //for (RecipeBloodCleanser r : RecipeBloodCleanser.getAllRecipes()) {
        //    bloodCleanserPages.add(new EntryBloodCleanserRecipe(r));
        //}
        entries.add(new EntryUniText(bloodCleanserPages, "tile.Sanguimancy.lumpCleaner.name"));

        ArrayList<IPage> soulTransferrerPages = new ArrayList<IPage>();
        soulTransferrerPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.soulTransferrer"), fontRenderer, SanguimancyItemStacks.sacrificeTransferrer));
        soulTransferrerPages.add(new PageIRecipe(RecipesRegistry.sacrificeTransferrer));
        entries.add(new EntryUniText(soulTransferrerPages, "tile.Sanguimancy.sacrificeTransfer.name"));

        ArrayList<IPage> etherealBlocksPages = new ArrayList<IPage>();
        etherealBlocksPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.etherealBlock"), fontRenderer, SanguimancyItemStacks.etherealBlock));
        etherealBlocksPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.corruptedEtherealBlock"), fontRenderer, SanguimancyItemStacks.etherealCorruptedBlock));
        etherealBlocksPages.add(new PageCorruptionRecipe(RecipesRegistry.corruptedEtherealBlock));
        etherealBlocksPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.toggledEtherealBlock"), fontRenderer, SanguimancyItemStacks.etherealToggledBlock));
        etherealBlocksPages.add(new PageIRecipe(RecipesRegistry.toggledEtherealBlock));
        etherealBlocksPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.boundEtherealBlocks"), fontRenderer));
        etherealBlocksPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.personalEtherealBlock"), fontRenderer, SanguimancyItemStacks.personalEtherealBlock));
        etherealBlocksPages.add(new PageIRecipe(RecipesRegistry.personalEtherealBlock));
        entries.add(new EntryUniText(etherealBlocksPages, "guide.Sanguimancy.entryName.etherealBlocks"));

        categories.add(new CategoryItemStack(entries, "guide.Sanguimancy.category.blocks", SanguimancyItemStacks.lumpCleaner));
    }
}
