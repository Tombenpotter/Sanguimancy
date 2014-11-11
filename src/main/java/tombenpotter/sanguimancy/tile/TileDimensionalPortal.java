package tombenpotter.sanguimancy.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileDimensionalPortal extends TileEntity {

    public String portalID;
    public int masterStoneX;
    public int masterStoneY;
    public int masterStoneZ;

    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        portalID = tag.getString("PortalRitualID");
        masterStoneX = tag.getInteger("masterStoneX");
        masterStoneY = tag.getInteger("masterStoneY");
        masterStoneZ = tag.getInteger("masterStoneZ");
    }

    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setString("PortalRitualID", portalID);
        tag.setInteger("masterStoneX", masterStoneX);
        tag.setInteger("masterStoneY", masterStoneY);
        tag.setInteger("masterStoneZ", masterStoneZ);
    }
}
