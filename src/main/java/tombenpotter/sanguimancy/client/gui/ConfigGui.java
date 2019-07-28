package tombenpotter.sanguimancy.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import tombenpotter.sanguimancy.Sanguimancy;

import java.util.ArrayList;
import java.util.List;

import static tombenpotter.sanguimancy.util.ConfigHandler.*;

public class ConfigGui extends GuiConfig {

    public ConfigGui(GuiScreen parentScreen) {
        super(parentScreen, getConfigElements(parentScreen), Sanguimancy.modid, false, false, I18n.format("gui." + Sanguimancy.modid + ".config.title"));
    }

    private static List<IConfigElement> getConfigElements(GuiScreen parent) {
        List<IConfigElement> list = new ArrayList<IConfigElement>();

        list.add(new ConfigElement(new ConfigCategory(config.getCategory(rituals.toLowerCase()).getName())));
        list.add(new ConfigElement(new ConfigCategory(config.getCategory(balancing.toLowerCase()).getName())));
        list.add(new ConfigElement(new ConfigCategory(config.getCategory(features.toLowerCase()).getName())));

        return list;
    }
}
