package melonystudios.backmath.item.custom.behavior;

import melonystudios.backmath.item.behavior.BMItemBehaviors;
import melonystudios.backmath.item.behavior.ItemBehavior;
import melonystudios.backmath.item.behavior.effecttype.ItemBehaviorEffectType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;
import java.util.function.Supplier;

public class BMArmorItem extends ArmorItem {
    private final Supplier<ItemBehavior> behavior;

    public BMArmorItem(Holder<ArmorMaterial> material, Type slot, Supplier<ItemBehavior> behavior, Properties properties) {
        super(material, slot, properties);
        this.behavior = behavior;
    }

    public BMArmorItem(Holder<ArmorMaterial> material, Type slot, Properties properties) {
        this(material, slot, BMItemBehaviors.NONE, properties);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player attacker, Entity target) {
        if (target instanceof LivingEntity livEntity) this.behavior.get().run(stack, attacker, livEntity, target.level());
        return super.onLeftClickEntity(stack, attacker, target);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        if (!this.behavior.get().effects.isEmpty()) tooltip.add(Component.translatable("tooltip.backmath.behavior.when_used").withStyle(ChatFormatting.GRAY));
        for (Supplier<ItemBehaviorEffectType> type : this.behavior.get().effects) {
            if (type != null) type.get().addToTooltip(stack, context, tooltip, flag);
        }
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return this.behavior.get().hasGlint(stack) || super.isFoil(stack);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return this.behavior.get().getDurabilityBarColor(stack, super.getBarColor(stack));
    }
}
