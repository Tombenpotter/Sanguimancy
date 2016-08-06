package tombenpotter.sanguimancy.api.objects;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPostition {

    public int x;
    public int y;
    public int z;

    public BlockPostition(int posX, int posY, int posZ) {
        x = posX;
        y = posY;
        z = posZ;
    }

    public BlockPostition(TileEntity tile) {
        x = tile.xCoord;
        y = tile.yCoord;
        z = tile.zCoord;
    }

    public static BlockPostition readFromNBT(NBTTagCompound tag) {
        return new BlockPostition(tag.getCompoundTag("BlockPostition").getInteger("posX"),
                tag.getCompoundTag("BlockPostition").getInteger("posY"),
                tag.getCompoundTag("BlockPostition").getInteger("posZ"));
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
        return world.getBlock(x, y, z);
    }

    public TileEntity getTile(World world) {
        return world.getTileEntity(x, y, z);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        tag.getCompoundTag("BlockPostition").setInteger("posX", x);
        tag.getCompoundTag("BlockPostition").setInteger("posY", y);
        tag.getCompoundTag("BlockPostition").setInteger("posZ", z);
        return tag;
    }

    @Override
    public String toString() {
        return "BlockPostition {x= " + x + ", y= " + y + ", z= " + z + '}';
    }
}
