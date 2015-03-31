package tombenpotter.sanguimancy.blocks.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemBlockBloodCleanser extends ItemBlock {

    public ItemBlockBloodCleanser(Block block) {
        super(block);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_) {
        list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.evil.toaster"));
    }
}
