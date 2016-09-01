package tombenpotter.sanguimancy.registry;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.items.ItemBloodAmulet;
import tombenpotter.sanguimancy.items.ItemPlayerSacrificer;
import tombenpotter.sanguimancy.items.ItemResource;

public class ItemsRegistry {

    public static Item playerSacrificer;
    public static Item bloodAmulet;
    public static Item resource;

    /*
    public static Item wand;
    public static Item corruptedSword;
    public static Item corruptedPickaxe;
    public static Item corruptedShovel;
    public static Item corruptedAxe;
    */

    public static void registerItems() {
        playerSacrificer = new ItemPlayerSacrificer().setRegistryName(Sanguimancy.modid, "playerSacrificer");
        GameRegistry.register(playerSacrificer);

        bloodAmulet = new ItemBloodAmulet().setRegistryName(Sanguimancy.modid, "bloodAmulet");
        GameRegistry.register(bloodAmulet);

        resource = new ItemResource().setRegistryName(Sanguimancy.modid, "resource");
        GameRegistry.register(resource);


        /*
        wand = new ItemWand();
        GameRegistry.registerItem(wand, "wand");
        corruptedSword = new ItemCorruptedSword(32);
        GameRegistry.registerItem(corruptedSword, "corruptedSword");

        corruptedPickaxe = new ItemCorruptedPickaxe(RandomUtils.corruptedMaterial);
        GameRegistry.registerItem(corruptedPickaxe, "corruptedPickaxe");

        corruptedShovel = new ItemCorruptedShovel(RandomUtils.corruptedMaterial);
        GameRegistry.registerItem(corruptedShovel, "corruptedShovel");

        corruptedAxe = new ItemCorruptedAxe(RandomUtils.corruptedMaterial);
        GameRegistry.registerItem(corruptedAxe, "corruptedAxe");
        */
    }
}
