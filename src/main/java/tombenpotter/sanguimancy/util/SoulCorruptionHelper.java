package tombenpotter.sanguimancy.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
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

    public static void removeCorruption(NBTTagCompound tag, int amount) {
        int initialAmount = getCorruptionLevel(tag);
        tag.setInteger(soulCorruptionTag, initialAmount - amount);
    }

    public static void spawnChickenFollower(EntityPlayer player) {
        EntityChickenMinion minion = new EntityChickenMinion(player.worldObj);
        minion.setPosition(player.posX, player.posY, player.posZ);
        String owner = player.getUniqueID().toString();
        minion.func_152115_b(owner);
        minion.setTamed(true);
        player.worldObj.spawnEntityInWorld(minion);
    }
}
