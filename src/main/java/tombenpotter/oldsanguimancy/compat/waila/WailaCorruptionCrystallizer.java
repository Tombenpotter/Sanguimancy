package tombenpotter.oldsanguimancy.compat.waila;

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
import tombenpotter.oldsanguimancy.blocks.BlockCorruptionCrystallizer;
import tombenpotter.oldsanguimancy.tile.TileCorruptionCrystallizer;

import java.util.List;

public class WailaCorruptionCrystallizer implements IWailaDataProvider {

    public static void register(IWailaRegistrar registrar) {
        registrar.registerBodyProvider(new WailaCorruptionCrystallizer(), BlockCorruptionCrystallizer.class);
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
        boolean isCrystallizer = accessor.getTileEntity() instanceof TileCorruptionCrystallizer;
        if (isCrystallizer) {
            TileCorruptionCrystallizer tile = (TileCorruptionCrystallizer) accessor.getTileEntity();
            currenttip.add(StatCollector.translateToLocal("compat.waila.multiblock.formed") + ": " + String.valueOf(tile.multiblockFormed));
            currenttip.add(StatCollector.translateToLocal("compat.waila.corruption.stored") + ": " + String.valueOf(tile.corruptionStored));
            currenttip.add(StatCollector.translateToLocal("compat.waila.owner") + ": " + tile.owner);
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