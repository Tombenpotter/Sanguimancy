package tombenpotter.sanguimancy.registry;

import WayofTime.alchemicalWizardry.ModBlocks;
import WayofTime.alchemicalWizardry.ModItems;
import WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipeRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import joshie.alchemicalWizardy.ShapedBloodOrbRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RecipesRegistry {

    public static void registerShapedRecipes() {
        GameRegistry.addShapedRecipe(new ItemStack(BlocksRegistry.altarEmitter), "XYX", "XZX", "XXX", 'X', Blocks.redstone_block, 'Y', Blocks.lever, 'Z', ModBlocks.blockAltar);
        GameRegistry.addShapedRecipe(new ItemStack(BlocksRegistry.sacrificeTransfer), "XAX", "YZY", "XYX", 'X', new ItemStack(ModItems.demonicSlate), 'A', new ItemStack(ModItems.lavaCrystal), 'Y', new ItemStack(Items.diamond), 'Z', new ItemStack(Blocks.heavy_weighted_pressure_plate));
    }

    public static void registerAltarRecipes() {
        AltarRecipeRegistry.registerAltarRecipe(new ItemStack(BlocksRegistry.altarDiviner), new ItemStack(ModBlocks.blockAltar), 3, 3000, 10, 10, false);
        AltarRecipeRegistry.registerAltarRecipe(new ItemStack(ItemsRegistry.playerSacrificer, 1, 1), new ItemStack(ItemsRegistry.playerSacrificer, 1, 0), 5, 30000, 10, 10, false);
    }

    public static void registerOrbRecipes() {
        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(new ItemStack(ItemsRegistry.playerSacrificer, 1, 0), "XYX", "YOY", "XYX", 'X', new ItemStack(ModItems.demonicSlate), 'Y', new ItemStack(ModBlocks.emptySocket), 'O', new ItemStack(ModItems.archmageBloodOrb)));
    }
}
