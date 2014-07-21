package tombenpotter.sanguimancy.rituals;

import WayofTime.alchemicalWizardry.api.rituals.IMasterRitualStone;
import WayofTime.alchemicalWizardry.api.rituals.RitualComponent;
import WayofTime.alchemicalWizardry.api.rituals.RitualEffect;
import WayofTime.alchemicalWizardry.api.soulNetwork.LifeEssenceNetwork;
import WayofTime.alchemicalWizardry.common.tileEntity.TEAltar;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RitualEffectDrillOfTheDead extends RitualEffect {

    public static final int timeDelay = 20;
    public static final int amount = 200;

    @Override
    public void performEffect(IMasterRitualStone ritualStone) {
        String owner = ritualStone.getOwner();
        World worldSave = MinecraftServer.getServer().worldServers[0];
        LifeEssenceNetwork data = (LifeEssenceNetwork) worldSave.loadItemData(LifeEssenceNetwork.class, owner);
        int currentEssence = data.currentEssence;
        World world = ritualStone.getWorld();
        int x = ritualStone.getXCoord();
        int y = ritualStone.getYCoord();
        int z = ritualStone.getZCoord();
        TEAltar tileAltar = null;
        boolean testFlag = false;
        EntityPlayer entityOwner = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(owner);

        if (data == null) {
            data = new LifeEssenceNetwork(owner);
            worldSave.setItemData(owner, data);
        }

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
        int vertRange = 5;
        AxisAlignedBB axisalignedbb = AxisAlignedBB.getAABBPool().getAABB((double) x, (double) y, (double) z, (double) (x + 1), (double) (y + 1), (double) (z + 1)).expand(d0, vertRange, d0);
        List list = world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
        Iterator iterator1 = list.iterator();
        EntityLivingBase entity;
        int entityCount = 0;
        boolean flag = false;

        while (iterator1.hasNext()) {
            entity = (EntityLivingBase) iterator1.next();
            entityCount++;
        }

        if (currentEssence < this.getCostPerRefresh() * entityCount) {
            if (entityOwner == null) {
                return;
            }

            entityOwner.addPotionEffect(new PotionEffect(Potion.confusion.id, 80));
            entityOwner.addPotionEffect(new PotionEffect(Potion.blindness.id, 80));
        } else {
            Iterator iterator2 = list.iterator();
            entityCount = 0;

            while (iterator2.hasNext()) {
                entity = (EntityLivingBase) iterator2.next();

                if (entity instanceof EntityPlayer) {
                    continue;
                }

                entity.attackEntityFrom(DamageSource.causePlayerDamage(entityOwner), entity.getMaxHealth() * 2);
                entityCount++;
                tileAltar.sacrificialDaggerCall(this.amount, true);
            }

            data.currentEssence = currentEssence - this.getCostPerRefresh() * entityCount;
            data.markDirty();
        }
    }

    @Override
    public int getCostPerRefresh() {
        return 20;
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
