package tombenpotter.sanguimancy.api.bloodutils.api.compact;

import net.minecraft.item.ItemStack;

public class Category {
    public String name;
    public ItemStack iconStack;

    public Category(String name, ItemStack iconStack) {
        this.name = name;
        this.iconStack = iconStack;
    }
}