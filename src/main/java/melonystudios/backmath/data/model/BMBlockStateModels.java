package melonystudios.backmath.data.model;

import melonystudios.backmath.BackMath;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public abstract class BMBlockStateModels extends BlockStateProvider {
    public BMBlockStateModels(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, BackMath.MOD_ID, fileHelper);
    }

    public void fluid(Block block, ResourceLocation stillTexture) {
        simpleBlock(block, models().getBuilder(BuiltInRegistries.BLOCK.getKey(block).getPath()).texture("particle", stillTexture));
    }
}
