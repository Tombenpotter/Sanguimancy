package tombenpotter.sanguimancy.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.api.soulCorruption.SoulCorruptionHelper;

import java.util.List;

public class ItemSoulCorruptionTest extends Item {

    public IIcon[] icon = new IIcon[4];

    public ItemSoulCorruptionTest() {
        setCreativeTab(Sanguimancy.creativeTab);
        setUnlocalizedName(Sanguimancy.modid + ".soulCorruption");
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
                name = "add";
                break;
            }
            case 1: {
                name = "remove";
                break;
            }
            case 2: {
                name = "negate";
                break;
            }
            case 3: {
                name = "reader";
                break;
            }
        }
        return getUnlocalizedName() + "." + name;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ri) {
        this.icon[0] = ri.registerIcon(Sanguimancy.texturePath + ":AddSoulCorruption");
        this.icon[1] = ri.registerIcon(Sanguimancy.texturePath + ":RemoveSoulCorruption");
        this.icon[2] = ri.registerIcon(Sanguimancy.texturePath + ":NegateSoulCorruption");
        this.icon[3] = ri.registerIcon(Sanguimancy.texturePath + ":SoulCorruptionReader");
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        return this.icon[meta];
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List list) {
        for (int i = 0; i < icon.length; i++) {
            list.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn) {
        if (!world.isRemote) {
            if (stack.getItemDamage() == 0) {
                if (!player.isSneaking()) {
                    SoulCorruptionHelper.incrementCorruption(player);
                } else {
                    SoulCorruptionHelper.addCorruption(player, 100);
                }
            }
            if (stack.getItemDamage() == 1) {
                if (!player.isSneaking()) {
                    SoulCorruptionHelper.decrementCorruption(player);
                } else {
                    SoulCorruptionHelper.removeCorruption(player, 100);
                }
            }
            if (stack.getItemDamage() == 2) {
                SoulCorruptionHelper.negateCorruption(player);
            }
            if (stack.getItemDamage() == 3) {
                player.sendMessage(new ChatComponentText(I18n.format("chat.Sanguimancy.soul.corruption") + ": " + String.valueOf(SoulCorruptionHelper.getCorruptionLevel(player))));
            }
        }
        return stack;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean par4) {
        if (stack.getItemDamage() == 0 || stack.getItemDamage() == 1 || stack.getItemDamage() == 2) {
            list.add(I18n.format("info.Sanguimancy.tooltip.creative.only"));
        }
    }
}