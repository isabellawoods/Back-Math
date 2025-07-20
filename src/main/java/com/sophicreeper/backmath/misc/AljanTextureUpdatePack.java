package com.sophicreeper.backmath.misc;

import com.google.common.base.Joiner;
import com.sophicreeper.backmath.util.BMUtils;
import net.minecraft.client.resources.I18n;
import net.minecraft.resources.ResourcePack;
import net.minecraft.resources.ResourcePackFileNotFoundException;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.loading.moddiscovery.ModFile;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

// All methods mostly copied from Twilight Forest/Forge.
public class AljanTextureUpdatePack extends ResourcePack {
    public static final String PACK_LOCATION = String.format("assets/backmath/resourcepacks/%s/", BMUtils.ALJAN_TEXTURE_UPDATE_ID);
    private final ModFile file;

    public AljanTextureUpdatePack(ModFile file) {
        super(file.getFilePath().toFile());
        this.file = file;
    }

    @Override
    @Nonnull
    public String getName() {
        String translation = "resource_pack.backmath." + BMUtils.ALJAN_TEXTURE_UPDATE_ID;
        if (!I18n.exists(translation)) return "Aljan Texture Update";
        return new TranslationTextComponent(translation).getString();
    }

    @Override
    @Nonnull
    public Collection<ResourceLocation> getResources(ResourcePackType type, String resourceNamespace, String pathIn, int maxDepth, Predicate<String> filter) {
        try {
            Path root = this.file.getLocator().findPath(this.file, PACK_LOCATION + type.getDirectory(), resourceNamespace).toAbsolutePath();
            Path inputPath = root.getFileSystem().getPath(pathIn);

            return Files.walk(root).
                    map(path -> root.relativize(path.toAbsolutePath())).
                    filter(path -> path.getNameCount() <= maxDepth). // Make sure the depth is within bounds
                            filter(path -> !path.toString().endsWith(".mcmeta")). // Ignore .mcmeta files
                            filter(path -> path.startsWith(inputPath)). // Make sure the target path is inside this one
                            filter(path -> filter.test(path.getFileName().toString())). // Test the file name against the predicate
                    // Finally we need to form the RL, so use the first name as the domain, and the rest as the path
                    // It is VERY IMPORTANT that we do not rely on Path.toString as this is inconsistent between operating systems
                    // Join the path names ourselves to force forward slashes
                            map(path -> new ResourceLocation(resourceNamespace, Joiner.on('/').join(path))).
                    collect(Collectors.toList());
        } catch (IOException exception) {
            return Collections.emptyList();
        }
    }

    @Override
    @Nonnull
    public Set<String> getNamespaces(ResourcePackType type) {
        try {
            Path root = this.file.getLocator().findPath(this.file, PACK_LOCATION + type.getDirectory()).toAbsolutePath();
            return Files.walk(root,1)
                    .map(path -> root.relativize(path.toAbsolutePath()))
                    .filter(path -> path.getNameCount() > 0) // skip the root entry
                    .map(path -> path.toString().replaceAll("/$","")) // remove the trailing slash, if present
                    .filter(string -> !string.isEmpty()) // filter empty strings, otherwise empty strings default to minecraft in ResourceLocations
                    .collect(Collectors.toSet());
        } catch (IOException exception) {
            if (type == ResourcePackType.SERVER_DATA) { // We still have to add the resource namespace if client resources exist, as we load languages (which are in assets) on server
                return this.getNamespaces(ResourcePackType.CLIENT_RESOURCES);
            } else {
                return Collections.emptySet();
            }
        }
    }

    @Override
    @Nonnull
    protected InputStream getResource(String name) throws IOException {
        Path path = this.file.getLocator().findPath(this.file, PACK_LOCATION + name);
        if (!Files.exists(path)) throw new ResourcePackFileNotFoundException(this.file.getFilePath().toFile(), name);
        return Files.newInputStream(path, StandardOpenOption.READ);
    }

    @Override
    @Nonnull
    public InputStream getResource(ResourcePackType type, ResourceLocation location) throws IOException {
        if (location.getPath().startsWith("lang/")) {
            return super.getResource(ResourcePackType.CLIENT_RESOURCES, location);
        } else {
            return super.getResource(type, location);
        }
    }

    @Override
    protected boolean hasResource(String name) {
        return Files.exists(this.file.getLocator().findPath(this.file, PACK_LOCATION + name));
    }

    @Override
    public boolean hasResource(ResourcePackType type, ResourceLocation location) {
        if (location.getPath().startsWith("lang/")) {
            return super.hasResource(ResourcePackType.CLIENT_RESOURCES, location);
        } else {
            return super.hasResource(type, location);
        }
    }

    @Override
    public void close() {}
}
