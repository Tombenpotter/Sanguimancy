package tombenpotter.sanguimancy.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import tombenpotter.sanguimancy.Sanguimancy;

public class ItemCorruptedDemonShard extends Item {

    public ItemCorruptedDemonShard() {
        setCreativeTab(Sanguimancy.tabSanguimancy);
        setUnlocalizedName(Sanguimancy.modid + ".corruptedDemonShard");
    }
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ri) {
        this.itemIcon = ri.registerIcon(Sanguimancy.texturePath + ":CorruptedDemonShard");
    }
}
