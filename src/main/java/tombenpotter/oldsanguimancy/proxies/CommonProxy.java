package tombenpotter.oldsanguimancy.proxies;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import tombenpotter.oldsanguimancy.Sanguimancy;
import tombenpotter.oldsanguimancy.api.snManifestation.ISNComponent;
import tombenpotter.oldsanguimancy.container.ContainerLumpCleaner;
import tombenpotter.oldsanguimancy.gui.GuiLumpCleaner;
import tombenpotter.oldsanguimancy.tile.TileBloodCleaner;

import java.util.Calendar;

public class CommonProxy implements IGuiHandler {

    public void preLoad() {
    }

    public void load() {
        Calendar calendar = Calendar.getInstance();
        Sanguimancy.isAprilFools = calendar.get(Calendar.MONTH) == Calendar.APRIL && calendar.get(Calendar.DAY_OF_MONTH) == 1;

        registerRenders();
    }

    public void postLoad() {
    }

    public void registerRenders() {
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
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
        TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
        ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND);
        switch (ID) {
            case 0:
                if (tile != null && tile instanceof TileBloodCleaner) {
                    return new GuiLumpCleaner(player, (TileBloodCleaner) tile);
                }
                /*
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
                */
            default:
                return null;
        }
    }

    public EntityPlayer getClientPlayer() {
        return null;
    }

    public void addLinkingEffects(ISNComponent component, World world, double x, double y, double z) {
    }

    public void addColoredFlameEffects(World world, double x, double y, double z, double movX, double movY, double movZ, int red, int green, int blue) {
    }

    public World getClientWorld() {
        return null;
    }
}
