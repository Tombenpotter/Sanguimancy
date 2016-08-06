package tombenpotter.oldsanguimancy.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tombenpotter.oldsanguimancy.Sanguimancy;
import tombenpotter.oldsanguimancy.tile.TileAltarManipulator;
import tombenpotter.sanguimancy.util.RandomUtils;
import tombenpotter.sanguimancy.util.SanguimancyItemStacks;

public class BlockAltarManipulator extends BlockContainer {

    public BlockAltarManipulator(Material material) {
        super(material);
        setHardness(5.0F);
        setCreativeTab(Sanguimancy.tabSanguimancy);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileAltarManipulator();
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block par5, int par6) {
        RandomUtils.dropItems(world, x, y, z);
        world.removeTileEntity(x, y, z);
        super.breakBlock(world, x, y, z, par5, par6);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        TileAltarManipulator tile = (TileAltarManipulator) world.getTileEntity(x, y, z);
        if (player.getHeldItem() == null && tile.getStackInSlot(2) != null) {
            ItemStack stack = tile.getStackInSlot(2);
            tile.setInventorySlotContents(2, null);
            player.inventory.addItemStackToInventory(stack);
            world.markBlockForUpdate(x, y, z);
            return true;
        } else if (player.getHeldItem() != null && player.getHeldItem().isItemEqual(SanguimancyItemStacks.sanguineShifter) && tile.getStackInSlot(2) == null) {
            ItemStack stack = player.getHeldItem().copy();
            stack.stackSize = 1;
            tile.setInventorySlotContents(2, stack);
            tile.sideToOutput = side;
            if (!player.capabilities.isCreativeMode) player.inventory.consumeInventoryItem(stack.getItem());
            world.markBlockForUpdate(x, y, z);
            return true;
        }
        return false;
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
}
