package tombenpotter.sanguimancy.network;

import cpw.mods.fml.common.eventhandler.Cancelable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import tombenpotter.sanguimancy.recipes.CorruptedInfusionRecipe;

@Cancelable
public class EventPlayerCorruptedInfusion extends PlayerEvent {

    public final CorruptedInfusionRecipe recipe;

    public EventPlayerCorruptedInfusion(EntityPlayer player, CorruptedInfusionRecipe recipe) {
        super(player);
        this.recipe = recipe;
    }

    public static final void fireEvent(EventPlayerCorruptedInfusion event) {
        MinecraftForge.EVENT_BUS.post(event);
    }
}
