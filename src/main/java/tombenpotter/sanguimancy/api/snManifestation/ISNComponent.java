package tombenpotter.sanguimancy.api.snManifestation;

import tombenpotter.sanguimancy.api.objects.BoolAndBlockPosList;

import java.util.ArrayList;

import net.minecraft.util.math.BlockPos;

public interface ISNComponent {

    public BoolAndBlockPosList getAdjacentComponents(BlockPos orignalPosition, BoolAndBlockPosList blockPosList);

    public BlockPos[] getAdjacentISNComponents();

    public BoolAndBlockPosList getComponentsInNetwork();

    public boolean isSNKnot();

    public void onNetworkUpdate(BlockPos originalPosition);

    public ArrayList<BlockPos> getSNKnots();
}
