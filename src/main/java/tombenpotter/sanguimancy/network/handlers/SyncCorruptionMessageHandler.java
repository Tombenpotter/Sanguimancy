package tombenpotter.sanguimancy.network.handlers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.network.packets.PacketSyncCorruption;

public class SyncCorruptionMessageHandler implements IMessageHandler<PacketSyncCorruption, IMessage> {

    @Override
    public IMessage onMessage(PacketSyncCorruption message, MessageContext ctx) {
        EntityPlayer clientPlayer = Sanguimancy.proxy.getClientPlayer();
        if (message.ownerName.equals(clientPlayer.getDisplayName())) {
            clientPlayer.getEntityData().setInteger(Sanguimancy.modid + ":SC", message.corruption);
        }
        return null;
    }
}
