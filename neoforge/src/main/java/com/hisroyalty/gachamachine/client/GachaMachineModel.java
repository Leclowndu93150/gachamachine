package com.hisroyalty.gachamachine.client;

import com.hisroyalty.gachamachine.GachaMachine;
import com.hisroyalty.gachamachine.block.GachaMachineAnimatedBlockEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;

public class GachaMachineModel
        extends GeoModel<GachaMachineAnimatedBlockEntity> {
    private final ResourceLocation model = GachaMachine.id("geo/gacha_machine.geo.json");
    private final ResourceLocation animations = GachaMachine.id("animations/time_machine.animation.json");
    private final ResourceLocation texture = GachaMachine.id("textures/block/strange_crystallized_machine.png");

    @Override
    public ResourceLocation getModelResource(GachaMachineAnimatedBlockEntity animatable) {
        return model;
    }

    @Override
    public ResourceLocation getTextureResource(GachaMachineAnimatedBlockEntity animatable) {
        return texture;
    }

    @Override
    public ResourceLocation getAnimationResource(GachaMachineAnimatedBlockEntity animatable) {
        return animations;
    }

    @Override
    public @Nullable RenderType getRenderType(GachaMachineAnimatedBlockEntity animatable, ResourceLocation texture) {
        return RenderType.entityCutout(texture);
    }
}
