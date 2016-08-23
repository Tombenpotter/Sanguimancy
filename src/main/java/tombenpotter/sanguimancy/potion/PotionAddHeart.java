package tombenpotter.sanguimancy.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.potion.Potion;
import tombenpotter.sanguimancy.registry.PotionsRegistry;

public class PotionAddHeart extends Potion {

    public PotionAddHeart(int color) {
        super(false, color);
    }

    @Override
    public void applyAttributesModifiersToEntity(EntityLivingBase entityLiving, AbstractAttributeMap attributeMap, int amplifier) {
        if (entityLiving.isPotionActive(this)) {
            double newHealth = 20 + entityLiving.getActivePotionEffect(PotionsRegistry.potionAddHeart).getAmplifier();
            entityLiving.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(newHealth);
        } else {
            double newHealth = 20 + 2;
            entityLiving.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(newHealth);
        }
    }

    @Override
    public void removeAttributesModifiersFromEntity(EntityLivingBase entityLiving, AbstractAttributeMap attributeMapIn, int amplifier) {
        entityLiving.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);

    }
}
