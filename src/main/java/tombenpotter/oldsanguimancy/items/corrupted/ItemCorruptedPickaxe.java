package tombenpotter.oldsanguimancy.items.corrupted;

import WayofTime.alchemicalWizardry.api.items.interfaces.IBindable;
import WayofTime.alchemicalWizardry.common.items.EnergyItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import tombenpotter.oldsanguimancy.Sanguimancy;
import tombenpotter.oldsanguimancy.api.soulCorruption.SoulCorruptionHelper;
import tombenpotter.sanguimancy.util.ConfigHandler;
import tombenpotter.sanguimancy.util.RandomUtils;

import java.util.List;

public class ItemCorruptedPickaxe extends ItemPickaxe implements IBindable {

    public int minimumCorruption = ConfigHandler.minimumToolCorruption;
    public IIcon silkTouch, fortuneI, fortuneII, fortuneIII, autosmelt;

    public ItemCorruptedPickaxe(ToolMaterial material) {
        super(material);
        setCreativeTab(Sanguimancy.tabSanguimancy);
        setUnlocalizedName(Sanguimancy.modid + ".corruptedPickaxe");
        setMaxDamage(0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        this.itemIcon = ir.registerIcon(Sanguimancy.texturePath + ":CorruptedPickaxe");
        this.silkTouch = ir.registerIcon(Sanguimancy.texturePath + ":CorruptedPickaxe_SilkTouch");
        this.fortuneI = ir.registerIcon(Sanguimancy.texturePath + ":CorruptedPickaxe_FortuneI");
        this.fortuneII = ir.registerIcon(Sanguimancy.texturePath + ":CorruptedPickaxe_FortuneII");
        this.fortuneIII = ir.registerIcon(Sanguimancy.texturePath + ":CorruptedPickaxe_FortuneIII");
        this.autosmelt = ir.registerIcon(Sanguimancy.texturePath + ":CorruptedPickaxe_AutoSmelt");
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        RandomUtils.checkAndSetCompound(stack);
        if (getToolMode(stack) == 1) return silkTouch;
        else if (getToolMode(stack) == 2) return fortuneI;
        else if (getToolMode(stack) == 3) return fortuneII;
        else if (getToolMode(stack) == 4) return fortuneIII;
        else if (getToolMode(stack) == 5) return autosmelt;
        else return this.itemIcon;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase entityLivingBase) {
        if (block.getBlockHardness(world, x, y, z) >= 0) {
            RandomUtils.checkAndSetCompound(stack);
            int toolMode = getToolMode(stack);
            int metadata = world.getBlockMetadata(x, y, z);
            int lpConsumption = 10;
            if (toolMode == 0) {
                RandomUtils.dropBlockDropsWithFortune(world, block, x, y, z, metadata, 0);
                world.setBlockToAir(x, y, z);
            } else if (toolMode == 1) {
                lpConsumption = lpConsumption * 5;
                RandomUtils.dropSilkDrops(world, block, x, y, z, metadata);
                world.setBlockToAir(x, y, z);
            } else if (toolMode == 2) {
                lpConsumption = lpConsumption * 5;
                RandomUtils.dropBlockDropsWithFortune(world, block, x, y, z, metadata, 1);
                world.setBlockToAir(x, y, z);
            } else if (toolMode == 3) {
                lpConsumption = lpConsumption * 10;
                RandomUtils.dropBlockDropsWithFortune(world, block, x, y, z, metadata, 2);
                world.setBlockToAir(x, y, z);
            } else if (toolMode == 4) {
                lpConsumption = lpConsumption * 20;
                RandomUtils.dropBlockDropsWithFortune(world, block, x, y, z, metadata, 3);
                world.setBlockToAir(x, y, z);
            } else if (toolMode == 5) {
                lpConsumption = lpConsumption * 15;
                RandomUtils.dropSmeltDrops(world, block, x, y, z, metadata);
                world.setBlockToAir(x, y, z);
            }
            if (entityLivingBase instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entityLivingBase;
                EnergyItems.syphonBatteries(stack, player, lpConsumption);
                if (getToolMode(stack) != 0) {
                    if (world.rand.nextInt(20) == 0) {
                        SoulCorruptionHelper.incrementCorruption(world.getPlayerEntityByName(RandomUtils.getItemOwner(stack)));
                    }
                }
            }
        }
        return getToolMode(stack) <= 5;
    }

    public int getToolMode(ItemStack stack) {
        if (stack != null) {
            RandomUtils.checkAndSetCompound(stack);
            return stack.stackTagCompound.getInteger("ToolMode");
        }
        return 0;
    }

    public void setToolMode(ItemStack stack, int mode) {
        RandomUtils.checkAndSetCompound(stack);
        stack.stackTagCompound.setInteger("ToolMode", mode);
    }

    public void nextToolMode(ItemStack stack) {
        RandomUtils.checkAndSetCompound(stack);
        if (stack.stackTagCompound.getInteger("ToolMode") + 1 <= 5) {
            setToolMode(stack, stack.stackTagCompound.getInteger("ToolMode") + 1);
        } else {
            stack.stackTagCompound.setInteger("ToolMode", 0);
            setToolMode(stack, 0);
        }
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        RandomUtils.checkAndSetCompound(stack);
        setToolMode(stack, 0);
        super.onCreated(stack, world, player);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        if (!GuiScreen.isShiftKeyDown()) {
            list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.shift.info"));
        } else {
            if (stack.hasTagCompound()) {
                list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.mode") + ": " + tooltipForMode(stack.stackTagCompound.getInteger("ToolMode")));
                list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.minimum.corruption.1"));
                list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.minimum.corruption.2") + ": " + String.valueOf(minimumCorruption));
            }
        }
    }

    public String tooltipForMode(int modeID) {
        String mode = StatCollector.translateToLocal("info.Sanguimancy.tooltip.mode.regular");
        if (modeID == 1) {
            mode = StatCollector.translateToLocal("info.Sanguimancy.tooltip.pickaxe.mode.silk.touch");
        } else if (modeID == 2) {
            mode = StatCollector.translateToLocal("info.Sanguimancy.tooltip.pickaxe.mode.fortune.1");
        } else if (modeID == 3) {
            mode = StatCollector.translateToLocal("info.Sanguimancy.tooltip.pickaxe.mode.fortune.2");
        } else if (modeID == 4) {
            mode = StatCollector.translateToLocal("info.Sanguimancy.tooltip.pickaxe.mode.fortune.3");
        } else if (modeID == 5) {
            mode = StatCollector.translateToLocal("info.Sanguimancy.tooltip.pickaxe.mode.smelt");
        }
        return mode;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        EnergyItems.checkAndSetItemOwner(stack, player);
        if (player.isSneaking()) nextToolMode(stack);
        return stack;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5) {
        RandomUtils.checkAndSetCompound(stack);
        stack.setItemDamage(0);
        super.onUpdate(stack, world, entity, par4, par5);
    }


    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        if (attacker instanceof EntityPlayer) EnergyItems.syphonBatteries(stack, (EntityPlayer) attacker, 15);
        return true;
    }
}