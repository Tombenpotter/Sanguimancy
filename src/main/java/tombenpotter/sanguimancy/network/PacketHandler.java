package tombenpotter.sanguimancy.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import tombenpotter.sanguimancy.Sanguimancy;

public class PacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Sanguimancy.channel);

    public static void registerPackets() {
        INSTANCE.registerMessage(PacketPlayerSearch.class, PacketPlayerSearch.class, 0, Side.SERVER);
        INSTANCE.registerMessage(BloodInterfaceUpdateMessage.class, BloodInterfaceUpdateMessage.class, 1, Side.CLIENT);
    }
}
