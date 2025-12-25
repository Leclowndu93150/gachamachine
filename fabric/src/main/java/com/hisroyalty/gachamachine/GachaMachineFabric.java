package com.hisroyalty.gachamachine;

import com.hisroyalty.gachamachine.block.GachaMachineAnimatedBlock;
import com.hisroyalty.gachamachine.block.GachaMachineAnimatedBlockEntity;
import com.hisroyalty.gachamachine.block.GachaMachineBlock;
import com.hisroyalty.gachamachine.block.GachaMachineBlockEntity;
import com.hisroyalty.gachamachine.cobblemon.GachaSpawnPoolReloadListenerFabric;
import com.hisroyalty.gachamachine.config.DatapackConfigFabric;
import com.hisroyalty.gachamachine.loot.CommandLootEntry;
import com.hisroyalty.gachamachine.loot.ModLootTypes;
import com.hisroyalty.gachamachine.mixin.LootContextTypesAccessor;
import com.mojang.authlib.GameProfile;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.UUID;
import java.util.function.Consumer;

public class GachaMachineFabric implements ModInitializer {

    public static BlockEntityType<GachaMachineBlockEntity> GACHA_MACHINE_BLOCK_ENTITY;
    public static BlockEntityType<GachaMachineAnimatedBlockEntity> GACHA_MACHINE_ANIMATED_BE;

    private static BlockEntityType<GachaMachineBlockEntity> getBlockEntityType() {
        return GACHA_MACHINE_BLOCK_ENTITY;
    }

    private static BlockEntityType<GachaMachineAnimatedBlockEntity> getAnimatedBlockEntityType() {
        return GACHA_MACHINE_ANIMATED_BE;
    }

