package tombenpotter.sanguimancy.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelHollowCube extends ModelBase {

    //fields
    ModelRenderer topBarA;
    ModelRenderer topBarB;
    ModelRenderer topBarC;
    ModelRenderer topBarD;
    ModelRenderer supportA;
    ModelRenderer supportB;
    ModelRenderer supportC;
    ModelRenderer supportD;
    ModelRenderer bottomBarA;
    ModelRenderer bottomBarB;
    ModelRenderer bottomBarC;
    ModelRenderer bottomBarD;

    public ModelHollowCube() {
        textureWidth = 64;
        textureHeight = 32;

        topBarA = new ModelRenderer(this, 0, 0);
        topBarA.addBox(-5F, -7.99F, -7.99F, 10, 3, 3);
        topBarA.setRotationPoint(0F, 7.99F, 0F);
        topBarA.setTextureSize(64, 32);
        topBarA.mirror = true;
        setRotation(topBarA, 0F, 0F, 0F);
        topBarB = new ModelRenderer(this, 0, 5);
        topBarB.addBox(-5F, -7.99F, 5F, 10, 3, 3);
        topBarB.setRotationPoint(0F, 7.99F, 0F);
        topBarB.setTextureSize(64, 32);
        topBarB.mirror = true;
        setRotation(topBarB, 0F, 0F, 0F);
        topBarC = new ModelRenderer(this, 0, 5);
        topBarC.addBox(5F, -7.99F, -5F, 3, 3, 10);
        topBarC.setRotationPoint(0F, 7.99F, 0F);
        topBarC.setTextureSize(64, 32);
        topBarC.mirror = true;
        setRotation(topBarC, 0F, 0F, 0F);
        topBarD = new ModelRenderer(this, 0, 15);
        topBarD.addBox(-7.99F, -7.99F, -5F, 3, 3, 10);
        topBarD.setRotationPoint(0F, 7.99F, 0F);
        topBarD.setTextureSize(64, 32);
        topBarD.mirror = true;
        setRotation(topBarD, 0F, 0F, 0F);
        supportA = new ModelRenderer(this, 0, 19);
        supportA.addBox(5F, -7.99F, 5F, 3, 16, 3);
        supportA.setRotationPoint(0F, 7.99F, 0F);
        supportA.setTextureSize(64, 32);
        supportA.mirror = true;
        setRotation(supportA, 0F, 0F, 0F);
        supportB = new ModelRenderer(this, 0, 10);
        supportB.addBox(5F, -7.99F, -7.99F, 3, 16, 3);
        supportB.setRotationPoint(0F, 7.99F, 0F);
        supportB.setTextureSize(64, 32);
        supportB.mirror = true;
        setRotation(supportB, 0F, 0F, 0F);
        supportC = new ModelRenderer(this, 0, 19);
        supportC.addBox(-7.99F, -7.99F, -7.99F, 3, 16, 3);
        supportC.setRotationPoint(0F, 7.99F, 0F);
        supportC.setTextureSize(64, 32);
        supportC.mirror = true;
        setRotation(supportC, 0F, 0F, 0F);
        supportD = new ModelRenderer(this, 12, 0);
        supportD.addBox(-7.99F, -7.99F, 5F, 3, 16, 3);
        supportD.setRotationPoint(0F, 7.99F, 0F);
        supportD.setTextureSize(64, 32);
        supportD.mirror = true;
        setRotation(supportD, 0F, 0F, 0F);
        bottomBarA = new ModelRenderer(this, 20, 0);
        bottomBarA.addBox(-7.99F, 5F, -5F, 3, 3, 10);
        bottomBarA.setRotationPoint(0F, 7.99F, 0F);
        bottomBarA.setTextureSize(64, 32);
        bottomBarA.mirror = true;
        setRotation(bottomBarA, 0F, 0F, 0F);
        bottomBarB = new ModelRenderer(this, 18, 0);
        bottomBarB.addBox(5F, 5F, -5F, 3, 3, 10);
        bottomBarB.setRotationPoint(0F, 7.99F, 0F);
        bottomBarB.setTextureSize(64, 32);
        bottomBarB.mirror = true;
        setRotation(bottomBarB, 0F, 0F, 0F);
        bottomBarC = new ModelRenderer(this, 17, 0);
        bottomBarC.addBox(-5F, 5F, 5F, 10, 3, 3);
        bottomBarC.setRotationPoint(0F, 7.99F, 0F);
        bottomBarC.setTextureSize(64, 32);
        bottomBarC.mirror = true;
        setRotation(bottomBarC, 0F, 0F, 0F);
        bottomBarD = new ModelRenderer(this, 17, 0);
        bottomBarD.addBox(-5F, 5F, -7.99F, 10, 3, 3);
        bottomBarD.setRotationPoint(0F, 7.99F, 0F);
        bottomBarD.setTextureSize(64, 32);
        bottomBarD.mirror = true;
        setRotation(bottomBarD, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        topBarA.render(f5);
        topBarB.render(f5);
        topBarC.render(f5);
        topBarD.render(f5);
        supportA.render(f5);
        supportB.render(f5);
        supportC.render(f5);
        supportD.render(f5);
        bottomBarA.render(f5);
        bottomBarB.render(f5);
        bottomBarC.render(f5);
        bottomBarD.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void renderAll() {
        topBarA.render(0.625F);
        topBarB.render(0.625F);
        topBarC.render(0.625F);
        topBarD.render(0.625F);
        supportA.render(0.625F);
        supportB.render(0.625F);
        supportC.render(0.625F);
        supportD.render(0.625F);
        bottomBarA.render(0.625F);
        bottomBarB.render(0.625F);
        bottomBarC.render(0.625F);
        bottomBarD.render(0.625F);
    }
}
