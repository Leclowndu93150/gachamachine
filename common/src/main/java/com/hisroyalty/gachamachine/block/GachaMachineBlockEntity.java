package com.hisroyalty.gachamachine.block;

import com.hisroyalty.gachamachine.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class GachaMachineBlockEntity extends BlockEntity implements WorldlyContainer {
    private int gachaLevel = 0;
    private int cooldownTicks = 0;
    private int usesBeforeCooldownRemaining = -1;
    private final NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);

    public GachaMachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public int getGachaLevel() {
        return gachaLevel;
    }

    public void setGachaLevel(int level) {
        this.gachaLevel = level;
        setChanged();
        if (this.level != null) {
            this.level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

    public int getCooldown() {
        return cooldownTicks;
    }

    public void setCooldown(int ticks) {
        this.cooldownTicks = Math.max(0, ticks);
        setChanged();
        if (this.level != null) {
            this.level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

    public int getUsesBeforeCooldownRemaining() {
        return usesBeforeCooldownRemaining;
    }

    public void setUsesBeforeCooldownRemaining(int remaining) {
        this.usesBeforeCooldownRemaining = remaining;
        setChanged();
        if (this.level != null) {
            this.level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("level", gachaLevel);
        tag.putInt("cooldown", cooldownTicks);
        tag.putInt("usesBeforeCooldownRemaining", usesBeforeCooldownRemaining);
        ContainerHelper.saveAllItems(tag, items, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        gachaLevel = tag.getInt("level");
        cooldownTicks = tag.getInt("cooldown");
        if (tag.contains("usesBeforeCooldownRemaining")) {
            usesBeforeCooldownRemaining = tag.getInt("usesBeforeCooldownRemaining");
        } else {
            usesBeforeCooldownRemaining = -1;
        }
        ContainerHelper.loadAllItems(tag, items, registries);
    }

    private static final int[] INPUT_SLOTS = new int[]{0};
    private static final int[] OUTPUT_SLOTS = new int[]{1};

    @Override
    public int[] getSlotsForFace(Direction side) {
        if (side == Direction.DOWN) {
            return OUTPUT_SLOTS;
        }
        return INPUT_SLOTS;
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction dir) {
        if (slot == 0) {
            return stack.is(ModTags.Items.CURRENCY_ITEMS) && getCooldown() == 0;
        }
        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction dir) {
        return slot == 1;
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : items) {
            if (!stack.isEmpty()) return false;
        }
        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return items.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack result = ContainerHelper.removeItem(items, slot, amount);
        setChanged();
        return result;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        ItemStack result = ContainerHelper.takeItem(items, slot);
        setChanged();
        return result;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        items.set(slot, stack);
        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }
        setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        items.clear();
    }

    @SuppressWarnings("unused")
    public static void tick(Level level, BlockPos pos, BlockState state, GachaMachineBlockEntity be) {
        if (level.isClientSide || !(level instanceof ServerLevel serverLevel)) return;

        // decrement cooldown if set
        if (be.getCooldown() > 0) {
            be.setCooldown(be.getCooldown() - 1);
        }

        ItemStack currencyStack = be.getItem(0);
        if (currencyStack.isEmpty() || !currencyStack.is(ModTags.Items.CURRENCY_ITEMS)) return;

        if (level.getBlockState(pos).getBlock() instanceof GachaMachineBlock block) {
            block.processCurrencyInsertion(serverLevel, pos, currencyStack, null, be);
        }
    }
}
