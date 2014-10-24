package tombenpotter.sanguimancy.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.blocks.*;
import tombenpotter.sanguimancy.blocks.items.ItemBlockAltarDiviner;
import tombenpotter.sanguimancy.blocks.items.ItemBlockAltarEmitter;
import tombenpotter.sanguimancy.blocks.items.ItemBlockBloodTank;
import tombenpotter.sanguimancy.blocks.items.ItemBlockIllusion;

public class BlocksRegistry {

    public static Block altarDiviner;
    public static Block altarEmitter;
    public static Block sacrificeTransfer;
    public static Block illusion;
    public static Block corruptionCrystallizer;
    public static Block lumpCleaner;
    public static Block bloodTank;

    public static void registerBlocks() {
        altarDiviner = new BlockAltarDiviner(Material.iron);
        GameRegistry.registerBlock(altarDiviner, ItemBlockAltarDiviner.class, "BlockAltarDiviner").setBlockName(Sanguimancy.modid + ".altarDiviner");

        altarEmitter = new BlockAltarEmitter(Material.iron);
        GameRegistry.registerBlock(altarEmitter, ItemBlockAltarEmitter.class, "BlockAltarEmitter").setBlockName(Sanguimancy.modid + ".altarEmitter");

        sacrificeTransfer = new BlockSacrificeTransfer(Material.iron);
        GameRegistry.registerBlock(sacrificeTransfer, "BlockSacrificeTransfer").setBlockName(Sanguimancy.modid + ".sacrificeTransfer");

        illusion = new BlockIllusion(Material.iron);
        GameRegistry.registerBlock(illusion, ItemBlockIllusion.class, "BlockIllusion").setBlockName(Sanguimancy.modid + ".illusion").setLightLevel(1F);

        corruptionCrystallizer = new BlockCorruptionCrystallizer(Material.iron);
        GameRegistry.registerBlock(corruptionCrystallizer, "BlockCorruptionCrystallizer").setBlockName(Sanguimancy.modid + ".corruptionCrystallizer");

        lumpCleaner = new BlockLumpCleaner(Material.iron);
        GameRegistry.registerBlock(lumpCleaner, "BlockLumpCleaner").setBlockName(Sanguimancy.modid + ".lumpCleaner");

        bloodTank = new BlockBloodTank(Material.iron);
        GameRegistry.registerBlock(bloodTank, ItemBlockBloodTank.class, "BlockBloodTank").setBlockName(Sanguimancy.modid + ".bloodTank");
    }
}
