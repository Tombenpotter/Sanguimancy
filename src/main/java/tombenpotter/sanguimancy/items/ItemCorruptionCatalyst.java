package tombenpotter.sanguimancy.items;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
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

import javax.annotation.Nullable;

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
        if (!stack.getTagCompound().hasKey("activated")) {
            stack.getTagCompound().setBoolean("activated", false);
        } else if (stack.getTagCompound().getBoolean("activated")) {
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
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
    	ItemStack stack = player.getHeldItemMainhand();
        RandomUtils.checkAndSetCompound(stack);
        if (!stack.getTagCompound().hasKey("activated")) {
            stack.getTagCompound().setBoolean("activated", false);
        }
        stack.getTagCompound().setBoolean("activated", !stack.getTagCompound().getBoolean("activated"));
        return stack;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean hasEffect(ItemStack stack) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("activated")) {
            return stack.getTagCompound().getBoolean("activated");
        }
        return false;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (!GuiScreen.isShiftKeyDown()) {
            tooltip.add(I18n.format("info.Sanguimancy.tooltip.shift.info"));
        } else {
            if (stack.hasTagCompound() && stack.getTagCompound().hasKey("activated")) {
            	tooltip.add(I18n.format("info.Sanguimancy.tooltip.activated") + ": " + stack.getTagCompound().getBoolean("activated"));
            }
            tooltip.add(I18n.format("info.Sanguimancy.tooltip.corrupted.infusion.1"));
            tooltip.add(I18n.format("info.Sanguimancy.tooltip.corrupted.infusion.2"));
        }
    }
}