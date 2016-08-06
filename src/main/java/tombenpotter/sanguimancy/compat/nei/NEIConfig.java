package tombenpotter.sanguimancy.compat.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import tombenpotter.oldsanguimancy.Sanguimancy;

public class NEIConfig implements IConfigureNEI {

    @Override
    public void loadConfig() {
        API.registerRecipeHandler(new NEICorruptedInfusion());
        API.registerUsageHandler(new NEICorruptedInfusion());
        API.registerUsageHandler(new NEIBloodCleanser());
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
