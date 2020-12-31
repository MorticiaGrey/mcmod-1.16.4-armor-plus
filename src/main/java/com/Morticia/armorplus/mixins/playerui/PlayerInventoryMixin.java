package com.Morticia.armorplus.mixins.playerui;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin {
    @Shadow
    @Final
    @Mutable
    public DefaultedList<ItemStack> offHand;

    @Shadow
    @Final
    @Mutable
    public DefaultedList<ItemStack> armor;

    @Mutable
    @Shadow
    @Final
    public DefaultedList<ItemStack> main;

    @Shadow
    @Final
    @Mutable
    private List<DefaultedList<ItemStack>> combinedInventory;

    @Inject(method = "<init>", at = @At(value = "RETURN"))
    private void initInject(PlayerEntity player, CallbackInfo ci) {
        this.main = DefaultedList.ofSize(60, ItemStack.EMPTY);
        this.armor = DefaultedList.ofSize(4, ItemStack.EMPTY);
        this.offHand = DefaultedList.ofSize(4, ItemStack.EMPTY);
        this.combinedInventory = ImmutableList.of(this.main, this.armor, this.offHand);
    }
}
