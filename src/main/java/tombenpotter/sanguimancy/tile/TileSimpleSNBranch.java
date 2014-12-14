package tombenpotter.sanguimancy.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import tombenpotter.sanguimancy.api.BlockPostition;
import tombenpotter.sanguimancy.api.tile.TileBaseSNBranch;

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
        /*
        for (BlockPostition positition : getSNKnots()) {
            EntityParticleBeam beam = new EntityParticleBeam(worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5);
            double velocity = Math.sqrt(Math.pow(positition.x, 2) + Math.pow(positition.y, 2) + Math.pow(positition.z, 2));
            double wantedVel = 0.3d;
            beam.setVelocity(wantedVel * positition.x / velocity, wantedVel * positition.y / velocity, wantedVel * positition.z / velocity);
            beam.setDestination(xCoord + positition.x, yCoord + positition.y, zCoord + positition.z);
            worldObj.spawnEntityInWorld(beam);
        }*/
    }
}