    public static final Block GACHA_MACHINE = registerBlock("gacha_machine",
            new GachaMachineBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).noOcclusion(),
                    ModTags.Items.CURRENCY_ITEMS, "gacha_machine", 1, GachaMachineFabric::getBlockEntityType));
    public static final Block GACHA_MACHINE_2 = registerBlock("gacha_machine_2",
            new GachaMachineBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).noOcclusion(),
                    ModTags.Items.CURRENCY_ITEMS_2, "gacha_machine_2", 2, GachaMachineFabric::getBlockEntityType));
    public static final Block GACHA_MACHINE_3 = registerBlock("gacha_machine_3",
            new GachaMachineBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).noOcclusion(),
                    ModTags.Items.CURRENCY_ITEMS_3, "gacha_machine_3", 3, GachaMachineFabric::getBlockEntityType));
    public static final Block GACHA_MACHINE_4 = registerBlock("gacha_machine_4",
            new GachaMachineAnimatedBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).noOcclusion(),
                    ModTags.Items.CURRENCY_ITEMS_4, "gacha_machine_4", 4, GachaMachineFabric::getBlockEntityType,
                    GachaMachineFabric::getAnimatedBlockEntityType));
    public static final Block GACHA_MACHINE_5 = registerBlock("gacha_machine_5",
            new GachaMachineBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).noOcclusion(),
                    ModTags.Items.CURRENCY_ITEMS_5, "gacha_machine_5", 5, GachaMachineFabric::getBlockEntityType));
    public static final Block GACHA_MACHINE_6 = registerBlock("gacha_machine_6",
            new GachaMachineBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).noOcclusion(),
                    ModTags.Items.CURRENCY_ITEMS_6, "gacha_machine_6", 6, GachaMachineFabric::getBlockEntityType));
    public static final Block GACHA_MACHINE_7 = registerBlock("gacha_machine_7",
            new GachaMachineBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).noOcclusion(),
                    ModTags.Items.CURRENCY_ITEMS_7, "gacha_machine_7", 7, GachaMachineFabric::getBlockEntityType));
    public static final Block GACHA_MACHINE_8 = registerBlock("gacha_machine_8",
            new GachaMachineBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).noOcclusion(),
                    ModTags.Items.CURRENCY_ITEMS_8, "gacha_machine_8", 8, GachaMachineFabric::getBlockEntityType));
    public static final Block GACHA_MACHINE_9 = registerBlock("gacha_machine_9",
            new GachaMachineBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).noOcclusion(),
                    ModTags.Items.CURRENCY_ITEMS_9, "gacha_machine_9", 9, GachaMachineFabric::getBlockEntityType));
    public static final Block GACHA_MACHINE_10 = registerBlock("gacha_machine_10",
            new GachaMachineBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).noOcclusion(),
                    ModTags.Items.CURRENCY_ITEMS_10, "gacha_machine_10", 10, GachaMachineFabric::getBlockEntityType));

    static {
        GACHA_MACHINE_BLOCK_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, GachaMachine.id("gacha"),
                BlockEntityType.Builder.of(
                        (pos, state) -> new GachaMachineBlockEntity(GACHA_MACHINE_BLOCK_ENTITY, pos, state),
                        GACHA_MACHINE, GACHA_MACHINE_2, GACHA_MACHINE_3, GACHA_MACHINE_5,
                        GACHA_MACHINE_6, GACHA_MACHINE_7, GACHA_MACHINE_8, GACHA_MACHINE_9, GACHA_MACHINE_10
                ).build(null));
        GACHA_MACHINE_ANIMATED_BE = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, GachaMachine.id("gacha_animated"),
                BlockEntityType.Builder.of(
                        (pos, state) -> new GachaMachineAnimatedBlockEntity(GACHA_MACHINE_ANIMATED_BE, pos, state),
                        GACHA_MACHINE_4
                ).build(null));
    }

    public static final ResourceKey<CreativeModeTab> GACHA_ITEM_GROUP_KEY =
            ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), GachaMachine.id("item_group"));

    public static final CreativeModeTab GACHA_ITEM_GROUP = Registry.register(
            BuiltInRegistries.CREATIVE_MODE_TAB,
            GACHA_ITEM_GROUP_KEY,
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(GachaItemRegistryFabric.GACHA_MACHINE))
                    .title(Component.translatable("itemGroup.gacha_machines"))
                    .displayItems((params, output) -> {
                        output.accept(GachaItemRegistryFabric.GACHA_MACHINE);
                        output.accept(GachaItemRegistryFabric.GACHA_MACHINE_2);
                        output.accept(GachaItemRegistryFabric.GACHA_MACHINE_3);
                        output.accept(GachaItemRegistryFabric.GACHA_MACHINE_4);
                        output.accept(GachaItemRegistryFabric.GACHA_MACHINE_5);
                        output.accept(GachaItemRegistryFabric.GACHA_MACHINE_6);
                        output.accept(GachaItemRegistryFabric.GACHA_MACHINE_7);
                        output.accept(GachaItemRegistryFabric.GACHA_MACHINE_8);
                        output.accept(GachaItemRegistryFabric.GACHA_MACHINE_9);
                        output.accept(GachaItemRegistryFabric.GACHA_MACHINE_10);

                        output.accept(GachaItemRegistryFabric.GACHA_COIN);
                        output.accept(GachaItemRegistryFabric.GACHA_COIN_2);
                        output.accept(GachaItemRegistryFabric.GACHA_COIN_3);
                        output.accept(GachaItemRegistryFabric.GACHA_COIN_4);
                        output.accept(GachaItemRegistryFabric.GACHA_COIN_5);
                        output.accept(GachaItemRegistryFabric.GACHA_COIN_6);
                        output.accept(GachaItemRegistryFabric.GACHA_COIN_7);
                        output.accept(GachaItemRegistryFabric.GACHA_COIN_8);
                        output.accept(GachaItemRegistryFabric.GACHA_COIN_9);
                        output.accept(GachaItemRegistryFabric.GACHA_COIN_10);

                        GachaItemRegistryFabric.ALL_CAPSULES.forEach(output::accept);
                    })
                    .build()
    );

    public static final LootContextParamSet GACHA_MACHINE_LOOT_CONTEXT = registerLootContext(
            GachaMachine.id("gacha_machine"),
            b -> b.required(LootContextParams.ORIGIN).required(LootContextParams.THIS_ENTITY)
    );
    public static final LootContextParamSet CAPSULE_LOOT_CONTEXT = registerLootContext(
            GachaMachine.id("capsule"),
            b -> b.required(LootContextParams.ORIGIN).optional(LootContextParams.THIS_ENTITY)
    );

    private static Block registerBlock(String name, Block block) {
        return Registry.register(BuiltInRegistries.BLOCK, GachaMachine.id(name), block);
    }

    private static LootContextParamSet registerLootContext(net.minecraft.resources.ResourceLocation id,
                                                            Consumer<LootContextParamSet.Builder> type) {
        LootContextParamSet.Builder builder = new LootContextParamSet.Builder();
        type.accept(builder);
        LootContextParamSet lootContextType = builder.build();
        LootContextParamSet check = LootContextTypesAccessor.getMAP().put(id, lootContextType);
        if (check != null) {
            throw new IllegalStateException("Loot table parameter set " + id + " is already registered");
        }
        return lootContextType;
    }

    @Override
    public void onInitialize() {
        GachaItemRegistryFabric.init();

        LootPoolEntryType commandEntry = new LootPoolEntryType(CommandLootEntry.CODEC);
        Registry.register(BuiltInRegistries.LOOT_POOL_ENTRY_TYPE,
                GachaMachine.id("command_entry"), commandEntry);
        ModLootTypes.setCommandEntry(commandEntry);

        GachaMachineRegistries.GACHA_MACHINE_LOOT_CONTEXT = GACHA_MACHINE_LOOT_CONTEXT;
        GachaMachineRegistries.CAPSULE_LOOT_CONTEXT = CAPSULE_LOOT_CONTEXT;
        GachaMachineRegistries.setItemIdLookup(item -> BuiltInRegistries.ITEM.getKey(item));
        GachaMachineRegistries.setFakePlayerProvider(level -> {
            GameProfile profile = new GameProfile(UUID.nameUUIDFromBytes("gachamachine".getBytes()), "gachamachine");
            return FakePlayer.get(level, profile);
        });

        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new DatapackConfigFabric());
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new GachaSpawnPoolReloadListenerFabric());

        GachaMachine.LOGGER.info("Gacha Machine Fabric initialized!");
    }
}
