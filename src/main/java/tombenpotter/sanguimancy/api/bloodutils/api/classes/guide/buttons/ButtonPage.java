package tombenpotter.sanguimancy.api.bloodutils.api.classes.guide.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import java.awt.*;

public class ButtonPage extends GuiButton {

    int gwidth = 170;
    int gheight = 180;

    public ButtonPage(int id, int xPos, int yPos, int width, int height, String string) {
        super(id, xPos, yPos, width, height, string);
    }

    @Override
    public void drawButton(Minecraft mc, int x, int y) {
        field_146123_n = x >= xPosition && y >= yPosition && x < xPosition + width && y < yPosition + height;
        int state = getHoverState(field_146123_n);
        x = this.xPosition + width / 2 - 30;
        y = this.yPosition + (height - 6) / 2;
        mc.fontRenderer.setUnicodeFlag(true);
        mc.fontRenderer.drawString(displayString, x + (state == 2 ? 5 : 0), y, calcColor(state));
        mc.fontRenderer.setUnicodeFlag(false);
    }

    public int calcColor(int state) {
        if (state == 2)
            return new Color(155, 155, 155).getRGB();
        else
            return new Color(55, 55, 55).getRGB();
    }
}