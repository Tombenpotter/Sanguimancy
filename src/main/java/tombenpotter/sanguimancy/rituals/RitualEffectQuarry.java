package tombenpotter.sanguimancy.rituals;

import WayofTime.alchemicalWizardry.ModBlocks;
import WayofTime.alchemicalWizardry.api.alchemy.energy.ReagentRegistry;
import WayofTime.alchemicalWizardry.api.rituals.IMasterRitualStone;
import WayofTime.alchemicalWizardry.api.rituals.RitualComponent;
import WayofTime.alchemicalWizardry.api.rituals.RitualEffect;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import WayofTime.alchemicalWizardry.common.spell.complex.effect.SpellHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import tombenpotter.sanguimancy.util.RitualUtils;
import tterrag.core.common.util.BlockCoord;
import tterrag.core.common.util.blockiterators.CubicBlockIterator;

import java.util.ArrayList;
import java.util.List;

public class RitualEffectQuarry extends RitualEffect {

    public static final int reagentDrain = 1000;

    @Override
    public boolean startRitual(IMasterRitualStone ritualStone, EntityPlayer player) {
        String owner = ritualStone.getOwner();
        int currentEssence = SoulNetworkHandler.getCurrentEssence(owner);
        World world = ritualStone.getWorld();
        int x = ritualStone.getXCoord();
        int y = ritualStone.getYCoord();
        int z = ritualStone.getZCoord();
        TileEntity tile = world.getTileEntity(x, y + 1, z);
        IInventory tileEntity;
        if (tile instanceof IInventory) {
            tileEntity = (IInventory) tile;
        } else {
            return false;
        }
        if (tileEntity.getSizeInventory() <= 0) {
            return false;
        }
        boolean hasRoom = false;
        for (int i = 0; i < tileEntity.getSizeInventory(); i++) {
            if (tileEntity.getStackInSlot(i) == null) {
                hasRoom = true;
                break;
            }
        }
        if (!hasRoom) {
            return false;
        }
        boolean hasCrystallos = this.canDrainReagent(ritualStone, ReagentRegistry.crystallosReagent, reagentDrain, false);
        boolean hasIncendium = this.canDrainReagent(ritualStone, ReagentRegistry.incendiumReagent, reagentDrain, false);
        boolean hasTenebrae = this.canDrainReagent(ritualStone, ReagentRegistry.tenebraeReagent, reagentDrain, false);
        boolean hasTerrae = this.canDrainReagent(ritualStone, ReagentRegistry.terraeReagent, reagentDrain, true);
        boolean hasOrbisTerrae = this.canDrainReagent(ritualStone, ReagentRegistry.orbisTerraeReagent, reagentDrain, true);
        if (currentEssence < this.getCostPerRefresh()) {
            EntityPlayer entityOwner = SpellHelper.getPlayerForUsername(owner);
            if (entityOwner == null) {
                return false;
            }
            SoulNetworkHandler.causeNauseaToPlayer(owner);
        } else {
            for (int i = 0; i < 6; i++) {
                SpellHelper.sendIndexedParticleToAllAround(world, x, y, z, 20, world.provider.dimensionId, 3, x, y, z);
            }
            CubicBlockIterator iterator1 = new CubicBlockIterator(new BlockCoord(x, y, z), 17 * RitualUtils.getRangeMultiplier(hasTerrae, hasOrbisTerrae));
            while (iterator1.hasNext()) {
                BlockCoord currentCoord = iterator1.next();
                Block block = currentCoord.getBlock(world);
                Fluid fluid = FluidRegistry.lookupFluidForBlock(block);
                if (fluid != null && FluidRegistry.isFluidRegistered(fluid)) {
                    world.setBlock(currentCoord.x, currentCoord.y, currentCoord.z, Blocks.stone, 0, 2);
                    SoulNetworkHandler.syphonFromNetwork(owner, getCostPerRefresh());
                }
            }
            CubicBlockIterator iterator = new CubicBlockIterator(new BlockCoord(x, y, z), 16 * RitualUtils.getRangeMultiplier(hasTerrae, hasOrbisTerrae));
            while (iterator.hasNext()) {
                BlockCoord currentCoord = iterator.next();
                Block block = currentCoord.getBlock(world);
                MinecraftServer server = MinecraftServer.getServer();
                if (!server.isBlockProtected(world, currentCoord.x, currentCoord.y, currentCoord.z, SpellHelper.getPlayerForUsername(owner)) && !(block == ModBlocks.blockMasterStone) && !(block == ModBlocks.ritualStone) && !(world.getTileEntity(currentCoord.x, currentCoord.y, currentCoord.z) instanceof IInventory) && !world.isAirBlock(currentCoord.x, currentCoord.y, currentCoord.z) && block.getBlockHardness(world, currentCoord.x, currentCoord.y, currentCoord.z) >= 0) {
                    if (hasCrystallos && hasTenebrae) {
                        RitualUtils.silkPlaceInInventory(block, world, currentCoord.x, currentCoord.y, currentCoord.z, tileEntity);
                        this.canDrainReagent(ritualStone, ReagentRegistry.crystallosReagent, reagentDrain, true);
                        this.canDrainReagent(ritualStone, ReagentRegistry.tenebraeReagent, reagentDrain, true);
                    } else if (hasIncendium && hasTenebrae) {
                        RitualUtils.smeltPlaceInInventory(block, world, currentCoord.x, currentCoord.y, currentCoord.z, tileEntity);
                        this.canDrainReagent(ritualStone, ReagentRegistry.incendiumReagent, reagentDrain, true);
                        this.canDrainReagent(ritualStone, ReagentRegistry.tenebraeReagent, reagentDrain, true);
                    } else if (hasTenebrae) {
                        RitualUtils.placeInInventory(block, world, currentCoord.x, currentCoord.y, currentCoord.z, tileEntity);
                        this.canDrainReagent(ritualStone, ReagentRegistry.tenebraeReagent, reagentDrain, true);
                    }
                    world.setBlock(currentCoord.x, currentCoord.y, currentCoord.z, Blocks.air, 0, 2);
                    SoulNetworkHandler.syphonFromNetwork(owner, getCostPerRefresh());
                }
            }
        }
        return true;
    }

    @Override
    public void performEffect(IMasterRitualStone ritualStone) {
        onRitualBroken(ritualStone);
    }

    @Override
    public int getCostPerRefresh() {
        return 300;
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
