package tombenpotter.oldsanguimancy.rituals;

import WayofTime.alchemicalWizardry.api.rituals.IMasterRitualStone;
import WayofTime.alchemicalWizardry.api.rituals.RitualComponent;
import WayofTime.alchemicalWizardry.api.rituals.RitualEffect;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import WayofTime.alchemicalWizardry.common.spell.complex.effect.SpellHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class RitualEffectTrash extends RitualEffect {

    @Override
    public void performEffect(IMasterRitualStone ritualStone) {
        String owner = ritualStone.getOwner();
        World world = ritualStone.getWorld();
        int x = ritualStone.getXCoord();
        int y = ritualStone.getYCoord();
        int z = ritualStone.getZCoord();
        int currentEssence = SoulNetworkHandler.getCurrentEssence(owner);
        if (currentEssence < this.getCostPerRefresh()) {
            EntityPlayer entityOwner = SpellHelper.getPlayerForUsername(owner);
            if (entityOwner == null) {
                return;
            }
            SoulNetworkHandler.causeNauseaToPlayer(owner);
        } else {
            List<EntityItem> itemDropList = SpellHelper.getItemsInRange(world, x + 0.5f, y + 0.5f, z + 0.5f, 2, 2);
            if (itemDropList != null) {
                for (EntityItem itemEntity : itemDropList) {
                    int stacksize = 1;
                    if (itemEntity.getEntityItem().stackSize > 0) stacksize = itemEntity.getEntityItem().stackSize;
                    itemEntity.setDead();
                    SoulNetworkHandler.syphonFromNetwork(owner, getCostPerRefresh() * stacksize);
                }
            }
        }
    }

    @Override
    public int getCostPerRefresh() {
        return 2;
    }

    @Override
    public List<RitualComponent> getRitualComponentList() {
        ArrayList<RitualComponent> trashRitual = new ArrayList();
        trashRitual.add(new RitualComponent(1, 0, 0, RitualComponent.DUSK));
        trashRitual.add(new RitualComponent(-1, 0, 0, RitualComponent.DUSK));
        trashRitual.add(new RitualComponent(0, 0, 1, RitualComponent.DUSK));
        trashRitual.add(new RitualComponent(0, 0, -1, RitualComponent.DUSK));
        trashRitual.add(new RitualComponent(1, 1, 0, RitualComponent.DUSK));
        trashRitual.add(new RitualComponent(-1, 1, 0, RitualComponent.DUSK));
        trashRitual.add(new RitualComponent(0, 1, -1, RitualComponent.DUSK));
        trashRitual.add(new RitualComponent(0, 1, 1, RitualComponent.DUSK));
        return trashRitual;
    }
}
