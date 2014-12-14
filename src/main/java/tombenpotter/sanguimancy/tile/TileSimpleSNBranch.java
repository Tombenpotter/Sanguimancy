package tombenpotter.sanguimancy.tile;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import tombenpotter.sanguimancy.api.BlockPostition;
import tombenpotter.sanguimancy.api.tile.TileBaseSNBranch;
import tombenpotter.sanguimancy.client.particle.EntityColoredFlameFX;

public class TileSimpleSNBranch extends TileBaseSNBranch {

    private NBTTagCompound custoomNBTTag;

    public TileSimpleSNBranch() {
        custoomNBTTag = new NBTTagCompound();
    }

    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        custoomNBTTag = tagCompound.getCompoundTag("customNBTTag");
    }

    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
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
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onNetworkUpdate(BlockPostition originalPosition) {
        addLinkingEffects();
    }

    @Override
    public void updateEntity() {
        if (worldObj.getWorldTime() % 100 == 0) {
            addLinkingEffects();
        }
    }

    public void addLinkingEffects() {
        if (!getSNKnots().isEmpty()) {
            double dx = xCoord + 0.5;
            double dy = yCoord + 0.5;
            double dz = zCoord + 0.5;
            for (int i = 0; i < getAdjacentISNComponents().length; i++) {
                if (getAdjacentISNComponents()[i] != null) {
                    for (float j = 0; j <= ForgeDirection.VALID_DIRECTIONS[i].offsetX; j += 0.1) {
                        EntityFX particle = new EntityColoredFlameFX(worldObj, dx + j, dy, dz, 0, 0, 0, 160, 160, 160, 100);
                        FMLClientHandler.instance().getClient().effectRenderer.addEffect(particle);
                    }
                    for (float j = 0; j <= ForgeDirection.VALID_DIRECTIONS[i].offsetY; j += 0.1) {
                        EntityFX particle = new EntityColoredFlameFX(worldObj, dx, dy + j, dz, 0, 0, 0, 160, 160, 160, 100);
                        FMLClientHandler.instance().getClient().effectRenderer.addEffect(particle);
                    }
                    for (float j = 0; j <= ForgeDirection.VALID_DIRECTIONS[i].offsetZ; j += 0.1) {
                        EntityFX particle = new EntityColoredFlameFX(worldObj, dx, dy, dz + j, 0, 0, 0, 160, 160, 160, 100);
                        FMLClientHandler.instance().getClient().effectRenderer.addEffect(particle);
                    }
                }
            }
        }
    }
}
