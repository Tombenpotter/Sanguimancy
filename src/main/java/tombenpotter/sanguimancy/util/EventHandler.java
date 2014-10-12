package tombenpotter.sanguimancy.util;

import WayofTime.alchemicalWizardry.api.soulNetwork.LifeEssenceNetwork;
import WayofTime.alchemicalWizardry.common.items.EnergyItems;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import org.lwjgl.input.Keyboard;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.network.PacketHandler;
import tombenpotter.sanguimancy.network.PacketPlayerSearch;
import tombenpotter.sanguimancy.registry.ItemsRegistry;

public class EventHandler {

    public EventHandler() {
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        if (event.entity != null && !event.entity.worldObj.isRemote) {
            if (event.entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) event.entity;
                String owner = player.getCommandSenderName();
                World worldSave = MinecraftServer.getServer().worldServers[0];
                LifeEssenceNetwork data = (LifeEssenceNetwork) worldSave.loadItemData(LifeEssenceNetwork.class, owner);
                if (data == null) {
                    data = new LifeEssenceNetwork(owner);
                    worldSave.setItemData(owner, data);
                }

                int currentEssence = data.currentEssence;
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
                        NBTTagCompound tag = SoulCorruptionHelper.getModTag(player, Sanguimancy.modid);
                        SoulCorruptionHelper.incrementCorruption(tag);
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
        if (SoulCorruptionHelper.isCorruptionOver(tag, 10)) SoulCorruptionHelper.spawnChickenFollower(event.player);
        if (SoulCorruptionHelper.isCorruptionOver(tag, 25)) SoulCorruptionHelper.killGrass(event.player);
        if (SoulCorruptionHelper.isCorruptionOver(tag, 40)) SoulCorruptionHelper.hurtAndHealAnimals(event.player);
        if (SoulCorruptionHelper.isCorruptionOver(tag, 50)) SoulCorruptionHelper.spawnIllusion(event.player);
        if (SoulCorruptionHelper.isCorruptionOver(tag, 70)) SoulCorruptionHelper.randomTeleport(event.player);
    }

    @SubscribeEvent
    public void onPlayerAttack(AttackEntityEvent event) {
        if (event.entityPlayer != null && event.target != null && event.target instanceof EntityLivingBase) {
            NBTTagCompound tag = SoulCorruptionHelper.getModTag(event.entityPlayer, Sanguimancy.modid);
            EntityLivingBase target = (EntityLivingBase) event.target;
            if (SoulCorruptionHelper.isCorruptionOver(tag, 30)) SoulCorruptionHelper.addWither(target);
        }
    }

    public static class ClientEventHandler {
        public static KeyBinding keySearchPlayer = new KeyBinding("key.sanguimancy.search", Keyboard.KEY_F, Sanguimancy.modid);

        public ClientEventHandler() {
            ClientRegistry.registerKeyBinding(keySearchPlayer);
        }

        @SubscribeEvent
        public void onKeyInput(InputEvent.KeyInputEvent event) {
            if (keySearchPlayer.isPressed()) {
                PacketHandler.INSTANCE.sendToServer(new PacketPlayerSearch());
            }
        }
    }
}
