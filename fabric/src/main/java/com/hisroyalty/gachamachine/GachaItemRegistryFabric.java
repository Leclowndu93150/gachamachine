package com.hisroyalty.gachamachine;

import com.hisroyalty.gachamachine.item.custom.CapsuleItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.ArrayList;
import java.util.List;

public class GachaItemRegistryFabric {

    public static final List<Item> ALL_CAPSULES = new ArrayList<>();

    public static final BlockItem GACHA_MACHINE = register("gacha_machine",
            new BlockItem(GachaMachineFabric.GACHA_MACHINE, new Item.Properties()));
    public static final BlockItem GACHA_MACHINE_2 = register("gacha_machine_2",
            new BlockItem(GachaMachineFabric.GACHA_MACHINE_2, new Item.Properties()));
    public static final BlockItem GACHA_MACHINE_3 = register("gacha_machine_3",
            new BlockItem(GachaMachineFabric.GACHA_MACHINE_3, new Item.Properties()));
    public static final BlockItem GACHA_MACHINE_4 = register("gacha_machine_4",
            new BlockItem(GachaMachineFabric.GACHA_MACHINE_4, new Item.Properties()));
    public static final BlockItem GACHA_MACHINE_5 = register("gacha_machine_5",
            new BlockItem(GachaMachineFabric.GACHA_MACHINE_5, new Item.Properties()));
    public static final BlockItem GACHA_MACHINE_6 = register("gacha_machine_6",
            new BlockItem(GachaMachineFabric.GACHA_MACHINE_6, new Item.Properties()));
    public static final BlockItem GACHA_MACHINE_7 = register("gacha_machine_7",
            new BlockItem(GachaMachineFabric.GACHA_MACHINE_7, new Item.Properties()));
    public static final BlockItem GACHA_MACHINE_8 = register("gacha_machine_8",
            new BlockItem(GachaMachineFabric.GACHA_MACHINE_8, new Item.Properties()));
    public static final BlockItem GACHA_MACHINE_9 = register("gacha_machine_9",
            new BlockItem(GachaMachineFabric.GACHA_MACHINE_9, new Item.Properties()));
    public static final BlockItem GACHA_MACHINE_10 = register("gacha_machine_10",
            new BlockItem(GachaMachineFabric.GACHA_MACHINE_10, new Item.Properties()));

