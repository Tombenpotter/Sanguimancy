package tombenpotter.sanguimancy.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.tile.TileBloodTank;
import tombenpotter.sanguimancy.util.RandomUtils;

import java.util.List;

public class ItemBloodAmulet extends Item {

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
            if (health < 10F && stack.hasTagCompound() && stack.stackTagCompound.hasKey("blood")) {
                if (stack.stackTagCompound.getInteger("blood") >= bloodLoss) {
                    livingBase.heal(1F);
                    livingBase.motionX = 0;
                    livingBase.motionY = 0;
                    livingBase.motionZ = 0;
                    livingBase.addPotionEffect(new PotionEffect(Potion.blindness.id, 100, 1));
                    stack.stackTagCompound.setInteger("blood", stack.stackTagCompound.getInteger("blood") - bloodLoss);
                }
            }
        }
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10) {
        if (!world.isRemote && addBloodAndDrainTank(stack, world, x, y, z)) {
            return true;
        }
        return super.onItemUse(stack, player, world, x, y, z, par7, par8, par9, par10);
    }

    public boolean addBloodAndDrainTank(ItemStack stack, World world, int x, int y, int z) {
        RandomUtils.checkAndSetCompound(stack);
        if (world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof TileBloodTank) {
            TileBloodTank tile = (TileBloodTank) world.getTileEntity(x, y, z);
            if (tile.tank.getFluidAmount() >= FluidContainerRegistry.BUCKET_VOLUME) {
                tile.drain(ForgeDirection.UNKNOWN, FluidContainerRegistry.BUCKET_VOLUME, true);
                RandomUtils.checkAndSetCompound(stack);
                stack.stackTagCompound.setInteger("blood", stack.stackTagCompound.getInteger("blood") + FluidContainerRegistry.BUCKET_VOLUME);
                world.markBlockForUpdate(x, y, z);
                return true;
            }
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_) {
        if (!GuiScreen.isShiftKeyDown()) {
            list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.shift.info"));
        }
        if (GuiScreen.isShiftKeyDown()) {
            if (stack.hasTagCompound() && stack.stackTagCompound.hasKey("blood")) {
                list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.amount") + ": " + stack.stackTagCompound.getInteger("blood") + " mB");
                list.add(bloodLoss + " mB " + StatCollector.translateToLocal("info.Sanguimancy.toolitp.per.point"));
            }
        }
    }
}
