package tombenpotter.sanguimancy.util;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.io.Serializable;

public class BlockLocation implements Serializable {

    public int x;
    public int y;
    public int z;
    public int dimID;

    public BlockLocation(int xPos, int yPos, int zPos) {
        x = xPos;
        y = yPos;
        z = zPos;
        dimID = 0;
    }

    public BlockLocation(int xPos, int yPos, int zPos, int dimensionID) {
        x = xPos;
        y = yPos;
        z = zPos;
        dimID = dimensionID;
    }

    public BlockLocation(TileEntity tile) {
        x = tile.xCoord;
        y = tile.yCoord;
        z = tile.zCoord;
        dimID = tile.getWorldObj().provider.dimensionId;
    }

    public BlockLocation(Entity entity) {
        x = (int) entity.posX;
        y = (int) entity.posY;
        z = (int) entity.posZ;
        dimID = entity.worldObj.provider.dimensionId;
    }

    public void readFromNBT(NBTTagCompound tag) {
        x = tag.getCompoundTag("BlockLocation").getInteger("posX");
        y = tag.getCompoundTag("BlockLocation").getInteger("posY");
        z = tag.getCompoundTag("BlockLocation").getInteger("posZ");
        dimID = tag.getCompoundTag("BlockLocation").getInteger("dimID");
    }

    public void writeToNBT(NBTTagCompound tag) {
        tag.getCompoundTag("BlockLocation").setInteger("posX", x);
        tag.getCompoundTag("BlockLocation").setInteger("posY", y);
        tag.getCompoundTag("BlockLocation").setInteger("posZ", z);
        tag.getCompoundTag("BlockLocation").setInteger("dimID", dimID);
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
