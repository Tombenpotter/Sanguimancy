package tombenpotter.sanguimancy.api.guide;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import tombenpotter.sanguimancy.Sanguimancy;

import java.util.ArrayList;

public class GuiMain extends GuiScreen {

    public EntityPlayer player;
    //TODO: Change the texture
    public ResourceLocation texture = new ResourceLocation(Sanguimancy.texturePath + ":textures/gui/guide/front.png");
    public int guiWidth;
    public int guiHeight;
    public int middleX;
    public int middleY;
    public ArrayList<TabWrapper> tabWrapperList = new ArrayList<TabWrapper>();

    public GuiMain(EntityPlayer entityPlayer) {
        this.player = entityPlayer;
    }

    @Override
    public void initGui() {
        super.initGui();
        middleX = (this.width / 2) - (guiWidth / 2);
        middleY = (this.height / 2) - (guiHeight / 2);
        int tabY = middleY + 12;
        for (Tab tab : GuideEntriesRegistry.tabList) {
            tabWrapperList.add(new TabWrapper(tab, middleX - 1, tabY));
            tabY = tabY + 20;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        GL11.glColor4f(1F, 1F, 1F, 1F);
        this.mc.renderEngine.bindTexture(texture);
        drawTexturedModalRect(middleX, middleY, 0, 0, guiWidth, guiHeight);

        for (TabWrapper tabWrapper : tabWrapperList) {
            if (tabWrapper.tab.canPlayerSeeTab()) {
                tabWrapper.drawTab();
                if (tabWrapper.isMouseOnTab(mouseX, mouseY)) {
                    tabWrapper.tab.onMouseHover(mouseX, mouseY);
                    ArrayList list = new ArrayList();
                    list.add(tabWrapper.tab.name);
                    this.drawHoveringText(list, mouseX, mouseY, this.fontRendererObj);
                }
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int type) {
        super.mouseClicked(mouseX, mouseY, type);
        for (TabWrapper tabWrapper : tabWrapperList) {
            if (tabWrapper.tab.canPlayerSeeTab()) {
                if (tabWrapper.isMouseOnTab(mouseX, mouseY)) {
                    if (type == 0) tabWrapper.tab.onTabLeftCLick(mouseX, mouseY);
                    if (type == 1) tabWrapper.tab.onTabRightCLick(mouseX, mouseY);
                }
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