    public static final Item GACHA_COIN = register("gacha_coin", new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final Item GACHA_COIN_2 = register("gacha_coin_2", new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final Item GACHA_COIN_3 = register("gacha_coin_3", new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final Item GACHA_COIN_4 = register("gacha_coin_4", new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final Item GACHA_COIN_5 = register("gacha_coin_5", new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final Item GACHA_COIN_6 = register("gacha_coin_6", new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final Item GACHA_COIN_7 = register("gacha_coin_7", new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final Item GACHA_COIN_8 = register("gacha_coin_8", new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final Item GACHA_COIN_9 = register("gacha_coin_9", new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final Item GACHA_COIN_10 = register("gacha_coin_10", new Item(new Item.Properties().rarity(Rarity.RARE)));

    public static final CapsuleItem CAPSULE_A1 = registerCapsule("capsule_a1");
    public static final CapsuleItem CAPSULE_A2 = registerCapsule("capsule_a2");
    public static final CapsuleItem CAPSULE_A3 = registerCapsule("capsule_a3");
    public static final CapsuleItem CAPSULE_A4 = registerCapsule("capsule_a4");
    public static final CapsuleItem CAPSULE_A5 = registerCapsule("capsule_a5");
    public static final CapsuleItem CAPSULE_A6 = registerCapsule("capsule_a6");
    public static final CapsuleItem CAPSULE_A7 = registerCapsule("capsule_a7");
    public static final CapsuleItem CAPSULE_A8 = registerCapsule("capsule_a8");
    public static final CapsuleItem CAPSULE_A9 = registerCapsule("capsule_a9");
    public static final CapsuleItem CAPSULE_A10 = registerCapsule("capsule_a10");

    public static final CapsuleItem CAPSULE_B1 = registerCapsule("capsule_b1");
    public static final CapsuleItem CAPSULE_B2 = registerCapsule("capsule_b2");
    public static final CapsuleItem CAPSULE_B3 = registerCapsule("capsule_b3");
    public static final CapsuleItem CAPSULE_B4 = registerCapsule("capsule_b4");
    public static final CapsuleItem CAPSULE_B5 = registerCapsule("capsule_b5");
    public static final CapsuleItem CAPSULE_B6 = registerCapsule("capsule_b6");
    public static final CapsuleItem CAPSULE_B7 = registerCapsule("capsule_b7");
    public static final CapsuleItem CAPSULE_B8 = registerCapsule("capsule_b8");
    public static final CapsuleItem CAPSULE_B9 = registerCapsule("capsule_b9");
    public static final CapsuleItem CAPSULE_B10 = registerCapsule("capsule_b10");

    public static final CapsuleItem CAPSULE_C1 = registerCapsule("capsule_c1");
    public static final CapsuleItem CAPSULE_C2 = registerCapsule("capsule_c2");
    public static final CapsuleItem CAPSULE_C3 = registerCapsule("capsule_c3");
    public static final CapsuleItem CAPSULE_C4 = registerCapsule("capsule_c4");
    public static final CapsuleItem CAPSULE_C5 = registerCapsule("capsule_c5");
    public static final CapsuleItem CAPSULE_C6 = registerCapsule("capsule_c6");
    public static final CapsuleItem CAPSULE_C7 = registerCapsule("capsule_c7");
    public static final CapsuleItem CAPSULE_C8 = registerCapsule("capsule_c8");
    public static final CapsuleItem CAPSULE_C9 = registerCapsule("capsule_c9");
    public static final CapsuleItem CAPSULE_C10 = registerCapsule("capsule_c10");

    public static final CapsuleItem CAPSULE_D1 = registerCapsule("capsule_d1");
    public static final CapsuleItem CAPSULE_D2 = registerCapsule("capsule_d2");
    public static final CapsuleItem CAPSULE_D3 = registerCapsule("capsule_d3");
    public static final CapsuleItem CAPSULE_D4 = registerCapsule("capsule_d4");
    public static final CapsuleItem CAPSULE_D5 = registerCapsule("capsule_d5");
    public static final CapsuleItem CAPSULE_D6 = registerCapsule("capsule_d6");
    public static final CapsuleItem CAPSULE_D7 = registerCapsule("capsule_d7");
    public static final CapsuleItem CAPSULE_D8 = registerCapsule("capsule_d8");
    public static final CapsuleItem CAPSULE_D9 = registerCapsule("capsule_d9");
    public static final CapsuleItem CAPSULE_D10 = registerCapsule("capsule_d10");

    public static final CapsuleItem CAPSULE_E1 = registerCapsule("capsule_e1");
    public static final CapsuleItem CAPSULE_E2 = registerCapsule("capsule_e2");
    public static final CapsuleItem CAPSULE_E3 = registerCapsule("capsule_e3");
    public static final CapsuleItem CAPSULE_E4 = registerCapsule("capsule_e4");
    public static final CapsuleItem CAPSULE_E5 = registerCapsule("capsule_e5");
    public static final CapsuleItem CAPSULE_E6 = registerCapsule("capsule_e6");
    public static final CapsuleItem CAPSULE_E7 = registerCapsule("capsule_e7");
    public static final CapsuleItem CAPSULE_E8 = registerCapsule("capsule_e8");
    public static final CapsuleItem CAPSULE_E9 = registerCapsule("capsule_e9");
    public static final CapsuleItem CAPSULE_E10 = registerCapsule("capsule_e10");

    public static final CapsuleItem CAPSULE_F1 = registerCapsule("capsule_f1");
    public static final CapsuleItem CAPSULE_F2 = registerCapsule("capsule_f2");
    public static final CapsuleItem CAPSULE_F3 = registerCapsule("capsule_f3");
    public static final CapsuleItem CAPSULE_F4 = registerCapsule("capsule_f4");
    public static final CapsuleItem CAPSULE_F5 = registerCapsule("capsule_f5");
    public static final CapsuleItem CAPSULE_F6 = registerCapsule("capsule_f6");
    public static final CapsuleItem CAPSULE_F7 = registerCapsule("capsule_f7");
    public static final CapsuleItem CAPSULE_F8 = registerCapsule("capsule_f8");
    public static final CapsuleItem CAPSULE_F9 = registerCapsule("capsule_f9");
    public static final CapsuleItem CAPSULE_F10 = registerCapsule("capsule_f10");

    public static final CapsuleItem CAPSULE_G1 = registerCapsule("capsule_g1");
    public static final CapsuleItem CAPSULE_G2 = registerCapsule("capsule_g2");
    public static final CapsuleItem CAPSULE_G3 = registerCapsule("capsule_g3");
    public static final CapsuleItem CAPSULE_G4 = registerCapsule("capsule_g4");
    public static final CapsuleItem CAPSULE_G5 = registerCapsule("capsule_g5");
    public static final CapsuleItem CAPSULE_G6 = registerCapsule("capsule_g6");
    public static final CapsuleItem CAPSULE_G7 = registerCapsule("capsule_g7");
    public static final CapsuleItem CAPSULE_G8 = registerCapsule("capsule_g8");
    public static final CapsuleItem CAPSULE_G9 = registerCapsule("capsule_g9");
    public static final CapsuleItem CAPSULE_G10 = registerCapsule("capsule_g10");

    public static final CapsuleItem CAPSULE_H1 = registerCapsule("capsule_h1");
    public static final CapsuleItem CAPSULE_H2 = registerCapsule("capsule_h2");
    public static final CapsuleItem CAPSULE_H3 = registerCapsule("capsule_h3");
    public static final CapsuleItem CAPSULE_H4 = registerCapsule("capsule_h4");
    public static final CapsuleItem CAPSULE_H5 = registerCapsule("capsule_h5");
    public static final CapsuleItem CAPSULE_H6 = registerCapsule("capsule_h6");
    public static final CapsuleItem CAPSULE_H7 = registerCapsule("capsule_h7");
    public static final CapsuleItem CAPSULE_H8 = registerCapsule("capsule_h8");
    public static final CapsuleItem CAPSULE_H9 = registerCapsule("capsule_h9");
    public static final CapsuleItem CAPSULE_H10 = registerCapsule("capsule_h10");

    public static final CapsuleItem CAPSULE_I1 = registerCapsule("capsule_i1");
    public static final CapsuleItem CAPSULE_I2 = registerCapsule("capsule_i2");
    public static final CapsuleItem CAPSULE_I3 = registerCapsule("capsule_i3");
    public static final CapsuleItem CAPSULE_I4 = registerCapsule("capsule_i4");
    public static final CapsuleItem CAPSULE_I5 = registerCapsule("capsule_i5");
    public static final CapsuleItem CAPSULE_I6 = registerCapsule("capsule_i6");
    public static final CapsuleItem CAPSULE_I7 = registerCapsule("capsule_i7");
    public static final CapsuleItem CAPSULE_I8 = registerCapsule("capsule_i8");
    public static final CapsuleItem CAPSULE_I9 = registerCapsule("capsule_i9");
    public static final CapsuleItem CAPSULE_I10 = registerCapsule("capsule_i10");

    public static final CapsuleItem CAPSULE_J1 = registerCapsule("capsule_j1");
    public static final CapsuleItem CAPSULE_J2 = registerCapsule("capsule_j2");
    public static final CapsuleItem CAPSULE_J3 = registerCapsule("capsule_j3");
    public static final CapsuleItem CAPSULE_J4 = registerCapsule("capsule_j4");
    public static final CapsuleItem CAPSULE_J5 = registerCapsule("capsule_j5");
    public static final CapsuleItem CAPSULE_J6 = registerCapsule("capsule_j6");
    public static final CapsuleItem CAPSULE_J7 = registerCapsule("capsule_j7");
    public static final CapsuleItem CAPSULE_J8 = registerCapsule("capsule_j8");
    public static final CapsuleItem CAPSULE_J9 = registerCapsule("capsule_j9");
    public static final CapsuleItem CAPSULE_J10 = registerCapsule("capsule_j10");

    private static <T extends Item> T register(String id, T item) {
        return Registry.register(BuiltInRegistries.ITEM, GachaMachine.id(id), item);
    }

    private static CapsuleItem registerCapsule(String id) {
        CapsuleItem capsule = register(id, new CapsuleItem(new Item.Properties()));
        ALL_CAPSULES.add(capsule);
        return capsule;
    }

    public static void init() {
    }
}
