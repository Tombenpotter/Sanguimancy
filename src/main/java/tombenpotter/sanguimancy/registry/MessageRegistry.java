package tombenpotter.sanguimancy.registry;

import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import tombenpotter.sanguimancy.api.objects.BlockAndMetadata;
import tombenpotter.sanguimancy.util.RandomUtils;

public class MessageRegistry {
    public static boolean registerMessage(String key, FMLInterModComms.IMCMessage message) {
        if (key.equals("transpositionBlacklist")) {
            if (message.isStringMessage()) {
                String[] splitSource = message.getStringValue().split(":");
                String modid = splitSource[0];
                String blockName = splitSource[1];
                Block block = GameRegistry.findBlock(modid, blockName);
                int meta = Integer.parseInt(splitSource[2]);
                if (block != null) {
                    RandomUtils.transpositionBlockBlacklist.add(new BlockAndMetadata(block, meta));
                }
            }
        }
        return false;
    }
}
