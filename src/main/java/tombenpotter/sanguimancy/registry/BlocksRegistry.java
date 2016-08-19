package tombenpotter.sanguimancy.registry;

import WayofTime.bloodmagic.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tombenpotter.oldsanguimancy.blocks.*;
import tombenpotter.oldsanguimancy.blocks.items.ItemBlockBloodCleanser;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.blocks.BlockAltarDiviner;
import tombenpotter.sanguimancy.blocks.BlockAltarEmitter;
import tombenpotter.sanguimancy.blocks.BlockAltarManipulator;
import tombenpotter.sanguimancy.blocks.BlockIllusion;
import tombenpotter.sanguimancy.blocks.items.ItemBlockAltarDiviner;
import tombenpotter.sanguimancy.blocks.items.ItemBlockAltarEmitter;
import tombenpotter.sanguimancy.blocks.items.ItemBlockAltarManipulator;
import tombenpotter.sanguimancy.blocks.items.ItemBlockIllusion;

public class BlocksRegistry {

    public static Block altarEmitter;
    public static Block altarDiviner;
    public static Block altarManipulator;

    public static Block sacrificeTransfer;
    public static Block illusion;
    public static Block corruptionCrystallizer;
    public static Block lumpCleaner;
    public static Block bloodStoneStairs;
    public static Block largeBloodStoneStairs;
    public static Block bloodstoneSlab;
    public static Block largeBloodstoneSlab;
    public static Block bloodInterface;
    public static Block etherealBlock;
    public static Block etherealCorruptedBlock;
    public static Block etherealToggledBlock;
    public static Block etherealBoundBlock;
    public static Block etherealBoundCorruptedBlock;
    public static Block etherealBoundToggledBlock;
    public static Block etherealPersonalBlock;

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

        illusion = new BlockIllusion(Material.IRON).setRegistryName(Sanguimancy.modid, "BlockIllusion").setUnlocalizedName(Sanguimancy.modid + ".illusion").setLightLevel(1F);
        GameRegistry.register(illusion);
        GameRegistry.register(new ItemBlockIllusion(illusion));
        
        //Not updated yet
        sacrificeTransfer = new BlockSacrificeTransfer(Material.iron);
        GameRegistry.registerBlock(sacrificeTransfer, "BlockSacrificeTransfer").setBlockName(Sanguimancy.modid + ".sacrificeTransfer");

        corruptionCrystallizer = new BlockCorruptionCrystallizer(Material.iron);
        GameRegistry.registerBlock(corruptionCrystallizer, "BlockCorruptionCrystallizer").setBlockName(Sanguimancy.modid + ".corruptionCrystallizer");

        lumpCleaner = new BlockLumpCleaner(Material.iron);
        GameRegistry.registerBlock(lumpCleaner, ItemBlockBloodCleanser.class, "BlockLumpCleaner").setBlockName(Sanguimancy.modid + ".lumpCleaner");

        bloodStoneStairs = new BlockBloodStoneStair(ModBlocks.bloodStoneBrick, 0);
        GameRegistry.registerBlock(bloodStoneStairs, "BlockBloodStoneStairs").setBlockName(Sanguimancy.modid + ".bloodStoneStair");

        largeBloodStoneStairs = new BlockLargeBloodStoneStair(ModBlocks.largeBloodStoneBrick, 0);
        GameRegistry.registerBlock(largeBloodStoneStairs, "BlockLargeBloodStoneStairs").setBlockName(Sanguimancy.modid + ".largeBloodStoneStair");

        bloodstoneSlab = new BlockBloodstoneSlab(false, Material.rock);
        GameRegistry.registerBlock(bloodstoneSlab, "BlockBloodStoneSlab").setBlockName(Sanguimancy.modid + ".bloodStoneSlab");

        largeBloodstoneSlab = new BlockLargeBloodstoneSlab(false, Material.rock);
        GameRegistry.registerBlock(largeBloodstoneSlab, "BlockLargeBloodStoneSlab").setBlockName(Sanguimancy.modid + ".largeBloodStoneSlab");

        bloodInterface = new BlockBloodInterface();
        GameRegistry.registerBlock(bloodInterface, "BlockBloodInterface").setBlockName(Sanguimancy.modid + ".interface");

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
    }
}
