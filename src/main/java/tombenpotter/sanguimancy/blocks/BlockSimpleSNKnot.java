package tombenpotter.sanguimancy.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.tile.TileSimpleSNKnot;
import tombenpotter.sanguimancy.util.BlockPostition;
import tombenpotter.sanguimancy.util.interfaces.ISNComponent;

public class BlockSimpleSNKnot extends BlockContainer {

    public BlockSimpleSNKnot(Material material) {
        super(material);
        setHardness(5.0F);
        setCreativeTab(Sanguimancy.tabSanguimancy);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileSimpleSNKnot();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister ri) {
        this.blockIcon = ri.registerIcon(Sanguimancy.texturePath + ":SimpleSNKnot");
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {
        ISNComponent tile = (ISNComponent) world.getTileEntity(x, y, z);
        if (!tile.getComponentsInNetwork().hashMap.isEmpty()) {
            for (BlockPostition postition : tile.getComponentsInNetwork().hashMap.keySet()) {
                if (!world.isAirBlock(postition.x, postition.y, postition.z)) {
                    world.markBlockForUpdate(postition.x, postition.y, postition.z);
                }
            }
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack stack) {
        if (living != null && living instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) living;
            TileSimpleSNKnot tile = (TileSimpleSNKnot) world.getTileEntity(x, y, z);
            tile.setSNKnotOwner(player.getCommandSenderName());
        }
    }
}
