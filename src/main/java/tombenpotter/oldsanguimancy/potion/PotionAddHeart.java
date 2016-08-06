package tombenpotter.oldsanguimancy.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.potion.Potion;
import tombenpotter.oldsanguimancy.registry.PotionsRegistry;
import tombenpotter.sanguimancy.util.ConfigHandler;

public class PotionAddHeart extends Potion {

    public PotionAddHeart(int par1, boolean par2, int par3) {
        super(par1, par2, par3);
    }

    @Override
    public Potion setIconIndex(int par1, int par2) {
        super.setIconIndex(par1, par2);
        return this;
    }

    @Override
    public void applyAttributesModifiersToEntity(EntityLivingBase entityLiving, BaseAttributeMap map, int par3) {
        if (entityLiving.isPotionActive(ConfigHandler.addHeartPotionID)) {
            double newHealth = 20 + entityLiving.getActivePotionEffect(PotionsRegistry.potionAddHeart).getAmplifier();
            entityLiving.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(newHealth);
        } else {
            double newHealth = 20 + 2;
            entityLiving.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(newHealth);
        }
    }

    @Override
    public void removeAttributesModifiersFromEntity(EntityLivingBase entityLiving, BaseAttributeMap map, int par3) {
        entityLiving.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20);
    }
}
