package tombenpotter.oldsanguimancy.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tombenpotter.oldsanguimancy.api.tile.TileBaseInventory;

public class TileSacrificeTransfer extends TileBaseInventory {

    public TileSacrificeTransfer() {
        customNBTTag = new NBTTagCompound();
        slots = new ItemStack[1];
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public String getInventoryName() {
        return "Sacrifice Transfer";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }
}
