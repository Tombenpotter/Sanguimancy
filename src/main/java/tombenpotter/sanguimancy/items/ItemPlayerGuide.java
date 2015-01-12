package tombenpotter.sanguimancy.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.util.RandomUtils;

import java.util.List;

public class ItemPlayerGuide extends Item {

    //All the GUI code comes from Wasliebob's API. I suck at GUIs.
    public ItemPlayerGuide() {
        setCreativeTab(Sanguimancy.tabSanguimancy);
        setUnlocalizedName(Sanguimancy.modid + ".playerGuide");
        setTextureName(Sanguimancy.texturePath + ":PlayerGuide");
        setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        player.openGui(Sanguimancy.instance, 1, world, (int) player.posX, (int) player.posY, (int) player.posZ);
        return stack;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5) {
        super.onUpdate(stack, world, entity, par4, par5);
        RandomUtils.checkAndSetCompound(stack);
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        super.onCreated(stack, world, player);
        RandomUtils.checkAndSetCompound(stack);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean simulate) {
        list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.book.author"));
    }
}
