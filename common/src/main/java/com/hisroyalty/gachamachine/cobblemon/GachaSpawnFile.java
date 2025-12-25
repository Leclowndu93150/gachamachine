package com.hisroyalty.gachamachine.cobblemon;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record GachaSpawnFile(List<GachaSpawnEntry> spawns) {

    public static final Codec<GachaSpawnFile> CODEC =
            RecordCodecBuilder.create(i -> i.group(
                    GachaSpawnEntry.CODEC.listOf()
                            .fieldOf("spawns")
                            .forGetter(GachaSpawnFile::spawns)
            ).apply(i, GachaSpawnFile::new));
}
