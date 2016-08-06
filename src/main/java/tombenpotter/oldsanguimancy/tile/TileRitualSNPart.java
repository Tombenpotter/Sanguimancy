package tombenpotter.oldsanguimancy.tile;

import WayofTime.alchemicalWizardry.ModItems;
import WayofTime.alchemicalWizardry.api.event.RitualRunEvent;
import WayofTime.alchemicalWizardry.api.event.RitualStopEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import tombenpotter.sanguimancy.api.objects.BlockPostition;
import tombenpotter.sanguimancy.api.objects.ICustomNBTTag;
import tombenpotter.oldsanguimancy.api.snManifestation.EnumSNType;
import tombenpotter.oldsanguimancy.api.tile.TileBaseSNPart;

import java.util.HashMap;

public class TileRitualSNPart extends TileBaseSNPart implements ICustomNBTTag {

    public NBTTagCompound customNBTTag;
    public int xRitual;
    public int yRitual;
    public int zRitual;

    public TileRitualSNPart() {
        customNBTTag = new NBTTagCompound();
    }

    public void validate() {
        super.validate();
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void invalidate() {
        MinecraftForge.EVENT_BUS.unregister(this);
        super.invalidate();
    }

    public void onChunkUnload() {
        MinecraftForge.EVENT_BUS.unregister(this);
        super.onChunkUnload();
    }

    @Override
    public EnumSNType getType() {
        return EnumSNType.RITUAL;
    }

    @Override
    public boolean isSNKnot() {
        return false;
    }

    @Override
    public void onNetworkUpdate(BlockPostition originalPosition) {
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        customNBTTag = tagCompound.getCompoundTag("customNBTTag");
        this.xRitual = tagCompound.getInteger("xRitual");
        this.yRitual = tagCompound.getInteger("yRitual");
        this.zRitual = tagCompound.getInteger("zRitual");
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setTag("customNBTTag", customNBTTag);
        tagCompound.setInteger("xRitual", xRitual);
        tagCompound.setInteger("yRitual", yRitual);
        tagCompound.setInteger("zRitual", zRitual);
    }

    @Override
    public NBTTagCompound getCustomNBTTag() {
        return customNBTTag;
    }

    @Override
    public void setCustomNBTTag(NBTTagCompound tag) {
        customNBTTag = tag;
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
        super.markDirty(); // Mark dirty for gamesave
        if (worldObj.isRemote) {
            return;
        }
        this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord); // Update block + TE via Network
    }

    @SubscribeEvent
    public void disableLinkedRitual(RitualRunEvent event) {
        HashMap<BlockPostition, SNKNotBoolean> map = getComponentsInNetwork().hashMap;
        for (BlockPostition postition : map.keySet()) {
            if (map.get(postition).isSNKnot && map.get(postition).isSNKnotActive && (worldObj.getBlockPowerInput(xCoord, yCoord, zCoord) > 0 || worldObj.getStrongestIndirectPower(xCoord, yCoord, zCoord) > 0)) {
                if (event.mrs.getXCoord() == xRitual && event.mrs.getYCoord() == yRitual && event.mrs.getZCoord() == zRitual) {
                    this.xRitual = event.mrs.getXCoord();
                    this.yRitual = event.mrs.getYCoord();
                    this.zRitual = event.mrs.getZCoord();
                    event.setCanceled(true);
                }
            }
            if (map.get(postition).isSNKnot && !map.get(postition).isSNKnotActive) {
                if (event.mrs.getXCoord() == xRitual && event.mrs.getYCoord() == yRitual && event.mrs.getZCoord() == zRitual) {
                    this.xRitual = event.mrs.getXCoord();
                    this.yRitual = event.mrs.getYCoord();
                    this.zRitual = event.mrs.getZCoord();
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public void removeMasterStone(RitualStopEvent event) {
        if (event.mrs.getXCoord() == xRitual && event.mrs.getYCoord() == yRitual && event.mrs.getZCoord() == zRitual) {
            worldObj.setBlockToAir(xCoord, yCoord, zCoord);
        }
    }

    public boolean onBlockRightClicked(EntityPlayer player, ItemStack stack) {
        if (stack != null) {
            if (stack.isItemEqual(new ItemStack(ModItems.divinationSigil)) || stack.isItemEqual(new ItemStack(ModItems.itemSeerSigil))) {
                if (!worldObj.isRemote) {
                    player.addChatComponentMessage(new ChatComponentText("x: " + String.valueOf(xRitual) + " y: " + String.valueOf(yRitual) + " z: " + String.valueOf(zRitual)));
                }
                return true;
            }
        }
        return false;
    }
}
