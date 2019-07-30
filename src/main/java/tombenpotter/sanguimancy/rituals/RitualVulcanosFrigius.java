package tombenpotter.sanguimancy.rituals;

import WayofTime.bloodmagic.ritual.*;
import WayofTime.bloodmagic.core.data.SoulNetwork;
import WayofTime.bloodmagic.util.helper.NetworkHelper;
import WayofTime.bloodmagic.item.ItemComponent;
import WayofTime.bloodmagic.tile.TileAlchemyArray;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.Sanguimancy;

import java.util.ArrayList;

public class RitualVulcanosFrigius extends Ritual {

    public static final String COBBLESTONE_RANGE = "vulcanosFrigiusRange";

    public RitualVulcanosFrigius() {
        super("ritualVulcanosFrigius", 0, 500, "ritual." + Sanguimancy.modid + ".vulcanos.frigius");
        addBlockRange(COBBLESTONE_RANGE, new AreaDescriptor.Cross(new BlockPos(0, 1, 0), 1));
    }

    @Override
    public void performRitual(IMasterRitualStone masterRitualStone) {
        World world = masterRitualStone.getWorldObj();
        SoulNetwork network = NetworkHelper.getSoulNetwork(masterRitualStone.getOwner());
        int currentEssence = network.getCurrentEssence();
        TileEntity tileEntity = world.getTileEntity(masterRitualStone.getBlockPos().up());
        Block block = Blocks.COBBLESTONE;

        if (currentEssence < getRefreshCost()) {
            network.causeNausea();
            return;
        }

        int maxEffects = currentEssence / getRefreshCost();
        int totalEffects = 0;

        AreaDescriptor cobblestoneRange = getBlockRange(COBBLESTONE_RANGE);

        if (tileEntity != null && tileEntity instanceof TileAlchemyArray) {
            TileAlchemyArray alchemyArray = (TileAlchemyArray) tileEntity;
            if (alchemyArray.getStackInSlot(0) != null && alchemyArray.getStackInSlot(0).getItem() instanceof ItemComponent) {
                switch (alchemyArray.getStackInSlot(0).getItemDamage()) {
                    case 0:
                        block = Blocks.OBSIDIAN;
                        alchemyArray.decrStackSize(0, 1);
                        world.setBlockToAir(alchemyArray.getPos());
                        break;
                    case 1:
                        block = Blocks.NETHERRACK;
                        alchemyArray.decrStackSize(0, 1);
                        world.setBlockToAir(alchemyArray.getPos());
                        break;
                /*
                 * case 4: block = Blocks.end_stone;
                 * alchemyArray.decrStackSize(0, 1);
                 * world.setBlockToAir(alchemyArray.getPos()); break;
                 */
                    default:
                        break;
                }
            }
        }

        for (BlockPos blockPos : cobblestoneRange.getContainedPositions(masterRitualStone.getBlockPos())) {
            if (world.isAirBlock(blockPos)) {
                world.setBlockState(blockPos, block.getDefaultState());
                totalEffects++;
            }

            if (totalEffects >= maxEffects) {
                break;
            }
        }

        network.syphon(getRefreshCost() * totalEffects);
    }

    @Override
    public int getRefreshCost() {
        return 25;
    }

    @Override
    public ArrayList<RitualComponent> getComponents() {
        ArrayList<RitualComponent> components = new ArrayList<RitualComponent>();

        this.addCornerRunes(components, 1, 1, EnumRuneType.FIRE);
        this.addParallelRunes(components, 1, 0, EnumRuneType.WATER);

        return components;
    }

    @Override
    public Ritual getNewCopy() {
        return new RitualVulcanosFrigius();
    }
}