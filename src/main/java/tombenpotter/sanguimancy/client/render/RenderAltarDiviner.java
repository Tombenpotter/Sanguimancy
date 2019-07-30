package tombenpotter.sanguimancy.client.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;
import org.lwjgl.opengl.GL11;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.client.model.ModelAltarDiviner;
import tombenpotter.sanguimancy.tiles.TileAltarDiviner;

public class RenderAltarDiviner extends TileEntitySpecialRenderer<TileAltarDiviner> {
    public ModelAltarDiviner model = new ModelAltarDiviner();
    public ResourceLocation texture = new ResourceLocation(Sanguimancy.texturePath + ":textures/blocks/AltarDiviner.png");

    public RenderAltarDiviner() {
    	
    }

    @Override
    public void render(TileAltarDiviner tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        renderModel(tile, x, y, z);
        GL11.glPushMatrix();
        if (!tile.getInventory(null).getStackInSlot(0).isEmpty()) {
            float scaleFactor = 0.75F;
            float rotationAngle = (float) (720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);
            EntityItem ghostEntityItem = new EntityItem(tile.getWorld());
            ghostEntityItem.hoverStart = 0.0F;
            ghostEntityItem.setItem(tile.getInventory(null).getStackInSlot(0));
            if (ghostEntityItem.getItem().getItem() instanceof ItemBlock) {
                GL11.glTranslatef((float) x + 0.5F, (float) y + 1F, (float) z + 0.5F);
            } else {
                GL11.glTranslatef((float) x + 0.5F, (float) y + 1F, (float) z + 0.5F);
            }
            GL11.glScalef(scaleFactor, scaleFactor, scaleFactor);
            GL11.glRotatef(rotationAngle, 0.0F, 1.0F, 0.0F);
        }
        GL11.glPopMatrix();
    }

    public void renderModel(TileAltarDiviner tile, double x, double y, double z) {
        float scale = 1F;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glScalef(scale, scale, scale);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
        GL11.glRotatef(180F, 90.0F, 0.0F, 90.0F);
        model.render((Entity) null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }

//    private void render(float x, float y, float z, float size) {
//        FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
//        GL11.glPushMatrix(); // start
//        GL11.glScalef(size, size, size);
//        GL11.glTranslatef(x, y, z); // size
//        GL11.glRotatef(180, 1, 0, 0);
//        GL11.glRotatef(-90, 0, 1, 0);
//        model.renderAll();
//        GL11.glPopMatrix(); // end
//    }
}
