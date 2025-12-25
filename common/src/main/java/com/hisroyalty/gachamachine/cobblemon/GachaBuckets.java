package com.hisroyalty.gachamachine.cobblemon;

import java.util.Map;
import java.util.Random;

public final class GachaBuckets {
    private static final Map<String, Integer> BUCKET_WEIGHTS = Map.of(
            "common", 100,
            "uncommon", 40,
            "rare", 15,
            "ultra_rare", 5,
            "legendary", 1
    );

    public static int getWeight(String bucket) {
        return BUCKET_WEIGHTS.getOrDefault(bucket.toLowerCase(), 10);
    }

    public static String roll(Random random) {
        int total = BUCKET_WEIGHTS.values().stream().mapToInt(i -> i).sum();
        int roll = random.nextInt(total);

        int current = 0;
        for (var entry : BUCKET_WEIGHTS.entrySet()) {
            current += entry.getValue();
            if (roll < current) {
                return entry.getKey();
            }
        }

        return "common";
    }
}
