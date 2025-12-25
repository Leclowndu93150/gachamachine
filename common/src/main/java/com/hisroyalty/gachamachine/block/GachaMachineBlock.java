package com.hisroyalty.gachamachine.block;

import com.hisroyalty.gachamachine.GachaMachine;
import com.hisroyalty.gachamachine.GachaMachineRegistries;
import com.hisroyalty.gachamachine.cobblemon.CobblemonGacha;
import com.hisroyalty.gachamachine.config.DatapackConfigData;
import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class GachaMachineBlock extends BaseEntityBlock {
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    private final TagKey<Item> currencyTag;
    private final String loot;
    private final int configProperty;
    private final Supplier<BlockEntityType<GachaMachineBlockEntity>> blockEntityTypeSupplier;

    public GachaMachineBlock(Properties properties, TagKey<Item> currencyTag, String loot, int configProperty,
                              Supplier<BlockEntityType<GachaMachineBlockEntity>> blockEntityTypeSupplier) {
        super(properties);
        this.currencyTag = currencyTag;
        this.loot = loot;
        this.configProperty = configProperty;
        this.blockEntityTypeSupplier = blockEntityTypeSupplier;
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(HALF);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return defaultBlockState()
                .setValue(FACING, ctx.getHorizontalDirection().getOpposite())
                .setValue(HALF, DoubleBlockHalf.LOWER);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            return;
        }

        if (!state.is(newState.getBlock())) {
            pos = getLowerPos(state, pos);
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof GachaMachineBlockEntity) {
                level.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, level, pos, newState, movedByPiston);
        }
    }

    private static String machineKey(int index) {
        return "gacha_machine_" + index;
    }

    public static int deserializeCooldown(int index) {
        return DatapackConfigData.getCooldown(machineKey(index)) * 20;
    }

    public static int deserializeUsesBeforeCooldown(int index) {
        return DatapackConfigData.getUsesBeforeCooldown(machineKey(index));
    }

    public static int deserializeLevel(int index) {
        return DatapackConfigData.getMaxCurrency(machineKey(index));
    }

    public static boolean deserializeSpecial(int index) {
        return DatapackConfigData.isSpecial(machineKey(index));
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide) return null;
        if (state.getValue(HALF) != DoubleBlockHalf.LOWER) return null;

        return (w, pos, st, be) -> {
            if (be instanceof GachaMachineBlockEntity g) {
                GachaMachineBlockEntity.tick(w, pos, st, g);
            }
        };
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.above()).canBeReplaced();
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            return Collections.emptyList();
        }
        return super.getDrops(state, builder);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (!level.isClientSide()) {
            level.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER), 3);
        }
        super.setPlacedBy(level, pos, state, placer, stack);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (direction.getAxis() == Direction.Axis.Y) {
            boolean valid = true;
            DoubleBlockHalf half = state.getValue(HALF);

            if (half == DoubleBlockHalf.LOWER && direction == Direction.UP)
                if (!neighborState.is(this) || neighborState.getValue(HALF) != DoubleBlockHalf.UPPER)
                    valid = false;
            if (half == DoubleBlockHalf.UPPER && direction == Direction.DOWN)
                if (!neighborState.is(this) || neighborState.getValue(HALF) != DoubleBlockHalf.LOWER)
                    valid = false;

            if (!valid) return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    private boolean isValidItem(ItemStack stack, Level level) {
        if (level.isClientSide) {
            return false;
        }
        return stack.is(currencyTag);
    }

    public boolean processCurrencyInsertion(ServerLevel level, BlockPos pos, ItemStack currencyStack,
                                            @Nullable Player player, GachaMachineBlockEntity be) {
        BlockState state = level.getBlockState(pos);
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            pos = pos.below();
            state = level.getBlockState(pos);
        }

        if (be == null) return false;

        if (be.getCooldown() > 0) {
            if (player != null) {
                int secondsLeft = (be.getCooldown() + 19) / 20;
                Component message = Component.translatable("message.gacha_machine.cooldown",
                        Component.literal(String.valueOf(secondsLeft)).withStyle(ChatFormatting.RED)
                ).withStyle(ChatFormatting.RED);
                player.displayClientMessage(message, true);
            }
            return false;
        }

        if (!isValidItem(currencyStack, level)) {
            if (player != null) {
                StringBuilder itemNames = new StringBuilder();
                var currencyItems = level.registryAccess()
                        .registryOrThrow(Registries.ITEM)
                        .getTagOrEmpty(currencyTag);

                boolean hasItems = false;
                for (Holder<Item> holder : currencyItems) {
                    Item item = holder.value();
                    String itemName = item.getDescription().getString();
                    if (hasItems) itemNames.append(", ");
                    itemNames.append(itemName);
                    hasItems = true;
                }

                if (!hasItems) {
                    itemNames.append("No valid currencies");
                }

                Component message = Component.translatable("message.gacha_machine.invalid_currency",
                        Component.literal(itemNames.toString()).withStyle(ChatFormatting.RED)
                ).withStyle(ChatFormatting.RED);

                player.displayClientMessage(message, message.getString().length() <= 128);
            }
            return false;
        }

        int currentLevel = be.getGachaLevel();
        int maxLevel = deserializeLevel(configProperty);

        if (currentLevel < (maxLevel - 1)) {
            be.setGachaLevel(currentLevel + 1);

            if (player != null && !level.isClientSide()) {
                Component text = Component.literal("[")
                        .append(Component.literal(String.valueOf(currentLevel + 1)))
                        .append(Component.literal("/"))
                        .append(Component.literal(String.valueOf(maxLevel)))
                        .append(Component.literal("]"));
                player.displayClientMessage(text, true);
            }

            if (player == null) {
                ItemStack s = be.getItem(0);
                if (!s.isEmpty()) {
                    s.shrink(1);
                    be.setItem(0, s);
                }
            } else if (!player.isCreative()) {
                currencyStack.shrink(1);
            }

            level.playSound(null, pos, SoundEvents.CHAIN_STEP, SoundSource.BLOCKS, 1.0F, 1.0F);
            return true;
        } else {
            if (!level.isClientSide()) {
                boolean special = deserializeSpecial(configProperty);
                if (special) {
                    ServerPlayer sp = player instanceof ServerPlayer serverPlayer
                            ? serverPlayer
                            : GachaMachineRegistries.getFakePlayer(level);
                    if (sp != null) {
                        CobblemonGacha.spawnPokemon(level, pos, sp);
                    }
                }
                if (!special) {
                    List<ItemStack> capsules = getOutput(player, level, pos);
                    if (capsules.isEmpty() && player != null) {
                        player.displayClientMessage(
                                Component.translatable("message.gacha_machine.dud")
                                        .withStyle(ChatFormatting.RED),
                                true
                        );
                    }

                    if (player == null) {
                        ItemStack s = be.getItem(0);
                        if (!s.isEmpty()) {
                            s.shrink(1);
                            be.setItem(0, s);
                        }
                    } else if (!player.isCreative()) {
                        currencyStack.shrink(1);
                    }

                    Direction facing = state.hasProperty(FACING) ? state.getValue(FACING) : Direction.NORTH;
                    for (ItemStack capsule : capsules) {
                        boolean insertedBelow = false;
                        BlockPos belowPos = pos.below();
                        BlockEntity belowEntity = level.getBlockEntity(belowPos);

                        if (belowEntity instanceof WorldlyContainer sidedBelow) {
                            int[] slots = sidedBelow.getSlotsForFace(Direction.UP);
                            for (int slot : slots) {
                                if (!sidedBelow.canPlaceItemThroughFace(slot, capsule, Direction.UP)) continue;
                                ItemStack existing = sidedBelow.getItem(slot);
                                int max = Math.min(existing.getMaxStackSize(), sidedBelow.getMaxStackSize());
                                if (existing.isEmpty()) {
                                    sidedBelow.setItem(slot, capsule.copy());
                                    insertedBelow = true;
                                    break;
                                } else if (ItemStack.isSameItemSameComponents(existing, capsule) &&
                                        existing.getCount() + capsule.getCount() <= max) {
                                    existing.grow(capsule.getCount());
                                    sidedBelow.setItem(slot, existing);
                                    insertedBelow = true;
                                    break;
                                }
                            }
                            if (insertedBelow) continue;
                        } else if (belowEntity instanceof Container invBelow) {
                            for (int i = 0; i < invBelow.getContainerSize(); i++) {
                                ItemStack existing = invBelow.getItem(i);
                                if (existing.isEmpty()) {
                                    invBelow.setItem(i, capsule.copy());
                                    insertedBelow = true;
                                    break;
                                } else if (ItemStack.isSameItemSameComponents(existing, capsule) &&
                                        existing.getCount() + capsule.getCount() <= existing.getMaxStackSize()) {
                                    existing.grow(capsule.getCount());
                                    invBelow.setItem(i, existing);
                                    insertedBelow = true;
                                    break;
                                }
                            }
                            if (insertedBelow) continue;
                        }

                        if (player != null) {
                            if (DatapackConfigData.shouldPickupOnDispense()) {
                                ItemStack remainder = capsule.copy();
                                player.getInventory().add(remainder);

                                if (!remainder.isEmpty()) {
                                    Vec3 spawnPos = pos.relative(facing).getCenter();
                                    ItemEntity itemEntity = new ItemEntity(
                                            level,
                                            spawnPos.x,
                                            spawnPos.y,
                                            spawnPos.z,
                                            remainder
                                    );
                                    itemEntity.setDefaultPickUpDelay();
                                    level.addFreshEntity(itemEntity);
                                }
                            } else {
                                Vec3 spawnPos = pos.relative(facing).getCenter();
                                ItemEntity itemEntity = new ItemEntity(level, spawnPos.x, spawnPos.y, spawnPos.z, capsule.copy());
                                itemEntity.setDefaultPickUpDelay();
                                level.addFreshEntity(itemEntity);
                            }
                        } else {
                            ItemStack outExisting = be.getItem(1);
                            if (outExisting.isEmpty()) {
                                be.setItem(1, capsule.copy());
                            } else if (ItemStack.isSameItemSameComponents(outExisting, capsule)) {
                                outExisting.grow(capsule.getCount());
                                be.setItem(1, outExisting);
                            }

                            Vec3 spawnPos = pos.relative(facing).getCenter();
                            ItemEntity itemEntity = new ItemEntity(level, spawnPos.x, spawnPos.y, spawnPos.z, capsule.copy());
                            itemEntity.setPickUpDelay(0);
                            level.addFreshEntity(itemEntity);
                        }
                    }
                }

                be.setGachaLevel(0);

                int configuredUses = deserializeUsesBeforeCooldown(configProperty);
                if (be.getUsesBeforeCooldownRemaining() == -1) {
                    be.setUsesBeforeCooldownRemaining(configuredUses);
                }

                int remaining = be.getUsesBeforeCooldownRemaining();
                if (configuredUses <= 1) {
                    int cooldownTicks = deserializeCooldown(configProperty);
                    if (cooldownTicks > 0) {
                        be.setCooldown(cooldownTicks);
                    }
                    be.setUsesBeforeCooldownRemaining(configuredUses);
                } else {
                    if (remaining > 1) {
                        be.setUsesBeforeCooldownRemaining(remaining - 1);
                    } else {
                        int cooldownTicks = deserializeCooldown(configProperty);
                        if (cooldownTicks > 0) {
                            be.setCooldown(cooldownTicks);
                        }
                        be.setUsesBeforeCooldownRemaining(configuredUses);
                    }
                }

                level.playSound(null, pos, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            return true;
        }
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        pos = getLowerPos(state, pos);

        if (level.getBlockEntity(pos) instanceof GachaMachineBlockEntity g) {
            return g.getGachaLevel();
        }
        return 0;
    }

    public List<ItemStack> getOutput(@Nullable Player player, ServerLevel serverLevel, BlockPos origin) {
        Entity entity = player != null ? player : GachaMachineRegistries.getFakePlayer(serverLevel);

        LootParams.Builder builder = new LootParams.Builder(serverLevel)
                .withParameter(LootContextParams.ORIGIN, origin.getCenter())
                .withParameter(LootContextParams.THIS_ENTITY, entity);

        LootParams parameters = builder.create(GachaMachineRegistries.GACHA_MACHINE_LOOT_CONTEXT);

        ResourceLocation lootTableId = GachaMachine.id(loot);
        LootTable lootTable = serverLevel.getServer()
                .reloadableRegistries()
                .getLootTable(ResourceKey.create(Registries.LOOT_TABLE, lootTableId));

        if (lootTable == LootTable.EMPTY) {
            GachaMachine.LOGGER.error("Loot table not found: {}", lootTableId);
            return List.of();
        }

        List<ItemStack> generatedLoot = lootTable.getRandomItems(parameters);

        if (generatedLoot == null || generatedLoot.isEmpty()) {
            return List.of();
        }

        return generatedLoot;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER
                ? new GachaMachineBlockEntity(blockEntityTypeSupplier.get(), pos, state)
                : null;
    }

    public static BlockPos getLowerPos(BlockState state, BlockPos pos) {
        return state.getValue(HALF) == DoubleBlockHalf.UPPER ? pos.below() : pos;
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide && (player.isCreative() || !player.hasCorrectToolForDrops(state))) {
            onBreakInCreative(level, pos, state, player);
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    protected static void onBreakInCreative(Level level, BlockPos pos, BlockState state, Player player) {
        DoubleBlockHalf half = state.getValue(HALF);
        if (half == DoubleBlockHalf.UPPER) {
            BlockPos blockPos = pos.below();
            BlockState blockState = level.getBlockState(blockPos);
            if (blockState.is(state.getBlock()) && blockState.getValue(HALF) == DoubleBlockHalf.LOWER) {
                BlockState newState = blockState.getFluidState().is(Fluids.WATER)
                        ? Blocks.WATER.defaultBlockState()
                        : Blocks.AIR.defaultBlockState();
                level.setBlock(blockPos, newState, 35);
                level.levelEvent(player, 2001, blockPos, Block.getId(blockState));
            }
        }
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level,
                                               BlockPos pos, Player player,
                                               InteractionHand hand, BlockHitResult hitResult) {
        pos = getLowerPos(state, pos);

        if (!(level.getBlockEntity(pos) instanceof GachaMachineBlockEntity be)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
            boolean success = processCurrencyInsertion(serverLevel, pos, stack, player, be);
            return success ? ItemInteractionResult.SUCCESS : ItemInteractionResult.FAIL;
        }

        return ItemInteractionResult.SUCCESS;
    }
}
