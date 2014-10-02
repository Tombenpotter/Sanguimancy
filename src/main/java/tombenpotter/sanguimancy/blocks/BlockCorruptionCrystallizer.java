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
import net.minecraft.world.World;
import tombenpotter.sanguimancy.Sanguimancy;
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
            player.addChatComponentMessage(new ChatComponentText("Tier " + String.valueOf(tile.tier)));
            player.addChatComponentMessage(new ChatComponentText("Corruption Stored " + String.valueOf(tile.corruptionStored)));
        }
        return false;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack stack) {
        if (living != null && living instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) living;
            TileCorruptionCrystallizer tile = (TileCorruptionCrystallizer) world.getTileEntity(x, y, z);
            tile.owner = player.getCommandSenderName();
        }
    }
}
