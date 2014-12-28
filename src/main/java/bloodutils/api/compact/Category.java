package bloodutils.api.compact;

import bloodutils.api.enums.EnumType;
import net.minecraft.item.ItemStack;

public class Category {
    public String name;
    public ItemStack iconStack;
    public EnumType type;

    public Category(String name, ItemStack iconStack, EnumType type) {
        this.name = name;
        this.iconStack = iconStack;
        this.type = type;
    }
}