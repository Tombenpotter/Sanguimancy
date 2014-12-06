package tombenpotter.sanguimancy.util.interfaces;

import tombenpotter.sanguimancy.util.BlockPostition;
import tombenpotter.sanguimancy.util.enums.EnumSNType;

import java.util.ArrayList;

public interface ISNPart extends ISNComponent {

    public EnumSNType getType();

    public ArrayList<BlockPostition> getSNKnots();
}
