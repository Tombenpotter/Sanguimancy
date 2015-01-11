package tombenpotter.sanguimancy.registry;

import WayofTime.alchemicalWizardry.ModBlocks;
import WayofTime.alchemicalWizardry.ModItems;
import WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipe;
import WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipeRegistry;
import WayofTime.alchemicalWizardry.api.items.ShapedBloodOrbRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;
import tombenpotter.sanguimancy.api.bloodutils.api.registries.RecipeRegistry;
import tombenpotter.sanguimancy.compat.computercraft.PeripheralProvider;
import tombenpotter.sanguimancy.recipes.RecipeBloodCleanser;
import tombenpotter.sanguimancy.recipes.RecipeCorruptedInfusion;
import tombenpotter.sanguimancy.util.RandomUtils;
import tombenpotter.sanguimancy.util.enums.ModList;

import java.util.ArrayList;

public class RecipesRegistry {

    public static IRecipe altarEmitter, sacrificeTransferrer, corruptionReader, unattunedPlayerSacrificer, corruptionCrystallizer, bloodTank, lumpCleaner,
            bloodAmulet, bloodstoneStairs, largeBloodstoneStairs, bloodstoneSlab, largeBloodstoneSlab, chunkClaimer, wand, bloodInterface, simpleBranch,
            simpleKnot, toggledKnot, corruptedMineral, corruptedSword, corruptedPickaxe, corruptedShovel, corruptedAxe;
    public static AltarRecipe altarDiviner, attunedPlayerSacrificer, corruptionCatalyst;
    public static RecipeCorruptedInfusion poisonousPotato, rottenFlesh, crackedStoneBricks, bonemeal, soulSand, corruptedDemonShard, cobblestone, gravel, sand, dirt;
    public static RecipeBloodCleanser imbuedStick;
    public static ArrayList<RecipeCorruptedInfusion> oreLumpRecipes = new ArrayList<RecipeCorruptedInfusion>();
    public static ArrayList<RecipeBloodCleanser> oreLumpCleansing = new ArrayList<RecipeBloodCleanser>();

