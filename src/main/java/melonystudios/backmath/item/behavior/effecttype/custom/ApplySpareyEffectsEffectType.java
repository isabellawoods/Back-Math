package melonystudios.backmath.item.behavior.effecttype.custom;

public class ApplySpareyEffectsEffectType {
    /*@Override
    public void runBehavior(ItemStack stack, PlayerEntity attacker, LivingEntity target, World world) {
        if (target.getType().is(inSpareyProhibitedTag(stack))) {
            // Gives Weakness LXIII (63) for 30 secs.
            attacker.addEffect(getSpareyEffect(new EffectInstance(Effects.WEAKNESS, 600, 64), stack, attacker.level, "prohibition_weakness_effect"));
        } else if (target.getType().is(inSpareyEffectivesTag(stack))) {
            // Gives Strength II effect for 10 secs.
            attacker.addEffect(getSpareyEffect(new EffectInstance(Effects.DAMAGE_BOOST, 200, 1), stack, attacker.level, "strength_effect"));
        } else {
            // Gives Weakness III effect for 2.5 secs, but it rounds it up to 3 secs.
            attacker.addEffect(getSpareyEffect(new EffectInstance(Effects.WEAKNESS, 50, 2), stack, attacker.level, "weakness_effect"));
        }
    }

    @Override
    public List<ITextComponent> addToTooltip(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.addToTooltip(stack, world, tooltip, flag);
        String effectiveTag = stack.getTag() != null && stack.getTag().contains("effective_entities", TagTypes.STRING) ? "#" + stack.getTag().getString("effective_entities") : "#backmath:sparey_effectives";
        tooltip.add(new TranslationTextComponent("tooltip.backmath.behavior.sparey.in_tag", effectiveTag).withStyle(TextFormatting.GRAY));
        tooltip.add(new TranslationTextComponent("tooltip.backmath.behavior.beneficial_effect", BMUtils.addEffectTooltip(Effects.DAMAGE_BOOST, 200, 1)).withStyle(TextFormatting.DARK_GRAY));

        tooltip.add(new TranslationTextComponent("tooltip.backmath.behavior.sparey.else").withStyle(TextFormatting.GRAY));
        tooltip.add(new TranslationTextComponent("tooltip.backmath.behavior.harmful_effect", BMUtils.addEffectTooltip(Effects.WEAKNESS, 50, 2)).withStyle(TextFormatting.DARK_GRAY));

        String prohibitedTag = stack.getTag() != null && stack.getTag().contains("prohibited_entities", TagTypes.STRING) ? "#" + stack.getTag().getString("prohibited_entities") : "#backmath:sparey_prohibited";
        tooltip.add(new TranslationTextComponent("tooltip.backmath.behavior.sparey.in_tag", prohibitedTag).withStyle(TextFormatting.GRAY));
        tooltip.add(new TranslationTextComponent("tooltip.backmath.behavior.harmful_effect", BMUtils.addEffectTooltip(Effects.WEAKNESS, 600, 64)).withStyle(TextFormatting.DARK_GRAY));
        return tooltip;
    }*/
}
