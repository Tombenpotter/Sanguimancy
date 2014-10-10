package tombenpotter.sanguimancy.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.registry.RecipeCorruption;
import tombenpotter.sanguimancy.util.SoulCorruptionHelper;

public class ItemCorruptionCatalyst extends Item {

    public ItemCorruptionCatalyst() {
        setCreativeTab(Sanguimancy.tabSanguimancy);
        setUnlocalizedName(Sanguimancy.modid + ".corruptionCatalist");
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ri) {
        this.itemIcon = ri.registerIcon(Sanguimancy.texturePath + ":CorruptionCatalyst");
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5) {
        if (entity instanceof EntityPlayer && entity != null) {
            EntityPlayer player = (EntityPlayer) entity;
            NBTTagCompound tag = SoulCorruptionHelper.getModTag(player, Sanguimancy.modid);
            if (player.getHeldItem() != null && RecipeCorruption.getOutput(player.getHeldItem()) != null && player.getHeldItem().stackSize >= RecipeCorruption.getRecipeFromStack(player.getHeldItem()).getInput().stackSize) {
                ItemStack input = player.getHeldItem().copy();
                ItemStack output = RecipeCorruption.getOutput(input);
                if (world.rand.nextInt(RecipeCorruption.getRecipeFromStack(input).getChance()) == 0 &&
                        SoulCorruptionHelper.isCorruptionOver(tag, RecipeCorruption.getRecipeFromStack(input).getMiniumCorruption())) {
                    for (int i = 0; i < RecipeCorruption.getRecipeFromStack(input).getInput().stackSize; i++) {
                        player.inventory.consumeInventoryItem(input.getItem());
                    }
                    if (!player.inventory.addItemStackToInventory(output)) {
                        dropItemStackInWorld(world, player.posX, player.posY, player.posZ, output);
                    }
                }
            }
        }
    }

    public static EntityItem dropItemStackInWorld(World worldObj, double x, double y, double z, ItemStack stack) {
        float f = 0.7F;
        float d0 = worldObj.rand.nextFloat() * f + (1.0F - f) * 0.5F;
        float d1 = worldObj.rand.nextFloat() * f + (1.0F - f) * 0.5F;
        float d2 = worldObj.rand.nextFloat() * f + (1.0F - f) * 0.5F;
        EntityItem entityitem = new EntityItem(worldObj, x + d0, y + d1, z + d2, stack);
        entityitem.delayBeforeCanPickup = 1;
        if (stack.hasTagCompound()) {
            entityitem.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
        }
        worldObj.spawnEntityInWorld(entityitem);
        return entityitem;
    }
}
