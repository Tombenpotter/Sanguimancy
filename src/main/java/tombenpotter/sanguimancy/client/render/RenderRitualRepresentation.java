package tombenpotter.sanguimancy.client.render;

import WayofTime.alchemicalWizardry.ModItems;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.client.model.ModelCube;
import tombenpotter.sanguimancy.tile.TileRitualSNPart;
import tombenpotter.sanguimancy.util.SanguimancyItemStacks;

public class RenderRitualRepresentation extends TileEntitySpecialRenderer implements IItemRenderer {

    public ModelCube model = new ModelCube();
    public ResourceLocation texture = new ResourceLocation(Sanguimancy.texturePath + ":textures/blocks/RitualRepresentation.png");

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float scale) {
        renderModel((TileRitualSNPart) tileEntity, x, y, z);
        if (tileEntity instanceof TileRitualSNPart) {
            TileRitualSNPart tile = (TileRitualSNPart) tileEntity;
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            if (player.getHeldItem() != null && player.getHeldItem().isItemEqual(new ItemStack(ModItems.itemSeerSigil))) {
                renderNameTag(tile, x, y, z);
            }
        }
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        switch (type) {
            case ENTITY: { //item entity
                if (item.getItem() == SanguimancyItemStacks.ritualRepresentation.getItem())
                    render(0.5F, 15F, -0.5F, 0.1F);
                return;
            }
            case EQUIPPED: { //third person in hand
                if (item.getItem() == SanguimancyItemStacks.ritualRepresentation.getItem())
                    render(2F, 15F, 5F, 0.1F);
                return;
            }
            case EQUIPPED_FIRST_PERSON: { //first person in hand
                if (item.getItem() == SanguimancyItemStacks.ritualRepresentation.getItem())
                    render(1F, 19F, 7F, 0.1F);
                return;
            }
            case INVENTORY: { //the item in inventories
                if (item.getItem() == SanguimancyItemStacks.ritualRepresentation.getItem())
                    render(-0.01F, 10F, 0.0F, 0.1F);
                return;
            }
            default:
                return;
        }
    }

    private void render(float x, float y, float z, float size) {
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
        GL11.glPushMatrix(); // start
        GL11.glScalef(size, size, size);
        GL11.glTranslatef(x, y, z); // size
        GL11.glRotatef(180, 1, 0, 0);
        GL11.glRotatef(-90, 0, 1, 0);
        model.renderAll();
        GL11.glPopMatrix(); // end
    }

    public void renderModel(TileRitualSNPart tile, double x, double y, double z) {
        float scale = 0.1F;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5f, (float) y - 0.5F, (float) z + 0.5f);
        GL11.glScalef(scale, scale, scale);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
        model.renderAll();
        GL11.glPopMatrix();
    }

    public void renderNameTag(TileRitualSNPart tile, double x, double y, double z) {
        float f = 1.6F;
        float f1 = 0.016666668F * f;
        String s = "x: " + String.valueOf(tile.xRitual) + " y: " + String.valueOf(tile.yRitual) + " z: " + String.valueOf(tile.zRitual);
        RenderManager manager = RenderManager.instance;
        FontRenderer fontrenderer = manager.getFontRenderer();
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-manager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(manager.playerViewX, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(-f1, -f1, f1);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glTranslatef(0.0F, 0.25F / f1, 0.0F);
        GL11.glDepthMask(false);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        Tessellator tessellator = Tessellator.instance;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        tessellator.startDrawingQuads();
        int i = fontrenderer.getStringWidth(s) / 2;
        tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
        tessellator.addVertex((double) (-i - 1), -1.0D, 0.0D);
        tessellator.addVertex((double) (-i - 1), 8.0D, 0.0D);
        tessellator.addVertex((double) (i + 1), 8.0D, 0.0D);
        tessellator.addVertex((double) (i + 1), -1.0D, 0.0D);
        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDepthMask(true);
        fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, 0, 0xFFFFFF);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }
}
