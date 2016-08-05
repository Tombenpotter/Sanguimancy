package tombenpotter.oldsanguimancy.api.snManifestation;

import tombenpotter.oldsanguimancy.api.objects.BlockPostition;

import java.util.ArrayList;

public interface ISNPart extends ISNComponent {

    public EnumSNType getType();

    public ArrayList<BlockPostition> getSNKnots();
}
