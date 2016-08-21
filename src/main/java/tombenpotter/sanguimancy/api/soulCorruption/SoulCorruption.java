package tombenpotter.sanguimancy.api.soulCorruption;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class SoulCorruption implements IExtendedEntityProperties {

    public static String ID = "SoulCorruption";
    public int soulCorruption;

    public SoulCorruption() {
        soulCorruption = 0;
    }

    public static void create(EntityPlayer player) {
        player.registerExtendedProperties(ID, new SoulCorruption());
    }

    public static SoulCorruption get(EntityPlayer player) {
        return (SoulCorruption) player.getExtendedProperties(ID);
    }

    public static int getSoulCorruption(EntityPlayer player) {
        if (player == null) {
            return 0;
        } else {
            return get(player).soulCorruption;
        }
    }

    public void setSoulCorruption(int level) {
        soulCorruption = level;
    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        compound.setInteger(ID, soulCorruption);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        soulCorruption = compound.getInteger(ID);
    }

    @Override
    public void init(Entity entity, World world) {
        soulCorruption = 0;
    }
}
