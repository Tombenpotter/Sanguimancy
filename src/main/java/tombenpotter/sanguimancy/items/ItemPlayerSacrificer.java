package tombenpotter.sanguimancy.items;

import WayofTime.alchemicalWizardry.api.items.interfaces.IBindable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import tombenpotter.sanguimancy.Sanguimancy;

import java.util.List;

public class ItemPlayerSacrificer extends Item implements IBindable {

    public IIcon[] icon = new IIcon[4];

    public ItemPlayerSacrificer() {
        setCreativeTab(Sanguimancy.tabSanguimancy);
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
    public void registerIcons(IIconRegister ri) {
        this.icon[0] = ri.registerIcon(Sanguimancy.texturePath + ":UnattunedPlayerSacrificer");
        this.icon[1] = ri.registerIcon(Sanguimancy.texturePath + ":AttunedPlayerSacrificer");
        this.icon[2] = ri.registerIcon(Sanguimancy.texturePath + ":FocusedPlayerSacrificer");
        this.icon[3] = ri.registerIcon(Sanguimancy.texturePath + ":FocusedPlayerSacrificer");
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        return this.icon[meta];
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List list) {
        for (int i = 0; i < 3; i++) {
            list.add(new ItemStack(this, 1, i));
        }
        ItemStack wayToDie = new ItemStack(this, 1, 3);
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("bloodStolen", 20000);
        tag.setString("ownerName", "WayofFlowingTime");
        tag.setString("thiefName", "Tombenpotter");
        wayToDie.setTagCompound(tag);
        list.add(wayToDie);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        if (!(stack.stackTagCompound == null) && stack.getItemDamage() == 2 || stack.getItemDamage() == 3) {
            list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.sacrifice.player.stolen") + ": " + stack.stackTagCompound.getInteger("bloodStolen"));
            list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.sacrifice.player.thief") + ": " + stack.stackTagCompound.getString("thiefName"));
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        if (!(stack.stackTagCompound == null) && stack.getItemDamage() == 2 || stack.getItemDamage() == 3) {
            return true;
        } else {
            return false;
        }
    }
}
