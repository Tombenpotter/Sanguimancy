package tombenpotter.sanguimancy.compat.nei;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Mouse;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.recipes.RecipeBloodCleanser;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class NEIBloodCleanser extends TemplateRecipeHandler {

    public class CachedCleansingRecipe extends CachedRecipe {
        public ItemStack input;
        public ItemStack output;

        public CachedCleansingRecipe(RecipeBloodCleanser recipe) {
            this.input = recipe.fInput;
            this.output = recipe.fOutput;
        }

        @Override
        public List<PositionedStack> getIngredients() {
            ArrayList<PositionedStack> stacks = new ArrayList<PositionedStack>();
            stacks.add(new PositionedStack(input, 47, 5));
            return stacks;
        }

        @Override
        public PositionedStack getResult() {
            return new PositionedStack(output, 124, 23);
        }
    }

    @Override
    public String getOverlayIdentifier() {
        return "bloodcleansingrecipes";
    }

    @Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(77, 25, 24, 17), "bloodcleansingrecipes"));
    }

    @Override
    public String getGuiTexture() {
        return Sanguimancy.texturePath + ":textures/gui/LumpCleaner.png";
    }

    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal("compat.nei.blood.cleanser");
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals("item"))
            loadCraftingRecipes((ItemStack) results[0]);
        else if (outputId.equals("bloodcleansingrecipes")) {
            for (RecipeBloodCleanser r : RecipeBloodCleanser.getAllRecipes()) {
                arecipes.add(new CachedCleansingRecipe(r));
            }
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        for (RecipeBloodCleanser r : RecipeBloodCleanser.getAllRecipes()) {
            if (r.fOutput.isItemEqual(result))
                arecipes.add(new CachedCleansingRecipe(r));
        }
    }

    @Override
    public void loadUsageRecipes(String inputId, Object... ingredients) {
        if (ingredients.length == 0)
            return;
        if ("item".equals(inputId)) {
            for (RecipeBloodCleanser r : RecipeBloodCleanser.getRecipes((ItemStack) ingredients[0]))
                arecipes.add(new CachedCleansingRecipe(r));
        }
    }

    public void drawTexturedModalRect(int par1, int par2, int par3, int par4, int par5, int par6, int w, int h) {
        float f = (float) 1 / w;
        float f1 = (float) 1 / h;
        Tessellator tess = Tessellator.instance;
        tess.startDrawingQuads();
        tess.addVertexWithUV((double) (par1 + 0), (double) (par2 + par6), 0.0F, (double) ((float) (par3 + 0) * f), (double) ((float) (par4 + par6) * f1));
        tess.addVertexWithUV((double) (par1 + par5), (double) (par2 + par6), 0.0F, (double) ((float) (par3 + par5) * f), (double) ((float) (par4 + par6) * f1));
        tess.addVertexWithUV((double) (par1 + par5), (double) (par2 + 0), 0.0F, (double) ((float) (par3 + par5) * f), (double) ((float) (par4 + 0) * f1));
        tess.addVertexWithUV((double) (par1 + 0), (double) (par2 + 0), 0.0F, (double) ((float) (par3 + 0) * f), (double) ((float) (par4 + 0) * f1));
        tess.draw();
    }

    //Taken from the Blood Magic Repo, written by Joshie
    public Point getMouse(int width, int height) {
        Point mousepos = this.getMousePosition();
        int guiLeft = (width - 176) / 2;
        int guiTop = (height - 166) / 2;
        Point relMouse = new Point(mousepos.x - guiLeft, mousepos.y - guiTop);
        return relMouse;
    }

    //width helper, getting width normal way hates me on compile
    public int getGuiWidth(GuiRecipe gui) {
        try {
            Field f = gui.getClass().getField("width");
            return (Integer) f.get(gui);
        } catch (NoSuchFieldException e) {
            try {
                Field f = gui.getClass().getField("field_146294_l");
                return (Integer) f.get(gui);
            } catch (Exception e2) {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    //height helper, getting height normal way hates me on compile
    public int getGuiHeight(GuiRecipe gui) {
        try {
            Field f = gui.getClass().getField("height");
            return (Integer) f.get(gui);
        } catch (NoSuchFieldException e) {
            try {
                Field f = gui.getClass().getField("field_146295_m");
                return (Integer) f.get(gui);
            } catch (Exception e2) {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static Point getMousePosition() {
        Dimension size = displaySize();
        Dimension res = displayRes();
        return new Point(Mouse.getX() * size.width / res.width, size.height - Mouse.getY() * size.height / res.height - 1);
    }

    public static Dimension displaySize() {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        return new Dimension(res.getScaledWidth(), res.getScaledHeight());
    }

    public static Dimension displayRes() {
        Minecraft mc = Minecraft.getMinecraft();
        return new Dimension(mc.displayWidth, mc.displayHeight);
    }
}
