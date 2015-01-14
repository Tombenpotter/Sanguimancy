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
import tombenpotter.sanguimancy.util.TeleportingUtils;
import tombenpotter.sanguimancy.util.singletons.ClaimedChunks;

public class ItemCorruptedDemonShard extends Item {

    public ItemCorruptedDemonShard() {
        setCreativeTab(Sanguimancy.tabSanguimancy);
        setUnlocalizedName(Sanguimancy.modid + ".corruptedDemonShard");
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ri) {
        this.itemIcon = ri.registerIcon(Sanguimancy.texturePath + ":CorruptedDemonShard");
    }

    @Override
    //TODO: Remove that testing code
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            if (player.worldObj.provider.dimensionId != 0) {
                ChunkCoordinates chunkCoords = MinecraftServer.getServer().worldServerForDimension(0).getSpawnPoint();
                chunkCoords.posY = MinecraftServer.getServer().worldServerForDimension(0).getTopSolidOrLiquidBlock(chunkCoords.posX, chunkCoords.posZ);
                TeleportingUtils.teleportEntityToDim(world, 0, chunkCoords.posX, chunkCoords.posY, chunkCoords.posZ, player, player.getCommandSenderName());
            } else {
                int dimID = ConfigHandler.snDimID;
                int x = ClaimedChunks.getClaimedChunks().getLinkedChunks(player.getCommandSenderName()).get(0).getCenterXPos();
                int z = ClaimedChunks.getClaimedChunks().getLinkedChunks(player.getCommandSenderName()).get(0).getCenterZPos();
                TeleportingUtils.teleportEntityToDim(world, dimID, x, 6, z, player, player.getCommandSenderName());
            }
        }
        return stack;
    }
}