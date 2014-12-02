package tombenpotter.sanguimancy.util.interfaces;

import tombenpotter.sanguimancy.util.BlockPostition;

public interface ISNBranch {

    public BlockPostition getSNKnot();

    public boolean hasSNKnot();

    public BlockPostition[] getAdjacentBranches();
}
