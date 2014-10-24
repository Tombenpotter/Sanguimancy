package tombenpotter.sanguimancy.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.util.RandomUtils;

import java.util.List;

public class ItemOreLump extends Item {

    public ItemOreLump() {
        setCreativeTab(Sanguimancy.tabSanguimancy);
        setUnlocalizedName(Sanguimancy.modid + ".oreLump");
        setHasSubtypes(true);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ri) {
        this.itemIcon = ri.registerIcon(Sanguimancy.texturePath + ":OreLump");
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        if (!GuiScreen.isShiftKeyDown()) {
            list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.shift.info"));
        }
        if (GuiScreen.isShiftKeyDown()) {
            if (stack.hasTagCompound()) {
                list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.ore") + ": " + stack.stackTagCompound.getString("ore"));
            } else {
                list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.any"));
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List list) {
        for (String ore : OreDictionary.getOreNames()) {
            if (ore.startsWith("ore")) {
                String output = ore.substring(3);
                if (!OreDictionary.getOres(ore).isEmpty() && !OreDictionary.getOres("ingot" + output).isEmpty()) {
                    ItemStack stack = new ItemStack(this);
                    RandomUtils.checkAndSetCompound(stack);
                    stack.stackTagCompound.setString("ore", output);
                    list.add(stack);
                }
            }
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (stack.hasTagCompound()) {
            return RandomUtils.capitalizeFirstLetter(stack.stackTagCompound.getString("ore")) + " " + super.getItemStackDisplayName(stack);
        } else return super.getItemStackDisplayName(stack);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getColorFromItemStack(ItemStack stack, int id) {
        if (stack.hasTagCompound()) {
            if (RandomUtils.oreDictColor.containsKey(stack.stackTagCompound.getString("ore"))) {
                return RandomUtils.oreDictColor.get(stack.stackTagCompound.getString("ore"));
            } else {
                return stack.stackTagCompound.getString("ore").hashCode();
            }
        } else {
            return 0;
        }
    }
}
