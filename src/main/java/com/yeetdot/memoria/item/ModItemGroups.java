package com.yeetdot.memoria.item;

import com.yeetdot.memoria.Memoria;
import com.yeetdot.memoria.block.ModBlocks;
import com.yeetdot.memoria.util.FieldCollector;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.lang.reflect.Field;
import java.util.List;

public class ModItemGroups {
    public static final ItemGroup MEMORIA_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP, Identifier.of(Memoria.MOD_ID, "memoria_item_group"), FabricItemGroup.builder()
                    .displayName(Text.translatable("itemgroup.memoria.memoria_item_group"))
                    .icon(() -> new ItemStack(ModItems.MEMORY_SHARD))
                    .entries(((displayContext, entries) -> {
                        List<Field> fields = FieldCollector.getStaticFields(ModItems.class);
                        fields.addAll(FieldCollector.getStaticFields(ModBlocks.class));

                        for (Field field : fields) {
                            try {
                                if (field.get(null) instanceof ItemConvertible item) {
                                    entries.add(item);
                                }
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }))
            .build());

    public static void registerItemGroups() {}
}
