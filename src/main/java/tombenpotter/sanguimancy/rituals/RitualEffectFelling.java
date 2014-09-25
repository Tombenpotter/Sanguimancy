package tombenpotter.sanguimancy.rituals;

import WayofTime.alchemicalWizardry.api.alchemy.energy.ReagentRegistry;
import WayofTime.alchemicalWizardry.api.rituals.IMasterRitualStone;
import WayofTime.alchemicalWizardry.api.rituals.RitualComponent;
import WayofTime.alchemicalWizardry.api.rituals.RitualEffect;
import WayofTime.alchemicalWizardry.api.soulNetwork.LifeEssenceNetwork;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import WayofTime.alchemicalWizardry.common.spell.complex.effect.SpellHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class RitualEffectFelling extends RitualEffect {

    public static final int crystallosDrain = 10;

    @Override
    public void performEffect(IMasterRitualStone ritualStone) {
        String owner = ritualStone.getOwner();
        World worldSave = MinecraftServer.getServer().worldServers[0];
        LifeEssenceNetwork data = (LifeEssenceNetwork) worldSave.loadItemData(LifeEssenceNetwork.class, owner);
        if (data == null) {
            data = new LifeEssenceNetwork(owner);
            worldSave.setItemData(owner, data);
        }
        int currentEssence = data.currentEssence;
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
        boolean hasCrystallos = this.canDrainReagent(ritualStone, ReagentRegistry.crystallosReagent, crystallosDrain, false);
        boolean isSilkTouch = hasCrystallos;
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
            for (int j = -64; j <= 0; j++) {
                for (int i = -8; i <= 8; i++) {
                    for (int k = -8; k <= 8; k++) {
                        Block block = world.getBlock(x + i, y + j, z + k);
                        int meta = world.getBlockMetadata(x + i, y + j, z + k);
                        if (block != null && !world.isAirBlock(x + i, y + j, z + k) && (block.isLeaves(world, x + i, y + j, z + k) || block.isWood(world, x + i, y + j, z + k))) {
                            if (isSilkTouch && block.canSilkHarvest(world, null, x + i, y + j, z + k, meta)) {
                                ItemStack item = new ItemStack(block, 1, meta);
                                ItemStack copyStack = item.copyItemStack(item);
                                SpellHelper.insertStackIntoInventory(copyStack, tileEntity);
                                if (copyStack.stackSize > 0) {
                                    world.spawnEntityInWorld(new EntityItem(world, x + 0.4, y + 2, z + 0.5, copyStack));
                                }
                                if (hasCrystallos) {
                                    this.canDrainReagent(ritualStone, ReagentRegistry.crystallosReagent, crystallosDrain, true);
                                }
                            } else {
                                ArrayList<ItemStack> itemDropList = block.getDrops(world, x + i, y + j, z + k, meta, 0);
                                if (itemDropList != null) {
                                    for (ItemStack item : itemDropList) {
                                        ItemStack copyStack = item.copyItemStack(item);
                                        SpellHelper.insertStackIntoInventory(copyStack, tileEntity);
                                        if (copyStack.stackSize > 0) {
                                            world.spawnEntityInWorld(new EntityItem(world, x + 0.4, y + 2, z + 0.5, copyStack));
                                        }
                                    }
                                }
                            }
                            world.setBlockToAir(x + i, y + j, z + k);
                            data.currentEssence = currentEssence - this.getCostPerRefresh();
                            data.markDirty();
                            return;
                        }
                    }
                }
            }
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
