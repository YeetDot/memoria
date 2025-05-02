package com.yeetdot.memoria.block.component;

import com.yeetdot.memoria.Memoria;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {
    public static final ComponentType<Float> MEMORY_STRENGTH = register("memory_strength", builder -> builder.codec(Codecs.rangedInclusiveFloat(0f, Float.MAX_VALUE)).packetCodec(PacketCodecs.FLOAT));

    private static <T>ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(Memoria.MOD_ID, name), builderOperator.apply(ComponentType.builder()).build());
    }

    public static void registerDataComponentTypes() {}
}
