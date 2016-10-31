package tombenpotter.sanguimancy.api.registries;

import WayofTime.bloodmagic.api.registry.AltarRecipeRegistry;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import java.util.ArrayList;

public class RecipeRegistry {
    public static ArrayList<IRecipe> craftingRecipes = new ArrayList<IRecipe>();
    public static ArrayList<AltarRecipeRegistry.AltarRecipe> altarRecipes = new ArrayList<AltarRecipeRegistry.AltarRecipe>();

    /**
     * Used to register crafting recipes to the guide
     */
    public static IRecipe getLatestCraftingRecipe() {
        IRecipe rec = (IRecipe) CraftingManager.getInstance().getRecipeList().get(CraftingManager.getInstance().getRecipeList().size() - 1);
        craftingRecipes.add(rec);
        return craftingRecipes.get(craftingRecipes.size() - 1);
    }
}