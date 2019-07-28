package tombenpotter.sanguimancy.registry;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.entity.EntityChickenMinion;

public class EntitiesRegistry {

    public static void registerEntities() {
        registerEntity(EntityChickenMinion.class, "EntityChickenMinion", 1, 0x59815, 0xE545);
    }

    private static void registerEntity(Class<? extends Entity> entity, String name, int id, int colPrim, int colSec) {
    	EntityRegistry.registerModEntity(new ResourceLocation(Sanguimancy.modid, name), EntityChickenMinion.class, name, id, Sanguimancy.instance, 80, 3, true, colPrim, colSec);
    }
}
