package tombenpotter.sanguimancy.items;

import WayofTime.alchemicalWizardry.ModItems;
import WayofTime.alchemicalWizardry.common.items.EnergyItems;
import WayofTime.alchemicalWizardry.common.tileEntity.TESpellParadigmBlock;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.util.RandomUtils;

import java.util.List;

public class ItemWand extends Item {

    public int maxSpells = 3;

    public ItemWand() {
        setCreativeTab(Sanguimancy.tabSanguimancy);
        setUnlocalizedName(Sanguimancy.modid + ".spellWand");
        setHasSubtypes(true);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ri) {
        this.itemIcon = ri.registerIcon(Sanguimancy.texturePath + ":Wand");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        EnergyItems.checkAndSetItemOwner(stack, player);
        if (player.isSneaking()) {
            nextSpell(stack);
            return stack;
        }
        if (player.inventory.hasItemStack(new ItemStack(ModItems.itemComplexSpellCrystal))) {
            setSpell(stack, player);
            return stack;
        }
        if (!player.isSneaking()) {
            shootSpell(world, player, stack);
            player.swingItem();
        }
        world.playSoundAtEntity(player, "random.fizz", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        return stack;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        if (stack.stackTagCompound != null && stack.stackTagCompound.getCompoundTag("spell" + String.valueOf(stack.stackTagCompound.getInteger("currentSpell"))) != null) {
            NBTTagCompound itemTag = stack.stackTagCompound.getCompoundTag("spell" + String.valueOf(stack.stackTagCompound.getInteger("currentSpell")));
            if (!GuiScreen.isShiftKeyDown()) {
                list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.spell.current") + ": " + String.valueOf(stack.stackTagCompound.getInteger("currentSpell") + 1));
                list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.shift.info"));
            } else {
                list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.spell.current") + ": " + String.valueOf(stack.stackTagCompound.getInteger("currentSpell") + 1));
                list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.coordinates") + ": " + itemTag.getInteger("xCoord") + ", " + itemTag.getInteger("yCoord") + ", " + itemTag.getInteger("zCoord"));
                list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.bound.dimension") + ": " + getDimensionID(itemTag));
            }
        }
    }

    public int getDimensionID(NBTTagCompound tag) {
        return tag.getInteger("dimensionId");
    }

    public void setSpell(ItemStack stack, EntityPlayer player) {
        for (int i = 0; i < player.inventory.mainInventory.length; ++i) {
            if (player.inventory.mainInventory[i] != null && player.inventory.mainInventory[i].isItemEqual(new ItemStack(ModItems.itemComplexSpellCrystal))) {
                ItemStack crystal = player.inventory.getStackInSlot(i);
                RandomUtils.checkAndSetCompound(stack);
                RandomUtils.checkAndSetCompound(crystal);
                NBTTagCompound stackTag = stack.stackTagCompound;
                NBTTagCompound crystalTag = crystal.stackTagCompound;
                crystalTag.setString("spellName", crystal.getDisplayName());
                stackTag.setTag("spell" + String.valueOf(stack.stackTagCompound.getInteger("currentSpell")), crystalTag.copy());
                player.inventory.setInventorySlotContents(i, null);
            }
        }
    }

    public void nextSpell(ItemStack stack) {
        RandomUtils.checkAndSetCompound(stack);
        if (stack.stackTagCompound.getInteger("currentSpell") != maxSpells) {
            stack.stackTagCompound.setInteger("currentSpell", stack.stackTagCompound.getInteger("currentSpell") + 1);
        } else {
            stack.stackTagCompound.setInteger("currentSpell", 0);
        }
    }

    public void shootSpell(World world, EntityPlayer player, ItemStack stack) {
        if (!world.isRemote) {
            if (stack.stackTagCompound != null && stack.stackTagCompound.getCompoundTag("spell" + String.valueOf(stack.stackTagCompound.getInteger("currentSpell"))) != null) {
                NBTTagCompound itemTag = stack.stackTagCompound.getCompoundTag("spell" + String.valueOf(stack.stackTagCompound.getInteger("currentSpell")));
                World paradWorld = MinecraftServer.getServer().worldServerForDimension(getDimensionID(itemTag));
                TileEntity tileEntity = paradWorld.getTileEntity(itemTag.getInteger("xCoord"), itemTag.getInteger("yCoord"), itemTag.getInteger("zCoord"));
                if (tileEntity instanceof TESpellParadigmBlock) {
                    TESpellParadigmBlock tileParad = (TESpellParadigmBlock) tileEntity;
                    tileParad.castSpell(world, player, stack);
                }
            }
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (stack.hasTagCompound() && stack.stackTagCompound.getCompoundTag("spell" + String.valueOf(stack.stackTagCompound.getInteger("currentSpell"))) != null) {
            NBTTagCompound itemTag = stack.stackTagCompound.getCompoundTag("spell" + String.valueOf(stack.stackTagCompound.getInteger("currentSpell")));
            if (itemTag.hasKey("spellName")) {
                return super.getItemStackDisplayName(stack) + ": " + itemTag.getString("spellName");
            }
        }
        return super.getItemStackDisplayName(stack);
    }

    @Override
    public boolean isFull3D() {
        return true;
    }
}