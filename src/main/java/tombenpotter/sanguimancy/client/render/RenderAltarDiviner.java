package tombenpotter.sanguimancy.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.client.model.ModelAltarDiviner;
import tombenpotter.sanguimancy.tile.TileAltarDiviner;

public class RenderAltarDiviner extends TileEntitySpecialRenderer {
    public ModelAltarDiviner model = new ModelAltarDiviner();
    private final RenderItem customRenderItem;
    public ResourceLocation texture = new ResourceLocation(Sanguimancy.texturePath + ":textures/blocks/AltarDiviner.png");

    public RenderAltarDiviner() {
        customRenderItem = new RenderItem() {
            @Override
            public boolean shouldBob() {
                return false;
            }
        };
        customRenderItem.setRenderManager(RenderManager.instance);
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float scale) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        GL11.glPushMatrix();
        GL11.glRotatef(180F, 90.0F, 0.0F, 90.0F);
        this.model.render((Entity) null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();

        GL11.glPushMatrix();

        if (tileEntity instanceof TileAltarDiviner) {
            TileAltarDiviner altarDiviner = (TileAltarDiviner) tileEntity;
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glPushMatrix();

            if (altarDiviner.getStackInSlot(0) != null) {
                float scaleFactor = getGhostItemScaleFactor(altarDiviner.getStackInSlot(0));
                float rotationAngle = (float) (720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);
                EntityItem ghostEntityItem = new EntityItem(altarDiviner.getWorldObj());
                ghostEntityItem.hoverStart = 0.0F;
                ghostEntityItem.setEntityItemStack(altarDiviner.getStackInSlot(0));
                float displacement = 0.3F;

                if (ghostEntityItem.getEntityItem().getItem() instanceof ItemBlock) {
                    GL11.glTranslatef((float) x + 0.5F, (float) y + displacement + 0.7F, (float) z + 0.5F);
                } else {
                    GL11.glTranslatef((float) x + 0.5F, (float) y + displacement + 0.6F, (float) z + 0.5F);
                }
                GL11.glScalef(scaleFactor * 1.3F, scaleFactor * 1.3F, scaleFactor * 1.3F);
                GL11.glRotatef(rotationAngle, 0.0F, 1.0F, 0.0F);
                customRenderItem.doRender(ghostEntityItem, 0, 0, 0, 0, 0);
            }

            GL11.glPopMatrix();
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glEnable(GL11.GL_LIGHTING);
        }

        GL11.glPopMatrix();

        GL11.glPopMatrix();
    }

    private float getGhostItemScaleFactor(ItemStack itemStack) {
        float scaleFactor = 1.0F;

        if (itemStack != null) {
            if (itemStack.getItem() instanceof ItemBlock) {
                switch (customRenderItem.getMiniBlockCount(itemStack, (byte) 1)) {
                    case 1:
                        return 0.90F;

                    case 2:
                        return 0.90F;

                    case 3:
                        return 0.90F;

                    case 4:
                        return 0.90F;

                    case 5:
                        return 0.80F;

                    default:
                        return 0.90F;
                }
            } else {
                switch (customRenderItem.getMiniItemCount(itemStack, (byte) 1)) {
                    case 1:
                        return 0.65F;

                    case 2:
                        return 0.65F;

                    case 3:
                        return 0.65F;

                    case 4:
                        return 0.65F;

                    default:
                        return 0.65F;
                }
            }
        }

        return scaleFactor;
    }
}
