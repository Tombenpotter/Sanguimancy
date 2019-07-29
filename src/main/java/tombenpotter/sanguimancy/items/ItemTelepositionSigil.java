package tombenpotter.sanguimancy.items;

import WayofTime.alchemicalWizardry.common.items.EnergyItems;
import WayofTime.alchemicalWizardry.common.tileEntity.TETeleposer;
import WayofTime.bloodmagic.teleport.TeleportQueue;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.util.RandomUtils;

import java.util.List;

public class ItemTelepositionSigil extends Item {

    public ItemTelepositionSigil() {
        this.maxStackSize = 1;
        setCreativeTab(Sanguimancy.creativeTab);
        setUnlocalizedName(Sanguimancy.modid + ".telepositionSigil");
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ri) {
        this.itemIcon = ri.registerIcon(Sanguimancy.texturePath + ":TelepositionSigil");
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        if (stack.getTagCompound() != null) {
            if (!GuiScreen.isShiftKeyDown()) {
                list.add(I18n.format("info.Sanguimancy.tooltip.teleposition.sigil.pun"));
                list.add(I18n.format("info.Sanguimancy.tooltip.shift.info"));
            } else {
                list.add(I18n.format("info.Sanguimancy.tooltip.teleposition.sigil.pun"));
                list.add(I18n.format("info.Sanguimancy.tooltip.coordinates") + ": " + stack.getTagCompound().getInteger("blockX") + ", " + stack.getTagCompound().getInteger("blockZ") + ", " + stack.getTagCompound().getInteger("blockZ"));
                list.add(I18n.format("info.Sanguimancy.tooltip.bound.dimension") + ": " + getDimensionID(stack.getTagCompound()));
            }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn) {
        RandomUtils.checkAndSetCompound(stack);
        EnergyItems.checkAndSetItemOwner(stack, player);
        int x = stack.getTagCompound().getInteger("blockX");
        int y = stack.getTagCompound().getInteger("blockY");
        int z = stack.getTagCompound().getInteger("blockZ");
        if (!world.isRemote) {
            if (world.provider.dimensionId == getDimensionID(stack.getTagCompound())) {
                TeleportQueue.getInstance().teleportSameDim(x, y + 1, z, player, RandomUtils.getItemOwner(stack));
            } else {
                TeleportQueue.getInstance().teleportToDim(world, getDimensionID(stack.getTagCompound()), x, y + 1, z, player, RandomUtils.getItemOwner(stack));
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
                stack.getTagCompound().setInteger("blockX", x);
                stack.getTagCompound().setInteger("blockY", y);
                stack.getTagCompound().setInteger("blockZ", z);
                stack.getTagCompound().setInteger("dimensionId", world.provider.dimensionId);
                return true;
            }
        }
        return false;
    }

    public int getDimensionID(NBTTagCompound tag) {
        return tag.getInteger("dimensionId");
    }
}