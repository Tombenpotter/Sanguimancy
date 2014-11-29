package tombenpotter.sanguimancy.blocks;

import WayofTime.alchemicalWizardry.AlchemicalWizardry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.tile.TileBloodInterface;

public class BlockBloodInterface extends BlockContainer {
    public BlockBloodInterface() {
        super(Material.iron);
        this.setCreativeTab(AlchemicalWizardry.tabBloodMagic);
        this.setHardness(2.5F);
        this.setStepSound(Block.soundTypeStone);
        this.setBlockTextureName("AlchemicalWizardry:BlankRune");
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te != null && te instanceof TileBloodInterface) {
            TileBloodInterface bloodInterface = (TileBloodInterface) te;
            if (!bloodInterface.isItemValidForSlot(0, player.getCurrentEquippedItem())) return true;
            if (!world.isRemote) {
                ItemStack oldStack = bloodInterface.getStackInSlot(0);
                bloodInterface.setInventorySlotContents(0, player.getCurrentEquippedItem());
                player.inventory.setInventorySlotContents(player.inventory.currentItem, oldStack);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
        if (!player.capabilities.isCreativeMode)
            breakBlock(world, x, y, z);
        return super.removedByPlayer(world, player, x, y, z, willHarvest);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block p_149749_5_, int p_149749_6_) {
        breakBlock(world, x, y, z);
        super.breakBlock(world, x, y, z, p_149749_5_, p_149749_6_);
    }

    private void breakBlock(World world, int xCoord, int yCoord, int zCoord) {
        TileEntity te = world.getTileEntity(xCoord, yCoord, zCoord);
        if (te instanceof TileBloodInterface)
            ((TileBloodInterface) te).breakInterface();
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
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileBloodInterface();
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int p_149736_5_) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te != null && te instanceof TileBloodInterface) {
            TileBloodInterface bloodInterface = (TileBloodInterface) te;
            return bloodInterface.getComparatorLevel();
        }
        return 0;
    }

}
