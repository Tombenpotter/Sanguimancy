package tombenpotter.bloodWizardry.registry;

import WayofTime.alchemicalWizardry.ModBlocks;
import WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipeRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class RecipesRegistry {

    public static void registerShapedRecipes() {
        GameRegistry.addShapedRecipe(new ItemStack(BlocksRegistry.altarEmitter), "XYX", "XZX", "XXX", 'X', Blocks.redstone_block, 'Y', Blocks.lever, 'Z', ModBlocks.blockAltar);
    }

    public static void registerAltarRecipes() {
        AltarRecipeRegistry.registerAltarRecipe(new ItemStack(BlocksRegistry.altarDiviner), new ItemStack(ModBlocks.blockAltar), 3, 3000, 50, 50, false);
    }
}
