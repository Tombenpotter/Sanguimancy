package tombenpotter.sanguimancy.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.tile.TileIllusion;

import java.util.List;

public class BlockIllusion extends BlockContainer {

    public IIcon[] icon = new IIcon[16];

    public BlockIllusion(Material material) {
        super(material);
        this.setBlockUnbreakable();
        this.setResistance(10000F);
        this.setCreativeTab(Sanguimancy.tabSanguimancy);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ir) {
        this.icon[0] = ir.registerIcon("diamond_ore");
        this.icon[1] = ir.registerIcon("diamond_block");
        this.icon[2] = ir.registerIcon("glowstone");
        this.icon[3] = ir.registerIcon("netherrack");
        this.icon[4] = ir.registerIcon("quartz_ore");
        this.icon[5] = ir.registerIcon("end_stone");
        this.icon[6] = ir.registerIcon("wool_colored_pink");
        this.icon[7] = ir.registerIcon("lava_flow");
        this.icon[8] = ir.registerIcon("nether_brick");
        this.icon[9] = ir.registerIcon("bedrock");
        this.icon[10] = ir.registerIcon("obsidian");
        this.icon[11] = ir.registerIcon("glass");
        this.icon[12] = ir.registerIcon("snow");
        this.icon[13] = ir.registerIcon("melon_side");
        this.icon[14] = ir.registerIcon("gold_block");
        this.icon[15] = ir.registerIcon("clay");
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item id, CreativeTabs tab, List list) {
        for (int i = 0; i < 16; i++) list.add(new ItemStack(id, 1, i));
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileIllusion();
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return icon[meta];
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }
}
