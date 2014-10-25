package tombenpotter.sanguimancy.compat;

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
import tombenpotter.sanguimancy.recipes.CorruptedInfusionRecipe;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class NEICorruptedInfusion extends TemplateRecipeHandler {

    public class CachedCorruptionRecipe extends CachedRecipe {
        public ItemStack[] input;
        public ItemStack output;
        public int time;
        public int miniumCorruption;
        public boolean exactAmountandNbt;

        public CachedCorruptionRecipe(CorruptedInfusionRecipe recipe) {
            this.input = recipe.fInput;
            this.output = recipe.fOutput;
            this.time = recipe.fTime;
            this.miniumCorruption = recipe.fMiniumCorruption;
            this.exactAmountandNbt = recipe.fExactAmountandNbt;
        }

        @Override
        public List<PositionedStack> getIngredients() {
            ArrayList<PositionedStack> stacks = new ArrayList<PositionedStack>();
            stacks.add(new PositionedStack(input, 20, 10));
            return stacks;
        }

        @Override
        public PositionedStack getResult() {
            return new PositionedStack(output, 124, 10);
        }
    }

    @Override
    public String getOverlayIdentifier() {
        return "corruptedinfusionrecipes";
    }

    @Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(65, 10, 22, 16), "corruptedinfusionrecipes"));
    }

    @Override
    public String getGuiTexture() {
        return Sanguimancy.texturePath + ":textures/gui/nei/corrupted_infusion.png";
    }

    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal("compat.nei.corrupted.infusion");
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals("item"))
            loadCraftingRecipes((ItemStack) results[0]);
        else if (outputId.equals("corruptedinfusionrecipes")) {
            for (CorruptedInfusionRecipe r : CorruptedInfusionRecipe.getAllRecipes()) {
                arecipes.add(new CachedCorruptionRecipe(r));
            }
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        for (CorruptedInfusionRecipe r : CorruptedInfusionRecipe.getAllRecipes()) {
            if (r.fOutput.isItemEqual(result))
                arecipes.add(new CachedCorruptionRecipe(r));
        }
    }

    @Override
    public void loadUsageRecipes(String inputId, Object... ingredients) {
        if (ingredients.length == 0)
            return;
        if ("item".equals(inputId)) {
            for (CorruptedInfusionRecipe r : CorruptedInfusionRecipe.getPossibleRecipes((ItemStack[]) ingredients, 500))
                arecipes.add(new CachedCorruptionRecipe(r));
        }
    }

    @Override
    public void drawExtras(int id) {
        CachedCorruptionRecipe recipe = (CachedCorruptionRecipe) arecipes.get(id);
        Minecraft.getMinecraft().fontRenderer.drawString(StatCollector.translateToLocal("compat.nei.corrupted.infusion.time") + ": " + recipe.time, 30, 35, 0x4F4C49);
        Minecraft.getMinecraft().fontRenderer.drawString(StatCollector.translateToLocal("compat.nei.corrupted.infusion.minimum.corruption") + ": " + recipe.miniumCorruption, 30, 45, 0x4F4C49);
        Minecraft.getMinecraft().fontRenderer.drawString(StatCollector.translateToLocal("compat.nei.corrupted.infusion.minimum.exact") + ": " + recipe.exactAmountandNbt, 30, 55, 0x4F4C49);
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
