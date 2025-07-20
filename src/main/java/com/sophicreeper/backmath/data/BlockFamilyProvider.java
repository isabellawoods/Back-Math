package com.sophicreeper.backmath.data;

import com.google.common.collect.Lists;
import com.sophicreeper.backmath.block.custom.GrapeVinePostBlock;
import net.minecraft.block.*;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sophicreeper.backmath.BackMath.backMath;
import static net.minecraft.data.ModelTextures.getBlockTexture;

public class BlockFamilyProvider {
    private static final Map<String, Block> BLOCK_TYPES = new HashMap<>();
    private static final Map<String, List<Object>> EXTRA_PROPERTIES = new HashMap<>();
    private final BlockStateProvider stateProvider;
    private final String materialName;
    private final ResourceLocation texture;

    public BlockFamilyProvider(BlockStateProvider stateProvider, String materialName, ResourceLocation texture) {
        this.stateProvider = stateProvider;
        this.materialName = materialName;
        this.texture = texture;
    }

    // Wood Blocks
    public BlockFamilyProvider log(Block log, Block wood, Block strippedLog, Block strippedWood) {
        BLOCK_TYPES.put("log", log);
        BLOCK_TYPES.put("wood", wood);
        BLOCK_TYPES.put("stripped_log", strippedLog);
        BLOCK_TYPES.put("stripped_wood", strippedWood);
        return this;
    }

    public BlockFamilyProvider grapeVinePost(Block grapeVinePost) {
        BLOCK_TYPES.put("grape_vine_post", grapeVinePost);
        return this;
    }

    public BlockFamilyProvider ladder(Block ladder) {
        BLOCK_TYPES.put("ladder", ladder);
        return this;
    }

    // Common Blocks
    public BlockFamilyProvider fullBlock(Block block) {
        BLOCK_TYPES.put("full_block", block);
        return this;
    }

    // Common Block Types
    public BlockFamilyProvider stairs(Block stairs) {
        BLOCK_TYPES.put("stairs", stairs);
        return this;
    }

    public BlockFamilyProvider slab(Block slab) {
        BLOCK_TYPES.put("slab", slab);
        return this;
    }

    public BlockFamilyProvider doubleSlab(Block slab, ResourceLocation sideTexture) {
        BLOCK_TYPES.put("double_slab", slab);
        EXTRA_PROPERTIES.put("double_slab", Lists.newArrayList(sideTexture));
        return this;
    }

    public BlockFamilyProvider wall(Block wall) {
        BLOCK_TYPES.put("wall", wall);
        return this;
    }

    public BlockFamilyProvider fence(Block fence) {
        BLOCK_TYPES.put("fence", fence);
        return this;
    }

    public BlockFamilyProvider fenceGate(Block fenceGate) {
        BLOCK_TYPES.put("fence_gate", fenceGate);
        return this;
    }

    public BlockFamilyProvider door(Block door) {
        BLOCK_TYPES.put("door", door);
        return this;
    }

    public BlockFamilyProvider trapdoor(Block trapdoor) {
        this.trapdoor(trapdoor, true);
        return this;
    }

    public BlockFamilyProvider trapdoor(Block trapdoor, boolean orientable) {
        BLOCK_TYPES.put("trapdoor", trapdoor);
        EXTRA_PROPERTIES.put("trapdoor", Lists.newArrayList(orientable));
        return this;
    }

    public BlockFamilyProvider pressurePlate(Block pressurePlate) {
        BLOCK_TYPES.put("pressure_plate", pressurePlate);
        return this;
    }

    public BlockFamilyProvider weightedPressurePlate(Block pressurePlate) {
        BLOCK_TYPES.put("weighted_pressure_plate", pressurePlate);
        return this;
    }

    public BlockFamilyProvider button(Block button) {
        BLOCK_TYPES.put("button", button);
        return this;
    }

