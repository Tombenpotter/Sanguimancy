package tombenpotter.sanguimancy.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import tombenpotter.sanguimancy.items.ItemPlayerSacrificer;
import tombenpotter.sanguimancy.items.ItemSoulCorruptionTest;

public class ItemsRegistry {

    public static Item playerSacrificer;
    public static Item soulCorruptionTest;

    public static void registerItems() {
        playerSacrificer = new ItemPlayerSacrificer();
        GameRegistry.registerItem(playerSacrificer, "playerSacrificer");

        soulCorruptionTest = new ItemSoulCorruptionTest();
        GameRegistry.registerItem(soulCorruptionTest, "soulCorruptionTest");
    }
}
