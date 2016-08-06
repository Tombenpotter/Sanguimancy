package tombenpotter.sanguimancy.network.packets;


import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import tombenpotter.oldsanguimancy.tile.TileBloodInterface;
import tombenpotter.sanguimancy.network.MessageHelper;

public class PacketBloodInterfaceUpdate implements IMessage {
    public BlockPos pos;
    public ResourceLocation itemName = null;
    public int itemDamage;
    public byte[] bytes = new byte[0];

    public PacketBloodInterfaceUpdate() {
    }

    public PacketBloodInterfaceUpdate(TileBloodInterface tileBloodInterface) {
        this.pos = tileBloodInterface.pos;
        ItemStack stack = tileBloodInterface.getStackInSlot(0);
        if (stack != null) {
            itemName = stack.getItem().getRegistryName();
            itemDamage = stack.getItemDamage();
            if (stack.hasTagCompound() && stack.getTagCompound().hasKey("ownerName")) {
                bytes = MessageHelper.stringToByteArray(stack.getTagCompound().getString("ownerName"));
            }
        }
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        this.pos = BlockPos.fromLong(buf.readLong());

        try {
            this.itemName = new ResourceLocation(ByteBufUtils.readUTF8String(buf));
            this.itemDamage = buf.readInt();
            buf.readBytes(bytes);
        } catch (IndexOutOfBoundsException e) {
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());

        if (this.itemName != null) {
            ByteBufUtils.writeUTF8String(buf, itemName.toString());
            buf.writeInt(this.itemDamage);
            if (bytes.length > 0)
                buf.writeBytes(bytes);
        }
    }
}
