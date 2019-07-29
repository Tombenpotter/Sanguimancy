package tombenpotter.sanguimancy.api.snManifestation;

import java.util.ArrayList;

import net.minecraft.util.math.BlockPos;

public interface ISNKnot extends ISNComponent {

    public boolean isSNKnotactive();

    public String getSNKnotOwner();

    public void setSNKnotOwner(String owner);

    public void setKnotActive(boolean isActive);

    public ArrayList<BlockPos> getSNParts();
}
