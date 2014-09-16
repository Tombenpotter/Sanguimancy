package tombenpotter.sanguimancy.registry;

import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.entity.EntityChickenMinion;
import tombenpotter.sanguimancy.entity.EntityPlayerPointer;

public class EntitiesRegistry {

    private static final Class<? extends EntityLiving> minionChicken = EntityChickenMinion.class;

    public static void registerEntities() {
        EntityRegistry.registerModEntity(EntityChickenMinion.class, "EntityChickenMinion", 1, Sanguimancy.instance, 80, 3, true);
        registerEntityEgg(minionChicken, 0x59815, 0xE545);
        EntityRegistry.registerModEntity(EntityPlayerPointer.class, "EntityPlayerPointer", 2, Sanguimancy.instance, 80, 3, true);
    }

    public static void registerEntityEgg(Class<? extends Entity> entity, int colPrim, int colSec) {
        int id = getUniqueEntityID();
        EntityList.IDtoClassMapping.put(id, entity);
        EntityList.entityEggs.put(id, new EntityList.EntityEggInfo(id, colPrim, colSec));
        return;
    }

    private static int getUniqueEntityID() {
        int startEID = 300;
        do {
            startEID++;
        } while (EntityList.getStringFromID(startEID) != null);
        return startEID;
    }
}
