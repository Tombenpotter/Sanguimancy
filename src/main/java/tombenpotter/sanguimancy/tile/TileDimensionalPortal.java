package tombenpotter.sanguimancy.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import tombenpotter.sanguimancy.util.interfaces.ICustomNBTTag;

public class TileDimensionalPortal extends TileEntity implements ICustomNBTTag {

    public String portalID;
    public int masterStoneX;
    public int masterStoneY;
    public int masterStoneZ;
    private NBTTagCompound custoomNBTTag;

    public TileDimensionalPortal() {
        custoomNBTTag = new NBTTagCompound();
    }

    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        portalID = tagCompound.getString("PortalRitualID");
        masterStoneX = tagCompound.getInteger("masterStoneX");
        masterStoneY = tagCompound.getInteger("masterStoneY");
        masterStoneZ = tagCompound.getInteger("masterStoneZ");
        custoomNBTTag = tagCompound.getCompoundTag("customNBTTag");
    }

    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setString("PortalRitualID", portalID);
        tagCompound.setInteger("masterStoneX", masterStoneX);
        tagCompound.setInteger("masterStoneY", masterStoneY);
        tagCompound.setInteger("masterStoneZ", masterStoneZ);
        tagCompound.setTag("customNBTTag", custoomNBTTag);
    }

    @Override
    public NBTTagCompound getCustomNBTTag() {
        return custoomNBTTag;
    }

    @Override
    public void setCustomNBTTag(NBTTagCompound tag) {
        custoomNBTTag = tag;
    }
}
