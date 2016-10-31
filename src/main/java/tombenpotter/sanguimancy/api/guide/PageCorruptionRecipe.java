package tombenpotter.sanguimancy.api.guide;

import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.gui.GuiBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tombenpotter.sanguimancy.recipes.RecipeCorruptedInfusion;

public class PageCorruptionRecipe extends PageBase {

    public ItemStack[] input;
    public ItemStack output;
    public int minimumCorruption;
    public int duration;

    public PageCorruptionRecipe(RecipeCorruptedInfusion recipe) {
        this.input = recipe.fInput;
        this.output = recipe.fOutput;
        this.minimumCorruption = recipe.fMiniumCorruption;
        this.duration = recipe.fTime;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer) {

        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Sanguimancy.texturePath + ":textures/gui/guide/altar.png"));
        guiBase.drawTexturedModalRect(guiLeft + 42, guiTop + 53, 0, 0, 150, 60);

        guiBase.drawCenteredString(fontRenderer, I18n.format("text.recipe.corrupted"), guiLeft + guiBase.xSize / 2, guiTop + 12, 0);

        int inputX = (1 + 1) * 20 + (guiLeft + guiBase.xSize / 7);
        int inputY = (1 * 20) + (guiTop + guiBase.ySize / 5);
        for (int i = 0; i < input.length; i++) {
            if (input[i] != null) {
                GuiHelper.drawItemStack(input[i], inputX, inputY + 7 * i);
                if (GuiHelper.isMouseBetween(mouseX, mouseY, inputX, inputY + 7 * i, 15, 15)) {
                    guiBase.renderToolTip(input[i], mouseX, mouseY);
                }
            }
        }

        if (output == null) {
            output = new ItemStack(Blocks.FIRE);
        }
        int outputX = (5 * 20) + (guiLeft + guiBase.xSize / 7);
        int outputY = (1 * 20) + (guiTop + guiBase.xSize / 5);
        GuiHelper.drawItemStack(output, outputX, outputY);
        if (GuiHelper.isMouseBetween(mouseX, mouseY, outputX, outputY, 15, 15)) {
            guiBase.renderToolTip(output, outputX, outputY);
        }

        if (output.getItem() == Item.getItemFromBlock(Blocks.fire)) {
            guiBase.drawCenteredString(fontRenderer, StatCollector.translateToLocal("text.furnace.error"), guiLeft + guiBase.xSize / 2, guiTop + 4 * guiBase.ySize / 6, 0xED073D);
            guiBase.drawCenteredString(fontRenderer, String.valueOf(minimumCorruption), guiLeft + guiBase.xSize / 2, guiTop + 4 * guiBase.ySize / 6 + 15, 0);
            guiBase.drawCenteredString(fontRenderer, String.valueOf(duration), guiLeft + guiBase.xSize / 2, guiTop + 4 * guiBase.ySize / 6 + 30, 0);
        }
        guiBase.drawCenteredString(fontRenderer, String.format(StatCollector.translateToLocal("text.recipe.corruption.minCorruption"), String.valueOf(minimumCorruption)), guiLeft + guiBase.xSize / 2, guiTop + 4 * guiBase.ySize / 6, 0);
        guiBase.drawCenteredString(fontRenderer, String.format(StatCollector.translateToLocal("text.recipe.corruption.duration"), String.valueOf(duration)), guiLeft + guiBase.xSize / 2, guiTop + 4 * guiBase.ySize / 6 + 15, 0);
    }
}
