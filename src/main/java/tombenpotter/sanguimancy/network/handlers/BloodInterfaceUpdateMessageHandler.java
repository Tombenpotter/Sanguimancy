package tombenpotter.sanguimancy.network.handlers;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.network.MessageHelper;
import tombenpotter.sanguimancy.network.packets.PacketBloodInterfaceUpdate;
import tombenpotter.sanguimancy.tiles.TileBloodInterface;

public class BloodInterfaceUpdateMessageHandler implements IMessageHandler<PacketBloodInterfaceUpdate, IMessage> {

    @Override
    public IMessage onMessage(PacketBloodInterfaceUpdate message, MessageContext ctx) {
        TileEntity tileEntity = Sanguimancy.proxy.getClientPlayer().world.getTileEntity(message.pos);
        if (tileEntity instanceof TileBloodInterface) {
            ItemStack stack = ItemStack.EMPTY;
            Item item = ForgeRegistries.ITEMS.getValue(message.itemName);
            if (item != null) {
                stack = new ItemStack(item, 1, message.itemDamage);
                if (message.bytes.length > 0) {
                    stack.setTagCompound(new NBTTagCompound());
                    stack.getTagCompound().setString("ownerName", MessageHelper.byteArrayToString(message.bytes));
                }
            }
            ((TileBloodInterface) tileEntity).getInventory(null).setStackInSlot(0, stack);
        }
        return null;

    }
}