    public BlockFamilyProvider sign(Block sign, Block wallSign) {
        BLOCK_TYPES.put("sign", sign);
        BLOCK_TYPES.put("wall_sign", wallSign);
        return this;
    }

    // Colored Blocks
    public BlockFamilyProvider concrete(Block concretePowder, Block concrete) {
        BLOCK_TYPES.put("concrete_powder", concretePowder);
        BLOCK_TYPES.put("concrete", concrete);
        return this;
    }

    public BlockFamilyProvider terracotta(Block terracotta, Block glazedTerracotta) {
        BLOCK_TYPES.put("terracotta", terracotta);
        BLOCK_TYPES.put("glazed_terracotta", glazedTerracotta);
        return this;
    }

    public BlockFamilyProvider wool(Block wool, Block carpet) {
        BLOCK_TYPES.put("wool", wool);
        BLOCK_TYPES.put("carpet", carpet);
        return this;
    }

    public BlockFamilyProvider stainedGlass(Block stainedGlass, Block stainedGlassPane) {
        BLOCK_TYPES.put("stained_glass", stainedGlass);
        BLOCK_TYPES.put("stained_glass_pane", stainedGlassPane);
        return this;
    }

    public void build() {
        for (String type : BLOCK_TYPES.keySet()) {
            Block typeBlock = BLOCK_TYPES.get(type);
            assert typeBlock.getRegistryName() != null;

            // Wood Blocks
            if (type.equals("log")) this.stateProvider.axisBlock((RotatedPillarBlock) typeBlock, backMath("block/" + this.materialName + "_log"), backMath("block/" + this.materialName + "_log_top"));
            if (type.equals("wood")) this.stateProvider.axisBlock((RotatedPillarBlock) typeBlock, backMath("block/" + this.materialName + "_log"), backMath("block/" + this.materialName + "_log"));
            if (type.equals("stripped_log")) this.stateProvider.axisBlock((RotatedPillarBlock) typeBlock, backMath("block/stripped_" + this.materialName + "_log"), backMath("block/stripped_" + this.materialName + "_log_top"));
            if (type.equals("stripped_wood")) this.stateProvider.axisBlock((RotatedPillarBlock) typeBlock, backMath("block/stripped_" + this.materialName + "_log"), backMath("block/stripped_" + this.materialName + "_log"));
            if (type.equals("grape_vine_post")) grapeVinePost((GrapeVinePostBlock) typeBlock, this.texture);
            if (type.equals("ladder")) ladder(typeBlock, getBlockTexture(typeBlock));
            if (type.equals("sign")) this.stateProvider.simpleBlock(typeBlock, models().getBuilder(typeBlock.getRegistryName().getPath()).texture("particle", this.texture));
            if (type.equals("wall_sign")) this.stateProvider.simpleBlock(typeBlock, models().getBuilder(typeBlock.getRegistryName().getPath()).texture("particle", this.texture));

            // Common Blocks
            if (type.equals("full_block")) this.stateProvider.simpleBlock(typeBlock);
            if (type.equals("stairs")) this.stateProvider.stairsBlock((StairsBlock) typeBlock, this.texture);
            if (type.equals("slab")) this.stateProvider.slabBlock((SlabBlock) typeBlock, this.texture, this.texture, this.texture, this.texture);
            if (type.equals("double_slab")) doubleSlab(typeBlock);
            if (type.equals("wall")) wallBlock(typeBlock, this.texture);
            if (type.equals("fence")) fenceBlock(typeBlock, this.texture);
            if (type.equals("fence_gate")) this.stateProvider.fenceGateBlock((FenceGateBlock) typeBlock, this.texture);
            if (type.equals("door")) this.stateProvider.doorBlock((DoorBlock) typeBlock, backMath("block/" + this.materialName + "_door_bottom"), backMath("block/" + this.materialName + "_door_top"));
            if (type.equals("trapdoor")) this.stateProvider.trapdoorBlock((TrapDoorBlock) typeBlock, backMath("block/" + this.materialName + "_trapdoor"), (Boolean) EXTRA_PROPERTIES.get("trapdoor").get(0));
            if (type.equals("pressure_plate")) pressurePlate(typeBlock, this.texture);
            if (type.equals("weighted_pressure_plate")) weightedPressurePlate(typeBlock, this.texture);
            if (type.equals("button")) button((AbstractButtonBlock) typeBlock, this.texture);

            // Colored Blocks
            if (type.equals("concrete")) blockFromCustom(typeBlock);
            if (type.equals("concrete_powder")) blockFromCustom(typeBlock);
            if (type.equals("terracotta")) blockFromCustom(typeBlock);
            if (type.equals("glazed_terracotta")) this.stateProvider.horizontalBlock(typeBlock, getBlockTexture(typeBlock), getBlockTexture(typeBlock), getBlockTexture(typeBlock));
            if (type.equals("wool")) blockFromCustom(typeBlock);
            if (type.equals("carpet")) this.stateProvider.simpleBlock(typeBlock, models().carpet(typeBlock.getRegistryName().getPath(), getBlockTexture(BLOCK_TYPES.get("wool"))));
            if (type.equals("stained_glass")) blockFromCustom(typeBlock);
            if (type.equals("stained_glass_pane")) this.stateProvider.paneBlock((PaneBlock) typeBlock, getBlockTexture(BLOCK_TYPES.get("stained_glass")), backMath("block/" + typeBlock.getRegistryName().getPath() + "_top"));
        }
        BLOCK_TYPES.clear();
        EXTRA_PROPERTIES.clear();
    }

