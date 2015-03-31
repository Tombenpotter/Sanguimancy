package tombenpotter.sanguimancy.api.snManifestation;

import tombenpotter.sanguimancy.api.objects.BlockPostition;

import java.util.ArrayList;

public interface ISNPart extends ISNComponent {

    public EnumSNType getType();

    public ArrayList<BlockPostition> getSNKnots();
}
