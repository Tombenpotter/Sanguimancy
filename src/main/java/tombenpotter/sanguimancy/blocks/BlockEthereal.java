package tombenpotter.sanguimancy.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.entity.EntityChickenMinion;
import tombenpotter.sanguimancy.tile.TileEthereal;
import tombenpotter.sanguimancy.util.SanguimancyItemStacks;

import java.util.List;

public class BlockEthereal extends BlockContainer {

    public BlockEthereal(Material material) {
        super(material);
        setHardness(5.0F);
        setCreativeTab(Sanguimancy.tabSanguimancy);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister ir) {
        this.blockIcon = ir.registerIcon(Sanguimancy.texturePath + ":EtherealBlock");
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List list, Entity entity) {
        if (entity != null && ((entity instanceof EntityPlayer && !entity.isSneaking()) || entity instanceof EntityChickenMinion)) {
            return;
        } else {
            super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
        }
    }

    @Override
    public boolean getBlocksMovement(IBlockAccess access, int x, int y, int z) {
        return false;
    }

    @Override
    public boolean isNormalCube() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEthereal();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        TileEthereal tile = (TileEthereal) world.getTileEntity(x, y, z);
        if (player.getHeldItem() != null && player.getHeldItem().isItemEqual(SanguimancyItemStacks.etherealBlock)) {
            return false;
        }
        if (player.isSneaking() && player.getHeldItem() == null) {
            tile.block = Block.getIdFromBlock(Blocks.air);
            tile.metadata = 0;
            world.markBlockForUpdate(x, y, z);
            return true;
        } else if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemBlock) {
            ItemBlock itemBlock = (ItemBlock) player.getHeldItem().getItem();
            tile.block = Block.getIdFromBlock(Block.getBlockFromItem(itemBlock));
            tile.metadata = player.getHeldItem().getItemDamage();
            world.markBlockForUpdate(x, y, z);
            System.out.println(String.valueOf(tile.block));
            return true;
        }
        return true;
    }

    @Override
    public int getMobilityFlag() {
        return 2;
    }

    @Override
    public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(IBlockAccess access, int x, int y, int z, int meta) {
        TileEthereal tile = (TileEthereal) access.getTileEntity(x, y, z);
        if (Block.getBlockById(tile.block) != Blocks.air) {
            return Block.getBlockById(tile.block).getIcon(access, x, y, z, meta);
        } else return super.getIcon(access, x, y, z, meta);
    }

    @Override
    public boolean canPlaceTorchOnTop(World world, int x, int y, int z) {
        TileEthereal tile = (TileEthereal) world.getTileEntity(x, y, z);
        if (Block.getBlockById(tile.block) != Blocks.air) {
            return Block.getBlockById(tile.block).canPlaceTorchOnTop(world, x, y, z);
        } else return true;
    }
}
