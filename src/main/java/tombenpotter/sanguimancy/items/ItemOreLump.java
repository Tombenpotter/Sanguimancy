package tombenpotter.sanguimancy.items;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.util.RandomUtils;

import java.util.List;

import javax.annotation.Nullable;

public class ItemOreLump extends Item {
    public IIcon overlayIcon;

    public ItemOreLump() {
        setCreativeTab(Sanguimancy.creativeTab);
        setUnlocalizedName(Sanguimancy.modid + ".oreLump");
        setHasSubtypes(true);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ri) {
        this.itemIcon = ri.registerIcon(Sanguimancy.texturePath + ":OreLumpBase");
        this.overlayIcon = ri.registerIcon(Sanguimancy.texturePath + ":OreLumpOverlay");
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flagIn) {
        NBTTagCompound tag = stack.getTagCompound();

        if (tag == null)
            stack.setTagCompound(new NBTTagCompound());

        String oreName = stack.getTagCompound().getString("ore");

        if (!oreName.equals("")) {
            if (!GuiScreen.isShiftKeyDown())
                tooltip.add(I18n.format("info.Sanguimancy.tooltip.shift.info"));
            else
                tooltip.add(String.format(I18n.format("info.Sanguimancy.tooltip.ore"), RandomUtils.capitalizeFirstLetter(oreName)));
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        for (ItemStack stack : RandomUtils.oreLumpList)
        	items.add(stack);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        NBTTagCompound tag = stack.getTagCompound();

        if (tag == null)
            stack.setTagCompound(new NBTTagCompound());

        String oreName = stack.getTagCompound().getString("ore");

        if (!oreName.equals(""))
            return I18n.format("item.Sanguimancy.oreLump.name", RandomUtils.capitalizeFirstLetter(oreName));
        else
            return I18n.format("info.Sanguimancy.tooltip.any");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getColorFromItemStack(ItemStack stack, int pass) {
        if (pass == 1) {
            if (stack.hasTagCompound()) {
                if (RandomUtils.oreDictColor.containsKey(stack.getTagCompound().getString("ore"))) {
                    return RandomUtils.oreDictColor.get(stack.getTagCompound().getString("ore"));
                } else {
                    return stack.getTagCompound().getString("ore").hashCode();
                }
            } else {
                return 0;
            }
        } else {
            return super.getColorFromItemStack(stack, pass);
        }
    }

    @Override
    public int getRenderPasses(int metadata) {
        return requiresMultipleRenderPasses() ? 2 : 1;
    }

    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        if (pass == 0) {
            return this.itemIcon;
        } else if (pass == 1) {
            return this.overlayIcon;
        }
        return getIconFromDamageForRenderPass(stack.getItemDamage(), pass);
    }
}