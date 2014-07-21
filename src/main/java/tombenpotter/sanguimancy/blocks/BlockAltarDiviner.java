package tombenpotter.sanguimancy.blocks;

import WayofTime.alchemicalWizardry.AlchemicalWizardry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.tile.TileAltarDiviner;

import java.util.Random;

public class BlockAltarDiviner extends BlockContainer {

    public BlockAltarDiviner(Material material) {
        super(material);
        setHardness(5.0F);
        setCreativeTab(AlchemicalWizardry.tabBloodMagic);
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
        if (player.getHeldItem() != null) {
            ItemStack stack = player.getHeldItem().copy();
            stack.stackSize = player.getHeldItem().stackSize;
            TileAltarDiviner tile = (TileAltarDiviner) world.getTileEntity(x, y, z);
            tile.setInventorySlotContents(0, stack);
            if (!player.capabilities.isCreativeMode)
                for (int i = 0; i <= stack.stackSize; i++)
                    player.inventory.consumeInventoryItem(stack.getItem());
        } else {
            TileAltarDiviner tile = (TileAltarDiviner) world.getTileEntity(x, y, z);
            ItemStack stack = tile.getStackInSlot(0);
            player.inventory.addItemStackToInventory(stack);
        }
        world.markBlockForUpdate(x, y, z);
        return true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block par5, int par6) {
        dropItems(world, x, y, z);
        world.removeTileEntity(x, y, z);
        super.breakBlock(world, x, y, z, par5, par6);
    }

    private void dropItems(World world, int x, int y, int z) {
        Random rand = new Random();

        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (!(tileEntity instanceof IInventory)) {
            return;
        }
        IInventory inventory = (IInventory) tileEntity;

        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack item = inventory.getStackInSlot(i);

            if (item != null && item.stackSize > 0) {
                float rx = rand.nextFloat() * 0.8F + 0.1F;
                float ry = rand.nextFloat() * 0.8F + 0.1F;
                float rz = rand.nextFloat() * 0.8F + 0.1F;

                EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z + rz, new ItemStack(item.getItem(), item.stackSize, item.getItemDamage()));

                if (item.hasTagCompound()) {
                    entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
                }

                float factor = 0.05F;
                entityItem.motionX = rand.nextGaussian() * factor;
                entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                entityItem.motionZ = rand.nextGaussian() * factor;
                world.spawnEntityInWorld(entityItem);
                item.stackSize = 0;
            }
        }
    }
}
