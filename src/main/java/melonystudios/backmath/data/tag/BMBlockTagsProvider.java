package melonystudios.backmath.data.tag;

import melonystudios.backmath.BackMath;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class BMBlockTagsProvider extends BlockTagsProvider {
    public BMBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper fileHelper) {
        super(output, lookupProvider, BackMath.MOD_ID, fileHelper);
    }

    @Override
    @NotNull
    public String getName() {
        return "Back Math - Block Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {}
}
