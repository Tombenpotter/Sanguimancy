package tombenpotter.sanguimancy.rituals;

import WayofTime.bloodmagic.api.ritual.EnumRuneType;
import WayofTime.bloodmagic.api.ritual.IMasterRitualStone;
import WayofTime.bloodmagic.api.ritual.Ritual;
import WayofTime.bloodmagic.api.ritual.RitualComponent;
import WayofTime.bloodmagic.api.saving.SoulNetwork;
import WayofTime.bloodmagic.api.util.helper.NetworkHelper;
import WayofTime.bloodmagic.registry.ModBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.Sanguimancy;

import java.util.ArrayList;

public class RitualLighting extends Ritual {

    public RitualLighting() {
        super("ritualLighting", 0, 500, "ritual." + Sanguimancy.modid + ".illumination");
    }

    @Override
    public void performRitual(IMasterRitualStone masterRitualStone) {
        World world = masterRitualStone.getWorldObj();
        SoulNetwork network = NetworkHelper.getSoulNetwork(masterRitualStone.getOwner());
        int currentEssence = network.getCurrentEssence();

        if (currentEssence < getRefreshCost()) {
            network.causeNausea();
            return;
        }

        BlockPos pos = masterRitualStone.getBlockPos().add(world.rand.nextInt(15) - world.rand.nextInt(15), world.rand.nextInt(15) - world.rand.nextInt(15), world.rand.nextInt(15) - world.rand.nextInt(15));

        if (world.isAirBlock(pos) && world.getLight(pos) < 10) {
            world.setBlockState(pos, ModBlocks.bloodLight.getDefaultState());
            network.syphon(getRefreshCost());
        }
    }

    @Override
    public int getRefreshCost() {
        return 10;
    }

    @Override
    public ArrayList<RitualComponent> getComponents() {
        ArrayList<RitualComponent> components = new ArrayList<RitualComponent>();

        this.addParallelRunes(components, 1, 0, EnumRuneType.DUSK);
        this.addParallelRunes(components, 1, 1, EnumRuneType.DUSK);

        return components;
    }

    @Override
    public Ritual getNewCopy() {
        return new RitualLighting();
    }
}
