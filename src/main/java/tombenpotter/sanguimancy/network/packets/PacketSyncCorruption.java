package tombenpotter.sanguimancy.network.packets;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.util.SoulCorruptionHelper;

public class PacketSyncCorruption implements IMessage {

    private EntityPlayer serverPlayer;
    public int corruption;

    public PacketSyncCorruption() {
    }

    public PacketSyncCorruption(EntityPlayer player) {
        this.serverPlayer = player;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        corruption = ByteBufUtils.readVarInt(buf, 5);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        NBTTagCompound serverTag = SoulCorruptionHelper.getModTag(serverPlayer, Sanguimancy.modid);
        ByteBufUtils.writeVarInt(buf, SoulCorruptionHelper.getCorruptionLevel(serverPlayer, serverTag), 5);
    }
}
