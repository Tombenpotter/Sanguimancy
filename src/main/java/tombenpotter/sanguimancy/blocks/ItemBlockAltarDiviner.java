package tombenpotter.sanguimancy.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemBlockAltarDiviner extends ItemBlock {

    public ItemBlockAltarDiviner(Block block) {
        super(block);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_) {
	    if(!GuiScreen.isShiftKeyDown())
		    list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.shift.info"));
	    else {
		    list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.place.altar"));
	    }
    }
}
