package tombenpotter.oldsanguimancy.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import tombenpotter.oldsanguimancy.Sanguimancy;
import tombenpotter.oldsanguimancy.entity.EntityChickenMinion;
import tombenpotter.oldsanguimancy.tile.TileCamouflageBound;

import java.util.List;

public class BlockEtherealPersonal extends BlockEtherealBound {

    public BlockEtherealPersonal(Material material) {
        super(material);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister ir) {
        this.blockIcon = ir.registerIcon(Sanguimancy.texturePath + ":PersonalEtherealBlock");
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLivingBase, ItemStack stack) {
        if (entityLivingBase instanceof EntityPlayer) {
            TileCamouflageBound tile = (TileCamouflageBound) world.getTileEntity(x, y, z);
            tile.addOwnerToList(entityLivingBase.getCommandSenderName());
        }
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List list, Entity entity) {
        if (entity != null && ((entity instanceof EntityPlayer && !entity.isSneaking()) || entity instanceof EntityChickenMinion)) {
            TileCamouflageBound tile = (TileCamouflageBound) world.getTileEntity(x, y, z);
            if (tile.getOwnersList().contains(entity.getCommandSenderName()))
                return;
            else
                super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
        } else {
            super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
        }
    }
}
