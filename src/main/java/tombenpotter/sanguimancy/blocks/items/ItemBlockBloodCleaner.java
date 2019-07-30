package tombenpotter.sanguimancy.blocks.items;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemBlockBloodCleaner extends ItemBlock {

    public ItemBlockBloodCleaner(Block block) {
        super(block);

        setRegistryName(block.getRegistryName());
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World world, List<String> tooltip, @Nonnull ITooltipFlag flag) {
        tooltip.add(I18n.format("info.Sanguimancy.tooltip.evil.toaster"));
    }
}
