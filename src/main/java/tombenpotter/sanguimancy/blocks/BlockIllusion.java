package tombenpotter.sanguimancy.blocks;

import WayofTime.bloodmagic.block.base.BlockStringContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tombenpotter.oldsanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.tiles.TileIllusion;

import java.util.List;

public class BlockIllusion extends BlockStringContainer {

    public static String[] types = {"diamond_ore", "diamond_block", "glowstone", "netherrack", "quartz_ore", "end_stone",
            "wool_colored_pink", "lava_flow", "nether_brick", "bedrock", "obsidian", "glass", "snow", "melon_side",
            "gold_block", "clay"};

    public BlockIllusion(Material material) {
        super(material, types);
        this.setBlockUnbreakable();
        this.setResistance(10000F);
        this.setCreativeTab(Sanguimancy.tabSanguimancy);
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item id, CreativeTabs tab, List list) {
        for (int i = 0; i < 16; i++) list.add(new ItemStack(id, 1, i));
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileIllusion();
    }
}
