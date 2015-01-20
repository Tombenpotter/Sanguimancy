package tombenpotter.sanguimancy.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import tombenpotter.sanguimancy.Sanguimancy;

import java.util.ArrayList;
import java.util.List;

public class ItemResource extends Item {

    ArrayList<String> namesList = new ArrayList<String>();
    public IIcon[] icon = new IIcon[50];

    public ItemResource() {
        setCreativeTab(Sanguimancy.tabSanguimancy);
        setUnlocalizedName(Sanguimancy.modid + ".resource");
        setHasSubtypes(true);

        namesList.add(0, "corruptedDemonShard");
        namesList.add(1, "corruptedMineral");
        namesList.add(2, "imbuedStick");
        namesList.add(3, "etherealManifestation");
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ri) {
        this.icon[0] = ri.registerIcon(Sanguimancy.texturePath + ":CorruptedDemonShard");
        this.icon[1] = ri.registerIcon(Sanguimancy.texturePath + ":CorruptedMineral");
        this.icon[2] = ri.registerIcon(Sanguimancy.texturePath + ":ImbuedStick");
        this.icon[3] = ri.registerIcon(Sanguimancy.texturePath + ":etherealManifestation");
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        String name = namesList.get(stack.getItemDamage());
        return getUnlocalizedName() + "." + name;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        return this.icon[meta];
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List list) {
        for (int i = 0; i < namesList.size(); i++) {
            list.add(new ItemStack(this, 1, i));
        }
    }
}
