package tombenpotter.sanguimancy.rituals;

import WayofTime.alchemicalWizardry.ModBlocks;
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

public class RitualEffectQuarry extends RitualEffect {

    public static final int reagentDrain = 5;

    public boolean startRitual(IMasterRitualStone ritualStone, EntityPlayer player) {
        int x = ritualStone.getXCoord();
        int y = ritualStone.getYCoord();
        int z = ritualStone.getZCoord();
        World world = ritualStone.getWorld();
        boolean hasTerrae = this.canDrainReagent(ritualStone, ReagentRegistry.terraeReagent, reagentDrain, true);
        boolean hasOrbisTerrae = this.canDrainReagent(ritualStone, ReagentRegistry.orbisTerraeReagent, reagentDrain, true);

        ArrayList<Int3> blocks = new ArrayList<Int3>();
        int rangeMultiplier = RitualUtils.getRangeMultiplier(hasTerrae, hasOrbisTerrae);
        if (blocks.isEmpty()) {
            blocks = RitualUtils.QuarryUtils.getBlocksInArea(world, x, y, z, rangeMultiplier);
        }
        for (Int3 int3 : blocks) {
            RitualUtils.QuarryUtils.deleteLiquids(world, int3.xCoord, int3.yCoord, int3.zCoord);
        }
        return true;
    }

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
        boolean hasTenebrae = this.canDrainReagent(ritualStone, ReagentRegistry.tenebraeReagent, reagentDrain, false);
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
            ArrayList<Int3> blocks = new ArrayList<Int3>();
            int rangeMultiplier = RitualUtils.getRangeMultiplier(hasTerrae, hasOrbisTerrae);
            int speedMultiplier = 1;
            if (hasPotentia) {
                speedMultiplier = 4;
            }
            if (blocks.isEmpty()) {
                blocks = RitualUtils.QuarryUtils.getBlocksInArea(world, x, y, z, rangeMultiplier);
            }
            for (Int3 int3 : blocks) {
                RitualUtils.QuarryUtils.deleteLiquids(world, int3.xCoord, int3.yCoord, int3.zCoord);
                if (world.rand.nextInt(500 / speedMultiplier) == 0) {
                    Block block = world.getBlock(int3.xCoord, int3.yCoord, int3.zCoord);
                    if (!(block == ModBlocks.blockMasterStone) && !(block == ModBlocks.ritualStone) && !(world.getTileEntity(int3.xCoord, int3.yCoord, int3.zCoord) instanceof IInventory)) {
                        if (hasCrystallos) {
                            RitualUtils.silkPlaceInInventory(block, world, int3.xCoord, int3.yCoord, int3.zCoord, tileEntity);
                            this.canDrainReagent(ritualStone, ReagentRegistry.crystallosReagent, reagentDrain * 2, true);
                        } else if (hasIncendium) {
                            RitualUtils.smeltPlaceInInventory(block, world, int3.xCoord, int3.yCoord, int3.zCoord, tileEntity);
                            this.canDrainReagent(ritualStone, ReagentRegistry.incendiumReagent, reagentDrain * 2, true);
                        } else if (hasTenebrae) {
                            RitualUtils.placeInInventory(block, world, int3.xCoord, int3.yCoord, int3.zCoord, tileEntity);
                            this.canDrainReagent(ritualStone, ReagentRegistry.tenebraeReagent, reagentDrain * 2, true);
                        }
                        world.setBlockToAir(int3.xCoord, int3.yCoord, int3.zCoord);
                        SoulNetworkHandler.syphonFromNetwork(owner, getCostPerRefresh());
                    }
                }
            }
            if (world.rand.nextInt(100) == 0) {
                blocks.clear();
            }
        }
    }

    @Override
    public int getCostPerRefresh() {
        return 50;
    }

    @Override
    public List<RitualComponent> getRitualComponentList() {
        ArrayList<RitualComponent> quarryRitual = new ArrayList();
        quarryRitual.add(new RitualComponent(1, 0, 1, RitualComponent.EARTH));
        quarryRitual.add(new RitualComponent(-1, 0, 1, RitualComponent.EARTH));
        quarryRitual.add(new RitualComponent(1, 0, -1, RitualComponent.EARTH));
        quarryRitual.add(new RitualComponent(-1, 0, -1, RitualComponent.EARTH));
        quarryRitual.add(new RitualComponent(2, 0, 0, RitualComponent.EARTH));
        quarryRitual.add(new RitualComponent(-2, 0, 0, RitualComponent.EARTH));
        quarryRitual.add(new RitualComponent(0, 0, -2, RitualComponent.EARTH));
        quarryRitual.add(new RitualComponent(0, 0, 2, RitualComponent.EARTH));

        quarryRitual.add(new RitualComponent(1, 2, 1, RitualComponent.DUSK));
        quarryRitual.add(new RitualComponent(-1, 2, 1, RitualComponent.DUSK));
        quarryRitual.add(new RitualComponent(1, 2, -1, RitualComponent.DUSK));
        quarryRitual.add(new RitualComponent(-1, 2, -1, RitualComponent.DUSK));
        quarryRitual.add(new RitualComponent(2, 2, 0, RitualComponent.DUSK));
        quarryRitual.add(new RitualComponent(-2, 2, 0, RitualComponent.DUSK));
        quarryRitual.add(new RitualComponent(0, 2, -2, RitualComponent.DUSK));
        quarryRitual.add(new RitualComponent(0, 2, 2, RitualComponent.DUSK));
        return quarryRitual;
    }
}
