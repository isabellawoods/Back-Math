package com.sophicreeper.backmath.crystallizer;

import com.sophicreeper.backmath.crystallizer.advanced.AdvancedMolds;
import net.minecraft.util.text.TranslationTextComponent;

import static com.sophicreeper.backmath.BackMath.*;

public class MoldUtils {
    public static Molds getMoldFromString(String mold) {
        switch (mold) {
            case "singularity": return Molds.SINGULARITY;
            case "crystallized": return Molds.CRYSTALLIZED;
            case "ingot": return Molds.INGOT;
            case "mold": return Molds.MOLD;
            case "rod": return Molds.ROD;
            case "empty": return Molds.EMPTY;
            default:
                LOGGER.error(new TranslationTextComponent("backmath.message_template", new TranslationTextComponent("error.backmath.crystallizer.invalid_mold", mold)).getString());
                return Molds.EMPTY;
        }
    }

    public static AdvancedMolds getAdvancedMoldFromString(String mold) {
        switch (mold) {
            case "singularity": return AdvancedMolds.SINGULARITY;
            case "crystallized": return AdvancedMolds.CRYSTALLIZED;
            case "ingot": return AdvancedMolds.INGOT;
            case "mold": return AdvancedMolds.MOLD;
            case "rod": return AdvancedMolds.ROD;
            case "crystalline_empty": return AdvancedMolds.CRYSTALLINE_EMPTY;
            case "crystalline_gem": return AdvancedMolds.CRYSTALLINE_GEM;
            case "empty": return AdvancedMolds.EMPTY;
            default:
                LOGGER.error(new TranslationTextComponent("backmath.message_template", new TranslationTextComponent("error.backmath.crystalline_crystallizer.invalid_mold", mold)).getString());
                return AdvancedMolds.EMPTY;
        }
    }

    public static Molds getNext(Molds mold) {
        switch (mold) {
            case EMPTY: return Molds.SINGULARITY;
            case SINGULARITY: return Molds.CRYSTALLIZED;
            case CRYSTALLIZED: return Molds.INGOT;
            case INGOT: return Molds.MOLD;
            case MOLD: return Molds.ROD;
            case ROD: return Molds.EMPTY;
        }
        return Molds.EMPTY;
    }

    public static AdvancedMolds getNextAdvanced(AdvancedMolds mold) {
        switch (mold) {
            case EMPTY: return AdvancedMolds.SINGULARITY;
            case SINGULARITY: return AdvancedMolds.CRYSTALLIZED;
            case CRYSTALLIZED: return AdvancedMolds.INGOT;
            case INGOT: return AdvancedMolds.MOLD;
            case MOLD: return AdvancedMolds.ROD;
            case ROD: return AdvancedMolds.CRYSTALLINE_EMPTY;
            case CRYSTALLINE_EMPTY: return AdvancedMolds.CRYSTALLINE_GEM;
            case CRYSTALLINE_GEM: return AdvancedMolds.EMPTY;
        }
        return AdvancedMolds.EMPTY;
    }

    public static AdvancedMolds getNextAdvancedWithoutCrystalline(AdvancedMolds mold) {
        switch (mold) {
            case EMPTY: return AdvancedMolds.SINGULARITY;
            case SINGULARITY: return AdvancedMolds.CRYSTALLIZED;
            case CRYSTALLIZED: return AdvancedMolds.INGOT;
            case INGOT: return AdvancedMolds.MOLD;
            case MOLD: return AdvancedMolds.ROD;
            case ROD: return AdvancedMolds.EMPTY;
        }
        return AdvancedMolds.EMPTY;
    }
}
