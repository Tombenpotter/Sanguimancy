package tombenpotter.sanguimancy.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import tombenpotter.sanguimancy.registry.PotionsRegistry;

public class PotionRemoveHeart extends Potion {

    public PotionRemoveHeart(int color) {
        super(true, color);
    }

    @Override
    public void applyAttributesModifiersToEntity(EntityLivingBase entityLiving, AbstractAttributeMap attributeMap, int amplifier) {
        if (entityLiving.isPotionActive(this)) {
            double newHealth = 20 - entityLiving.getActivePotionEffect(PotionsRegistry.potionRemoveHeart).getAmplifier();
            entityLiving.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(newHealth);
        } else {
            double newHealth = 20 - 1;
            entityLiving.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(newHealth);
        }
        entityLiving.attackEntityFrom(DamageSource.outOfWorld, 1);
    }

    @Override
    public void removeAttributesModifiersFromEntity(EntityLivingBase entityLiving, AbstractAttributeMap attributeMapIn, int amplifier) {
        entityLiving.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
    }
}
