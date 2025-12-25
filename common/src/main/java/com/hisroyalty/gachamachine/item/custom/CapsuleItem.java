package com.hisroyalty.gachamachine.item.custom;

import com.hisroyalty.gachamachine.GachaMachine;
import com.hisroyalty.gachamachine.GachaMachineRegistries;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class CapsuleItem extends Item {
    private ResourceLocation lootTableId;

    public CapsuleItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) return InteractionResultHolder.pass(player.getItemInHand(hand));

        List<ItemStack> drops = getDrops((ServerLevel) level, player.position(), player);

        if (drops.isEmpty()) {
            player.displayClientMessage(
                    Component.translatable("message.capsule.empty_loot_table")
                            .withStyle(ChatFormatting.RED),
                    true
            );
        }

        ItemStack capsule = player.getItemInHand(hand);
        ItemStack resultStack = capsule.copy();
        resultStack.shrink(1);

        for (ItemStack stack : drops) {
            if (!player.addItem(stack)) {
                player.drop(stack, false);
            }
        }

        level.playSound(null, player.blockPosition(),
                SoundEvents.SNIFFER_EGG_PLOP,
                SoundSource.PLAYERS, 2f, 1f);

        return InteractionResultHolder.consume(resultStack);
    }

    private List<ItemStack> getDrops(ServerLevel level, Vec3 pos, @Nullable Entity user) {
        if (getLootTableId() == null) return Collections.emptyList();
        LootParams.Builder builder = new LootParams.Builder(level)
                .withParameter(LootContextParams.ORIGIN, pos);
        if (user != null) {
            builder.withOptionalParameter(LootContextParams.THIS_ENTITY, user);
        }
        LootParams lootParams = builder.create(GachaMachineRegistries.CAPSULE_LOOT_CONTEXT);
        LootTable lootTable = level.getServer()
                .reloadableRegistries()
                .getLootTable(ResourceKey.create(Registries.LOOT_TABLE, getLootTableId()));
        return lootTable.getRandomItems(lootParams);
    }

    public final ResourceLocation getLootTableId() {
        if (lootTableId == null) {
            ResourceLocation itemId = GachaMachineRegistries.getItemId(this);
            if (itemId != null) {
                lootTableId = ResourceLocation.fromNamespaceAndPath(itemId.getNamespace(), "gacha_capsules/" + itemId.getPath());
            }
        }
        return lootTableId;
    }
}
