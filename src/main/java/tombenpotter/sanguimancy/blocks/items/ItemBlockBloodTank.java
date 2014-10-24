package tombenpotter.sanguimancy.blocks.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import tombenpotter.sanguimancy.util.RandomUtils;

import java.util.List;

public class ItemBlockBloodTank extends ItemBlock {

    public ItemBlockBloodTank(Block block) {
        super(block);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_) {
        if (stack.hasTagCompound()) {
            if (!GuiScreen.isShiftKeyDown())
                list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.shift.info"));
            else {
                if (stack.hasTagCompound() && stack.stackTagCompound.getString("FluidName") != "") {
                    list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.fluid") + ": " + RandomUtils.capitalizeFirstLetter(stack.stackTagCompound.getString("FluidName")));
                    list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.amount") + ": " + stack.stackTagCompound.getInteger("Amount") + "/" + stack.stackTagCompound.getInteger("capacity") + "mB");
                }
            }
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (stack.hasTagCompound() && stack.stackTagCompound.getString("FluidName") != "") {
            return super.getItemStackDisplayName(stack) + " (" + RandomUtils.capitalizeFirstLetter(RandomUtils.capitalizeFirstLetter(stack.stackTagCompound.getString("FluidName")) + ")");
        } else
            return super.getItemStackDisplayName(stack) + " (" + StatCollector.translateToLocal("info.Sanguimancy.tooltip.none") + ")";
    }
}
