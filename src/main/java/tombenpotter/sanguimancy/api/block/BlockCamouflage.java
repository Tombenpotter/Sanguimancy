package tombenpotter.sanguimancy.api.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.api.tile.TileCamouflage;

public class BlockCamouflage extends BlockContainer {

    public BlockCamouflage(Material material) {
        super(material);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        TileCamouflage tile = (TileCamouflage) world.getTileEntity(x, y, z);
        if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemBlock) {
            Block block = Block.getBlockFromItem(player.getHeldItem().getItem());
            if (block instanceof BlockCamouflage) return false;
            if (!block.isNormalCube()) return false;
        }
        if (player.getHeldItem() != null && player.getHeldItem().getItem() == Items.water_bucket) {
            tile.block = Block.getIdFromBlock(Blocks.air);
            tile.metadata = 0;
            world.setBlockMetadataWithNotify(x, y, z, 0, 3);
            world.markBlockForUpdate(x, y, z);
            return true;
        } else if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemBlock) {
            ItemBlock itemBlock = (ItemBlock) player.getHeldItem().getItem();
            tile.block = Block.getIdFromBlock(Block.getBlockFromItem(itemBlock));
            tile.metadata = player.getHeldItem().getItemDamage();
            world.setBlockMetadataWithNotify(x, y, z, player.getHeldItem().getItemDamage(), 3);
            world.markBlockForUpdate(x, y, z);
            return true;
        }
        return true;
    }

    @Override
    public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side) {
        TileCamouflage tile = (TileCamouflage) access.getTileEntity(x, y, z);
        if (Block.getBlockById(tile.block) != Blocks.air) {
            return Block.getBlockById(tile.block).getIcon(side, tile.metadata);
        } else return super.getIcon(access, x, y, z, side);
    }

    @Override
    public boolean canPlaceTorchOnTop(World world, int x, int y, int z) {
        TileCamouflage tile = (TileCamouflage) world.getTileEntity(x, y, z);
        if (Block.getBlockById(tile.block) != Blocks.air) {
            return Block.getBlockById(tile.block).canPlaceTorchOnTop(world, x, y, z);
        } else return true;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileCamouflage();
    }

    @Override
    public Item getItem(World world, int x, int y, int z) {
        TileCamouflage tile = (TileCamouflage) world.getTileEntity(x, y, z);
        if (Block.getBlockById(tile.block) != Blocks.air) {
            return new ItemStack(Block.getBlockById(tile.block), 1, tile.metadata).getItem();
        } else return super.getItem(world, x, y, z);
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
        TileCamouflage tile = (TileCamouflage) world.getTileEntity(x, y, z);
        if (Block.getBlockById(tile.block) != Blocks.air) {
            return new ItemStack(Block.getBlockById(tile.block), 1, tile.metadata);
        } else return super.getPickBlock(target, world, x, y, z, player);
    }

    @Override
    public int getDamageValue(World world, int x, int y, int z) {
        TileCamouflage tile = (TileCamouflage) world.getTileEntity(x, y, z);
        if (Block.getBlockById(tile.block) != Blocks.air) return tile.metadata;
        else return super.getDamageValue(world, x, y, z);
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        TileCamouflage tile = (TileCamouflage) world.getTileEntity(x, y, z);
        if (Block.getBlockById(tile.block) != Blocks.air) {
            return Block.getBlockById(tile.block).getLightValue();
        } else return super.getLightValue(world, x, y, z);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int colorMultiplier(IBlockAccess access, int x, int y, int z) {
        TileCamouflage tile = (TileCamouflage) access.getTileEntity(x, y, z);
        if (Block.getBlockById(tile.block) != Blocks.air) {
            return Block.getBlockById(tile.block).colorMultiplier(access, x, y, z);
        } else return super.colorMultiplier(access, x, y, z);
    }

    @Override
    public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess access, int x, int y, int z) {
        return false;
    }
}
