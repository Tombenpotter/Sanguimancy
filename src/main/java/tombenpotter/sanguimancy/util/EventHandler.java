package tombenpotter.sanguimancy.util;

import WayofTime.alchemicalWizardry.api.soulNetwork.LifeEssenceNetwork;
import WayofTime.alchemicalWizardry.common.items.EnergyItems;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.registry.ItemsRegistry;

public class EventHandler {

    public int spawningDelay;

    public EventHandler() {
        spawningDelay = 50;
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        if (event.entity != null && !event.entity.worldObj.isRemote) {
            if (event.entity instanceof EntityPlayer) {

                EntityPlayer player = (EntityPlayer) event.entity;
                String owner = player.getCommandSenderName();
                World worldSave = MinecraftServer.getServer().worldServers[0];
                LifeEssenceNetwork data = (LifeEssenceNetwork) worldSave.loadItemData(LifeEssenceNetwork.class, owner);
                int currentEssence = data.currentEssence;

                if (data == null) {
                    data = new LifeEssenceNetwork(owner);
                    worldSave.setItemData(owner, data);
                }

                if (event.source.getEntity() != null && event.source.getEntity() instanceof EntityPlayer) {
                    EntityPlayer perpetrator = (EntityPlayer) event.source.getEntity();
                    ItemStack attunedStack = new ItemStack(ItemsRegistry.playerSacrificer, 1, 1);
                    if (perpetrator.inventory.hasItemStack(attunedStack)) {
                        perpetrator.inventory.consumeInventoryItem(attunedStack.getItem());

                        ItemStack focusedStack = new ItemStack(ItemsRegistry.playerSacrificer, 1, 2);
                        EnergyItems.checkAndSetItemOwner(focusedStack, owner);
                        focusedStack.stackTagCompound.setInteger("bloodStolen", currentEssence);
                        focusedStack.stackTagCompound.setString("thiefName", perpetrator.getCommandSenderName());
                        perpetrator.inventory.addItemStackToInventory(focusedStack);

                        data.currentEssence = 0;
                        data.markDirty();
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onJoinWorld(EntityJoinWorldEvent event) {
        if (!event.entity.worldObj.isRemote && event.entity != null && event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;
            NBTTagCompound tag = SoulCorruptionHelper.getModTag(player, Sanguimancy.modid);
            if (SoulCorruptionHelper.getCorruptionLevel(tag) > 0) return;
            else SoulCorruptionHelper.negateCorruption(tag);
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        NBTTagCompound tag = SoulCorruptionHelper.getModTag(event.player, Sanguimancy.modid);
        if (SoulCorruptionHelper.getCorruptionLevel(tag) >= 25) {
            if (spawningDelay <= 0) {
                SoulCorruptionHelper.spawnChickenFollower(event.player);
                spawningDelay = 50000;
            } else {
                spawningDelay--;
            }
        }
    }
}
