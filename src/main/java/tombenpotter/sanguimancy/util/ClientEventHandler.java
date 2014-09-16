package tombenpotter.sanguimancy.util;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.network.PacketHandler;
import tombenpotter.sanguimancy.network.PacketPlayerSearch;

public class ClientEventHandler {

    public static KeyBinding keySearchPlayer = new KeyBinding("key.sanguimancy.search", Keyboard.KEY_F, Sanguimancy.modid);

    public ClientEventHandler() {
        ClientRegistry.registerKeyBinding(keySearchPlayer);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (keySearchPlayer.isPressed()) {
            PacketHandler.INSTANCE.sendToServer(new PacketPlayerSearch());
        }
    }
}
