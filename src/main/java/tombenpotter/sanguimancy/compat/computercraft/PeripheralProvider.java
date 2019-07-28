package tombenpotter.sanguimancy.compat.computercraft;

import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import tombenpotter.sanguimancy.api.tiles.TileComputerBase;
import tombenpotter.sanguimancy.util.enums.ModList;

@Optional.Interface(iface = "dan200.computercraft.api.peripheral.IPeripheralProvider", modid = ModList.Names.COMPUTERCRAFT)
public class PeripheralProvider implements IPeripheralProvider {
    public static void register() {
        ComputerCraftAPI.registerPeripheralProvider(new PeripheralProvider());
    }

    @Override
    @Optional.Method(modid = ModList.Names.COMPUTERCRAFT)
    public IPeripheral getPeripheral(World world, BlockPos pos, EnumFacing side) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileComputerBase) {
            return (IPeripheral) te;
        }
        return null;
    }
}
