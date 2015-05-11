package tombenpotter.sanguimancy.rituals;

import WayofTime.alchemicalWizardry.api.alchemy.energy.ReagentRegistry;
import WayofTime.alchemicalWizardry.api.rituals.IMasterRitualStone;
import WayofTime.alchemicalWizardry.api.rituals.RitualComponent;
import WayofTime.alchemicalWizardry.api.rituals.RitualEffect;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import WayofTime.alchemicalWizardry.common.spell.complex.effect.SpellHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class RitualEffectCobble extends RitualEffect {

    public int reagentDrain = 5;

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
            boolean hasTenebrae = this.canDrainReagent(ritualStone, ReagentRegistry.tenebraeReagent, reagentDrain, false);
            if (world.isAirBlock(x + 1, y + 1, z)) {
                if (hasTenebrae) {
                    world.setBlock(x + 1, y + 1, z, Blocks.obsidian, 0, 3);
                    SoulNetworkHandler.syphonFromNetwork(owner, getCostPerRefresh() * 10);
                    this.canDrainReagent(ritualStone, ReagentRegistry.tenebraeReagent, reagentDrain, true);
                } else {
                    world.setBlock(x + 1, y + 1, z, Blocks.cobblestone, 0, 3);
                    SoulNetworkHandler.syphonFromNetwork(owner, getCostPerRefresh());
                }
            }
            if (world.isAirBlock(x - 1, y + 1, z)) {
                if (hasTenebrae) {
                    world.setBlock(x - 1, y + 1, z, Blocks.obsidian, 0, 3);
                    SoulNetworkHandler.syphonFromNetwork(owner, getCostPerRefresh() * 10);
                    this.canDrainReagent(ritualStone, ReagentRegistry.tenebraeReagent, reagentDrain, true);
                } else {
                    world.setBlock(x - 1, y + 1, z, Blocks.cobblestone, 0, 3);
                    SoulNetworkHandler.syphonFromNetwork(owner, getCostPerRefresh());
                }
            }
            if (world.isAirBlock(x, y + 1, z + 1)) {
                if (hasTenebrae) {
                    world.setBlock(x, y + 1, z + 1, Blocks.obsidian, 0, 3);
                    SoulNetworkHandler.syphonFromNetwork(owner, getCostPerRefresh() * 10);
                    this.canDrainReagent(ritualStone, ReagentRegistry.tenebraeReagent, reagentDrain, true);
                } else {
                    world.setBlock(x, y + 1, z + 1, Blocks.cobblestone, 0, 3);
                    SoulNetworkHandler.syphonFromNetwork(owner, getCostPerRefresh());
                }
            }
            if (world.isAirBlock(x, y + 1, z - 1)) {
                if (hasTenebrae) {
                    world.setBlock(x, y + 1, z - 1, Blocks.obsidian, 0, 3);
                    SoulNetworkHandler.syphonFromNetwork(owner, getCostPerRefresh() * 10);
                    this.canDrainReagent(ritualStone, ReagentRegistry.tenebraeReagent, reagentDrain, true);
                } else {
                    world.setBlock(x, y + 1, z - 1, Blocks.cobblestone, 0, 3);
                    SoulNetworkHandler.syphonFromNetwork(owner, getCostPerRefresh());
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
        ArrayList<RitualComponent> obsidianRitual = new ArrayList();
        obsidianRitual.add(new RitualComponent(1, 0, 0, RitualComponent.WATER));
        obsidianRitual.add(new RitualComponent(-1, 0, 0, RitualComponent.WATER));
        obsidianRitual.add(new RitualComponent(0, 0, 1, RitualComponent.WATER));
        obsidianRitual.add(new RitualComponent(0, 0, -1, RitualComponent.WATER));
        obsidianRitual.add(new RitualComponent(-1, 1, -1, RitualComponent.FIRE));
        obsidianRitual.add(new RitualComponent(1, 1, -1, RitualComponent.FIRE));
        obsidianRitual.add(new RitualComponent(1, 1, 1, RitualComponent.FIRE));
        obsidianRitual.add(new RitualComponent(-1, 1, 1, RitualComponent.FIRE));
        return obsidianRitual;
    }
}
