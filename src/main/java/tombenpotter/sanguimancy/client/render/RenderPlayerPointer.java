package tombenpotter.sanguimancy.client.render;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderPlayerPointer extends RenderBiped {

    public RenderPlayerPointer() {
        super(new ModelBiped(), 0.5F, 1.0F);
    }

    @Override
    public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        GL11.glDisable(2929);
        GL11.glColor3f(255, 0, 0);
        ItemStack itemstack = p_76986_1_.getHeldItem();
        this.func_82420_a(p_76986_1_, itemstack);
        double d3 = p_76986_4_ - (double) p_76986_1_.yOffset;

        if (p_76986_1_.isSneaking()) {
            d3 -= 0.125D;
        }

        super.doRender(p_76986_1_, p_76986_2_, d3, p_76986_6_, p_76986_8_, p_76986_9_);
        this.field_82423_g.aimedBow = this.field_82425_h.aimedBow = this.modelBipedMain.aimedBow = false;
        this.field_82423_g.isSneak = this.field_82425_h.isSneak = this.modelBipedMain.isSneak = false;
        this.field_82423_g.heldItemRight = this.field_82425_h.heldItemRight = this.modelBipedMain.heldItemRight = 0;
        GL11.glEnable(2929);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityLiving p_110775_1_) {
        return new ResourceLocation("textures/entity/steve.png");
    }
}
