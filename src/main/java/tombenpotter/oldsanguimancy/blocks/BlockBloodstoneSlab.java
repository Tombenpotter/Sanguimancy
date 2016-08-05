package tombenpotter.oldsanguimancy.blocks;

import WayofTime.alchemicalWizardry.ModBlocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;
import tombenpotter.oldsanguimancy.Sanguimancy;

public class BlockBloodstoneSlab extends BlockSlab {

    public BlockBloodstoneSlab(boolean bool, Material material) {
        super(bool, material);
        setHardness(5.0F);
        setCreativeTab(Sanguimancy.tabSanguimancy);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int par1, int par2) {
        return ModBlocks.bloodStoneBrick.getIcon(par1, par2);
    }

    @Override
    public String func_150002_b(int p_150002_1_) {
        return "BloodstoneSlab";
    }
}
