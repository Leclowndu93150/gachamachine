package com.hisroyalty.gachamachine.cobblemon;

import com.google.gson.JsonParser;
import com.hisroyalty.gachamachine.GachaMachine;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class GachaSpawnPoolReloadListenerNeoForge implements PreparableReloadListener {

    public static final List<GachaSpawnEntry> ENTRIES = new ArrayList<>();

    @Override
    public CompletableFuture<Void> reload(PreparationBarrier barrier, ResourceManager manager,
                                           ProfilerFiller prepProfiler, ProfilerFiller applyProfiler,
                                           Executor prepExecutor, Executor applyExecutor) {
        return CompletableFuture.supplyAsync(() -> {
            List<GachaSpawnEntry> loaded = new ArrayList<>();

            manager.listResources(
                    "spawn_pool_files",
                    id -> id.getNamespace().equals(GachaMachine.MOD_ID) && id.getPath().endsWith(".json")
            ).forEach((id, resource) -> {
                try (InputStreamReader reader = new InputStreamReader(resource.open())) {
                    GachaSpawnFile file =
                            GachaSpawnFile.CODEC.parse(JsonOps.INSTANCE, JsonParser.parseReader(reader))
                                    .getOrThrow();

                    loaded.addAll(file.spawns());
                } catch (Exception e) {
                    GachaMachine.LOGGER.error("Failed to load gacha spawn {}", id, e);
                }
            });

            return loaded;
        }, prepExecutor).thenCompose(barrier::wait).thenAcceptAsync(loaded -> {
            ENTRIES.clear();
            GachaSpawnRegistry.clear();

            ENTRIES.addAll(loaded);
            for (var entry : ENTRIES) {
                GachaSpawnRegistry.add(entry);
            }

            GachaMachine.LOGGER.info("Loaded {} gacha spawn entries", ENTRIES.size());
        }, applyExecutor);
    }
}
