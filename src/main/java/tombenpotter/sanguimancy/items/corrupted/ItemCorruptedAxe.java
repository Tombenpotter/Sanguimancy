package tombenpotter.sanguimancy.items.corrupted;

import WayofTime.alchemicalWizardry.common.items.EnergyItems;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.api.objects.MapKey;
import tombenpotter.sanguimancy.api.soulCorruption.SoulCorruptionHelper;
import tombenpotter.sanguimancy.util.ConfigHandler;
import tombenpotter.sanguimancy.util.RandomUtils;

import java.util.List;

public class ItemCorruptedAxe extends ItemAxe {
    public int minimumCorruption = ConfigHandler.minimumToolCorruption;

    public ItemCorruptedAxe(ToolMaterial material) {
        super(material);
        setCreativeTab(Sanguimancy.tabSanguimancy);
        setUnlocalizedName(Sanguimancy.modid + ".corruptedAxe");
        setMaxDamage(0);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        super.registerIcons(ir);
        //TODO: Add an icon and a different overlay for every mode.
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
                for (int i = -5; i <= 5; i++) {
                    for (int j = -5; j <= 5; j++) {
                        for (int k = -5; k <= 5; k++) {
                            if (world.getBlock(i, j, k).isLeaves(world, i, j, k)) {
                                RandomUtils.dropBlockDropsWithFortune(world, block, i, j, k, metadata, 0);
                                world.setBlockToAir(i, j, k);
                            }
                        }
                    }
                }
                RandomUtils.dropBlockDropsWithFortune(world, block, x, y, z, metadata, 0);
                world.setBlockToAir(x, y, z);
            } else if (toolMode == 2) {
                RandomUtils.dropBlockDropsWithFortune(world, block, x, y, z, metadata, 0);
                world.setBlockToAir(x, y, z);
            } else if (toolMode == 3) {
                lpConsumption = lpConsumption * 10;
                if (!RandomUtils.getItemStackName(new ItemStack(block)).isEmpty() && RandomUtils.getItemStackName(new ItemStack(block)).contains("plankWood")) {
                    ItemStack drops = new ItemStack(Items.stick, 3).copy();
                    RandomUtils.dropItemStackInWorld(world, x, y, z, drops);
                } else if (!RandomUtils.getItemStackName(new ItemStack(block)).isEmpty() && RandomUtils.getItemStackName(new ItemStack(block)).contains("logWood")) {
                    ItemStack drops = RandomUtils.logToPlank.get(new MapKey(new ItemStack(block, 1, metadata))).copy();
                    drops.stackSize = 6;
                    RandomUtils.dropItemStackInWorld(world, x, y, z, drops);
                } else {
                    RandomUtils.dropBlockDropsWithFortune(world, block, x, y, z, metadata, 0);
                }
                world.setBlockToAir(x, y, z);
            }
            if (entityLivingBase instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entityLivingBase;
                EnergyItems.syphonBatteries(stack, player, lpConsumption);
                if (getToolMode(stack) != 0) {
                    if (world.rand.nextInt(20) == 0) {
                        SoulCorruptionHelper.incrementCorruption(RandomUtils.getItemOwner(stack));
                    }
                }
            }
        }
        return getToolMode(stack) <= 3;
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
        if (stack.stackTagCompound.getInteger("ToolMode") + 1 <= 3) {
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
            mode = StatCollector.translateToLocal("info.Sanguimancy.tooltip.axe.mode.leaf.decay");
        } else if (modeID == 2) {
            mode = StatCollector.translateToLocal("info.Sanguimancy.tooltip.axe.mode.head.hunter");
        } else if (modeID == 3) {
            mode = StatCollector.translateToLocal("info.Sanguimancy.tooltip.axe.mode.refine");
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
    public float getDigSpeed(ItemStack stack, Block block, int meta) {
        RandomUtils.checkAndSetCompound(stack);
        int playerCorruption = SoulCorruptionHelper.getCorruptionLevel(RandomUtils.getItemOwner(stack));
        return super.getDigSpeed(stack, block, meta) * (playerCorruption / minimumCorruption);

    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        if (attacker instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) attacker;
            EnergyItems.syphonBatteries(stack, player, 100);
            if (getToolMode(stack) == 2) {
                if (player.worldObj.rand.nextInt(20) == 0) {
                    SoulCorruptionHelper.incrementCorruption(RandomUtils.getItemOwner(stack));
                }
            }
            return true;
        }
        return false;
    }

    public ItemStack getSkullDrop(EntityLivingBase entity) {
        if (entity instanceof EntitySkeleton) {
            int type = ((EntitySkeleton) entity).getSkeletonType();
            if (type == 1) return new ItemStack(Items.skull, 1, 1);
            else return new ItemStack(Items.skull, 1, 0);
        } else if (entity instanceof EntityPlayer) {
            String name = entity.getCommandSenderName();
            ItemStack skull = new ItemStack(Items.skull, 1, 3);
            RandomUtils.checkAndSetCompound(skull);
            skull.stackTagCompound.setString("SkullOwner", name);
            return skull;
        } else if (entity instanceof EntityZombie) {
            return new ItemStack(Items.skull, 1, 2);
        } else if (entity instanceof EntityCreeper) {
            return new ItemStack(Items.skull, 1, 4);
        } else {
            return null;
        }
    }

    @SubscribeEvent
    public void onEntityDrop(LivingDropsEvent event) {
        if (event.source.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.source.getEntity();
            if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemCorruptedAxe) {
                int corruption = SoulCorruptionHelper.getCorruptionLevel(RandomUtils.getItemOwner(player.getHeldItem()));
                int chance = 100 * (minimumCorruption / corruption);
                if (chance < 1) chance = 1;
                if (player.worldObj.rand.nextInt(chance) == 0 && getSkullDrop(event.entityLiving) != null) {
                    RandomUtils.dropItemStackInWorld(event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, getSkullDrop(event.entityLiving).copy());
                }
            }
        }
    }
}
