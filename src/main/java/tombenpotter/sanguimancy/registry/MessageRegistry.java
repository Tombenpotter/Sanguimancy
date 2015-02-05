package tombenpotter.sanguimancy.registry;

import cpw.mods.fml.common.event.FMLInterModComms;
import net.minecraft.block.Block;
import tombenpotter.sanguimancy.util.RandomUtils;

public class MessageRegistry
{
    public static boolean registerMessage(String key, FMLInterModComms.IMCMessage message)
    {
        if (key.equals("transpositionBlacklist"))
        {
            if (message.isStringMessage())
            {
                Block blacklist = Block.getBlockFromName(message.getStringValue());
                if (blacklist != null)
                {
                    RandomUtils.transpositionBlockBlacklist.add(blacklist);
                    return true;
                }
            }
        }
        return false;
    }
}
