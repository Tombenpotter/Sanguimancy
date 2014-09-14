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
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityChickenMinion extends EntityTameable {

    public float field_70886_e;
    public float destPos;
    public float field_70884_g;
    public float field_70888_h;
    public float field_70889_i = 1.0F;
    public int timeUntilNextEgg;

    public EntityChickenMinion(World world) {
        super(world);
        this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
        this.setSize(0.15F, 0.35F);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.4D));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(3, new EntityAITempt(this, 1.0D, Items.wheat_seeds, false));
        this.tasks.addTask(4, new EntityAIFollowOwner(this, 1.1D, 1, 1));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
    }

    @Override
    public boolean isAIEnabled() {
        return true;
    }

    @Override
    public void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35D);
    }

    public void onLivingUpdate() {
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

        if (!this.worldObj.isRemote && !this.isChild() && --this.timeUntilNextEgg <= 0) {
            this.playSound("mob.chicken.plop", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            this.dropItem(Items.egg, 1);
            this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
        }
    }

    @Override
    public EntityAgeable createChild(EntityAgeable p_90011_1_) {
        return new EntityChicken(this.worldObj);
    }

    @Override
    public void fall(float p_70069_1_) {
    }

    @Override
    public String getLivingSound() {
        return "mob.chicken.say";
    }

    @Override
    public String getHurtSound() {
        return "mob.chicken.hurt";
    }

    @Override
    public String getDeathSound() {
        return "mob.chicken.hurt";
    }

    @Override
    public void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_) {
        this.playSound("mob.chicken.step", 0.15F, 1.0F);
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
        return Item.getItemFromBlock(Blocks.cobblestone);
    }

    @Override
    public void dropFewItems(boolean p_70628_1_, int p_70628_2_) {
        int j = this.rand.nextInt(3) + this.rand.nextInt(1 + p_70628_2_);

        for (int k = 0; k < j; ++k) {
            this.dropItem(Item.getItemFromBlock(Blocks.cobblestone), 1);
        }

        if (this.isBurning()) {
            this.dropItem(Item.getItemFromBlock(Blocks.waterlily), 1);
        } else {
            this.dropItem(Item.getItemFromBlock(Blocks.stone), 1);
        }
    }
}
