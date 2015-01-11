package tombenpotter.sanguimancy.api.bloodutils.api.entries;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.api.bloodutils.api.classes.guide.GuiEntry;
import tombenpotter.sanguimancy.recipes.RecipeCorruptedInfusion;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EntryCorruptionRecipe extends EntryBase {

    public RecipeCorruptedInfusion recipe;
    public ItemStack input[], output;
    public int time, minimumCorruption;
    public boolean exactAmountAndNbt;
    public ArrayList<ItemIcon> icons = new ArrayList<ItemIcon>();

    public EntryCorruptionRecipe(RecipeCorruptedInfusion recipe) {
        this.recipe = recipe;
        populate(recipe);
    }

    public void populate(RecipeCorruptedInfusion recipe) {
        this.input = recipe.fInput;
        this.output = recipe.fOutput;
        this.time = recipe.fTime;
        this.minimumCorruption = recipe.fMiniumCorruption;
        this.exactAmountAndNbt = recipe.fExactAmountandNbt;
    }

    @Override
    public void draw(GuiEntry entry, int width, int height, int left, int top, EntityPlayer player, String key, int page, int mX, int mY) {
        Minecraft.getMinecraft().fontRenderer.drawSplitString(StatCollector.translateToLocal("compat.nei.corrupted.infusion.time") + ": " + String.valueOf(time), left + width / 2 - 58, top + 15, 110, 0);
        Minecraft.getMinecraft().fontRenderer.drawSplitString(StatCollector.translateToLocal("compat.nei.corrupted.infusion.minimum.corruption") + ": " + String.valueOf(minimumCorruption), left + width / 2 - 58, top + 35, 110, 0);
        Minecraft.getMinecraft().fontRenderer.drawSplitString(StatCollector.translateToLocal("compat.nei.corrupted.infusion.minimum.exact") + ": " + String.valueOf(exactAmountAndNbt), left + width / 2 - 58, top + 25, 110, 0);


        int x, y;

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1F, 1F, 1F, 1F);
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        renderOverlay(entry, width, height, left, top);

        x = left + width / 2 - (65 - 45);
        y = (height / 2 - 36) + (18 * (4 - 3)) - 10;
        for (int i = 0; i < input.length; i++) {
            y += 10;
            drawIcon(input[i], x, y);
        }

        /** Result */
        x = left + width / 2 - (65 - (48 + 48) - 5);
        y = (height / 2 - 36) + (18 * (4 - 3));
        drawIcon(this.output, x, y);

        RenderHelper.disableStandardItemLighting();

        GL11.glDisable(GL11.GL_LIGHTING);

        GL11.glPopMatrix();

        for (ItemIcon icon : icons) {
            if (icon.stack != null)
                icon.onMouseBetween(mX, mY);
        }
    }

    public void drawIcon(ItemStack stack, int x, int y) {
        RenderItem ri = new RenderItem();
        ri.renderItemAndEffectIntoGUI(Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().getTextureManager(), stack, x, y);
        ri.renderItemOverlayIntoGUI(Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().getTextureManager(), stack, x, y);

        icons.add(new ItemIcon(stack, x, y));
    }

    public void renderOverlay(GuiEntry entry, int width, int height, int left, int top) {
        TextureManager tm = Minecraft.getMinecraft().getTextureManager();
        tm.bindTexture(new ResourceLocation(Sanguimancy.texturePath + ":textures/gui/corruptedInfusion.png"));
        entry.drawTexturedModalRect(left, (height / 2 - 36) + (18 * 0) - 17, 0, 0, width, height);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void initGui(int width, int height, int left, int top, EntityPlayer player, List buttonList) {
    }

    @Override
    public void actionPerformed(GuiButton button) {
    }

    public static class ItemIcon {
        public ItemStack stack;
        public int x, y;

        public ItemIcon(ItemStack stack, int x, int y) {
            this.stack = stack;
            this.x = x;
            this.y = y;
        }

        public void onMouseBetween(int mX, int mY) {
            int xSize = x + 16;
            int ySize = y + 16;


            if (mX > x && mX < xSize && mY > y && mY < ySize) {
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                if (stack != null && stack.getDisplayName() != null && !stack.getTooltip(Minecraft.getMinecraft().thePlayer, false).isEmpty()) {
                    Minecraft.getMinecraft().fontRenderer.drawString(stack.getDisplayName(), mX + 6, mY, new Color(100, 100, 100).getRGB());
                    int addY = 10;
                    for (int i = 1; i < stack.getTooltip(Minecraft.getMinecraft().thePlayer, false).size(); i++) {
                        Minecraft.getMinecraft().fontRenderer.drawString(String.valueOf(stack.getTooltip(Minecraft.getMinecraft().thePlayer, false).get(i)), mX + 6, mY + addY, new Color(139, 137, 137).getRGB());
                        addY = addY + 10;
                    }
                }
                GL11.glEnable(GL11.GL_DEPTH_TEST);
            }
        }
    }
}
