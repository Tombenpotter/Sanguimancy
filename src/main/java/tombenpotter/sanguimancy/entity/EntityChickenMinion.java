package tombenpotter.sanguimancy.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityChickenMinion extends EntityTameable {

    public float field_70886_e;
    public float destPos;
    public float field_70884_g;
    public float field_70888_h;
    public float field_70889_i = 1.0F;
    public int timeUntilNextEgg;
    public int ticksLived;

    public EntityChickenMinion(World world) {
        super(world);
        this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
        this.setSize(0.3F, 0.7F);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.4D));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(3, new EntityAITempt(this, 1.0D, Items.WHEAT_SEEDS, false));
        this.tasks.addTask(4, new EntityAIFollowOwner(this, 1.1D, 1, 1));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.ticksLived = 0;
    }

    @Override
    public void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
    }

    @Override
    public void onLivingUpdate() {
        this.ticksLived++;
        super.onLivingUpdate();
        this.field_70888_h = this.field_70886_e;
        this.field_70884_g = this.destPos;
        this.destPos = (float) ((double) this.destPos + (double) (this.onGround ? -1 : 4) * 0.3D);
        if (this.destPos < 0.0F) {
            this.destPos = 0.0F;
        }
        if (this.destPos > 1.0F) {
            this.destPos = 1.0F;
        }
        if (!this.onGround && this.field_70889_i < 1.0F) {
            this.field_70889_i = 1.0F;
        }
        this.field_70889_i = (float) ((double) this.field_70889_i * 0.9D);
        if (!this.onGround && this.motionY < 0.0D) {
            this.motionY *= 0.6D;
        }
        this.field_70886_e += this.field_70889_i * 2.0F;

        if (!this.world.isRemote && !this.isChild() && --this.timeUntilNextEgg <= 0) {
            this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            this.dropItem(Items.EGG, 1);
            this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
        }
        if (ticksLived > 36000 && !world.isRemote) {
            this.setDead();
        }
    }

    @Override
    public EntityAgeable createChild(EntityAgeable entityAgeable) {
        return new EntityChicken(this.world);
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
    }

    public SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_CHICKEN_AMBIENT;
    }

    public SoundEvent getHurtSound() {
        return SoundEvents.ENTITY_CHICKEN_HURT;
    }

    public SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_CHICKEN_DEATH;
    }

    public void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 1.0F);
    }

    @Override
    public boolean canDespawn() {
        return true;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack != null && stack.getItem() instanceof ItemSeeds;
    }

    @Override
    public Item getDropItem() {
        return Item.getItemFromBlock(Blocks.COBBLESTONE);
    }

    @Override
    public void dropFewItems(boolean p_70628_1_, int p_70628_2_) {
        int j = this.rand.nextInt(3) + this.rand.nextInt(1 + p_70628_2_);

        for (int k = 0; k < j; ++k) {
            this.dropItem(Item.getItemFromBlock(Blocks.COBBLESTONE), 1);
        }

        if (this.isBurning()) {
            this.dropItem(Item.getItemFromBlock(Blocks.WATERLILY), 1);
        } else {
            this.dropItem(Item.getItemFromBlock(Blocks.STONE), 1);
        }
    }
}
