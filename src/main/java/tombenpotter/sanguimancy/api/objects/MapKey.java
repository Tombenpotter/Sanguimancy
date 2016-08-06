package tombenpotter.sanguimancy.api.objects;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

//This class was made by Hilburn. He just gave me permission to use it.
public class MapKey {

    int hashcode;

    public MapKey(ItemStack stack) {
        hashcode = stack.getItem().hashCode() ^ stack.getItemDamage();
    }

    public MapKey(FluidStack stack) {
        hashcode = stack.getFluid().hashCode();
    }

    public static MapKey getKey(ItemStack stack) {
        if (stack == null || stack.getItem() == null) return null;
        return new MapKey(stack);
    }

    public static MapKey getKey(FluidStack stack) {
        if (stack == null) return null;
        return new MapKey(stack);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof MapKey)
            return hashcode == ((MapKey) o).hashcode;
        return false;
    }

    @Override
    public int hashCode() {
        return hashcode;
    }
}