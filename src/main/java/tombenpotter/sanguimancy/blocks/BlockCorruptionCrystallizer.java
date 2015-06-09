package tombenpotter.sanguimancy.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.api.soulCorruption.SoulCorruptionHelper;
import tombenpotter.sanguimancy.tile.TileCorruptionCrystallizer;

public class BlockCorruptionCrystallizer extends BlockContainer {

    public BlockCorruptionCrystallizer(Material material) {
        super(material);
        setHardness(5.0F);
        setCreativeTab(Sanguimancy.tabSanguimancy);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister ri) {
        this.blockIcon = ri.registerIcon(Sanguimancy.texturePath + ":CrystallizedCorruption");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int var2) {
        return new TileCorruptionCrystallizer();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (!world.isRemote) {
            TileCorruptionCrystallizer tile = (TileCorruptionCrystallizer) world.getTileEntity(x, y, z);
            tile.checkMultiblockTier(world, x, y, z);
            player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("compat.waila.multiblock.formed") + ": " + String.valueOf(tile.multiblockFormed)));
            player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("compat.waila.corruption.stored") + ": " + String.valueOf(tile.corruptionStored)));
            player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("compat.waila.owner") + ": " + tile.owner));
        }
        return super.onBlockActivated(world, x, y, z, player, p_149727_6_, p_149727_7_, p_149727_8_, p_149727_9_);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack stack) {
        if (living != null && living instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) living;
            TileCorruptionCrystallizer tile = (TileCorruptionCrystallizer) world.getTileEntity(x, y, z);
            tile.owner = player.getCommandSenderName();
        }
    }

    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
        if (player != null) {
            TileCorruptionCrystallizer tile = (TileCorruptionCrystallizer) world.getTileEntity(x, y, z);
            SoulCorruptionHelper.addCorruption(player, tile.corruptionStored);
        }
        world.removeTileEntity(x, y, y);
        return super.removedByPlayer(world, player, x, y, z);
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
