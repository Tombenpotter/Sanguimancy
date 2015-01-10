package tombenpotter.sanguimancy.network.handlers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.network.packets.PacketSyncCorruption;
import tombenpotter.sanguimancy.util.SoulCorruptionHelper;

public class SyncCorruptionMessageHandler implements IMessageHandler<PacketSyncCorruption, IMessage> {

    @Override
    public IMessage onMessage(PacketSyncCorruption message, MessageContext ctx) {
        EntityPlayer clientPlayer = Minecraft.getMinecraft().thePlayer;
        NBTTagCompound clientTag = SoulCorruptionHelper.getModTag(clientPlayer, Sanguimancy.modid);
        SoulCorruptionHelper.setCorruptionLevel(clientPlayer, clientTag, message.corruption);
        return null;
    }
}
