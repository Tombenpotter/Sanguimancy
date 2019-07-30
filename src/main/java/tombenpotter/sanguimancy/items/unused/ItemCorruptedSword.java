package tombenpotter.sanguimancy.items.unused;

import net.minecraft.item.Item;

public class ItemCorruptedSword extends Item {
/*
    public float baseDamage;
    public int minimumCorruption = ConfigHandler.minimumToolCorruption;
    public IIcon activated;

    public ItemCorruptedSword(int damage) {
        setCreativeTab(Sanguimancy.tabSanguimancy);
        setUnlocalizedName(Sanguimancy.modid + ".corruptedSword");
        setMaxDamage(0);
        baseDamage = damage;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        this.itemIcon = ir.registerIcon(Sanguimancy.texturePath + ":CorruptedSword");
        this.activated = ir.registerIcon(Sanguimancy.texturePath + ":CorruptedSword_Activated");
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        RandomUtils.checkAndSetCompound(stack);
        if (getActivated(stack)) return this.activated;
        else return this.itemIcon;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase entity) {
        if (entity instanceof EntityPlayer) EnergyItems.syphonBatteries(stack, (EntityPlayer) entity, 5);
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        if (!GuiScreen.isShiftKeyDown()) {
            list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.shift.info"));
        } else {
            if (stack.hasTagCompound()) {
                list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.minimum.corruption.1"));
                list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.minimum.corruption.2") + ": " + String.valueOf(minimumCorruption));
                list.add(StatCollector.translateToLocal("info.Sanguimancy.tooltip.activated") + ": " + String.valueOf(getActivated(stack)));
                int corruption = SoulCorruptionHelper.getCorruptionLevel(player);
                list.add("\u00A79+ " + (int) (baseDamage * (corruption / minimumCorruption)) + " " + StatCollector.translateToLocal("info.Sanguimancy.tooltip.attack.damage"));
            }
        }
    }


    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        if (attacker instanceof EntityPlayer) {
            EntityPlayer attackerPlayer = (EntityPlayer) attacker;
            int corruption = SoulCorruptionHelper.getCorruptionLevel(((EntityPlayer) attacker).worldObj.getPlayerEntityByName(RandomUtils.getItemOwner(stack)));
            target.attackEntityFrom(DamageSource.causePlayerDamage(attackerPlayer), baseDamage * (corruption / minimumCorruption));
            if (target instanceof EntityPlayer && getActivated(stack)) {
                EntityPlayer targetPlayer = (EntityPlayer) target;
                int amplifier = (int) (baseDamage * (corruption / minimumCorruption));
                if (amplifier > 15) amplifier = 15;
                targetPlayer.addPotionEffect(new PotionEffect(PotionsRegistry.potionRemoveHeart.id, 1200, amplifier, false));
                attackerPlayer.addPotionEffect(new PotionEffect(PotionsRegistry.potionAddHeart.id, 1200, amplifier, false));
                if (attackerPlayer.worldObj.rand.nextInt(20) == 0) {
                    SoulCorruptionHelper.incrementCorruption(((EntityPlayer) attacker).worldObj.getPlayerEntityByName(RandomUtils.getItemOwner(stack)));
                }
                EnergyItems.syphonBatteries(stack, attackerPlayer, 50 * amplifier);
            }
            EnergyItems.syphonBatteries(stack, attackerPlayer, 10);
            return true;
        }
        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        EnergyItems.checkAndSetItemOwner(stack, player);
        player.setItemInUse(stack, getMaxItemUseDuration(stack));
        if (player.isSneaking()) setActivated(stack, !getActivated(stack));
        return stack;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5) {
        RandomUtils.checkAndSetCompound(stack);
        stack.setItemDamage(0);
        super.onUpdate(stack, world, entity, par4, par5);
    }

    @Override
    public int getItemEnchantability() {
        return 32;
    }

    @Override
    public boolean isFull3D() {
        return true;
    }

    @Override
    public float getDigSpeed(ItemStack itemstack, Block block, int metadata) {
        if (block == Blocks.web) {
            return 15.0F;
        } else {
            Material material = block.getMaterial();
            return material != Material.plants && material != Material.vine && material != Material.coral && material != Material.leaves && material != Material.gourd ? 1.0F : 1.5F;
        }
    }

    @Override
    public EnumAction getItemUseAction(ItemStack p_77661_1_) {
        return EnumAction.block;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack p_77626_1_) {
        return 72000;
    }

    @Override
    public boolean func_150897_b(Block p_150897_1_) {
        return p_150897_1_ == Blocks.web;
    }

    public void setActivated(ItemStack stack, boolean isActivated) {
        RandomUtils.checkAndSetCompound(stack);
        stack.stackTagCompound.setBoolean("isActive", isActivated);
    }

    public boolean getActivated(ItemStack stack) {
        RandomUtils.checkAndSetCompound(stack);
        return stack.stackTagCompound.getBoolean("isActive");
    }
    */
}