package tombenpotter.sanguimancy.tile;

import WayofTime.alchemicalWizardry.ModItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tombenpotter.sanguimancy.api.objects.BlockPostition;
import tombenpotter.sanguimancy.api.tile.TileBaseSNKnot;

public class TileSimpleSNKnot extends TileBaseSNKnot {

    public String knotOwner;
    public boolean knotActive;
    private NBTTagCompound custoomNBTTag;

    public TileSimpleSNKnot() {
        custoomNBTTag = new NBTTagCompound();
        knotActive = true;
    }

    @Override
    public boolean isSNKnotactive() {
        return knotActive;
    }

    @Override
    public void setKnotActive(boolean isActive) {
        this.knotActive = isActive;
    }

    @Override
    public String getSNKnotOwner() {
        return knotOwner;
    }

    @Override
    public void setSNKnotOwner(String owner) {
        this.knotOwner = owner;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        knotOwner = tagCompound.getString("knotOwner");
        knotActive = tagCompound.getBoolean("knotActive");
        custoomNBTTag = tagCompound.getCompoundTag("customNBTTag");
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setString("knotOwner", knotOwner);
        tagCompound.setBoolean("knotActive", knotActive);
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

    @Override
    public boolean isSNKnot() {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onNetworkUpdate(BlockPostition originalPosition) {
    }

    public boolean onBlockRightClicked(ItemStack stack) {
        if (stack != null) {
            if (stack.isItemEqual(new ItemStack(ModItems.divinationSigil)) || stack.isItemEqual(new ItemStack(ModItems.itemSeerSigil))) {
                setKnotActive(!isSNKnotactive());
                return true;
            }
        }
        return false;
    }
}
