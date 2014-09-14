package tombenpotter.sanguimancy.util;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import tombenpotter.sanguimancy.entity.EntityChickenMinion;

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

    public static int getCorruptionLevel(NBTTagCompound tag) {
        return tag.getInteger(soulCorruptionTag);
    }

    public static void negateCorruption(NBTTagCompound tag) {
        tag.setInteger(soulCorruptionTag, 0);
    }

    public static void setCorruptionLevel(NBTTagCompound tag, int amount) {
        tag.setInteger(soulCorruptionTag, amount);
    }

    public static void addCorruption(NBTTagCompound tag, int amount) {
        int initialAmount = getCorruptionLevel(tag);
        tag.setInteger(soulCorruptionTag, initialAmount + amount);
    }

    public static void incrementCorruption(NBTTagCompound tag) {
        int initialAmount = getCorruptionLevel(tag);
        tag.setInteger(soulCorruptionTag, initialAmount + 1);
    }

    public static void decrementCorruption(NBTTagCompound tag) {
        int initialAmount = getCorruptionLevel(tag);
        tag.setInteger(soulCorruptionTag, initialAmount - 1);
    }

    public static void removeCorruption(NBTTagCompound tag, int amount) {
        int initialAmount = getCorruptionLevel(tag);
        tag.setInteger(soulCorruptionTag, initialAmount - amount);
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
            player.worldObj.addWeatherEffect(new EntityLightningBolt(player.worldObj, player.posX, player.posY, player.posZ));
            if (player.worldObj.rand.nextInt(9) == 0) {
                player.addPotionEffect(new PotionEffect(Potion.blindness.id, 20, 0));
            }
            if (player.worldObj.rand.nextInt(9) == 0) {
                player.addPotionEffect(new PotionEffect(Potion.confusion.id, 20, 0));
            }
            if (player.worldObj.rand.nextInt(9) == 0) {
                player.addPotionEffect(new PotionEffect(Potion.hunger.id, 20, 0));
            }
            if (player.worldObj.rand.nextInt(9) == 0) {
                player.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 20, 0));
            }
            if (player.worldObj.rand.nextInt(9) == 0) {
                player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 20, 0));
            }
            if (player.worldObj.rand.nextInt(9) == 0) {
                player.addPotionEffect(new PotionEffect(Potion.weakness.id, 20, 0));
            }
            int i = (int) (player.posX + player.worldObj.rand.nextInt(15) - player.worldObj.rand.nextInt(15));
            int j = (int) (player.posY + player.worldObj.rand.nextInt(15) - player.worldObj.rand.nextInt(15));
            int k = (int) (player.posZ + player.worldObj.rand.nextInt(15) - player.worldObj.rand.nextInt(15));
            if (j <= 5) j = j + 10;
            player.setPositionAndUpdate(i, j, k);
        }
    }
}
