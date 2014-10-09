package tombenpotter.sanguimancy.registry;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import tombenpotter.sanguimancy.util.Input_Output;

import java.util.ArrayList;
import java.util.List;

public class RecipeCorruption {

    public static ArrayList<Input_Output> list = new ArrayList<Input_Output>();

    public static void addRecipe(ItemStack input, ItemStack output, int chance, int minimumCorruption) {
        Input_Output recipe = new Input_Output(input, output, chance, minimumCorruption);
        list.add(recipe);
    }

    public static ItemStack getOutput(ItemStack stack) {
        if (stack == null) return null;
        for (Input_Output recipe : list) {
            if (recipe.getInput().isItemEqual(stack)) return recipe.getOutput();
        }
        return null;
    }

    public static Input_Output getRecipeFromStack(ItemStack stack) {
        if (stack == null)
            return null;
        for (Input_Output recipe : list) {
            if (recipe.getInput().isItemEqual(stack)) return recipe;
        }
        return null;
    }

    public static List<Input_Output> getAllRecipes() {
        return ImmutableList.copyOf(list);
    }
}
