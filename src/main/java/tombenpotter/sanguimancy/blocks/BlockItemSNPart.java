package tombenpotter.sanguimancy.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.tile.TileItemSNPart;
import tombenpotter.sanguimancy.api.BlockPostition;
import tombenpotter.sanguimancy.api.soulNetworkManifestation.ISNComponent;
import tombenpotter.sanguimancy.util.singletons.BoundItems;

import java.util.Random;

public class BlockItemSNPart extends BlockContainer {

    public BlockItemSNPart(Material material) {
        super(material);
        setHardness(20.0F);
        setCreativeTab(Sanguimancy.tabSanguimancy);
        setBlockUnbreakable();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister ir) {
        this.blockIcon = ir.registerIcon(Sanguimancy.texturePath + ":SNBoundItem");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return new TileItemSNPart();
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
    public int quantityDropped(Random p_149745_1_) {
        return 0;
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return null;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int p_149749_6_) {
        if (world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof TileItemSNPart) {
            TileItemSNPart tile = (TileItemSNPart) world.getTileEntity(x, y, z);
            BoundItems.getBoundItems().removeItem(tile.getCustomNBTTag().getString("SavedItemName"));
        }
        super.breakBlock(world, x, y, z, block, p_149749_6_);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof TileItemSNPart) {
            TileItemSNPart tile = (TileItemSNPart) world.getTileEntity(x, y, z);
            if (tile.onBlockRightClicked(player, player.getHeldItem())) {
                return true;
            }
        }
        return super.onBlockActivated(world, x, y, z, player, p_149727_6_, p_149727_7_, p_149727_8_, p_149727_9_);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {
        ISNComponent branch = (ISNComponent) world.getTileEntity(x, y, z);
        if (!branch.getComponentsInNetwork().hashMap.isEmpty()) {
            for (BlockPostition postition : branch.getComponentsInNetwork().hashMap.keySet()) {
                ISNComponent component = (ISNComponent) postition.getTile(world);
                component.onNetworkUpdate(new BlockPostition(x, y, z));
            }
        }
    }
}
