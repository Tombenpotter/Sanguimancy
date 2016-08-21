package tombenpotter.sanguimancy.blocks;

import WayofTime.bloodmagic.api.util.helper.PlayerHelper;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import tombenpotter.oldsanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.api.soulCorruption.SoulCorruptionHelper;
import tombenpotter.sanguimancy.tiles.TileCorruptionCrystallizer;

import javax.annotation.Nullable;

public class BlockCorruptionCrystallizer extends BlockContainer {

    public BlockCorruptionCrystallizer(Material material) {
        super(material);
        setHardness(5.0F);
        setCreativeTab(Sanguimancy.tabSanguimancy);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileCorruptionCrystallizer();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileCorruptionCrystallizer tile = (TileCorruptionCrystallizer) world.getTileEntity(pos);
        if (!world.isRemote) {
            tile.checkMultiblock(world);
        } else {
            player.addChatComponentMessage(new TextComponentString(I18n.format("compat.waila.multiblock.formed") + ": " + String.valueOf(tile.multiblockFormed)));
            player.addChatComponentMessage(new TextComponentString(I18n.format("compat.waila.corruption.stored") + ": " + String.valueOf(tile.corruptionStored)));
            player.addChatComponentMessage(new TextComponentString(I18n.format("compat.waila.owner") + ": " + tile.ownerUUID));
        }
        return super.onBlockActivated(world, pos, state, player, hand, heldItem, side, hitX, hitY, hitZ);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (placer != null && placer instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) placer;
            TileCorruptionCrystallizer tile = (TileCorruptionCrystallizer) world.getTileEntity(pos);
            tile.ownerUUID = PlayerHelper.getUUIDFromPlayer(player).toString();
        }
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        if (player != null) {
            TileCorruptionCrystallizer tile = (TileCorruptionCrystallizer) world.getTileEntity(pos);
            SoulCorruptionHelper.addCorruption(player, tile.corruptionStored);
        }
        world.removeTileEntity(pos);

        return super.removedByPlayer(state, world, pos, player, willHarvest);
    }
}
