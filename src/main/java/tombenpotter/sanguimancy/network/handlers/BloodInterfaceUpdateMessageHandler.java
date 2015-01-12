package tombenpotter.sanguimancy.network.handlers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.network.packets.PacketBloodInterfaceUpdate;
import tombenpotter.sanguimancy.network.MessageHelper;
import tombenpotter.sanguimancy.tile.TileBloodInterface;

public class BloodInterfaceUpdateMessageHandler implements IMessageHandler<PacketBloodInterfaceUpdate, IMessage> {

    @Override
    public IMessage onMessage(PacketBloodInterfaceUpdate message, MessageContext ctx) {
        TileEntity tileEntity = Sanguimancy.proxy.getClientWorld().getTileEntity(message.posX, message.posY, message.posZ);
        if (tileEntity instanceof TileBloodInterface) {
            ItemStack stack = null;
            if (message.itemID != -1) {
                stack = new ItemStack(Item.getItemById(message.itemID), 1, message.itemDamage);
                if (message.bytes.length > 0) {
                    stack.stackTagCompound = new NBTTagCompound();
                    stack.stackTagCompound.setString("ownerName", MessageHelper.byteArrayToString(message.bytes));
                }
            }
            ((TileBloodInterface) tileEntity).setInventorySlotContents(0, stack);
        }
        return null;

    }
}
