package tombenpotter.sanguimancy.blocks.items;

import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemBlockAltarDiviner extends ItemBlock {

    public ItemBlockAltarDiviner(Block block) {
        super(block);
        setRegistryName(block.getRegistryName());
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean p_77624_4_) {
        if (!GuiScreen.isShiftKeyDown())
            list.add(I18n.format("info.Sanguimancy.tooltip.shift.info"));
        else {
            list.add(I18n.format("info.Sanguimancy.tooltip.place.altar"));
        }
    }
}
