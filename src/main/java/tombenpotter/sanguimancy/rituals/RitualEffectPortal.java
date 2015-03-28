package tombenpotter.sanguimancy.rituals;

import WayofTime.alchemicalWizardry.ModBlocks;
import WayofTime.alchemicalWizardry.api.rituals.*;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import WayofTime.alchemicalWizardry.common.spell.complex.effect.SpellHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.registry.BlocksRegistry;
import tombenpotter.sanguimancy.tile.TileDimensionalPortal;
import tombenpotter.sanguimancy.util.RandomUtils;
import tombenpotter.sanguimancy.util.singletons.LocationsHandler;
import tombenpotter.sanguimancy.util.teleporting.PortalLocation;

import java.util.ArrayList;
import java.util.List;

public class RitualEffectPortal extends RitualEffect {

    @Override
    public boolean startRitual(IMasterRitualStone ritualStone, EntityPlayer player) {
        String owner = ritualStone.getOwner();
        World world = ritualStone.getWorld();
        int x = ritualStone.getXCoord();
        int y = ritualStone.getYCoord();
        int z = ritualStone.getZCoord();
        ritualStone.getCustomRitualTag().setInteger("ritualDirection", Rituals.getDirectionOfRitual(world, x, y, z, "portalRitual"));
        int direction = ritualStone.getCustomRitualTag().getInteger("ritualDirection");
        if (!world.isRemote) {
            ritualStone.getCustomRitualTag().removeTag("PortalRitualID");
            String name = owner;
            if (direction == 1 || direction == 3) {
                for (int i = x - 3; i <= x + 3; i++) {
                    for (int k = z - 2; k <= z + 2; k++) {
                        if (!world.isAirBlock(i, y, k) && !(world.getBlock(i, y, k) == ModBlocks.ritualStone)) {
                            name = RandomUtils.addStringToEnd(name, Block.blockRegistry.getNameForObject(world.getBlock(i, y, k)) + String.valueOf(world.getBlockMetadata(i, y, k)));
                        }
                    }
                }
                for (int j = y + 1; j <= y + 5; j++) {
                    if (!world.isAirBlock(x - 3, j, z) && !(world.getBlock(x - 3, j, z) == ModBlocks.ritualStone)) {
                        name = RandomUtils.addStringToEnd(name, Block.blockRegistry.getNameForObject(world.getBlock(x - 3, j, z)) + String.valueOf(world.getBlockMetadata(x - 3, j, z)));
                    }
                }
                for (int j = y + 1; j <= y + 5; j++) {
                    if (!world.isAirBlock(x + 3, j, z) && !(world.getBlock(x + 3, j, z) == ModBlocks.ritualStone)) {
                        name = RandomUtils.addStringToEnd(name, Block.blockRegistry.getNameForObject(world.getBlock(x + 3, j, z)) + String.valueOf(world.getBlockMetadata(x + 3, j, z)));
                    }
                }
            } else if (direction == 2 || direction == 4) {
                for (int k = z - 3; k <= z + 3; k++) {
                    for (int i = x - 2; i <= x + 2; i++) {
                        if (!world.isAirBlock(i, y, k) && !(world.getBlock(i, y, k) == ModBlocks.ritualStone)) {
                            name = RandomUtils.addStringToEnd(name, Block.blockRegistry.getNameForObject(world.getBlock(i, y, k)) + String.valueOf(world.getBlockMetadata(i, y, k)));
                        }
                    }
                }
                for (int j = y + 1; j <= y + 5; j++) {
                    if (!world.isAirBlock(x, j, z - 3) && !(world.getBlock(x, j, z - 3) == ModBlocks.ritualStone)) {
                        name = RandomUtils.addStringToEnd(name, Block.blockRegistry.getNameForObject(world.getBlock(x, j, z - 3)) + String.valueOf(world.getBlockMetadata(x, j, z - 3)));
                    }
                }
                for (int j = y + 1; j <= y + 5; j++) {
                    if (!world.isAirBlock(x, j, z + 3) && !(world.getBlock(x, j, z + 3) == ModBlocks.ritualStone)) {
                        name = RandomUtils.addStringToEnd(name, Block.blockRegistry.getNameForObject(world.getBlock(x, j, z + 3)) + String.valueOf(world.getBlockMetadata(x, j, z + 3)));
                    }
                }
            }
            if (LocationsHandler.getLocationsHandler() != null) {
                if (LocationsHandler.getLocationsHandler().addLocation(name, new PortalLocation(x, y + 1, z, world.provider.dimensionId))) {
                    ritualStone.getCustomRitualTag().setString("PortalRitualID", name);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void performEffect(IMasterRitualStone ritualStone) {
        String owner = ritualStone.getOwner();
        World world = ritualStone.getWorld();
        int x = ritualStone.getXCoord();
        int y = ritualStone.getYCoord();
        int z = ritualStone.getZCoord();
        int direction = ritualStone.getCustomRitualTag().getInteger("ritualDirection");
        int currentEssence = SoulNetworkHandler.getCurrentEssence(owner);
        if (currentEssence < this.getCostPerRefresh()) {
            EntityPlayer entityOwner = SpellHelper.getPlayerForUsername(owner);
            if (entityOwner == null) {
                return;
            }
            SoulNetworkHandler.causeNauseaToPlayer(owner);
        } else {
            if (direction == 1 || direction == 3) {
                for (int i = x - 1; i <= x + 1; i++) {
                    for (int j = y + 1; j <= y + 3; j++) {
                        if (world.isAirBlock(i, j, z)) {
                            world.setBlock(i, j, z, BlocksRegistry.dimensionalPortal, 3, 3);
                            if (world.getTileEntity(i, j, z) != null && world.getTileEntity(i, j, z) instanceof TileDimensionalPortal) {
                                TileDimensionalPortal tile = (TileDimensionalPortal) world.getTileEntity(i, j, z);
                                tile.masterStoneX = x;
                                tile.masterStoneY = y;
                                tile.masterStoneZ = z;
                                tile.portalID = ritualStone.getCustomRitualTag().getString("PortalRitualID");
                            }
                        }
                    }
                }
            } else if (direction == 2 || direction == 4) {
                for (int k = z - 1; k <= z + 1; k++) {
                    for (int j = y + 1; j <= y + 3; j++) {
                        if (world.isAirBlock(x, j, k)) {
                            world.setBlock(x, j, k, BlocksRegistry.dimensionalPortal, 2, 3);
                            if (world.getTileEntity(x, j, k) != null && world.getTileEntity(x, j, k) instanceof TileDimensionalPortal) {
                                TileDimensionalPortal tile = (TileDimensionalPortal) world.getTileEntity(x, j, k);
                                tile.masterStoneX = x;
                                tile.masterStoneY = y;
                                tile.masterStoneZ = z;
                                tile.portalID = ritualStone.getCustomRitualTag().getString("PortalRitualID");
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onRitualBroken(IMasterRitualStone ritualStone, RitualBreakMethod method) {
        World world = ritualStone.getWorld();
        int x = ritualStone.getXCoord();
        int y = ritualStone.getYCoord();
        int z = ritualStone.getZCoord();
        int direction = ritualStone.getCustomRitualTag().getInteger("ritualDirection");
        if (LocationsHandler.getLocationsHandler() != null) {
            LocationsHandler.getLocationsHandler().removeLocation(ritualStone.getCustomRitualTag().getString("PortalRitualID"), new PortalLocation(x, y + 1, z, world.provider.dimensionId));
        }
        if (direction == 1 || direction == 3) {
            for (int i = x - 2; i <= x + 2; i++) {
                for (int j = y + 1; j <= y + 3; j++) {
                    if (world.getBlock(i, j, z) == BlocksRegistry.dimensionalPortal) {
                        world.setBlockToAir(i, j, z);
                    }
                }
            }
        } else if (direction == 2 || direction == 4) {
            for (int k = z - 2; k <= z + 2; k++) {
                for (int j = y + 1; j <= y + 3; j++) {
                    if (world.getBlock(x, j, k) == BlocksRegistry.dimensionalPortal) {
                        world.setBlockToAir(x, j, k);
                    }
                }
            }
        }
        ritualStone.getCustomRitualTag().removeTag("PortalRitualID");
    }

    @Override
    public int getCostPerRefresh() {
        return 0;
    }

    @Override
    public List<RitualComponent> getRitualComponentList() {
        ArrayList<RitualComponent> portalRitual = new ArrayList();
        portalRitual.add(new RitualComponent(1, 0, 0, RitualComponent.AIR));
        portalRitual.add(new RitualComponent(2, 0, 0, RitualComponent.WATER));
        portalRitual.add(new RitualComponent(-1, 0, 0, RitualComponent.FIRE));
        portalRitual.add(new RitualComponent(-2, 0, 0, RitualComponent.EARTH));
        portalRitual.add(new RitualComponent(2, 1, 0, RitualComponent.DUSK));
        portalRitual.add(new RitualComponent(2, 2, 0, RitualComponent.AIR));
        portalRitual.add(new RitualComponent(2, 3, 0, RitualComponent.WATER));
        portalRitual.add(new RitualComponent(2, 4, 0, RitualComponent.FIRE));
        portalRitual.add(new RitualComponent(1, 4, 0, RitualComponent.EARTH));
        portalRitual.add(new RitualComponent(0, 4, 0, RitualComponent.DUSK));
        portalRitual.add(new RitualComponent(-1, 4, 0, RitualComponent.AIR));
        portalRitual.add(new RitualComponent(-2, 4, 0, RitualComponent.WATER));
        portalRitual.add(new RitualComponent(-2, 3, 0, RitualComponent.FIRE));
        portalRitual.add(new RitualComponent(-2, 2, 0, RitualComponent.EARTH));
        portalRitual.add(new RitualComponent(-2, 1, 0, RitualComponent.DUSK));
        return portalRitual;
    }
}
