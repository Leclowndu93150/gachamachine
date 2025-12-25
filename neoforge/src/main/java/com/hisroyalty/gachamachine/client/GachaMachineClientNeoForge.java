package com.hisroyalty.gachamachine.client;

import com.hisroyalty.gachamachine.GachaMachine;
import com.hisroyalty.gachamachine.GachaMachineNeoForge;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = GachaMachine.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class GachaMachineClientNeoForge {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(GachaMachineNeoForge.GACHA_MACHINE.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(GachaMachineNeoForge.GACHA_MACHINE_2.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(GachaMachineNeoForge.GACHA_MACHINE_3.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(GachaMachineNeoForge.GACHA_MACHINE_5.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(GachaMachineNeoForge.GACHA_MACHINE_6.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(GachaMachineNeoForge.GACHA_MACHINE_7.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(GachaMachineNeoForge.GACHA_MACHINE_8.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(GachaMachineNeoForge.GACHA_MACHINE_9.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(GachaMachineNeoForge.GACHA_MACHINE_10.get(), RenderType.translucent());
        });
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(GachaMachineNeoForge.GACHA_MACHINE_ANIMATED_BE.get(), GachaMachineRenderer::new);
    }
}
