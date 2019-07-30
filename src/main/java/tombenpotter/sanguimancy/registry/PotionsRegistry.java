package tombenpotter.sanguimancy.registry;

import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.potion.PotionAddHeart;
import tombenpotter.sanguimancy.potion.PotionRemoveHeart;

import java.awt.*;

public class PotionsRegistry {

    public static Potion potionAddHeart;
    public static Potion potionRemoveHeart;

    public static void registerPotions() {
        potionAddHeart = new PotionAddHeart(Color.RED.getRGB()).setRegistryName(Sanguimancy.modid, "AddHeartPotion").setPotionName("AddHeart");
        GameRegistry.register(potionAddHeart);

        potionRemoveHeart = new PotionRemoveHeart(Color.GRAY.getRGB()).setRegistryName(Sanguimancy.modid, "RemoveHeartPotion").setPotionName("RemoveHeart");
        GameRegistry.register(potionRemoveHeart);
    }
}
