package tombenpotter.sanguimancy.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.util.SoulCorruptionHelper;
import tombenpotter.sanguimancy.util.TeleportingUtils;

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
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            if (player.worldObj.provider.dimensionId != 0) {
                ChunkCoordinates chunkCoords = MinecraftServer.getServer().worldServerForDimension(0).getSpawnPoint();
                chunkCoords.posY = MinecraftServer.getServer().worldServerForDimension(0).getTopSolidOrLiquidBlock(chunkCoords.posX, chunkCoords.posZ);
                TeleportingUtils.teleportEntityToDim(world, 0, chunkCoords.posX, chunkCoords.posY, chunkCoords.posZ, player, player.getCommandSenderName());
            } else {
                NBTTagCompound tag = SoulCorruptionHelper.getModTag(player, Sanguimancy.modid);
                int dimID = tag.getInteger("SoulNetworkMainfestationDimID");
                ChunkCoordinates chunkCoords = MinecraftServer.getServer().worldServerForDimension(dimID).getSpawnPoint();
                TeleportingUtils.teleportEntityToDim(world, dimID, chunkCoords.posX, 6, chunkCoords.posZ, player, player.getCommandSenderName());
            }
        }
        return stack;
    }
}
