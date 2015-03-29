package tombenpotter.sanguimancy.api.guide;

import WayofTime.alchemicalWizardry.api.items.ShapedBloodOrbRecipe;
import amerifrance.guideapi.ModInformation;
import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.pages.PageIRecipe;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import tombenpotter.sanguimancy.util.RandomUtils;

import java.util.ArrayList;

public class PageOrbRecipe extends PageIRecipe {

    /**
     * @param recipe - Recipe to draw
     */
    public PageOrbRecipe(IRecipe recipe) {
        super(recipe);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer) {

        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(ModInformation.GUITEXLOC + "recipe_elements.png"));
        guiBase.drawTexturedModalRect(guiLeft + 42, guiTop + 53, 0, 0, 105, 65);

        guiBase.drawCenteredString(fontRenderer, StatCollector.translateToLocal("text.recipe.shapedOrb"), guiLeft + guiBase.xSize / 2, guiTop + 12, 0);
        ShapedBloodOrbRecipe shapedBloodOrbRecipe = (ShapedBloodOrbRecipe) recipe;
        int width = ReflectionHelper.getPrivateValue(ShapedBloodOrbRecipe.class, shapedBloodOrbRecipe, 4);
        int height = ReflectionHelper.getPrivateValue(ShapedBloodOrbRecipe.class, shapedBloodOrbRecipe, 5);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int stackX = (x + 1) * 20 + (guiLeft + guiBase.xSize / 7);
                int stackY = (y + 1) * 20 + (guiTop + guiBase.ySize / 5);
                Object component = shapedBloodOrbRecipe.getInput()[y * width + x];
                if (component != null) {
                    if (component instanceof ItemStack) {
                        GuiHelper.drawItemStack((ItemStack) component, stackX, stackY);
                        if (GuiHelper.isMouseBetween(mouseX, mouseY, stackX, stackY, 15, 15)) {
                            guiBase.renderToolTip((ItemStack) component, stackX, stackY);
                        }
                    } else if (component instanceof Integer) {
                        GuiHelper.drawItemStack(RandomUtils.getOrbForLevel((Integer) component), stackX, stackY);
                        if (GuiHelper.isMouseBetween(mouseX, mouseY, stackX, stackY, 15, 15)) {
                            guiBase.renderToolTip(RandomUtils.getOrbForLevel((Integer) component), stackX, stackY);
                        }
                    } else {
                        if (((ArrayList<ItemStack>) component).isEmpty()) return;
                        GuiHelper.drawItemStack(((ArrayList<ItemStack>) component).get(0), stackX, stackY);
                        if (GuiHelper.isMouseBetween(mouseX, mouseY, stackX, stackY, 15, 15)) {
                            guiBase.renderToolTip(((ArrayList<ItemStack>) component).get(0), stackX, stackY);
                        }
                    }
                }
            }
        }
        int outputX = (5 * 20) + (guiLeft + guiBase.xSize / 7);
        int outputY = (2 * 20) + (guiTop + guiBase.xSize / 5);
        GuiHelper.drawItemStack(shapedBloodOrbRecipe.getRecipeOutput(), outputX, outputY);
        if (GuiHelper.isMouseBetween(mouseX, mouseY, outputX, outputY, 15, 15)) {
            guiBase.renderToolTip(shapedBloodOrbRecipe.getRecipeOutput(), outputX, outputY);
        }
    }
}
