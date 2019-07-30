package tombenpotter.sanguimancy.items;

import WayofTime.bloodmagic.teleport.TeleportQueue;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.util.ConfigHandler;

public class ItemSoulTransporter extends Item {
    public ItemSoulTransporter() {
        setCreativeTab(Sanguimancy.creativeTab);
        setUnlocalizedName(Sanguimancy.modid + ".soulTransporter");
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ri) {
        this.itemIcon = ri.registerIcon(Sanguimancy.texturePath + ":SoulTransporter");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn) {
        if (!world.isRemote) {
            if (player.world.provider.dimensionId != 0) {
                ChunkCoordinates chunkCoords = MinecraftServer.getServer().worldServerForDimension(0).getSpawnPoint();
                chunkCoords.posY = MinecraftServer.getServer().worldServerForDimension(0).getTopSolidOrLiquidBlock(chunkCoords.posX, chunkCoords.posZ);
                TeleportQueue.getInstance().teleportToDim(world, 0, chunkCoords.posX, chunkCoords.posY, chunkCoords.posZ, player, player.getCommandSenderName());
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
                TeleportQueue.getInstance().teleportToDim(world, dimID, x, 6, z, player, player.getCommandSenderName());
            }
            player.inventory.consumeInventoryItem(this);
        }
        return stack;
    }
}