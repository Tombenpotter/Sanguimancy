package tombenpotter.sanguimancy.api.guide;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import tombenpotter.sanguimancy.Sanguimancy;

public class TabWrapper {
    public ResourceLocation texture = new ResourceLocation(Sanguimancy.texturePath + ":textures/gui/guide/tab.png");
    public Tab tab;
    public int x;
    public int y;

    public TabWrapper(Tab tabObject, int tabX, int tabY) {
        this.tab = tabObject;
        this.x = tabX;
        this.y = tabY;
    }

    public void drawTab() {
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        GuideHelper.drawItemStack(tab.stack, x, y);
    }

    public boolean isMouseOnTab(int mouseX, int mouseY) {
        return GuideHelper.isMouseBetween(mouseX, mouseY, x, y, tab.width, tab.height);
    }
}