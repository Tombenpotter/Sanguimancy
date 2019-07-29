package tombenpotter.sanguimancy.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import tombenpotter.sanguimancy.api.tiles.TileBase;

public class TileBloodTank extends TileBase implements IFluidHandler {

    public int capacity;
    public FluidTank tank;

    public TileBloodTank() {
        capacity = 16 * FluidContainerRegistry.BUCKET_VOLUME;
        tank = new FluidTank(capacity);
        customNBTTag = new NBTTagCompound();
    }

    public TileBloodTank(int capacity) {
        capacity = capacity * FluidContainerRegistry.BUCKET_VOLUME;
        tank = new FluidTank(capacity);
        customNBTTag = new NBTTagCompound();
    }

    @Override
    public void update() {
        if (world.getWorldTime() % 60 == 0) world.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return tank.fill(resource, doFill);
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return tank.drain(resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return tank.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return true;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return true;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[]{tank.getInfo()};
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        tank.readFromNBT(tagCompound.getCompoundTag("tank"));
        capacity = tagCompound.getInteger("capacity");
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        if (tank.getFluidAmount() != 0) tagCompound.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
        tagCompound.setInteger("capacity", capacity);
    }

	@Override
	public IFluidTankProperties[] getTankProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		// TODO Auto-generated method stub
		return null;
	}
}