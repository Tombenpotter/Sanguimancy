package tombenpotter.oldsanguimancy.network.packets;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class PacketSyncCorruption implements IMessage {

    public int entityID;
    public NBTTagCompound tagCompound;

    public PacketSyncCorruption() {
    }

    public PacketSyncCorruption(EntityPlayer player, NBTTagCompound tagCompound) {
        this.entityID = player.getEntityId();
        this.tagCompound = tagCompound;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.entityID = buf.readInt();
        this.tagCompound = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(entityID);
        ByteBufUtils.writeTag(buf, tagCompound);
    }
}
