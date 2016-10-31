package tombenpotter.sanguimancy.registry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.blocks.*;
import tombenpotter.sanguimancy.blocks.items.*;

public class BlocksRegistry {

    public static Block altarEmitter;
    public static Block altarDiviner;
    public static Block altarManipulator;

    public static Block bloodInterface;

    public static Block sacrificeTransfer;
    public static Block bloodCleaner;

    public static void registerBlocks() {
        altarEmitter = new BlockAltarEmitter(Material.IRON).setRegistryName(Sanguimancy.modid, "BlockAltarEmitter").setUnlocalizedName(Sanguimancy.modid + ".altarEmitter");
        GameRegistry.register(altarEmitter);
        GameRegistry.register(new ItemBlockAltarEmitter(altarEmitter));

        altarDiviner = new BlockAltarDiviner(Material.IRON).setRegistryName(Sanguimancy.modid, "BlockAltarDiviner").setUnlocalizedName(Sanguimancy.modid + ".altarDiviner");
        GameRegistry.register(altarDiviner);
        GameRegistry.register(new ItemBlockAltarDiviner(altarDiviner));

        altarManipulator = new BlockAltarManipulator(Material.IRON).setRegistryName(Sanguimancy.modid, "BlockAltarManipulator").setUnlocalizedName(Sanguimancy.modid + ".altarManipulator");
        GameRegistry.register(altarManipulator);
        GameRegistry.register(new ItemBlockAltarManipulator(altarManipulator));

        bloodInterface = new BlockBloodInterface(Material.IRON).setRegistryName(Sanguimancy.modid, "BlockBloodInterface").setUnlocalizedName(Sanguimancy.modid + ".interface");
        GameRegistry.registerWithItem(bloodInterface);

        sacrificeTransfer = new BlockSacrificeTransfer(Material.IRON).setRegistryName(Sanguimancy.modid, "BlockSacrificeTransfer").setUnlocalizedName(Sanguimancy.modid + ".sacrificeTransfer");
        GameRegistry.registerWithItem(sacrificeTransfer);

        bloodCleaner = new BlockBloodCleaner(Material.IRON).setRegistryName(Sanguimancy.modid, "BlockBloodCleaner").setUnlocalizedName(Sanguimancy.modid + ".bloodCleaner");
        GameRegistry.register(bloodCleaner);
        GameRegistry.register(new ItemBlockBloodCleaner(bloodCleaner));
    }
}
