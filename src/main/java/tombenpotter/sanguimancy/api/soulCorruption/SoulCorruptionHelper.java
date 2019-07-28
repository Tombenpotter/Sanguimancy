package tombenpotter.sanguimancy.api.soulCorruption;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.entity.EntityChickenMinion;
import tombenpotter.sanguimancy.registry.BlocksRegistry;
import tombenpotter.sanguimancy.registry.PotionsRegistry;
import tombenpotter.sanguimancy.util.ConfigHandler;

import java.util.List;

public class SoulCorruptionHelper {

    public static int getCorruptionLevel(EntityPlayer player) {
        return SoulCorruption.getSoulCorruption(player);
    }

    public static void setCorruptionLevel(EntityPlayer player, int level) {
        SoulCorruption.get(player).setSoulCorruption(level);
    }

    public static boolean isCorruptionEqual(EntityPlayer player, int level) {
        return getCorruptionLevel(player) == level;
    }

    public static boolean isCorruptionOver(EntityPlayer player, int level) {
        return getCorruptionLevel(player) >= level;
    }

    public static boolean isCorruptionLower(EntityPlayer player, int level) {
        return getCorruptionLevel(player) <= level;
    }

    public static void negateCorruption(EntityPlayer player) {
        setCorruptionLevel(player, 0);
    }

    public static void addCorruption(EntityPlayer player, int level) {
        setCorruptionLevel(player, getCorruptionLevel(player) + level);
    }

    public static void removeCorruption(EntityPlayer player, int level) {
        setCorruptionLevel(player, getCorruptionLevel(player) - level);
    }

    public static void incrementCorruption(EntityPlayer player) {
        addCorruption(player, 1);
    }

    public static void decrementCorruption(EntityPlayer player) {
        removeCorruption(player, 1);
    }

    @SideOnly(Side.CLIENT)
    public static int getClientPlayerCorruption() {
        return Sanguimancy.proxy.getClientPlayer().getEntityData().getInteger(Sanguimancy.modid + ":SC");
    }

    public static void spawnChickenFollower(EntityPlayer player) {
        if (player.world.rand.nextInt(10000) == 0) {
            EntityChickenMinion minion = new EntityChickenMinion(player.world);
            minion.setPosition(player.posX, player.posY, player.posZ);
            minion.setOwnerId(player.getUniqueID());
            minion.setTamed(true);
            if (!player.world.isRemote) {
                player.world.spawnEntity(minion);
            }
//            if (!player.world.isRemote && ConfigHandler.serverMessagesWhenCorruptionEffect) {
//                MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText(String.format(StatCollector.translateToLocal("chat.Sanguimancy.chicken.minion"), player.getDisplayName())));
//            }
            if (!player.world.isRemote && ConfigHandler.playerMessageWhenCorruptionEffect) {
                player.sendMessage(new TextComponentString(I18n.format("chat.Sanguimancy.chicken.minion", player.getDisplayName())));
            }
        }
    }

    public static void randomTeleport(EntityPlayer player) {
        if (player.world.rand.nextInt(5000) == 0) {
            player.world.addWeatherEffect(new EntityLightningBolt(player.world, player.posX, player.posY, player.posZ, true));
            if (player.world.rand.nextInt(10) == 0) {
                player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 20, 0));
            }
            if (player.world.rand.nextInt(10) == 0) {
                player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 20, 0));
            }
            if (player.world.rand.nextInt(10) == 0) {
                player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 20, 0));
            }
            if (player.world.rand.nextInt(10) == 0) {
                player.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 20, 0));
            }
            if (player.world.rand.nextInt(10) == 0) {
                player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20, 0));
            }
            if (player.world.rand.nextInt(10) == 0) {
                player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 20, 0));
            }
            int i = (int) (player.posX + player.world.rand.nextInt(16) - player.world.rand.nextInt(16));
            int j = (int) (player.posY + player.world.rand.nextInt(16) - player.world.rand.nextInt(16));
            int k = (int) (player.posZ + player.world.rand.nextInt(16) - player.world.rand.nextInt(16));
            if (j <= 5) j = j + 10;
            player.setPositionAndUpdate(i, j, k);
            decrementCorruption(player);

//            if (!player.world.isRemote && ConfigHandler.serverMessagesWhenCorruptionEffect) {
//                MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText(String.format(StatCollector.translateToLocal("chat.Sanguimancy.random.teleport"), player.getDisplayName())));
//            }
            if (!player.world.isRemote && ConfigHandler.playerMessageWhenCorruptionEffect) {
                player.sendMessage(new TextComponentString(I18n.format("chat.Sanguimancy.random.teleport", player.getDisplayName())));
            }
        }
    }

    public static void killGrass(EntityPlayer player) {
        if (player.world.rand.nextInt(10) == 0) {
            int x = (int) player.posX;
            int y = (int) player.posY - 1;
            int z = (int) player.posZ;
            if (player.world.getBlock(x, y, z) == Blocks.GRASS) player.world.setBlock(x, y, z, Blocks.DIRT);
        }
    }

    public static void hurtAndHealAnimals(EntityPlayer player) {
        if (player.world.rand.nextInt(10) == 0) {
            int range = 4;
            int rangeY = 4;
            List<EntityAnimal> entities = player.world.getEntitiesWithinAABB(EntityAnimal.class, AxisAlignedBB.getBoundingBox(player.posX - range, player.posY - rangeY, player.posZ - range, player.posX + range, player.posY + rangeY, player.posZ + range));
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
        livingBase.addPotionEffect(new PotionEffect(MobEffects.WITHER, 100));
    }

    public static void spawnIllusion(EntityPlayer player) {
        int i = (int) (player.posX + player.world.rand.nextInt(16) - player.world.rand.nextInt(16));
        int j = (int) (player.posY + player.world.rand.nextInt(16) - player.world.rand.nextInt(16));
        int k = (int) (player.posZ + player.world.rand.nextInt(16) - player.world.rand.nextInt(16));
        if (j <= 0) j = j + 5;
        if (player.world.rand.nextInt(500) == 0 && player.world.isAirBlock(i, j, k)) {
            player.world.setBlock(i, j, k, BlocksRegistry.illusion, player.world.rand.nextInt(16), 3);
        }
    }

    public static void loseHeart(EntityPlayer player) {
        if (!player.capabilities.isCreativeMode) {
            if (player.world.rand.nextInt(750) == 0) {
                int level = player.world.rand.nextInt(5);
                player.addPotionEffect(new PotionEffect(PotionsRegistry.potionRemoveHeart.id, 1200, level, false));
//                if (!player.worldObj.isRemote && ConfigHandler.serverMessagesWhenCorruptionEffect) {
//                    MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText(String.format(StatCollector.translateToLocal("chat.Sanguimancy.loose.heart"), player.getDisplayName())));
//                }
                if (!player.world.isRemote && ConfigHandler.playerMessageWhenCorruptionEffect) {
                    player.sendMessage(new TextComponentString(I18n.format("chat.Sanguimancy.loose.heart", player.getDisplayName())));
                }
            }
        }
    }
}