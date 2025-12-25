package com.hisroyalty.gachamachine.block;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class GachaMachineAnimatedBlock extends GachaMachineBlock {

    private final Supplier<BlockEntityType<GachaMachineAnimatedBlockEntity>> animatedBlockEntityTypeSupplier;

    public GachaMachineAnimatedBlock(Properties properties,
                                     TagKey<Item> currencyTag,
                                     String loot,
                                     int configProperty,
                                     Supplier<BlockEntityType<GachaMachineBlockEntity>> blockEntityTypeSupplier,
                                     Supplier<BlockEntityType<GachaMachineAnimatedBlockEntity>> animatedBlockEntityTypeSupplier) {
        super(properties, currencyTag, loot, configProperty, blockEntityTypeSupplier);
        this.animatedBlockEntityTypeSupplier = animatedBlockEntityTypeSupplier;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER
                ? new GachaMachineAnimatedBlockEntity(animatedBlockEntityTypeSupplier.get(), pos, state)
                : null;
    }
}
