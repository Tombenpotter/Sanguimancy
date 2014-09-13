package tombenpotter.sanguimancy.blocks;

import WayofTime.alchemicalWizardry.AlchemicalWizardry;
import WayofTime.alchemicalWizardry.api.soulNetwork.LifeEssenceNetwork;
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
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.client.particle.EntityColoredFlameFX;
import tombenpotter.sanguimancy.registry.ItemsRegistry;
import tombenpotter.sanguimancy.tile.TileSacrificeTransfer;

import java.util.Random;

public class BlockSacrificeTransfer extends BlockContainer {

    public BlockSacrificeTransfer(Material material) {
        super(material);
        setHardness(5.0F);
        setCreativeTab(AlchemicalWizardry.tabBloodMagic);
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
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        TileSacrificeTransfer tile = (TileSacrificeTransfer) world.getTileEntity(x, y, z);
        if (player.getHeldItem() == null && tile.getStackInSlot(0) != null) {
            ItemStack stack = tile.getStackInSlot(0);
            tile.setInventorySlotContents(0, null);
            player.inventory.addItemStackToInventory(stack);
            world.markBlockForUpdate(x, y, z);
        } else if (player.getHeldItem() != null && tile.getStackInSlot(0) == null) {
            ItemStack stack = player.getHeldItem().copy();
            stack.stackSize = player.getHeldItem().stackSize;
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
            if (entity instanceof EntityPlayer && tile.slots[0] != null && tile.slots[0].getItem().equals(new ItemStack(ItemsRegistry.playerSacrificer, 1, 2))) {
                ItemStack stack = tile.slots[0];
                EntityPlayer player = (EntityPlayer) entity;
                if (stack.stackTagCompound.getString("thiefName").equals(player.getCommandSenderName())) {
                    String owner = player.getCommandSenderName();
                    World worldSave = MinecraftServer.getServer().worldServers[0];
                    LifeEssenceNetwork data = (LifeEssenceNetwork) worldSave.loadItemData(LifeEssenceNetwork.class, owner);
                    int currentEssence = data.currentEssence;

                    if (data == null) {
                        data = new LifeEssenceNetwork(owner);
                        worldSave.setItemData(owner, data);
                    }

                    data.currentEssence = currentEssence + stack.stackTagCompound.getInteger("bloodStolen");
                    data.markDirty();
                    player.setFire(1000);
                    player.addPotionEffect(new PotionEffect(Potion.wither.id, 3000, 500));
                    player.addPotionEffect(new PotionEffect(Potion.confusion.id, 1, 500));
                    tile.setInventorySlotContents(0, null);
                } else if (stack.stackTagCompound.getString("ownerName").equals(player.getCommandSenderName())) {
                    String sacrificed = player.getCommandSenderName();
                    World worldSave = MinecraftServer.getServer().worldServers[0];
                    LifeEssenceNetwork sacrificedData = (LifeEssenceNetwork) worldSave.loadItemData(LifeEssenceNetwork.class, sacrificed);
                    int sacrificedEssence = sacrificedData.currentEssence;

                    if (sacrificedData == null) {
                        sacrificedData = new LifeEssenceNetwork(sacrificed);
                        worldSave.setItemData(sacrificed, sacrificedData);
                    }

                    sacrificedData.currentEssence = sacrificedEssence + stack.stackTagCompound.getInteger("bloodStolen");
                    sacrificedData.markDirty();
                    tile.setInventorySlotContents(0, null);
                } else {
                    player.addChatComponentMessage(new ChatComponentText("Do not have your dirty work done by others!"));
                    world.spawnEntityInWorld(new EntityLightningBolt(world, x, y, z));
                    player.setFire(100);
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        TileSacrificeTransfer tile = (TileSacrificeTransfer) world.getTileEntity(x, y, z);
        if (tile.spewFire) {
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

    @Override
    public void breakBlock(World world, int x, int y, int z, Block par5, int par6) {
        dropItems(world, x, y, z);
        world.removeTileEntity(x, y, z);
        super.breakBlock(world, x, y, z, par5, par6);
    }

    private void dropItems(World world, int x, int y, int z) {
        Random rand = new Random();

        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (!(tileEntity instanceof IInventory)) {
            return;
        }
        IInventory inventory = (IInventory) tileEntity;

        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack item = inventory.getStackInSlot(i);

            if (item != null && item.stackSize > 0) {
                float rx = rand.nextFloat() * 0.8F + 0.1F;
                float ry = rand.nextFloat() * 0.8F + 0.1F;
                float rz = rand.nextFloat() * 0.8F + 0.1F;

                EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z + rz, new ItemStack(item.getItem(), item.stackSize, item.getItemDamage()));

                if (item.hasTagCompound()) {
                    entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
                }

                float factor = 0.05F;
                entityItem.motionX = rand.nextGaussian() * factor;
                entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                entityItem.motionZ = rand.nextGaussian() * factor;
                world.spawnEntityInWorld(entityItem);
                item.stackSize = 0;
            }
        }
    }
}
