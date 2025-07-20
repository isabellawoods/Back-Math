package com.sophicreeper.backmath.util;

import com.google.common.collect.Lists;
import com.sophicreeper.backmath.BackMath;
import com.sophicreeper.backmath.entity.custom.QueenLucyEntity;
import com.sophicreeper.backmath.entity.custom.QueenLucyPetEntity;
import com.sophicreeper.backmath.entity.custom.WandererSophieEntity;
import com.sophicreeper.backmath.entity.custom.termian.TermianPatrollerEntity;
import com.sophicreeper.backmath.entity.outfit.OutfitDefinition;
import com.sophicreeper.backmath.entity.outfit.OutfitSlot;
import com.sophicreeper.backmath.item.AxolotlTest;
import com.sophicreeper.backmath.util.revaried.RVUtils;
import com.sophicreeper.backmath.variant.queenlucy.QueenLucyVariant;
import com.sophicreeper.backmath.variant.queenlucypet.QueenLucyPetVariant;
import com.sophicreeper.backmath.variant.wansophie.WandererSophieVariant;
import com.sophicreeper.backmath.world.structure.BMStructures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration;
import net.minecraftforge.client.event.EntityViewRenderEvent;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/// Just generalized methods that are used more than twice throughout the code.
public class BMUtils {
    public static final List<String> VALID_WOOD_TYPES = Lists.newArrayList("aljanwood", "aljancap", "insomnian", "avondalic_willow");
    public static final String ALJAN_TEXTURE_UPDATE_ID = "aljan_texture_update";
    public static final int EMISSIVE_LIGHT_VALUE = 0xF00000;
    public static final int END_PORTAL_OPEN = 1038;
    private static final float[] FOG_COLORS = new float[3];
    private static float COLOR = 0;

