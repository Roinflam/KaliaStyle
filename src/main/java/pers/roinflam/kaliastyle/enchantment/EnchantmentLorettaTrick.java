package pers.roinflam.kaliastyle.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.world.Explosion;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pers.roinflam.kaliastyle.init.KaliaStyleEnchantments;
import pers.roinflam.kaliastyle.utils.helper.task.SynchronizationTask;
import pers.roinflam.kaliastyle.utils.java.random.RandomUtil;
import pers.roinflam.kaliastyle.utils.util.EnchantmentUtil;
import pers.roinflam.kaliastyle.utils.util.EntityUtil;

@Mod.EventBusSubscriber
public class EnchantmentLorettaTrick extends Enchantment {

    public EnchantmentLorettaTrick(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots) {
        super(rarityIn, typeIn, slots);
        EnchantmentUtil.registerEnchantment(this, "loretta_trick");
        KaliaStyleEnchantments.ENCHANTMENTS.add(this);
    }

    public static Enchantment getEnchantment() {
        return KaliaStyleEnchantments.LORETTA_TRICK;
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onProjectileImpact_Arrow(ProjectileImpactEvent.Arrow evt) {
        if (!evt.getEntity().world.isRemote) {
            if (evt.getArrow().shootingEntity != null) {
                EntityArrow entityArrow = evt.getArrow();
                EntityLivingBase attacker = (EntityLivingBase) evt.getArrow().shootingEntity;
                int bonusLevel = EnchantmentHelper.getEnchantmentLevel(getEnchantment(), attacker.getHeldItemMainhand());
                if (bonusLevel > 0) {
                    entityArrow.setDamage(entityArrow.getDamage() - entityArrow.getDamage() * 0.25);
                    new SynchronizationTask(1, 5) {
                        private int time = 0;

                        @Override
                        public void run() {
                            if (++time > 4) {
                                this.cancel();
                                return;
                            }
                            Explosion explosion = attacker.world.createExplosion(attacker, entityArrow.posX - 2.5 + RandomUtil.getInt(0, 5), entityArrow.posY, entityArrow.posZ - 2.5 + RandomUtil.getInt(0, 5), EntityUtil.getFire(entityArrow) > 0 ? 4 : 3, false);
                        }

                    }.start();
                }
            }
        }
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 35;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return getMinEnchantability(enchantmentLevel) * 2;
    }

    @Override
    public boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench) && !ench.equals(KaliaStyleEnchantments.LORETTA_BIG_BOW);
    }

    @Override
    public boolean isTreasureEnchantment() {
        return true;
    }
}
