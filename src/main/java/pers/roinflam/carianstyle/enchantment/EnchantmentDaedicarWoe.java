package pers.roinflam.carianstyle.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pers.roinflam.carianstyle.base.enchantment.rarity.VeryRaryBase;
import pers.roinflam.carianstyle.init.CarianStyleEnchantments;
import pers.roinflam.carianstyle.utils.util.EnchantmentUtil;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber
public class EnchantmentDaedicarWoe extends VeryRaryBase {

    public EnchantmentDaedicarWoe(EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots) {
        super(typeIn, slots, "daedicar_woe");
    }

    @Nonnull
    public static Enchantment getEnchantment() {
        return CarianStyleEnchantments.DAEDICAR_WOE;
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLivingHurt(@Nonnull LivingHurtEvent evt) {
        if (!evt.getEntity().world.isRemote) {
            EntityLivingBase hurter = evt.getEntityLiving();
            int bonusLevel = 0;
            for (@Nonnull ItemStack itemStack : hurter.getArmorInventoryList()) {
                if (!itemStack.isEmpty()) {
                    bonusLevel += EnchantmentHelper.getEnchantmentLevel(getEnchantment(), itemStack);
                }
            }
            if (bonusLevel > 0) {
                hurter.hurtResistantTime = (int) (hurter.maxHurtResistantTime * 0.75);
                evt.setAmount(evt.getAmount() * 5);
            }
        }
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 35;
    }

    @Override
    public boolean isCurse() {
        return true;
    }
}
