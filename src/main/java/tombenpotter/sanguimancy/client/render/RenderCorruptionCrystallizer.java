package tombenpotter.sanguimancy.client.render;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.client.model.ModelCorruptionCrystallizer;
import tombenpotter.sanguimancy.tile.TileCorruptionCrystallizer;
import tombenpotter.sanguimancy.util.SanguimancyItemStacks;

public class RenderCorruptionCrystallizer extends TileEntitySpecialRenderer implements IItemRenderer {

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
        switch (type) {
            case ENTITY: { //item entity
                if (item.getItem() == SanguimancyItemStacks.corruptionCrystallizer.getItem())
                    render(0.5F, 13F, -0.5F, 0.09F);
                return;
            }
            case EQUIPPED: { //third person in hand
                if (item.getItem() == SanguimancyItemStacks.corruptionCrystallizer.getItem())
                    render(2F, 15F, 5F, 0.10F);
                return;
            }
            case EQUIPPED_FIRST_PERSON: { //first person in hand
                if (item.getItem() == SanguimancyItemStacks.corruptionCrystallizer.getItem())
                    render(1F, 19F, 7F, 0.08F);
                return;
            }
            case INVENTORY: { //the item in inventories
                if (item.getItem() == SanguimancyItemStacks.corruptionCrystallizer.getItem())
                    render(-0.01F, 9F, 0.0F, 0.11F);
                return;
            }
            default:
                return;
        }

    }

    public void render(float x, float y, float z, float size) {
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
        GL11.glPushMatrix(); // start
        GL11.glScalef(size, size, size);
        GL11.glTranslatef(x, y, z); // size
        GL11.glRotatef(180, 1, 0, 0);
        GL11.glRotatef(-90, 0, 1, 0);
        model.renderAll();
        GL11.glPopMatrix(); // end
    }
}
