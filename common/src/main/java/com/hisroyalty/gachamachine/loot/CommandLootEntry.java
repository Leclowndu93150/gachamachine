package com.hisroyalty.gachamachine.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Consumer;

public class CommandLootEntry extends LootPoolSingletonContainer {
    public static final MapCodec<CommandLootEntry> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            com.mojang.serialization.Codec.STRING.fieldOf("command").forGetter(e -> e.command)
    ).and(singletonFields(instance)).apply(instance, CommandLootEntry::new));

    private final String command;

    public CommandLootEntry(String command, int weight, int quality, List<LootItemCondition> conditions, List<LootItemFunction> functions) {
        super(weight, quality, conditions, functions);
        this.command = command;
    }

    @Override
    protected void createItemStack(Consumer<ItemStack> stackConsumer, LootContext context) {
        Level level = context.getLevel();
        if (!(level instanceof ServerLevel serverLevel)) return;

        MinecraftServer server = serverLevel.getServer();
        Vec3 origin = context.getParam(LootContextParams.ORIGIN);

        CommandSourceStack source = new CommandSourceStack(
                CommandSource.NULL,
                origin,
                Vec2.ZERO,
                serverLevel,
                2,
                "LootCommand",
                Component.literal("LootCommand"),
                server,
                null
        );

        try {
            server.getCommands().performPrefixedCommand(source, command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public LootPoolEntryType getType() {
        return ModLootTypes.COMMAND_ENTRY;
    }
}
