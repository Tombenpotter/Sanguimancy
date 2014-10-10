package tombenpotter.sanguimancy.compat;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import tombenpotter.sanguimancy.Sanguimancy;

public class NEIConfig implements IConfigureNEI {

    @Override
    public void loadConfig() {
        API.registerRecipeHandler(new NEICompat());
        API.registerUsageHandler(new NEICompat());
    }

    @Override
    public String getName() {
        return Sanguimancy.name;
    }

    @Override
    public String getVersion() {
        return Sanguimancy.version;
    }
}
