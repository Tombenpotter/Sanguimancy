package tombenpotter.sanguimancy.blocks.items;

import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tombenpotter.sanguimancy.blocks.BlockBloodTank;
import tombenpotter.sanguimancy.util.RandomUtils;

import java.util.List;

public class ItemBlockBloodTank extends ItemBlock implements IFluidContainerItem {

    public ItemBlockBloodTank(Block block) {
        super(block);
        setHasSubtypes(true);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean p_77624_4_) {
        list.add(String.format(I18n.format("text.recipe.altar.tier"), stack.getItemDamage() + 1));

        if (!GuiScreen.isShiftKeyDown())
            list.add(I18n.format("info.Sanguimancy.tooltip.shift.info"));
        else {
            list.add(I18n.format("info.Sanguimancy.tooltip.capacity") + ": " + String.valueOf(getCapacity(stack)) + "mB");
            if (stack.hasTagCompound()) {
                NBTTagCompound tag = stack.getTagCompound().getCompoundTag("tank");
                if (tag.getString("FluidName") != "") {
                    list.add(I18n.format("info.Sanguimancy.tooltip.fluid") + ": " + RandomUtils.capitalizeFirstLetter(tag.getString("FluidName")));
                    list.add(I18n.format("info.Sanguimancy.tooltip.amount") + ": " + tag.getInteger("Amount") + "/" + getCapacity(stack) + "mB");
                }
            }
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("tank") && stack.getTagCompound().getCompoundTag("tank").getString("FluidName") != "") {
            NBTTagCompound tag = stack.getTagCompound().getCompoundTag("tank");
            return super.getItemStackDisplayName(stack) + " (" + RandomUtils.capitalizeFirstLetter(RandomUtils.capitalizeFirstLetter(tag.getString("FluidName")) + ")");
        } else
            return super.getItemStackDisplayName(stack);
    }

    @Override
    public FluidStack getFluid(ItemStack stack) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("tank") && stack.getTagCompound().getCompoundTag("tank").getString("FluidName") != "") {
            NBTTagCompound tag = stack.getTagCompound().getCompoundTag("tank");
            return FluidStack.loadFluidStackFromNBT(tag);
        }
        return null;
    }

    @Override
    public int getCapacity(ItemStack container) {
        return BlockBloodTank.capacities[container.getItemDamage()] * FluidContainerRegistry.BUCKET_VOLUME;
    }

    @Override
    public int fill(ItemStack stack, FluidStack resource, boolean doFill) {
        if (resource == null || stack.getCount() != 1) return 0;
        int fillAmount = 0, capacity = getCapacity(stack);
        NBTTagCompound tag = stack.getTagCompound(), fluidTag = null;
        FluidStack fluid = null;
        if (tag == null || !tag.hasKey("tank") || (fluidTag = tag.getCompoundTag("tank")) == null || (fluid = FluidStack.loadFluidStackFromNBT(fluidTag)) == null)
            fillAmount = Math.min(capacity, resource.amount);
        if (fluid == null) {
            if (doFill) {
                fluid = resource.copy();
                fluid.amount = 0;
            }
        } else if (!fluid.isFluidEqual(resource))
            return 0;
        else
            fillAmount = Math.min(capacity - fluid.amount, resource.amount);
        fillAmount = Math.max(fillAmount, 0);
        if (doFill) {
            if (tag == null) {
                stack.setTagCompound(new NBTTagCompound());
            	tag = stack.getTagCompound();
            }
            fluid.amount += fillAmount;
            tag.setTag("tank", fluid.writeToNBT(fluidTag == null ? new NBTTagCompound() : fluidTag));
        }
        return fillAmount;
    }

    @Override
    public FluidStack drain(ItemStack stack, int maxDrain, boolean doDrain) {
        NBTTagCompound tag = stack.getTagCompound(), fluidTag = null;
        FluidStack fluid = null;
        if (tag == null || !tag.hasKey("tank") || (fluidTag = tag.getCompoundTag("tank")) == null || (fluid = FluidStack.loadFluidStackFromNBT(fluidTag)) == null) {
            if (fluidTag != null)
                tag.removeTag("tank");
            return null;
        }
        int drainAmount = Math.min(maxDrain, fluid.amount);
        if (doDrain) {
            tag.removeTag("tank");
            fluid.amount -= drainAmount;
            if (fluid.amount > 0)
                fill(stack, fluid, true);
        }
        fluid.amount = drainAmount;
        return fluid;
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }
}