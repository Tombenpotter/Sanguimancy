package tombenpotter.sanguimancy.tiles;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileIllusion extends TileEntity implements ITickable {
    public int timer;

    public TileIllusion() {
        timer = 600;
    }

    @Override
    public void update() {
        if (timer <= 0) this.world.setBlockToAir(this.getPos());
        else timer--;
    }
}