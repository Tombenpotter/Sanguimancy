package tombenpotter.sanguimancy.compat.waila;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import tombenpotter.sanguimancy.blocks.BlockAltarDiviner;
import tombenpotter.sanguimancy.tile.TileAltarDiviner;

import java.util.List;

public class WailaAltarDiviner implements IWailaDataProvider {

    public static void register(IWailaRegistrar registrar) {
        registrar.registerBodyProvider(new WailaAltarDiviner(), BlockAltarDiviner.class);
    }

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        boolean isDiviner = accessor.getTileEntity() instanceof TileAltarDiviner;
        if (isDiviner) {
            TileAltarDiviner tile = (TileAltarDiviner) accessor.getTileEntity();
            if (tile.getStackInSlot(0) != null) {
                currenttip.add(StatCollector.translateToLocal("compat.waila.content") + ": " + tile.getStackInSlot(0).getDisplayName());
                currenttip.add(StatCollector.translateToLocal("compat.waila.stacksize") + ": " + String.valueOf(tile.getStackInSlot(0).stackSize));
            }
        }
        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }
}
