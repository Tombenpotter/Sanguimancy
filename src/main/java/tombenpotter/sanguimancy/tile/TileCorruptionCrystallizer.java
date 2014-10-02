package tombenpotter.sanguimancy.tile;

import WayofTime.alchemicalWizardry.ModBlocks;
import WayofTime.alchemicalWizardry.api.alchemy.energy.*;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import WayofTime.alchemicalWizardry.common.tileEntity.TEMasterStone;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.util.SoulCorruptionHelper;

public class TileCorruptionCrystallizer extends TileEntity implements IReagentHandler {

    public int tier = 1;
    public int corruptionStored = 0;
    public String owner;
    public ReagentContainer reagentContainer = new ReagentContainer(4000);

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            if (worldObj.getWorldTime() % 100 == 0) {
                tier = checkMultiblockTier(worldObj, this.xCoord, this.yCoord, this.zCoord);
            }

            if (!owner.equals("")) {
                EntityPlayer player = SoulNetworkHandler.getPlayerForUsername(owner);
                removeAndStoreCorruption(worldObj, player);
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        reagentContainer.readFromNBT(tag);
        owner = tag.getString("owner");
        tier = tag.getInteger("tier");
        corruptionStored = tag.getInteger("corruptionStored");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        reagentContainer.writeToNBT(tag);
        tag.setString("owner", owner);
        tag.setInteger("tier", tier);
        tag.setInteger("corruptionStored", corruptionStored);
    }

    public int checkMultiblockTier(World world, int x, int y, int z) {
        if (world.getBlock(x + 1, y - 1, z + 1) == ModBlocks.blockMasterStone && world.getBlock(x + 1, y - 1, z - 1) == ModBlocks.blockMasterStone && world.getBlock(x - 1, y - 1, z + 1) == ModBlocks.blockMasterStone && world.getBlock(x - 1, y - 1, z - 1) == ModBlocks.blockMasterStone) {
            TEMasterStone waterStone1 = (TEMasterStone) world.getTileEntity(x + 1, y - 1, z + 1);
            TEMasterStone waterStone2 = (TEMasterStone) world.getTileEntity(x + 1, y - 1, z - 1);
            TEMasterStone waterStone3 = (TEMasterStone) world.getTileEntity(x - 1, y - 1, z + 1);
            TEMasterStone waterStone4 = (TEMasterStone) world.getTileEntity(x - 1, y - 1, z - 1);
            if (!waterStone1.getCurrentRitual().equals("") && waterStone1.getCurrentRitual().equals("AW001Water")
                    && !waterStone2.getCurrentRitual().equals("") && waterStone2.getCurrentRitual().equals("AW001Water")
                    && !waterStone3.getCurrentRitual().equals("") && waterStone3.getCurrentRitual().equals("AW001Water")
                    && !waterStone4.getCurrentRitual().equals("") && waterStone4.getCurrentRitual().equals("AW001Water")) {

                if (world.getBlock(x + 2, y - 2, z + 1) == ModBlocks.blockMasterStone && world.getBlock(x + 2, y - 2, z - 1) == ModBlocks.blockMasterStone
                        && world.getBlock(x - 2, y - 2, z + 1) == ModBlocks.blockMasterStone && world.getBlock(x - 2, y - 2, z - 1) == ModBlocks.blockMasterStone
                        && world.getBlock(x + 1, y - 2, z + 2) == ModBlocks.blockMasterStone && world.getBlock(x - 1, y - 2, z + 2) == ModBlocks.blockMasterStone
                        && world.getBlock(x + 1, y - 2, z - 2) == ModBlocks.blockMasterStone && world.getBlock(x - 1, y - 2, z - 2) == ModBlocks.blockMasterStone) {
                    TEMasterStone fireStone1 = (TEMasterStone) world.getTileEntity(x + 2, y - 2, z + 1);
                    TEMasterStone fireStone2 = (TEMasterStone) world.getTileEntity(x + 2, y - 2, z - 1);
                    TEMasterStone fireStone3 = (TEMasterStone) world.getTileEntity(x - 2, y - 2, z + 1);
                    TEMasterStone fireStone4 = (TEMasterStone) world.getTileEntity(x - 2, y - 2, z - 1);
                    TEMasterStone fireStone5 = (TEMasterStone) world.getTileEntity(x + 1, y - 2, z + 2);
                    TEMasterStone fireStone6 = (TEMasterStone) world.getTileEntity(x - 1, y - 2, z + 2);
                    TEMasterStone fireStone7 = (TEMasterStone) world.getTileEntity(x + 1, y - 2, z - 2);
                    TEMasterStone fireStone8 = (TEMasterStone) world.getTileEntity(x - 1, y - 2, z - 2);
                    if (!fireStone1.getCurrentRitual().equals("") && fireStone1.getCurrentRitual().equals("AW002Lava")
                            && !fireStone2.getCurrentRitual().equals("") && fireStone2.getCurrentRitual().equals("AW002Lava")
                            && !fireStone3.getCurrentRitual().equals("") && fireStone3.getCurrentRitual().equals("AW002Lava")
                            && !fireStone4.getCurrentRitual().equals("") && fireStone4.getCurrentRitual().equals("AW002Lava")
                            && !fireStone5.getCurrentRitual().equals("") && fireStone5.getCurrentRitual().equals("AW002Lava")
                            && !fireStone6.getCurrentRitual().equals("") && fireStone6.getCurrentRitual().equals("AW002Lava")
                            && !fireStone7.getCurrentRitual().equals("") && fireStone7.getCurrentRitual().equals("AW002Lava")
                            && !fireStone8.getCurrentRitual().equals("") && fireStone8.getCurrentRitual().equals("AW002Lava")) {
                        return 3;
                    }
                }
            }
        } else if (world.getBlock(x + 1, y - 1, z + 1) == ModBlocks.blockMasterStone && world.getBlock(x + 1, y - 1, z - 1) == ModBlocks.blockMasterStone && world.getBlock(x - 1, y - 1, z + 1) == ModBlocks.blockMasterStone && world.getBlock(x - 1, y - 1, z - 1) == ModBlocks.blockMasterStone) {
            TEMasterStone waterStone1 = (TEMasterStone) world.getTileEntity(x + 1, y - 1, z + 1);
            TEMasterStone waterStone2 = (TEMasterStone) world.getTileEntity(x + 1, y - 1, z - 1);
            TEMasterStone waterStone3 = (TEMasterStone) world.getTileEntity(x - 1, y - 1, z + 1);
            TEMasterStone waterStone4 = (TEMasterStone) world.getTileEntity(x - 1, y - 1, z - 1);
            if (!waterStone1.getCurrentRitual().equals("") && waterStone1.getCurrentRitual().equals("AW001Water")
                    && !waterStone2.getCurrentRitual().equals("") && waterStone2.getCurrentRitual().equals("AW001Water")
                    && !waterStone3.getCurrentRitual().equals("") && waterStone3.getCurrentRitual().equals("AW001Water")
                    && !waterStone4.getCurrentRitual().equals("") && waterStone4.getCurrentRitual().equals("AW001Water")) {
                return 2;
            }
        }
        return 1;
    }

