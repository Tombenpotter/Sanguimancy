package tombenpotter.sanguimancy.proxies;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.api.bloodutils.api.classes.guide.GuiCategories;
import tombenpotter.sanguimancy.api.bloodutils.api.classes.guide.GuiEntry;
import tombenpotter.sanguimancy.api.bloodutils.api.classes.guide.GuiIndex;
import tombenpotter.sanguimancy.api.bloodutils.api.compact.Category;
import tombenpotter.sanguimancy.api.bloodutils.api.registries.EntryRegistry;
import tombenpotter.sanguimancy.container.ContainerLumpCleaner;
import tombenpotter.sanguimancy.gui.GuiLumpCleaner;
import tombenpotter.sanguimancy.tile.TileBloodCleaner;

public class CommonProxy implements IGuiHandler {

    public void load() {
        registerRenders();
    }

    public void postLoad() {
    }

    public void registerRenders() {
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        switch (ID) {
            case 0:
                if (tile != null && tile instanceof TileBloodCleaner) {
                    return new ContainerLumpCleaner(player, (TileBloodCleaner) tile);
                }
            case 1:
                return null;
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        ItemStack stack = player.getHeldItem();
        switch (ID) {
            case 0:
                if (tile != null && tile instanceof TileBloodCleaner) {
                    return new GuiLumpCleaner(player, (TileBloodCleaner) tile);
                }
            case 1:
                if (stack.hasTagCompound() && stack.getTagCompound().getString("CATEGORY") != null) {
                    if (stack.hasTagCompound() && stack.getTagCompound().getString("KEY") != null && stack.getTagCompound().getString("KEY") != "0") {
                        String cate = stack.getTagCompound().getString("CATEGORY");
                        String key = stack.getTagCompound().getString("KEY");
                        int page = stack.getTagCompound().getInteger("PAGE");
                        if (EntryRegistry.categoryMap.containsKey(cate)) {
                            Category category = EntryRegistry.categoryMap.get(cate);
                            return new GuiEntry(key, player, category, page);
                        } else {
                            return new GuiCategories(player);
                        }
                    } else if (stack.hasTagCompound() && stack.getTagCompound().getString("CATEGORY") != null) {
                        String cate = stack.getTagCompound().getString("CATEGORY");
                        int page = stack.getTagCompound().getInteger("PAGE");
                        if (EntryRegistry.categoryMap.containsKey(cate)) {
                            Category category = EntryRegistry.categoryMap.get(cate);
                            return new GuiIndex(category, player, page);
                        }
                    }
                }
                return new GuiCategories(player);
            default:
                return null;
        }
    }

    public EntityPlayer getClientPlayer() {
        return null;
    }
}
