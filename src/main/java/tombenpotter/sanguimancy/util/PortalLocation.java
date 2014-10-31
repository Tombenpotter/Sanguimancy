package tombenpotter.sanguimancy.util;

import net.minecraft.nbt.NBTTagCompound;

import java.io.Serializable;

public class PortalLocation implements Serializable {

    public int x;
    public int y;
    public int z;
    public int dimension;

    public PortalLocation(int x, int y, int z, int dimension) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dimension = dimension;
    }

    public static PortalLocation readFromNBT(NBTTagCompound tag) {
        if (tag.hasKey("PortalLocation")) {
            NBTTagCompound locationTag = tag.getCompoundTag("PortalLocation");
            return new PortalLocation(locationTag.getInteger("x"), locationTag.getInteger("y"), locationTag.getInteger("z"), locationTag.getInteger("dimension"));
        }
        return null;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        NBTTagCompound locationTag = new NBTTagCompound();
        locationTag.setInteger("x", x);
        locationTag.setInteger("y", y);
        locationTag.setInteger("z", z);
        locationTag.setInteger("dimension", dimension);
        tag.setTag("PortalLocation", locationTag);
        return tag;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PortalLocation)) {
            return false;
        }
        PortalLocation portalLocation = (PortalLocation) o;
        if (portalLocation.x != this.x || portalLocation.y != this.y || portalLocation.z != this.z) {
            return false;
        }
        return true;
    }
}
