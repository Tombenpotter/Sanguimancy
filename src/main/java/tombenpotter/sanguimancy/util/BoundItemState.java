package tombenpotter.sanguimancy.util;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.io.Serializable;

public class BoundItemState implements Serializable {

    public int x;
    public int y;
    public int z;
    public int dimID;
    public boolean activated;

    public BoundItemState(int xPos, int yPos, int zPos, int dimensionID, boolean isActivated) {
        x = xPos;
        y = yPos;
        z = zPos;
        dimID = dimensionID;
        activated = isActivated;
    }

    public void readFromNBT(NBTTagCompound tag) {
        x = tag.getCompoundTag("BoundItemState").getInteger("posX");
        y = tag.getCompoundTag("BoundItemState").getInteger("posY");
        z = tag.getCompoundTag("BoundItemState").getInteger("posZ");
        dimID = tag.getCompoundTag("BoundItemState").getInteger("dimID");
        activated = tag.getCompoundTag("BoundItemState").getBoolean("activated");
    }

    public void writeToNBT(NBTTagCompound tag) {
        tag.getCompoundTag("BoundItemState").setInteger("posX", x);
        tag.getCompoundTag("BoundItemState").setInteger("posY", y);
        tag.getCompoundTag("BoundItemState").setInteger("posZ", z);
        tag.getCompoundTag("BoundItemState").setInteger("dimID", dimID);
        tag.getCompoundTag("BoundItemState").setBoolean("activated", activated);
    }

    public WorldServer getWorldServer() {
        return MinecraftServer.getServer().worldServerForDimension(dimID);
    }

    public Block getBlock(World world) {
        return world.getBlock(x, y, z);
    }

    public TileEntity getTileEntity(World world) {
        return world.getTileEntity(x, y, z);
    }
}
