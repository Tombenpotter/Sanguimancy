package tombenpotter.sanguimancy.tile;

import net.minecraft.tileentity.TileEntity;

public class TileIllusion extends TileEntity {
    public int timer;

    public TileIllusion() {
        timer = 600;
    }

    @Override
    public void updateEntity() {
        if (timer <= 0) this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
        else timer--;
    }
}