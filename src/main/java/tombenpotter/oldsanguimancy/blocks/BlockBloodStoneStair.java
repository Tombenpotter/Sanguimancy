package tombenpotter.oldsanguimancy.blocks;

import WayofTime.alchemicalWizardry.ModBlocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.util.IIcon;
import tombenpotter.oldsanguimancy.Sanguimancy;

public class BlockBloodStoneStair extends BlockStairs {

    public BlockBloodStoneStair(Block block, int var2) {
        super(block, var2);
        setHardness(5.0F);
        setCreativeTab(Sanguimancy.tabSanguimancy);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int par1, int par2) {
        return ModBlocks.bloodStoneBrick.getIcon(par1, par2);
    }
}
