package tombenpotter.sanguimancy.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.util.SoulCorruptionHelper;

public class PacketPlayerSearch implements IMessage, IMessageHandler<PacketPlayerSearch, IMessage> {

    public PacketPlayerSearch() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    @Override
    public IMessage onMessage(PacketPlayerSearch message, MessageContext ctx) {
        EntityPlayer player = ctx.getServerHandler().playerEntity;
        NBTTagCompound tag = SoulCorruptionHelper.getModTag(player, Sanguimancy.modid);
        if (SoulCorruptionHelper.isCorruptionOver(ctx.getServerHandler().playerEntity, tag, 20)) {
            SoulCorruptionHelper.locatePlayersAround(player);
        }
        return null;
    }
}
