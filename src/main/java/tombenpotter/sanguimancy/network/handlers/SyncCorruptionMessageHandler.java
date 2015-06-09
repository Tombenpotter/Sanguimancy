package tombenpotter.sanguimancy.network.handlers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.api.soulCorruption.SoulCorruption;
import tombenpotter.sanguimancy.network.packets.PacketSyncCorruption;

public class SyncCorruptionMessageHandler implements IMessageHandler<PacketSyncCorruption, IMessage> {

    @Override
    public IMessage onMessage(PacketSyncCorruption message, MessageContext ctx) {
        World world = Sanguimancy.proxy.getClientWorld();
        EntityPlayer player = (EntityPlayer) world.getEntityByID(message.entityID);
        if (player != null) {
            SoulCorruption data = SoulCorruption.get(player);
            data.loadNBTData(message.tagCompound);
        }
        return null;
    }
}
