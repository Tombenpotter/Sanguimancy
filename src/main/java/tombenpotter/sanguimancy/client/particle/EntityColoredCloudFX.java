package tombenpotter.sanguimancy.client.particle;

import net.minecraft.client.particle.EntityCloudFX;
import net.minecraft.world.World;

public class EntityColoredCloudFX extends EntityCloudFX {

    public EntityColoredCloudFX(World world, double x, double y, double z, double movX, double movY, double movZ, float red, float green, float blue) {
        super(world, x, y, z, movX, movY, movZ);
        this.particleRed = red / 255.0F;
        this.particleGreen = green / 255.0F;
        this.particleBlue = blue / 255.0F;
    }
}
