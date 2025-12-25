package com.hisroyalty.gachamachine.cobblemon;

import com.google.gson.JsonParser;
import com.hisroyalty.gachamachine.GachaMachine;
import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GachaSpawnPoolReloadListenerFabric
        implements SimpleSynchronousResourceReloadListener {

    public static final List<GachaSpawnEntry> ENTRIES = new ArrayList<>();

    @Override
    public ResourceLocation getFabricId() {
        return GachaMachine.id("gacha_spawn_pools");
    }

    @Override
    public void onResourceManagerReload(ResourceManager manager) {
        ENTRIES.clear();
        GachaSpawnRegistry.clear();

        manager.listResources(
                "spawn_pool_files",
                id -> id.getNamespace().equals(GachaMachine.MOD_ID) && id.getPath().endsWith(".json")
        ).forEach((id, resource) -> {
            try (InputStreamReader reader = new InputStreamReader(resource.open())) {
                GachaSpawnFile file =
                        GachaSpawnFile.CODEC.parse(JsonOps.INSTANCE, JsonParser.parseReader(reader))
                                .getOrThrow();

                ENTRIES.addAll(file.spawns());
            } catch (Exception e) {
                GachaMachine.LOGGER.error("Failed to load gacha spawn {}", id, e);
            }
        });

        for (var entry : ENTRIES) {
            GachaSpawnRegistry.add(entry);
        }

        GachaMachine.LOGGER.info("Loaded {} gacha spawn entries", ENTRIES.size());
    }
}
