package com.hisroyalty.gachamachine;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;

public class GachaMachineRegistries {
    public static LootContextParamSet GACHA_MACHINE_LOOT_CONTEXT;
    public static LootContextParamSet CAPSULE_LOOT_CONTEXT;

    private static ItemIdLookup itemIdLookup;
    private static FakePlayerProvider fakePlayerProvider;

    @FunctionalInterface
    public interface ItemIdLookup {
        ResourceLocation getItemId(Item item);
    }

    @FunctionalInterface
    public interface FakePlayerProvider {
        ServerPlayer getFakePlayer(ServerLevel level);
    }

    public static void setItemIdLookup(ItemIdLookup lookup) {
        itemIdLookup = lookup;
    }

    public static ResourceLocation getItemId(Item item) {
        if (itemIdLookup != null) {
            return itemIdLookup.getItemId(item);
        }
        return null;
    }

    public static void setFakePlayerProvider(FakePlayerProvider provider) {
        fakePlayerProvider = provider;
    }

    public static ServerPlayer getFakePlayer(ServerLevel level) {
        if (fakePlayerProvider != null) {
            return fakePlayerProvider.getFakePlayer(level);
        }
        return null;
    }
}
