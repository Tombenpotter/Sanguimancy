package tombenpotter.sanguimancy.tiles;

import WayofTime.bloodmagic.api.Constants;
import WayofTime.bloodmagic.api.saving.SoulNetwork;
import WayofTime.bloodmagic.api.util.helper.NetworkHelper;
import WayofTime.bloodmagic.api.util.helper.PlayerHelper;
import WayofTime.bloodmagic.registry.ModRituals;
import WayofTime.bloodmagic.tile.TileMasterRitualStone;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.api.soulCorruption.SoulCorruptionHelper;
import tombenpotter.sanguimancy.api.tile.TileBase;

public class TileCorruptionCrystallizer extends TileBase implements ITickable {

    public BlockPos[] firstRow;
    public BlockPos[] secondRow;

    public int corruptionStored = 0;
    public String ownerUUID;
    public boolean multiblockFormed;

    public TileCorruptionCrystallizer() {
        firstRow = new BlockPos[]{pos.add(1, -1, 1), pos.add(1, -1, -1), pos.add(-1, -1, -1), pos.add(-1, -1, 1)};

        secondRow = new BlockPos[]{pos.add(2, -2, 1), pos.add(2, -2, -1), pos.add(-2, -2, 1), pos.add(-2, -2, -1),
                pos.add(1, -2, 2), pos.add(-1, -2, 2), pos.add(1, -2, -2), pos.add(-1, -2, -3)};
    }

    @Override
    public void update() {
        if (!worldObj.isRemote) {
            if (worldObj.getWorldTime() % 100 == 0) {
                multiblockFormed = checkMultiblock(worldObj);
                markForUpdate();
            }

            if (!ownerUUID.equals("")) {
                removeAndStoreCorruption(worldObj, PlayerHelper.getPlayerFromUUID(ownerUUID));
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);

        ownerUUID = tagCompound.getString(Constants.NBT.OWNER_UUID);
        corruptionStored = tagCompound.getInteger("corruptionStored");
        multiblockFormed = tagCompound.getBoolean("multiblockFormed");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);

        tagCompound.setString(Constants.NBT.OWNER_UUID, ownerUUID);
        tagCompound.setInteger("corruptionStored", corruptionStored);
        tagCompound.setBoolean("multiblockFormed", multiblockFormed);

        return tagCompound;
    }

    public boolean checkMultiblock(World world) {
        for (BlockPos position : firstRow) {
            if (world.getTileEntity(position) instanceof TileMasterRitualStone) {
                TileMasterRitualStone tile = (TileMasterRitualStone) world.getTileEntity(position);
                if (tile.getCurrentRitual() == null || !tile.getCurrentRitual().getName().equals(ModRituals.waterRitual.getName())) {
                    return false;
                }
            } else {
                return false;
            }
        }

        for (BlockPos position : secondRow) {
            if (world.getTileEntity(position) instanceof TileMasterRitualStone) {
                TileMasterRitualStone tile = (TileMasterRitualStone) world.getTileEntity(position);
                if (tile.getCurrentRitual() == null || !tile.getCurrentRitual().getName().equals(ModRituals.lavaRitual.getName())) {
                    return false;
                }
            } else {
                return false;
            }
        }

        return true;
    }

    public void removeAndStoreCorruption(World world, EntityPlayer player) {
        if (player != null) {
            SoulNetwork network = NetworkHelper.getSoulNetwork(ownerUUID);
            if (SoulCorruptionHelper.isCorruptionOver(player, 1) && (world.getWorldTime() % 200 == 0)) {
                SoulCorruptionHelper.decrementCorruption(player);
                corruptionStored++;
                NetworkHelper.syphonAndDamage(network, player, 500);
            }
            if (corruptionStored > 0) {
                if ((world.getWorldTime() % 200 == 0)) {
                    NetworkHelper.syphonAndDamage(network, player, 50);
                }
            }
        }
    }
}