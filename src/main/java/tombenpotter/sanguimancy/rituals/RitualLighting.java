package tombenpotter.sanguimancy.rituals;

import WayofTime.bloodmagic.ritual.EnumRuneType;
import WayofTime.bloodmagic.ritual.IMasterRitualStone;
import WayofTime.bloodmagic.ritual.Ritual;
import WayofTime.bloodmagic.ritual.RitualComponent;
import WayofTime.bloodmagic.core.data.SoulNetwork;
import WayofTime.bloodmagic.core.RegistrarBloodMagicBlocks;
import WayofTime.bloodmagic.util.helper.NetworkHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.Sanguimancy;

import java.util.ArrayList;
import java.util.function.Consumer;

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
            world.setBlockState(pos, RegistrarBloodMagicBlocks.BLOOD_LIGHT.getDefaultState());
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

	@Override
	public void gatherComponents(Consumer<RitualComponent> arg0) {
		// TODO Auto-generated method stub
		
	}
}
