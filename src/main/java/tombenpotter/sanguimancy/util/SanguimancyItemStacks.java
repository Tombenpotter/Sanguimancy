package tombenpotter.sanguimancy.util;

import net.minecraft.item.ItemStack;
import tombenpotter.sanguimancy.registry.BlocksRegistry;
import tombenpotter.sanguimancy.registry.ItemsRegistry;

public class SanguimancyItemStacks {

    // Items
    public static ItemStack unattunedPlayerSacrificer = new ItemStack(ItemsRegistry.playerSacrificer, 1, 0);
    public static ItemStack attunnedPlayerSacrificer = new ItemStack(ItemsRegistry.playerSacrificer, 1, 1);
    public static ItemStack focusedPlayerSacrificer = new ItemStack(ItemsRegistry.playerSacrificer, 1, 2);
    public static ItemStack wayToDie = new ItemStack(ItemsRegistry.playerSacrificer, 1, 3);
    public static ItemStack corruptedDemonShard = new ItemStack(ItemsRegistry.resource, 1, 0);
    public static ItemStack bloodAmulet = new ItemStack(ItemsRegistry.bloodAmulet);
    /*
    public static ItemStack wand = new ItemStack(ItemsRegistry.wand);
    public static ItemStack corruptedSword = new ItemStack(ItemsRegistry.corruptedSword);
    public static ItemStack corruptedPickaxe = new ItemStack(ItemsRegistry.corruptedPickaxe);
    public static ItemStack corruptedShovel = new ItemStack(ItemsRegistry.corruptedShovel);
    public static ItemStack corruptedAxe = new ItemStack(ItemsRegistry.corruptedAxe);
    */
    public static ItemStack corruptedMineral = new ItemStack(ItemsRegistry.resource, 1, 1);
    public static ItemStack imbuedStick = new ItemStack(ItemsRegistry.resource, 1, 2);
    public static ItemStack etherealManifestation = new ItemStack(ItemsRegistry.resource, 1, 3);
    public static ItemStack sanguineShifter = new ItemStack(ItemsRegistry.resource, 1, 4);

    // Blocks
    public static ItemStack altarEmitter = new ItemStack(BlocksRegistry.altarEmitter);
    public static ItemStack altarDiviner = new ItemStack(BlocksRegistry.altarDiviner);
    public static ItemStack sacrificeTransferrer = new ItemStack(BlocksRegistry.sacrificeTransfer);
    public static ItemStack corruptionCrystallizer = new ItemStack(BlocksRegistry.corruptionCrystallizer);
    public static ItemStack lumpCleaner = new ItemStack(BlocksRegistry.bloodCleaner);
    public static ItemStack bloodInterface = new ItemStack(BlocksRegistry.bloodInterface);
    public static ItemStack altarManipulator = new ItemStack(BlocksRegistry.altarManipulator);
}