package tombenpotter.oldsanguimancy.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import tombenpotter.oldsanguimancy.Sanguimancy;
import tombenpotter.oldsanguimancy.network.handlers.BloodInterfaceUpdateMessageHandler;
import tombenpotter.oldsanguimancy.network.handlers.SyncCorruptionMessageHandler;
import tombenpotter.oldsanguimancy.network.packets.PacketBloodInterfaceUpdate;
import tombenpotter.oldsanguimancy.network.packets.PacketSyncCorruption;

public class PacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Sanguimancy.channel);

    public static void registerPackets() {
        INSTANCE.registerMessage(BloodInterfaceUpdateMessageHandler.class, PacketBloodInterfaceUpdate.class, 0, Side.CLIENT);
        INSTANCE.registerMessage(SyncCorruptionMessageHandler.class, PacketSyncCorruption.class, 1, Side.CLIENT);
    }
}