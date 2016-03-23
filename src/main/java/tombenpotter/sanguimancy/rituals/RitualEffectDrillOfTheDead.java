package tombenpotter.sanguimancy.rituals;

import WayofTime.alchemicalWizardry.AlchemicalWizardry;
import WayofTime.alchemicalWizardry.api.rituals.IMasterRitualStone;
import WayofTime.alchemicalWizardry.api.rituals.RitualComponent;
import WayofTime.alchemicalWizardry.api.rituals.RitualEffect;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import WayofTime.alchemicalWizardry.common.tileEntity.TEAltar;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.api.soulCorruption.SoulCorruptionHelper;
import tombenpotter.sanguimancy.util.ConfigHandler;

import java.util.ArrayList;
import java.util.List;

public class RitualEffectDrillOfTheDead extends RitualEffect {

    public static final int timeDelay = 20;
    public static final int amount = 200;

    @Override
    public boolean startRitual(IMasterRitualStone ritualStone, EntityPlayer player) {
        SoulCorruptionHelper.incrementCorruption(player);
        return true;
    }

    @Override
    public void performEffect(IMasterRitualStone ritualStone) {
        String owner = ritualStone.getOwner();
        World world = ritualStone.getWorld();
        int x = ritualStone.getXCoord();
        int y = ritualStone.getYCoord();
        int z = ritualStone.getZCoord();
        TEAltar tileAltar = null;
        boolean testFlag = false;
        int currentEssence = SoulNetworkHandler.getCurrentEssence(owner);
        if (world.getWorldTime() % this.timeDelay != 0) {
            return;
        }
        for (int i = -5; i <= 5; i++) {
            for (int j = -5; j <= 5; j++) {
                for (int k = -10; k <= 10; k++) {
                    if (world.getTileEntity(x + i, y + k, z + j) instanceof TEAltar) {
                        tileAltar = (TEAltar) world.getTileEntity(x + i, y + k, z + j);
                        testFlag = true;
                    }
                }
            }
        }
        if (!testFlag) {
            return;
        }
        int d0 = 10;
        int vertRange = 10;
        AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox((double) x, (double) y, (double) z, (double) (x + 1), (double) (y + 1), (double) (z + 1)).expand(d0, vertRange, d0);
        List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
        int entityCount = 0;
        if (currentEssence < this.getCostPerRefresh() * list.size()) {
            SoulNetworkHandler.causeNauseaToPlayer(owner);
        } else {
            for (EntityLivingBase livingEntity : list) {
                if (livingEntity instanceof EntityPlayer || livingEntity instanceof IBossDisplayData || AlchemicalWizardry.wellBlacklist.contains(livingEntity.getClass())) {
                    continue;
                }
                if (SoulNetworkHandler.getPlayerForUsername(owner) != null && (!Sanguimancy.isTTLoaded || !Sanguimancy.isIguanaTweaksLoaded || !ConfigHandler.noPlayerDamageforDoD)) {
                    if (livingEntity.attackEntityFrom(DamageSource.causePlayerDamage(SoulNetworkHandler.getPlayerForUsername(owner)), livingEntity.getMaxHealth() * 2)) {
                        entityCount++;
                        tileAltar.sacrificialDaggerCall(this.amount, true);
                    }
                } else {
                    if (livingEntity.attackEntityFrom(DamageSource.outOfWorld, livingEntity.getMaxHealth() * 2)) {
                        entityCount++;
                        tileAltar.sacrificialDaggerCall(this.amount, true);
                    }
                }
            }
            SoulNetworkHandler.syphonFromNetwork(owner, getCostPerRefresh() * entityCount);
        }
    }

    @Override
    public int getCostPerRefresh() {
        return 75;
    }

