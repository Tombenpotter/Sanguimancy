package tombenpotter.sanguimancy.client.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import org.lwjgl.opengl.GL11;
import tombenpotter.sanguimancy.blocks.BlockBloodTank;

public class RenderBloodTank implements ISimpleBlockRenderingHandler, IItemRenderer {

//I suck horribly at rendering. This is all skyboy's

    @Override
    public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
        return type.ordinal() < IItemRenderer.ItemRenderType.FIRST_PERSON_MAP.ordinal();
    }

    @Override
    public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        RenderBlocks renderer = (RenderBlocks) data[0];
        Block block = Block.getBlockFromItem(item.getItem());
        GL11.glPushMatrix();
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        switch (type) {
            case EQUIPPED_FIRST_PERSON:
            case EQUIPPED:
                GL11.glTranslated(0.5, 0.5, 0.5);
                break;
            default:
        }
        renderTank(block, item, renderer);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    private void renderTank(Block block, ItemStack stack, RenderBlocks renderer) {
        FluidStack fluid = ((IFluidContainerItem) stack.getItem()).drain(stack, 1, false);
        int color = fluid == null ? 0xFFFFFF : fluid.getFluid().getColor(fluid);
        float red = (color >> 16 & 255) / 255.0F;
        float green = (color >> 8 & 255) / 255.0F;
        float blue = (color & 255) / 255.0F;
        IIcon iconFluid = fluid != null ? fluid.getFluid().getIcon(fluid) : renderer.getBlockIconFromSideAndMetadata(block, 6, 1);
        IIcon iconFluidTop = fluid != null ? iconFluid : renderer.getBlockIconFromSideAndMetadata(block, 7, 1);
        Tessellator tessellator = Tessellator.instance;
        final double offset = 0.003;
        block.setBlockBoundsForItemRender();
        renderer.setRenderBoundsFromBlock(block);
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        renderer.renderFaceYNeg(block, 0, 0, 0, renderer.getBlockIconFromSideAndMetadata(block, 0, 0));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        tessellator.setColorOpaque_F(red, green, blue);
        renderer.renderMaxY = 1 - offset;
        renderer.renderFaceYPos(block, 0, 0, 0, iconFluidTop);
        renderer.renderMaxY = 1;
        tessellator.setColorOpaque_F(1, 1, 1);
        renderer.renderFaceYPos(block, 0, 0, 0, renderer.getBlockIconFromSideAndMetadata(block, 1, 0));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        tessellator.setColorOpaque_F(red, green, blue);
        renderer.renderMaxX = 1 - offset;
        renderer.renderFaceXPos(block, 0, 0, 0, iconFluid);
        renderer.renderMaxX = 1;
        tessellator.setColorOpaque_F(1, 1, 1);
        renderer.renderFaceXPos(block, 0, 0, 0, renderer.getBlockIconFromSideAndMetadata(block, 2, 0));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        tessellator.setColorOpaque_F(red, green, blue);
        renderer.renderMinX = offset;
        renderer.renderFaceXNeg(block, 0, 0, 0, iconFluid);
        renderer.renderMinX = 0;
        tessellator.setColorOpaque_F(1, 1, 1);
        renderer.renderFaceXNeg(block, 0, 0, 0, renderer.getBlockIconFromSideAndMetadata(block, 3, 0));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        tessellator.setColorOpaque_F(red, green, blue);
        renderer.renderMinZ = offset;
        renderer.renderFaceZNeg(block, 0, 0, 0, iconFluid);
        renderer.renderMinZ = 0;
        tessellator.setColorOpaque_F(1, 1, 1);
        renderer.renderFaceZNeg(block, 0, 0, 0, renderer.getBlockIconFromSideAndMetadata(block, 4, 0));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        tessellator.setColorOpaque_F(red, green, blue);
        renderer.renderMaxZ = 1 - offset;
        renderer.renderFaceZPos(block, 0, 0, 0, iconFluid);
        renderer.renderMaxZ = 1;
        tessellator.setColorOpaque_F(1, 1, 1);
        renderer.renderFaceZPos(block, 0, 0, 0, renderer.getBlockIconFromSideAndMetadata(block, 5, 0));
        tessellator.draw();
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
    }

    private int max(int a, Block block, IBlockAccess blockAccess, int x, int y, int z) {
        if (((a >> 4) & 15) == 15)
            return a;
        return Math.max(a, block.getMixedBrightnessForBlock(blockAccess, x, y, z));
    }

    private boolean isBlock(IBlockAccess blockAccess, int x, int y, int z, Block block) {
        return blockAccess.getBlock(x, y, z) == block;
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        if (renderer.hasOverrideBlockTexture()) {
            renderer.renderFaceYNeg(block, x, y, z, null);
            renderer.renderFaceYPos(block, x, y, z, null);
            renderer.renderFaceZNeg(block, x, y, z, null);
            renderer.renderFaceZPos(block, x, y, z, null);
            renderer.renderFaceXNeg(block, x, y, z, null);
            renderer.renderFaceXPos(block, x, y, z, null);
            return true;
        }
        Tessellator tessellator = Tessellator.instance;
        int b = block.getLightValue(blockAccess, x, y, z);
        b = b << 20 | b << 4;
        int b2 = Math.max(b, block.getMixedBrightnessForBlock(blockAccess, x, y, z));
        int worldHeight = blockAccess.getHeight();
        int color = block.colorMultiplier(blockAccess, x, y, z);
        float red = (color >> 16 & 255) / 255.0F;
        float green = (color >> 8 & 255) / 255.0F;
        float blue = (color & 255) / 255.0F;
        IIcon iconFluid = block.getIcon(blockAccess, x, y, z, 6);
        IIcon iconFluidTop = block.getIcon(blockAccess, x, y, z, 7);
        final double offset = 0.003;
        boolean render = renderer.renderAllFaces;
        boolean[] renderSide = {
                render || y <= 0 || block.shouldSideBeRendered(blockAccess, x, y - 1, z, 0),
                render || y >= worldHeight || block.shouldSideBeRendered(blockAccess, x, y + 1, z, 1),
                render || block.shouldSideBeRendered(blockAccess, x, y, z - 1, 2),
                render || block.shouldSideBeRendered(blockAccess, x, y, z + 1, 3),
                render || block.shouldSideBeRendered(blockAccess, x - 1, y, z, 4),
                render || block.shouldSideBeRendered(blockAccess, x + 1, y, z, 5),
        };
        render = false;
        if (renderSide[0]) {
            tessellator.setBrightness(max(b, block, blockAccess, x, y - 1, z));
            tessellator.setColorOpaque_F(1, 1, 1);
            renderer.renderFaceYNeg(block, x, y, z, block.getIcon(blockAccess, x, y, z, 0));
            render = true;
        }
        if (!isBlock(blockAccess, x, y - 1, z, block)) {
            tessellator.setBrightness(b2);
            tessellator.setColorOpaque_F(1, 1, 1);
            renderer.renderMaxY = offset;
            renderer.renderFaceYPos(block, x, y, z, block.getIcon(blockAccess, x, y, z, 0));
            renderer.renderMaxY = 1;
            render = true;
        }
        if (renderSide[1]) {
            tessellator.setBrightness(max(b, block, blockAccess, x, y + 1, z));
            tessellator.setColorOpaque_F(red, green, blue);
            renderer.renderMaxY = 1 - offset;
            renderer.renderFaceYPos(block, x, y, z, iconFluidTop);
            renderer.renderMaxY = 1;
            tessellator.setColorOpaque_F(1, 1, 1);
            renderer.renderFaceYPos(block, x, y, z, block.getIcon(blockAccess, x, y, z, 1));
            render = true;
        }
        if (!isBlock(blockAccess, x, y + 1, z, block)) {
            tessellator.setBrightness(b2);
            tessellator.setColorOpaque_F(1, 1, 1);
            renderer.renderMinY = 1 - offset * 2;
            renderer.renderFaceYNeg(block, x, y, z, block.getIcon(blockAccess, x, y, z, 1));
            renderer.renderMinY = 0;
            render = true;
        }
        if (renderSide[2]) {
            tessellator.setBrightness(max(b, block, blockAccess, x, y, z - 1));
            tessellator.setColorOpaque_F(red, green, blue);
            renderer.renderMinZ = offset;
            renderer.renderFaceZNeg(block, x, y, z, iconFluid);
            renderer.renderMinZ = 0;
            tessellator.setColorOpaque_F(1, 1, 1);
            renderer.renderFaceZNeg(block, x, y, z, block.getIcon(blockAccess, x, y, z, 2));
            render = true;
        }
        if (!isBlock(blockAccess, x, y, z - 1, block)) {
            tessellator.setBrightness(b2);
            tessellator.setColorOpaque_F(1, 1, 1);
            renderer.renderMaxZ = offset * 2;
            renderer.renderFaceZPos(block, x, y, z, block.getIcon(blockAccess, x, y, z, 2));
            renderer.renderMaxZ = 1;
            render = true;
        }
        if (renderSide[3]) {
            tessellator.setBrightness(max(b, block, blockAccess, x, y, z + 1));
            tessellator.setColorOpaque_F(red, green, blue);
            renderer.renderMaxZ = 1 - offset;
            renderer.renderFaceZPos(block, x, y, z, iconFluid);
            renderer.renderMaxZ = 1;
            tessellator.setColorOpaque_F(1, 1, 1);
            renderer.renderFaceZPos(block, x, y, z, block.getIcon(blockAccess, x, y, z, 3));
            render = true;
        }
        if (!isBlock(blockAccess, x, y, z + 1, block)) {
            tessellator.setBrightness(b2);
            tessellator.setColorOpaque_F(1, 1, 1);
            renderer.renderMinZ = 1 - offset * 2;
            renderer.renderFaceZNeg(block, x, y, z, block.getIcon(blockAccess, x, y, z, 3));
            renderer.renderMinZ = 0;
            render = true;
        }
        if (renderSide[4]) {
            tessellator.setBrightness(max(b, block, blockAccess, x - 1, y, z));
            tessellator.setColorOpaque_F(red, green, blue);
            renderer.renderMinX = offset;
            renderer.renderFaceXNeg(block, x, y, z, iconFluid);
            renderer.renderMinX = 0;
            tessellator.setColorOpaque_F(1, 1, 1);
            renderer.renderFaceXNeg(block, x, y, z, block.getIcon(blockAccess, x, y, z, 4));
            render = true;
        }
        if (!isBlock(blockAccess, x - 1, y, z, block)) {
            tessellator.setBrightness(b2);
            tessellator.setColorOpaque_F(1, 1, 1);
            renderer.renderMaxX = offset * 2;
            renderer.renderFaceXPos(block, x, y, z, block.getIcon(blockAccess, x, y, z, 4));
            renderer.renderMaxX = 1;
            render = true;
        }
        if (renderSide[5]) {
            tessellator.setBrightness(max(b, block, blockAccess, x + 1, y, z));
            tessellator.setColorOpaque_F(red, green, blue);
            renderer.renderMaxX = 1 - offset;
            renderer.renderFaceXPos(block, x, y, z, iconFluid);
            renderer.renderMaxX = 1;
            tessellator.setColorOpaque_F(1, 1, 1);
            renderer.renderFaceXPos(block, x, y, z, block.getIcon(blockAccess, x, y, z, 5));
            render = true;
        }
        if (!isBlock(blockAccess, x + 1, y, z, block)) {
            tessellator.setBrightness(b2);
            tessellator.setColorOpaque_F(1, 1, 1);
            renderer.renderMinX = 1 - offset * 2;
            renderer.renderFaceXNeg(block, x, y, z, block.getIcon(blockAccess, x, y, z, 5));
            renderer.renderMinY = 0;
            render = true;
        }
        return render;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }

    @Override
    public int getRenderId() {
        return BlockBloodTank.renderId;
    }
}
