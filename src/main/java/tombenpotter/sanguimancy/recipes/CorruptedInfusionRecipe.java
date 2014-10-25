package tombenpotter.sanguimancy.recipes;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import tombenpotter.sanguimancy.util.RandomUtils;

import java.util.LinkedList;
import java.util.List;

public class CorruptedInfusionRecipe {

    public final ItemStack fOutput;
    public final ItemStack[] fInput;
    public final int fMiniumCorruption;
    public final int fTime;
    public final boolean fExactAmountandNbt;

    public static List<CorruptedInfusionRecipe> recipeList = new LinkedList<CorruptedInfusionRecipe>();

    private CorruptedInfusionRecipe(ItemStack output, ItemStack[] input, int minimumCorruption, int time, boolean exactAmountandNbt) {
        this.fOutput = output;
        this.fInput = input;
        this.fMiniumCorruption = minimumCorruption;
        this.fTime = time;
        this.fExactAmountandNbt = exactAmountandNbt;
    }

    public static CorruptedInfusionRecipe addRecipe(ItemStack output, ItemStack[] input, int minimumCorruption, int time, boolean exactAmountandNbt) {
        CorruptedInfusionRecipe recipe = new CorruptedInfusionRecipe(output, input, minimumCorruption, time, exactAmountandNbt);
        recipeList.add(recipe);
        return recipe;
    }

    public static CorruptedInfusionRecipe addRecipe(ItemStack output, ItemStack input, int minimumCorruption, int time, boolean exactAmountandNbt) {
        CorruptedInfusionRecipe recipe = new CorruptedInfusionRecipe(output, new ItemStack[]{input}, minimumCorruption, time, exactAmountandNbt);
        recipeList.add(recipe);
        return recipe;
    }

    public static boolean isRecipeValid(ItemStack[] input, int playerCorruption) {
        for (CorruptedInfusionRecipe recipe : getPossibleRecipes(input, playerCorruption)) {
            if (recipe.fOutput != null && playerCorruption >= recipe.fMiniumCorruption) {
                return true;
            }
        }
        return false;
    }

    public static List<CorruptedInfusionRecipe> getPossibleRecipes(ItemStack[] input, int minimumCorruption) {
        List<CorruptedInfusionRecipe> returnList = new LinkedList<CorruptedInfusionRecipe>();
        for (CorruptedInfusionRecipe recipe : recipeList) {
            if (recipe.fMiniumCorruption <= minimumCorruption && RandomUtils.areStacksEqual(input, recipe.fInput, recipe.fExactAmountandNbt)) {
                returnList.add(recipe);
            }
        }
        return returnList;
    }

    public static List<CorruptedInfusionRecipe> getAllRecipes() {
        return ImmutableList.copyOf(recipeList);
    }
}
