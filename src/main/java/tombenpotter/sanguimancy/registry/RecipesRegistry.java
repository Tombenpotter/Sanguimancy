package tombenpotter.sanguimancy.registry;

import WayofTime.alchemicalWizardry.ModBlocks;
import WayofTime.alchemicalWizardry.ModItems;
import WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipe;
import WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipeRegistry;
import WayofTime.alchemicalWizardry.api.items.ShapedBloodOrbRecipe;
import bloodutils.api.registries.RecipeRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;
import tombenpotter.sanguimancy.util.Input_Output;
import tombenpotter.sanguimancy.util.RandomUtils;
import tombenpotter.sanguimancy.util.RecipeCorruption;
import tombenpotter.sanguimancy.util.SanguimancyItemStacks;

public class RecipesRegistry {

    public static IRecipe altarEmitter, sacrificeTransferrer, corruptionReader, unattunedPlayerSacrificer, corruptionCrystallizer, bloodTank, lumpCleaner;
    public static AltarRecipe altarDiviner, attunedPlayerSacrificer, corruptionCatalyst;
    public static Input_Output poisonousPotato, rottenFlesh, crackedStoneBricks, bonemeal, soulSand, corruptedDemonShard, cobblestone, gravel, sand, dirt;

    public static void registerShapedRecipes() {
        altarEmitter = GameRegistry.addShapedRecipe(SanguimancyItemStacks.altarEmitter, "XYX", "XZX", "XXX", 'X', Blocks.redstone_block, 'Y', Blocks.lever, 'Z', ModBlocks.blockAltar);
        sacrificeTransferrer = GameRegistry.addShapedRecipe(SanguimancyItemStacks.sacrificeTransferrer, "XAX", "YZY", "XYX", 'X', new ItemStack(ModItems.demonicSlate), 'A', new ItemStack(ModItems.lavaCrystal), 'Y', new ItemStack(Items.diamond), 'Z', new ItemStack(Blocks.heavy_weighted_pressure_plate));
        corruptionReader = GameRegistry.addShapedRecipe(SanguimancyItemStacks.corruptionReader, "AXA", "ZYB", "AXA", 'X', Blocks.soul_sand, 'Y', new ItemStack(ModItems.divinationSigil), 'Z', new ItemStack(Items.skull, 1, 1), 'A', Blocks.nether_brick, 'B', Items.ender_eye);
    }

    public static void registerAltarRecipes() {
        AltarRecipeRegistry.registerAltarRecipe(SanguimancyItemStacks.altarDiviner, new ItemStack(ModBlocks.blockAltar), 3, 3000, 10, 10, false);
        altarDiviner = RecipeRegistry.getLatestAltarRecipe();
        AltarRecipeRegistry.registerAltarRecipe(SanguimancyItemStacks.attunnedPlayerSacrificer, SanguimancyItemStacks.unattunedPlayerSacrificer, 5, 30000, 10, 10, false);
        attunedPlayerSacrificer = RecipeRegistry.getLatestAltarRecipe();
        AltarRecipeRegistry.registerAltarRecipe(SanguimancyItemStacks.corruptionCatalist, new ItemStack(Items.skull, 1, 1), 3, 3000, 10, 10, false);
        corruptionCatalyst = RecipeRegistry.getLatestAltarRecipe();
    }

    public static void registerOrbRecipes() {
        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(SanguimancyItemStacks.unattunedPlayerSacrificer, "XYX", "YOY", "XYX", 'X', new ItemStack(ModItems.demonicSlate), 'Y', new ItemStack(ModBlocks.emptySocket), 'O', new ItemStack(ModItems.archmageBloodOrb)));
        unattunedPlayerSacrificer = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(SanguimancyItemStacks.corruptionCrystallizer, "XYX", "ZAZ", "XBX", 'X', new ItemStack(Blocks.obsidian), 'Y', new ItemStack(ModBlocks.bloodSocket), 'Z', new ItemStack(Blocks.diamond_block), 'A', SanguimancyItemStacks.corruptedDemonShard, 'B', new ItemStack(ModItems.archmageBloodOrb)));
        corruptionCrystallizer = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(SanguimancyItemStacks.bloodTank, "XXX", "XYX", "XXX", 'X', new ItemStack(Blocks.stained_glass, 1, 14), 'Y', new ItemStack(ModItems.apprenticeBloodOrb)));
        bloodTank = RecipeRegistry.getLatestCraftingRecipe();
        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(SanguimancyItemStacks.lumpCleaner, "XXX", "YZY", "ABA", 'X', SanguimancyItemStacks.oreLump, 'Y', SanguimancyItemStacks.bloodTank, 'Z', new ItemStack(ModItems.masterBloodOrb), 'A', new ItemStack(Blocks.iron_block), 'B', new ItemStack(Blocks.diamond_block)));
        lumpCleaner = RecipeRegistry.getLatestCraftingRecipe();
    }

    public static void registercorruptionRecipes() {
        poisonousPotato = RecipeCorruption.addRecipe(new ItemStack(Items.potato), new ItemStack(Items.poisonous_potato), 50, 5);
        rottenFlesh = RecipeCorruption.addRecipe(new ItemStack(Items.beef, 1), new ItemStack(Items.rotten_flesh, 1), 50, 5);
        crackedStoneBricks = RecipeCorruption.addRecipe(new ItemStack(Blocks.stonebrick, 1, 0), new ItemStack(Blocks.stonebrick, 1, 2), 50, 5);
        bonemeal = RecipeCorruption.addRecipe(new ItemStack(Items.bone), new ItemStack(Items.dye, 6, 15), 70, 10);
        soulSand = RecipeCorruption.addRecipe(new ItemStack(Blocks.sand), new ItemStack(Blocks.soul_sand), 100, 10);
        corruptedDemonShard = RecipeCorruption.addRecipe(new ItemStack(ModItems.demonBloodShard), SanguimancyItemStacks.corruptedDemonShard, 500, 50);
        cobblestone = RecipeCorruption.addRecipe(new ItemStack(Blocks.stone), new ItemStack(Blocks.cobblestone), 50, 5);
        gravel = RecipeCorruption.addRecipe(new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.gravel), 50, 5);
        sand = RecipeCorruption.addRecipe(new ItemStack(Blocks.gravel), new ItemStack(Blocks.sand), 50, 5);
        dirt = RecipeCorruption.addRecipe(new ItemStack(Blocks.grass), new ItemStack(Blocks.dirt), 50, 5);

        for (String ore : OreDictionary.getOreNames()) {
            if (ore.startsWith("ore")) {
                String output = ore.substring(3);
                if (!OreDictionary.getOres(ore).isEmpty() && !OreDictionary.getOres("ingot" + output).isEmpty()) {
                    for (int i = 0; i < OreDictionary.getOres(ore).size(); i++) {
                        ItemStack oreLump = new ItemStack(ItemsRegistry.oreLump, 2, 0);
                        RandomUtils.checkAndSetCompound(oreLump);
                        oreLump.stackTagCompound.setString("ore", output);
                        RecipeCorruption.addRecipe(OreDictionary.getOres(ore).get(i), oreLump, 200, 20);
                    }
                }
            }
        }
    }
}