    public static void registerShapedRecipes() {
        GameRegistry.addShapelessRecipe(RandomUtils.SanguimancyItemStacks.playerGuide, Items.book, ModItems.divinationSigil);
        altarEmitter = GameRegistry.addShapedRecipe(RandomUtils.SanguimancyItemStacks.altarEmitter, "XYX", "XZX", "XXX", 'X', Blocks.redstone_block, 'Y', Blocks.lever, 'Z', ModBlocks.blockAltar);
        sacrificeTransferrer = GameRegistry.addShapedRecipe(RandomUtils.SanguimancyItemStacks.sacrificeTransferrer, "XAX", "YZY", "XYX", 'X', new ItemStack(ModItems.demonicSlate), 'A', new ItemStack(ModItems.lavaCrystal), 'Y', new ItemStack(Items.diamond), 'Z', new ItemStack(Blocks.heavy_weighted_pressure_plate));
        corruptionReader = GameRegistry.addShapedRecipe(RandomUtils.SanguimancyItemStacks.corruptionReader, "AXA", "ZYB", "AXA", 'X', Blocks.soul_sand, 'Y', new ItemStack(ModItems.divinationSigil), 'Z', new ItemStack(Items.skull, 1, 1), 'A', Blocks.nether_brick, 'B', Items.ender_eye);
        bloodstoneStairs = GameRegistry.addShapedRecipe(RandomUtils.SanguimancyItemStacks.bloodstoneStairs, "X  ", "XX ", "XXX", 'X', ModBlocks.bloodStoneBrick);
        largeBloodstoneStairs = GameRegistry.addShapedRecipe(RandomUtils.SanguimancyItemStacks.largeBloodstoneStairs, "X  ", "XX ", "XXX", 'X', ModBlocks.largeBloodStoneBrick);
        bloodstoneSlab = GameRegistry.addShapedRecipe(RandomUtils.SanguimancyItemStacks.bloodstoneSlab, "XXX", 'X', ModBlocks.bloodStoneBrick);
        largeBloodstoneSlab = GameRegistry.addShapedRecipe(RandomUtils.SanguimancyItemStacks.largeBloodstoneSlab, "XXX", 'X', ModBlocks.largeBloodStoneBrick);
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.bloodStoneBrick), "X", "X", 'X', RandomUtils.SanguimancyItemStacks.bloodstoneSlab);
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.largeBloodStoneBrick), "X", "X", 'X', RandomUtils.SanguimancyItemStacks.largeBloodstoneSlab);
        chunkClaimer = GameRegistry.addShapedRecipe(RandomUtils.SanguimancyItemStacks.chunkClaimer, " X ", "XYX", " X ", 'X', ModItems.demonicSlate, 'Y', RandomUtils.SanguimancyItemStacks.corruptedDemonShard);
        wand = GameRegistry.addShapedRecipe(RandomUtils.SanguimancyItemStacks.wand, "XYX", "XZX", "XZX", 'X', ModItems.reinforcedSlate, 'Y', ModItems.itemComplexSpellCrystal, 'Z', Items.stick);
        simpleBranch = GameRegistry.addShapedRecipe(new ItemStack(BlocksRegistry.simpleBranch, 8), "XYX", "Z Z", "XYX", 'X', ModItems.blankSlate, 'Y', ModItems.reinforcedSlate, 'Z', Blocks.glass);
        simpleKnot = GameRegistry.addShapedRecipe(RandomUtils.SanguimancyItemStacks.simpleKnot, " X ", "XYX", " X ", 'X', RandomUtils.SanguimancyItemStacks.simpleBranch, 'Y', ModItems.demonicSlate);
        toggledKnot = GameRegistry.addShapedRecipe(RandomUtils.SanguimancyItemStacks.toggleKnot, " X ", "XYX", " X ", 'X', Items.redstone, 'Y', RandomUtils.SanguimancyItemStacks.simpleKnot);
        corruptedMineral = GameRegistry.addShapedRecipe(RandomUtils.SanguimancyItemStacks.corruptedMineral, " X ", " Y ", "ZYA", 'X', new ItemStack(Items.skull, 1, 1), 'Y', Blocks.stone, 'Z', Items.diamond, 'A', Items.gold_ingot);
        corruptedSword = GameRegistry.addShapedRecipe(RandomUtils.SanguimancyItemStacks.corruptedSword, " X ", " X ", " Y ", 'X', RandomUtils.SanguimancyItemStacks.corruptedMineral, 'Y', RandomUtils.SanguimancyItemStacks.imbuedStick);
        corruptedPickaxe = GameRegistry.addShapedRecipe(RandomUtils.SanguimancyItemStacks.corruptedPickaxe, "XXX", " Y ", " Y ", 'X', RandomUtils.SanguimancyItemStacks.corruptedMineral, 'Y', RandomUtils.SanguimancyItemStacks.imbuedStick);
        corruptedShovel = GameRegistry.addShapedRecipe(RandomUtils.SanguimancyItemStacks.corruptedShovel, " X ", " Y ", " Y ", 'X', RandomUtils.SanguimancyItemStacks.corruptedMineral, 'Y', RandomUtils.SanguimancyItemStacks.imbuedStick);
        corruptedAxe = GameRegistry.addShapedRecipe(RandomUtils.SanguimancyItemStacks.corruptedAxe, "XX ", "XY ", " Y ", 'X', RandomUtils.SanguimancyItemStacks.corruptedMineral, 'Y', RandomUtils.SanguimancyItemStacks.imbuedStick);
    }

    public static void registerAltarRecipes() {
        AltarRecipeRegistry.registerAltarRecipe(RandomUtils.SanguimancyItemStacks.altarDiviner, new ItemStack(ModBlocks.blockAltar), 3, 3000, 10, 10, false);
        altarDiviner = RecipeRegistry.getLatestAltarRecipe();
        AltarRecipeRegistry.registerAltarRecipe(RandomUtils.SanguimancyItemStacks.attunnedPlayerSacrificer, RandomUtils.SanguimancyItemStacks.unattunedPlayerSacrificer, 5, 30000, 10, 10, false);
        attunedPlayerSacrificer = RecipeRegistry.getLatestAltarRecipe();
        AltarRecipeRegistry.registerAltarRecipe(RandomUtils.SanguimancyItemStacks.corruptionCatalist, new ItemStack(Items.skull, 1, 1), 3, 3000, 10, 10, false);
        corruptionCatalyst = RecipeRegistry.getLatestAltarRecipe();
    }

    public static void registerOrbRecipes() {
        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(RandomUtils.SanguimancyItemStacks.unattunedPlayerSacrificer, "XYX", "YOY", "XYX", 'X', new ItemStack(ModItems.demonicSlate), 'Y', new ItemStack(ModBlocks.emptySocket), 'O', new ItemStack(ModItems.archmageBloodOrb)));
        unattunedPlayerSacrificer = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(RandomUtils.SanguimancyItemStacks.corruptionCrystallizer, "XYX", "ZAZ", "XBX", 'X', new ItemStack(Blocks.obsidian), 'Y', new ItemStack(ModBlocks.bloodSocket), 'Z', new ItemStack(Blocks.diamond_block), 'A', RandomUtils.SanguimancyItemStacks.corruptedDemonShard, 'B', new ItemStack(ModItems.archmageBloodOrb)));
        corruptionCrystallizer = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(RandomUtils.SanguimancyItemStacks.bloodTank, "XZX", "AYA", "XZX", 'X', new ItemStack(Blocks.stained_glass, 1, 14), 'Y', new ItemStack(ModItems.apprenticeBloodOrb), 'Z', new ItemStack(Blocks.obsidian), 'A', new ItemStack(ModItems.blankSlate)));
        bloodTank = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(RandomUtils.SanguimancyItemStacks.lumpCleaner, "XXX", "YZY", "ABA", 'X', RandomUtils.SanguimancyItemStacks.oreLump, 'Y', RandomUtils.SanguimancyItemStacks.bloodTank, 'Z', new ItemStack(ModItems.masterBloodOrb), 'A', new ItemStack(Blocks.iron_block), 'B', new ItemStack(Blocks.diamond_block)));
        lumpCleaner = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(RandomUtils.SanguimancyItemStacks.bloodAmulet, "XYX", "ZAZ", "BCB", 'X', new ItemStack(Items.string), 'Y', new ItemStack(Items.bucket), 'Z', new ItemStack(ModItems.demonicSlate), 'A', new ItemStack(Items.clock), 'B', RandomUtils.SanguimancyItemStacks.bloodTank, 'C', new ItemStack(ModItems.magicianBloodOrb)));
        bloodAmulet = RecipeRegistry.getLatestCraftingRecipe();
    }

    public static void registerCustomModRecipes() {
        poisonousPotato = RecipeCorruptedInfusion.addRecipe(new ItemStack(Items.poisonous_potato), new ItemStack(Items.potato), 5, 50, false);
        rottenFlesh = RecipeCorruptedInfusion.addRecipe(new ItemStack(Items.rotten_flesh), new ItemStack(Items.beef), 5, 50, false);
        crackedStoneBricks = RecipeCorruptedInfusion.addRecipe(new ItemStack(Blocks.stonebrick, 1, 2), new ItemStack(Blocks.stonebrick, 1, 0), 5, 50, false);
        bonemeal = RecipeCorruptedInfusion.addRecipe(new ItemStack(Items.dye, 6, 15), new ItemStack(Items.bone), 10, 70, false);
        soulSand = RecipeCorruptedInfusion.addRecipe(new ItemStack(Blocks.soul_sand), new ItemStack(Blocks.sand), 10, 100, false);
        corruptedDemonShard = RecipeCorruptedInfusion.addRecipe(RandomUtils.SanguimancyItemStacks.corruptedDemonShard, new ItemStack(ModItems.demonBloodShard), 50, 500, false);
        cobblestone = RecipeCorruptedInfusion.addRecipe(new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.stone), 5, 50, false);
        gravel = RecipeCorruptedInfusion.addRecipe(new ItemStack(Blocks.gravel), new ItemStack(Blocks.cobblestone), 5, 50, false);
        sand = RecipeCorruptedInfusion.addRecipe(new ItemStack(Blocks.sand), new ItemStack(Blocks.gravel), 5, 50, false);
        dirt = RecipeCorruptedInfusion.addRecipe(new ItemStack(Blocks.dirt), new ItemStack(Blocks.grass), 5, 50, false);

        for (String ore : OreDictionary.getOreNames()) {
            if (ore.startsWith("ore")) {
                String output = ore.substring(3);
                if (!OreDictionary.getOres(ore).isEmpty() && !OreDictionary.getOres("ingot" + output).isEmpty()) {
                    for (int i = 0; i < OreDictionary.getOres(ore).size(); i++) {
                        ItemStack oreLump = new ItemStack(ItemsRegistry.oreLump, 2, 0);
                        RandomUtils.checkAndSetCompound(oreLump);
                        oreLump.stackTagCompound.setString("ore", output);
                        oreLumpRecipes.add(RecipeCorruptedInfusion.addRecipe(oreLump, OreDictionary.getOres(ore).get(i), 20, 200, false));
                    }
                    ItemStack input = new ItemStack(ItemsRegistry.oreLump, 1, 0);
                    RandomUtils.checkAndSetCompound(input);
                    input.stackTagCompound.setString("ore", output);
                    oreLumpCleansing.add(RecipeBloodCleanser.addRecipe(input, OreDictionary.getOres("ingot" + output).get(0)));
                }
            }
        }

        if (!OreDictionary.getOres("dustWood").isEmpty()) {
            for (int i = 0; i < OreDictionary.getOres("logWood").size(); i++) {
                RecipeCorruptedInfusion.addRecipe(OreDictionary.getOres("dustWood").get(0), OreDictionary.getOres("logWood").get(i), 5, 50, false);
            }
        }
        RecipeBloodCleanser.addRecipe(new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.netherrack));
        imbuedStick = RecipeBloodCleanser.addRecipe(new ItemStack(Items.stick), RandomUtils.SanguimancyItemStacks.imbuedStick);

        ItemStack stackInterface = new ItemStack(BlocksRegistry.bloodInterface);
        ItemStack stackRune = new ItemStack(ModBlocks.bloodRune, 1, 0);
        if (ModList.computercraft.isLoaded()) {
            PeripheralProvider.register();
            ItemStack modem = GameRegistry.findItemStack(ModList.Names.COMPUTERCRAFT, "CC-Cable", 1);
            modem.setItemDamage(1);
            bloodInterface = GameRegistry.addShapedRecipe(stackInterface.copy(), "XYX", "XZX", "XXX", 'X', Blocks.stone, 'Y', stackRune, 'Z', modem);
        }
        if (ModList.opencomputers.isLoaded())
            bloodInterface = GameRegistry.addShapedRecipe(stackInterface.copy(), "XYX", "XZX", "XXX", 'X', Blocks.stone, 'Y', stackRune, 'Z', new ItemStack(GameRegistry.findBlock(ModList.Names.OPENCOMPUTERS, "adapter")));
        if (!(ModList.opencomputers.isLoaded() || ModList.computercraft.isLoaded()))
            bloodInterface = GameRegistry.addShapedRecipe(stackInterface.copy(), "XYX", "XZX", "XXX", 'X', Blocks.stone, 'Y', stackRune, 'Z', new ItemStack(Blocks.unpowered_comparator));
    }
}