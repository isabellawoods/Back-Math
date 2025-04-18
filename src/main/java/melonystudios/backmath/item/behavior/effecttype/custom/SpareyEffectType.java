package melonystudios.backmath.item.behavior.effecttype.custom;

import melonystudios.backmath.item.behavior.effecttype.ItemBehaviorEffectType;

public abstract class SpareyEffectType extends ItemBehaviorEffectType {
    /*public TagKey<EntityType<?>> inSpareyEffectivesTag(ItemStack stack) {
        TagKey<EntityType<?>> effectiveEntities = BMEntityTypeTags.SPAREY_EFFECTIVES;
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains("effective_entities", TagTypes.STRING)) {
            ResourceLocation tagLocation = ResourceLocation.tryParse(tag.getString("effective_entities"));
            if (tagLocation != null) {
                TagKey<EntityType<?>> managerTag = TagCollectionManager.getInstance().getEntityTypes().getTag(tagLocation);
                if (managerTag != null) return managerTag;
                else return effectiveEntities;
            }
        }
        return effectiveEntities;
    }

    public TagKey<EntityType<?>> inDevilSpareyEffectivesTag(ItemStack stack) {
        TagKey<EntityType<?>> effectiveEntities = BMEntityTypeTags.DEVIL_SPAREY_EFFECTIVES;
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains("effective_entities", TagTypes.STRING)) {
            ResourceLocation tagLocation = ResourceLocation.tryParse(tag.getString("effective_entities"));
            if (tagLocation != null) {
                TagKey<EntityType<?>> managerTag = TagCollectionManager.getInstance().getEntityTypes().getTag(tagLocation);
                if (managerTag != null) return managerTag;
            }
        }
        return effectiveEntities;
    }

    public TagKey<EntityType<?>> inSpareyProhibitedTag(ItemStack stack) {
        TagKey<EntityType<?>> prohibitedEntities = BMEntityTypeTags.SPAREYS_PROHIBITED;
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains("prohibited_entities", TagTypes.STRING)) {
            ResourceLocation tagLocation = ResourceLocation.tryParse(tag.getString("prohibited_entities"));
            if (tagLocation != null) {
                TagKey<EntityType<?>> managerTag = TagCollectionManager.getInstance().getEntityTypes().getTag(tagLocation);
                if (managerTag != null) return managerTag;
            }
        }
        return prohibitedEntities;
    }

    public MobEffectInstance getSpareyEffect(MobEffectInstance defaultEffect, ItemStack stack, Level world, String tagType) {
        CompoundTag tag = stack.getTag();

        if (tag != null && tag.contains(tagType, TagTypes.COMPOUND)) {
            CompoundTag effectTag = tag.getCompound(tagType);
            int duration = 20;
            int amplifier = 0;
            boolean ambient = false;
            boolean showParticles = true;
            boolean showIcon = true;
            boolean noCounter = false;
            List<ItemStack> curativeItems = Lists.newArrayList();
            if (effectTag.contains("duration", TagTypes.ANY_NUMERIC)) duration = effectTag.getInt("duration");
            if (effectTag.contains("amplifier", TagTypes.ANY_NUMERIC)) amplifier = effectTag.getInt("amplifier");
            if (effectTag.contains("ambient", TagTypes.ANY_NUMERIC)) ambient = effectTag.getBoolean("ambient");
            if (effectTag.contains("show_particles", TagTypes.ANY_NUMERIC)) showParticles = effectTag.getBoolean("show_particles");
            if (effectTag.contains("show_icon", TagTypes.ANY_NUMERIC)) showIcon = effectTag.getBoolean("show_icon");
            if (effectTag.contains("no_counter", TagTypes.ANY_NUMERIC)) noCounter = effectTag.getBoolean("no_counter");
            if (effectTag.contains("curative_items", TagTypes.LIST)) {
                ListTag curativeList = effectTag.getList("curative_items", TagTypes.COMPOUND);
                for (int i = 0; i < curativeList.size(); i++) curativeItems.add(ItemStack.of(curativeList.getCompound(i)));
            }

            MobEffect effect = BuiltInRegistries.MOB_EFFECT.get(ResourceLocation.tryParse(effectTag.getString("id")));
            if (effect != null) {
                MobEffectInstance instance = new MobEffectInstance(effect, duration, amplifier, ambient, showParticles, showIcon);
                if (world.isClientSide) instance.setNoCounter(noCounter);
                if (!curativeItems.isEmpty()) instance.setCurativeItems(curativeItems);
                return instance;
            }
        }
        return defaultEffect;
    }*/
}
