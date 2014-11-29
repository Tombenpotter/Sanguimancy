package tombenpotter.sanguimancy.client.render;

import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.client.model.ModelInterface;
import tombenpotter.sanguimancy.tile.TileBloodInterface;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderInterface extends TileEntitySpecialRenderer
{
    private ModelInterface hollowCube = new ModelInterface();
    private final RenderItem customRenderItem;
    public static final ResourceLocation texture =  new ResourceLocation(Sanguimancy.modid, "textures/blocks/BloodInterface.png");

    public RenderInterface()
    {
        customRenderItem = new RenderItem()
        {
            @Override
            public boolean shouldBob()
            {
                return false;
            }
        };
        customRenderItem.setRenderManager(RenderManager.instance);
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double d0, double d1, double d2, float f)
    {
        TileBloodInterface tileAltar = tileEntity == null? null:(TileBloodInterface) tileEntity;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) d0 + 0.5F, (float) d1 + 1F, (float) d2 + 0.5F);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
        GL11.glPushMatrix();
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        this.hollowCube.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
        GL11.glPushMatrix();

        if (tileAltar!=null && tileAltar.getStackInSlot(0) != null)
        {
            float scaleFactor = getGhostItemScaleFactor(tileAltar.getStackInSlot(0));
            float rotationAngle = (float) (720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);
            EntityItem ghostEntityItem = new EntityItem(tileAltar.getWorldObj());
            ghostEntityItem.hoverStart = 0.0F;
            ghostEntityItem.setEntityItemStack(tileAltar.getStackInSlot(0));
            float displacement = 0.2F;
            GL11.glTranslatef((float) d0 + 0.5F, (float) d1 + displacement + 0.2F, (float) d2 + 0.5F);
            GL11.glScalef(scaleFactor, scaleFactor, scaleFactor);
            GL11.glRotatef(rotationAngle, 0.0F, 1.0F, 0.0F);
            customRenderItem.doRender(ghostEntityItem, 0, 0, 0, 0, 0);
        }

        GL11.glPopMatrix();


        GL11.glPushMatrix();
        GL11.glTranslatef((float) d0 + 0.5F, (float) d1 + 1.5F, (float) d2 + 0.5F);

        FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
        GL11.glPushMatrix();
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    private float getGhostItemScaleFactor(ItemStack itemStack)
    {
        float scaleFactor = 0.8F;

        return scaleFactor;
    }
}
