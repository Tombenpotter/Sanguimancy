package tombenpotter.sanguimancy.client.render;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderPlayerPointer extends RenderBiped<EntityLiving> {
	public RenderPlayerPointer(RenderManager renderManager) {
        super(renderManager, new ModelBiped(), 0.5F);
    }

    @Override
    public void doRender(EntityLiving entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GL11.glDisable(2929);
        GL11.glColor3f(255, 0, 0);
        ItemStack itemstack = entity.getHeldItemMainhand();
        this.func_82420_a(entity, itemstack);
        double d3 = y - (double) entity.yOffset;
        if (entity.isSneaking()) {
            d3 -= 0.125D;
        }
        super.doRender(entity, x, d3, z, entityYaw, partialTicks);
        this.field_82423_g.aimedBow = this.field_82425_h.aimedBow = this.modelBipedMain.aimedBow = false;
        this.field_82423_g.isSneak = this.field_82425_h.isSneak = this.modelBipedMain.isSneak = false;
        this.field_82423_g.heldItemRight = this.field_82425_h.heldItemRight = this.modelBipedMain.heldItemRight = 0;
        GL11.glEnable(2929);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityLiving entity) {
        return new ResourceLocation("textures/entity/steve.png");
    }
}