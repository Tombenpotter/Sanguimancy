package tombenpotter.sanguimancy.compat;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import tombenpotter.sanguimancy.blocks.BlockAltarEmitter;
import tombenpotter.sanguimancy.tile.TileAltarEmitter;

import java.util.List;

public class WailaAltarEmitter implements IWailaDataProvider {

    public static void register(IWailaRegistrar registrar) {
        registrar.registerBodyProvider(new WailaAltarEmitter(), BlockAltarEmitter.class);
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
        boolean isEmitter = accessor.getTileEntity() instanceof TileAltarEmitter;
        if (isEmitter) {
            TileAltarEmitter tile = (TileAltarEmitter) accessor.getTileEntity();
            currenttip.add(StatCollector.translateToLocal("compat.waila.blood.required") + ": " + String.valueOf(tile.bloodAsked));
        }
        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }
}
