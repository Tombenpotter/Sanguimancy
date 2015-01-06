package tombenpotter.sanguimancy.items;

import WayofTime.alchemicalWizardry.AlchemicalWizardry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.tile.TileBloodTank;
import tombenpotter.sanguimancy.util.RandomUtils;

import java.util.List;

public class ItemBloodAmulet extends Item implements IFluidContainerItem {

    public int bloodLoss = 1200;

    public ItemBloodAmulet() {
        setCreativeTab(Sanguimancy.tabSanguimancy);
        setUnlocalizedName(Sanguimancy.modid + ".bloodAmulet");
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ri) {
        this.itemIcon = ri.registerIcon(Sanguimancy.texturePath + ":BloodAmulet");
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5) {
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase livingBase = (EntityLivingBase) entity;
            float health = livingBase.getHealth();
            RandomUtils.checkAndSetCompound(stack);
            if (health < 10F && stack.hasTagCompound() && getFluid(stack) != null) {
                if (getFluid(stack).fluidID == new FluidStack(AlchemicalWizardry.lifeEssenceFluid, 1).fluidID && getFluid(stack).amount >= bloodLoss) {
                    livingBase.heal(1F);
                    livingBase.motionX = 0;
                    livingBase.motionY = 0;
                    livingBase.motionZ = 0;
                    livingBase.addPotionEffect(new PotionEffect(Potion.blindness.id, 100, 1));
                    if (drain(stack, bloodLoss, false) != null) {
                        drain(stack, bloodLoss, true);
                    }
                }
            }
        }
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
        if (world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof TileBloodTank) {
            TileBloodTank tile = (TileBloodTank) world.getTileEntity(x, y, z);
            if (tile.tank.getFluid() != null && tile.tank.getFluid().fluidID == AlchemicalWizardry.lifeEssenceFluid.getID()) {
                tile.drain(ForgeDirection.UNKNOWN, FluidContainerRegistry.BUCKET_VOLUME, true);
                fill(stack, new FluidStack(AlchemicalWizardry.lifeEssenceFluid, FluidContainerRegistry.BUCKET_VOLUME), true);
                return true;
            }
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_) {
        if (stack.hasTagCompound()) {
            if (!GuiScreen.isShiftKeyDown())
                list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.shift.info"));
            else {
                NBTTagCompound tag = stack.stackTagCompound.getCompoundTag("tank");
                if (stack.hasTagCompound() && tag.getString("FluidName") != "") {
                    list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.fluid") + ": " + RandomUtils.capitalizeFirstLetter(tag.getString("FluidName")));
                    list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.amount") + ": " + tag.getInteger("Amount") + "/" + getCapacity(stack) + "mB");
                }
            }
        }
    }

    @Override
    public FluidStack getFluid(ItemStack stack) {
        if (stack.hasTagCompound() && stack.stackTagCompound.hasKey("tank") && stack.stackTagCompound.getCompoundTag("tank").getString("FluidName") != "") {
            NBTTagCompound tag = stack.stackTagCompound.getCompoundTag("tank");
            return FluidStack.loadFluidStackFromNBT(tag);
        }
        return null;
    }

    @Override
    public int getCapacity(ItemStack container) {
        return Integer.MAX_VALUE;
    }

    @Override
    public int fill(ItemStack stack, FluidStack resource, boolean doFill) {
        if (resource == null || stack.stackSize != 1) return 0;
        if (resource.fluidID != AlchemicalWizardry.lifeEssenceFluid.getID()) {
            return 0;
        }
        int fillAmount = 0, capacity = getCapacity(stack);
        NBTTagCompound tag = stack.stackTagCompound, fluidTag = null;
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
            if (tag == null)
                tag = stack.stackTagCompound = new NBTTagCompound();
            fluid.amount += fillAmount;
            tag.setTag("tank", fluid.writeToNBT(fluidTag == null ? new NBTTagCompound() : fluidTag));
        }
        return fillAmount;
    }

    @Override
    public FluidStack drain(ItemStack stack, int maxDrain, boolean doDrain) {
        NBTTagCompound tag = stack.stackTagCompound, fluidTag = null;
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
}
