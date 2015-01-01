package tombenpotter.sanguimancy.api.snManifestation;

import tombenpotter.sanguimancy.api.objects.BlockPostition;
import tombenpotter.sanguimancy.api.EnumSNType;

import java.util.ArrayList;

public interface ISNPart extends ISNComponent {

    public EnumSNType getType();

    public ArrayList<BlockPostition> getSNKnots();
}
