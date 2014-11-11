package tombenpotter.sanguimancy.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.container.ContainerLumpCleaner;
import tombenpotter.sanguimancy.tile.TileLumpCleaner;

import java.util.ArrayList;

public class GuiLumpCleaner extends GuiContainer {

    public static final ResourceLocation gui = new ResourceLocation(Sanguimancy.texturePath, "textures/gui/LumpCleaner.png");
    @SuppressWarnings("unused")
    public ContainerLumpCleaner container;
    public TileLumpCleaner te;
    String containerName = "Ore Lump Cleaner";

    public GuiLumpCleaner(EntityPlayer player, TileLumpCleaner tile) {
        super(new ContainerLumpCleaner(player, tile));
        this.container = (ContainerLumpCleaner) this.inventorySlots;
        this.te = tile;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        fontRendererObj.drawString(containerName, xSize / 2 - fontRendererObj.getStringWidth(containerName) / 2, 6, 4210752);
        fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        GL11.glColor4f(1F, 1F, 1F, 1F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(gui);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);

        int i1 = this.te.ticksLeft * 24 / this.te.maxTicks;
        this.drawTexturedModalRect(xStart + 81, yStart + 34, 176, 14, i1 + 1, 16);

        if (this.te.tank.getFluid() != null) {
            int bloodLevelHeight = this.te.tank.getFluid().amount * 50 / this.te.tank.getCapacity();
            int bloodLevel = yStart + 10 + 51 - bloodLevelHeight;
            if (bloodLevelHeight > 0)
                this.drawTexturedModalRect(xStart + 12, bloodLevel, 177, 32, 12, bloodLevelHeight + 1);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float mouseAngle) {
        super.drawScreen(mouseX, mouseY, mouseAngle);
        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        if ((xStart + 12 <= mouseX) && (xStart + 24 > mouseX) && (yStart + 10 <= mouseY) && (yStart + 60 > mouseY) && this.te.tank.getFluid() != null) {
            String bloodStatus = StatCollector.translateToLocal("info.Sanguimancy.tooltip.amount") + ": ";
            bloodStatus += this.te.tank.getFluid().amount + " / " + this.te.tank.getCapacity();
            bloodStatus += " mB";
            ArrayList<String> toolTipList = new ArrayList<String>();
            toolTipList.add(bloodStatus);
            drawHoveringText(toolTipList, mouseX, mouseY, fontRendererObj);
        }
    }
}
