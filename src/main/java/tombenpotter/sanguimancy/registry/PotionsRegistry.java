package tombenpotter.sanguimancy.registry;

import net.minecraft.potion.Potion;
import tombenpotter.sanguimancy.potion.PotionAddHeart;
import tombenpotter.sanguimancy.potion.PotionRemoveHeart;
import tombenpotter.sanguimancy.util.ConfigHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class PotionsRegistry {

    public static Potion potionAddHeart;
    public static Potion potionRemoveHeart;

    public static void potionPreInit() {
        Potion[] potionTypes = null;
        for (Field f : Potion.class.getDeclaredFields()) {
            f.setAccessible(true);
            try {
                if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a")) {
                    Field modField = Field.class.getDeclaredField("modifiers");
                    modField.setAccessible(true);
                    modField.setInt(f, f.getModifiers() & ~Modifier.FINAL);
                    potionTypes = (Potion[]) f.get(null);
                    final Potion[] newPotionTypes = new Potion[potionTypes.length];
                    System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
                    f.set(null, newPotionTypes);
                }
            } catch (Exception e) {
                System.err.println("Severe error during potions pre-init, please report this to the mod author:");
                System.err.println(e);
            }
        }
    }

    public static void registerPotions() {
        potionAddHeart = new PotionAddHeart(ConfigHandler.addHeartPotionID, false, 0).setIconIndex(0, 0).setPotionName("AddHeart");
        potionRemoveHeart = new PotionRemoveHeart(ConfigHandler.removeHeartPotionID, true, 0).setIconIndex(0, 0).setPotionName("RemoveHeart");
    }
}
