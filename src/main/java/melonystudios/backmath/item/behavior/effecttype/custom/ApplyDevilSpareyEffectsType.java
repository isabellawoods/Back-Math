package melonystudios.backmath.item.behavior.effecttype.custom;

public class ApplyDevilSpareyEffectsType {
    /*@Override
    public void runBehavior(ItemStack stack, Player attacker, LivingEntity target, Level world) {
        if (target.getType().is(inSpareyProhibitedTag(stack))) {
            // Give sword user hitting Queen Lucy Weakness LXIII (63) for 30 secs.
            attacker.addEffect(this.getSpareyEffect(new MobEffectInstance(MobEffects.WEAKNESS, 600, 64), stack, attacker.level(), "prohibition_weakness_effect"));
        } else if (target.getType().is(this.inDevilSpareyEffectivesTag(stack))) {
            // If the sword user hits one of these:
            // - Wanderer Sophie, (Archer) Insomnia Sophie, Warrior Sophie, Queen Lucy Pet, Archer Lucia, Karate Lucia, Shy Alcalyte, Collector Alcalyte or Malaika
            // Give player Strength III effect for 2.5 secs (or 3 secs rounded)
            attacker.addEffect(this.getSpareyEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 50, 2), stack, attacker.level(), "strength_effect"));
        } else {
            // Give user Weakness II for 10 secs.
            attacker.addEffect(this.getSpareyEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 1), stack, attacker.level(), "weakness_effect"));
        }
    }

    @Override
    public List<Component> addToTooltip(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.addToTooltip(stack, context, tooltip, flag);
        String effectiveTag = stack.getTag() != null && stack.getTag().contains("effective_entities", TagTypes.STRING) ? "#" + stack.getTag().getString("effective_entities") : "#backmath:devil_sparey_effectives";
        tooltip.add(Component.translatable("tooltip.backmath.behavior.sparey.in_tag", effectiveTag).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.backmath.behavior.beneficial_effect", BMUtils.addEffectTooltip(MobEffects.DAMAGE_BOOST.value(), 200, 1, context.tickRate())).withStyle(ChatFormatting.DARK_GRAY));

        tooltip.add(Component.translatable("tooltip.backmath.behavior.sparey.else").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.backmath.behavior.harmful_effect", BMUtils.addEffectTooltip(MobEffects.WEAKNESS.value(), 50, 2, context.tickRate())).withStyle(ChatFormatting.DARK_GRAY));

        String prohibitedTag = stack.getTag() != null && stack.getTag().contains("prohibited_entities", TagTypes.STRING) ? "#" + stack.getTag().getString("prohibited_entities") : "#backmath:sparey_prohibited";
        tooltip.add(Component.translatable("tooltip.backmath.behavior.sparey.in_tag", prohibitedTag).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.backmath.behavior.harmful_effect", BMUtils.addEffectTooltip(MobEffects.WEAKNESS.value(), 600, 64, context.tickRate())).withStyle(ChatFormatting.DARK_GRAY));
        return tooltip;
    }*/
}
