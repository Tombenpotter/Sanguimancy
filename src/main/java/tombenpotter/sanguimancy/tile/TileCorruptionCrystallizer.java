package tombenpotter.sanguimancy.tile;

import WayofTime.alchemicalWizardry.ModBlocks;
import WayofTime.alchemicalWizardry.api.alchemy.energy.Reagent;
import WayofTime.alchemicalWizardry.api.alchemy.energy.ReagentRegistry;
import WayofTime.alchemicalWizardry.api.alchemy.energy.ReagentStack;
import WayofTime.alchemicalWizardry.api.alchemy.energy.TileSegmentedReagentHandler;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import WayofTime.alchemicalWizardry.common.tileEntity.TEMasterStone;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import tombenpotter.sanguimancy.api.objects.ICustomNBTTag;
import tombenpotter.sanguimancy.api.soulCorruption.SoulCorruptionHelper;

public class TileCorruptionCrystallizer extends TileSegmentedReagentHandler implements ICustomNBTTag {

    public int corruptionStored = 0;
    public String owner;
    public boolean multiblockFormed;
    private NBTTagCompound customNBTTag;

    public TileCorruptionCrystallizer() {
        customNBTTag = new NBTTagCompound();
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            if (worldObj.getWorldTime() % 100 == 0) {
                multiblockFormed = checkMultiblockTier(worldObj, this.xCoord, this.yCoord, this.zCoord);
                worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            }

            if (!owner.equals("")) {
                EntityPlayer player = SoulNetworkHandler.getPlayerForUsername(owner);
                removeAndStoreCorruption(worldObj, player, this.xCoord, this.yCoord, this.zCoord);
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        owner = tagCompound.getString("owner");
        corruptionStored = tagCompound.getInteger("corruptionStored");
        multiblockFormed = tagCompound.getBoolean("multiblockFormed");
        customNBTTag = tagCompound.getCompoundTag("customNBTTag");
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setString("owner", owner);
        tagCompound.setInteger("corruptionStored", corruptionStored);
        tagCompound.setBoolean("multiblockFormed", multiblockFormed);
        tagCompound.setTag("customNBTTag", customNBTTag);
    }

    public boolean checkMultiblockTier(World world, int x, int y, int z) {
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
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void removeAndStoreCorruption(World world, EntityPlayer player, int x, int y, int z) {
        if (player != null) {
            if (canDrainReagent(ReagentRegistry.sanctusReagent, 20)) {
                if (SoulCorruptionHelper.isCorruptionOver(player.getDisplayName(), 1) && (world.getWorldTime() % 200 == 0)) {
                    drain(ForgeDirection.UNKNOWN, 20, true);
                    SoulCorruptionHelper.decrementCorruption(player.getDisplayName());
                    corruptionStored = corruptionStored + 1;
                    SoulNetworkHandler.syphonFromNetwork(player.getCommandSenderName(), 500);
                } else if (corruptionStored > 0 && canDrainReagent(ReagentRegistry.sanctusReagent, 5)) {
                    if ((world.getWorldTime() % 200 == 0)) {
                        drain(ForgeDirection.UNKNOWN, 5, true);
                        SoulNetworkHandler.syphonFromNetwork(player.getCommandSenderName(), 50);
                    }
                }
            } else {
                if (corruptionStored > 0) {
                    if ((world.getWorldTime() % 100 == 0)) {
                        SoulCorruptionHelper.incrementCorruption(player.getDisplayName());
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

    public boolean canDrainReagent(Reagent reagent, int amount) {
        if (reagent == null || amount == 0) {
            return false;
        }
        ReagentStack reagentStack = new ReagentStack(reagent, amount);
        ReagentStack stack = drain(ForgeDirection.UNKNOWN, reagentStack, false);
        if (stack != null && stack.amount >= amount) {
            return true;
        }
        return false;
    }

    @Override
    public NBTTagCompound getCustomNBTTag() {
        return customNBTTag;
    }

    @Override
    public void setCustomNBTTag(NBTTagCompound tag) {
        customNBTTag = tag;
    }
}