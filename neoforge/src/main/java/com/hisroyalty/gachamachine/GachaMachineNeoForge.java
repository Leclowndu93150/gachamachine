package com.hisroyalty.gachamachine;

import com.hisroyalty.gachamachine.block.GachaMachineAnimatedBlock;
import com.hisroyalty.gachamachine.block.GachaMachineAnimatedBlockEntity;
import com.hisroyalty.gachamachine.block.GachaMachineBlock;
import com.hisroyalty.gachamachine.block.GachaMachineBlockEntity;
import com.hisroyalty.gachamachine.cobblemon.GachaSpawnPoolReloadListenerNeoForge;
import com.hisroyalty.gachamachine.config.DatapackConfigNeoForge;
import com.hisroyalty.gachamachine.loot.CommandLootEntry;
import com.hisroyalty.gachamachine.loot.ModLootTypes;
import com.hisroyalty.gachamachine.mixin.LootContextTypesAccessor;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.items.wrapper.SidedInvWrapper;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Mod(GachaMachine.MOD_ID)
public class GachaMachineNeoForge {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(Registries.BLOCK, GachaMachine.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, GachaMachine.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GachaMachine.MOD_ID);
    public static final DeferredRegister<LootPoolEntryType> LOOT_ENTRY_TYPES =
            DeferredRegister.create(Registries.LOOT_POOL_ENTRY_TYPE, GachaMachine.MOD_ID);

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<GachaMachineBlockEntity>> GACHA_MACHINE_BLOCK_ENTITY;
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<GachaMachineAnimatedBlockEntity>> GACHA_MACHINE_ANIMATED_BE;

    private static BlockEntityType<GachaMachineBlockEntity> getBlockEntityType() {
        return GACHA_MACHINE_BLOCK_ENTITY.get();
    }

    private static BlockEntityType<GachaMachineAnimatedBlockEntity> getAnimatedBlockEntityType() {
        return GACHA_MACHINE_ANIMATED_BE.get();
    }

