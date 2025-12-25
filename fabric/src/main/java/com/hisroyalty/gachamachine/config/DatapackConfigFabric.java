package com.hisroyalty.gachamachine.config;

import com.hisroyalty.gachamachine.GachaMachine;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

public class DatapackConfigFabric implements SimpleSynchronousResourceReloadListener {
    @Override
    public ResourceLocation getFabricId() {
        return GachaMachine.id("datapack_config");
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        DatapackConfigData.reload(resourceManager);
    }
}
