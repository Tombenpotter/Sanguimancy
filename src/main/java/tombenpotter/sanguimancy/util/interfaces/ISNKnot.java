package tombenpotter.sanguimancy.util.interfaces;

import tombenpotter.sanguimancy.util.BlockPostition;

import java.util.ArrayList;

public interface ISNKnot extends ISNComponent {

    public boolean isSNKnotactive();

    public String getSNKnotOwner();

    public void setSNKnotOwner(String owner);

    public void setKnotActive(boolean isActive);

    public ArrayList<BlockPostition> getSNParts();
}
