package tombenpotter.sanguimancy.items;


import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tombenpotter.sanguimancy.Sanguimancy;

import java.util.ArrayList;
import java.util.List;

public class ItemResource extends Item {

    ArrayList<String> namesList = new ArrayList<String>();

    public ItemResource() {
        setCreativeTab(Sanguimancy.creativeTab);
        setUnlocalizedName(Sanguimancy.modid + ".resource");
        setHasSubtypes(true);

        namesList.add(0, "corruptedDemonShard");
        namesList.add(1, "corruptedMineral");
        namesList.add(2, "imbuedStick");
        namesList.add(3, "etherealManifestation");
        namesList.add(4, "manipulatorUpgrade");
    }


    @Override
    public String getUnlocalizedName(ItemStack stack) {
        String name = namesList.get(stack.getItemDamage());
        return getUnlocalizedName() + "." + name;
    }
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List list) {
        for (int i = 0; i < namesList.size(); i++) {
            list.add(new ItemStack(this, 1, i));
        }
    }
}
