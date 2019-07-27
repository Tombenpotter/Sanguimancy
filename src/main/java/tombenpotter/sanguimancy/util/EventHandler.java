package tombenpotter.sanguimancy.util;

import WayofTime.bloodmagic.api.util.helper.NetworkHelper;
import WayofTime.bloodmagic.api.util.helper.PlayerHelper;
import amerifrance.guideapi.api.GuideRegistry;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.api.soulCorruption.SoulCorruptionHelper;
import tombenpotter.sanguimancy.registry.ItemsRegistry;
import tombenpotter.sanguimancy.registry.SanguimancyGuide;

public class EventHandler {

    public EventHandler() {
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Sanguimancy.modid)) {
            ConfigHandler.syncConfig();
            Sanguimancy.logger.info(I18n.format("info." + Sanguimancy.modid + ".console.config.refresh"));
        }
    }

    @SubscribeEvent
    public void onPlayerSacrificed(LivingDeathEvent event) {
        if (event.getEntity() != null && !event.getEntity().world.isRemote) {
            if (event.getEntity() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) event.getEntity();

                if (event.getSource().getEntity() != null && event.getSource().getEntity() instanceof EntityPlayer) {
                    String owner = PlayerHelper.getUUIDFromPlayer(player).toString();
                    EntityPlayer perpetrator = (EntityPlayer) event.getSource().getEntity();
                    ItemStack attunedStack = new ItemStack(ItemsRegistry.playerSacrificer, 1, 1);

                    if (perpetrator.inventory.hasItemStack(attunedStack)) {
                        perpetrator.inventory.deleteStack(attunedStack);
                        ItemStack focusedStack = new ItemStack(ItemsRegistry.playerSacrificer, 1, 2);
                        focusedStack.getTagCompound().setString("ownerUUID", owner);
                        focusedStack.getTagCompound().setInteger("bloodStolen", NetworkHelper.getSoulNetwork(player).getCurrentEssence());
                        focusedStack.getTagCompound().setString("thiefUUID", PlayerHelper.getUUIDFromPlayer(perpetrator).toString());
                        perpetrator.inventory.addItemStackToInventory(focusedStack);
                        NetworkHelper.getSoulNetwork(player).setCurrentEssence(0);
                        SoulCorruptionHelper.incrementCorruption(player);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerJoinWorld(EntityJoinWorldEvent event) {
        if (event.getEntity() != null && !event.getEntity().world.isRemote && event.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            NBTTagCompound tag = RandomUtils.getModTag(player, Sanguimancy.modid);

            if (!tag.getBoolean("hasInitialGuide") && ConfigHandler.addItemsOnFirstLogin) {
                if (!player.inventory.addItemStackToInventory(GuideRegistry.getItemStackForBook(SanguimancyGuide.sanguimancyGuide).copy())) {
                    RandomUtils.dropItemStackInWorld(player.world, player.posX, player.posY, player.posZ, (GuideRegistry.getItemStackForBook(SanguimancyGuide.sanguimancyGuide).copy()));
                }
                tag.setBoolean("hasInitialGuide", true);
            }
        }
    }

    @SubscribeEvent
    public void onChunkForce(ForgeChunkManager.ForceChunkEvent event) {
        if (!Loader.isModLoaded("loaderlist")) {
            RandomUtils.writeLog(event.getTicket().getModId() + " forcing the loading of the chunk at x= " + String.valueOf(event.getLocation().getCenterXPos()) + " and z=" + String.valueOf(event.getLocation().getCenterZPosition()) + " in dimension " + String.valueOf(event.getTicket().world.provider.getDimension()), "ChunkloadingLog.txt");
        }
    }

    @SubscribeEvent
    public void onChunkUnforce(ForgeChunkManager.UnforceChunkEvent event) {
        if (!Loader.isModLoaded("loaderlist")) {
            RandomUtils.writeLog(event.getTicket().getModId() + " unforcing the loading of the chunk at x= " + String.valueOf(event.getLocation().getCenterXPos()) + " and z=" + String.valueOf(event.getLocation().getCenterZPosition()) + " in dimension " + String.valueOf(event.getTicket().world.provider.getDimension()), "ChunkloadingLog.txt");
        }
    }

    /*
    @SubscribeEvent
    public void onDigWithCorruptedTool(net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed event) {
        if (event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND) != null) {
            ItemStack stack = event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND);
            if (stack.getItem() == ItemsRegistry.corruptedAxe || stack.getItem() == ItemsRegistry.corruptedShovel || stack.getItem() == ItemsRegistry.corruptedPickaxe) {
                int corruption = SoulCorruptionHelper.getCorruptionLevel(event.getEntityPlayer());
                event.setNewSpeed(event.getOriginalSpeed() * (corruption / ConfigHandler.minimumToolCorruption));
            }
        }
    }
    */

    public static class ClientEventHandler {

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        //This code is very much inspired by the one in ProfMobius' Waila mod
        public void onSanguimancyItemTooltip(ItemTooltipEvent event) {
            ItemStack stack = event.getItemStack();

            if (stack != null) {
                ResourceLocation id = Item.REGISTRY.getNameForObject(stack.getItem());
                if (id != null && id.getResourceDomain().equals(Sanguimancy.modid) && stack.getTagCompound() != null && stack.getTagCompound().hasKey("ownerName")) {
                    if (GuiScreen.isShiftKeyDown()) {
                        event.getToolTip().add(I18n.format("info.Sanguimancy.tooltip.owner") + ": " + stack.getTagCompound().getString("ownerName"));
                    }
                }
            }
        }
    }
}
