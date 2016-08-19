package tombenpotter.oldsanguimancy.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import tombenpotter.oldsanguimancy.Sanguimancy;
import tombenpotter.oldsanguimancy.tiles.TileCamouflageBound;

public class BlockEtherealBoundCorrupted extends BlockEtherealCorrupted {

    public BlockEtherealBoundCorrupted(Material material) {
        super(material);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister ir) {
        this.blockIcon = ir.registerIcon(Sanguimancy.texturePath + ":BoundEtherealCorruptedBlock");
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLivingBase, ItemStack stack) {
        if (entityLivingBase instanceof EntityPlayer) {
            TileCamouflageBound tile = (TileCamouflageBound) world.getTileEntity(x, y, z);
            tile.addOwnerToList(entityLivingBase.getCommandSenderName());
        }
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileCamouflageBound();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        TileCamouflageBound tile = (TileCamouflageBound) world.getTileEntity(x, y, z);
        if (!tile.getOwnersList().contains(player.getCommandSenderName())) {
            return false;
        }
        return super.onBlockActivated(world, x, y, z, player, p_149727_6_, p_149727_7_, p_149727_8_, p_149727_9_);
    }

    @Override
    public void onBlockExploded(World world, int x, int y, int z, Explosion explosion) {
    }
}
