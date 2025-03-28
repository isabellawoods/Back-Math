package com.sophicreeper.backmath.loot.function;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.sophicreeper.backmath.loot.BMLootFunctions;
import com.sophicreeper.backmath.util.RVUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class SetCrateContents extends LootFunction {
    private final List<LootEntry> entries;

    public SetCrateContents(ILootCondition[] conditions, List<LootEntry> lootEntries) {
        super(conditions);
        this.entries = ImmutableList.copyOf(lootEntries);
    }

    @Override
    @Nonnull
    public LootFunctionType getType() {
        return BMLootFunctions.SET_CRATE_CONTENTS;
    }

    @Override
    @Nonnull
    protected ItemStack run(ItemStack stack, LootContext context) {
        if (!stack.isEmpty()) {
            NonNullList<ItemStack> stackList = NonNullList.create();
            this.entries.forEach(entry -> entry.expand(context, generator -> generator.createItemStack(LootTable.createStackSplitter(stackList::add), context)));
            CompoundNBT contentsTag = new CompoundNBT();
            RVUtils.saveAllItems(contentsTag, stackList);
            CompoundNBT tag = stack.getOrCreateTag();
            tag.put("BlockEntityTag", contentsTag.merge(tag.getCompound("BlockEntityTag")));
        }
        return stack;
    }

    @Override
    public void validate(ValidationTracker tracker) {
        super.validate(tracker);

        for (int i = 0; i < this.entries.size(); ++i) {
            this.entries.get(i).validate(tracker.forChild(".entry[" + i + "]"));
        }
    }

    public static class Serializer extends LootFunction.Serializer<SetCrateContents> {
        @Override
        public void serialize(JsonObject object, SetCrateContents function, JsonSerializationContext context) {
            super.serialize(object, function, context);
            object.add("entries", context.serialize(function.entries));
        }

        @Override
        @Nonnull
        public SetCrateContents deserialize(JsonObject object, JsonDeserializationContext context, ILootCondition[] conditions) {
            LootEntry[] lootEntries = JSONUtils.getAsObject(object, "entries", context, LootEntry[].class);
            return new SetCrateContents(conditions, Arrays.asList(lootEntries));
        }
    }
}
