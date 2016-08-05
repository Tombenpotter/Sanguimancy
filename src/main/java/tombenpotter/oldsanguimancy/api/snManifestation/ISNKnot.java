package tombenpotter.oldsanguimancy.api.snManifestation;

import tombenpotter.oldsanguimancy.api.objects.BlockPostition;

import java.util.ArrayList;

public interface ISNKnot extends ISNComponent {

    public boolean isSNKnotactive();

    public String getSNKnotOwner();

    public void setSNKnotOwner(String owner);

    public void setKnotActive(boolean isActive);

    public ArrayList<BlockPostition> getSNParts();
}