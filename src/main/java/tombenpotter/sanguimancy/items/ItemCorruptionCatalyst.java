package tombenpotter.sanguimancy.items;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.api.soulCorruption.SoulCorruptionHelper;
import tombenpotter.sanguimancy.network.events.EventCorruptedInfusion;
import tombenpotter.sanguimancy.recipes.RecipeCorruptedInfusion;
import tombenpotter.sanguimancy.util.RandomUtils;

import java.util.List;

public class ItemCorruptionCatalyst extends Item {

    public ItemCorruptionCatalyst() {
        setCreativeTab(Sanguimancy.creativeTab);
        setUnlocalizedName(Sanguimancy.modid + ".corruptionCatalist");
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ri) {
        this.itemIcon = ri.registerIcon(Sanguimancy.texturePath + ":CorruptionCatalyst");
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5) {
        RandomUtils.checkAndSetCompound(stack);
        if (!stack.stackTagCompound.hasKey("activated")) {
            stack.stackTagCompound.setBoolean("activated", false);
        } else if (stack.stackTagCompound.getBoolean("activated")) {
            if (entity != null && entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entity;
                if (player.getHeldItem(EnumHand.MAIN_HAND) != null && RecipeCorruptedInfusion.isRecipeValid(new ItemStack[]{player.getHeldItem(EnumHand.MAIN_HAND)}, SoulCorruptionHelper.getCorruptionLevel(player))) {
                    ItemStack[] input = new ItemStack[]{player.getHeldItem(EnumHand.MAIN_HAND).copy()};
                    RecipeCorruptedInfusion recipe = RecipeCorruptedInfusion.getPossibleRecipes(input, SoulCorruptionHelper.getCorruptionLevel(player)).get(0);
                    ItemStack output = recipe.fOutput.copy();
                    for (ItemStack inputStack : recipe.fInput) {
                        if (world.getWorldTime() % recipe.fTime == 0) {
                            EventCorruptedInfusion.EventPlayerCorruptedInfusion event = new EventCorruptedInfusion.EventPlayerCorruptedInfusion(player, recipe);
                            RandomUtils.fireEvent(event);
                            if (event.isCancelable() && !event.isCanceled()) {
                                for (int i = 0; i < inputStack.getCount(); i++) {
                                    player.inventory.consumeInventoryItem(inputStack.getItem());
                                }
                                if (!player.inventory.addItemStackToInventory(output)) {
                                    RandomUtils.dropItemStackInWorld(world, player.posX, player.posY, player.posZ, output);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        RandomUtils.checkAndSetCompound(stack);
        if (!stack.stackTagCompound.hasKey("activated")) {
            stack.stackTagCompound.setBoolean("activated", false);
        }
        stack.stackTagCompound.setBoolean("activated", !stack.stackTagCompound.getBoolean("activated"));
        return stack;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean hasEffect(ItemStack stack) {
        if (stack.hasTagCompound() && stack.stackTagCompound.hasKey("activated")) {
            return stack.stackTagCompound.getBoolean("activated");
        }
        return false;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean par4) {
        if (!GuiScreen.isShiftKeyDown()) {
            list.add(I18n.format("info.Sanguimancy.tooltip.shift.info"));
        } else {
            if (stack.hasTagCompound() && stack.stackTagCompound.hasKey("activated")) {
                list.add(I18n.format("info.Sanguimancy.tooltip.activated") + ": " + stack.stackTagCompound.getBoolean("activated"));
            }
            list.add(I18n.format("info.Sanguimancy.tooltip.corrupted.infusion.1"));
            list.add(I18n.format("info.Sanguimancy.tooltip.corrupted.infusion.2"));
        }
    }
}