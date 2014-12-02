package tombenpotter.sanguimancy.util.interfaces;

import tombenpotter.sanguimancy.util.BlockPostition;
import tombenpotter.sanguimancy.util.enums.EnumSNType;

public interface ISNPart {

    public EnumSNType getType();

    public BlockPostition getSNKnot();

    public boolean hasSNKnot();

    public BlockPostition[] getAdjacentBranches();
}
