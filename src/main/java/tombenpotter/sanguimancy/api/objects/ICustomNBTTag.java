package tombenpotter.sanguimancy.api.objects;

import net.minecraft.nbt.NBTTagCompound;

public interface ICustomNBTTag {

    public NBTTagCompound getCustomNBTTag();

    public void setCustomNBTTag(NBTTagCompound tag);
}
