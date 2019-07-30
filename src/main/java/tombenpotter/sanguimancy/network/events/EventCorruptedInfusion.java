package tombenpotter.sanguimancy.network.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;
import tombenpotter.sanguimancy.recipes.RecipeCorruptedInfusion;

public class EventCorruptedInfusion extends Event {
    public final RecipeCorruptedInfusion recipeCorruptedInfusion;

    public EventCorruptedInfusion(RecipeCorruptedInfusion recipe) {
        this.recipeCorruptedInfusion = recipe;
    }

    @Cancelable
    public static class EventPlayerCorruptedInfusion extends EventCorruptedInfusion {
        public final EntityPlayer entityPlayer;

        public EventPlayerCorruptedInfusion(EntityPlayer player, RecipeCorruptedInfusion recipe) {
            super(recipe);
            this.entityPlayer = player;
        }
    }
}
