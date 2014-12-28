package tombenpotter.sanguimancy.util;

import WayofTime.alchemicalWizardry.common.spell.complex.effect.SpellHelper;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.network.EventCorruptedInfusion;

public class HolidaysBitsAndBobsEventHandler {

    @SubscribeEvent
    public void onPlayerCorruptedInfusionAesthetic(EventCorruptedInfusion.EventPlayerCorruptedInfusion event) {
        double posX = event.entityPlayer.posX;
        double posY = event.entityPlayer.posY;
        double posZ = event.entityPlayer.posZ;
        event.entityPlayer.worldObj.playSoundEffect((double) ((float) posX + 0.5F), (double) ((float) posY + 0.5F), (double) ((float) posZ + 0.5F), "random.fizz", 0.5F, 2.6F + (event.entityPlayer.worldObj.rand.nextFloat() - event.entityPlayer.worldObj.rand.nextFloat()) * 0.8F);
        SpellHelper.sendIndexedParticleToAllAround(event.entityPlayer.worldObj, posX, posY, posZ, 20, event.entityPlayer.worldObj.provider.dimensionId, 4, posX, posY, posZ);
    }

    @SubscribeEvent
    //This code is very much inspired by the one in ProfMobius' Waila mod
    public void onSanguimancyItemTooltip(ItemTooltipEvent event) {
        try {
            ModContainer mod = GameData.findModOwner(GameData.itemRegistry.getNameForObject(event.itemStack.getItem()));
            String modname = mod == null ? "Minecraft" : mod.getName();
            if (modname.equals(Sanguimancy.name) && event.itemStack.stackTagCompound != null && event.itemStack.stackTagCompound.hasKey("ownerName")) {
                if (GuiScreen.isShiftKeyDown()) {
                    event.toolTip.add((StatCollector.translateToLocal("info.Sanguimancy.tooltip.owner") + ": " + RandomUtils.getItemOwner(event.itemStack)));
                }
            }
        } catch (NullPointerException e) {
            Sanguimancy.logger.info("No mod found for this item");
        }
    }
}
