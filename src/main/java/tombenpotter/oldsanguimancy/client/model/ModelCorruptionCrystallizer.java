package tombenpotter.oldsanguimancy.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCorruptionCrystallizer extends ModelBase {
    //fields
    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape3;
    ModelRenderer Shape4;
    ModelRenderer Shape5;
    ModelRenderer Shape6;
    ModelRenderer Shape7;
    ModelRenderer Shape8;
    ModelRenderer Shape9;
    ModelRenderer Shape10;
    ModelRenderer Shape11;

    public ModelCorruptionCrystallizer() {
        textureWidth = 64;
        textureHeight = 64;

        Shape1 = new ModelRenderer(this, 60, 0);
        Shape1.addBox(0F, 0F, 0F, 1, 1, 1);
        Shape1.setRotationPoint(0F, 23F, 0F);
        Shape1.setTextureSize(64, 64);
        Shape1.mirror = true;
        setRotation(Shape1, 0F, 0F, 0F);
        Shape2 = new ModelRenderer(this, 52, 4);
        Shape2.addBox(0F, 0F, 0F, 3, 1, 3);
        Shape2.setRotationPoint(-1F, 22F, -1F);
        Shape2.setTextureSize(64, 64);
        Shape2.mirror = true;
        setRotation(Shape2, 0F, 0F, 0F);
        Shape3 = new ModelRenderer(this, 44, 8);
        Shape3.addBox(0F, 0F, 0F, 5, 1, 5);
        Shape3.setRotationPoint(-2F, 21F, -2F);
        Shape3.setTextureSize(64, 64);
        Shape3.mirror = true;
        setRotation(Shape3, 0F, 0F, 0F);
        Shape4 = new ModelRenderer(this, 36, 14);
        Shape4.addBox(0F, 0F, 0F, 7, 1, 7);
        Shape4.setRotationPoint(-3F, 20F, -3F);
        Shape4.setTextureSize(64, 64);
        Shape4.mirror = true;
        setRotation(Shape4, 0F, 0F, 0F);
        Shape5 = new ModelRenderer(this, 28, 22);
        Shape5.addBox(0F, 0F, 0F, 9, 1, 9);
        Shape5.setRotationPoint(-4F, 19F, -4F);
        Shape5.setTextureSize(64, 64);
        Shape5.mirror = true;
        setRotation(Shape5, 0F, 0F, 0F);
        Shape6 = new ModelRenderer(this, 0, 42);
        Shape6.addBox(0F, 0F, 0F, 11, 11, 11);
        Shape6.setRotationPoint(-5F, 8F, -5F);
        Shape6.setTextureSize(64, 64);
        Shape6.mirror = true;
        setRotation(Shape6, 0F, 0F, 0F);
        Shape7 = new ModelRenderer(this, 0, 0);
        Shape7.addBox(0F, 0F, 0F, 1, 1, 1);
        Shape7.setRotationPoint(0F, 3F, 0F);
        Shape7.setTextureSize(64, 64);
        Shape7.mirror = true;
        setRotation(Shape7, 0F, 0F, 0F);
        Shape8 = new ModelRenderer(this, 0, 4);
        Shape8.addBox(0F, 0F, 0F, 3, 1, 3);
        Shape8.setRotationPoint(-1F, 4F, -1F);
        Shape8.setTextureSize(64, 64);
        Shape8.mirror = true;
        setRotation(Shape8, 0F, 0F, 0F);
        Shape9 = new ModelRenderer(this, 0, 8);
        Shape9.addBox(0F, 0F, 0F, 5, 1, 5);
        Shape9.setRotationPoint(-2F, 5F, -2F);
        Shape9.setTextureSize(64, 64);
        Shape9.mirror = true;
        setRotation(Shape9, 0F, 0F, 0F);
        Shape10 = new ModelRenderer(this, 0, 14);
        Shape10.addBox(0F, 0F, 0F, 7, 1, 7);
        Shape10.setRotationPoint(-3F, 6F, -3F);
        Shape10.setTextureSize(64, 64);
        Shape10.mirror = true;
        setRotation(Shape10, 0F, 0F, 0F);
        Shape11 = new ModelRenderer(this, 0, 32);
        Shape11.addBox(0F, 0F, 0F, 9, 1, 9);
        Shape11.setRotationPoint(-4F, 7F, -4F);
        Shape11.setTextureSize(64, 64);
        Shape11.mirror = true;
        setRotation(Shape11, 0F, 0F, 0F);
    }

    @Override
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
        Shape10.render(f5);
        Shape11.render(f5);
    }

    public void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }

    public void renderAll() {
        this.Shape1.render(0.625F);
        this.Shape2.render(0.625F);
        this.Shape3.render(0.625F);
        this.Shape4.render(0.625F);
        this.Shape5.render(0.625F);
        this.Shape6.render(0.625F);
        this.Shape7.render(0.625F);
        this.Shape8.render(0.625F);
        this.Shape9.render(0.625F);
        this.Shape10.render(0.625F);
        this.Shape11.render(0.625F);
    }
}
