package tombenpotter.sanguimancy.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.client.model.ModelHollowCube;
import tombenpotter.sanguimancy.tiles.TileBloodInterface;

public class RenderBloodInterface extends TileEntitySpecialRenderer<TileBloodInterface> {

    public static final ResourceLocation texture = new ResourceLocation(Sanguimancy.texturePath + ":textures/blocks/BloodInterface.png");
    private final RenderItem customRenderItem;
    private ModelHollowCube model = new ModelHollowCube();

    public RenderBloodInterface() {
        customRenderItem = Minecraft.getMinecraft().getRenderItem();
    }

    @Override
    public void render(TileBloodInterface tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        renderModel(tile, x, y, z);
        GL11.glPushMatrix();
        ItemStack displayItem = tile.getInventory(null).getStackInSlot(0);
        if (!displayItem.isEmpty()) {
            float scaleFactor = 0.75F;
            float rotationAngle = (float) (720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);

            if (displayItem.getItem() instanceof ItemBlock) {
                GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
            } else {
                GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
            }
            GL11.glScalef(scaleFactor, scaleFactor, scaleFactor);
            GL11.glRotatef(rotationAngle, 0.0F, 1.0F, 0.0F);
            customRenderItem.renderItem(displayItem, ItemCameraTransforms.TransformType.GROUND);
        }
        GL11.glPopMatrix();
    }

    public void renderModel(TileBloodInterface tile, double x, double y, double z) {
        float scale = 0.1F;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.0F, (float) z + 0.5F);
        GL11.glScalef(scale, scale, scale);
        bindTexture(texture);
        GL11.glRotatef(180F, 90.0F, 0.0F, 90.0F);
        model.renderAll();
        GL11.glPopMatrix();
    }
}
