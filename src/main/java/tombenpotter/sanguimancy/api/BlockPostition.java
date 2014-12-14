package tombenpotter.sanguimancy.api;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPostition {

    public double x;
    public double y;
    public double z;

    public BlockPostition(double posX, double posY, double posZ) {
        x = posX;
        y = posY;
        z = posZ;
    }

    public BlockPostition(TileEntity tile) {
        x = tile.xCoord;
        y = tile.yCoord;
        z = tile.zCoord;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof BlockPostition ? (((BlockPostition) o).x == this.x && ((BlockPostition) o).y == this.y && ((BlockPostition) o).z == this.z) : false;
    }

    @Override
    public int hashCode() {
        return (int) (this.x * 1912 + this.y * 1219 + this.z);
    }

    public Block getBlock(World world) {
        return world.getBlock((int) x, (int) y, (int) z);
    }

    public TileEntity getTile(World world) {
        return world.getTileEntity((int) x, (int) y, (int) z);
    }

    public void readFromNBT(NBTTagCompound tag) {
        x = tag.getCompoundTag("BlockPostition").getDouble("posX");
        y = tag.getCompoundTag("BlockPostition").getDouble("posY");
        z = tag.getCompoundTag("BlockPostition").getDouble("posZ");
    }

    public void writeToNBT(NBTTagCompound tag) {
        tag.getCompoundTag("BlockPostition").setDouble("posX", x);
        tag.getCompoundTag("BlockPostition").setDouble("posY", y);
        tag.getCompoundTag("BlockPostition").setDouble("posZ", z);
    }
}
