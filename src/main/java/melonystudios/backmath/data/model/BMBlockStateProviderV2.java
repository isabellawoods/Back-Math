package melonystudios.backmath.data.model;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import static melonystudios.backmath.block.BMBlocks.*;

public class BMBlockStateProviderV2 extends BMBlockStateModels {
    public BMBlockStateProviderV2(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, fileHelper);
    }

    @Override
    @NotNull
    public String getName() {
        return "Back Math - Block State Definitions and Models V2";
    }

    @Override
    protected void registerStatesAndModels() {
        fluid(HILLARY.get(), modLoc("block/hilary_still"));
        fluid(MILKLLARY.get(), modLoc("block/milklary_still"));
        fluid(LIQUID_ALJAME.get(), modLoc("block/liquid_aljame"));
        fluid(LIQUID_MANGA.get(), modLoc("block/liquid_manga"));
        fluid(LIQUEFIED_MONSTER.get(), modLoc("block/liquefied_monster"));
        fluid(SLEEPISHWATER.get(), modLoc("block/sleepishwater"));
    }
}
