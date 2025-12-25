package com.hisroyalty.gachamachine.client;

import com.hisroyalty.gachamachine.GachaMachineFabric;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

public class GachaMachineClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(GachaMachineFabric.GACHA_MACHINE, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(GachaMachineFabric.GACHA_MACHINE_2, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(GachaMachineFabric.GACHA_MACHINE_3, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(GachaMachineFabric.GACHA_MACHINE_5, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(GachaMachineFabric.GACHA_MACHINE_6, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(GachaMachineFabric.GACHA_MACHINE_7, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(GachaMachineFabric.GACHA_MACHINE_8, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(GachaMachineFabric.GACHA_MACHINE_9, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(GachaMachineFabric.GACHA_MACHINE_10, RenderType.translucent());

        BlockEntityRenderers.register(GachaMachineFabric.GACHA_MACHINE_ANIMATED_BE, GachaMachineRenderer::new);
    }
}
