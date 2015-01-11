package tombenpotter.sanguimancy.api.soulCorruption;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.entity.EntityChickenMinion;
import tombenpotter.sanguimancy.network.PacketHandler;
import tombenpotter.sanguimancy.network.events.EventSoulCorruption;
import tombenpotter.sanguimancy.network.packets.PacketSyncCorruption;
import tombenpotter.sanguimancy.registry.BlocksRegistry;
import tombenpotter.sanguimancy.registry.PotionsRegistry;
import tombenpotter.sanguimancy.util.ConfigHandler;
import tombenpotter.sanguimancy.util.RandomUtils;
import tombenpotter.sanguimancy.util.singletons.SoulCorruption;

import java.util.List;

public class SoulCorruptionHelper {

    public static int getCorruptionLevel(String ownerName) {
        RandomUtils.fireEvent(new EventSoulCorruption.EventCheckSoulCorruption(ownerName));
        return SoulCorruption.getSoulCorruption().getCorruption(ownerName);
    }

    public static void setCorruptionLevel(String ownerName, int level) {
        SoulCorruption.getSoulCorruption().setCorruption(ownerName, level);
        RandomUtils.fireEvent(new EventSoulCorruption.EventSetSoulCorruption(ownerName, level));
        PacketHandler.INSTANCE.sendToAll(new PacketSyncCorruption(ownerName));
    }

    public static boolean isCorruptionEqual(String ownerName, int level) {
        return getCorruptionLevel(ownerName) == level;
    }

    public static boolean isCorruptionOver(String ownerName, int level) {
        return getCorruptionLevel(ownerName) >= level;
    }

    public static boolean isCorruptionLower(String ownerName, int level) {
        return getCorruptionLevel(ownerName) <= level;
    }

    public static void negateCorruption(String ownerName) {
        setCorruptionLevel(ownerName, 0);
    }

    public static void addCorruption(String ownerName, int level) {
        setCorruptionLevel(ownerName, getCorruptionLevel(ownerName) + level);
    }

    public static void removeCorruption(String ownerName, int level) {
        setCorruptionLevel(ownerName, getCorruptionLevel(ownerName) - level);
    }

    public static void incrementCorruption(String ownerName) {
        addCorruption(ownerName, 1);
    }

    public static void decrementCorruption(String ownerName) {
        removeCorruption(ownerName, 1);
    }

    public static int getClientPlayerCorruption() {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        return player.getEntityData().getInteger(Sanguimancy.modid + ":SC");
    }

