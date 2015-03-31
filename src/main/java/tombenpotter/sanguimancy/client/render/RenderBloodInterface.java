package tombenpotter.sanguimancy.client.render;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.client.model.ModelHollowCube;
import tombenpotter.sanguimancy.tile.TileBloodInterface;

public class RenderBloodInterface extends TileEntitySpecialRenderer implements IItemRenderer {

    public static final ResourceLocation texture = new ResourceLocation(Sanguimancy.texturePath + ":textures/blocks/BloodInterface.png");
    private final RenderItem customRenderItem;
    private ModelHollowCube model = new ModelHollowCube();

    public RenderBloodInterface() {
        customRenderItem = new RenderItem() {
            @Override
            public boolean shouldBob() {
                return false;
            }
        };
        customRenderItem.setRenderManager(RenderManager.instance);
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
        renderModel((TileBloodInterface) tileEntity, x, y, z);
        if (tileEntity instanceof TileBloodInterface) {
            TileBloodInterface tile = (TileBloodInterface) tileEntity;
            GL11.glPushMatrix();
            if (tile.getStackInSlot(0) != null) {
                float scaleFactor = getGhostItemScaleFactor(tile.getStackInSlot(0));
                float rotationAngle = (float) (720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);
                EntityItem ghostEntityItem = new EntityItem(tile.getWorldObj());
                ghostEntityItem.hoverStart = 0.0F;
                ghostEntityItem.setEntityItemStack(tile.getStackInSlot(0));
                if (ghostEntityItem.getEntityItem().getItem() instanceof ItemBlock) {
                    GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
                } else {
                    GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
                }
                GL11.glScalef(scaleFactor, scaleFactor, scaleFactor);
                GL11.glRotatef(rotationAngle, 0.0F, 1.0F, 0.0F);
                customRenderItem.doRender(ghostEntityItem, 0, 0, 0, 0, 0);
            }
            GL11.glPopMatrix();
        }
    }

    public void renderModel(TileBloodInterface tile, double x, double y, double z) {
        float scale = 0.1F;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.0F, (float) z + 0.5F);
        GL11.glScalef(scale, scale, scale);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
        GL11.glRotatef(180F, 90.0F, 0.0F, 90.0F);
        model.renderAll();
        GL11.glPopMatrix();
    }

    private float getGhostItemScaleFactor(ItemStack itemStack) {
        float scaleFactor = 0.7F;
        return scaleFactor;
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
        GL11.glPushMatrix();
        GL11.glScalef(-1F, -1F, 1F);
        switch (type) {
            case ENTITY:
            case INVENTORY:
                GL11.glTranslatef(0, -0.5F, 0);
                break;
            case EQUIPPED:
                GL11.glTranslatef(-0.5F, -1F, 0.5F);
                break;
            case EQUIPPED_FIRST_PERSON:
                GL11.glTranslatef(-0.3F, -1.1F, 0.5F);
                break;
            default:
        }
        Minecraft.getMinecraft().renderEngine.bindTexture(RenderBloodInterface.texture);
        model.render(null, 0, 0, 0, 0, 0, 0.0625F);
        GL11.glPopMatrix();
    }
}
