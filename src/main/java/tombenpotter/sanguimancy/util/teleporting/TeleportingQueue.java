package tombenpotter.sanguimancy.util.teleporting;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class TeleportingQueue {
    private static TeleportingQueue teleportingQueue = new TeleportingQueue();
    private List<Teleport> queue = new ArrayList<Teleport>();


    public static TeleportingQueue getInstance() {
        return teleportingQueue;
    }

    public void teleportToDim(World oldWorld, int newWorld, int x, int y, int z, Entity entity, String name) {
        queue.add(new TeleportToDim(oldWorld, newWorld, x, y, z, entity, name));
    }

    public void teleportSameDim(int x, int y, int z, Entity entity, String name) {
        queue.add(new TeleportSameDim(x, y, z, entity, name));
    }

    @SubscribeEvent
    public void serverTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        for (Teleport teleport : queue) teleport.teleport();
        queue.clear();
    }

    private abstract static class Teleport {
        public abstract void teleport();
    }

    private static class TeleportSameDim extends Teleport {
        int x, y, z;
        Entity entity;
        String name;

        public TeleportSameDim(int x, int y, int z, Entity entity, String name) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.entity = entity;
            this.name = name;
        }

        @Override
        public void teleport() {
            TeleportingUtils.teleportEntitySameDim(x, y, z, entity, name);
        }
    }

    private static class TeleportToDim extends Teleport {
        World oldWorld;
        int newWorld;
        int x, y, z;
        Entity entity;
        String name;

        public TeleportToDim(World oldWorld, int newWorld, int x, int y, int z, Entity entity, String name) {
            this.oldWorld = oldWorld;
            this.newWorld = newWorld;
            this.x = x;
            this.y = y;
            this.z = z;
            this.entity = entity;
            this.name = name;
        }

        @Override
        public void teleport() {
            TeleportingUtils.teleportEntityToDim(oldWorld, newWorld, x, y, z, entity, name);
        }
    }
}
