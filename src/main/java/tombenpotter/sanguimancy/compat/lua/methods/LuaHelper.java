package tombenpotter.sanguimancy.compat.lua.methods;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.LinkedHashMap;
import java.util.Map;

public class LuaHelper {
    public static Map<String, Object> stackToMap(ItemStack stack) {
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        if (stack == null || stack.getItem() == null) return null;
        GameRegistry.UniqueIdentifier id = GameRegistry.findUniqueIdentifierFor(stack.getItem());
        if (id == null) return null;
        result.put("item", id.toString());
        result.put("quantity", stack.getCount());
        result.put("damage", stack.getItemDamage());
        result.put("name", stack.getDisplayName());
        if (stack.hasTagCompound())
            result.put("nbt", stack.getTagCompound().toString());
        return result;
    }
}
