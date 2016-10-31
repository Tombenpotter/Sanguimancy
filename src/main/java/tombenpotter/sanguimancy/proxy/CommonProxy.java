package tombenpotter.sanguimancy.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.container.ContainerLumpCleaner;
import tombenpotter.sanguimancy.gui.GuiLumpCleaner;
import tombenpotter.sanguimancy.tiles.TileBloodCleaner;

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

            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));

        switch (ID) {
            case 0:
                if (tile != null && tile instanceof TileBloodCleaner) {
                    return new GuiLumpCleaner(player, (TileBloodCleaner) tile);
                }

            default:
                return null;
        }
    }

    public EntityPlayer getClientPlayer() {
        return null;
    }

    public void addColoredFlameEffects(World world, double x, double y, double z, double movX, double movY, double movZ, int red, int green, int blue) {
    }

    public World getClientWorld() {
        return null;
    }
}
