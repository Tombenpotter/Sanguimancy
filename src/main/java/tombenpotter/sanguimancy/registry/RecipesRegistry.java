package tombenpotter.sanguimancy.registry;

import WayofTime.alchemicalWizardry.ModBlocks;
import WayofTime.alchemicalWizardry.ModItems;
import WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipeRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import joshie.alchemicalWizardy.ShapedBloodOrbRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import tombenpotter.sanguimancy.util.SanguimancyItemStacks;

public class RecipesRegistry {

    public static void registerShapedRecipes() {
        GameRegistry.addShapedRecipe(SanguimancyItemStacks.altarEmitter, "XYX", "XZX", "XXX", 'X', Blocks.redstone_block, 'Y', Blocks.lever, 'Z', ModBlocks.blockAltar);
        GameRegistry.addShapedRecipe(SanguimancyItemStacks.sacrificeTransferrer, "XAX", "YZY", "XYX", 'X', new ItemStack(ModItems.demonicSlate), 'A', new ItemStack(ModItems.lavaCrystal), 'Y', new ItemStack(Items.diamond), 'Z', new ItemStack(Blocks.heavy_weighted_pressure_plate));
        GameRegistry.addShapedRecipe(SanguimancyItemStacks.corruptionReader, "AXA", "ZYB", "AXA", 'X', Blocks.soul_sand, 'Y', new ItemStack(ModItems.divinationSigil), 'Z', new ItemStack(Items.skull, 1, 1), 'A', Blocks.nether_brick, 'B', Items.ender_eye);
    }

    public static void registerAltarRecipes() {
        AltarRecipeRegistry.registerAltarRecipe(SanguimancyItemStacks.altarDiviner, new ItemStack(ModBlocks.blockAltar), 3, 3000, 10, 10, false);
        AltarRecipeRegistry.registerAltarRecipe(SanguimancyItemStacks.attunnedPlayerSacrificer, SanguimancyItemStacks.unattunedPlayerSacrificer, 5, 30000, 10, 10, false);
    }

    public static void registerOrbRecipes() {
        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(SanguimancyItemStacks.unattunedPlayerSacrificer, "XYX", "YOY", "XYX", 'X', new ItemStack(ModItems.demonicSlate), 'Y', new ItemStack(ModBlocks.emptySocket), 'O', new ItemStack(ModItems.archmageBloodOrb)));
    }
}