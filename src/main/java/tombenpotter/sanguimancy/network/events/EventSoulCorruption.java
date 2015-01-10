package tombenpotter.sanguimancy.network.events;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.player.EntityPlayer;

public class EventSoulCorruption extends Event {
    public final EntityPlayer entityPlayer;

    public EventSoulCorruption(EntityPlayer player) {
        this.entityPlayer = player;
    }

    public static class EventAddSoulCorruption extends EventSoulCorruption {
        public final int amountAdded;

        public EventAddSoulCorruption(EntityPlayer player, int amount) {
            super(player);
            this.amountAdded = amount;
        }
    }

    public static class EventRemoveSoulCorruption extends EventSoulCorruption {
        public final int amountRemoved;

        public EventRemoveSoulCorruption(EntityPlayer player, int amount) {
            super(player);
            this.amountRemoved = amount;
        }
    }

    public static class EventSetSoulCorruption extends EventSoulCorruption {
        public final int amountSet;

        public EventSetSoulCorruption(EntityPlayer player, int amount) {
            super(player);
            this.amountSet = amount;
        }
    }

    public static class EventCheckSoulCorruption extends EventSoulCorruption {

        public EventCheckSoulCorruption(EntityPlayer player) {
            super(player);
        }
    }
}
