package tombenpotter.sanguimancy.rituals;

import WayofTime.alchemicalWizardry.api.rituals.IMasterRitualStone;
import WayofTime.alchemicalWizardry.api.rituals.RitualComponent;
import WayofTime.alchemicalWizardry.api.rituals.RitualEffect;
import WayofTime.alchemicalWizardry.api.soulNetwork.LifeEssenceNetwork;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import WayofTime.alchemicalWizardry.common.spell.complex.effect.SpellHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class RitualEffectPlacer extends RitualEffect {

    @Override
    public void performEffect(IMasterRitualStone ritualStone) {
        String owner = ritualStone.getOwner();
        World worldSave = MinecraftServer.getServer().worldServers[0];
        LifeEssenceNetwork data = (LifeEssenceNetwork) worldSave.loadItemData(LifeEssenceNetwork.class, owner);
        World world = ritualStone.getWorld();
        int x = ritualStone.getXCoord();
        int y = ritualStone.getYCoord();
        int z = ritualStone.getZCoord();
        TileEntity tile = world.getTileEntity(x, y + 1, z);
        IInventory tileEntity;
        if (data == null) {
            data = new LifeEssenceNetwork(owner);
            worldSave.setItemData(owner, data);
        }

        if (tile instanceof IInventory) {
            tileEntity = (IInventory) tile;
        } else {
            return;
        }
        if (tileEntity.getSizeInventory() <= 0) {
            return;
        }

        int currentEssence = data.currentEssence;

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
                        if (world.getBlock(i, y, k).isReplaceable(world, i, y, k) && tileEntity.getStackInSlot(inv) != null) {
                            if (tileEntity.getStackInSlot(inv).getItem() instanceof ItemBlock) {
                                world.setBlock(i, y, k, Block.getBlockFromItem(tileEntity.getStackInSlot(inv).getItem()), tileEntity.getStackInSlot(inv).getItemDamage(), 3);
                                if (tileEntity.getStackInSlot(inv).stackSize > 1) {
                                    tileEntity.getStackInSlot(inv).stackSize = tileEntity.getStackInSlot(inv).stackSize - 1;
                                } else tileEntity.setInventorySlotContents(inv, null);
                                data.currentEssence = currentEssence - this.getCostPerRefresh();
                            }
                        }
                    }
                }
            }
            data.markDirty();
        }
    }

    @Override
    public int getCostPerRefresh() {
        return 10;
    }

    @Override
    public List<RitualComponent> getRitualComponentList() {
        ArrayList<RitualComponent> placingRitual = new ArrayList();
        placingRitual.add(new RitualComponent(0, -1, 0, RitualComponent.DUSK));
        placingRitual.add(new RitualComponent(1, -1, 0, RitualComponent.DUSK));
        placingRitual.add(new RitualComponent(0, -1, 1, RitualComponent.DUSK));
        placingRitual.add(new RitualComponent(-1, -1, 0, RitualComponent.DUSK));
        placingRitual.add(new RitualComponent(0, -1, -1, RitualComponent.DUSK));
        placingRitual.add(new RitualComponent(1, -1, 1, RitualComponent.DUSK));
        placingRitual.add(new RitualComponent(-1, -1, -1, RitualComponent.DUSK));
        placingRitual.add(new RitualComponent(-1, -1, 1, RitualComponent.DUSK));
        placingRitual.add(new RitualComponent(1, -1, -1, RitualComponent.DUSK));
        return placingRitual;
    }
}
