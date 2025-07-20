package com.sophicreeper.backmath.util.fix;

import com.sophicreeper.backmath.entity.custom.aljan.ZombieFabricioEntity;
import com.sophicreeper.backmath.misc.BMRegistries;
import com.sophicreeper.backmath.util.TagTypes;
import com.sophicreeper.backmath.variant.queenlucy.QueenLucyVariant;
import com.sophicreeper.backmath.variant.queenlucypet.BMQueenLucyPetVariants;
import com.sophicreeper.backmath.variant.queenlucypet.QueenLucyPetVariant;
import com.sophicreeper.backmath.variant.wansophie.BMWandererSophieVariants;
import com.sophicreeper.backmath.variant.wansophie.WandererSophieVariant;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TagFixes {
    public static final Logger LOGGER = LogManager.getLogger("backmath/TagFixes");

    // Updates the old "SpellTicks" tag of Queen Lucy to the new "lucy_spells.spell_cooldown_ticks" tag.
    public static int moveSpellTicks(CompoundNBT tag) {
        CompoundNBT lucySpells = tag.getCompound("lucy_spells");
        if (!lucySpells.contains("spell_cooldown_ticks", TagTypes.ANY_NUMERIC) || tag.contains("SpellTicks", TagTypes.ANY_NUMERIC)) {
            lucySpells.putInt("spell_cooldown_ticks", tag.getInt("SpellTicks"));
            tag.remove("SpellTicks");
        }
        return lucySpells.getInt("spell_cooldown_ticks");
    }

    // Updates the old "Inventory" tag of archer lucias to the new "inventory" tag.
    public static ListNBT renameInventory(CompoundNBT tag) {
        if (tag.contains("Inventory", TagTypes.LIST)) {
            tag.put("inventory", tag.getList("Inventory", TagTypes.COMPOUND));
            tag.remove("Inventory");
        }
        return tag.getList("inventory", TagTypes.COMPOUND);
    }

    // Updates the old "CanBreakDoors" tag of insomnia zombies and zombie fabricios to the new "can_break_doors" tag.
    public static boolean renameCanBreakDoors(CompoundNBT tag) {
        if (tag.contains("CanBreakDoors", TagTypes.ANY_NUMERIC)) {
            tag.putBoolean("can_break_doors", tag.getBoolean("CanBreakDoors"));
            tag.remove("CanBreakDoors");
        }
        return tag.getBoolean("can_break_doors");
    }

    // Updates the old "Size" tag of amaracamelers to the new "size" tag.
    public static int renameSize(CompoundNBT tag) {
        if (tag.contains("Size", TagTypes.ANY_NUMERIC)) {
            tag.putInt("size", tag.getInt("Size"));
            tag.remove("Size");
        }
        return tag.getInt("size");
    }

    // Updates the old "wasOnGround" tag of amaracamelers to the new "was_on_ground" tag.
    public static boolean renameWasOnGround(CompoundNBT tag) {
        if (tag.contains("wasOnGround", TagTypes.ANY_NUMERIC)) {
            tag.putBoolean("was_on_ground", tag.getBoolean("wasOnGround"));
            tag.remove("wasOnGround");
        }
        return tag.getBoolean("was_on_ground");
    }

    // Updates the old "fabricio_conversion_ticks" tag of zombie fabricios to the new "shy_alcalyte_conversion_ticks" tag.
    public static void renameFabricioConversionTicks(CompoundNBT tag, ZombieFabricioEntity zombieFabricio) {
        if (tag.contains("fabricio_conversion_ticks", TagTypes.ANY_NUMERIC)) {
            tag.putInt("shy_alcalyte_conversion_ticks", tag.getInt("fabricio_conversion_ticks"));
            tag.remove("fabricio_conversion_ticks");
        }

        if (tag.contains("shy_alcalyte_conversion_ticks", TagTypes.ANY_NUMERIC) && tag.getInt("shy_alcalyte_conversion_ticks") > -1) {
            zombieFabricio.startConversionToShyAlcalyte(tag.hasUUID("converter_uuid") ? tag.getUUID("converter_uuid") : null, tag.getInt("shy_alcalyte_conversion_ticks"));
        }
    }

    public static QueenLucyVariant setQueenLucyVariant(CompoundNBT tag) {
        // if (!tag.contains("variant", TagTypes.STRING)) tag.putString("variant", BMQueenLucyVariants.CURRENT.get().getAssetID().toString());
        try {
            return QueenLucyVariant.DATA_DRIVEN_VARIANTS.get(new ResourceLocation(tag.getString("variant")));
        } catch (Exception exception) {
            LOGGER.warn(new TranslationTextComponent("error.backmath.queen_lucy_variant.load_error", tag.get("variant"), exception).getString());
            return BMRegistries.QUEEN_LUCY_VARIANT.getValue(new ResourceLocation(tag.getString("variant")));
        }
    }

    // Updates the old "SophieType" and "Variant" (integer) tags of wanderer sophies to the new "variant" (string/data-driven registry) tag.
    public static WandererSophieVariant updateWandererSophieVariant(CompoundNBT tag) {
        // Make the first versions of then friend sophies able to update properly to today's Back Math.
        if (tag.contains("SophieType", TagTypes.ANY_NUMERIC)) {
            tag.putInt("Variant", tag.getInt("SophieType"));
            tag.remove("SophieType");
        }

        tag.remove("variant_reg");
        if (tag.contains("Variant", TagTypes.ANY_NUMERIC)) {
            switch (tag.getInt("Variant")) {
                case 1: return BMWandererSophieVariants.CYAN_AXOLOTL.get();
                case 2: return BMWandererSophieVariants.CREEPER.get();
                case 3: return BMWandererSophieVariants.MODIFIED.get();
                case 4: return BMWandererSophieVariants.ORIGINAL.get();
                case 5: return BMWandererSophieVariants.SAVANNAH.get();
                case 6: return BMWandererSophieVariants.BRAZIL_SHIRT.get();
                case 7: return BMWandererSophieVariants.PAJAMA.get();
                case 8: return BMWandererSophieVariants.WITCHER.get();
                case 9: return BMWandererSophieVariants.MAID.get();
                case 10: return BMWandererSophieVariants.ENDER.get();
                case 11: return BMWandererSophieVariants.WORKER.get();
                case 12: return BMWandererSophieVariants.BLUE_AXOLOTL.get();
                case 13: return BMWandererSophieVariants.CYAN_AXOLOTL_2.get();
                case 14: return BMWandererSophieVariants.EMPRESARY2.get();
                case 15: return BMWandererSophieVariants.ENTREPRENEUR.get();
                case 0: default: return BMWandererSophieVariants.YELLOW_AXOLOTL.get();
            }
        }
        try {
            return WandererSophieVariant.DATA_DRIVEN_VARIANTS.get(new ResourceLocation(tag.getString("variant")));
        } catch (Exception exception) {
            LOGGER.warn(new TranslationTextComponent("error.backmath.wanderer_sophie_variant.load_error", tag.get("variant"), exception).getString());
            return BMRegistries.WANDERER_SOPHIE_VARIANT.getValue(new ResourceLocation(tag.getString("variant")));
        }
    }

    // Updates the old "Variant" (integer) and tag of queen lucy pets to the new "variant" (string/data-driven registry) tag.
    public static QueenLucyPetVariant updateQueenLucyPetVariant(CompoundNBT tag) {
        if (tag.contains("Variant", TagTypes.ANY_NUMERIC)) {
            switch (tag.getInt("Variant")) {
                case 1: return BMQueenLucyPetVariants.ALTERNATE.get();
                case 2: return BMQueenLucyPetVariants.RELIC.get();
                case 3: return BMQueenLucyPetVariants.SHY_FABRICIO.get();
                case 4: return BMQueenLucyPetVariants.SV_WORKER.get();
                case 5: return BMQueenLucyPetVariants.SV_ORIGINAL.get();
                case 6: return BMQueenLucyPetVariants.SV_MODIFIED.get();
                case 7: return BMQueenLucyPetVariants.SV_CREEPER.get();
                case 8: return BMQueenLucyPetVariants.SV_BRAZIL_SHIRT.get();
                case 9: return BMQueenLucyPetVariants.SV_PAJAMA.get();
                case 10: return BMQueenLucyPetVariants.SV_SAVANNAH.get();
                case 11: return BMQueenLucyPetVariants.SV_WITCHER.get();
                case 12: return BMQueenLucyPetVariants.SV_MAID.get();
                case 13: return BMQueenLucyPetVariants.SV_YELLOW_AXOLOTL.get();
                case 14: return BMQueenLucyPetVariants.SV_CYAN_AXOLOTL.get();
                case 15: return BMQueenLucyPetVariants.SV_ENDER.get();
                case 16: return BMQueenLucyPetVariants.SV_EMPRESARY2.get();
                case 17: return BMQueenLucyPetVariants.SV_ENTREPRENEUR.get();
                case 18: return BMQueenLucyPetVariants.SV_BLUE_AXOLOTL.get();
                case 19: return BMQueenLucyPetVariants.SV_CYAN_AXOLOTL_2.get();
                case 0: default: return BMQueenLucyPetVariants.CURRENT.get();
            }
        }
        try {
            return QueenLucyPetVariant.DATA_DRIVEN_VARIANTS.get(new ResourceLocation(tag.getString("variant")));
        } catch (Exception exception) {
            LOGGER.warn(new TranslationTextComponent("error.backmath.queen_lucy_pet_variant.load_error", tag.get("variant"), exception).getString());
            return BMRegistries.QUEEN_LUCY_PET_VARIANT.getValue(new ResourceLocation(tag.getString("variant")));
        }
    }
}
