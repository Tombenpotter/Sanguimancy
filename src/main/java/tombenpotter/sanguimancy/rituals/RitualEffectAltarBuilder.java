package tombenpotter.sanguimancy.rituals;

import WayofTime.alchemicalWizardry.api.rituals.IMasterRitualStone;
import WayofTime.alchemicalWizardry.api.rituals.RitualComponent;
import WayofTime.alchemicalWizardry.api.rituals.RitualEffect;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import WayofTime.alchemicalWizardry.common.block.BloodRune;
import WayofTime.alchemicalWizardry.common.bloodAltarUpgrade.AltarComponent;
import WayofTime.alchemicalWizardry.common.bloodAltarUpgrade.UpgradedAltars;
import WayofTime.alchemicalWizardry.common.spell.complex.effect.SpellHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class RitualEffectAltarBuilder extends RitualEffect {

    @Override
    public void performEffect(IMasterRitualStone ritualStone) {
        String owner = ritualStone.getOwner();
        World world = ritualStone.getWorld();
        int x = ritualStone.getXCoord();
        int y = ritualStone.getYCoord();
        int z = ritualStone.getZCoord();
        TileEntity tile = world.getTileEntity(x, y + 1, z);
        IInventory tileEntity;
        if (tile instanceof IInventory) {
            tileEntity = (IInventory) tile;
        } else {
            return;
        }
        if (tileEntity.getSizeInventory() <= 0) {
            return;
        }
        int currentEssence = SoulNetworkHandler.getCurrentEssence(owner);
        if (currentEssence < this.getCostPerRefresh()) {
            EntityPlayer entityOwner = SpellHelper.getPlayerForUsername(owner);
            if (entityOwner == null) {
                return;
            }
            SoulNetworkHandler.causeNauseaToPlayer(owner);
        } else {
            for (int i = 2; i <= UpgradedAltars.highestAltar; i++) {
                List<AltarComponent> altarComponents = UpgradedAltars.getAltarUpgradeListForTier(i);
                for (AltarComponent altarComponent : altarComponents) {
                    if (!altarComponent.isBloodRune() && hasItem(tileEntity, Item.getItemFromBlock(altarComponent.getBlock()), altarComponent.getMetadata())) {
                        if (world.getBlock(x + altarComponent.getX(), y + 2 + altarComponent.getY(), z + altarComponent.getZ()).isReplaceable(world, x + altarComponent.getX(), y + 2 + altarComponent.getY(), z + altarComponent.getZ())) {
                            world.setBlock(x + altarComponent.getX(), y + 2 + altarComponent.getY(), z + altarComponent.getZ(), altarComponent.getBlock(), altarComponent.getMetadata(), 3);
                            consumeItem(tileEntity, Item.getItemFromBlock(altarComponent.getBlock()), altarComponent.getMetadata());
                            SoulNetworkHandler.syphonFromNetwork(owner, getCostPerRefresh());
                        }
                    } else if (altarComponent.isBloodRune()) {
                        if (world.getBlock(x + altarComponent.getX(), y + 2 + altarComponent.getY(), z + altarComponent.getZ()).isReplaceable(world, x + altarComponent.getX(), y + 2 + altarComponent.getY(), z + altarComponent.getZ())) {
                            int bloodRuneSlot = getBloodRuneSlot(tileEntity);
                            if (bloodRuneSlot != -1) {
                                world.setBlock(x + altarComponent.getX(), y + 2 + altarComponent.getY(), z + altarComponent.getZ(), Block.getBlockFromItem(tileEntity.getStackInSlot(bloodRuneSlot).getItem()), tileEntity.getStackInSlot(bloodRuneSlot).getItemDamage(), 3);
                                consumeItem(tileEntity, tileEntity.getStackInSlot(bloodRuneSlot).getItem(), tileEntity.getStackInSlot(bloodRuneSlot).getItemDamage());
                                SoulNetworkHandler.syphonFromNetwork(owner, getCostPerRefresh());
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public int getCostPerRefresh() {
        return 10;
    }

    @Override
    public List<RitualComponent> getRitualComponentList() {
        ArrayList<RitualComponent> altarBuilderRitual = new ArrayList();
        for (int i = -12; i <= -8; i++) {
            altarBuilderRitual.add(new RitualComponent(i, -6, 13, RitualComponent.AIR));
            altarBuilderRitual.add(new RitualComponent(i, -6, -13, RitualComponent.FIRE));

            altarBuilderRitual.add(new RitualComponent(13, -6, i, RitualComponent.EARTH));
            altarBuilderRitual.add(new RitualComponent(-13, -6, i, RitualComponent.WATER));

            altarBuilderRitual.add(new RitualComponent(i, 5, 13, RitualComponent.AIR));
            altarBuilderRitual.add(new RitualComponent(i, 5, -13, RitualComponent.FIRE));

            altarBuilderRitual.add(new RitualComponent(13, 5, i, RitualComponent.EARTH));
            altarBuilderRitual.add(new RitualComponent(-13, 5, i, RitualComponent.WATER));
        }

        for (int i = 8; i <= 12; i++) {
            altarBuilderRitual.add(new RitualComponent(i, -6, 13, RitualComponent.AIR));
            altarBuilderRitual.add(new RitualComponent(i, -6, -13, RitualComponent.FIRE));

            altarBuilderRitual.add(new RitualComponent(13, -6, i, RitualComponent.EARTH));
            altarBuilderRitual.add(new RitualComponent(-13, -6, i, RitualComponent.WATER));

            altarBuilderRitual.add(new RitualComponent(i, 5, 13, RitualComponent.AIR));
            altarBuilderRitual.add(new RitualComponent(i, 5, -13, RitualComponent.FIRE));

            altarBuilderRitual.add(new RitualComponent(13, 5, i, RitualComponent.EARTH));
            altarBuilderRitual.add(new RitualComponent(-13, 5, i, RitualComponent.WATER));
        }

        for (int i = -6; i <= -4; i++) {
            altarBuilderRitual.add(new RitualComponent(13, i, 13, RitualComponent.DUSK));
            altarBuilderRitual.add(new RitualComponent(-13, i, 13, RitualComponent.DUSK));
            altarBuilderRitual.add(new RitualComponent(13, i, -13, RitualComponent.DUSK));
            altarBuilderRitual.add(new RitualComponent(-13, i, -13, RitualComponent.DUSK));
        }

        for (int i = 3; i <= 5; i++) {
            altarBuilderRitual.add(new RitualComponent(13, i, 13, RitualComponent.DUSK));
            altarBuilderRitual.add(new RitualComponent(-13, i, 13, RitualComponent.DUSK));
            altarBuilderRitual.add(new RitualComponent(13, i, -13, RitualComponent.DUSK));
            altarBuilderRitual.add(new RitualComponent(-13, i, -13, RitualComponent.DUSK));
        }
        return altarBuilderRitual;
    }

    public boolean hasItem(IInventory inv, Item item, int damage) {
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            if (inv.getStackInSlot(i) != null && inv.getStackInSlot(i).getItem() == item && inv.getStackInSlot(i).getItemDamage() == damage)
                return true;
        }
        return false;
    }

    public void consumeItem(IInventory inv, Item item, int damage) {
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            if (inv.getStackInSlot(i) != null && inv.getStackInSlot(i).getItem() == item && inv.getStackInSlot(i).getItemDamage() == damage)
                inv.decrStackSize(i, 1);
        }
    }

    public int getBloodRuneSlot(IInventory inv) {
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            if (inv.getStackInSlot(i) != null && inv.getStackInSlot(i).getItem() instanceof ItemBlock && Block.getBlockFromItem(inv.getStackInSlot(i).getItem()) instanceof BloodRune)
                return i;
        }
        return -1;
    }
}