    @Override
    public List<RitualComponent> getRitualComponentList() {
        ArrayList<RitualComponent> drillOfTheDeadRitual = new ArrayList();
        drillOfTheDeadRitual.add(new RitualComponent(1, 0, 1, RitualComponent.DUSK));
        drillOfTheDeadRitual.add(new RitualComponent(-1, 0, 1, RitualComponent.DUSK));
        drillOfTheDeadRitual.add(new RitualComponent(1, 0, -1, RitualComponent.DUSK));
        drillOfTheDeadRitual.add(new RitualComponent(-1, 0, -1, RitualComponent.DUSK));
        drillOfTheDeadRitual.add(new RitualComponent(2, -1, 2, RitualComponent.DUSK));
        drillOfTheDeadRitual.add(new RitualComponent(2, -1, -2, RitualComponent.DUSK));
        drillOfTheDeadRitual.add(new RitualComponent(-2, -1, 2, RitualComponent.DUSK));
        drillOfTheDeadRitual.add(new RitualComponent(-2, -1, -2, RitualComponent.DUSK));
        drillOfTheDeadRitual.add(new RitualComponent(0, -1, 2, RitualComponent.DUSK));
        drillOfTheDeadRitual.add(new RitualComponent(2, -1, 0, RitualComponent.DUSK));
        drillOfTheDeadRitual.add(new RitualComponent(0, -1, -2, RitualComponent.DUSK));
        drillOfTheDeadRitual.add(new RitualComponent(-2, -1, 0, RitualComponent.DUSK));
        drillOfTheDeadRitual.add(new RitualComponent(-3, -1, -3, RitualComponent.DUSK));
        drillOfTheDeadRitual.add(new RitualComponent(3, -1, -3, RitualComponent.DUSK));
        drillOfTheDeadRitual.add(new RitualComponent(-3, -1, 3, RitualComponent.DUSK));
        drillOfTheDeadRitual.add(new RitualComponent(3, -1, 3, RitualComponent.DUSK));
        drillOfTheDeadRitual.add(new RitualComponent(2, -1, 4, RitualComponent.DUSK));
        drillOfTheDeadRitual.add(new RitualComponent(4, -1, 2, RitualComponent.DUSK));
        drillOfTheDeadRitual.add(new RitualComponent(-2, -1, 4, RitualComponent.DUSK));
        drillOfTheDeadRitual.add(new RitualComponent(4, -1, -2, RitualComponent.DUSK));
        drillOfTheDeadRitual.add(new RitualComponent(2, -1, -4, RitualComponent.DUSK));
        drillOfTheDeadRitual.add(new RitualComponent(-4, -1, 2, RitualComponent.DUSK));
        drillOfTheDeadRitual.add(new RitualComponent(-2, -1, -4, RitualComponent.DUSK));
        drillOfTheDeadRitual.add(new RitualComponent(-4, -1, -2, RitualComponent.DUSK));
        drillOfTheDeadRitual.add(new RitualComponent(1, 0, 4, RitualComponent.DUSK));
        drillOfTheDeadRitual.add(new RitualComponent(4, 0, 1, RitualComponent.DUSK));
        drillOfTheDeadRitual.add(new RitualComponent(1, 0, -4, RitualComponent.DUSK));
        drillOfTheDeadRitual.add(new RitualComponent(-4, 0, 1, RitualComponent.DUSK));
        drillOfTheDeadRitual.add(new RitualComponent(-1, 0, 4, RitualComponent.DUSK));
        drillOfTheDeadRitual.add(new RitualComponent(4, 0, -1, RitualComponent.DUSK));
        drillOfTheDeadRitual.add(new RitualComponent(-1, 0, -4, RitualComponent.DUSK));
        drillOfTheDeadRitual.add(new RitualComponent(-4, 0, -1, RitualComponent.DUSK));
        drillOfTheDeadRitual.add(new RitualComponent(4, 1, 0, RitualComponent.DUSK));
        drillOfTheDeadRitual.add(new RitualComponent(0, 1, 4, RitualComponent.DUSK));
        drillOfTheDeadRitual.add(new RitualComponent(-4, 1, 0, RitualComponent.DUSK));
        drillOfTheDeadRitual.add(new RitualComponent(0, 1, -4, RitualComponent.DUSK));
        return drillOfTheDeadRitual;
    }
}
