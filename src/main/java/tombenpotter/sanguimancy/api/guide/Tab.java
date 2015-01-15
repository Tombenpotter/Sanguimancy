package tombenpotter.sanguimancy.api.guide;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import tombenpotter.sanguimancy.api.soulCorruption.SoulCorruptionHelper;

public class Tab {

    public ItemStack stack;
    public String name;
    public int minimumCorruption;
    public EntityPlayer player;

    public Tab(ItemStack itemStack, String tabName, int minimumCorruptionRequired, EntityPlayer entityPlayer) {
        this.stack = itemStack;
        this.name = tabName;
        this.minimumCorruption = minimumCorruptionRequired;
        this.player = entityPlayer;
    }

    public void onTabLeftCLick(int mouseX, int mouseY) {
    }

    public void onTabRightCLick(int mouseX, int mouseY) {
    }

    public void onMouseHover(int mouseX, int mouseY) {
    }

    public boolean canPlayerSeeTab() {
        return SoulCorruptionHelper.getClientPlayerCorruption() >= minimumCorruption;
    }
}
