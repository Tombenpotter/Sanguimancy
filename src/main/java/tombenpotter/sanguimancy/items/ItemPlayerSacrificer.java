package tombenpotter.sanguimancy.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tombenpotter.sanguimancy.Sanguimancy;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemPlayerSacrificer extends Item {

    public ItemPlayerSacrificer() {
        setCreativeTab(Sanguimancy.creativeTab);
        setUnlocalizedName(Sanguimancy.modid + ".playerSacrificer");
        setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        String name = "";
        switch (itemstack.getItemDamage()) {
            default: {
                name = "nothing";
                break;
            }
            case 0: {
                name = "unattuned";
                break;
            }
            case 1: {
                name = "attuned";
                break;
            }
            case 2: {
                name = "focused";
                break;
            }
            case 3: {
                name = "wayToDie";
                break;
            }
        }
        return getUnlocalizedName() + "." + name;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {
        for (int i = 0; i < 3; i++) {
            items.add(new ItemStack(this, 1, i));
        }
        ItemStack wayToDie = new ItemStack(this, 1, 3);
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("bloodStolen", 20000);
        tag.setString("ownerName", "WayofFlowingTime");
        tag.setString("thiefName", "Tombenpotter");
        wayToDie.setTagCompound(tag);
        items.add(wayToDie);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        if (!(stack.getTagCompound() == null) && stack.getItemDamage() == 2 || stack.getItemDamage() == 3) {
            tooltip.add(I18n.format("info.Sanguimancy.tooltip.sacrifice.player.stolen") + ": " + stack.getTagCompound().getInteger("bloodStolen"));
            tooltip.add(I18n.format("info.Sanguimancy.tooltip.sacrifice.player.thief") + ": " + stack.getTagCompound().getString("thiefName"));
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
    	return (!(stack.getTagCompound() == null) && stack.getItemDamage() == 2 || stack.getItemDamage() == 3);
    }
}