    /// Plays the item pickup sound at a (server) player.
    public static void playItemPickupSound(ServerPlayerEntity serverPlayer) {
        float pitch = ((serverPlayer.getRandom().nextFloat() - serverPlayer.getRandom().nextFloat()) * 0.7F + 1) * 2;
        serverPlayer.level.playSound(null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), SoundEvents.ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, pitch);
    }

    /// Adds a tooltip line for an effect.
    public static IFormattableTextComponent addEffectTooltip(Effect effect, int duration, int amplifier) {
        IFormattableTextComponent component = new TranslationTextComponent("potion.withAmplifier", new TranslationTextComponent(effect.getDescriptionId()), new TranslationTextComponent("potion.potency." + amplifier));
        return new TranslationTextComponent("potion.withDuration", component, StringUtils.formatTickDuration(duration)).withStyle(RVUtils.getFromRGB(effect.getColor()));
    }

    /// Adds the Bakugou armor set to the entity.
    /// <p>
    /// Used to replace the armor entirely.
    public static void addBakugouOutfit(LivingEntity livEntity) {
        if (livEntity.getItemBySlot(EquipmentSlotType.HEAD).isEmpty()) livEntity.setItemSlot(EquipmentSlotType.HEAD, new ItemStack(AxolotlTest.BAKUGOU_HAIR.get()));
        if (livEntity.getItemBySlot(EquipmentSlotType.CHEST).isEmpty()) livEntity.setItemSlot(EquipmentSlotType.CHEST, new ItemStack(AxolotlTest.BAKUGOU_BLOUSE.get()));
        if (livEntity.getItemBySlot(EquipmentSlotType.LEGS).isEmpty()) livEntity.setItemSlot(EquipmentSlotType.LEGS, new ItemStack(AxolotlTest.BAKUGOU_PANTS.get()));
        if (livEntity.getItemBySlot(EquipmentSlotType.FEET).isEmpty()) livEntity.setItemSlot(EquipmentSlotType.FEET, new ItemStack(AxolotlTest.BAKUGOU_SHOES.get()));
        livEntity.playSound(SoundEvents.ARMOR_EQUIP_LEATHER, 1, 1);
    }

    /// Returns a Back Fields Explorer Map. Used by cartographer villagers.
    public static ItemStack makeBackFieldsExplorerMap(Entity trader) {
        if (trader.level instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) trader.level;
            BlockPos structurePos = serverWorld.findNearestMapFeature(BMStructures.SOPHIE_TOWER.get(), trader.blockPosition(), 100, true);
            if (structurePos != null) {
                ItemStack mapStack = FilledMapItem.create(serverWorld, structurePos.getX(), structurePos.getZ(), (byte) 2, true, true);
                FilledMapItem.renderBiomePreviewMap(serverWorld, mapStack);
                MapData.addTargetDecoration(mapStack, structurePos, "+", MapDecoration.Type.TARGET_X);
                mapStack.setHoverName(new TranslationTextComponent("filled_map." + BMStructures.SOPHIE_TOWER.get().getFeatureName().toLowerCase(Locale.ROOT)));
                CompoundNBT displayTag = mapStack.getOrCreateTagElement("display");
                displayTag.putInt("MapColor", 0x3D267F);
                return mapStack;
            } else {
                return new ItemStack(Items.MAP);
            }
        }
        return new ItemStack(Items.MAP);
    }

    /// Returns a Termian Empire Banner. Used by termian patrollers.
    public static ItemStack getTermianBannerInstance() {
        ItemStack lightBlueBanner = new ItemStack(Items.LIGHT_BLUE_BANNER);
        CompoundNBT blockEntityTag = lightBlueBanner.getOrCreateTagElement("BlockEntityTag");
        ListNBT patterns = new BannerPattern.Builder().addPattern(BannerPattern.GRADIENT_UP, DyeColor.PURPLE).addPattern(BannerPattern.STRIPE_CENTER, DyeColor.LIGHT_BLUE).addPattern(
                BannerPattern.RHOMBUS_MIDDLE, DyeColor.CYAN).addPattern(BannerPattern.FLOWER, DyeColor.RED).addPattern(BannerPattern.FLOWER, DyeColor.YELLOW).toListTag();
        blockEntityTag.put("Patterns", patterns);
        lightBlueBanner.hideTooltipPart(ItemStack.TooltipDisplayFlags.ADDITIONAL);
        lightBlueBanner.setHoverName(new TranslationTextComponent("block." + BackMath.MOD_ID + ".termian_empire_banner").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x1DC2D1)).withItalic(false)));
        return lightBlueBanner;
    }

    /// Returns the resource location for a Termian Patroller's cape.
    public static ResourceLocation getTermianPatrollerCape(TermianPatrollerEntity patroller) {
        String capeNamespace = new ResourceLocation(patroller.getCapeTexture()).getNamespace();
        String capePath = new ResourceLocation(patroller.getCapeTexture()).getPath();

        if (capePath.isEmpty()) capePath = "cape/cherry_blossom";
        return new ResourceLocation(capeNamespace, "textures/entity/" + capePath + ".png");
    }

    /// Sets a random cape to a Termian Patroller entity (out of 16 vanilla/default capes).
    public static void setRandomCape(TermianPatrollerEntity patroller, Random rand) {
        List<ResourceLocation> capeTextures = Lists.newArrayList(BackMath.backMath("cape/cherry_blossom"), BackMath.backMath("cape/migrator"),
                BackMath.backMath("cape/vanilla"), BackMath.backMath("cape/followers"), BackMath.backMath("cape/purple_heart"),
                BackMath.backMath("cape/15th_anniversary"), BackMath.backMath("cape/pan"), BackMath.backMath("cape/mc_championship"),
                BackMath.backMath("cape/minecraft_experience"), BackMath.backMath("cape/mojang_office"), BackMath.backMath("cape/home"),
                BackMath.backMath("cape/menace"), BackMath.backMath("cape/yearn"), BackMath.backMath("cape/common"),
                BackMath.backMath("cape/founders"), BackMath.backMath("cape/progress_pride"));
        patroller.setCapeTexture(capeTextures.get(rand.nextInt(16)).toString());
    }

    /// Sets a random Wanderer Sophie variant from the <code>mob_variant/wanderer_sophie</code> data folder.
    public static void randomizeWandererSophieVariant(WandererSophieEntity sophie) {
        ResourceLocation[] variants = WandererSophieVariant.DATA_DRIVEN_VARIANTS.keySet().toArray(new ResourceLocation[0]);
        ResourceLocation variant = variants[sophie.level.random.nextInt(WandererSophieVariant.DATA_DRIVEN_VARIANTS.size())];
        while (WandererSophieVariant.DATA_DRIVEN_VARIANTS.get(variant) != null &&
                !WandererSophieVariant.DATA_DRIVEN_VARIANTS.get(variant).spawnsNaturally()) {
            variant = variants[sophie.level.random.nextInt(WandererSophieVariant.DATA_DRIVEN_VARIANTS.size())];
        }
        sophie.setVariant(variant);
    }

    /// Sets a random Queen Lucy variant from the <code>mob_variant/queen_lucy</code> data folder.
    public static QueenLucyVariant getQueenLucyVariant(QueenLucyEntity lucy) {
        ResourceLocation[] variants = QueenLucyVariant.DATA_DRIVEN_VARIANTS.keySet().toArray(new ResourceLocation[0]);
        ResourceLocation variant = variants[lucy.level.random.nextInt(QueenLucyVariant.DATA_DRIVEN_VARIANTS.size())];
        return QueenLucyVariant.DATA_DRIVEN_VARIANTS.get(variant);
    }

    /// Sets a random Queen Lucy Pet variant from the <code>mob_variant/queen_lucy_pet</code> data folder.
    public static void randomizeQueenLucyPetVariant(QueenLucyPetEntity lucy) {
        ResourceLocation[] variants = QueenLucyPetVariant.DATA_DRIVEN_VARIANTS.keySet().toArray(new ResourceLocation[0]);
        ResourceLocation variant = variants[lucy.level.random.nextInt(QueenLucyPetVariant.DATA_DRIVEN_VARIANTS.size())];
        lucy.setVariant(variant);
    }

    /// Gets the wood type for a boat from the string tag <code>wood_type</code> if available, or the <code>woodType</code> parameter if it isn't.
    /// @apiNote <b>This is not compatible with Revaried's boats</b> -- using a Revaried wood type on a Back Math boat won't work, and vice versa.
    public static String getBoatType(ItemStack stack, String woodType) {
        CompoundNBT tag = stack.getTag();
        if (tag != null && tag.contains("wood_type", TagTypes.STRING)) {
            if (isValidWoodType(tag.getString("wood_type"))) return tag.getString("wood_type");
        }
        return woodType;
    }

    /// Whether a wood type for a boat is valid for this Back Math boat.
    public static boolean isValidWoodType(String woodType) {
        return VALID_WOOD_TYPES.contains(woodType);
    }

    /// Whether the <b>"Aljan Texture Update"</b> resource pack is enabled.
    public static boolean aljanPackEnabled() {
        return Minecraft.getInstance().getResourcePackRepository().getSelectedIds().contains(BackMath.backMath(ALJAN_TEXTURE_UPDATE_ID).toString());
    }

    /// Custom overlay coordinates method to remove the red tint from taking damage or dying.
    public static int getOverlayCoordinates(float u) {
        return OverlayTexture.pack(OverlayTexture.u(u), OverlayTexture.v(false));
    }

    /// Copied from <code>teamtwilight/twilightforest</code>. Smoothly transitions the fog color in the Aljan to a light purple color at nighttime.
    /// @param event The {@link EntityViewRenderEvent.FogColors} event.
    /// @param shouldApplyColors Whether it should be applying or not the colors of the Aljan night sky.
    public static void transitionFogColor(EntityViewRenderEvent.FogColors event, boolean shouldApplyColors) {
        float[] baseColors = {event.getRed(), event.getGreen(), event.getBlue()};
        float[] targetColors = {0.333F, 0.231F, 0.305F};
        for (int i = 0; i < 3; i++) {
            float base = baseColors[i];
            float target = targetColors[i];
            boolean inverse = base > target;
            FOG_COLORS[i] = base == target ? target : (float) MathHelper.clampedLerp(inverse ? target : base, inverse ? base : target, COLOR);
        }
        float shift = (float) (0.01F * event.getRenderPartialTicks());
        if (shouldApplyColors) COLOR += shift;
        else COLOR -= shift;
        COLOR = MathHelper.clamp(COLOR, 0, 1);
        event.setRed(FOG_COLORS[0]);
        event.setGreen(FOG_COLORS[1]);
        event.setBlue(FOG_COLORS[2]);
    }

    /// Alternate version of {@link LivingEntity#curePotionEffects(ItemStack)} that bypasses the curative items check. It still won't cure it if it doesn't have a curative item.
    ///  @param livEntity The entity that it's clearing the effects from.
    public static boolean clearMobEffects(LivingEntity livEntity) {
        if (livEntity.level.isClientSide) return false;
        boolean curedAny = false;
        for (EffectInstance instance : livEntity.getActiveEffects()) {
            if (!instance.getCurativeItems().isEmpty()) {
                livEntity.removeEffect(instance.getEffect());
                curedAny = true;
            }
        }
        return curedAny;
    }

    /// Gets the color of an outfit slot. If the outfit slot has a color, it chooses that, if it doesn't, but the item stack does, it picks the item stack's color.
    /// @param assetID The asset id of the outfit definition.
    /// @param stack An optional item stack to check for colors.
    /// @param slotType The slot being checked for the colors.
    public static float[] getOutfitColors(ResourceLocation assetID, @Nullable ItemStack stack, EquipmentSlotType slotType) {
        if (OutfitDefinition.DATA_DRIVEN_OUTFITS.containsKey(assetID)) {
            OutfitDefinition definition = OutfitDefinition.DATA_DRIVEN_OUTFITS.get(assetID);
            OutfitSlot slot = OutfitDefinition.byEquipmentSlot(definition, slotType);
            if (slot != null && slot.color() != null) {
                try {
                    int color = slot.color();
                    float red = (float) (color >> 16 & 255) / 255;
                    float green = (float) (color >> 8 & 255) / 255;
                    float blue = (float) (color & 255) / 255;
                    return new float[] {red, green, blue};
                } catch (NullPointerException exception) {
                    return new float[] {1, 1, 1};
                }
            } else if (stack != null) {
                Integer color = null;
                CompoundNBT displayTag = stack.getTagElement("display");
                if (displayTag != null && displayTag.contains("color", TagTypes.ANY_NUMERIC)) {
                    color = displayTag.getInt("color");
                } else if (stack.getItem() instanceof IDyeableArmorItem) {
                    color = ((IDyeableArmorItem) stack.getItem()).getColor(stack);
                }

                if (color != null) {
                    float red = (float) (color >> 16 & 255) / 255;
                    float green = (float) (color >> 8 & 255) / 255;
                    float blue = (float) (color & 255) / 255;
                    return new float[] {red, green, blue};
                }
            }
        }
        return new float[] {1, 1, 1};
    }
}
