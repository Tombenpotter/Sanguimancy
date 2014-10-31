package tombenpotter.sanguimancy.tile;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S06PacketUpdateHealth;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S1FPacketSetExperience;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.Iterator;

public class TileDimensionalPortal extends TileEntity {

    public String portalID;
    public int masterStoneX;
    public int masterStoneY;
    public int masterStoneZ;

    public Entity teleportEntitySameDim(int x, int y, int z, Entity entity) {
        if (entity != null) {
            if (entity.timeUntilPortal <= 0) {
                if (entity instanceof EntityPlayer) {
                    EntityPlayerMP player = (EntityPlayerMP) entity;
                    player.setPositionAndUpdate(x, y, z);
                    player.worldObj.updateEntityWithOptionalForce(player, false);
                    player.playerNetServerHandler.sendPacket(new S06PacketUpdateHealth(player.getHealth(), player.getFoodStats().getFoodLevel(), player.getFoodStats().getSaturationLevel()));
                    player.timeUntilPortal = 300;
                    return player;
                } else {
                    WorldServer world = (WorldServer) entity.worldObj;
                    if (entity != null) {
                        entity.setPosition(x, y, z);
                        entity.timeUntilPortal = 300;
                    }
                    world.resetUpdateEntityTick();
                    return entity;
                }
            }
        }
        return null;
    }

    //Adapated from Enhanced Portals 3 code
    public Entity teleportEntityToDim(World oldWorld, World newWorld, int x, int y, int z, Entity entity) {
        if (entity != null) {
            if (entity.timeUntilPortal <= 0) {
                WorldServer oldWorldServer = (WorldServer) oldWorld;
                WorldServer newWorldServer = (WorldServer) newWorld;
                if (entity instanceof EntityPlayer) {
                    EntityPlayerMP player = (EntityPlayerMP) entity;
                    if (!player.worldObj.isRemote) {
                        player.worldObj.theProfiler.startSection("portal");
                        player.worldObj.theProfiler.startSection("changeDimension");
                        ServerConfigurationManager config = player.mcServer.getConfigurationManager();
                        player.closeScreen();
                        player.dimension = newWorldServer.provider.dimensionId;
                        player.playerNetServerHandler.sendPacket(new S07PacketRespawn(player.dimension, player.worldObj.difficultySetting, newWorldServer.getWorldInfo().getTerrainType(), player.theItemInWorldManager.getGameType()));
                        oldWorldServer.removeEntity(player);
                        player.isDead = false;
                        player.setLocationAndAngles(x, y, z, player.rotationYaw, player.rotationPitch);
                        newWorldServer.spawnEntityInWorld(player);
                        player.setWorld(newWorldServer);
                        config.func_72375_a(player, oldWorldServer);
                        player.playerNetServerHandler.setPlayerLocation(x, y, z, entity.rotationYaw, entity.rotationPitch);
                        player.theItemInWorldManager.setWorld(newWorldServer);
                        config.updateTimeAndWeatherForPlayer(player, newWorldServer);
                        config.syncPlayerInventory(player);
                        player.worldObj.theProfiler.endSection();
                        oldWorldServer.resetUpdateEntityTick();
                        newWorldServer.resetUpdateEntityTick();
                        player.worldObj.theProfiler.endSection();
                        for (Iterator<PotionEffect> potion = player.getActivePotionEffects().iterator(); potion.hasNext(); ) {
                            player.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(player.getEntityId(), (PotionEffect) potion.next()));
                        }
                        player.playerNetServerHandler.sendPacket(new S1FPacketSetExperience(player.experience, player.experienceTotal, player.experienceLevel));
                        FMLCommonHandler.instance().firePlayerChangedDimensionEvent(player, oldWorldServer.provider.dimensionId, player.dimension);
                        player.timeUntilPortal = 300;
                    }
                    player.worldObj.theProfiler.endSection();
                    return player;
                } else {
                    NBTTagCompound tag = new NBTTagCompound();
                    entity.writeToNBTOptional(tag);
                    entity.setDead();
                    Entity teleportedEntity = EntityList.createEntityFromNBT(tag, newWorldServer);
                    if (teleportedEntity != null) {
                        teleportedEntity.setLocationAndAngles(x, y, z, entity.rotationYaw, entity.rotationPitch);
                        teleportedEntity.forceSpawn = true;
                        newWorldServer.spawnEntityInWorld(teleportedEntity);
                        teleportedEntity.setWorld(newWorldServer);
                        entity.timeUntilPortal = 300;
                    }
                    oldWorldServer.resetUpdateEntityTick();
                    oldWorldServer.resetUpdateEntityTick();
                    return teleportedEntity;
                }
            }
        }
        return null;
    }

    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        portalID = tag.getString("PortalRitualID");
        masterStoneX = tag.getInteger("masterStoneX");
        masterStoneY = tag.getInteger("masterStoneY");
        masterStoneZ = tag.getInteger("masterStoneZ");
    }

    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setString("PortalRitualID", portalID);
        tag.setInteger("masterStoneX", masterStoneX);
        tag.setInteger("masterStoneY", masterStoneY);
        tag.setInteger("masterStoneZ", masterStoneZ);
    }
}
