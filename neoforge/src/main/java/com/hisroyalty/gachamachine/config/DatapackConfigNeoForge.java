package com.hisroyalty.gachamachine.config;

import com.hisroyalty.gachamachine.GachaMachine;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;

@EventBusSubscriber(modid = GachaMachine.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class DatapackConfigNeoForge {

    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener((prepBarrier, resourceManager, profilerPre, profilerPost, execPre, execPost) ->
                prepBarrier.wait(null).thenRunAsync(() -> {
                    DatapackConfigData.reload(resourceManager);
                }, execPost)
        );
    }
}
