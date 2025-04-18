package melonystudios.backmath.item.custom.armor;

import melonystudios.backmath.BackMath;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class BMArmors {
    public static final DeferredRegister<ArmorMaterial> MATERIALS = DeferredRegister.create(BuiltInRegistries.ARMOR_MATERIAL, BackMath.MOD_ID);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> GOLDEN_PLATED = MATERIALS.register("golden_plated", () -> new ArmorMaterial(makeDefenseMap(2, 6, 5, 2), 15,
            SoundEvents.ARMOR_EQUIP_IRON, () -> Ingredient.EMPTY, defaultLayers("golden_plated"), 0, 0));

    private static List<ArmorMaterial.Layer> defaultLayers(String name) {
        return List.of(new ArmorMaterial.Layer(BackMath.backMath(name)));
    }

    private static Map<ArmorItem.Type, Integer> makeDefenseMap(int helmet, int chestplate, int leggings, int boots) {
        EnumMap<ArmorItem.Type, Integer> map = new EnumMap<>(ArmorItem.Type.class);
        map.put(ArmorItem.Type.HELMET, helmet);
        map.put(ArmorItem.Type.CHESTPLATE, chestplate);
        map.put(ArmorItem.Type.LEGGINGS, leggings);
        map.put(ArmorItem.Type.BOOTS, boots);
        return map;
    }
}
