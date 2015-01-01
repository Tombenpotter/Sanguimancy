package tombenpotter.sanguimancy.api.snManifestation;

import tombenpotter.sanguimancy.api.objects.BlockPostition;

import java.util.ArrayList;

public interface ISNKnot extends ISNComponent {

    public boolean isSNKnotactive();

    public String getSNKnotOwner();

    public void setSNKnotOwner(String owner);

    public void setKnotActive(boolean isActive);

    public ArrayList<BlockPostition> getSNParts();
}
