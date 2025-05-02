package com.yeetdot.memoria.item;

import com.yeetdot.memoria.Memoria;
import com.yeetdot.memoria.item.custom.MemoryShardItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModItems {
    public static final Item QUARTZ_BALL = register("quartz_ball", Item::new, new Item.Settings().maxCount(1));
    public static final Item MEMORY_SHARD = register("memory_shard", Item::new, new Item.Settings());
    public static final Item QUARTZ_RING = register("quartz_ring", Item::new, new Item.Settings().maxDamage(64));


    private static Item register(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Memoria.MOD_ID, name));
        Item item = factory.apply(settings.registryKey(key));
        return Registry.register(Registries.ITEM, key, item);
    }

    public static void registerItems() {}
}
