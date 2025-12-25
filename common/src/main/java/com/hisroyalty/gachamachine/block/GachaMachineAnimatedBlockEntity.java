package com.hisroyalty.gachamachine.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class GachaMachineAnimatedBlockEntity
        extends GachaMachineBlockEntity
        implements GeoBlockEntity {

    private final AnimatableInstanceCache cache =
            GeckoLibUtil.createInstanceCache(this);

    public GachaMachineAnimatedBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(
                new AnimationController<>(
                        this,
                        "idle_controller",
                        0,
                        state -> {
                            state.setAnimation(
                                    RawAnimation.begin()
                                            .thenLoop("animation.strange_crystallized_machine.idle")
                            );
                            return PlayState.CONTINUE;
                        }
                )
        );
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
