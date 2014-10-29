package tombenpotter.sanguimancy.rituals;

import WayofTime.alchemicalWizardry.api.rituals.IMasterRitualStone;
import WayofTime.alchemicalWizardry.api.rituals.RitualComponent;
import WayofTime.alchemicalWizardry.api.rituals.RitualEffect;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import WayofTime.alchemicalWizardry.common.spell.complex.effect.SpellHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import tombenpotter.sanguimancy.util.RitualUtils;

import java.util.ArrayList;
import java.util.List;

public class RitualEffectPump extends RitualEffect {

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
            FluidStack fluid = RitualUtils.PumpUtils.pumpFluid(world, x, y - 1, z, false);
            int amount = tileEntity.fill(ForgeDirection.DOWN, fluid, false);
            if (amount >= 1000) {
                tileEntity.fill(ForgeDirection.DOWN, fluid, true);
                SoulNetworkHandler.syphonFromNetwork(owner, this.getCostPerRefresh());
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
