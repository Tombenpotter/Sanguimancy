package tombenpotter.sanguimancy.client.render;

import tombenpotter.sanguimancy.client.model.ModelInterface;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class RenderItemInterface implements IItemRenderer{
    private ModelInterface model;

    public RenderItemInterface(ModelInterface model) {
        this.model = model;
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

        Minecraft.getMinecraft().renderEngine.bindTexture(RenderInterface.texture);

        model.render(null,0, 0, 0,0,0, 0.0625F);

        GL11.glPopMatrix();
    }
}