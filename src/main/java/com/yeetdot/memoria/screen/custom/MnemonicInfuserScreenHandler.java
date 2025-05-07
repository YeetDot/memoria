package com.yeetdot.memoria.screen.custom;

import com.yeetdot.memoria.screen.ModScreenHandlers;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class MnemonicInfuserScreenHandler extends ScreenHandler {
    private final Inventory inventory;

    public MnemonicInfuserScreenHandler(int syncId, PlayerInventory inventory, BlockPos pos) {
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(pos));
    }

    public MnemonicInfuserScreenHandler(int syncId, PlayerInventory inventory, BlockEntity blockEntity) {
        super(ModScreenHandlers.MNEMONIC_INFUSER_SCREEN_HANDLER, syncId);

        this.inventory = (Inventory) blockEntity;

        List<Integer> x = List.of(0, 30, 20, 0, -20, -30, -20, 0, 20);
        List<Integer> y = List.of(0, 0, -20, -30, -20, 0, 20, 30, 20);

        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(this.inventory, i, 38 + x.get(i), 35 + y.get(i)) {
                @Override
                public int getMaxItemCount() {
                    return 1;
                }
            });
        }

        this.addSlot(new Slot(this.inventory, 9, 120, 35));

        addPlayerHotbar(inventory);
        addPlayerInventory(inventory);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 118 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 176));
        }
    }


    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = this.slots.get(slot);
        if (slot2 != null && slot2.hasStack()) {
            ItemStack itemStack2 = slot2.getStack();
            itemStack = itemStack2.copy();
            if (slot < 9) {
                if (!this.insertItem(itemStack2, 9, 45, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemStack2, 0, 9, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            } else {
                slot2.markDirty();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot2.onTakeItem(player, itemStack2);
        }

        return itemStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }
}