    public void blockFromCustom(Block block) {
        this.stateProvider.simpleBlock(block, models().cubeAll(block.getRegistryName().getPath(), getBlockTexture(block)));
    }

    private void doubleSlab(Block typeBlock) {
        this.stateProvider.models().cubeBottomTop("double_" + typeBlock.getRegistryName().getPath(), (ResourceLocation) EXTRA_PROPERTIES.get("double_slab").get(0), this.texture, this.texture);
        this.stateProvider.slabBlock((SlabBlock) typeBlock, new ResourceLocation(typeBlock.getRegistryName().getNamespace(), "double_" + typeBlock.getRegistryName().getPath()), (ResourceLocation) EXTRA_PROPERTIES.get("double_slab").get(0), this.texture, this.texture);
    }

    public void fenceBlock(Block fence, ResourceLocation texture) {
        this.stateProvider.fenceBlock((FenceBlock) fence, texture);
        assert fence.getRegistryName() != null;
        models().withExistingParent(fence.getRegistryName().getPath() + "_inventory", backMath("block/fence_inventory")).texture("texture", texture);
    }

    public void wallBlock(Block wall, ResourceLocation texture) {
        this.stateProvider.wallBlock((WallBlock) wall, texture);
        assert wall.getRegistryName() != null;
        models().withExistingParent(wall.getRegistryName().getPath() + "_inventory", mcLoc("block/wall_inventory")).texture("wall", texture);
    }

