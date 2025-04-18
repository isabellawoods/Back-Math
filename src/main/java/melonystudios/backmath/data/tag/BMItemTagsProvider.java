package melonystudios.backmath.data.tag;

import melonystudios.backmath.BackMath;
import melonystudios.backmath.item.AxolotlTest;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class BMItemTagsProvider extends ItemTagsProvider {
    public BMItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper fileHelper) {
        super(output, lookupProvider, blockTags, BackMath.MOD_ID, fileHelper);
    }

    @Override
    @NotNull
    public String getName() {
        return "Back Math - Item Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ItemTags.CHEST_ARMOR).add(AxolotlTest.GOLDEN_PLATED.get());
    }
}
