package tombenpotter.sanguimancy.tiles;


import WayofTime.bloodmagic.block.BlockLifeEssence;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import tombenpotter.sanguimancy.api.tiles.TileBaseSidedInventory;
import tombenpotter.sanguimancy.recipes.RecipeBloodCleanser;

public class TileBloodCleaner extends TileBaseSidedInventory implements ITickable {

    public int ticksLeft;
    public int maxTicks;
    public boolean isActive;
    protected FluidTank tank;

    public TileBloodCleaner() {
        super(2);

        tank = new FluidTank(BlockLifeEssence.getLifeEssence(), Fluid.BUCKET_VOLUME, 16 * Fluid.BUCKET_VOLUME);

        maxTicks = 150;
        isActive = false;
    }

    @Override
    public void update() {
        if (inventory.getStackInSlot(0) != null && canBloodClean()) {
            if (ticksLeft >= maxTicks) {
                bloodClean();
                ticksLeft = 0;
                markForUpdate();
            } else {
                ticksLeft++;
            }
            isActive = true;
        } else {
            ticksLeft = 0;
            isActive = false;
            markForUpdate();
        }
    }

    public void bloodClean() {
        if (canBloodClean()) {
            ItemStack ouput = RecipeBloodCleanser.getRecipe(inventory.getStackInSlot(0)).fOutput.copy();
            if (inventory.getStackInSlot(1) == null) {
                inventory.setStackInSlot(1, ouput);
            } else if (inventory.getStackInSlot(1).isItemEqual(RecipeBloodCleanser.getRecipe(inventory.getStackInSlot(0)).fOutput.copy())) {
                inventory.getStackInSlot(1).grow(ouput.getCount());
            }
            getInventory(null).extractItem(0, RecipeBloodCleanser.getRecipe(inventory.getStackInSlot(0)).fInput.getCount(), false);
            tank.drain(Fluid.BUCKET_VOLUME, true);
        }
    }

    public boolean canBloodClean() {
        if (getInventory(null).getStackInSlot(0) == null) {
            return false;
        } else {
            ItemStack input = getInventory(null).getStackInSlot(0);
            if (!RecipeBloodCleanser.isRecipeValid(input)) {
                return false;
            }
            if (tank.getFluid() == null) {
                return false;
            }
            if (tank.getFluidAmount() < FluidContainerRegistry.BUCKET_VOLUME) {
                return false;
            }
            RecipeBloodCleanser recipe = RecipeBloodCleanser.getRecipe(input);
            ItemStack output = recipe.fOutput.copy();
            if (inventory.getStackInSlot(1) == null) {
                return true;
            }
            if (!inventory.getStackInSlot(1).isItemEqual(output)) {
                return false;
            }
            if (!(tank.getFluid().amount >= FluidContainerRegistry.BUCKET_VOLUME)) {
                return false;
            }
            int result = inventory.getStackInSlot(1).getCount() + output.getCount();
            return result <= inventory.getStackInSlot(1).getMaxStackSize();
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && sidesAllowed.contains(facing)) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && sidesAllowed.contains(facing)) {
            return (T) tank;
        }
        return super.getCapability(capability, facing);
    }

    public FluidTank getTank(EnumFacing facing) {
        return (FluidTank) getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);

        ticksLeft = tagCompound.getInteger("ticksLeft");
        isActive = tagCompound.getBoolean("isActive");
        CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.readNBT(tank, null, tagCompound.getCompoundTag("tank"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);

        tagCompound.setInteger("ticksLeft", ticksLeft);
        tagCompound.setBoolean("isActive", isActive);
        tagCompound.setTag("tank", CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.writeNBT(tank, null));

        return tagCompound;
    }
}
