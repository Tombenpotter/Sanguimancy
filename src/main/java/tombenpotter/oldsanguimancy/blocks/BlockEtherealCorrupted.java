package tombenpotter.oldsanguimancy.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tombenpotter.oldsanguimancy.Sanguimancy;
import tombenpotter.oldsanguimancy.api.block.BlockCamouflage;
import tombenpotter.oldsanguimancy.api.soulCorruption.SoulCorruptionHelper;
import tombenpotter.oldsanguimancy.api.tile.TileCamouflage;
import tombenpotter.oldsanguimancy.entity.EntityChickenMinion;

import java.util.List;

public class BlockEtherealCorrupted extends BlockCamouflage {

    public BlockEtherealCorrupted(Material material) {
        super(material);
        setHardness(5.0F);
        setCreativeTab(Sanguimancy.tabSanguimancy);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister ir) {
        this.blockIcon = ir.registerIcon(Sanguimancy.texturePath + ":EtherealCorruptedBlock");
    }


    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        TileCamouflage tile = (TileCamouflage) world.getTileEntity(x, y, z);
        if (!world.isRemote && player.getHeldItem() == null) {
            if (!player.isSneaking()) {
                tile.getCustomNBTTag().setInteger("MinimumCorruption", tile.getCustomNBTTag().getInteger("MinimumCorruption") + 1);
            } else if (player.isSneaking() && tile.getCustomNBTTag().getInteger("MinimumCorruption") - 1 >= 0) {
                tile.getCustomNBTTag().setInteger("MinimumCorruption", tile.getCustomNBTTag().getInteger("MinimumCorruption") - 1);
            }
            player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("compat.nei.corrupted.infusion.minimum.corruption") + ": " + String.valueOf(tile.getCustomNBTTag().getInteger("MinimumCorruption"))));
        }
        return super.onBlockActivated(world, x, y, z, player, p_149727_6_, p_149727_7_, p_149727_8_, p_149727_9_);
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List list, Entity entity) {
        if (entity instanceof EntityChickenMinion) return;

        if (entity != null && ((entity instanceof EntityPlayer && !entity.isSneaking()))) {
            EntityPlayer player = (EntityPlayer) entity;
            TileCamouflage tile = (TileCamouflage) world.getTileEntity(x, y, z);
            if (SoulCorruptionHelper.isCorruptionOver(player, tile.getCustomNBTTag().getInteger("MinimumCorruption"))) {
                return;
            } else {
                super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
            }
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
    public int getMobilityFlag() {
        return 2;
    }
}
