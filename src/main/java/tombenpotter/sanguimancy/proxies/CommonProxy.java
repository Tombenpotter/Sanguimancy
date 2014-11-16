package tombenpotter.sanguimancy.proxies;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.container.ContainerLumpCleaner;
import tombenpotter.sanguimancy.gui.GuiLumpCleaner;
import tombenpotter.sanguimancy.tile.TileBloodCleaner;

public class CommonProxy implements IGuiHandler {

    public void load() {
        registerRenders();
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
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        switch (ID) {
            case 0:
                if (tile != null && tile instanceof TileBloodCleaner) {
                    return new GuiLumpCleaner(player, (TileBloodCleaner) tile);
                }
            default:
                return null;
        }
    }
}
