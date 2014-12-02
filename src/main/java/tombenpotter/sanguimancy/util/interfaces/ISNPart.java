package tombenpotter.sanguimancy.util.interfaces;

import tombenpotter.sanguimancy.util.BlockPostition;
import tombenpotter.sanguimancy.util.BoolAndBlockPosList;
import tombenpotter.sanguimancy.util.enums.EnumSNType;

import java.util.ArrayList;

public interface ISNPart {

    public EnumSNType getType();

    public BoolAndBlockPosList getComponentsInNetwork();

    public BlockPostition[] getAdjacentComponents();

    public ArrayList<BlockPostition> getSNKnots();
}
