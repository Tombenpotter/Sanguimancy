package tombenpotter.oldsanguimancy.rituals;

import WayofTime.alchemicalWizardry.ModBlocks;
import WayofTime.alchemicalWizardry.api.rituals.IMasterRitualStone;
import WayofTime.alchemicalWizardry.api.rituals.RitualComponent;
import WayofTime.alchemicalWizardry.api.rituals.RitualEffect;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import WayofTime.alchemicalWizardry.common.spell.complex.effect.SpellHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class RitualEffectLighting extends RitualEffect {

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
            for (int i = 0; i < 10; i++) {
                SpellHelper.sendIndexedParticleToAllAround(world, x, y, z, 20, world.provider.dimensionId, 3, x, y, z);
            }

            int i = x + world.rand.nextInt(15) - world.rand.nextInt(15);
            int j = y + world.rand.nextInt(15) - world.rand.nextInt(15);
            int k = z + world.rand.nextInt(15) - world.rand.nextInt(15);
            if (j > world.getHeightValue(i, k))
                j = world.getHeightValue(i, k);

            if ((world.isAirBlock(i, j, k)) && (world.getBlockLightValue(i, j, k) < 10)) {
                world.setBlockToAir(i, j, k);
                world.setBlock(i, j, k, ModBlocks.blockBloodLight);
                SoulNetworkHandler.syphonFromNetwork(owner, getCostPerRefresh());
            }
        }
    }

    @Override
    public int getCostPerRefresh() {
        return 10;
    }

    @Override
    public List<RitualComponent> getRitualComponentList() {
        ArrayList<RitualComponent> lightingRitual = new ArrayList();
        lightingRitual.add(new RitualComponent(1, -1, 1, RitualComponent.AIR));
        lightingRitual.add(new RitualComponent(-1, -1, 1, RitualComponent.AIR));
        lightingRitual.add(new RitualComponent(-1, -1, -1, RitualComponent.AIR));
        lightingRitual.add(new RitualComponent(1, -1, -1, RitualComponent.AIR));
        lightingRitual.add(new RitualComponent(1, 0, 0, RitualComponent.FIRE));
        lightingRitual.add(new RitualComponent(-1, 0, 0, RitualComponent.FIRE));
        lightingRitual.add(new RitualComponent(0, 0, 1, RitualComponent.FIRE));
        lightingRitual.add(new RitualComponent(0, 0, -1, RitualComponent.FIRE));
        lightingRitual.add(new RitualComponent(1, 1, 1, RitualComponent.AIR));
        lightingRitual.add(new RitualComponent(-1, 1, 1, RitualComponent.AIR));
        lightingRitual.add(new RitualComponent(-1, 1, -1, RitualComponent.AIR));
        lightingRitual.add(new RitualComponent(1, 1, -1, RitualComponent.AIR));
        return lightingRitual;
    }
}
