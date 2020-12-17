package com.Morticia.armorplus.mixins.playerui;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerScreenHandler.class)
public abstract class PlayerScreenHandlerMixin extends AbstractRecipeScreenHandler<CraftingInventory> {
    // Redirects addSlot() and only makes a slot if it's not an armor slot
    @Redirect(at = @At(value  = "INVOKE", target = "net/minecraft/screen/PlayerScreenHandler.addSlot(Lnet/minecraft/screen/slot/Slot;)Lnet/minecraft/screen/slot/Slot;"), method = "<init>")
    private Slot PlayerScreenHandler(PlayerScreenHandler playerScreenHandler, Slot slot) {
        if (slot.x == 8 && slot.y >= 8 && slot.y <= 80) {
            return slot;
        } else {
            slot.id = this.slots.size();
            this.slots.add(slot);
            this.trackedStacks.add(ItemStack.EMPTY);
            return slot;
        }
    }

    private PlayerScreenHandlerMixin(ScreenHandlerType<?> screenHandlerType, int i) {
        super(screenHandlerType, i);
    }
}
