package com.hisroyalty.gachamachine.cobblemon;

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public record GachaSpawnEntry(
        String species,
        String bucket,
        int weight,
        int minLevel,
        int maxLevel,
        Optional<List<ResourceLocation>> biomes
) {
    public static final Codec<GachaSpawnEntry> CODEC =
            RecordCodecBuilder.create(i -> i.group(
                    Codec.STRING.fieldOf("species").forGetter(GachaSpawnEntry::species),
                    Codec.STRING.fieldOf("bucket").forGetter(GachaSpawnEntry::bucket),
                    Codec.INT.fieldOf("weight").forGetter(GachaSpawnEntry::weight),
                    Codec.INT.fieldOf("minLevel").forGetter(GachaSpawnEntry::minLevel),
                    Codec.INT.fieldOf("maxLevel").forGetter(GachaSpawnEntry::maxLevel),
                    ResourceLocation.CODEC.listOf().optionalFieldOf("biomes")
                            .forGetter(GachaSpawnEntry::biomes)
            ).apply(i, GachaSpawnEntry::new));

    public boolean matches(ServerLevel level, BlockPos pos) {
        if (biomes.isEmpty()) return true;
        return biomes.get().contains(
                level.getBiome(pos).unwrapKey().get().location()
        );
    }

    public Pokemon create(ServerPlayer player, Random random) {
        Species s = PokemonSpecies.INSTANCE.getByName(species);
        int level = minLevel + random.nextInt(maxLevel - minLevel + 1);
        return s.create(level);
    }

    public Species getSpecies() {
        return PokemonSpecies.INSTANCE.getByName(species);
    }
}
