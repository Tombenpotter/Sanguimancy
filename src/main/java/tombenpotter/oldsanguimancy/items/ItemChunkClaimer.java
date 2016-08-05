package tombenpotter.oldsanguimancy.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import tombenpotter.oldsanguimancy.Sanguimancy;
import tombenpotter.oldsanguimancy.util.ChunkIntPairSerializable;
import tombenpotter.oldsanguimancy.util.ConfigHandler;
import tombenpotter.oldsanguimancy.util.singletons.ClaimedChunks;

public class ItemChunkClaimer extends Item {

    public ItemChunkClaimer() {
        setCreativeTab(Sanguimancy.tabSanguimancy);
        setUnlocalizedName(Sanguimancy.modid + ".chunkClaimer");
        setMaxStackSize(16);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ri) {
        this.itemIcon = ri.registerIcon(Sanguimancy.texturePath + ":ChunkClaimer");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            if (player.worldObj.provider.dimensionId == ConfigHandler.snDimID) {
                ChunkIntPairSerializable chunkIntPairSerializable = new ChunkIntPairSerializable((int) player.posX >> 4, (int) player.posZ >> 4);
                if (ClaimedChunks.getClaimedChunks().addLocation(player.getCommandSenderName(), chunkIntPairSerializable)) {
                    String text = StatCollector.translateToLocal("chat.Sanguimancy.successfully.claimed");
                    text.replace("%x", String.valueOf(chunkIntPairSerializable.getCenterXPos()));
                    text.replace("%z", String.valueOf(chunkIntPairSerializable.getCenterZPos()));
                    text.replace("%d", String.valueOf(ConfigHandler.snDimID));
                    player.addChatComponentMessage(new ChatComponentText(text));
                    player.inventory.consumeInventoryItem(this);
                } else {
                    player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("chat.Sanguimancy.unsuccessfully.claimed")));
                }
            } else {
                player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("chat.Sanguimancy.not.sn.dim")));
            }
        }
        return stack;
    }
}
