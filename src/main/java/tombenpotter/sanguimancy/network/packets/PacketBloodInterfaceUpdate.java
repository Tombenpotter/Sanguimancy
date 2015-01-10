package tombenpotter.sanguimancy.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import tombenpotter.sanguimancy.network.MessageHelper;
import tombenpotter.sanguimancy.tile.TileBloodInterface;

public class PacketBloodInterfaceUpdate implements IMessage {
    public int posX, posY, posZ, itemID = -1, itemDamage = -1;
    public byte[] bytes = new byte[0];

    public PacketBloodInterfaceUpdate() {
    }

    public PacketBloodInterfaceUpdate(TileBloodInterface tileBloodInterface) {
        this.posX = tileBloodInterface.xCoord;
        this.posY = tileBloodInterface.yCoord;
        this.posZ = tileBloodInterface.zCoord;
        ItemStack stack = tileBloodInterface.getStackInSlot(0);
        if (stack != null) {
            itemID = Item.getIdFromItem(stack.getItem());
            itemDamage = stack.getItemDamage();
            if (stack.hasTagCompound() && stack.getTagCompound().hasKey("ownerName")) {
                bytes = MessageHelper.stringToByteArray(stack.getTagCompound().getString("ownerName"));
            }
        }
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        this.posX = buf.readInt();
        this.posY = buf.readInt();
        this.posZ = buf.readInt();
        try {
            this.itemID = buf.readInt();
            this.itemDamage = buf.readInt();
            buf.readBytes(bytes);
        } catch (IndexOutOfBoundsException e) {
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.posX);
        buf.writeInt(this.posY);
        buf.writeInt(this.posZ);
        if (this.itemID >= 0) {
            buf.writeInt(this.itemID);
            buf.writeInt(this.itemDamage);
            if (bytes.length > 0) buf.writeBytes(bytes);
        }
    }
}