    public static final DeferredHolder<Block, GachaMachineBlock> GACHA_MACHINE = BLOCKS.register("gacha_machine",
            () -> new GachaMachineBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).noOcclusion(),
                    ModTags.Items.CURRENCY_ITEMS, "gacha_machine", 1, GachaMachineNeoForge::getBlockEntityType));
    public static final DeferredHolder<Block, GachaMachineBlock> GACHA_MACHINE_2 = BLOCKS.register("gacha_machine_2",
            () -> new GachaMachineBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).noOcclusion(),
                    ModTags.Items.CURRENCY_ITEMS_2, "gacha_machine_2", 2, GachaMachineNeoForge::getBlockEntityType));
    public static final DeferredHolder<Block, GachaMachineBlock> GACHA_MACHINE_3 = BLOCKS.register("gacha_machine_3",
            () -> new GachaMachineBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).noOcclusion(),
                    ModTags.Items.CURRENCY_ITEMS_3, "gacha_machine_3", 3, GachaMachineNeoForge::getBlockEntityType));
    public static final DeferredHolder<Block, GachaMachineAnimatedBlock> GACHA_MACHINE_4 = BLOCKS.register("gacha_machine_4",
            () -> new GachaMachineAnimatedBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).noOcclusion(),
                    ModTags.Items.CURRENCY_ITEMS_4, "gacha_machine_4", 4, GachaMachineNeoForge::getBlockEntityType,
                    GachaMachineNeoForge::getAnimatedBlockEntityType));
    public static final DeferredHolder<Block, GachaMachineBlock> GACHA_MACHINE_5 = BLOCKS.register("gacha_machine_5",
            () -> new GachaMachineBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).noOcclusion(),
                    ModTags.Items.CURRENCY_ITEMS_5, "gacha_machine_5", 5, GachaMachineNeoForge::getBlockEntityType));
    public static final DeferredHolder<Block, GachaMachineBlock> GACHA_MACHINE_6 = BLOCKS.register("gacha_machine_6",
            () -> new GachaMachineBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).noOcclusion(),
                    ModTags.Items.CURRENCY_ITEMS_6, "gacha_machine_6", 6, GachaMachineNeoForge::getBlockEntityType));
    public static final DeferredHolder<Block, GachaMachineBlock> GACHA_MACHINE_7 = BLOCKS.register("gacha_machine_7",
            () -> new GachaMachineBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).noOcclusion(),
                    ModTags.Items.CURRENCY_ITEMS_7, "gacha_machine_7", 7, GachaMachineNeoForge::getBlockEntityType));
    public static final DeferredHolder<Block, GachaMachineBlock> GACHA_MACHINE_8 = BLOCKS.register("gacha_machine_8",
            () -> new GachaMachineBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).noOcclusion(),
                    ModTags.Items.CURRENCY_ITEMS_8, "gacha_machine_8", 8, GachaMachineNeoForge::getBlockEntityType));
    public static final DeferredHolder<Block, GachaMachineBlock> GACHA_MACHINE_9 = BLOCKS.register("gacha_machine_9",
            () -> new GachaMachineBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).noOcclusion(),
                    ModTags.Items.CURRENCY_ITEMS_9, "gacha_machine_9", 9, GachaMachineNeoForge::getBlockEntityType));
    public static final DeferredHolder<Block, GachaMachineBlock> GACHA_MACHINE_10 = BLOCKS.register("gacha_machine_10",
            () -> new GachaMachineBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).noOcclusion(),
                    ModTags.Items.CURRENCY_ITEMS_10, "gacha_machine_10", 10, GachaMachineNeoForge::getBlockEntityType));

    public static final DeferredHolder<LootPoolEntryType, LootPoolEntryType> COMMAND_ENTRY =
            LOOT_ENTRY_TYPES.register("command_entry", () -> new LootPoolEntryType(CommandLootEntry.CODEC));

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GACHA_TAB =
            CREATIVE_TABS.register("gacha_machines", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.gacha_machines"))
                    .icon(() -> new ItemStack(GachaItemRegistryNeoForge.GACHA_MACHINE.get()))
                    .displayItems((params, output) -> {
                        output.accept(GachaItemRegistryNeoForge.GACHA_MACHINE.get());
                        output.accept(GachaItemRegistryNeoForge.GACHA_MACHINE_2.get());
                        output.accept(GachaItemRegistryNeoForge.GACHA_MACHINE_3.get());
                        output.accept(GachaItemRegistryNeoForge.GACHA_MACHINE_4.get());
                        output.accept(GachaItemRegistryNeoForge.GACHA_MACHINE_5.get());
                        output.accept(GachaItemRegistryNeoForge.GACHA_MACHINE_6.get());
                        output.accept(GachaItemRegistryNeoForge.GACHA_MACHINE_7.get());
                        output.accept(GachaItemRegistryNeoForge.GACHA_MACHINE_8.get());
                        output.accept(GachaItemRegistryNeoForge.GACHA_MACHINE_9.get());
                        output.accept(GachaItemRegistryNeoForge.GACHA_MACHINE_10.get());

                        output.accept(GachaItemRegistryNeoForge.GACHA_COIN.get());
                        output.accept(GachaItemRegistryNeoForge.GACHA_COIN_2.get());
                        output.accept(GachaItemRegistryNeoForge.GACHA_COIN_3.get());
                        output.accept(GachaItemRegistryNeoForge.GACHA_COIN_4.get());
                        output.accept(GachaItemRegistryNeoForge.GACHA_COIN_5.get());
                        output.accept(GachaItemRegistryNeoForge.GACHA_COIN_6.get());
                        output.accept(GachaItemRegistryNeoForge.GACHA_COIN_7.get());
                        output.accept(GachaItemRegistryNeoForge.GACHA_COIN_8.get());
                        output.accept(GachaItemRegistryNeoForge.GACHA_COIN_9.get());
                        output.accept(GachaItemRegistryNeoForge.GACHA_COIN_10.get());

                        GachaItemRegistryNeoForge.ALL_CAPSULES.forEach(holder -> output.accept(holder.get()));
                    })
                    .build());

    public static LootContextParamSet GACHA_MACHINE_LOOT_CONTEXT;
    public static LootContextParamSet CAPSULE_LOOT_CONTEXT;

    public GachaMachineNeoForge(IEventBus modEventBus) {
        GACHA_MACHINE_BLOCK_ENTITY = BLOCK_ENTITIES.register("gacha", () -> BlockEntityType.Builder.of(
                (pos, state) -> new GachaMachineBlockEntity(GACHA_MACHINE_BLOCK_ENTITY.get(), pos, state),
                GACHA_MACHINE.get(), GACHA_MACHINE_2.get(), GACHA_MACHINE_3.get(),
                GACHA_MACHINE_5.get(), GACHA_MACHINE_6.get(), GACHA_MACHINE_7.get(), GACHA_MACHINE_8.get(),
                GACHA_MACHINE_9.get(), GACHA_MACHINE_10.get()
        ).build(null));
        GACHA_MACHINE_ANIMATED_BE = BLOCK_ENTITIES.register("gacha_animated", () -> BlockEntityType.Builder.of(
                (pos, state) -> new GachaMachineAnimatedBlockEntity(GACHA_MACHINE_ANIMATED_BE.get(), pos, state),
                GACHA_MACHINE_4.get()
        ).build(null));

        BLOCKS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
        CREATIVE_TABS.register(modEventBus);
        LOOT_ENTRY_TYPES.register(modEventBus);
        GachaItemRegistryNeoForge.ITEMS.register(modEventBus);

        GACHA_MACHINE_LOOT_CONTEXT = registerLootContext(GachaMachine.id("gacha_machine"),
                b -> b.required(LootContextParams.ORIGIN).required(LootContextParams.THIS_ENTITY));
        CAPSULE_LOOT_CONTEXT = registerLootContext(GachaMachine.id("capsule"),
                b -> b.required(LootContextParams.ORIGIN).optional(LootContextParams.THIS_ENTITY));

        GachaMachineRegistries.GACHA_MACHINE_LOOT_CONTEXT = GACHA_MACHINE_LOOT_CONTEXT;
        GachaMachineRegistries.CAPSULE_LOOT_CONTEXT = CAPSULE_LOOT_CONTEXT;
        GachaMachineRegistries.setItemIdLookup(item -> BuiltInRegistries.ITEM.getKey(item));
        GachaMachineRegistries.setFakePlayerProvider(level -> {
            GameProfile profile = new GameProfile(UUID.nameUUIDFromBytes("gachamachine".getBytes()), "gachamachine");
            return new FakePlayer(level, profile);
        });

        modEventBus.addListener(this::onRegistryComplete);
        modEventBus.addListener(this::registerCapabilities);

        NeoForge.EVENT_BUS.addListener(this::onAddReloadListeners);

        GachaMachine.LOGGER.info("Gacha Machine NeoForge initialized!");
    }

    private void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new GachaSpawnPoolReloadListenerNeoForge());
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                GACHA_MACHINE_BLOCK_ENTITY.get(),
                (blockEntity, direction) -> new SidedInvWrapper(blockEntity, direction)
        );
    }

    private void onRegistryComplete(net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModLootTypes.setCommandEntry(COMMAND_ENTRY.get());
        });
    }

    private static LootContextParamSet registerLootContext(ResourceLocation id, Consumer<LootContextParamSet.Builder> type) {
        LootContextParamSet.Builder builder = new LootContextParamSet.Builder();
        type.accept(builder);
        LootContextParamSet lootContextType = builder.build();
        LootContextParamSet check = LootContextTypesAccessor.getMAP().put(id, lootContextType);
        if (check != null) {
            throw new IllegalStateException("Loot table parameter set " + id + " is already registered");
        }
        return lootContextType;
    }
}
