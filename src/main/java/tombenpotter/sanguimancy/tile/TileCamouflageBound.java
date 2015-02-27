package tombenpotter.sanguimancy.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.common.util.Constants;
import tombenpotter.sanguimancy.api.tile.IBoundTile;
import tombenpotter.sanguimancy.api.tile.TileCamouflage;

import java.util.ArrayList;

public class TileCamouflageBound extends TileCamouflage implements IBoundTile {

    ArrayList<String> ownerList;

    public TileCamouflageBound() {
        customNBTTag = new NBTTagCompound();
        this.ownerList = new ArrayList<String>();
    }

    @Override
    public ArrayList<String> getOwnersList() {
        return this.ownerList;
    }

    @Override
    public void setOwnersList(ArrayList<String> ownersList) {
        this.ownerList = ownersList;
    }

    @Override
    public void addOwnerToList(String ownerName) {
        this.ownerList.add(ownerName);
    }

    @Override
    public void removeOwnerFromList(String ownerName) {
        this.ownerList.remove(ownerName);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        NBTTagList owners = tagCompound.getTagList("owners", Constants.NBT.TAG_STRING);
        for (int i = 0; i < owners.tagCount(); i++) this.ownerList.add(owners.getStringTagAt(i));
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        NBTTagList owners = new NBTTagList();
        for (String s : this.ownerList) owners.appendTag(new NBTTagString(s));
        tagCompound.setTag("owners", owners);
    }
}
