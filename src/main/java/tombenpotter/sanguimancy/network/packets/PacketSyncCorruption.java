package tombenpotter.sanguimancy.network.packets;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import tombenpotter.sanguimancy.api.soulCorruption.SoulCorruptionHelper;

public class PacketSyncCorruption implements IMessage {

    public String ownerName;
    public int corruption;

    public PacketSyncCorruption() {
    }

    public PacketSyncCorruption(String owner) {
        this.ownerName = owner;
    }

    public PacketSyncCorruption(EntityPlayer player) {
        this.ownerName = player.getDisplayName();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        corruption = ByteBufUtils.readVarInt(buf, 5);
        ownerName = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeVarInt(buf, SoulCorruptionHelper.getCorruptionLevel(ownerName), 5);
        ByteBufUtils.writeUTF8String(buf, ownerName);
    }
}
