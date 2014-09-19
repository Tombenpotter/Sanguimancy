package tombenpotter.sanguimancy.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockIllusion extends ItemBlock {

    public ItemBlockIllusion(Block block) {
        super(block);
        setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        String name = "";
        switch (stack.getItemDamage()) {
            default:
                name = "nothing";
            case 0: {
                name = "diamond.ore";
                break;
            }
            case 1: {
                name = "diamond.block";
                break;
            }
            case 2: {
                name = "glowstone";
                break;
            }
            case 3: {
                name = "netherrack";
                break;
            }
            case 4: {
                name = "quartz.ore";
                break;
            }
            case 5: {
                name = "end.stone";
                break;
            }
            case 6: {
                name = "wool.pink";
                break;
            }
            case 7: {
                name = "lava.flow";
                break;
            }
            case 8: {
                name = "pumpkin.on";
                break;
            }
            case 9: {
                name = "bedrock";
                break;
            }
            case 10: {
                name = "obsidian";
                break;
            }
            case 11: {
                name = "glass";
                break;
            }
            case 12: {
                name = "snow";
                break;
            }
            case 13: {
                name = "melon";
                break;
            }
            case 14: {
                name = "gold.block";
                break;
            }
            case 15: {
                name = "clay";
                break;
            }
        }
        return getUnlocalizedName() + "." + name;
    }

    @Override
    public int getMetadata(int par1) {
        return par1;
    }
}
