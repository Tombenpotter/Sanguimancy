package tombenpotter.sanguimancy.api.guide;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class GuiTab extends GuiScreen {

    public EntityPlayer player;
    public Tab tab;

    public GuiTab(EntityPlayer entityPlayer, Tab tabObject) {
        this.player = entityPlayer;
        this.tab = tabObject;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
