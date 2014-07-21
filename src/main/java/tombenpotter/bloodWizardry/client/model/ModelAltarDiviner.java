package tombenpotter.bloodWizardry.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelAltarDiviner extends ModelBase {
    ModelRenderer Leg1;
    ModelRenderer Leg2;
    ModelRenderer Leg3;
    ModelRenderer Leg4;
    ModelRenderer Plate;

    public ModelAltarDiviner() {
        textureWidth = 64;
        textureHeight = 64;

        Leg1 = new ModelRenderer(this, 0, 0);
        Leg1.addBox(0F, 0F, 0F, 1, 16, 1);
        Leg1.setRotationPoint(7F, 8F, 7F);
        Leg1.setTextureSize(64, 32);
        Leg1.mirror = true;
        setRotation(Leg1, 0F, 0F, 0F);
        Leg2 = new ModelRenderer(this, 4, 0);
        Leg2.addBox(0F, 0F, 0F, 1, 16, 1);
        Leg2.setRotationPoint(7F, 8F, -8F);
        Leg2.setTextureSize(64, 32);
        Leg2.mirror = true;
        setRotation(Leg2, 0F, 0F, 0F);
        Leg3 = new ModelRenderer(this, 8, 0);
        Leg3.addBox(0F, 0F, 0F, 1, 16, 1);
        Leg3.setRotationPoint(-8F, 8F, 7F);
        Leg3.setTextureSize(64, 32);
        Leg3.mirror = true;
        setRotation(Leg3, 0F, 0F, 0F);
        Leg4 = new ModelRenderer(this, 12, 0);
        Leg4.addBox(0F, 0F, 0F, 1, 16, 1);
        Leg4.setRotationPoint(-8F, 8F, -8F);
        Leg4.setTextureSize(64, 32);
        Leg4.mirror = true;
        setRotation(Leg4, 0F, 0F, 0F);
        Plate = new ModelRenderer(this, 0, 17);
        Plate.addBox(0F, 0F, 0F, 16, 1, 16);
        Plate.setRotationPoint(-8F, 12F, -8F);
        Plate.setTextureSize(64, 32);
        Plate.mirror = true;
        setRotation(Plate, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        Leg1.render(f5);
        Leg2.render(f5);
        Leg3.render(f5);
        Leg4.render(f5);
        Plate.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }

}
