package tombenpotter.sanguimancy.network;

import tombenpotter.sanguimancy.tile.TileBloodInterface;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class BloodInterfaceUpdateMessage implements IMessage, IMessageHandler<BloodInterfaceUpdateMessage, IMessage>
{
    public int posX,posY,posZ, itemID = -1, itemDamage = -1;
    public byte[] bytes = new byte[0];

    public BloodInterfaceUpdateMessage()
    {
    }

    public BloodInterfaceUpdateMessage(TileBloodInterface tileBloodInterface)
    {
        this.posX = tileBloodInterface.xCoord;
        this.posY = tileBloodInterface.yCoord;
        this.posZ = tileBloodInterface.zCoord;
        ItemStack stack = tileBloodInterface.getStackInSlot(0);
        if (stack!=null)
        {
            itemID = Item.getIdFromItem(stack.getItem());
            itemDamage = stack.getItemDamage();
            if (stack.hasTagCompound() && stack.getTagCompound().hasKey("ownerName"))
            {
                bytes = MessageHelper.stringToByeArray(stack.getTagCompound().getString("ownerName"));
            }
        }
    }


    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.posX = buf.readInt();
        this.posY = buf.readInt();
        this.posZ = buf.readInt();
        try
        {
            this.itemID = buf.readInt();
            this.itemDamage = buf.readInt();
            buf.readBytes(bytes);
        }
        catch (IndexOutOfBoundsException e)
        {}
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.posX);
        buf.writeInt(this.posY);
        buf.writeInt(this.posZ);
        if (this.itemID>=0)
        {
            buf.writeInt(this.itemID);
            buf.writeInt(this.itemDamage);
            if (bytes.length>0) buf.writeBytes(bytes);
        }

    }

    @Override
    public IMessage onMessage(BloodInterfaceUpdateMessage message, MessageContext ctx)
    {
        TileEntity tileEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.posX, message.posY, message.posZ);
        if (tileEntity instanceof TileBloodInterface)
        {
            ItemStack stack = null;
            if (message.itemID!=-1)
            {
                stack = new ItemStack(Item.getItemById(message.itemID),1,message.itemDamage);
                if (message.bytes.length>0)
                {
                    stack.stackTagCompound = new NBTTagCompound();
                    stack.stackTagCompound.setString("ownerName",MessageHelper.byteArrayToString(message.bytes));
                }
            }
            ((TileBloodInterface) tileEntity).setInventorySlotContents(0,stack);
        }
        return null;

    }
}
