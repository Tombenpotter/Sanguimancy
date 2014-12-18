package tombenpotter.sanguimancy.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.entity.EntityChickenMinion;
import tombenpotter.sanguimancy.entity.EntityPlayerPointer;
import tombenpotter.sanguimancy.network.EventSoulCorruption;
import tombenpotter.sanguimancy.registry.BlocksRegistry;

import java.util.List;

public class SoulCorruptionHelper {

    public static String soulCorruptionTag = "SoulCorruption";

    public static NBTTagCompound getModTag(EntityPlayer player, String modName) {
        NBTTagCompound tag = player.getEntityData();
        NBTTagCompound persistTag;
        if (tag.hasKey(EntityPlayer.PERSISTED_NBT_TAG)) persistTag = tag.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
        else {
            persistTag = new NBTTagCompound();
            tag.setTag(EntityPlayer.PERSISTED_NBT_TAG, persistTag);
        }

        NBTTagCompound modTag;
        if (persistTag.hasKey(modName)) {
            modTag = persistTag.getCompoundTag(modName);
        } else {
            modTag = new NBTTagCompound();
            persistTag.setTag(modName, modTag);
        }
        return modTag;
    }

    public static int getCorruptionLevel(EntityPlayer player, NBTTagCompound tag) {
        RandomUtils.fireEvent(new EventSoulCorruption.EventCheckSoulCorruption(player));
        return tag.getInteger(soulCorruptionTag);
    }

    public static boolean isCorruptionEqual(EntityPlayer player, NBTTagCompound tag, int level) {
        return (getCorruptionLevel(player, tag) == level);
    }

    public static boolean isCorruptionOver(EntityPlayer player, NBTTagCompound tag, int level) {
        return (getCorruptionLevel(player, tag) >= level);
    }

    public static boolean isCorruptionLower(EntityPlayer player, NBTTagCompound tag, int level) {
        return (getCorruptionLevel(player, tag) <= level);
    }

    public static void negateCorruption(EntityPlayer player, NBTTagCompound tag) {
        tag.setInteger(soulCorruptionTag, 0);
        RandomUtils.fireEvent(new EventSoulCorruption.EventSetSoulCorruption(player, 0));
    }

    public static void setCorruptionLevel(EntityPlayer player, NBTTagCompound tag, int amount) {
        tag.setInteger(soulCorruptionTag, amount);
        RandomUtils.fireEvent(new EventSoulCorruption.EventSetSoulCorruption(player, amount));
    }

    public static void addCorruption(EntityPlayer player, NBTTagCompound tag, int amount) {
        int initialAmount = getCorruptionLevel(player, tag);
        tag.setInteger(soulCorruptionTag, initialAmount + amount);
        RandomUtils.fireEvent(new EventSoulCorruption.EventAddSoulCorruption(player, amount));
    }

    public static void removeCorruption(EntityPlayer player, NBTTagCompound tag, int amount) {
        int initialAmount = getCorruptionLevel(player, tag);
        tag.setInteger(soulCorruptionTag, initialAmount - amount);
        RandomUtils.fireEvent(new EventSoulCorruption.EventRemoveSoulCorruption(player, amount));
    }

    public static void incrementCorruption(EntityPlayer player, NBTTagCompound tag) {
        int amount = getCorruptionLevel(player, tag);
        addCorruption(player, tag, 1);
    }

    public static void decrementCorruption(EntityPlayer player, NBTTagCompound tag) {
        int amount = getCorruptionLevel(player, tag);
        if (amount > 0) removeCorruption(player, tag, 1);
    }

    public static void spawnChickenFollower(EntityPlayer player) {
        if (player.worldObj.rand.nextInt(10000) == 0) {
            EntityChickenMinion minion = new EntityChickenMinion(player.worldObj);
            minion.setPosition(player.posX, player.posY, player.posZ);
            String owner = player.getUniqueID().toString();
            minion.func_152115_b(owner);
            minion.setTamed(true);
            player.worldObj.spawnEntityInWorld(minion);
        }
    }

    public static void randomTeleport(EntityPlayer player) {
        if (player.worldObj.rand.nextInt(5000) == 0) {
            NBTTagCompound tag = SoulCorruptionHelper.getModTag(player, Sanguimancy.modid);
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
            decrementCorruption(player, tag);
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
}
