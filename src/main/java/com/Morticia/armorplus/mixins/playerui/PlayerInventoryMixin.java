package com.Morticia.armorplus.mixins.playerui;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin {
    @Shadow
    @Final
    @Mutable
    public final DefaultedList<ItemStack> offHand = DefaultedList.ofSize(4, ItemStack.EMPTY);

    @Shadow
    @Final
    @Mutable
    public final DefaultedList<ItemStack> main = DefaultedList.ofSize(38, ItemStack.EMPTY);
}
