package tombenpotter.sanguimancy.tiles;

import WayofTime.bloodmagic.api.Constants;
import WayofTime.bloodmagic.api.orb.IBloodOrb;
import WayofTime.bloodmagic.api.util.helper.NBTHelper;
import WayofTime.bloodmagic.api.util.helper.NetworkHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import tombenpotter.sanguimancy.api.objects.Timer;
import tombenpotter.sanguimancy.api.tile.TileComputerBase;
import tombenpotter.sanguimancy.compat.lua.events.LuaOrbMaxed;
import tombenpotter.sanguimancy.compat.lua.methods.LuaGetLifeEssence;
import tombenpotter.sanguimancy.compat.lua.methods.LuaGetOrbMax;
import tombenpotter.sanguimancy.compat.lua.methods.LuaGetOwner;
import tombenpotter.sanguimancy.network.PacketHandler;
import tombenpotter.sanguimancy.network.packets.PacketBloodInterfaceUpdate;

public class TileBloodInterface extends TileComputerBase implements ITickable {

    private int maxOrbLP = 0;
    private int redstone = 0;
    private String ownerName = null;

    private Timer redstoneUpdate;

    public TileBloodInterface() {
        super("BloodInterface", 1);

        redstoneUpdate = new Timer(5);
        //this.addMethod(new LuaGetStackInSlot());
        this.addMethod(new LuaGetLifeEssence());
        this.addMethod(new LuaGetOrbMax());
        this.addMethod(new LuaGetOwner());
    }

    private void triggerUpdate() {
        if (!worldObj.isRemote)
            worldObj.notifyBlockUpdate(pos, worldObj.getBlockState(pos), worldObj.getBlockState(pos), 3);
    }

    private ItemStack getOrb() {
        return getInventory(null).getStackInSlot(0);
    }

    @Override
    public String getType() {
        return "blood_interface";
    }

    public int getLifeEssence() {
        if (ownerName == null || getOrb() == null) return 0;
        return Math.min(maxOrbLP, getCurrentEssence(getOrb()));
    }

    public String getOwner() {
        return ownerName;
    }

    public int getOrbMax() {
        return maxOrbLP;
    }

    @Override
    public void update() {
        super.update();
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

    public void updateOrb() {
        maxOrbLP = getOrb() == null ? 0 : ((IBloodOrb) getOrb().getItem()).getMaxEssence(getOrb().getItemDamage());
        ownerName = getOrb() == null || !getOrb().hasTagCompound() || !getOrb().getTagCompound().hasKey(Constants.NBT.OWNER_UUID) ? null : getOrb().getTagCompound().getString(Constants.NBT.OWNER_UUID);
    }

    public void updateRedstone() {
        int result = calcRedstone();
        if (result != redstone) {
            redstone = result;
            if (worldObj != null && !worldObj.isRemote)
                worldObj.notifyNeighborsOfStateChange(pos, worldObj.getBlockState(pos).getBlock());
        }
    }

    public int calcRedstone() {
        if (getInventory(null).getStackInSlot(0) == null || maxOrbLP == 0)
            return 0;
        return 15 * getLifeEssence() / maxOrbLP;
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
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);

        updateOrb();
        updateRedstone();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);

        return tagCompound;
    }

    public int getComparatorLevel() {
        return redstone;
    }


    public int getCurrentEssence(ItemStack stack) {
        if (stack == null) {
            return 0;
        }

        stack = NBTHelper.checkNBT(stack);

        if (!stack.getTagCompound().hasKey(Constants.NBT.OWNER_UUID)) {
            return 0;
        }

        return NetworkHelper.getSoulNetwork(stack.getTagCompound().getString(Constants.NBT.OWNER_UUID)).getCurrentEssence();
    }
}