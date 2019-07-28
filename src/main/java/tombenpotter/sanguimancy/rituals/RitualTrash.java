package tombenpotter.sanguimancy.rituals;

import WayofTime.bloodmagic.ritual.*;
import WayofTime.bloodmagic.core.data.SoulNetwork;
import WayofTime.bloodmagic.util.helper.NetworkHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.Sanguimancy;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class RitualTrash extends Ritual {

    public static final String TRASH_RANGE = "trashRange";

    public RitualTrash() {
        super("ritualTrash", 0, 500, "ritual." + Sanguimancy.modid + ".trash");
        addBlockRange(TRASH_RANGE, new AreaDescriptor.Rectangle(new BlockPos(0, 0, 0), new BlockPos(1, 1, 1)));
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

        AreaDescriptor area = getBlockRange(TRASH_RANGE);
        List<EntityItem> itemList = world.getEntitiesWithinAABB(EntityItem.class, area.getAABB(masterRitualStone.getBlockPos().up()));

        if (itemList.isEmpty()) {
            return;
        }

        for (EntityItem entityItem : itemList) {
            network.syphon(getRefreshCost() * entityItem.getEntityItem().stackSize);
            entityItem.setDead();
        }
    }

    @Override
    public int getRefreshCost() {
        return 25;
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
        return new RitualTrash();
    }

	@Override
	public void gatherComponents(Consumer<RitualComponent> arg0) {
		// TODO Auto-generated method stub
		
	}
}
