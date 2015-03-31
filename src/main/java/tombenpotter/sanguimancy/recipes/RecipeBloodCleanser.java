package tombenpotter.sanguimancy.recipes;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RecipeBloodCleanser {
    public static List<RecipeBloodCleanser> recipeList = new LinkedList<RecipeBloodCleanser>();
    public final ItemStack fInput;
    public final ItemStack fOutput;

    private RecipeBloodCleanser(ItemStack input, ItemStack output) {
        this.fInput = input;
        this.fOutput = output;
    }

    public static RecipeBloodCleanser addRecipe(ItemStack input, ItemStack output) {
        RecipeBloodCleanser recipe = new RecipeBloodCleanser(input, output);
        recipeList.add(recipe);
        return recipe;
    }

    public static RecipeBloodCleanser getRecipe(ItemStack input) {
        for (RecipeBloodCleanser recipe : recipeList) {
            if (recipe != null)
                if (recipe.fInput.isItemEqual(input) && recipe.fInput.stackTagCompound.equals(input.stackTagCompound)) {
                    return recipe;
                }
        }
        return null;
    }

    public static List<RecipeBloodCleanser> getRecipes(ItemStack input) {
        List<RecipeBloodCleanser> out = new ArrayList<RecipeBloodCleanser>();
        if (input == null)
            return null;
        for (RecipeBloodCleanser r : recipeList) {
            if (r.fInput.isItemEqual(input))
                out.add(r);
        }
        return out;
    }

    public static boolean isRecipeValid(ItemStack input) {
        RecipeBloodCleanser recipe = getRecipe(input);
        if (recipe != null)
            if (recipe.fInput.isItemEqual(input) && recipe.fOutput != null && recipe.fInput.stackTagCompound.equals(input.stackTagCompound)) {
                return true;
            }
        return false;
    }

    public static List<RecipeBloodCleanser> getAllRecipes() {
        return ImmutableList.copyOf(recipeList);
    }
}
