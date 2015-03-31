package tombenpotter.sanguimancy.tile;

import WayofTime.alchemicalWizardry.api.items.interfaces.IBloodOrb;
import WayofTime.alchemicalWizardry.common.items.EnergyBattery;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import tombenpotter.sanguimancy.api.objects.Timer;
import tombenpotter.sanguimancy.api.tile.TileComputerBase;
import tombenpotter.sanguimancy.compat.lua.events.LuaOrbMaxed;
import tombenpotter.sanguimancy.compat.lua.methods.LuaGetLifeEssence;
import tombenpotter.sanguimancy.compat.lua.methods.LuaGetOrbMax;
import tombenpotter.sanguimancy.compat.lua.methods.LuaGetOwner;
import tombenpotter.sanguimancy.network.PacketHandler;
import tombenpotter.sanguimancy.network.packets.PacketBloodInterfaceUpdate;
import tombenpotter.sanguimancy.registry.BlocksRegistry;

public class TileBloodInterface extends TileComputerBase implements IInventory {

    private ItemStack itemStack;
    private int maxOrbLP = 0;
    private int redstone = 0;
    private String ownerName = null;
    private Timer redstoneUpdate;
    private NBTTagCompound custoomNBTTag;

    public TileBloodInterface() {
        super("BloodInterface");
        redstoneUpdate = new Timer(5);
        //this.addMethod(new LuaGetStackInSlot());
        this.addMethod(new LuaGetLifeEssence());
        this.addMethod(new LuaGetOrbMax());
        this.addMethod(new LuaGetOwner());
        custoomNBTTag = new NBTTagCompound();
    }

    private void triggerUpdate() {
        if (!worldObj.isRemote) worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    @Override
    public String getType() {
        return "blood_interface";
    }

    public int getLifeEssence() {
        if (ownerName == null || itemStack == null) return 0;
        return Math.min(maxOrbLP, ((EnergyBattery) itemStack.getItem()).getCurrentEssence(itemStack));
    }

    public String getOwner() {
        return ownerName;
    }

    public int getOrbMax() {
        return maxOrbLP;
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return slot == 0 ? itemStack : null;
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        ItemStack stack = getStackInSlot(slot);
        if (stack != null) {
            setInventorySlotContents(slot, null);
        }
        return stack;
    }

    public void breakInterface() {
        if (!worldObj.isRemote) {
            if (itemStack != null) {
                float spawnX = xCoord + 0.5F;
                float spawnY = yCoord + 0.5F;
                float spawnZ = zCoord + 0.5F;
                EntityItem droppedItem = new EntityItem(this.worldObj, spawnX, spawnY, spawnZ, itemStack);
                this.worldObj.spawnEntityInWorld(droppedItem);
            }
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
    }

    @Override
    public void serverUpdate() {
        super.serverUpdate();
        if (redstoneUpdate.update()) updateRedstone();
    }

    @Override
    public void init() {
        super.init();
        this.addEvent(new LuaOrbMaxed(), 5);
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemStack) {
        if (slot == 0) {
            this.itemStack = itemStack;
            updateOrb();
            updateRedstone();
            markDirty();
        }
    }

    public void updateOrb() {
        maxOrbLP = itemStack == null ? 0 : ((EnergyBattery) itemStack.getItem()).getMaxEssence();
        ownerName = itemStack == null || !itemStack.hasTagCompound() || !itemStack.getTagCompound().hasKey("ownerName") ? null : itemStack.getTagCompound().getString("ownerName");
    }

    public void updateRedstone() {
        int result = calcRedstone();
        if (result != redstone) {
            redstone = result;
            if (worldObj != null && !worldObj.isRemote)
                worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, BlocksRegistry.bloodInterface);
        }
    }

    public int calcRedstone() {
        if (itemStack == null || maxOrbLP == 0) return 0;
        return 15 * getLifeEssence() / maxOrbLP;
    }

    @Override
    public String getInventoryName() {
        return this.getType();
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 0;
    }

    @Override
    public void markDirty() {
        super.markDirty();
        if (worldObj != null && !worldObj.isRemote) {
            PacketBloodInterfaceUpdate message = new PacketBloodInterfaceUpdate(this);
            PacketHandler.INSTANCE.sendToAll(message);
        }
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
        return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && entityPlayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
        if (this.itemStack != null && itemStack != null) return false;
        if (this.itemStack != null && itemStack == null) return true;
        NBTTagCompound itemTag = itemStack != null ? itemStack.stackTagCompound : null;
        if (itemTag == null || !itemTag.hasKey("ownerName") || itemTag.getString("ownerName").equals("")) return false;
        return slot == 0 && itemStack.getItem() instanceof IBloodOrb;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        NBTTagCompound item = compound.getCompoundTag("Item");
        if (item != null) {
            itemStack = ItemStack.loadItemStackFromNBT(item);
            updateOrb();
            updateRedstone();
        }
        custoomNBTTag = compound.getCompoundTag("customNBTTag");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if (itemStack != null) {
            compound.setTag("Item", itemStack.writeToNBT(new NBTTagCompound()));
        }
        compound.setTag("customNBTTag", custoomNBTTag);
    }

    public int getComparatorLevel() {
        return redstone;
    }

    @Override
    public Packet getDescriptionPacket() {
        writeToNBT(new NBTTagCompound());
        return PacketHandler.INSTANCE.getPacketFrom(new PacketBloodInterfaceUpdate(this));
    }

    @Override
    public NBTTagCompound getCustomNBTTag() {
        return custoomNBTTag;
    }

    @Override
    public void setCustomNBTTag(NBTTagCompound tag) {
        custoomNBTTag = tag;
    }
}
