package tombenpotter.sanguimancy.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.network.handlers.BloodInterfaceUpdateMessageHandler;
import tombenpotter.sanguimancy.network.handlers.SyncCorruptionMessageHandler;
import tombenpotter.sanguimancy.network.packets.PacketBloodInterfaceUpdate;
import tombenpotter.sanguimancy.network.packets.PacketSyncCorruption;

public class PacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Sanguimancy.channel);

    public static void registerPackets() {
        INSTANCE.registerMessage(BloodInterfaceUpdateMessageHandler.class, PacketBloodInterfaceUpdate.class, 0, Side.CLIENT);
        INSTANCE.registerMessage(SyncCorruptionMessageHandler.class, PacketSyncCorruption.class, 1, Side.CLIENT);
    }
}