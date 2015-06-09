package tombenpotter.sanguimancy.compat.waila;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.blocks.BlockBloodTank;
import tombenpotter.sanguimancy.tile.TileBloodTank;
import tombenpotter.sanguimancy.util.RandomUtils;

import java.util.List;

public class WailaBloodTank implements IWailaDataProvider {

    public static void register(IWailaRegistrar registrar) {
        registrar.registerBodyProvider(new WailaBloodTank(), BlockBloodTank.class);
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
        boolean isTank = accessor.getTileEntity() instanceof TileBloodTank;
        if (isTank) {
            TileBloodTank tank = (TileBloodTank) accessor.getTileEntity();
            if (tank.tank.getFluid() != null) {
                currenttip.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.fluid") + ": " + RandomUtils.capitalizeFirstLetter(tank.tank.getFluid().getLocalizedName()));
                currenttip.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.amount") + ": " + tank.tank.getFluidAmount() + "/" + tank.tank.getCapacity() + "mB");
            }
        }
        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z) {
        return tag;

    }
}