    public void ladder(Block ladder, ResourceLocation texture) {
        this.stateProvider.getVariantBuilder(ladder).forAllStatesExcept(state -> ConfiguredModel.builder().modelFile(models().withExistingParent(ladder.getRegistryName().getPath(), backMath("block/template_ladder")).texture("ladder", texture)).rotationY(
                (int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite().toYRot()).build(), BlockStateProperties.WATERLOGGED);
    }

    public void pressurePlate(Block pressurePlate, ResourceLocation texture) {
        this.stateProvider.getVariantBuilder(pressurePlate).forAllStates(state -> {
            boolean isPowered = state.getValue(BlockStateProperties.POWERED);
            return ConfiguredModel.builder().modelFile(models().getBuilder(pressurePlate.getRegistryName().getPath() + (isPowered ? "_down" : "")).parent(models().getExistingFile(mcLoc("block/pressure_plate_" + (isPowered ? "down" : "up")))).texture("texture",
                    texture)).build();
        });
    }

    public void weightedPressurePlate(Block pressurePlate, ResourceLocation texture) {
        this.stateProvider.getVariantBuilder(pressurePlate).forAllStates(state -> {
            int weight = state.getValue(BlockStateProperties.POWER);
            return ConfiguredModel.builder().modelFile(models().getBuilder(pressurePlate.getRegistryName().getPath() + (weight > 0 ? "_down" : "")).parent(models().getExistingFile(mcLoc("block/pressure_plate_" + (weight > 0 ? "down" : "up")))).texture("texture",
                    texture)).build();
        });
    }

    public void button(AbstractButtonBlock block, ResourceLocation texture) {
        ModelFile button = button(block.getRegistryName().getPath(), texture);
        ModelFile buttonPressed = buttonPressed(block.getRegistryName().getPath() + "_pressed", texture);
        this.button(block, button, buttonPressed);
        models().withExistingParent(block.getRegistryName().getPath() + "_inventory", mcLoc("block/button_inventory")).texture("texture", texture);
    }

    public void button(AbstractButtonBlock block, ModelFile button, ModelFile buttonPressed) {
        this.stateProvider.getVariantBuilder(block).forAllStates((state) -> {
            Direction facing = state.getValue(AbstractButtonBlock.FACING);
            AttachFace face = state.getValue(AbstractButtonBlock.FACE);
            boolean powered = state.getValue(AbstractButtonBlock.POWERED);
            return ConfiguredModel.builder().modelFile(powered ? buttonPressed : button).rotationX(face == AttachFace.FLOOR ? 0 : (face == AttachFace.WALL ? 90 : 180)).rotationY((int) (face == AttachFace.CEILING ? facing : facing.getOpposite()).toYRot()).uvLock(face ==
                    AttachFace.WALL).build();
        });
    }

    public void grapeVinePost(GrapeVinePostBlock block, ResourceLocation texture) {
        ModelFile notGrown = grapeVinePost(block.getRegistryName().getPath() + "_age0", backMath("block/hanging_grape_vines_age0"), texture);
        ModelFile preGrown = grapeVinePost(block.getRegistryName().getPath() + "_age1", backMath("block/hanging_grape_vines_age1"), texture);
        ModelFile grown = grapeVinePost(block.getRegistryName().getPath() + "_age2", backMath("block/hanging_grape_vines_age2"), texture);
        this.grapeVinePost(block, notGrown, preGrown, grown);
    }

    public void grapeVinePost(GrapeVinePostBlock block, ModelFile notGrown, ModelFile preGrown, ModelFile grown) {
        this.stateProvider.getVariantBuilder(block).forAllStatesExcept((state) -> {
            Direction facing = state.getValue(GrapeVinePostBlock.FACING);
            int age = state.getValue(GrapeVinePostBlock.AGE);
            return ConfiguredModel.builder().modelFile(age == 0 || age == 1 ? notGrown : (age == 2 ? preGrown : grown)).rotationY((int) facing.toYRot()).build();
        }, BlockStateProperties.WATERLOGGED);
    }

    public ModelFile button(String name, ResourceLocation texture) {
        return models().singleTexture(name, mcLoc("block/button"), texture);
    }

    public ModelFile buttonPressed(String name, ResourceLocation texture) {
        return models().singleTexture(name, mcLoc("block/button_pressed"), texture);
    }

    public ModelFile grapeVinePost(String name, ResourceLocation texture, ResourceLocation postTexture) {
        return models().withExistingParent(name, backMath("block/template_grape_vine_post_v2")).texture("vines", texture).texture("post", postTexture);
    }

    public BlockModelProvider models() {
        return this.stateProvider.models();
    }

    private ResourceLocation mcLoc(String name) {
        return new ResourceLocation(name);
    }
}
