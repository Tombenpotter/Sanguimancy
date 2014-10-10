package tombenpotter.sanguimancy.util;

import net.minecraft.item.ItemStack;

public class Input_Output {

    private ItemStack input;
    private ItemStack output;
    private int chance;
    private int miniumCorruption;

    public Input_Output(ItemStack input, ItemStack output, int chance, int miniumCorruption) {
        this.input = input.copy();
        this.output = output.copy();
        this.chance = chance;
        this.miniumCorruption = miniumCorruption;
    }

    public ItemStack getInput() {
        return input.copy();
    }

    public ItemStack getOutput() {
        return output.copy();
    }

    public int getChance() {
        return chance;
    }

    public int getMiniumCorruption() {
        return miniumCorruption;
    }
}