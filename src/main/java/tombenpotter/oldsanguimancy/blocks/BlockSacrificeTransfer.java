package tombenpotter.oldsanguimancy.blocks;

import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import tombenpotter.oldsanguimancy.Sanguimancy;
import tombenpotter.oldsanguimancy.api.soulCorruption.SoulCorruptionHelper;
import tombenpotter.oldsanguimancy.client.particle.EntityColoredFlameFX;
import tombenpotter.oldsanguimancy.items.ItemPlayerSacrificer;
import tombenpotter.oldsanguimancy.tile.TileSacrificeTransfer;
import tombenpotter.sanguimancy.util.RandomUtils;
import tombenpotter.sanguimancy.util.SanguimancyItemStacks;

import java.util.Random;

public class BlockSacrificeTransfer extends BlockContainer {

    public BlockSacrificeTransfer(Material material) {
        super(material);
        setHardness(5.0F);
        setCreativeTab(Sanguimancy.tabSanguimancy);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister ir) {
        this.blockIcon = ir.registerIcon(Sanguimancy.texturePath + ":SacrificeTransferrer");
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileSacrificeTransfer();
    }


    @Override
    public void breakBlock(World world, int x, int y, int z, Block par5, int par6) {
        RandomUtils.dropItems(world, x, y, z);
        world.removeTileEntity(x, y, z);
        super.breakBlock(world, x, y, z, par5, par6);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        TileSacrificeTransfer tile = (TileSacrificeTransfer) world.getTileEntity(x, y, z);
        if (player.getHeldItem() == null && tile.getStackInSlot(0) != null) {
            ItemStack stack = tile.getStackInSlot(0);
            tile.setInventorySlotContents(0, null);
            player.inventory.addItemStackToInventory(stack);
            world.markBlockForUpdate(x, y, z);
        } else if (player.getHeldItem() != null && tile.getStackInSlot(0) == null) {
            ItemStack stack = player.getHeldItem().copy();
            stack.stackSize = 1;
            tile.setInventorySlotContents(0, stack);
            if (!player.capabilities.isCreativeMode) {
                for (int i = 0; i < stack.stackSize; i++)
                    player.inventory.consumeInventoryItem(stack.getItem());
            }
            world.markBlockForUpdate(x, y, z);

        }
        return true;
    }

    @Override
    public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
        if (!world.isRemote) {
            TileSacrificeTransfer tile = (TileSacrificeTransfer) world.getTileEntity(x, y, z);
            if (entity instanceof EntityPlayer && tile.slots[0] != null && (tile.slots[0].isItemEqual(SanguimancyItemStacks.focusedPlayerSacrificer) || tile.slots[0].isItemEqual(SanguimancyItemStacks.wayToDie))) {
                ItemStack stack = tile.slots[0];
                EntityPlayer player = (EntityPlayer) entity;
                RandomUtils.checkAndSetCompound(stack);
                if (stack.stackTagCompound.getString("thiefName").equals(player.getCommandSenderName())) {
                    String owner = player.getCommandSenderName();
                    int currentEssence = SoulNetworkHandler.getCurrentEssence(owner);
                    SoulNetworkHandler.setCurrentEssence(owner, currentEssence + stack.stackTagCompound.getInteger("bloodStolen"));
                    player.setFire(1000);
                    tile.setInventorySlotContents(0, null);
                    SoulCorruptionHelper.addCorruption(player, 2);
                } else if (stack.stackTagCompound.getString("ownerName").equals(player.getCommandSenderName())) {
                    String sacrificed = player.getCommandSenderName();
                    int sacrificedEssence = SoulNetworkHandler.getCurrentEssence(sacrificed);
                    SoulNetworkHandler.setCurrentEssence(sacrificed, sacrificedEssence + stack.stackTagCompound.getInteger("bloodStolen"));
                    tile.setInventorySlotContents(0, null);
                } else {
                    player.addChatComponentMessage(new ChatComponentTranslation(StatCollector.translateToLocal("info.Sanguimancy.tooltip.sacrifice.transfer")));
                    world.spawnEntityInWorld(new EntityLightningBolt(world, x, y, z));
                    player.setFire(100);
                }
            }
        }
        world.markBlockForUpdate(x, y, z);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        TileSacrificeTransfer tile = (TileSacrificeTransfer) world.getTileEntity(x, y, z);
        if (tile.getStackInSlot(0) != null && tile.getStackInSlot(0).getItem() instanceof ItemPlayerSacrificer) {
            for (float i = 0; i <= 7; i += 0.1) {
                EntityFX fire = new EntityColoredFlameFX(world, x - 0.5, y + i, z + 0.5, 0, 0.1, 0, 255, 72, 0);
                Minecraft.getMinecraft().effectRenderer.addEffect(fire);
                EntityFX water = new EntityColoredFlameFX(world, x + 0.5, y + i, z - 0.5, 0, 0.1, 0, 0, 136, 255);
                Minecraft.getMinecraft().effectRenderer.addEffect(water);
                EntityFX air = new EntityColoredFlameFX(world, x + 1.5, y + i, z + 0.5, 0, 0.1, 0, 153, 204, 204);
                Minecraft.getMinecraft().effectRenderer.addEffect(air);
                EntityFX earth = new EntityColoredFlameFX(world, x + 0.5, y + i, z + 1.5, 0, 0.1, 0, 40, 166, 32);
                Minecraft.getMinecraft().effectRenderer.addEffect(earth);
                EntityFX darkness = new EntityColoredFlameFX(world, x + 0.5, y + i, z + 0.5, 0, -0.2, 0, 0, 0, 0);
                Minecraft.getMinecraft().effectRenderer.addEffect(darkness);
            }
        }
    }
}
