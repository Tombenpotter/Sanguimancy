package tombenpotter.sanguimancy.registry;

import WayofTime.alchemicalWizardry.ModBlocks;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.blocks.*;
import tombenpotter.sanguimancy.blocks.items.*;

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
    public static Block largeBloodstoneSlab;
    public static Block bloodInterface;
    public static Block simpleKnot;
    public static Block toggleKnot;
    public static Block simpleBranch;
    public static Block boundItem;
    public static Block ritualRepresentation;
    public static Block etherealBlock;
    public static Block etherealCorruptedBlock;
    public static Block etherealToggledBlock;
    public static Block etherealBoundBlock;
    public static Block etherealBoundCorruptedBlock;
    public static Block etherealBoundToggledBlock;
    public static Block etherealPersonalBlock;
    public static Block altarManipulator;

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
        GameRegistry.registerBlock(lumpCleaner, ItemBlockBloodCleanser.class, "BlockLumpCleaner").setBlockName(Sanguimancy.modid + ".lumpCleaner");

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

        largeBloodstoneSlab = new BlockLargeBloodstoneSlab(false, Material.rock);
        GameRegistry.registerBlock(largeBloodstoneSlab, "BlockLargeBloodStoneSlab").setBlockName(Sanguimancy.modid + ".largeBloodStoneSlab");

        boundItem = new BlockItemSNPart(Material.iron);
        GameRegistry.registerBlock(boundItem, "BlockItemSNPart").setBlockName(Sanguimancy.modid + ".itemSNPart");

        bloodInterface = new BlockBloodInterface();
        GameRegistry.registerBlock(bloodInterface, "BlockBloodInterface").setBlockName(Sanguimancy.modid + ".interface");

        simpleKnot = new BlockSimpleSNKnot(Material.iron);
        GameRegistry.registerBlock(simpleKnot, "BlockSimpleSoulKnot").setBlockName(Sanguimancy.modid + ".simpleSNKnot");

        toggleKnot = new BlockToggleSNKnot(Material.iron);
        GameRegistry.registerBlock(toggleKnot, "BlockToggledSoulKnot").setBlockName(Sanguimancy.modid + ".toggleSNKnot");

        simpleBranch = new BlockSimpleSNBranch(Material.iron);
        GameRegistry.registerBlock(simpleBranch, "BlockSimpleSoulBranch").setBlockName(Sanguimancy.modid + ".simpleSNBranch");

        ritualRepresentation = new BlockRitualSNPart(Material.iron);
        GameRegistry.registerBlock(ritualRepresentation, "BlockRitualRepresentation").setBlockName(Sanguimancy.modid + ".ritualRepresentation");

        etherealBlock = new BlockEthereal(Material.iron);
        GameRegistry.registerBlock(etherealBlock, "BlockEthereal").setBlockName(Sanguimancy.modid + ".etherealBlock");

        etherealCorruptedBlock = new BlockEtherealCorrupted(Material.iron);
        GameRegistry.registerBlock(etherealCorruptedBlock, "BlockEtherealCorrupted").setBlockName(Sanguimancy.modid + ".etherealCorruptedBlock");

        etherealToggledBlock = new BlockEtherealToggled(Material.iron);
        GameRegistry.registerBlock(etherealToggledBlock, "BlockEtehrealToggled").setBlockName(Sanguimancy.modid + ".etherealToggledBlock");

        etherealBoundBlock = new BlockEtherealBound(Material.iron);
        GameRegistry.registerBlock(etherealBoundBlock, "BlockEtherealBound").setBlockName(Sanguimancy.modid + ".etherealBoundBlock");

        etherealBoundCorruptedBlock = new BlockEtherealBoundCorrupted(Material.iron);
        GameRegistry.registerBlock(etherealBoundCorruptedBlock, "BlockEtherealBoundCorrupted").setBlockName(Sanguimancy.modid + ".etherealBoundCorruptedBlock");

        etherealBoundToggledBlock = new BlockEtherealBoundToggled(Material.iron);
        GameRegistry.registerBlock(etherealBoundToggledBlock, "BlockEtherealBoundToggled").setBlockName(Sanguimancy.modid + ".etherealBoundToggledBlock");

        etherealPersonalBlock = new BlockEtherealPersonal(Material.iron);
        GameRegistry.registerBlock(etherealPersonalBlock, "BlockEtherealPersonal").setBlockName(Sanguimancy.modid + ".etherealPersonalBlock");

        altarManipulator = new BlockAltarManipulator(Material.iron);
        GameRegistry.registerBlock(altarManipulator, ItemBlockAltarManipulator.class, "BlockAltarManipulator").setBlockName(Sanguimancy.modid + ".altarManipulator");
    }
}
