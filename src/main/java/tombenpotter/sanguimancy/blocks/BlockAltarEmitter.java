package tombenpotter.sanguimancy.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.tile.TileAltarEmitter;

public class BlockAltarEmitter extends BlockContainer {

    public BlockAltarEmitter(Material material) {
        super(material);
        setHardness(5.0F);
        setCreativeTab(Sanguimancy.tabSanguimancy);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister ri) {
        this.blockIcon = ri.registerIcon(Sanguimancy.texturePath + ":AltarEmitter");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int var2) {
        return new TileAltarEmitter();
    }

    @Override
    public boolean canProvidePower() {
        return true;
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess access, int x, int y, int z, int p_149709_5_) {
        if (access.getTileEntity(x, y, z) != null && access.getTileEntity(x, y, z) instanceof TileAltarEmitter) {
            TileAltarEmitter tile = (TileAltarEmitter) access.getTileEntity(x, y, z);
            if (tile.isOverBloodAsked)
                return 15;
        }
        return 0;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof TileAltarEmitter && !world.isRemote) {
            TileAltarEmitter tile = (TileAltarEmitter) world.getTileEntity(x, y, z);
	        if (!player.isSneaking()) {
		        tile.bloodAsked += 100;
		        player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("chat.Sanguimancy.blood.required") + ": " + String.valueOf(tile.bloodAsked)));
		        world.notifyBlocksOfNeighborChange(x, y, z, this);
	        } else if (tile.bloodAsked >= 100) {
		        tile.bloodAsked -= 100;
		        player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("chat.Sanguimancy.blood.required") + ": " + String.valueOf(tile.bloodAsked)));
		        world.notifyBlocksOfNeighborChange(x, y, z, this);
	        }
        }
        return true;
    }
}
