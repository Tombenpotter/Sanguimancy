package tombenpotter.sanguimancy.network;

import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.player.EntityPlayer;
import tombenpotter.sanguimancy.recipes.CorruptedInfusionRecipe;

public class EventCorruptedInfusion extends Event {

    public final CorruptedInfusionRecipe corruptedInfusionRecipe;

    public EventCorruptedInfusion(CorruptedInfusionRecipe recipe) {
        this.corruptedInfusionRecipe = recipe;
    }

    @Cancelable
    public static class EventPlayerCorruptedInfusion extends EventCorruptedInfusion {

        public final EntityPlayer entityPlayer;

        public EventPlayerCorruptedInfusion(EntityPlayer player, CorruptedInfusionRecipe recipe) {
            super(recipe);
            this.entityPlayer = player;
        }
    }
}
