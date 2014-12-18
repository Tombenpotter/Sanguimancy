package tombenpotter.sanguimancy.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.api.BlockPostition;
import tombenpotter.sanguimancy.api.snManifestation.ISNComponent;
import tombenpotter.sanguimancy.tile.TileRitualSNPart;

import java.util.Random;

public class BlockRitualSNPart extends BlockContainer {

    public BlockRitualSNPart(Material material) {
        super(material);
        setHardness(20.0F);
        setCreativeTab(Sanguimancy.tabSanguimancy);
        setBlockUnbreakable();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister ir) {
        this.blockIcon = ir.registerIcon(Sanguimancy.texturePath + ":SNRitualPart");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return new TileRitualSNPart();
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return null;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {
        ISNComponent branch = (ISNComponent) world.getTileEntity(x, y, z);
        if (!branch.getComponentsInNetwork().hashMap.isEmpty()) {
            for (BlockPostition postition : branch.getComponentsInNetwork().hashMap.keySet()) {
                ISNComponent component = (ISNComponent) postition.getTile(world);
                component.onNetworkUpdate(new BlockPostition(x, y, z));
            }
        }
    }
}
