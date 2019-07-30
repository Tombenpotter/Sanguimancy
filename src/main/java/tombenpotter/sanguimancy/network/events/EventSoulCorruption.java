package tombenpotter.sanguimancy.network.events;

import net.minecraftforge.fml.common.eventhandler.Event;

public class EventSoulCorruption extends Event {
    public final String ownerName;

    public EventSoulCorruption(String owner) {
        this.ownerName = owner;
    }

    public static class EventSetSoulCorruption extends EventSoulCorruption {
        public final int amountSet;

        public EventSetSoulCorruption(String owner, int amount) {
            super(owner);
            this.amountSet = amount;
        }
    }

    public static class EventCheckSoulCorruption extends EventSoulCorruption {

        public EventCheckSoulCorruption(String owner) {
            super(owner);
        }
    }
}
