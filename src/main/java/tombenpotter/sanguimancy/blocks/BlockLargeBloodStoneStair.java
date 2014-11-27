package tombenpotter.sanguimancy.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockLargeBloodStoneStair extends BlockStairs {

    public BlockLargeBloodStoneStair(Block block, int var2) {
        super(block, var2);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister ir) {
        this.blockIcon = ir.registerIcon("AlchemicalWizardry:LargeBloodStoneBrick");
    }
}
