package tombenpotter.sanguimancy.registry;

import WayofTime.alchemicalWizardry.ModBlocks;
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
    public static Block dimensionalPortal;
    public static Block bloodStoneStairs;
    public static Block largeBloodStoneStairs;
    public static Block bloodstoneSlab;
    public static Block doubleBloodstoneSlab;
    public static Block largeBloodstoneSlab;
    public static Block doubleLargeBloodstoneSlab;
    public static Block boundItem;
    public static Block bloodInterface;

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

        dimensionalPortal = new BlockDimensionalPortal(Material.iron);
        GameRegistry.registerBlock(dimensionalPortal, "BlockDimensionalPortal").setBlockName(Sanguimancy.modid + ".dimensionalPortal");

        bloodStoneStairs = new BlockBloodStoneStair(ModBlocks.bloodStoneBrick, 0);
        GameRegistry.registerBlock(bloodStoneStairs, "BlockBloodStoneStairs").setBlockName(Sanguimancy.modid + ".bloodStoneStair");

        largeBloodStoneStairs = new BlockLargeBloodStoneStair(ModBlocks.largeBloodStoneBrick, 0);
        GameRegistry.registerBlock(largeBloodStoneStairs, "BlockLargeBloodStoneStairs").setBlockName(Sanguimancy.modid + ".largeBloodStoneStair");

        bloodstoneSlab = new BlockBloodstoneSlab(false, Material.rock);
        GameRegistry.registerBlock(bloodstoneSlab, "BlockBloodStoneSlab").setBlockName(Sanguimancy.modid + ".bloodStoneSlab");

        doubleBloodstoneSlab = new BlockBloodstoneSlab(true, Material.rock);
        GameRegistry.registerBlock(doubleBloodstoneSlab, "BlockDoubleBloodStoneSlab").setBlockName(Sanguimancy.modid + ".doubleBloodStoneSlab");

        largeBloodstoneSlab = new BlockLargeBloodstoneSlab(false, Material.rock);
        GameRegistry.registerBlock(largeBloodstoneSlab, "BlockLargeBloodStoneSlab").setBlockName(Sanguimancy.modid + ".largeBloodStoneSlab");

        doubleLargeBloodstoneSlab = new BlockLargeBloodstoneSlab(true, Material.rock);
        GameRegistry.registerBlock(doubleLargeBloodstoneSlab, "BlockDoubleLargeBloodStoneSlab").setBlockName(Sanguimancy.modid + ".doubleLargeBloodStoneSlab");

        boundItem = new BlockBoundItem(Material.iron);
        GameRegistry.registerBlock(boundItem, "BlockBoundItem").setBlockName(Sanguimancy.modid + ".boundItem");

        bloodInterface = new BlockBloodInterface();
        GameRegistry.registerBlock(bloodInterface, "interface").setBlockName(Sanguimancy.modid + ".interface");
    }
}
