package tombenpotter.sanguimancy.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.tile.TileAltarDiviner;
import tombenpotter.sanguimancy.util.RandomUtils;

public class BlockAltarDiviner extends BlockContainer {

    public BlockAltarDiviner(Material material) {
        super(material);
        setHardness(5.0F);
        setCreativeTab(Sanguimancy.tabSanguimancy);
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
        return new TileAltarDiviner();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister ir) {
        this.blockIcon = ir.registerIcon(Sanguimancy.texturePath + ":AltarDiviner");
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        TileAltarDiviner tile = (TileAltarDiviner) world.getTileEntity(x, y, z);
        if (player.getHeldItem() == null && tile.getStackInSlot(0) != null) {
            ItemStack stack = tile.getStackInSlot(0);
            tile.setInventorySlotContents(0, null);
            player.inventory.addItemStackToInventory(stack);
            world.markBlockForUpdate(x, y, z);
        } else if (player.getHeldItem() != null && tile.getStackInSlot(0) == null) {
            ItemStack stack = player.getHeldItem().copy();
            stack.stackSize = player.getHeldItem().stackSize;
            tile.setInventorySlotContents(0, stack);
            if (!player.capabilities.isCreativeMode) {
                for (int i = 0; i < stack.stackSize; i++)
                    player.inventory.consumeInventoryItem(stack.getItem());
            }
            world.markBlockForUpdate(x, y, z);
        }
        return true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block par5, int par6) {
        RandomUtils.dropItems(world, x, y, z);
        world.removeTileEntity(x, y, z);
        super.breakBlock(world, x, y, z, par5, par6);
    }
}
