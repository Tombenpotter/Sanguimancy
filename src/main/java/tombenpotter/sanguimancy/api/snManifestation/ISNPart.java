package tombenpotter.sanguimancy.api.snManifestation;

import java.util.ArrayList;

import net.minecraft.util.math.BlockPos;

public interface ISNPart extends ISNComponent {
    public EnumSNType getType();

    public ArrayList<BlockPos> getSNKnots();
}
