package tombenpotter.oldsanguimancy.old.ded;

import WayofTime.alchemicalWizardry.api.Int3;
import WayofTime.alchemicalWizardry.api.alchemy.energy.ReagentRegistry;
import WayofTime.alchemicalWizardry.api.rituals.IMasterRitualStone;
import WayofTime.alchemicalWizardry.api.rituals.RitualComponent;
import WayofTime.alchemicalWizardry.api.rituals.RitualEffect;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import WayofTime.alchemicalWizardry.common.spell.complex.effect.SpellHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.ArrayList;
import java.util.List;

public class RitualEffectPump extends RitualEffect {

    public static final int reagentDrain = 5;

    @Override
    public void performEffect(IMasterRitualStone ritualStone) {
        String owner = ritualStone.getOwner();
        int currentEssence = SoulNetworkHandler.getCurrentEssence(owner);
        World world = ritualStone.getWorld();
        if (world.getWorldTime() % 10 != 5) {
            return;
        }
        int x = ritualStone.getXCoord();
        int y = ritualStone.getYCoord();
        int z = ritualStone.getZCoord();
        TileEntity tile = world.getTileEntity(x, y + 1, z);
        IFluidHandler tileEntity;
        if (tile instanceof IFluidHandler) {
            tileEntity = (IFluidHandler) tile;
        } else {
            return;
        }
        boolean hasTerrae = this.canDrainReagent(ritualStone, ReagentRegistry.terraeReagent, reagentDrain, true);
        boolean hasOrbisTerrae = this.canDrainReagent(ritualStone, ReagentRegistry.orbisTerraeReagent, reagentDrain, true);
        if (currentEssence < this.getCostPerRefresh()) {
            EntityPlayer entityOwner = SpellHelper.getPlayerForUsername(owner);
            if (entityOwner == null) {
                return;
            }
            SoulNetworkHandler.causeNauseaToPlayer(owner);
        } else {
            for (int i = 0; i < 6; i++) {
                SpellHelper.sendIndexedParticleToAllAround(world, x, y, z, 20, world.provider.dimensionId, 3, x, y, z);
            }
            if (tileEntity.drain(ForgeDirection.UNKNOWN, 1000, false) != null) {
                ArrayList<Int3> pumpables = new ArrayList<Int3>();
                FluidStack fluid = tileEntity.drain(ForgeDirection.UNKNOWN, 1000, false);
                int rangeMultiplier = RitualUtils.getRangeMultiplier(hasTerrae, hasOrbisTerrae);
                if (pumpables.isEmpty()) {
                    pumpables = RitualUtils.PumpUtils.getPumpablesInArea(world, fluid, x, y, z, rangeMultiplier);
                }
                for (Int3 int3 : pumpables) {
                    if (world.rand.nextInt(pumpables.size()) == 0) {
                        tileEntity.fill(ForgeDirection.DOWN, fluid, true);
                        SoulNetworkHandler.syphonFromNetwork(owner, this.getCostPerRefresh());
                        world.setBlock(int3.xCoord, int3.yCoord, int3.zCoord, Blocks.stone);
                        SoulNetworkHandler.syphonFromNetwork(owner, getCostPerRefresh());
                    }
                }
                pumpables.clear();
            }
        }
    }

    @Override
    public int getCostPerRefresh() {
        return 20;
    }

    @Override
    public List<RitualComponent> getRitualComponentList() {
        ArrayList<RitualComponent> pumpRitual = new ArrayList();
        pumpRitual.add(new RitualComponent(1, 0, 1, RitualComponent.WATER));
        pumpRitual.add(new RitualComponent(-1, 0, 1, RitualComponent.FIRE));
        pumpRitual.add(new RitualComponent(1, 0, -1, RitualComponent.EARTH));
        pumpRitual.add(new RitualComponent(-1, 0, -1, RitualComponent.AIR));
        pumpRitual.add(new RitualComponent(1, -1, 1, RitualComponent.DUSK));
        pumpRitual.add(new RitualComponent(-1, -1, 1, RitualComponent.DUSK));
        pumpRitual.add(new RitualComponent(1, -1, -1, RitualComponent.DUSK));
        pumpRitual.add(new RitualComponent(-1, -1, -1, RitualComponent.DUSK));
        return pumpRitual;
    }
}
