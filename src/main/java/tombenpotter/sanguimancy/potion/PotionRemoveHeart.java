package tombenpotter.sanguimancy.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import tombenpotter.sanguimancy.registry.PotionsRegistry;
import tombenpotter.sanguimancy.util.ConfigHandler;

public class PotionRemoveHeart extends Potion {

    public PotionRemoveHeart(int par1, boolean par2, int par3) {
        super(par1, par2, par3);
    }

    @Override
    public Potion setIconIndex(int par1, int par2) {
        super.setIconIndex(par1, par2);
        return this;
    }

    @Override
    public void applyAttributesModifiersToEntity(EntityLivingBase entityLiving, BaseAttributeMap map, int par3) {
        if (entityLiving.isPotionActive(ConfigHandler.removeHeartPotionID)) {
            double newHealth = 20 - entityLiving.getActivePotionEffect(PotionsRegistry.potionRemoveHeart).getAmplifier();
            entityLiving.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(newHealth);
        } else {
            double newHealth = 20 - 1;
            entityLiving.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(newHealth);
        }
        entityLiving.attackEntityFrom(DamageSource.outOfWorld, 1);
    }

    @Override
    public void removeAttributesModifiersFromEntity(EntityLivingBase entityLiving, BaseAttributeMap map, int par3) {
        entityLiving.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20);
    }
}
