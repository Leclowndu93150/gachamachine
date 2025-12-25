package com.hisroyalty.gachamachine.client;

import com.hisroyalty.gachamachine.block.GachaMachineAnimatedBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class GachaMachineRenderer
        extends GeoBlockRenderer<GachaMachineAnimatedBlockEntity> {

    public GachaMachineRenderer(BlockEntityRendererProvider.Context context) {
        super(new GachaMachineModel());
    }
}
