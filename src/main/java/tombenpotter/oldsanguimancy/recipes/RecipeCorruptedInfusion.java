package tombenpotter.oldsanguimancy.recipes;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import tombenpotter.sanguimancy.util.RandomUtils;

import java.util.LinkedList;
import java.util.List;

public class RecipeCorruptedInfusion {

    public static List<RecipeCorruptedInfusion> recipeList = new LinkedList<RecipeCorruptedInfusion>();
    public final ItemStack fOutput;
    public final ItemStack[] fInput;
    public final int fMiniumCorruption;
    public final int fTime;
    public final boolean fExactAmountandNbt;

    private RecipeCorruptedInfusion(ItemStack output, ItemStack[] input, int minimumCorruption, int time, boolean exactAmountandNbt) {
        this.fOutput = output;
        this.fInput = input;
        this.fMiniumCorruption = minimumCorruption;
        this.fTime = time;
        this.fExactAmountandNbt = exactAmountandNbt;
    }

    public static RecipeCorruptedInfusion addRecipe(ItemStack output, ItemStack[] input, int minimumCorruption, int time, boolean exactAmountandNbt) {
        RecipeCorruptedInfusion recipe = new RecipeCorruptedInfusion(output, input, minimumCorruption, time, exactAmountandNbt);
        recipeList.add(recipe);
        return recipe;
    }

    public static RecipeCorruptedInfusion addRecipe(ItemStack output, ItemStack input, int minimumCorruption, int time, boolean exactAmountandNbt) {
        return addRecipe(output, new ItemStack[]{input}, minimumCorruption, time, exactAmountandNbt);
    }

    public static boolean isRecipeValid(ItemStack[] input, int playerCorruption) {
        for (RecipeCorruptedInfusion recipe : getPossibleRecipes(input, playerCorruption)) {
            if (recipe.fOutput != null && playerCorruption >= recipe.fMiniumCorruption) {
                return true;
            }
        }
        return false;
    }

    public static List<RecipeCorruptedInfusion> getPossibleRecipes(ItemStack[] input, int minimumCorruption) {
        List<RecipeCorruptedInfusion> returnList = new LinkedList<RecipeCorruptedInfusion>();
        for (RecipeCorruptedInfusion recipe : recipeList) {
            if (recipe.fMiniumCorruption <= minimumCorruption && RandomUtils.areStacksEqual(input, recipe.fInput, recipe.fExactAmountandNbt)) {
                returnList.add(recipe);
            }
        }
        return returnList;
    }

    public static List<RecipeCorruptedInfusion> getAllRecipes() {
        return ImmutableList.copyOf(recipeList);
    }
}
