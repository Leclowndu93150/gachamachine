package com.hisroyalty.gachamachine;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static com.hisroyalty.gachamachine.GachaMachine.id;

public class ModTags {

    public static class Items {
        public static final TagKey<Item> CURRENCY_ITEMS = createTag("currency_items");
        public static final TagKey<Item> CURRENCY_ITEMS_2 = createTag("currency_items_2");
        public static final TagKey<Item> CURRENCY_ITEMS_3 = createTag("currency_items_3");
        public static final TagKey<Item> CURRENCY_ITEMS_4 = createTag("currency_items_4");
        public static final TagKey<Item> CURRENCY_ITEMS_5 = createTag("currency_items_5");
        public static final TagKey<Item> CURRENCY_ITEMS_6 = createTag("currency_items_6");
        public static final TagKey<Item> CURRENCY_ITEMS_7 = createTag("currency_items_7");
        public static final TagKey<Item> CURRENCY_ITEMS_8 = createTag("currency_items_8");
        public static final TagKey<Item> CURRENCY_ITEMS_9 = createTag("currency_items_9");
        public static final TagKey<Item> CURRENCY_ITEMS_10 = createTag("currency_items_10");

        private static TagKey<Item> createTag(String name) {
            return TagKey.create(Registries.ITEM, id(name));
        }
    }
}
