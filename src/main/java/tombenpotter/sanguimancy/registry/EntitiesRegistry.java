package tombenpotter.sanguimancy.registry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import tombenpotter.oldsanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.entity.EntityChickenMinion;

public class EntitiesRegistry {

    public static void registerEntities() {
        registerEntity(EntityChickenMinion.class, "EntityChickenMinion", 1, 0x59815, 0xE545);
    }

    public static void registerEntity(Class<? extends Entity> entity, String name, int id, int colPrim, int colSec) {
        EntityRegistry.registerModEntity(EntityChickenMinion.class, name, id, Sanguimancy.instance, 80, 3, true);
        EntityList.addMapping(entity, name, 300 + id, colPrim, colSec);
    }
}
