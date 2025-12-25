package com.hisroyalty.gachamachine.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hisroyalty.gachamachine.GachaMachine;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class DatapackConfigData {
    private static final Codec<Map<String, Integer>> CONFIG_CODEC = Codec.unboundedMap(Codec.STRING, Codec.INT);
    private static final Codec<Map<String, Boolean>> SPECIALS_CODEC = Codec.unboundedMap(Codec.STRING, Codec.BOOL);

    private static final Map<String, Integer> gachaMachineCurrencyLimits = new HashMap<>();
    private static final Map<String, Integer> gachaMachineCooldowns = new HashMap<>();
    private static final Map<String, Integer> gachaMachineUsesBeforeCooldowns = new HashMap<>();
    private static final Map<String, Boolean> gachaMachineSpecials = new HashMap<>();

    private static final int DEFAULT_COOLDOWN = 0;
    private static final int DEFAULT_USES_BEFORE_COOLDOWN = 1;
    private static boolean pickupOnDispense = false;

    public static void reload(ResourceManager resourceManager) {
        gachaMachineCurrencyLimits.clear();
        gachaMachineCooldowns.clear();
        gachaMachineUsesBeforeCooldowns.clear();
        gachaMachineSpecials.clear();
        pickupOnDispense = false;

        Map<ResourceLocation, Resource> resources = resourceManager.listResources("config", path -> path.getPath().endsWith(".json"));
        for (Map.Entry<ResourceLocation, Resource> entry : resources.entrySet()) {
            ResourceLocation id = entry.getKey();
            Resource resource = entry.getValue();

            if (id.getPath().equals("config/server_config.json")) {
                try (InputStreamReader reader = new InputStreamReader(resource.open())) {
                    JsonElement json = JsonParser.parseReader(reader);

                    JsonElement forCodec = json;
                    if (json.isJsonObject()) {
                        JsonObject obj = json.getAsJsonObject();

                        if (obj.has("pickup") && obj.get("pickup").isJsonPrimitive()) {
                            try {
                                pickupOnDispense = obj.get("pickup").getAsBoolean();
                            } catch (Exception ignored) {
                                pickupOnDispense = false;
                            }
                        }

                        if (obj.has("cooldowns") && obj.get("cooldowns").isJsonObject()) {
                            JsonObject cooldownsJson = obj.getAsJsonObject("cooldowns");
                            DataResult<Map<String, Integer>> cooldownResult = CONFIG_CODEC.parse(JsonOps.INSTANCE, cooldownsJson);
                            cooldownResult.resultOrPartial(System.err::println).ifPresentOrElse(
                                    gachaMachineCooldowns::putAll,
                                    () -> GachaMachine.LOGGER.warn("Failed to load cooldowns for gacha machines: {}", id)
                            );
                        }

                        if (obj.has("usesBeforeCooldown") && obj.get("usesBeforeCooldown").isJsonObject()) {
                            JsonObject usesJson = obj.getAsJsonObject("usesBeforeCooldown");
                            DataResult<Map<String, Integer>> usesResult = CONFIG_CODEC.parse(JsonOps.INSTANCE, usesJson);
                            usesResult.resultOrPartial(System.err::println).ifPresentOrElse(
                                    gachaMachineUsesBeforeCooldowns::putAll,
                                    () -> GachaMachine.LOGGER.warn("Failed to load usesBeforeCooldown for gacha machines: {}", id)
                            );
                        }

                        if (obj.has("special")) {
                            if (obj.get("special").isJsonPrimitive()) {
                                GachaMachine.LOGGER.warn("'special' found as a boolean in server_config.json - use an object mapping machine IDs to booleans instead: {}", id);
                            } else if (obj.get("special").isJsonObject()) {
                                JsonObject specialsJson = obj.getAsJsonObject("special");
                                DataResult<Map<String, Boolean>> specialsResult = SPECIALS_CODEC.parse(JsonOps.INSTANCE, specialsJson);
                                specialsResult.resultOrPartial(System.err::println).ifPresentOrElse(
                                        gachaMachineSpecials::putAll,
                                        () -> GachaMachine.LOGGER.warn("Failed to load specials for gacha machines: {}", id)
                                );
                            }
                        }

                        JsonObject limitsObj = obj.deepCopy();
                        limitsObj.remove("pickup");
                        limitsObj.remove("cooldowns");
                        limitsObj.remove("usesBeforeCooldown");
                        limitsObj.remove("special");
                        forCodec = limitsObj;
                    }

                    DataResult<Map<String, Integer>> result = CONFIG_CODEC.parse(JsonOps.INSTANCE, forCodec);

                    result.resultOrPartial(System.err::println).ifPresentOrElse(
                            gachaMachineCurrencyLimits::putAll,
                            () -> GachaMachine.LOGGER.warn("Failed to load config for gacha machine: {}", id)
                    );

                    GachaMachine.LOGGER.info("Loaded config for gacha machine: {} (pickup={})", id, pickupOnDispense);
                } catch (Exception e) {
                    GachaMachine.LOGGER.error("Failed to load config for gacha machine: {}", id, e);
                }
            }
        }
    }

    public static int getMaxCurrency(String machineId) {
        return gachaMachineCurrencyLimits.getOrDefault(machineId, 5);
    }

    public static int getCooldown(String machineId) {
        return gachaMachineCooldowns.getOrDefault(machineId, DEFAULT_COOLDOWN);
    }

    public static int getUsesBeforeCooldown(String machineId) {
        return gachaMachineUsesBeforeCooldowns.getOrDefault(machineId, DEFAULT_USES_BEFORE_COOLDOWN);
    }

    public static boolean shouldPickupOnDispense() {
        return pickupOnDispense;
    }

    public static boolean isSpecial(String machineId) {
        return gachaMachineSpecials.getOrDefault(machineId, false);
    }
}
