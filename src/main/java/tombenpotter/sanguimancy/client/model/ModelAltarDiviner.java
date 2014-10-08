package tombenpotter.sanguimancy.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelAltarDiviner extends ModelBase {
    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape3;
    ModelRenderer Shape4;
    ModelRenderer Shape5;
    ModelRenderer Shape6;
    ModelRenderer Shape7;
    ModelRenderer Shape8;
    ModelRenderer Shape9;

    public ModelAltarDiviner() {
        textureWidth = 128;
        textureHeight = 64;

        Shape1 = new ModelRenderer(this, 111, 18);
        Shape1.addBox(-2F, -7F, -2F, 4, 7, 4);
        Shape1.setRotationPoint(0F, 20F, 0F);
        Shape1.setTextureSize(128, 64);
        Shape1.mirror = true;
        setRotation(Shape1, 0F, 0F, 0F);
        Shape2 = new ModelRenderer(this, 79, 30);
        Shape2.addBox(-6F, 0F, -6F, 12, 1, 12);
        Shape2.setRotationPoint(0F, 20F, 0F);
        Shape2.setTextureSize(128, 64);
        Shape2.mirror = true;
        setRotation(Shape2, 0F, -0.7807508F, 0F);
        Shape3 = new ModelRenderer(this, 87, 5);
        Shape3.addBox(-5F, 0F, -5F, 10, 1, 10);
        Shape3.setRotationPoint(0F, 12F, 0F);
        Shape3.setTextureSize(128, 64);
        Shape3.mirror = true;
        setRotation(Shape3, 0F, -0.8551081F, 0F);
        Shape4 = new ModelRenderer(this, 2, 48);
        Shape4.addBox(-7F, 0F, -7F, 14, 1, 14);
        Shape4.setRotationPoint(0F, 11F, 0F);
        Shape4.setTextureSize(128, 64);
        Shape4.mirror = true;
        setRotation(Shape4, 0F, 0F, 0F);
        Shape5 = new ModelRenderer(this, 1, 28);
        Shape5.addBox(0F, 0F, -8F, 2, 2, 16);
        Shape5.setRotationPoint(6F, 9F, 0F);
        Shape5.setTextureSize(128, 64);
        Shape5.mirror = true;
        setRotation(Shape5, 0F, 0F, 0F);
        Shape6 = new ModelRenderer(this, 1, 9);
        Shape6.addBox(-2F, 0F, -8F, 2, 2, 16);
        Shape6.setRotationPoint(-6F, 9F, 0F);
        Shape6.setTextureSize(128, 64);
        Shape6.mirror = true;
        setRotation(Shape6, 0F, 0F, 0F);
        Shape7 = new ModelRenderer(this, 0, 3);
        Shape7.addBox(-6F, 0F, 0F, 12, 2, 2);
        Shape7.setRotationPoint(0F, 9F, 6F);
        Shape7.setTextureSize(128, 64);
        Shape7.mirror = true;
        setRotation(Shape7, 0F, 0F, 0F);
        Shape8 = new ModelRenderer(this, 30, 3);
        Shape8.addBox(-6F, 0F, -2F, 12, 2, 2);
        Shape8.setRotationPoint(0F, 9F, -6F);
        Shape8.setTextureSize(128, 64);
        Shape8.mirror = true;
        setRotation(Shape8, 0F, 0F, 0F);
        Shape9 = new ModelRenderer(this, 63, 44);
        Shape9.addBox(-8F, 0F, -8F, 16, 3, 16);
        Shape9.setRotationPoint(0F, 21F, 0F);
        Shape9.setTextureSize(128, 64);
        Shape9.mirror = true;
        setRotation(Shape9, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        Shape1.render(f5);
        Shape2.render(f5);
        Shape3.render(f5);
        Shape4.render(f5);
        Shape5.render(f5);
        Shape6.render(f5);
        Shape7.render(f5);
        Shape8.render(f5);
        Shape9.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }

    public void renderAll() {
        Shape1.render(0.625F);
        Shape2.render(0.625F);
        Shape3.render(0.625F);
        Shape4.render(0.625F);
        Shape5.render(0.625F);
        Shape6.render(0.625F);
        Shape7.render(0.625F);
        Shape8.render(0.625F);
        Shape9.render(0.625F);
    }
}
