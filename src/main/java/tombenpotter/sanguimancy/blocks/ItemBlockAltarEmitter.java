package tombenpotter.sanguimancy.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBlockAltarEmitter extends ItemBlock {
    public ItemBlockAltarEmitter(Block block) {
        super(block);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_) {
        if (!GuiScreen.isShiftKeyDown())
            list.add("Press Shift for info");
        else {
            list.add("Place in the corner of a ");
            list.add("9x9 platform around the altar");
        }
    }
}
