package tombenpotter.sanguimancy.items.unused;

public class ItemCorruptedShovel {
/*
    public int minimumCorruption = ConfigHandler.minimumToolCorruption;
    public IIcon breakingDown, goldDigger, transmutation;
    private HashMap<BlockAndMetadata, BlockAndMetadata> breakdownBlocks = new HashMap<BlockAndMetadata, BlockAndMetadata>();
    private HashMap<BlockAndMetadata, BlockAndMetadata> transmuteBlocks = new HashMap<BlockAndMetadata, BlockAndMetadata>();

    public ItemCorruptedShovel(ToolMaterial material) {
        super(material);
        setCreativeTab(Sanguimancy.tabSanguimancy);
        setUnlocalizedName(Sanguimancy.modid + ".corruptedShovel");
        setMaxDamage(0);
        setbreakdownBlocks();
        setTransmuteBlocks();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        this.itemIcon = ir.registerIcon(Sanguimancy.texturePath + ":CorruptedShovel");
        this.breakingDown = ir.registerIcon(Sanguimancy.texturePath + ":CorruptedShovel_BreakDown");
        this.goldDigger = ir.registerIcon(Sanguimancy.texturePath + ":CorruptedShovel_GoldDigger");
        this.transmutation = ir.registerIcon(Sanguimancy.texturePath + ":CorruptedShovel_Transmute");
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        if (getToolMode(stack) == 1) return breakingDown;
        else if (getToolMode(stack) == 2) return goldDigger;
        else if (getToolMode(stack) == 3) return transmutation;
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
            } else if (toolMode == 2) {
                lpConsumption = lpConsumption * 7;
                if (block == Blocks.gravel) {
                    ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
                    if (world.rand.nextInt(200) == 0) drops.add(new ItemStack(Items.diamond));
                    if (world.rand.nextInt(300) == 0) drops.add(new ItemStack(Items.emerald));
                    if (world.rand.nextInt(100) == 0) drops.add(new ItemStack(Items.gold_nugget));
                    for (ItemStack drop : drops) RandomUtils.dropItemStackInWorld(world, x, y, z, drop);
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
                        SoulCorruptionHelper.incrementCorruption(world.getPlayerEntityByName(RandomUtils.getItemOwner(stack)));
                    }
                }
            }
        }
        return getToolMode(stack) != 1 && getToolMode(stack) != 3;
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
            mode = StatCollector.translateToLocal("info.Sanguimancy.tooltip.shovel.mode.break.down");
        } else if (modeID == 2) {
            mode = StatCollector.translateToLocal("info.Sanguimancy.tooltip.shovel.mode.lucky");
        } else if (modeID == 3) {
            mode = StatCollector.translateToLocal("info.Sanguimancy.tooltip.shovel.mode.transmute");
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

    public void setbreakdownBlocks() {
        breakdownBlocks.put(new BlockAndMetadata(Blocks.stone), new BlockAndMetadata(Blocks.cobblestone));
        breakdownBlocks.put(new BlockAndMetadata(Blocks.cobblestone), new BlockAndMetadata(Blocks.gravel));
        breakdownBlocks.put(new BlockAndMetadata(Blocks.gravel), new BlockAndMetadata(Blocks.sand));
        breakdownBlocks.put(new BlockAndMetadata(Blocks.glass), new BlockAndMetadata(Blocks.glass_pane));
        breakdownBlocks.put(new BlockAndMetadata(Blocks.iron_block), new BlockAndMetadata(Blocks.iron_bars));
        breakdownBlocks.put(new BlockAndMetadata(Blocks.brick_block), new BlockAndMetadata(Blocks.hardened_clay));
        breakdownBlocks.put(new BlockAndMetadata(Blocks.hardened_clay), new BlockAndMetadata(Blocks.clay));
        breakdownBlocks.put(new BlockAndMetadata(Blocks.stonebrick), new BlockAndMetadata(Blocks.stonebrick, 2));
    }

    public void setTransmuteBlocks() {
        transmuteBlocks.put(new BlockAndMetadata(Blocks.sand), new BlockAndMetadata(Blocks.clay));
        transmuteBlocks.put(new BlockAndMetadata(Blocks.dirt), new BlockAndMetadata(Blocks.grass));
        transmuteBlocks.put(new BlockAndMetadata(Blocks.grass), new BlockAndMetadata(Blocks.dirt, 2));
        transmuteBlocks.put(new BlockAndMetadata(Blocks.clay), new BlockAndMetadata(Blocks.hardened_clay));
        transmuteBlocks.put(new BlockAndMetadata(Blocks.hardened_clay), new BlockAndMetadata(Blocks.brick_block));
        transmuteBlocks.put(new BlockAndMetadata(Blocks.vine), new BlockAndMetadata(Blocks.waterlily));
        transmuteBlocks.put(new BlockAndMetadata(Blocks.obsidian), new BlockAndMetadata(Blocks.end_stone));
        transmuteBlocks.put(new BlockAndMetadata(Blocks.redstone_block), new BlockAndMetadata(Blocks.glowstone));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void drawTransmutationsAndBreakdowns(RenderWorldLastEvent event) {
        Minecraft minecraft = Minecraft.getMinecraft();
        EntityPlayer player = minecraft.thePlayer;
        World world = minecraft.theWorld;
        MovingObjectPosition mop = minecraft.objectMouseOver;
        if (mop != null) {
            int x = mop.blockX;
            int y = mop.blockY;
            int z = mop.blockZ;
            Block block = minecraft.theWorld.getBlock(x, y, z);
            int metadata = world.getBlockMetadata(x, y, z);
            if (player.getHeldItem() != null && player.getHeldItem().getItem() == ItemsRegistry.corruptedShovel) {
                if (getToolMode(player.getHeldItem()) == 3 && transmuteBlocks.containsKey(new BlockAndMetadata(block, minecraft.theWorld.getBlockMetadata(x, y, z)))) {
                    Block resultingBlock = transmuteBlocks.get(new BlockAndMetadata(block, metadata)).block;
                    int resultingMeta = transmuteBlocks.get(new BlockAndMetadata(block, metadata)).metadata;
                    for (ForgeDirection d : ForgeDirection.VALID_DIRECTIONS) {
                        if (world.isAirBlock(x + d.offsetX, y + d.offsetY, z + d.offsetZ)) {
                            RandomUtils.renderBlock(event, player, world, resultingBlock, resultingMeta, x + d.offsetX, y + d.offsetY, z + d.offsetZ);
                            break;
                        }
                    }
                } else if (getToolMode(player.getHeldItem()) == 1 && breakdownBlocks.containsKey(new BlockAndMetadata(block, minecraft.theWorld.getBlockMetadata(x, y, z)))) {
                    Block resultingBlock = breakdownBlocks.get(new BlockAndMetadata(block, metadata)).block;
                    int resultingMeta = breakdownBlocks.get(new BlockAndMetadata(block, metadata)).metadata;
                    for (ForgeDirection d : ForgeDirection.VALID_DIRECTIONS) {
                        if (world.isAirBlock(x + d.offsetX, y + d.offsetY, z + d.offsetZ)) {
                            RandomUtils.renderBlock(event, player, world, resultingBlock, resultingMeta, x + d.offsetX, y + d.offsetY, z + d.offsetZ);
                            break;
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onBlockBrokenByCorruptedShovel(BlockEvent.BreakEvent event) {
        Block block = event.block;
        int metadata = event.blockMetadata;
        ItemStack stack = event.getPlayer().getHeldItem();
        EntityPlayer player = event.getPlayer();
        World world = event.world;
        int x = event.x;
        int y = event.y;
        int z = event.z;
        if (stack != null && stack.isItemEqual(SanguimancyItemStacks.corruptedShovel)) {
            int lpConsumption = 10;
            int toolMode = getToolMode(stack);
            if (toolMode == 1) {
                lpConsumption = lpConsumption * 5;
                if (breakdownBlocks.containsKey(new BlockAndMetadata(block, metadata))) {
                    Block bb = breakdownBlocks.get(new BlockAndMetadata(block, metadata)).block;
                    int meta = breakdownBlocks.get(new BlockAndMetadata(block, metadata)).metadata;
                    world.setBlock(x, y, z, bb, meta, 3);
                    event.setCanceled(true);
                } else {
                    RandomUtils.dropBlockDropsWithFortune(world, block, x, y, z, metadata, 0);
                    world.setBlockToAir(x, y, z);
                }
            } else if (toolMode == 3) {
                lpConsumption = lpConsumption * 10;
                if (transmuteBlocks.containsKey(new BlockAndMetadata(block, metadata))) {
                    Block bb = transmuteBlocks.get(new BlockAndMetadata(block, metadata)).block;
                    int meta = transmuteBlocks.get(new BlockAndMetadata(block, metadata)).metadata;
                    world.setBlock(x, y, z, bb, meta, 3);
                    event.setCanceled(true);
                } else {
                    RandomUtils.dropBlockDropsWithFortune(world, block, x, y, z, metadata, 0);
                    world.setBlockToAir(x, y, z);
                }
            }
            EnergyItems.syphonBatteries(stack, player, lpConsumption);
            if (getToolMode(stack) != 0) {
                if (world.rand.nextInt(20) == 0) {
                    SoulCorruptionHelper.incrementCorruption(world.getPlayerEntityByName(RandomUtils.getItemOwner(stack)));
                }
            }
        }
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        if (attacker instanceof EntityPlayer) EnergyItems.syphonBatteries(stack, (EntityPlayer) attacker, 15);
        return true;
    }
    */
}