package tombenpotter.sanguimancy.registry;

import WayofTime.bloodmagic.alchemyArray.AlchemyArrayEffectBinding;
import WayofTime.bloodmagic.api.alchemyCrafting.AlchemyArrayEffectCrafting;
import WayofTime.bloodmagic.api.alchemyCrafting.AlchemyCircleRenderer;
import WayofTime.bloodmagic.api.altar.EnumAltarTier;
import WayofTime.bloodmagic.api.recipe.ShapedBloodOrbRecipe;
import WayofTime.bloodmagic.client.render.alchemyArray.BindingAlchemyCircleRenderer;
import WayofTime.bloodmagic.core.registry.AlchemyArrayRecipeRegistry;
import WayofTime.bloodmagic.core.registry.AltarRecipeRegistry.AltarRecipe;
import WayofTime.bloodmagic.core.registry.OrbRegistry;
import WayofTime.bloodmagic.item.ItemComponent;
import WayofTime.bloodmagic.registry.ModBlocks;
import WayofTime.bloodmagic.registry.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import tombenpotter.sanguimancy.api.registries.RecipeRegistry;
import tombenpotter.sanguimancy.recipes.RecipeBloodCleanser;
import tombenpotter.sanguimancy.recipes.RecipeCorruptedInfusion;
import tombenpotter.sanguimancy.util.RandomUtils;
import tombenpotter.sanguimancy.util.SanguimancyItemStacks;

import java.util.ArrayList;

public class RecipesRegistry {

    public static IRecipe altarEmitter, sacrificeTransferrer, corruptionReader, unattunedPlayerSacrificer, corruptionCrystallizer, lumpCleaner,
            bloodAmulet, bloodstoneStairs, largeBloodstoneStairs, bloodstoneSlab, largeBloodstoneSlab, chunkClaimer, wand, bloodInterface,
            corruptedMineral, corruptedSword, corruptedPickaxe, corruptedShovel, corruptedAxe, toggledEtherealBlock,
            personalEtherealBlock, soulTransporter, sanguineShifter, altarManipulator;
    public static IRecipe[] bloodTank = new IRecipe[16];
    public static AltarRecipe altarDiviner, attunedPlayerSacrificer, corruptionCatalyst, imbuedStick, etherealManifestation;
    public static RecipeCorruptedInfusion poisonousPotato, rottenFlesh, crackedStoneBricks, bonemeal, soulSand, corruptedDemonShard, cobblestone, gravel,
            sand, dirt, corruptedEtherealBlock;
    public static ArrayList<RecipeCorruptedInfusion> oreLumpRecipes = new ArrayList<RecipeCorruptedInfusion>();
    public static ArrayList<RecipeBloodCleanser> oreLumpCleansing = new ArrayList<RecipeBloodCleanser>();

    public static void registerShapedRecipes() {
        altarEmitter = GameRegistry.addShapedRecipe(SanguimancyItemStacks.altarEmitter, "XYX", "XZX", "XXX", 'X', Blocks.REDSTONE_BLOCK, 'Y', Blocks.LEVER, 'Z', ModBlocks.altar);
        sacrificeTransferrer = GameRegistry.addShapedRecipe(SanguimancyItemStacks.sacrificeTransferrer, "XAX", "YZY", "XYX", 'X', new ItemStack(ModItems.slate, 1, 3), 'A', new ItemStack(ModItems.lavaCrystal), 'Y', new ItemStack(Items.DIAMOND), 'Z', new ItemStack(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE));
        corruptionReader = GameRegistry.addShapedRecipe(SanguimancyItemStacks.corruptionReader, "AXA", "ZYB", "AXA", 'X', Blocks.SOUL_SAND, 'Y', new ItemStack(ModItems.sigilDivination), 'Z', new ItemStack(Items.SKULL, 1, 1), 'A', Blocks.NETHER_BRICK, 'B', Items.ENDER_EYE);
//        bloodstoneStairs = GameRegistry.addShapedRecipe(new ItemStack(BlocksRegistry.bloodStoneStairs, 4), "X  ", "XX ", "XXX", 'X', ModBlocks.bloodStoneBrick);
//        largeBloodstoneStairs = GameRegistry.addShapedRecipe(new ItemStack(BlocksRegistry.largeBloodStoneStairs, 4), "X  ", "XX ", "XXX", 'X', ModBlocks.largeBloodStoneBrick);
//        bloodstoneSlab = GameRegistry.addShapedRecipe(new ItemStack(BlocksRegistry.bloodstoneSlab, 6), "XXX", 'X', ModBlocks.bloodStoneBrick);
//        largeBloodstoneSlab = GameRegistry.addShapedRecipe(new ItemStack(BlocksRegistry.largeBloodstoneSlab, 6), "XXX", 'X', ModBlocks.largeBloodStoneBrick);
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.bloodStoneBrick), "X", "X", 'X', SanguimancyItemStacks.bloodstoneSlab);
//        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.largeBloodStoneBrick), "X", "X", 'X', SanguimancyItemStacks.largeBloodstoneSlab);
        chunkClaimer = GameRegistry.addShapedRecipe(SanguimancyItemStacks.chunkClaimer, " X ", "XYX", " X ", 'X', new ItemStack(ModItems.slate, 1, 3), 'Y', SanguimancyItemStacks.corruptedDemonShard);
