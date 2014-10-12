package tombenpotter.sanguimancy.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.client.model.ModelCorruptionCrystallizer;
import tombenpotter.sanguimancy.tile.TileCorruptionCrystallizer;

public class RenderCorruptionCrystallizer extends TileEntitySpecialRenderer {

    public ModelCorruptionCrystallizer model = new ModelCorruptionCrystallizer();
    public ResourceLocation texture = new ResourceLocation(Sanguimancy.texturePath + ":textures/blocks/CorruptionCrystallizer.png");

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float scale) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 2F, (float) z + 0.5F);
        GL11.glRotatef(180F, 90.0F, 0.0F, 90.0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        GL11.glPushMatrix();
        if (tileEntity instanceof TileCorruptionCrystallizer) {
            TileCorruptionCrystallizer corruptionCrystallizer = (TileCorruptionCrystallizer) tileEntity;
            if (corruptionCrystallizer.multiblockFormed) {
                GL11.glScalef(1F, 1.5F, 1F);
            } else {
                GL11.glScalef(1F, 1F, 1F);
            }
        }
        this.model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
}
