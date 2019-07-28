package tombenpotter.sanguimancy.rituals;

import WayofTime.bloodmagic.ConfigHandler;
import WayofTime.bloodmagic.api.impl.BloodMagicAPI;
import WayofTime.bloodmagic.core.data.SoulNetwork;
import WayofTime.bloodmagic.util.helper.NetworkHelper;
import WayofTime.bloodmagic.ritual.AreaDescriptor;
import WayofTime.bloodmagic.ritual.EnumRuneType;
import WayofTime.bloodmagic.ritual.IMasterRitualStone;
import WayofTime.bloodmagic.ritual.Ritual;
import WayofTime.bloodmagic.ritual.RitualComponent;
import WayofTime.bloodmagic.tile.TileAltar;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.Sanguimancy;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class RitualDrillOfTheDead extends Ritual {

    public static final String ALTAR_RANGE = "altar";
    public static final String DAMAGE_RANGE = "damage";

    public static final int SACRIFICE_AMOUNT = 200;

    public BlockPos altarOffsetPos = new BlockPos(0, 0, 0); //TODO: Save!

    public RitualDrillOfTheDead() {
        super("ritualDrillOfTheDead", 0, 40000, "ritual." + Sanguimancy.modid + ".drill.dead");
        addBlockRange(ALTAR_RANGE, new AreaDescriptor.Rectangle(new BlockPos(-5, -10, -5), 11, 21, 11));
        addBlockRange(DAMAGE_RANGE, new AreaDescriptor.Rectangle(new BlockPos(-10, -10, -10), 21));

        setMaximumVolumeAndDistanceOfRange(ALTAR_RANGE, 0, 10, 15);
        setMaximumVolumeAndDistanceOfRange(DAMAGE_RANGE, 0, 15, 15);
    }

    @SuppressWarnings("deprecation")
	@Override
    public void performRitual(IMasterRitualStone masterRitualStone) {
        World world = masterRitualStone.getWorldObj();
        SoulNetwork network = NetworkHelper.getSoulNetwork(masterRitualStone.getOwner());
        int currentEssence = network.getCurrentEssence();

        if (currentEssence < getRefreshCost()) {
            network.causeNausea();
            return;
        }

        BlockPos pos = masterRitualStone.getBlockPos();

        int maxEffects = currentEssence / getRefreshCost();
        int totalEffects = 0;

        BlockPos altarPos = pos.add(altarOffsetPos);

        TileEntity tile = world.getTileEntity(altarPos);

        AreaDescriptor altarRange = getBlockRange(ALTAR_RANGE);

        if (!altarRange.isWithinArea(altarOffsetPos) || !(tile instanceof TileAltar)) {
            for (BlockPos newPos : altarRange.getContainedPositions(pos)) {
                TileEntity nextTile = world.getTileEntity(newPos);
                if (nextTile instanceof TileAltar) {
                    tile = nextTile;
                    altarOffsetPos = newPos.subtract(pos);

                    altarRange.resetCache();
                    break;
                }
            }
        }

        if (tile instanceof TileAltar) {
            TileAltar tileAltar = (TileAltar) tile;

            AreaDescriptor damageRange = getBlockRange(DAMAGE_RANGE);
            AxisAlignedBB range = damageRange.getAABB(pos);

            List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, range);

            for (EntityLivingBase entity : entities) {
                if (ConfigHandler.wellOfSufferingBlacklist.contains(entity.getClass().getSimpleName()))
                    continue;

                String simpleClassName = entity.getClass().getSimpleName();
                if (BloodMagicAPI.getEntitySacrificeValues().containsKey(simpleClassName) && BloodMagicAPI.getEntitySacrificeValues().get(simpleClassName) <= 0)
                    continue;

                if (entity.isEntityAlive() && !(entity instanceof EntityPlayer)) {
                    if (entity.attackEntityFrom(DamageSource.OUT_OF_WORLD, entity.getMaxHealth() * 2)) {
                        tileAltar.sacrificialDaggerCall(SACRIFICE_AMOUNT, true);

                        totalEffects++;

                        if (totalEffects >= maxEffects) {
                            break;
                        }
                    }
                }
            }
        }

        network.syphon(getRefreshCost() * totalEffects);
    }

    @Override
    public int getRefreshCost() {
        return 75;
    }

    @Override
    public ArrayList<RitualComponent> getComponents() {
        ArrayList<RitualComponent> components = new ArrayList<RitualComponent>();

        this.addCornerRunes(components, 1, 0, EnumRuneType.DUSK);
        this.addCornerRunes(components, 2, -1, EnumRuneType.DUSK);
        this.addParallelRunes(components, 2, -1, EnumRuneType.DUSK);
        this.addCornerRunes(components, -3, -1, EnumRuneType.DUSK);
        this.addOffsetRunes(components, 2, 4, -1, EnumRuneType.DUSK);
        this.addOffsetRunes(components, 1, 4, 0, EnumRuneType.DUSK);
        this.addParallelRunes(components, 4, 1, EnumRuneType.DUSK);

        return components;
    }

    @Override
    public Ritual getNewCopy() {
        return new RitualDrillOfTheDead();
    }

	@Override
	public void gatherComponents(Consumer<RitualComponent> arg0) {
		// TODO Auto-generated method stub
		
	}
}
