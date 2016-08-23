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

    public static Block illusion;
    public static Block bloodInterface;

    public static Block sacrificeTransfer;
    public static Block corruptionCrystallizer;
    public static Block bloodCleaner;

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

        bloodInterface = new BlockBloodInterface(Material.IRON).setRegistryName(Sanguimancy.modid, "BlockBloodInterface").setUnlocalizedName(Sanguimancy.modid + ".interface");
        GameRegistry.registerWithItem(bloodInterface);

        sacrificeTransfer = new BlockSacrificeTransfer(Material.IRON).setRegistryName(Sanguimancy.modid, "BlockSacrificeTransfer").setUnlocalizedName(Sanguimancy.modid + ".sacrificeTransfer");
        GameRegistry.registerWithItem(sacrificeTransfer);

        corruptionCrystallizer = new BlockCorruptionCrystallizer(Material.IRON).setRegistryName(Sanguimancy.modid, "BlockCorruptionCrystallizer").setUnlocalizedName(Sanguimancy.modid + ".corruptionCrystallizer");
        GameRegistry.registerWithItem(corruptionCrystallizer);

        bloodCleaner = new BlockBloodCleaner(Material.IRON).setRegistryName(Sanguimancy.modid, "BlockBloodCleaner").setUnlocalizedName(Sanguimancy.modid + ".bloodCleaner");
        GameRegistry.register(bloodCleaner);
        GameRegistry.register(new ItemBlockBloodCleaner(bloodCleaner));


        /*
        //Not updated yet
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
        */
    }
}
