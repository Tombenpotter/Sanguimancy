package tombenpotter.sanguimancy.api.snManifestation;

import tombenpotter.sanguimancy.api.objects.BlockPostition;
import tombenpotter.sanguimancy.api.objects.BoolAndBlockPosList;

public interface ISNComponent {

    public BoolAndBlockPosList getAdjacentComponents(BlockPostition orignalPosition, BoolAndBlockPosList blockPosList);

    public BlockPostition[] getAdjacentISNComponents();

    public BoolAndBlockPosList getComponentsInNetwork();

    public boolean isSNKnot();

    public void onNetworkUpdate(BlockPostition originalPosition);
}