//        wand = GameRegistry.addShapedRecipe(SanguimancyItemStacks.wand, "XYX", "XZX", "XZX", 'X', new ItemStack(ModItems.slate, 1, 1), 'Y', ModItems.itemComplexSpellCrystal, 'Z', Items.STICK);
        corruptedMineral = GameRegistry.addShapedRecipe(SanguimancyItemStacks.corruptedMineral, " X ", " Y ", "ZYA", 'X', new ItemStack(Items.SKULL, 1, 1), 'Y', Blocks.STONE, 'Z', Items.DIAMOND, 'A', Items.GOLD_INGOT);
        corruptedSword = GameRegistry.addShapedRecipe(SanguimancyItemStacks.corruptedSword, " X ", " X ", " Y ", 'X', SanguimancyItemStacks.corruptedMineral, 'Y', SanguimancyItemStacks.imbuedStick);
        corruptedPickaxe = GameRegistry.addShapedRecipe(SanguimancyItemStacks.corruptedPickaxe, "XXX", " Y ", " Y ", 'X', SanguimancyItemStacks.corruptedMineral, 'Y', SanguimancyItemStacks.imbuedStick);
        corruptedShovel = GameRegistry.addShapedRecipe(SanguimancyItemStacks.corruptedShovel, " X ", " Y ", " Y ", 'X', SanguimancyItemStacks.corruptedMineral, 'Y', SanguimancyItemStacks.imbuedStick);
        corruptedAxe = GameRegistry.addShapedRecipe(SanguimancyItemStacks.corruptedAxe, "XX ", "XY ", " Y ", 'X', SanguimancyItemStacks.corruptedMineral, 'Y', SanguimancyItemStacks.imbuedStick);
        toggledEtherealBlock = GameRegistry.addShapedRecipe(new ItemStack(BlocksRegistry.etherealToggledBlock, 8), "XXX", "XYX", "XXX", 'Y', Blocks.REDSTONE_BLOCK, 'X', SanguimancyItemStacks.etherealBlock);
        personalEtherealBlock = GameRegistry.addShapedRecipe(new ItemStack(BlocksRegistry.etherealPersonalBlock, 8), "XXX", "XYX", "XXX", 'X', SanguimancyItemStacks.etherealBoundBlock, 'Y', Items.NAME_TAG);
        bloodInterface = GameRegistry.addShapedRecipe(SanguimancyItemStacks.bloodInterface, "XYX", "XZX", "XXX", 'X', Blocks.STONE, 'Y', new ItemStack(ModBlocks.bloodRune, 1, 0), 'Z', new ItemStack(Items.COMPARATOR));
    }

    public static void registerAltarRecipes() {
        altarDiviner = new AltarRecipeRegistry.AltarRecipe(SanguimancyItemStacks.altarDiviner, new ItemStack(ModBlocks.altar), EnumAltarTier.THREE, 3000, 10, 10, false);
        AltarRecipeRegistry.registerRecipe(altarDiviner);

        attunedPlayerSacrificer = new AltarRecipeRegistry.AltarRecipe(SanguimancyItemStacks.attunnedPlayerSacrificer, SanguimancyItemStacks.unattunedPlayerSacrificer, EnumAltarTier.FIVE, 30000, 10, 10, false);
        AltarRecipeRegistry.registerRecipe(attunedPlayerSacrificer);

        corruptionCatalyst = new AltarRecipeRegistry.AltarRecipe(SanguimancyItemStacks.corruptionCatalist, new ItemStack(Items.SKULL, 1, 1), EnumAltarTier.THREE, 3000, 10, 10, false);
        AltarRecipeRegistry.registerRecipe(corruptionCatalyst);

        imbuedStick = new AltarRecipeRegistry.AltarRecipe(SanguimancyItemStacks.imbuedStick, new ItemStack(Items.STICK), EnumAltarTier.TWO, 3000, 10, 10, false);
        AltarRecipeRegistry.registerRecipe(imbuedStick);

        etherealManifestation = new AltarRecipeRegistry.AltarRecipe(SanguimancyItemStacks.etherealManifestation, SanguimancyItemStacks.corruptionCatalist, EnumAltarTier.FOUR, 6000, 10, 10, false);
        AltarRecipeRegistry.registerRecipe(etherealManifestation);
    }

    public static void registerOrbRecipes() {
        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(SanguimancyItemStacks.unattunedPlayerSacrificer, "XYX", "YOY", "XYX", 'X', new ItemStack(ModItems.slate, 1, 3), 'Y', new ItemStack(ModBlocks.bloodRune), 'O', OrbRegistry.getOrbStack(ModItems.orbArchmage)));
        unattunedPlayerSacrificer = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(SanguimancyItemStacks.corruptionCrystallizer, "XYX", "ZAZ", "XBX", 'X', new ItemStack(Blocks.OBSIDIAN), 'Y', new ItemStack(ModBlocks.bloodRune), 'Z', new ItemStack(Blocks.DIAMOND_BLOCK), 'A', SanguimancyItemStacks.corruptedDemonShard, 'B', OrbRegistry.getOrbStack(ModItems.orbArchmage)));
        corruptionCrystallizer = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(SanguimancyItemStacks.lumpCleaner, "XXX", "YZY", "ABA", 'X', SanguimancyItemStacks.oreLump, 'Y', ModBlocks.bloodTank, 'Z', OrbRegistry.getOrbStack(ModItems.orbMaster), 'A', new ItemStack(Blocks.IRON_BLOCK), 'B', new ItemStack(Blocks.DIAMOND_BLOCK)));
        lumpCleaner = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(SanguimancyItemStacks.bloodAmulet, "XYX", "ZAZ", "BCB", 'X', new ItemStack(Items.STRING), 'Y', new ItemStack(Items.BUCKET), 'Z', new ItemStack(ModItems.slate, 1, 3), 'A', new ItemStack(Items.CLOCK), 'B', ModBlocks.bloodTank, 'C', OrbRegistry.getOrbStack(ModItems.orbMagician)));
        bloodAmulet = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(SanguimancyItemStacks.soulTransporter, "XYZ", "COC", "ACA", 'X', ModItems.sigilDivination, 'Y', new ItemStack(ModItems.slate, 1, 3), 'Z', ModItems.sigilEnderSeverance, 'C', Blocks.SOUL_SAND, 'A', ModBlocks.bloodRune, 'O', OrbRegistry.getOrbStack(ModItems.orbApprentice)));
        soulTransporter = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(SanguimancyItemStacks.sanguineShifter, "A", "O", "L", 'A', SanguimancyItemStacks.altarManipulator, 'O', OrbRegistry.getOrbStack(ModItems.orbMaster), 'L', Blocks.HOPPER));
        sanguineShifter = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(SanguimancyItemStacks.altarManipulator, "DED", "EOE", "DED", 'D', SanguimancyItemStacks.altarDiviner, 'E', SanguimancyItemStacks.altarEmitter, 'O', OrbRegistry.getOrbStack(ModItems.orbMagician)));
        altarManipulator = RecipeRegistry.getLatestCraftingRecipe();

        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(ModBlocks.bloodTank, "XZX", "AYA", "XZX", 'X', new ItemStack(Blocks.STAINED_GLASS, 1, 14), 'Y', OrbRegistry.getOrbStack(ModItems.orbApprentice), 'Z', new ItemStack(Blocks.OBSIDIAN), 'A', new ItemStack(ModItems.slate, 1, 0)));
        bloodTank[0] = RecipeRegistry.getLatestCraftingRecipe();
        for (int i = 0; i < 15; i++) {
            GameRegistry.addRecipe(new ShapedBloodOrbRecipe(new ItemStack(ModBlocks.bloodTank, 1, i + 1), "XSX", "TOT", "XTX", 'X', new ItemStack(Blocks.STAINED_GLASS, 1, 14), 'S', new ItemStack(ModItems.slate, 1, 3), 'T', new ItemStack(ModBlocks.bloodTank, 1, i), 'O', OrbRegistry.getOrbStack(ModItems.orbArchmage)));
            bloodTank[i + 1] = RecipeRegistry.getLatestCraftingRecipe();
        }
    }

    public static void registerBindingRecipes() {
        AlchemyArrayRecipeRegistry.registerRecipe(ItemComponent.getStack(ItemComponent.REAGENT_BINDING), SanguimancyItemStacks.etherealBlock, new AlchemyArrayEffectBinding("boundBlock", SanguimancyItemStacks.etherealBoundBlock), new BindingAlchemyCircleRenderer());
        AlchemyArrayRecipeRegistry.registerRecipe(ItemComponent.getStack(ItemComponent.REAGENT_BINDING), SanguimancyItemStacks.etherealCorruptedBlock, new AlchemyArrayEffectBinding("boundCorruptedBlock", SanguimancyItemStacks.etherealBoundCorruptedBlock), new BindingAlchemyCircleRenderer());
        AlchemyArrayRecipeRegistry.registerRecipe(ItemComponent.getStack(ItemComponent.REAGENT_BINDING), SanguimancyItemStacks.etherealToggledBlock, new AlchemyArrayEffectBinding("boundToggledBlock", SanguimancyItemStacks.etherealBoundToggledBlock), new BindingAlchemyCircleRenderer());
        AlchemyArrayRecipeRegistry.registerRecipe(ItemComponent.getStack(ItemComponent.REAGENT_VOID), new ItemStack(Blocks.SOUL_SAND), new AlchemyArrayEffectCrafting(SanguimancyItemStacks.etherealBlock), new AlchemyCircleRenderer());
    }

    public static void registerCustomModRecipes() {
        poisonousPotato = RecipeCorruptedInfusion.addRecipe(new ItemStack(Items.POISONOUS_POTATO), new ItemStack(Items.POTATO), 5, 50, false);
        rottenFlesh = RecipeCorruptedInfusion.addRecipe(new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.BEEF), 5, 50, false);
        crackedStoneBricks = RecipeCorruptedInfusion.addRecipe(new ItemStack(Blocks.STONEBRICK, 1, 2), new ItemStack(Blocks.STONEBRICK, 1, 0), 5, 50, false);
        bonemeal = RecipeCorruptedInfusion.addRecipe(new ItemStack(Items.DYE, 6, 15), new ItemStack(Items.BONE), 10, 70, false);
        soulSand = RecipeCorruptedInfusion.addRecipe(new ItemStack(Blocks.SOUL_SAND), new ItemStack(Blocks.SAND), 10, 100, false);
        corruptedDemonShard = RecipeCorruptedInfusion.addRecipe(SanguimancyItemStacks.corruptedDemonShard, new ItemStack(ModItems.itemDemonCrystal), 50, 500, false);
        cobblestone = RecipeCorruptedInfusion.addRecipe(new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.STONE), 5, 50, false);
        gravel = RecipeCorruptedInfusion.addRecipe(new ItemStack(Blocks.GRAVEL), new ItemStack(Blocks.COBBLESTONE), 5, 50, false);
        sand = RecipeCorruptedInfusion.addRecipe(new ItemStack(Blocks.SAND), new ItemStack(Blocks.GRAVEL), 5, 50, false);
        dirt = RecipeCorruptedInfusion.addRecipe(new ItemStack(Blocks.DIRT), new ItemStack(Blocks.GRASS), 5, 50, false);

        for (String ore : OreDictionary.getOreNames()) {
            if (ore.startsWith("ore")) {
                String output = ore.substring(3);
                if (!OreDictionary.getOres(ore).isEmpty() && !OreDictionary.getOres("ingot" + output).isEmpty()) {
                    for (int i = 0; i < OreDictionary.getOres(ore).size(); i++) {
                        ItemStack oreLump = new ItemStack(ItemsRegistry.oreLump, 2, 0);
                        RandomUtils.checkAndSetCompound(oreLump);
                        oreLump.getTagCompound().setString("ore", output);
                        oreLumpRecipes.add(RecipeCorruptedInfusion.addRecipe(oreLump, OreDictionary.getOres(ore).get(i), 20, 150, false));
                    }
                    ItemStack input = new ItemStack(ItemsRegistry.oreLump, 1, 0);
                    RandomUtils.checkAndSetCompound(input);
                    input.getTagCompound().setString("ore", output);
                    oreLumpCleansing.add(RecipeBloodCleanser.addRecipe(input, OreDictionary.getOres("ingot" + output).get(0)));
                    //That doesn't work
                    //AltarRecipeRegistry.registerNBTAltarRecipe(OreDictionary.getOres("ingot" + output).get(0), input, 2, 1000, 10, 10, false);
                }
            }
        }

        if (!OreDictionary.getOres("dustWood").isEmpty()) {
            for (int i = 0; i < OreDictionary.getOres("logWood").size(); i++) {
                RecipeCorruptedInfusion.addRecipe(OreDictionary.getOres("dustWood").get(0), OreDictionary.getOres("logWood").get(i), 5, 50, false);
            }
        }
        RecipeBloodCleanser.addRecipe(new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.COBBLESTONE));

        corruptedEtherealBlock = RecipeCorruptedInfusion.addRecipe(SanguimancyItemStacks.etherealCorruptedBlock, SanguimancyItemStacks.etherealBlock, 50, 100, false);
    }
}