    public void removeAndStoreCorruption(World world, EntityPlayer player) {
        if (player != null) {
            NBTTagCompound tag = SoulCorruptionHelper.getModTag(player, Sanguimancy.modid);
            if (canDrain(ForgeDirection.UNKNOWN, ReagentRegistry.sanctusReagent)) {
                if (SoulCorruptionHelper.isCorruptionOver(tag, 1)) {
                    if ((world.getWorldTime() % 200 == 0)) {
                        drain(ForgeDirection.UNKNOWN, 20, true);
                        SoulCorruptionHelper.decrementCorruption(tag);
                        corruptionStored = corruptionStored + 1;
                    }
                } else if ((world.getWorldTime() % 200 == 0) && corruptionStored > 0) {
                    drain(ForgeDirection.UNKNOWN, 5, true);
                }
            } else {
                while (corruptionStored > 0) {
                    if ((world.getWorldTime() % 100 == 0)) {
                        SoulCorruptionHelper.incrementCorruption(tag);
                        corruptionStored = corruptionStored - 1;
                    }
                }
            }
        }
    }

    @Override
    public final Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        writeToNBT(nbt);
        S35PacketUpdateTileEntity packet = new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbt);
        return packet;
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        NBTTagCompound nbt = pkt.func_148857_g();
        readFromNBT(nbt);
    }

    @Override
    public void markDirty() {
        super.markDirty();
        if (worldObj.isRemote) {
            return;
        }
        this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    /* IReagentHandler */
    @Override
    public int fill(ForgeDirection from, ReagentStack resource, boolean doFill) {
        return reagentContainer.fill(resource, doFill);
    }

    @Override
    public ReagentStack drain(ForgeDirection from, ReagentStack resource, boolean doDrain) {
        if (resource == null || !resource.isReagentEqual(reagentContainer.getReagent())) {
            return null;
        }
        return reagentContainer.drain(resource.amount, doDrain);
    }

    @Override
    public ReagentStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return reagentContainer.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Reagent reagent) {
        return true;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Reagent reagent) {
        return true;
    }

    @Override
    public ReagentContainerInfo[] getContainerInfo(ForgeDirection from) {
        return new ReagentContainerInfo[]{reagentContainer.getInfo()};
    }
}