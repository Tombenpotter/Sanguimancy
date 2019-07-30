package tombenpotter.sanguimancy.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import tombenpotter.sanguimancy.api.tiles.TileBase;

public class TileBloodTank extends TileBase implements IFluidHandler {
    public int capacity;
    public FluidTank tank;

    public TileBloodTank() {
        capacity = 16 * Fluid.BUCKET_VOLUME;
        tank = new FluidTank(capacity);
        customNBTTag = new NBTTagCompound();
    }

    public TileBloodTank(int capacity) {
        capacity = capacity * Fluid.BUCKET_VOLUME;
        tank = new FluidTank(capacity);
        customNBTTag = new NBTTagCompound();
    }

    @Override
    public void markForUpdate() {
    	IBlockState state = world.getBlockState(pos);
    	
        if (world.getWorldTime() % 60 == 0)
        	world.notifyBlockUpdate(pos, state, state, 0);
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        return tank.fill(resource, doFill);
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        return tank.drain(resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        return tank.drain(maxDrain, doDrain);
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return tank.getTankProperties();
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        tank.readFromNBT(tagCompound.getCompoundTag("tank"));
        capacity = tagCompound.getInteger("capacity");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        if (tank.getFluidAmount() != 0) tagCompound.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
        tagCompound.setInteger("capacity", capacity);
		return tagCompound;
    }
}