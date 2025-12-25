package com.hisroyalty.gachamachine.cobblemon;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.CobblemonEntities;
import com.cobblemon.mod.common.api.spawning.CobblemonSpawnPools;
import com.cobblemon.mod.common.api.spawning.detail.PokemonSpawnDetail;
import com.cobblemon.mod.common.api.spawning.detail.SpawnDetail;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CobblemonGacha {

    public static void spawnPokemon(ServerLevel level, BlockPos pos, ServerPlayer player) {
        boolean machineHasCustomPool = !GachaSpawnRegistry.isEmpty();
        if (machineHasCustomPool) {
            useGachaSpawns(level, pos, player);
        } else {
            useCobblemonWorldSpawns(level, pos, player);
        }
    }

    public static void useCobblemonWorldSpawns(ServerLevel level, BlockPos pos, ServerPlayer player) {
        List<PokemonSpawnDetail> validSpawns = new ArrayList<>();

        for (SpawnDetail detail : CobblemonSpawnPools.WORLD_SPAWN_POOL.getDetails()) {
            if (detail instanceof PokemonSpawnDetail pokemonDetail) {
                validSpawns.add(pokemonDetail);
            }
        }

        if (validSpawns.isEmpty()) {
            player.sendSystemMessage(
                    Component.literal("No Pokémon spawn data found.")
                            .withStyle(ChatFormatting.RED)
            );
            return;
        }

        PokemonSpawnDetail chosen = selectWeighted(validSpawns, new Random());
        if (chosen == null) return;

        Pokemon pokemon = chosen.getPokemon().create(player);

        spawnPokemonEntity(level, pos, player, pokemon);
    }

    private static void useGachaSpawns(ServerLevel level, BlockPos pos, ServerPlayer player) {
        List<GachaSpawnEntry> entries = GachaSpawnRegistry.getAll();

        if (entries.isEmpty()) {
            player.sendSystemMessage(
                    Component.literal("No custom gacha spawns loaded.")
                            .withStyle(ChatFormatting.RED)
            );
            return;
        }
        Random rd = new Random();
        String bucket = GachaBuckets.roll(rd);

        List<GachaSpawnEntry> candidates = entries.stream()
                .filter(e -> e.bucket().equalsIgnoreCase(bucket))
                .filter(e -> e.matches(level, pos))
                .toList();

        if (candidates.isEmpty()) {
            player.sendSystemMessage(
                    Component.literal("No spawns available for bucket: " + bucket)
                            .withStyle(ChatFormatting.RED)
            );
            return;
        }

        GachaSpawnEntry chosen = selectWeightedInBucket(candidates, rd);
        if (chosen == null) return;

        Pokemon pokemon = chosen.create(player, rd);

        spawnPokemonEntity(level, pos, player, pokemon);
    }

    private static GachaSpawnEntry selectWeightedInBucket(
            List<GachaSpawnEntry> entries,
            Random random
    ) {
        int total = 0;
        for (GachaSpawnEntry e : entries) {
            total += e.weight();
        }

        int roll = random.nextInt(total);
        int current = 0;

        for (GachaSpawnEntry e : entries) {
            current += e.weight();
            if (roll < current) return e;
        }

        return entries.get(entries.size() - 1);
    }

    private static void spawnPokemonEntity(ServerLevel level, BlockPos pos, ServerPlayer player, Pokemon pokemon) {
        PokemonEntity entity = new PokemonEntity(level, pokemon, CobblemonEntities.POKEMON);
        entity.setPokemon(pokemon);

        BlockPos spawnPos = player.blockPosition();

        entity.absMoveTo(
                spawnPos.getX() + 0.5,
                spawnPos.getY() + 1,
                spawnPos.getZ() + 0.5,
                level.random.nextFloat() * 360F,
                0F
        );

        level.addFreshEntity(entity);

        player.sendSystemMessage(
                Component.literal("A wild ")
                        .append(Component.literal(pokemon.getSpecies().getName()).withStyle(ChatFormatting.GREEN))
                        .append(Component.literal(" appeared!"))
        );

        givePokemon(player, pokemon);
    }

    private static PokemonSpawnDetail selectWeighted(List<PokemonSpawnDetail> details, Random random) {
        float total = 0f;
        for (PokemonSpawnDetail d : details) {
            total += d.getWeight();
        }

        float roll = random.nextFloat() * total;
        float current = 0f;

        for (PokemonSpawnDetail d : details) {
            current += d.getWeight();
            if (roll <= current) return d;
        }

        return details.get(details.size() - 1);
    }

    private static void givePokemon(ServerPlayer player, Pokemon pokemon) {
        var storage = Cobblemon.INSTANCE.getStorage();

        if (!storage.getParty(player).add(pokemon)) {
            storage.getPC(player).add(pokemon);
            player.sendSystemMessage(
                    Component.literal("Party full! Pokémon sent to PC.")
                            .withStyle(ChatFormatting.YELLOW)
            );
        } else {
            player.sendSystemMessage(
                    Component.literal("Obtained Pokémon: ")
                            .append(Component.literal(pokemon.getSpecies().getName())
                                    .withStyle(ChatFormatting.GREEN))
            );
        }
    }
}
