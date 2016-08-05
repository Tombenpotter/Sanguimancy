package tombenpotter.oldsanguimancy.compat.waila;

import cpw.mods.fml.common.event.FMLInterModComms;

public class WailaCompatRegistry {

    public static void register() {
        FMLInterModComms.sendMessage("Waila", "register", "tombenpotter.sanguimancy.compat.waila.WailaCorruptionCrystallizer.register");
        FMLInterModComms.sendMessage("Waila", "register", "tombenpotter.sanguimancy.compat.waila.WailaAltarDiviner.register");
        FMLInterModComms.sendMessage("Waila", "register", "tombenpotter.sanguimancy.compat.waila.WailaAltarEmitter.register");
        FMLInterModComms.sendMessage("Waila", "register", "tombenpotter.sanguimancy.compat.waila.WailaBloodTank.register");
    }
}
