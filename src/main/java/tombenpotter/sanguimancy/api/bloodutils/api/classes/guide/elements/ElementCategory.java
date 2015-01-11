package tombenpotter.sanguimancy.api.bloodutils.api.classes.guide.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.api.bloodutils.api.classes.guide.GuiIndex;
import tombenpotter.sanguimancy.api.bloodutils.api.compact.Category;
import tombenpotter.sanguimancy.api.bloodutils.api.helpers.GuiHelper;
import tombenpotter.sanguimancy.api.bloodutils.api.interfaces.IEntryElement;

public class ElementCategory extends GuiScreen implements IEntryElement {
    public Category category;
    public EntityPlayer player;
    public int x;
    public int y;
    public int width;
    public int height;

    public ElementCategory(Category category, int x, int y, int width, int height, EntityPlayer player) {
        this.category = category;
        this.player = player;

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void drawElement() {
        IIcon icon = category.iconStack.getIconIndex();
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Sanguimancy.texturePath + ":textures/gui/guide/tab.png"));
        GuiHelper.drawIconWithoutColor(x - 1, y - 1, width + 2, height + 2, 0);
        GuiHelper.drawItemStack(category.iconStack, x + 3, y + 2);
    }

    @Override
    public boolean isMouseInElement(int mX, int mY) {
        return GuiHelper.isMouseBetween(mX, mY, x, y, width, height);
    }

    @Override
    public void onMouseEnter(int mX, int mY) {
        Minecraft.getMinecraft().fontRenderer.drawString(this.category.name, mX + 6, mY, 0);
    }

    @Override
    public void onMouseClick(int mX, int mY, int type) {
        Minecraft.getMinecraft().displayGuiScreen(new GuiIndex(this.category, this.player));
    }
}