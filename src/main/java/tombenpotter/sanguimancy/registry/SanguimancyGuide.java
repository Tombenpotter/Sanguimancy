package tombenpotter.sanguimancy.registry;

import WayofTime.bloodmagic.compat.guideapi.page.PageAltarRecipe;
import WayofTime.bloodmagic.core.RegistrarBloodMagicBlocks;
import WayofTime.bloodmagic.registry.ModItems;
import amerifrance.guideapi.api.IGuideBook;
import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.BookBinder;
import amerifrance.guideapi.api.impl.Entry;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.PageHelper;
import amerifrance.guideapi.category.CategoryItemStack;
import amerifrance.guideapi.entry.EntryItemStack;
import amerifrance.guideapi.page.PageIRecipe;
import amerifrance.guideapi.page.PageImage;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.api.guide.PageCorruptionRecipe;
import tombenpotter.sanguimancy.recipes.RecipeCorruptedInfusion;
import tombenpotter.sanguimancy.util.ConfigHandler;
import tombenpotter.sanguimancy.util.SanguimancyItemStacks;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class SanguimancyGuide implements IGuideBook {
    public static Book sanguimancyGuide;
    public static List<CategoryAbstract> categories = new ArrayList<CategoryAbstract>();

    @Nullable
    @Override
	public Book buildBook() {
		createLoreEntries();
        createItemEntries();
        createRitualEntries();
        createBlockEntries();

        sanguimancyGuide = new Book();
        sanguimancyGuide.setTitle(I18n.format("guide.Sanguimancy.book.title"));
        sanguimancyGuide.setDisplayName(I18n.format("guide.Sanguimancy.book.name"));
        sanguimancyGuide.setAuthor(Sanguimancy.name);
        sanguimancyGuide.setColor(new Color(190, 10, 0));
        sanguimancyGuide.setWelcomeMessage(I18n.format("guide.Sanguimancy.welcomeMessage"));
        sanguimancyGuide.setCategoryList(categories);
        sanguimancyGuide.setRegistryName(new ResourceLocation(Sanguimancy.modid, "guide_book"));
        
        return sanguimancyGuide;
	}
    
    @Override
    public IRecipe getRecipe(@Nonnull ItemStack bookStack) {
//    	Mod item was weakBloodOrb
    	return new ShapedOreRecipe(null, bookStack, "X", "Y", "O", 'X', "dyeBlack", 'O', RegistrarBloodMagicBlocks.BLOOD_LIGHT, 'Y', Items.WRITABLE_BOOK);
    }
    
    public static void createLoreEntries() {
        List<IPage> sacrificeMagicPages = Lists.newArrayList();
        List<IPage> soulCorruptionPages = Lists.newArrayList();
        List<IPage> protectionPages = Lists.newArrayList();
        List<IPage> corruptionApplicationsPages = Lists.newArrayList();
        Map<ResourceLocation, EntryAbstract> entries = Maps.newHashMap();
        
        sacrificeMagicPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.sacrificeMagic")));
        entries.put(new ResourceLocation(Sanguimancy.modid, "guide.Sanguimancy.entry.sacrificeMagic"), new Entry(sacrificeMagicPages, "guide.Sanguimancy.entryName.sacrificeMagic", true));

        soulCorruptionPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.corruption")));
        entries.put(new ResourceLocation(Sanguimancy.modid, "guide.Sanguimancy.entry.corruption"), new Entry(soulCorruptionPages, "guide.Sanguimancy.entryName.corruption", true));

        protectionPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.protection.1")));
        protectionPages.add(new PageImage(new ResourceLocation(Sanguimancy.texturePath + ":textures/screenshots/CompleteCrystallizerMultiblock.png")));
        protectionPages.add(new PageImage(new ResourceLocation(Sanguimancy.texturePath + ":textures/screenshots/CrystallizerMultiblock.png")));
        protectionPages.add(new PageImage(new ResourceLocation(Sanguimancy.texturePath + ":textures/screenshots/WaterCrystallizerMultiblock.png")));
        protectionPages.add(new PageImage(new ResourceLocation(Sanguimancy.texturePath + ":textures/screenshots/LavaCrystallizerMultiblock.png")));
        protectionPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.protection.2")));
        entries.put(new ResourceLocation(Sanguimancy.modid, "guide.Sanguimancy.entry.protection"), new Entry(protectionPages, "guide.Sanguimancy.entry.protection", true));

        corruptionApplicationsPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.applications")));
        entries.put(new ResourceLocation(Sanguimancy.modid, "guide.Sanguimancy.entry.applications"), new Entry(corruptionApplicationsPages, "guide.Sanguimancy.entryName.apllications", true));

        categories.add(new CategoryItemStack(entries, "guide.Sanguimancy.category.lore", SanguimancyItemStacks.attunnedPlayerSacrificer));
    }

    public static void createItemEntries() {
    	Map<ResourceLocation, EntryAbstract> entries = Maps.newHashMap();
        List<IPage> bloodAmuletPages = Lists.newArrayList();
        List<IPage> chunkClaimerPages = Lists.newArrayList();
        List<IPage> craftingItemsPages = Lists.newArrayList();
        List<IPage> corruptionCatalystPages = Lists.newArrayList();
        List<IPage> corruptionReaderPages = Lists.newArrayList();
        List<IPage> oreLumpPages = Lists.newArrayList();
        List<IPage> playerSacrificingStonesPages = Lists.newArrayList();
        
        bloodAmuletPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.bloodAmulet"), SanguimancyItemStacks.bloodAmulet));
        bloodAmuletPages.add(BloodMagicGuide.getOrbPageForRecipe(RecipesRegistry.bloodAmulet));
        entries.put(new ResourceLocation(Sanguimancy.modid, "guide.Sanguimancy.entry.bloodAmulet"), new EntryItemStack(bloodAmuletPages, "item.Sanguimancy.bloodAmulet.name", new ItemStack(ItemsRegistry.bloodAmulet)));

        chunkClaimerPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.chunkClaimer"), SanguimancyItemStacks.chunkClaimer));
        chunkClaimerPages.add(new PageIRecipe(RecipesRegistry.chunkClaimer));
        entries.put(new ResourceLocation(Sanguimancy.modid, "guide.Sanguimancy.entry.chunkClaimer"), new Entry(chunkClaimerPages, "item.Sanguimancy.chunkClaimer.name", true));

        craftingItemsPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.craftingItems")));
        craftingItemsPages.add(new PageCorruptionRecipe(RecipesRegistry.corruptedDemonShard));
        craftingItemsPages.add(new PageAltarRecipe(RecipesRegistry.imbuedStick));
        craftingItemsPages.add(new PageIRecipe(RecipesRegistry.corruptedMineral));
        entries.put(new ResourceLocation(Sanguimancy.modid, "guide.Sanguimancy.entry.craftingItems"), new Entry(craftingItemsPages, "guide.Sanguimancy.entryName.craftingItems", true));

        corruptionCatalystPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.corruptionCatalyst.1"), SanguimancyItemStacks.corruptionCatalist));
        corruptionCatalystPages.add(new PageAltarRecipe(RecipesRegistry.corruptionCatalyst));
        corruptionCatalystPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.corruptionCatalyst.2")));
        for (RecipeCorruptedInfusion r : RecipeCorruptedInfusion.getAllRecipes()) {
            corruptionCatalystPages.add(new PageCorruptionRecipe(r));
        }
        entries.put(new ResourceLocation(Sanguimancy.modid, "guide.Sanguimancy.entry.corruptionCatalyst"), new Entry(corruptionCatalystPages, "item.Sanguimancy.corruptionCatalist.name", true));

        corruptionReaderPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.corruptionReader"), SanguimancyItemStacks.corruptionReader));
        corruptionReaderPages.add(new PageIRecipe(RecipesRegistry.corruptionReader));
        entries.put(new ResourceLocation(Sanguimancy.modid, "guide.Sanguimancy.entry.corruptionReader"), new Entry(corruptionReaderPages, "item.Sanguimancy.soulCorruption.reader.name", true));

        oreLumpPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.oreLump"), SanguimancyItemStacks.oreLump));
        for (RecipeCorruptedInfusion r : RecipesRegistry.oreLumpRecipes) {
            oreLumpPages.add(new PageCorruptionRecipe(r));
        }
        //for (RecipeBloodCleanser r : RecipesRegistry.oreLumpCleansing) {
        //    oreLumpPages.add(new EntryBloodCleanserRecipe(r));
        // }
        entries.put(new ResourceLocation(Sanguimancy.modid, "guide.Sanguimancy.entry.oreLump"), new Entry(oreLumpPages, "info.Sanguimancy.tooltip.any", true));

        playerSacrificingStonesPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.sacrificingStones.1"), SanguimancyItemStacks.unattunedPlayerSacrificer));
        playerSacrificingStonesPages.add(BloodMagicGuide.getOrbPageForRecipe(RecipesRegistry.unattunedPlayerSacrificer));
        playerSacrificingStonesPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.sacrificingStones.2"), SanguimancyItemStacks.attunnedPlayerSacrificer));
        playerSacrificingStonesPages.add(new PageAltarRecipe(RecipesRegistry.attunedPlayerSacrificer));
        playerSacrificingStonesPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.sacrificingStones.3"), SanguimancyItemStacks.focusedPlayerSacrificer));
        entries.put(new ResourceLocation(Sanguimancy.modid, "guide.Sanguimancy.entry.sacrificingStones"), new Entry(playerSacrificingStonesPages, "Player Sacrificing Stones", true));

//        Unused
//        ArrayList<IPage> wandPages = new ArrayList<IPage>();
//        wandPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.wand"), SanguimancyItemStacks.wand));
//        wandPages.add(new PageIRecipe(RecipesRegistry.wand));
//        entries.add(new EntryUniText(wandPages, "item.Sanguimancy.spellWand.name"));
//
//        ArrayList<IPage> corruptedAxePages = new ArrayList<IPage>();
//        corruptedAxePages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.corruptedAxe"), SanguimancyItemStacks.corruptedAxe));
//        corruptedAxePages.add(new PageIRecipe(RecipesRegistry.corruptedAxe));
//        entries.add(new EntryUniText(corruptedAxePages, "item.Sanguimancy.corruptedAxe.name"));
//
//        ArrayList<IPage> corruptedPickaxePages = new ArrayList<IPage>();
//        corruptedPickaxePages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.corruptedPickaxe"), SanguimancyItemStacks.corruptedPickaxe));
//        corruptedPickaxePages.add(new PageIRecipe(RecipesRegistry.corruptedPickaxe));
//        entries.add(new EntryUniText(corruptedPickaxePages, "item.Sanguimancy.corruptedPickaxe.name"));
//
//        ArrayList<IPage> corruptedShovelPages = new ArrayList<IPage>();
//        corruptedShovelPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.corruptedShovel"), SanguimancyItemStacks.corruptedShovel));
//        corruptedShovelPages.add(new PageIRecipe(RecipesRegistry.corruptedShovel));
//        entries.add(new EntryUniText(corruptedShovelPages, "item.Sanguimancy.corruptedShovel.name"));
//
//        ArrayList<IPage> corruptedSwordPages = new ArrayList<IPage>();
//        corruptedSwordPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.corruptedSword"), SanguimancyItemStacks.corruptedSword));
//        corruptedSwordPages.add(new PageIRecipe(RecipesRegistry.corruptedSword));
//        entries.add(new EntryUniText(corruptedSwordPages, "item.Sanguimancy.corruptedSword.name"));

        ArrayList<IPage> soulTranporterPages = new ArrayList<IPage>();
        soulTranporterPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.soulTransporter"), SanguimancyItemStacks.soulTransporter));
        soulTranporterPages.add(BloodMagicGuide.getOrbPageForRecipe(RecipesRegistry.soulTransporter));
        entries.add(new EntryUniText(soulTranporterPages, "item.Sanguimancy.soulTransporter.name"));

        ArrayList<IPage> telepositionSigilIPages = new ArrayList<IPage>();
        telepositionSigilIPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.telepositionSigil"), SanguimancyItemStacks.telepositionSigil));
        telepositionSigilIPages.add(BloodMagicGuide.getOrbPageForRecipe(RecipesRegistry.telepositionSigil));
        entries.add(new EntryUniText(telepositionSigilIPages, "item.Sanguimancy.telepositionSigil.name"));

        ArrayList<IPage> transpositionSigilPages = new ArrayList<IPage>();
        transpositionSigilPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.transpositionSigil"), SanguimancyItemStacks.transpositionSigil));
        transpositionSigilPages.add(BloodMagicGuide.getOrbPageForRecipe(RecipesRegistry.transpositionSigil));
        entries.add(new EntryUniText(transpositionSigilPages, "item.Sanguimancy.transpositionSigil.name"));

        categories.add(new CategoryItemStack(entries, "guide.Sanguimancy.category.items", SanguimancyItemStacks.corruptedSword));
    }

    public static void createRitualEntries() {
        List<EntryAbstract> entries = new ArrayList<EntryAbstract>();

        if (ConfigHandler.enableAltarBuilder) {
            ArrayList<IPage> altarBuilderPages = new ArrayList<IPage>();
            altarBuilderPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.altar.builder")));
            entries.add(new EntryUniText(altarBuilderPages, "ritual.Sanguimancy.altar.builder"));
        }

        if (ConfigHandler.enableDrillOfTheDead) {
            ArrayList<IPage> drillOfTheDeadPages = new ArrayList<IPage>();
            drillOfTheDeadPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.drillOfTheDead")));
            entries.add(new EntryUniText(drillOfTheDeadPages, "ritual.Sanguimancy.drill.dead"));
        }

        if (ConfigHandler.enableFelling) {
            ArrayList<IPage> timbermanPages = new ArrayList<IPage>();
            timbermanPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.timberman")));
            entries.add(new EntryUniText(timbermanPages, "ritual.Sanguimancy.feller"));
            ;
        }

        if (ConfigHandler.enableIllumination) {
            ArrayList<IPage> enlightenmentPages = new ArrayList<IPage>();
            enlightenmentPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.enlightenment")));
            entries.add(new EntryUniText(enlightenmentPages, "ritual.Sanguimancy.illumination"));
        }

        if (ConfigHandler.enableVulcanosFrigius) {
            ArrayList<IPage> vulcanosFrigiusPages = new ArrayList<IPage>();
            vulcanosFrigiusPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.vulcanosFrigius")));
            entries.add(new EntryUniText(vulcanosFrigiusPages, "ritual.Sanguimancy.vulcanos.frigius"));
        }

        if (ConfigHandler.enablePlacer) {
            ArrayList<IPage> fillerPages = new ArrayList<IPage>();
            fillerPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.filler")));
            entries.add(new EntryUniText(fillerPages, "ritual.Sanguimancy.placer"));
        }

        if (ConfigHandler.enablePortal) {
            ArrayList<IPage> portalPages = new ArrayList<IPage>();
            portalPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.portal.1")));
            portalPages.add(new PageUnlocImage("guide.Sanguimancy.entry.portal.picture", new ResourceLocation(Sanguimancy.texturePath + ":textures/screenshots/PortalExample.png"), true));
            portalPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.portal.2")));
            entries.add(new EntryUniText(portalPages, "ritual.Sanguimancy.portal"));
        }

        if (ConfigHandler.enablePump) {
            ArrayList<IPage> pumpPages = new ArrayList<IPage>();
            pumpPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.pump")));
            entries.add(new EntryUniText(pumpPages, "ritual.Sanguimancy.pump"));
        }

        if (ConfigHandler.enableTrash) {
            ArrayList<IPage> greatDeletionPages = new ArrayList<IPage>();
            greatDeletionPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.greatDeletion")));
            entries.add(new EntryUniText(greatDeletionPages, "ritual.Sanguimancy.trash"));
        }

        categories.add(new CategoryItemStack(entries, "guide.Sanguimancy.category.rituals", new ItemStack(ModBlocks.blockMasterStone)));
    }

    public static void createBlockEntries() {
        List<EntryAbstract> entries = new ArrayList<EntryAbstract>();

        ArrayList<IPage> altarEmitterPages = new ArrayList<IPage>();
        altarEmitterPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.altarEmitter"), SanguimancyItemStacks.altarEmitter));
        altarEmitterPages.add(new PageIRecipe(RecipesRegistry.altarEmitter));
        entries.add(new EntryUniText(altarEmitterPages, "tile.Sanguimancy.altarEmitter.name"));

        ArrayList<IPage> altarDivinerPages = new ArrayList<IPage>();
        altarDivinerPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.altarDiviner"), SanguimancyItemStacks.altarDiviner));
        altarDivinerPages.add(new PageAltarRecipe(RecipesRegistry.altarDiviner));
        entries.add(new EntryUniText(altarDivinerPages, "tile.Sanguimancy.altarDiviner.name"));

        ArrayList<IPage> altarManipulatorPages = new ArrayList<IPage>();
        altarManipulatorPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.altarManipulator"), SanguimancyItemStacks.altarManipulator));
        altarManipulatorPages.add(BloodMagicGuide.getOrbPageForRecipe(RecipesRegistry.altarManipulator));
        altarManipulatorPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.sanguineShifter"), SanguimancyItemStacks.sanguineShifter));
        altarManipulatorPages.add(BloodMagicGuide.getOrbPageForRecipe(RecipesRegistry.sanguineShifter));
        entries.add(new EntryUniText(altarManipulatorPages, "tile.Sanguimancy.altarManipulator.name"));

        ArrayList<IPage> bloodInterfacePages = new ArrayList<IPage>();
        bloodInterfacePages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.bloodInterface"), SanguimancyItemStacks.bloodInterface));
        bloodInterfacePages.add(new PageIRecipe(RecipesRegistry.bloodInterface));
        entries.add(new EntryUniText(bloodInterfacePages, "tile.Sanguimancy.interface.name"));

        ArrayList<IPage> decorativeBlocksPages = new ArrayList<IPage>();
        decorativeBlocksPages.add(new PageIRecipe(RecipesRegistry.bloodstoneSlab));
        decorativeBlocksPages.add(new PageIRecipe(RecipesRegistry.largeBloodstoneSlab));
        decorativeBlocksPages.add(new PageIRecipe(RecipesRegistry.bloodstoneStairs));
        decorativeBlocksPages.add(new PageIRecipe(RecipesRegistry.largeBloodstoneStairs));
        entries.add(new EntryUniText(decorativeBlocksPages, "guide.Sanguimancy.entryName.decorativeBlocks"));

        ArrayList<IPage> bloodTankPages = new ArrayList<IPage>();
        bloodTankPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.bloodTank"), SanguimancyItemStacks.bloodTank));
        for (IRecipe recipe : RecipesRegistry.bloodTank) {
            bloodTankPages.add(BloodMagicGuide.getOrbPageForRecipe(recipe));
        }
        entries.add(new EntryUniText(bloodTankPages, "tile.Sanguimancy.bloodTank.name"));

        ArrayList<IPage> corruptionCrystallizerPages = new ArrayList<IPage>();
        corruptionCrystallizerPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.corruptionCrystallizer"), SanguimancyItemStacks.corruptionCrystallizer));
        corruptionCrystallizerPages.add(BloodMagicGuide.getOrbPageForRecipe(RecipesRegistry.corruptionCrystallizer));
        entries.add(new EntryUniText(corruptionCrystallizerPages, "tile.Sanguimancy.corruptionCrystallizer.name"));

        ArrayList<IPage> manifestationsPages = new ArrayList<IPage>();
        manifestationsPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.itemManifestation"), SanguimancyItemStacks.boundItem));
        manifestationsPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.ritualManifestation"), SanguimancyItemStacks.ritualRepresentation));
        manifestationsPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.etherealManifestation"), SanguimancyItemStacks.etherealManifestation));
        manifestationsPages.add(new PageAltarRecipe(RecipesRegistry.etherealManifestation));
        manifestationsPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.soulBranch"), SanguimancyItemStacks.simpleBranch));
        manifestationsPages.add(new PageIRecipe(RecipesRegistry.simpleBranch));
        manifestationsPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.simpleKnot"), SanguimancyItemStacks.simpleKnot));
        manifestationsPages.add(new PageIRecipe(RecipesRegistry.simpleKnot));
        manifestationsPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.toggledKnot"), SanguimancyItemStacks.toggleKnot));
        manifestationsPages.add(new PageIRecipe(RecipesRegistry.toggledKnot));
        entries.add(new EntryUniText(manifestationsPages, "guide.Sanguimancy.entryName.manifestations"));

        ArrayList<IPage> bloodCleanserPages = new ArrayList<IPage>();
        bloodCleanserPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.bloodCleanser"), SanguimancyItemStacks.lumpCleaner));
        bloodCleanserPages.add(BloodMagicGuide.getOrbPageForRecipe(RecipesRegistry.lumpCleaner));
        //for (RecipeBloodCleanser r : RecipeBloodCleanser.getAllRecipes()) {
        //    bloodCleanserPages.add(new EntryBloodCleanserRecipe(r));
        //}
        entries.add(new EntryUniText(bloodCleanserPages, "tile.Sanguimancy.bloodCleaner.name"));

        ArrayList<IPage> soulTransferrerPages = new ArrayList<IPage>();
        soulTransferrerPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.soulTransferrer"), SanguimancyItemStacks.sacrificeTransferrer));
        soulTransferrerPages.add(new PageIRecipe(RecipesRegistry.sacrificeTransferrer));
        entries.add(new EntryUniText(soulTransferrerPages, "tile.Sanguimancy.sacrificeTransfer.name"));

        ArrayList<IPage> etherealBlocksPages = new ArrayList<IPage>();
        etherealBlocksPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.etherealBlock"), SanguimancyItemStacks.etherealBlock));
        etherealBlocksPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.corruptedEtherealBlock"), SanguimancyItemStacks.etherealCorruptedBlock));
        etherealBlocksPages.add(new PageCorruptionRecipe(RecipesRegistry.corruptedEtherealBlock));
        etherealBlocksPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.toggledEtherealBlock"), SanguimancyItemStacks.etherealToggledBlock));
        etherealBlocksPages.add(new PageIRecipe(RecipesRegistry.toggledEtherealBlock));
        etherealBlocksPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.boundEtherealBlocks")));
        etherealBlocksPages.addAll(PageHelper.pagesForLongText(StatCollector.translateToLocal("guide.Sanguimancy.entry.personalEtherealBlock"), SanguimancyItemStacks.personalEtherealBlock));
        etherealBlocksPages.add(new PageIRecipe(RecipesRegistry.personalEtherealBlock));
        entries.add(new EntryUniText(etherealBlocksPages, "guide.Sanguimancy.entryName.etherealBlocks"));

        categories.add(new CategoryItemStack(entries, "guide.Sanguimancy.category.blocks", SanguimancyItemStacks.lumpCleaner));
    }
}
