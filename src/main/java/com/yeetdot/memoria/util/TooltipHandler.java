package com.yeetdot.memoria.util;

import com.yeetdot.memoria.component.ModDataComponentTypes;
import com.yeetdot.memoria.item.ModItems;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.text.Text;

public class TooltipHandler {
    public static void register() {
        ItemTooltipCallback.EVENT.register(((itemStack, tooltipContext, tooltipType, list) -> {
            if (itemStack.isOf(ModItems.MEMORY_SHARD) && itemStack.get(ModDataComponentTypes.MEMORY_STRENGTH) != null) {
                list.add(Text.translatable("tooltip.memoria.memory_shard ").append(String.format("%.2f", itemStack.get(ModDataComponentTypes.MEMORY_STRENGTH))));
            }
        }));
    }
}
