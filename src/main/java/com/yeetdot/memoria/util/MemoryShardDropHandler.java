package com.yeetdot.memoria.util;

import com.yeetdot.memoria.component.ModDataComponentTypes;
import com.yeetdot.memoria.item.ModItems;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.entity.ItemEntity;

public class MemoryShardDropHandler {
    public static void register() {
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {
            if (!(entity.getWorld() instanceof ServerWorld serverWorld)) return;

            // Check if attacker is a player
            if (source.getAttacker() instanceof PlayerEntity player) {
                // Check if player has the required item somewhere in inventory
                boolean hasSpecialItem = false;
                for (ItemStack stack : player.getInventory().getMainStacks()) {
                    if (!stack.isEmpty() && stack.getItem() == ModItems.QUARTZ_RING) {
                        hasSpecialItem = true;
                        stack.damage(1, player);
                        break;
                    }
                }

                if (hasSpecialItem) {
                    ItemStack stack = new ItemStack(ModItems.MEMORY_SHARD);
                    float health = entity.getMaxHealth();
                    float speed = (float) entity.getAttributeValue(EntityAttributes.MOVEMENT_SPEED);
                    float attack = (float) entity.getAttributeValue(EntityAttributes.ATTACK_DAMAGE);

                    float baseStrength = (health + attack * 3 + speed * 10) / 10f;
                    float strength = (float) (baseStrength + serverWorld.getRandom().nextGaussian() * baseStrength/3);

                    stack.set(ModDataComponentTypes.MEMORY_STRENGTH, strength < 0 ? 0 : strength);

                    serverWorld.spawnEntity(new ItemEntity(
                            serverWorld,
                            entity.getX(), entity.getY(), entity.getZ(),
                            stack
                    ));
                }
            }
        });
    }
}

