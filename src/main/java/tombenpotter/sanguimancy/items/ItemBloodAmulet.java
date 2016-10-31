package tombenpotter.sanguimancy.items;

import WayofTime.bloodmagic.block.BlockLifeEssence;
import WayofTime.bloodmagic.tile.TileBloodTank;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.ItemFluidContainer;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.util.RandomUtils;

import java.util.List;

public class ItemBloodAmulet extends ItemFluidContainer {

    public int bloodLoss = 1200;

    public ItemBloodAmulet() {
        super(Integer.MAX_VALUE);

        setCreativeTab(Sanguimancy.creativeTab);
        setUnlocalizedName(Sanguimancy.modid + ".bloodAmulet");
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5) {
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase livingBase = (EntityLivingBase) entity;
            float health = livingBase.getHealth();
            RandomUtils.checkAndSetCompound(stack);
            FluidHandlerItemStack handler = new FluidHandlerItemStack(stack, capacity);

            if (health < 10F && stack.hasTagCompound() && handler.getFluid() != null) {
                if (handler.getFluid().getFluid() == BlockLifeEssence.getLifeEssence() && handler.getFluid().amount >= bloodLoss) {
                    livingBase.heal(1F);
                    livingBase.motionX = 0;
                    livingBase.motionY = 0;
                    livingBase.motionZ = 0;
                    livingBase.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100, 1));
                    handler.drain(bloodLoss, true);
                }
            }
        }
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileBloodTank) {
            TileBloodTank tile = (TileBloodTank) world.getTileEntity(pos);
            if (tile.tank.getFluid() != null && tile.tank.getFluid().getFluid() == BlockLifeEssence.getLifeEssence()) {
                tile.drain(null, Fluid.BUCKET_VOLUME, true);

                FluidHandlerItemStack handler = new FluidHandlerItemStack(stack, capacity);
                handler.fill(new FluidStack(BlockLifeEssence.getLifeEssence(), Fluid.BUCKET_VOLUME), true);
                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.FAIL;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_) {
        if (stack.hasTagCompound()) {
            if (!GuiScreen.isShiftKeyDown())
                list.add(I18n.format("info.Sanguimancy.tooltip.shift.info"));
            else {
                NBTTagCompound tag = stack.getTagCompound().getCompoundTag("tank");
                if (stack.hasTagCompound() && tag.getString("FluidName") != "") {
                    list.add(I18n.format("info.Sanguimancy.tooltip.fluid") + ": " + RandomUtils.capitalizeFirstLetter(tag.getString("FluidName")));
                    list.add(I18n.format("info.Sanguimancy.tooltip.amount") + ": " + tag.getInteger("Amount") + "/" + capacity + "mB");
                }
            }
        }
    }
}
