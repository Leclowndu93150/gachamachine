package com.hisroyalty.gachamachine.cobblemon;

import java.util.ArrayList;
import java.util.List;

public final class GachaSpawnRegistry {
    private static final List<GachaSpawnEntry> ENTRIES = new ArrayList<>();

    public static void clear() {
        ENTRIES.clear();
    }

    public static void add(GachaSpawnEntry entry) {
        ENTRIES.add(entry);
    }

    public static List<GachaSpawnEntry> getAll() {
        return ENTRIES;
    }

    public static boolean isEmpty() {
        return ENTRIES.isEmpty();
    }
}
