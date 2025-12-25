package com.hisroyalty.gachamachine.loot;

import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;

public class ModLootTypes {
    public static LootPoolEntryType COMMAND_ENTRY;

    public static void setCommandEntry(LootPoolEntryType type) {
        COMMAND_ENTRY = type;
    }
}
