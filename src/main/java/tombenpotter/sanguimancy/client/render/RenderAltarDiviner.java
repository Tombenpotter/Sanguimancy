package tombenpotter.sanguimancy.client.render;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import tombenpotter.oldsanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.client.model.ModelAltarDiviner;
import tombenpotter.sanguimancy.registry.BlocksRegistry;
import tombenpotter.sanguimancy.tiles.TileAltarDiviner;

public class RenderAltarDiviner extends TileEntitySpecialRenderer implements IItemRenderer {
    private final RenderItem customRenderItem;
    public ModelAltarDiviner model = new ModelAltarDiviner();
    public ResourceLocation texture = new ResourceLocation(Sanguimancy.texturePath + ":textures/blocks/AltarDiviner.png");

    public RenderAltarDiviner() {
        customRenderItem = new RenderItem() {
            @Override
            public boolean shouldBob() {
                return false;
            }
        };
        customRenderItem.setRenderManager(RenderManager.instance);
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float scale) {
        renderModel((TileAltarDiviner) tileEntity, x, y, z);
        if (tileEntity instanceof TileAltarDiviner) {
            TileAltarDiviner tile = (TileAltarDiviner) tileEntity;
            GL11.glPushMatrix();
            if (tile.getStackInSlot(0) != null) {
                float scaleFactor = getGhostItemScaleFactor(tile.getStackInSlot(0));
                float rotationAngle = (float) (720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);
                EntityItem ghostEntityItem = new EntityItem(tile.getWorldObj());
                ghostEntityItem.hoverStart = 0.0F;
                ghostEntityItem.setEntityItemStack(tile.getStackInSlot(0));
                if (ghostEntityItem.getEntityItem().getItem() instanceof ItemBlock) {
                    GL11.glTranslatef((float) x + 0.5F, (float) y + 1F, (float) z + 0.5F);
                } else {
                    GL11.glTranslatef((float) x + 0.5F, (float) y + 1F, (float) z + 0.5F);
                }
                GL11.glScalef(scaleFactor, scaleFactor, scaleFactor);
                GL11.glRotatef(rotationAngle, 0.0F, 1.0F, 0.0F);
                customRenderItem.doRender(ghostEntityItem, 0, 0, 0, 0, 0);
            }
            GL11.glPopMatrix();
        }
    }

    public void renderModel(TileAltarDiviner tile, double x, double y, double z) {
        float scale = 1F;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glScalef(scale, scale, scale);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
        GL11.glRotatef(180F, 90.0F, 0.0F, 90.0F);
        model.render((Entity) null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }

    private float getGhostItemScaleFactor(ItemStack itemStack) {
        float scaleFactor = 1.0F;

        if (itemStack != null) {
            if (itemStack.getItem() instanceof ItemBlock) {
                switch (customRenderItem.getMiniBlockCount(itemStack, (byte) 1)) {
                    case 1:
                        return 0.90F;

                    case 2:
                        return 0.90F;

                    case 3:
                        return 0.90F;

                    case 4:
                        return 0.90F;

                    case 5:
                        return 0.80F;

                    default:
                        return 0.90F;
                }
            } else {
                switch (customRenderItem.getMiniItemCount(itemStack, (byte) 1)) {
                    case 1:
                        return 0.65F;

                    case 2:
                        return 0.65F;

                    case 3:
                        return 0.65F;

                    case 4:
                        return 0.65F;

                    default:
                        return 0.65F;
                }
            }
        }
        return scaleFactor;
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
                    render(0.5F, 15F, -0.5F, 0.1F);
                return;
            }
            case EQUIPPED: { //third person in hand
                if (item.getItem() == new ItemStack(BlocksRegistry.altarDiviner).getItem())
                    render(2F, 15F, 5F, 0.1F);
                return;
            }
            case EQUIPPED_FIRST_PERSON: { //first person in hand
                if (item.getItem() == new ItemStack(BlocksRegistry.altarDiviner).getItem())
                    render(1F, 19F, 7F, 0.1F);
                return;
            }
            case INVENTORY: { //the item in inventories
                if (item.getItem() == new ItemStack(BlocksRegistry.altarDiviner).getItem())
                    render(-0.01F, 10F, 0.0F, 0.1F);
                return;
            }
            default:
                return;
        }

    }

    private void render(float x, float y, float z, float size) {
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
