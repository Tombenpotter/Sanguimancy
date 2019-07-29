package tombenpotter.sanguimancy.items;

import WayofTime.alchemicalWizardry.common.items.EnergyItems;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.api.objects.BlockAndMetadata;
import tombenpotter.sanguimancy.util.ConfigHandler;
import tombenpotter.sanguimancy.util.RandomUtils;

import java.util.List;

public class ItemTranspositionSigil extends EnergyItems {

    public ItemTranspositionSigil() {
        this.maxStackSize = 1;
        setCreativeTab(Sanguimancy.creativeTab);
        setUnlocalizedName(Sanguimancy.modid + ".transpositionSigil");
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ri) {
        this.itemIcon = ri.registerIcon(Sanguimancy.texturePath + ":TranspositionSigil");
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        if (stack.getTagCompound() != null) {
            if (!GuiScreen.isShiftKeyDown()) {
                list.add(I18n.format("info.Sanguimancy.tooltip.shift.transposition.sigil.pun"));
                list.add(I18n.format("info.Sanguimancy.tooltip.shift.info"));
            } else {
                if (stack.getTagCompound().getInteger("blockId") != 0) {
                    String name = new ItemStack(Block.getBlockById(stack.getTagCompound().getInteger("blockId")), 1, stack.getTagCompound().getInteger("metadata")).getDisplayName();
                    list.add(I18n.format("compat.waila.content") + ": " + name);
                }
            }
        }
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        RandomUtils.checkAndSetCompound(stack);
        EnergyItems.checkAndSetItemOwner(stack, player);
        if (player.isSneaking() && stack.getTagCompound().getInteger("blockId") == 0) {
            EntityLightningBolt lightningBolt = new EntityLightningBolt(world, x, y, z, false);
            lightningBolt.getEntityData().setBoolean("isTranspositionSigilBolt", true);
            world.spawnEntity(lightningBolt);
        }
        if (!world.isRemote) {
            if (player.isSneaking() && stack.getTagCompound().getInteger("blockId") == 0 && !RandomUtils.transpositionBlockBlacklist.contains(new BlockAndMetadata(world.getBlock(x, y, z), world.getBlockMetadata(x, y, z)))) {
                int cost = ConfigHandler.transpositionSigilCost;
                if (world.getBlock(x, y, z).getPlayerRelativeBlockHardness(player, world, x, y, z) >= 0 && world.getBlock(x, y, z).getBlockHardness(world, x, y, z) >= 0) {
                    NBTTagCompound tileNBTTag = new NBTTagCompound();
                    int blockId = Block.getIdFromBlock(world.getBlock(x, y, z));
                    stack.getTagCompound().setInteger("blockId", blockId);
                    int metadata = world.getBlockMetadata(x, y, z);
                    stack.getTagCompound().setInteger("metadata", metadata);
                    if (world.getTileEntity(x, y, z) != null) {
                        TileEntity tile = world.getTileEntity(x, y, z);
                        tile.writeToNBT(tileNBTTag);
                        cost = cost * 5;
                        if (world.getTileEntity(x, y, z) instanceof TileEntityMobSpawner) cost = cost * 4;
                    }
                    stack.getTagCompound().setTag("TileNBTTag", tileNBTTag);
                    world.removeTileEntity(x, y, z);
                    world.setBlockToAir(x, y, z);
                    EnergyItems.syphonBatteries(stack, player, cost);
                    Sanguimancy.proxy.addColoredFlameEffects(world, x + 0.5, y + 0.5, z + 0.5, 0, 0, 0, 255, 72, 0);
                    return true;
                }
            } else {
                Block block = world.getBlock(x, y, z);
                Block blockToPlace = Block.getBlockById(stack.getTagCompound().getInteger("blockId"));
                int metadata = stack.getTagCompound().getInteger("metadata");

                if (blockToPlace != Blocks.AIR) {
                    if (block == Blocks.SNOW_LAYER && (world.getBlockMetadata(x, y, z) & 7) < 1) {
                        side = 1;
                    } else if (block != Blocks.VINE && block != Blocks.TALLGRASS && block != Blocks.DEADBUSH && !block.isReplaceable(world, x, y, z)) {
                        if (side == 0) --y;
                        if (side == 1) ++y;
                        if (side == 2) --z;
                        if (side == 3) ++z;
                        if (side == 4) --x;
                        if (side == 5) ++x;
                    }
                    if (block.canPlaceBlockOnSide(world, x, y, z, side)) {
                        EntityLightningBolt lightningBolt = new EntityLightningBolt(world, x, y, z, false);
                        lightningBolt.getEntityData().setBoolean("isTranspositionSigilBolt", true);
                        world.spawnEntity(lightningBolt);
                        if (!world.isRemote) {
                            world.setBlock(x, y, z, blockToPlace, metadata, 3);
                            stack.getTagCompound().setInteger("blockId", 0);
                            stack.getTagCompound().setInteger("metadata", 0);
                            blockToPlace.onBlockPlacedBy(world, x, y, z, player, new ItemStack(blockToPlace));
                            blockToPlace.onPostBlockPlaced(world, x, y, z, metadata);
                            if (stack.getTagCompound().getCompoundTag("TileNBTTag") != null && blockToPlace.hasTileEntity(metadata)) {
                                stack.getTagCompound().getCompoundTag("TileNBTTag").setInteger("x", x);
                                stack.getTagCompound().getCompoundTag("TileNBTTag").setInteger("y", y);
                                stack.getTagCompound().getCompoundTag("TileNBTTag").setInteger("z", z);
                                world.getTileEntity(x, y, z).readFromNBT(stack.getTagCompound().getCompoundTag("TileNBTTag"));
                                world.markBlockForUpdate(x, y, z);
                            }
                            world.markBlockForUpdate(x, y, z);
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }
}