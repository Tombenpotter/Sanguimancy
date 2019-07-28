package tombenpotter.sanguimancy.rituals;

import WayofTime.bloodmagic.ritual.IMasterRitualStone;
import WayofTime.bloodmagic.ritual.RitualComponent;
import WayofTime.alchemicalWizardry.api.rituals.RitualEffect;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import WayofTime.alchemicalWizardry.common.spell.complex.effect.SpellHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RitualPlacer extends RitualEffect {

    @Override
    public void performEffect(IMasterRitualStone ritualStone) {
        UUID owner = ritualStone.getOwner();
        World world = ritualStone.getWorldObj();
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
            for (int i = x - 2; i <= x + 2; i++) {
                for (int k = z - 2; k <= z + 2; k++) {
                    for (int inv = 0; inv < tileEntity.getSizeInventory(); inv++) {
                        if (world.getBlock(i, y, k).isReplaceable(world, i, y + 1, k) && tileEntity.getStackInSlot(inv) != null && tileEntity.getStackInSlot(inv).getCount() != 0) {
                            if (tileEntity.getStackInSlot(inv).getItem() instanceof ItemBlock && world.getBlock(i, y - 1, k) != null) {
                                world.setBlock(i, y, k, Block.getBlockFromItem(tileEntity.getStackInSlot(inv).getItem()), tileEntity.getStackInSlot(inv).getItemDamage(), 3);
                                tileEntity.decrStackSize(inv, 1);
                                tileEntity.markDirty();
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
        return 15;
    }

    @Override
    public List<RitualComponent> getRitualComponentList() {
        ArrayList<RitualComponent> placingRitual = new ArrayList<RitualComponent>();
        placingRitual.add(new RitualComponent(3, 0, 3, RitualComponent.EARTH));
        placingRitual.add(new RitualComponent(3, 0, -3, RitualComponent.EARTH));
        placingRitual.add(new RitualComponent(-3, 0, 3, RitualComponent.EARTH));
        placingRitual.add(new RitualComponent(-3, 0, -3, RitualComponent.EARTH));

        placingRitual.add(new RitualComponent(3, 0, 2, RitualComponent.WATER));
        placingRitual.add(new RitualComponent(3, 0, -2, RitualComponent.WATER));
        placingRitual.add(new RitualComponent(2, 0, 3, RitualComponent.WATER));
        placingRitual.add(new RitualComponent(2, 0, -3, RitualComponent.WATER));
        placingRitual.add(new RitualComponent(-2, 0, 3, RitualComponent.WATER));
        placingRitual.add(new RitualComponent(-2, 0, -3, RitualComponent.WATER));
        placingRitual.add(new RitualComponent(-3, 0, -2, RitualComponent.WATER));
        placingRitual.add(new RitualComponent(-3, 0, 2, RitualComponent.WATER));
        return placingRitual;
    }
}