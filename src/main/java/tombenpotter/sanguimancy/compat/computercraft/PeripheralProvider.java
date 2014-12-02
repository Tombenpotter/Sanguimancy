package tombenpotter.sanguimancy.compat.computercraft;

import cpw.mods.fml.common.Optional;
import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.tile.TileComputerBase;
import tombenpotter.sanguimancy.util.enums.ModList;

@Optional.Interface(iface = "dan200.computercraft.api.peripheral.IPeripheralProvider", modid = ModList.Names.COMPUTERCRAFT)
public class PeripheralProvider implements IPeripheralProvider {
    public static void register() {
        ComputerCraftAPI.registerPeripheralProvider(new PeripheralProvider());
    }

    @Override
    @Optional.Method(modid = ModList.Names.COMPUTERCRAFT)
    public IPeripheral getPeripheral(World world, int x, int y, int z, int side) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileComputerBase) {
            return (IPeripheral) te;
        }
        return null;
    }
}