    public static void spawnChickenFollower(EntityPlayer player) {
        if (player.worldObj.rand.nextInt(10000) == 0) {
            EntityChickenMinion minion = new EntityChickenMinion(player.worldObj);
            minion.setPosition(player.posX, player.posY, player.posZ);
            String owner = player.getUniqueID().toString();
            minion.func_152115_b(owner);
            minion.setTamed(true);
            player.worldObj.spawnEntityInWorld(minion);
            if (!player.worldObj.isRemote && ConfigHandler.messagesWhenCorruptionEffect) {
                MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText(StatCollector.translateToLocal("chat.Sanguimancy.chicken.minion") + " " + player.getDisplayName()));
            }
        }
    }

    public static void randomTeleport(EntityPlayer player) {
        if (player.worldObj.rand.nextInt(5000) == 0) {
            player.worldObj.addWeatherEffect(new EntityLightningBolt(player.worldObj, player.posX, player.posY, player.posZ));
            if (player.worldObj.rand.nextInt(10) == 0) {
                player.addPotionEffect(new PotionEffect(Potion.blindness.id, 20, 0));
            }
            if (player.worldObj.rand.nextInt(10) == 0) {
                player.addPotionEffect(new PotionEffect(Potion.confusion.id, 20, 0));
            }
            if (player.worldObj.rand.nextInt(10) == 0) {
                player.addPotionEffect(new PotionEffect(Potion.hunger.id, 20, 0));
            }
            if (player.worldObj.rand.nextInt(10) == 0) {
                player.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 20, 0));
            }
            if (player.worldObj.rand.nextInt(10) == 0) {
                player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 20, 0));
            }
            if (player.worldObj.rand.nextInt(10) == 0) {
                player.addPotionEffect(new PotionEffect(Potion.weakness.id, 20, 0));
            }
            int i = (int) (player.posX + player.worldObj.rand.nextInt(16) - player.worldObj.rand.nextInt(16));
            int j = (int) (player.posY + player.worldObj.rand.nextInt(16) - player.worldObj.rand.nextInt(16));
            int k = (int) (player.posZ + player.worldObj.rand.nextInt(16) - player.worldObj.rand.nextInt(16));
            if (j <= 5) j = j + 10;
            player.setPositionAndUpdate(i, j, k);
            decrementCorruption(player.getDisplayName());

            if (!player.worldObj.isRemote && ConfigHandler.messagesWhenCorruptionEffect) {
                MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText(StatCollector.translateToLocal("chat.Sanguimancy.random.teleport").replace("%player%", player.getDisplayName())));
            }
        }
    }

    public static void killGrass(EntityPlayer player) {
        if (player.worldObj.rand.nextInt(10) == 0) {
            int x = (int) player.posX;
            int y = (int) player.posY - 1;
            int z = (int) player.posZ;
            if (player.worldObj.getBlock(x, y, z) == Blocks.grass) player.worldObj.setBlock(x, y, z, Blocks.dirt);
        }
    }

    public static void hurtAndHealAnimals(EntityPlayer player) {
        if (player.worldObj.rand.nextInt(10) == 0) {
            int range = 4;
            int rangeY = 4;
            List<EntityAnimal> entities = player.worldObj.getEntitiesWithinAABB(EntityAnimal.class, AxisAlignedBB.getBoundingBox(player.posX - range, player.posY - rangeY, player.posZ - range, player.posX + range, player.posY + rangeY, player.posZ + range));
            for (EntityAnimal animal : entities) {
                if (animal.getClass() != EntityChickenMinion.class) {
                    animal.attackEntityFrom(DamageSource.causePlayerDamage(player), 1.0F);
                    animal.heal(1.0F);
                }
            }
        }
    }

    /*
    public static void locatePlayersAround(EntityPlayer player) {
        int range = 32;
        int rangeY = 32;
        List<EntityPlayer> entities = player.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(player.posX - range, player.posY - rangeY, player.posZ - range, player.posX + range, player.posY + rangeY, player.posZ + range));
        for (EntityPlayer entity : entities) {
            int x = (int) entity.posX;
            int y = (int) entity.posY;
            int z = (int) entity.posZ;
            if (entity != player) {
                EntityPlayerPointer pointer = new EntityPlayerPointer(player.worldObj);
                pointer.setPosition(x, y + 1, z);
                player.worldObj.spawnEntityInWorld(pointer);
            }
        }
    }
    */

    public static void addWither(EntityLivingBase livingBase) {
        livingBase.addPotionEffect(new PotionEffect(Potion.wither.id, 100));
    }

    public static void spawnIllusion(EntityPlayer player) {
        int i = (int) (player.posX + player.worldObj.rand.nextInt(16) - player.worldObj.rand.nextInt(16));
        int j = (int) (player.posY + player.worldObj.rand.nextInt(16) - player.worldObj.rand.nextInt(16));
        int k = (int) (player.posZ + player.worldObj.rand.nextInt(16) - player.worldObj.rand.nextInt(16));
        if (j <= 0) j = j + 5;
        if (player.worldObj.rand.nextInt(500) == 0 && player.worldObj.isAirBlock(i, j, k)) {
            player.worldObj.setBlock(i, j, k, BlocksRegistry.illusion, player.worldObj.rand.nextInt(16), 3);
        }
    }

    public static void looseHeart(EntityPlayer player) {
        if (player.worldObj.rand.nextInt(750) == 0) {
            int level = player.worldObj.rand.nextInt(5);
            player.addPotionEffect(new PotionEffect(PotionsRegistry.potionRemoveHeart.id, 1200, level, false));
            if (!player.worldObj.isRemote && ConfigHandler.messagesWhenCorruptionEffect) {
                MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText(player.getDisplayName() + " " + StatCollector.translateToLocal("chat.Sanguimancy.loose.heart")));
            }
        }
    }
}