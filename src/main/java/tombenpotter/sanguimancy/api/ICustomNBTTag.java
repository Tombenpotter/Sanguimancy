package tombenpotter.sanguimancy.api;

import net.minecraft.nbt.NBTTagCompound;

public interface ICustomNBTTag {

    public NBTTagCompound getCustomNBTTag();

    public void setCustomNBTTag(NBTTagCompound tag);
}
