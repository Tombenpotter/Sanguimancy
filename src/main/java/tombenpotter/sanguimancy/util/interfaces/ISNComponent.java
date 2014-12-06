package tombenpotter.sanguimancy.util.interfaces;

import tombenpotter.sanguimancy.util.BlockPostition;
import tombenpotter.sanguimancy.util.BoolAndBlockPosList;

public interface ISNComponent {

    public BoolAndBlockPosList getAdjacentComponents(BlockPostition orignalPosition, BoolAndBlockPosList blockPosList);

    public BlockPostition[] getAdjacentISNComponents();

    public BoolAndBlockPosList getComponentsInNetwork();

    public boolean isSNKnot();
}
