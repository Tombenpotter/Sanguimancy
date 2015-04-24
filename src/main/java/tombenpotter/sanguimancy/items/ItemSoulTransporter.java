package tombenpotter.sanguimancy.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.util.ConfigHandler;
import tombenpotter.sanguimancy.util.singletons.ClaimedChunks;
import tombenpotter.sanguimancy.util.teleporting.TeleportingQueue;

public class ItemSoulTransporter extends Item {

    public ItemSoulTransporter() {
        setCreativeTab(Sanguimancy.tabSanguimancy);
        setUnlocalizedName(Sanguimancy.modid + ".soulTransporter");
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ri) {
        this.itemIcon = ri.registerIcon(Sanguimancy.texturePath + ":SoulTransporter");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            if (player.worldObj.provider.dimensionId != 0) {
                ChunkCoordinates chunkCoords = MinecraftServer.getServer().worldServerForDimension(0).getSpawnPoint();
                chunkCoords.posY = MinecraftServer.getServer().worldServerForDimension(0).getTopSolidOrLiquidBlock(chunkCoords.posX, chunkCoords.posZ);
                TeleportingQueue.getInstance().teleportToDim(world, 0, chunkCoords.posX, chunkCoords.posY, chunkCoords.posZ, player, player.getCommandSenderName());
            } else {
                int dimID = ConfigHandler.snDimID;
                int x, z;
                if (ClaimedChunks.getClaimedChunks().getLinkedChunks(player.getCommandSenderName()) == null || ClaimedChunks.getClaimedChunks().getLinkedChunks(player.getCommandSenderName()).isEmpty()) {
                    ChunkCoordinates chunkCoords = MinecraftServer.getServer().worldServerForDimension(dimID).getSpawnPoint();
                    x = chunkCoords.posX;
                    z = chunkCoords.posZ;
                } else if (ClaimedChunks.getClaimedChunks().getLinkedChunks(player.getCommandSenderName()).get(0) != null) {
                    x = ClaimedChunks.getClaimedChunks().getLinkedChunks(player.getCommandSenderName()).get(0).getCenterXPos();
                    z = ClaimedChunks.getClaimedChunks().getLinkedChunks(player.getCommandSenderName()).get(0).getCenterZPos();
                } else {
                    ChunkCoordinates chunkCoords = MinecraftServer.getServer().worldServerForDimension(dimID).getSpawnPoint();
                    x = chunkCoords.posX;
                    z = chunkCoords.posZ;
                }
                TeleportingQueue.getInstance().teleportToDim(world, dimID, x, 6, z, player, player.getCommandSenderName());
            }
            player.inventory.consumeInventoryItem(this);
        }
        return stack;
    }
}
