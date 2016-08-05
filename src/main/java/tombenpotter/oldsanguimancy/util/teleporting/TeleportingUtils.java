package tombenpotter.oldsanguimancy.util.teleporting;

import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S06PacketUpdateHealth;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class TeleportingUtils {

    public static Entity teleportEntitySameDim(int x, int y, int z, Entity entity, String name) {
        if (entity != null) {
            if (entity.timeUntilPortal <= 0) {
                if (entity instanceof EntityPlayer) {
                    EntityPlayerMP player = (EntityPlayerMP) entity;
                    player.setPositionAndUpdate(x, y, z);
                    player.worldObj.updateEntityWithOptionalForce(player, false);
                    player.playerNetServerHandler.sendPacket(new S06PacketUpdateHealth(player.getHealth(), player.getFoodStats().getFoodLevel(), player.getFoodStats().getSaturationLevel()));
                    player.timeUntilPortal = 150;
                    SoulNetworkHandler.syphonFromNetwork(name, 1000);
                    player.worldObj.playSoundEffect(x, y, z, "mob.endermen.portal", 1.0F, 1.0F);
                    return player;
                } else {
                    WorldServer world = (WorldServer) entity.worldObj;
                    entity.setPosition(x, y, z);
                    entity.timeUntilPortal = 150;
                    world.resetUpdateEntityTick();
                    SoulNetworkHandler.syphonFromNetwork(name, 1000);
                    entity.worldObj.playSoundEffect(x, y, z, "mob.endermen.portal", 1.0F, 1.0F);
                    return entity;
                }
            }
        }
        return null;
    }

    public static void teleportEntityToDim(World oldWorld, int newWorldID, int x, int y, int z, Entity entity, String name) {
        if (entity != null) {
            if (entity.timeUntilPortal <= 0) {
                MinecraftServer server = MinecraftServer.getServer();
                WorldServer oldWorldServer = server.worldServerForDimension(entity.dimension);
                WorldServer newWorldServer = server.worldServerForDimension(newWorldID);
                if (entity instanceof EntityPlayer) {
                    EntityPlayerMP player = (EntityPlayerMP) entity;
                    if (!player.worldObj.isRemote) {
                        server.getConfigurationManager().transferPlayerToDimension(player, newWorldID, new SanguimancyTeleporter(newWorldServer));
                        player.setPositionAndUpdate(x, y, z);
                        player.worldObj.updateEntityWithOptionalForce(player, false);
                        player.playerNetServerHandler.sendPacket(new S06PacketUpdateHealth(player.getHealth(), player.getFoodStats().getFoodLevel(), player.getFoodStats().getSaturationLevel()));

                        if (oldWorld.provider.dimensionId == 1) {
                            // For some reason teleporting out of the end does weird things.
                            player.setPositionAndUpdate(x, y, z);
                            newWorldServer.spawnEntityInWorld(player);
                            newWorldServer.updateEntityWithOptionalForce(player, false);
                        }
                    }
                } else if (!entity.worldObj.isRemote) {
                    NBTTagCompound tag = new NBTTagCompound();
                    entity.writeToNBTOptional(tag);
                    entity.setDead();
                    oldWorld.playSoundEffect(entity.posX, entity.posY, entity.posZ, "mob.endermen.portal", 1.0F, 1.0F);
                    Entity teleportedEntity = EntityList.createEntityFromNBT(tag, newWorldServer);
                    if (teleportedEntity != null) {
                        teleportedEntity.setLocationAndAngles(x, y, z, entity.rotationYaw, entity.rotationPitch);
                        teleportedEntity.forceSpawn = true;
                        newWorldServer.spawnEntityInWorld(teleportedEntity);
                        teleportedEntity.setWorld(newWorldServer);
                        teleportedEntity.timeUntilPortal = 150;
                    }
                    oldWorldServer.resetUpdateEntityTick();
                    newWorldServer.resetUpdateEntityTick();
                }
                entity.timeUntilPortal = 150;
                SoulNetworkHandler.syphonFromNetwork(name, 10000);
                newWorldServer.playSoundEffect(x, y, z, "mob.endermen.portal", 1.0F, 1.0F);
            }
        }
    }
}
