package tombenpotter.sanguimancy.rituals;

import WayofTime.alchemicalWizardry.api.alchemy.energy.ReagentRegistry;
import WayofTime.alchemicalWizardry.api.rituals.IMasterRitualStone;
import WayofTime.alchemicalWizardry.api.rituals.RitualComponent;
import WayofTime.alchemicalWizardry.api.rituals.RitualEffect;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import WayofTime.alchemicalWizardry.common.Int3;
import WayofTime.alchemicalWizardry.common.spell.complex.effect.SpellHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.util.RitualUtils;

import java.util.ArrayList;
import java.util.List;

public class RitualEffectFelling extends RitualEffect {

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
        IInventory tileEntity;
        if (tile instanceof IInventory) {
            tileEntity = (IInventory) tile;
        } else {
            return;
        }
        if (tileEntity.getSizeInventory() <= 0) {
            return;
        }
        boolean hasRoom = false;
        for (int i = 0; i < tileEntity.getSizeInventory(); i++) {
            if (tileEntity.getStackInSlot(i) == null) {
                hasRoom = true;
                break;
            }
        }
        if (!hasRoom) {
            return;
        }
        boolean hasCrystallos = this.canDrainReagent(ritualStone, ReagentRegistry.crystallosReagent, reagentDrain, false);
        boolean hasIncendium = this.canDrainReagent(ritualStone, ReagentRegistry.incendiumReagent, reagentDrain, false);
        boolean hasTerrae = this.canDrainReagent(ritualStone, ReagentRegistry.terraeReagent, reagentDrain, true);
        boolean hasOrbisTerrae = this.canDrainReagent(ritualStone, ReagentRegistry.orbisTerraeReagent, reagentDrain, true);
        boolean hasPotentia = this.canDrainReagent(ritualStone, ReagentRegistry.potentiaReagent, reagentDrain, true);
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
            ArrayList<Int3> harvestables = new ArrayList<Int3>();
            int rangeMultiplier = 1;
            int timeDivider = 1;
            if (harvestables.isEmpty()) {
                if (hasTerrae) {
                    if (hasOrbisTerrae) {
                        rangeMultiplier = 8;
                    } else {
                        rangeMultiplier = 3;
                    }
                } else {
                    if (hasOrbisTerrae) {
                        rangeMultiplier = 5;
                    }
                }
                if (hasPotentia) {
                    timeDivider = 2;
                }
                harvestables = RitualUtils.TimbermanUtils.getHarvestablesInArea(world, x, y, z, rangeMultiplier);
            }
            for (Int3 int3 : harvestables) {
                if (world.rand.nextInt(10 / timeDivider) == 0) {
                    Block block = world.getBlock(int3.xCoord, int3.yCoord, int3.zCoord);
                    if (!world.isAirBlock(int3.xCoord, int3.yCoord, int3.zCoord) && (block.isLeaves(world, int3.xCoord, int3.yCoord, int3.zCoord) || block.isWood(world, int3.xCoord, int3.yCoord, int3.zCoord))) {
                        if (hasCrystallos) {
                            RitualUtils.silkPlaceInInventory(block, world, int3.xCoord, int3.yCoord, int3.zCoord, tileEntity);
                            this.canDrainReagent(ritualStone, ReagentRegistry.crystallosReagent, reagentDrain, true);
                        } else if (hasIncendium) {
                            RitualUtils.smeltPlaceInInventory(block, world, int3.xCoord, int3.yCoord, int3.zCoord, tileEntity);
                            this.canDrainReagent(ritualStone, ReagentRegistry.incendiumReagent, reagentDrain / 2, true);
                        } else {
                            RitualUtils.placeInInventory(block, world, int3.xCoord, int3.yCoord, int3.zCoord, tileEntity);
                        }
                        world.setBlockToAir(int3.xCoord, int3.yCoord, int3.zCoord);
                        SoulNetworkHandler.syphonFromNetwork(owner, getCostPerRefresh());
                    }
                }
            }
            harvestables.clear();
        }
    }

    @Override
    public int getCostPerRefresh() {
        return 30;
    }

    @Override
    public List<RitualComponent> getRitualComponentList() {
        ArrayList<RitualComponent> fellingRitual = new ArrayList();
        fellingRitual.add(new RitualComponent(1, 0, 0, RitualComponent.EARTH));
        fellingRitual.add(new RitualComponent(-1, 0, 0, RitualComponent.EARTH));
        fellingRitual.add(new RitualComponent(0, 0, 1, RitualComponent.EARTH));
        fellingRitual.add(new RitualComponent(0, 0, -1, RitualComponent.EARTH));
        fellingRitual.add(new RitualComponent(1, 1, 1, RitualComponent.EARTH));
        fellingRitual.add(new RitualComponent(-1, 1, 1, RitualComponent.EARTH));
        fellingRitual.add(new RitualComponent(-1, 1, -1, RitualComponent.EARTH));
        fellingRitual.add(new RitualComponent(1, 1, -1, RitualComponent.EARTH));
        return fellingRitual;
    }
}
