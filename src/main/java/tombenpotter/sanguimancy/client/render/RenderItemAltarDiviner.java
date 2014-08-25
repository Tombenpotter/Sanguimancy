package tombenpotter.sanguimancy.client.render;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.client.model.ModelAltarDiviner;
import tombenpotter.sanguimancy.registry.BlocksRegistry;

public class RenderItemAltarDiviner implements IItemRenderer {

    private ModelAltarDiviner modelAltar;
    private ResourceLocation texure;

    public RenderItemAltarDiviner() {
        modelAltar = new ModelAltarDiviner();
        texure = new ResourceLocation(Sanguimancy.texturePath, "textures/blocks/AltarDiviner.png");
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
                if (item.getItem() == new ItemStack(BlocksRegistry.altarDiviner).getItem())
                    renderWorkbench(0.5F, 15F, -0.5F, 0.09F);
                return;
            }
            case EQUIPPED: { //third person in hand
                if (item.getItem() == new ItemStack(BlocksRegistry.altarDiviner).getItem())
                    renderWorkbench(2F, 15F, 5F, 0.10F);
                return;
            }
            case EQUIPPED_FIRST_PERSON: { //first person in hand
                if (item.getItem() == new ItemStack(BlocksRegistry.altarDiviner).getItem())
                    renderWorkbench(1F, 19F, 7F, 0.08F);
                return;
            }
            case INVENTORY: { //the item in inventories
                if (item.getItem() == new ItemStack(BlocksRegistry.altarDiviner).getItem())
                    renderWorkbench(-0.01F, 10F, 0.0F, 0.1F);
                return;
            }
            default:
                return;
        }

    }

    private void renderWorkbench(float x, float y, float z, float size) {

        FMLClientHandler.instance().getClient().renderEngine.bindTexture(texure);
        GL11.glPushMatrix(); // start
        GL11.glScalef(size, size, size);
        GL11.glTranslatef(x, y, z); // size
        GL11.glRotatef(180, 1, 0, 0);
        GL11.glRotatef(-90, 0, 1, 0);
        modelAltar.renderAll();
        GL11.glPopMatrix(); // end
    }
}
