package tombenpotter.sanguimancy.tile;

import WayofTime.alchemicalWizardry.api.event.RitualRunEvent;
import WayofTime.alchemicalWizardry.api.event.RitualStopEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.MinecraftForge;
import tombenpotter.sanguimancy.api.objects.BlockPostition;
import tombenpotter.sanguimancy.api.EnumSNType;
import tombenpotter.sanguimancy.api.ICustomNBTTag;
import tombenpotter.sanguimancy.api.objects.SNKNotBoolean;
import tombenpotter.sanguimancy.api.tile.TileBaseSNPart;

import java.util.HashMap;

public class TileRitualSNPart extends TileBaseSNPart implements ICustomNBTTag {

    public NBTTagCompound custoomNBTTag;
    public BlockPostition ritualPosition;

    public TileRitualSNPart() {
        MinecraftForge.EVENT_BUS.register(this);
        custoomNBTTag = new NBTTagCompound();
    }

    @Override
    public EnumSNType getType() {
        return EnumSNType.RITUAL;
    }

    @Override
    public boolean isSNKnot() {
        return false;
    }

    @Override
    public void onNetworkUpdate(BlockPostition originalPosition) {
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        custoomNBTTag = tagCompound.getCompoundTag("customNBTTag");
        ritualPosition = BlockPostition.readFromNBT(tagCompound);
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setTag("customNBTTag", custoomNBTTag);
        ritualPosition.writeToNBT(tagCompound);
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
    public final Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        writeToNBT(nbt);
        S35PacketUpdateTileEntity packet = new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbt);
        return packet;
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        NBTTagCompound nbt = pkt.func_148857_g();
        readFromNBT(nbt);
    }

    @Override
    public void markDirty() {
        super.markDirty(); // Mark dirty for gamesave
        if (worldObj.isRemote) {
            return;
        }
        this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord); // Update block + TE via Network
    }

    @SubscribeEvent
    public void disableLinkedRitual(RitualRunEvent event) {
        if (ritualPosition != null) {
            HashMap<BlockPostition, SNKNotBoolean> map = getComponentsInNetwork().hashMap;
            for (BlockPostition postition : map.keySet()) {
                if (map.get(postition).isSNKnot && map.get(postition).isSNKnotActive && worldObj.getBlockPowerInput(xCoord, yCoord, zCoord) > 0) {
                    if (event.mrs.getXCoord() == ritualPosition.x && event.mrs.getYCoord() == ritualPosition.y && event.mrs.getZCoord() == ritualPosition.z) {
                        event.setResult(Event.Result.DENY);
                    }
                } else if (map.get(postition).isSNKnot && !map.get(postition).isSNKnotActive) {
                    if (event.mrs.getXCoord() == ritualPosition.x && event.mrs.getYCoord() == ritualPosition.y && event.mrs.getZCoord() == ritualPosition.z) {
                        event.setResult(Event.Result.DENY);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void removeMasterStone(RitualStopEvent event) {
        if (ritualPosition != null) {
            if (event.mrs.getXCoord() == ritualPosition.x && event.mrs.getYCoord() == ritualPosition.y && event.mrs.getZCoord() == ritualPosition.z) {
                worldObj.setBlockToAir(xCoord, yCoord, zCoord);
            }
        }
    }
}
