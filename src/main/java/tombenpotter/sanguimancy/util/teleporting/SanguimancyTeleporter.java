package tombenpotter.sanguimancy.util.teleporting;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class SanguimancyTeleporter extends Teleporter {

    public SanguimancyTeleporter(WorldServer worldServer) {
        super(worldServer);
    }

    @Override
    public boolean makePortal(Entity p_85188_1_) {
        return true;
    }

    @Override
    public void removeStalePortalLocations(long p_85189_1_) {
    }

    @Override
    public boolean placeInExistingPortal(Entity entity, double x, double y, double z, float p_77184_8_) {
        return true;
    }

    @Override
    public void placeInPortal(Entity entity, double x, double y, double z, float p_77185_8_) {
        entity.setLocationAndAngles(x, y, z, entity.rotationPitch, entity.rotationYaw);
    }
}
