package tombenpotter.oldsanguimancy.api.snManifestation;

import tombenpotter.oldsanguimancy.api.objects.BlockPostition;
import tombenpotter.oldsanguimancy.api.objects.BoolAndBlockPosList;

import java.util.ArrayList;

public interface ISNComponent {

    public BoolAndBlockPosList getAdjacentComponents(BlockPostition orignalPosition, BoolAndBlockPosList blockPosList);

    public BlockPostition[] getAdjacentISNComponents();

    public BoolAndBlockPosList getComponentsInNetwork();

    public boolean isSNKnot();

    public void onNetworkUpdate(BlockPostition originalPosition);

    public ArrayList<BlockPostition> getSNKnots();
}
