package tombenpotter.oldsanguimancy.old.ded;

import WayofTime.alchemicalWizardry.common.items.EnergyItems;
import WayofTime.alchemicalWizardry.common.tileEntity.TETeleposer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import tombenpotter.oldsanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.util.RandomUtils;
import tombenpotter.oldsanguimancy.util.teleporting.TeleportingQueue;

import java.util.List;

public class ItemTelepositionSigil extends Item {

    public ItemTelepositionSigil() {
        this.maxStackSize = 1;
        setCreativeTab(Sanguimancy.tabSanguimancy);
        setUnlocalizedName(Sanguimancy.modid + ".telepositionSigil");
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ri) {
        this.itemIcon = ri.registerIcon(Sanguimancy.texturePath + ":TelepositionSigil");
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        if (stack.stackTagCompound != null) {
            if (!GuiScreen.isShiftKeyDown()) {
                list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.teleposition.sigil.pun"));
                list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.shift.info"));
            } else {
                list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.teleposition.sigil.pun"));
                list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.coordinates") + ": " + stack.stackTagCompound.getInteger("blockX") + ", " + stack.stackTagCompound.getInteger("blockZ") + ", " + stack.stackTagCompound.getInteger("blockZ"));
                list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.bound.dimension") + ": " + getDimensionID(stack.stackTagCompound));
            }
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        RandomUtils.checkAndSetCompound(stack);
        EnergyItems.checkAndSetItemOwner(stack, player);
        int x = stack.stackTagCompound.getInteger("blockX");
        int y = stack.stackTagCompound.getInteger("blockY");
        int z = stack.stackTagCompound.getInteger("blockZ");
        if (!world.isRemote) {
            if (world.provider.dimensionId == getDimensionID(stack.stackTagCompound)) {
                TeleportingQueue.getInstance().teleportSameDim(x, y + 1, z, player, RandomUtils.getItemOwner(stack));
            } else {
                TeleportingQueue.getInstance().teleportToDim(world, getDimensionID(stack.stackTagCompound), x, y + 1, z, player, RandomUtils.getItemOwner(stack));
            }
        }
        return stack;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote && player.isSneaking()) {
            RandomUtils.checkAndSetCompound(stack);
            EnergyItems.checkAndSetItemOwner(stack, player);
            if (world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof TETeleposer) {
                stack.stackTagCompound.setInteger("blockX", x);
                stack.stackTagCompound.setInteger("blockY", y);
                stack.stackTagCompound.setInteger("blockZ", z);
                stack.stackTagCompound.setInteger("dimensionId", world.provider.dimensionId);
                return true;
            }
        }
        return false;
    }

    public int getDimensionID(NBTTagCompound tag) {
        return tag.getInteger("dimensionId");
    }
}
