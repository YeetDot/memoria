package com.yeetdot.memoria.block.entity.custom;

import com.yeetdot.memoria.block.entity.ModBlockEntities;
import com.yeetdot.memoria.recipe.MnemonicInfuserRecipe;
import com.yeetdot.memoria.recipe.MnemonicInfuserRecipeInput;
import com.yeetdot.memoria.recipe.ModRecipes;
import com.yeetdot.memoria.screen.custom.MnemonicInfuserScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class MnemonicInfuserBlockEntity extends BlockEntity implements Pedestal, ExtendedScreenHandlerFactory<BlockPos> {
    private int progress = 0;
    private int maxProgress = 100;
    private final List<ItemStack> nearbyPedestalContents = new ArrayList<>();
    private float rotation;
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(10, ItemStack.EMPTY);
    protected final PropertyDelegate propertyDelegate;

    public MnemonicInfuserBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MNEMONIC_INFUSER_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> MnemonicInfuserBlockEntity.this.progress;
                    case 1 -> MnemonicInfuserBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0: MnemonicInfuserBlockEntity.this.progress = value;
                    case 1: MnemonicInfuserBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        Inventories.writeNbt(nbt, inventory, registries);
        nbt.putInt("mnemonic_infuser.progress", progress);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        Inventories.readNbt(nbt, inventory, registries);
        progress = nbt.getInt("mnemonic_infuser.progress").get();
        super.readNbt(nbt, registries);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (hasRecipe(world)) {
            if (getCurrentRecipe(world).isPresent()) {
                this.maxProgress = getCurrentRecipe(world).get().value().duration();
            }
            increaseCraftingProgress();

            if (hasFinishedCrafting()) {
                craftItem(world);
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void craftItem(World world) {
        Optional<RecipeEntry<MnemonicInfuserRecipe>> recipe = getCurrentRecipe(world);
        if (recipe.isPresent()){
            ItemStack result = recipe.get().value().output();
            this.removeStack(0);
            this.setStack(0, new ItemStack(result.getItem()));
        }
    }

    private void resetProgress() {
        this.progress = 0;
        this.nearbyPedestalContents.clear();
    }

    private boolean hasFinishedCrafting() {
        return this.progress >= this.maxProgress;
    }

    private void increaseCraftingProgress() {
        this.progress++;
    }

    private boolean hasRecipe(World world) {
        Optional<RecipeEntry<MnemonicInfuserRecipe>> recipe = getCurrentRecipe(world);
        return recipe.isPresent();
    }

    public String test () {
        return this.getItems().getFirst().toString();
    }

    public List<ItemStack> getNearbyPedestalContents(World world) {
            BlockPos pos = this.getPos();
            List<Vec3i> poses = List.of(
                    new Vec3i(3, 0, 0),
                    new Vec3i(2, 0, 2),
                    new Vec3i(0, 0, 3),
                    new Vec3i(-2, 0, 2),
                    new Vec3i(-3, 0, 0),
                    new Vec3i(-2, 0, -2),
                    new Vec3i(0, 0, -3),
                    new Vec3i(2, 0, -2));

            for (int i = 0; i < 8; i++) {
                BlockEntity entity = world.getBlockEntity(pos.add(poses.get(i)));
                if (entity instanceof PedestalBlockEntity pedestal) {
                    nearbyPedestalContents.add(pedestal.getItems().getFirst());
                }
            }
//          nearbyPedestalContents.sort(Comparator.comparing((itemStack -> itemStack.getItem().toString())));


        return nearbyPedestalContents;
    }

    private Optional<RecipeEntry<MnemonicInfuserRecipe>> getCurrentRecipe(World world) {
        return this.getWorld().getServer().getRecipeManager().getFirstMatch(ModRecipes.MNEMONIC_INFUSER_TYPE, new MnemonicInfuserRecipeInput(inventory.getFirst(), getNearbyPedestalContents(world)), world);
    }

    @Override
    public float getRotation() {
        rotation += 0.5f;
        if (rotation >= 360) {
            rotation = 0;
        }
        return rotation;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        return createNbt(registries);
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return pos;
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Mnemonic Infuser");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new MnemonicInfuserScreenHandler(syncId, playerInventory, this.pos);
    }
}
