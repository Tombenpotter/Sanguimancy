package tombenpotter.sanguimancy.rituals;

import WayofTime.alchemicalWizardry.api.rituals.IMasterRitualStone;
import WayofTime.alchemicalWizardry.api.rituals.RitualComponent;
import WayofTime.alchemicalWizardry.api.rituals.RitualEffect;
import WayofTime.alchemicalWizardry.api.rituals.Rituals;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import WayofTime.alchemicalWizardry.common.spell.complex.effect.SpellHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.registry.BlocksRegistry;
import tombenpotter.sanguimancy.tile.TileDimensionalPortal;
import tombenpotter.sanguimancy.util.LocationsHandler;
import tombenpotter.sanguimancy.util.PortalLocation;
import tombenpotter.sanguimancy.util.RandomUtils;

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
        ritualStone.getCustomRitualTag().setString("currentRitualString", "portalRitual");
        int direction = Rituals.getDirectionOfRitual(world, x, y, z, ritualStone.getCustomRitualTag().getString("currentRitualString"));
        if (!world.isRemote) {
            String name = owner;
            if (direction == 1 || direction == 4) {
                for (int i = x - 3; i <= x + 3; i++) {
                    for (int k = z - 2; k <= z + 2; k++) {
                        name = RandomUtils.addStringToEnd(name, world.getBlock(i, y, k).getUnlocalizedName() + String.valueOf(world.getBlockMetadata(i, y, k)));
                    }
                }
                for (int j = y + 1; j <= y + 5; j++) {
                    name = RandomUtils.addStringToEnd(name, world.getBlock(x - 3, j, z).getUnlocalizedName() + String.valueOf(world.getBlockMetadata(x - 3, j, z)));
                }
                for (int j = y + 1; j <= y + 5; j++) {
                    name = RandomUtils.addStringToEnd(name, world.getBlock(x + 3, j, z).getUnlocalizedName() + String.valueOf(world.getBlockMetadata(x + 3, j, z)));
                }
            } else if (direction == 2 || direction == 3) {
                for (int k = x - 2; k <= x + 2; k++) {
                    for (int i = z - 3; i <= z + 3; i++) {
                        name = RandomUtils.addStringToEnd(name, world.getBlock(i, y, k).getUnlocalizedName() + String.valueOf(world.getBlockMetadata(i, y, k)));
                    }
                }
                for (int j = y + 1; j <= y + 5; j++) {
                    name = RandomUtils.addStringToEnd(name, world.getBlock(x, j, z - 3).getUnlocalizedName() + String.valueOf(world.getBlockMetadata(x, j, z - 3)));
                }
                for (int j = y + 1; j <= y + 5; j++) {
                    name = RandomUtils.addStringToEnd(name, world.getBlock(x, j, z + 3).getUnlocalizedName() + String.valueOf(world.getBlockMetadata(x, j, z + 3)));
                }
            }
            if (LocationsHandler.getLocationsHandler() != null) {
                System.out.println("Location handler not null");
                LocationsHandler.getLocationsHandler().addLocation(name, new PortalLocation(x, y + 1, z, world.provider.dimensionId));
                ritualStone.getCustomRitualTag().setString("PortalRitualID", name);
            }
        }
        return true;
    }

    @Override
    public void performEffect(IMasterRitualStone ritualStone) {
        String owner = ritualStone.getOwner();
        World world = ritualStone.getWorld();
        int x = ritualStone.getXCoord();
        int y = ritualStone.getYCoord();
        int z = ritualStone.getZCoord();
        int direction = Rituals.getDirectionOfRitual(world, x, y, z, ritualStone.getCustomRitualTag().getString("currentRitualString"));
        int currentEssence = SoulNetworkHandler.getCurrentEssence(owner);
        if (currentEssence < this.getCostPerRefresh()) {
            EntityPlayer entityOwner = SpellHelper.getPlayerForUsername(owner);
            if (entityOwner == null) {
                return;
            }
            SoulNetworkHandler.causeNauseaToPlayer(owner);
        } else {
            if (direction == 1 || direction == 4) {
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
            } else if (direction == 2 || direction == 3) {
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
    public void onRitualBroken(IMasterRitualStone ritualStone) {
        World world = ritualStone.getWorld();
        int x = ritualStone.getXCoord();
        int y = ritualStone.getYCoord();
        int z = ritualStone.getZCoord();
        int direction = Rituals.getDirectionOfRitual(world, x, y, z, ritualStone.getCustomRitualTag().getString("currentRitualString"));
        if (LocationsHandler.getLocationsHandler() != null) {
            System.out.println("Location handler not null");
            LocationsHandler.getLocationsHandler().removeLocation(ritualStone.getCustomRitualTag().getString("PortalRitualID"), new PortalLocation(x, y + 1, z, world.provider.dimensionId));
        }
        if (direction == 1 || direction == 4) {
            for (int i = x - 2; i <= x + 2; i++) {
                for (int j = y + 1; j <= y + 3; j++) {
                    if (world.getBlock(i, j, z) == BlocksRegistry.dimensionalPortal) {
                        world.setBlockToAir(i, j, z);
                    }
                }
            }
        } else if (direction == 2 || direction == 3) {
            for (int k = z - 2; k <= z + 2; k++) {
                for (int j = y + 1; j <= y + 3; j++) {
                    if (world.getBlock(x, j, k) == BlocksRegistry.dimensionalPortal) {
                        world.setBlockToAir(x, j, k);
                    }
                }
            }
        }
    }

    @Override
    public int getCostPerRefresh() {
        return 0;
    }

    @Override
    public List<RitualComponent> getRitualComponentList() {
        ArrayList<RitualComponent> portalRitual = new ArrayList();
        portalRitual.add(new RitualComponent(1, 0, 0, RitualComponent.AIR));
        portalRitual.add(new RitualComponent(2, 0, 0, RitualComponent.AIR));
        portalRitual.add(new RitualComponent(-1, 0, 0, RitualComponent.AIR));
        portalRitual.add(new RitualComponent(-2, 0, 0, RitualComponent.AIR));
        portalRitual.add(new RitualComponent(2, 1, 0, RitualComponent.AIR));
        portalRitual.add(new RitualComponent(2, 2, 0, RitualComponent.AIR));
        portalRitual.add(new RitualComponent(2, 3, 0, RitualComponent.AIR));
        portalRitual.add(new RitualComponent(2, 4, 0, RitualComponent.AIR));
        portalRitual.add(new RitualComponent(1, 4, 0, RitualComponent.AIR));
        portalRitual.add(new RitualComponent(0, 4, 0, RitualComponent.AIR));
        portalRitual.add(new RitualComponent(-1, 4, 0, RitualComponent.AIR));
        portalRitual.add(new RitualComponent(-2, 4, 0, RitualComponent.AIR));
        portalRitual.add(new RitualComponent(-2, 3, 0, RitualComponent.AIR));
        portalRitual.add(new RitualComponent(-2, 2, 0, RitualComponent.AIR));
        portalRitual.add(new RitualComponent(-2, 1, 0, RitualComponent.AIR));
        return portalRitual;
    }
}
