package tombenpotter.sanguimancy.items;

import WayofTime.alchemicalWizardry.common.items.EnergyItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.util.RandomUtils;

import java.util.List;

public class ItemTranspositionSigil extends EnergyItems {

    public ItemTranspositionSigil() {
        this.maxStackSize = 1;
        setCreativeTab(Sanguimancy.tabSanguimancy);
        setUnlocalizedName(Sanguimancy.modid + ".transpositionSigil");
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ri) {
        this.itemIcon = ri.registerIcon(Sanguimancy.texturePath + ":TranspositionSigil");
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        if (stack.stackTagCompound != null) {
            if (!GuiScreen.isShiftKeyDown()) {
                list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.shift.transposition.sigil.pun"));
                list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.shift.info"));
            } else {
                list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.shift.transposition.sigil.pun"));
            }
        }
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        RandomUtils.checkAndSetCompound(stack);
        EnergyItems.checkAndSetItemOwner(stack, player);
        if (!world.isRemote) {
            if (player.isSneaking() && stack.stackTagCompound.getInteger("blockId") == 0) {
                NBTTagCompound tileNBTTag = new NBTTagCompound();
                int blockId = Block.getIdFromBlock(world.getBlock(x, y, z));
                stack.stackTagCompound.setInteger("blockId", blockId);
                int metadata = world.getBlockMetadata(x, y, z);
                stack.stackTagCompound.setInteger("metadata", metadata);
                if (world.getTileEntity(x, y, z) != null) {
                    TileEntity tile = world.getTileEntity(x, y, z);
                    tile.writeToNBT(tileNBTTag);
                }
                stack.stackTagCompound.setTag("TileNBTTag", tileNBTTag);
                world.removeTileEntity(x, y, z);
                world.setBlockToAir(x, y, z);
                EnergyItems.syphonBatteries(stack, player, 750);
                return true;
            } else {
                Block block = world.getBlock(x, y, z);
                Block blockToPlace = Block.getBlockById(stack.stackTagCompound.getInteger("blockId"));
                int metadata = stack.stackTagCompound.getInteger("metadata");

                if (blockToPlace != Blocks.air) {
                    if (block == Blocks.snow_layer && (world.getBlockMetadata(x, y, z) & 7) < 1) {
                        side = 1;
                    } else if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush && !block.isReplaceable(world, x, y, z)) {
                        if (side == 0) --y;
                        if (side == 1) ++y;
                        if (side == 2) --z;
                        if (side == 3) ++z;
                        if (side == 4) --x;
                        if (side == 5) ++x;
                    }
                    if (block.canPlaceBlockOnSide(world, x, y, z, side)) {
                        if (!world.isRemote) {
                            world.setBlock(x, y, z, blockToPlace, metadata, 3);
                            blockToPlace.onBlockPlacedBy(world, x, y, z, player, new ItemStack(blockToPlace));
                            if (stack.stackTagCompound.getCompoundTag("TileNBTTag") != null && blockToPlace.hasTileEntity(metadata)) {
                                TileEntity tile = TileEntity.createAndLoadEntity(stack.stackTagCompound.getCompoundTag("TileNBTTag"));
                                tile.xCoord = x;
                                tile.yCoord = y;
                                tile.zCoord = z;
                                world.setTileEntity(x, y, z, tile);
                                world.markBlockForUpdate(x, y, z);
                                tile.markDirty();
                            }
                            stack.stackTagCompound.setInteger("blockId", 0);
                            stack.stackTagCompound.setInteger("metadata", 0);
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }
}
