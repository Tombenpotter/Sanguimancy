package tombenpotter.sanguimancy.tiles;

import net.minecraft.util.ITickable;
import tombenpotter.sanguimancy.api.tiles.TileBase;

public class TileIllusion extends TileBase implements ITickable {

    public TileIllusion() {
    }

    @Override
    public void update() {
        if (noCooldown())
            worldObj.setBlockToAir(pos);
    }
}
