package com.Morticia.armorplus.mixins.playerui;

import com.Morticia.armorplus.GUI.GUICoords;
import com.Morticia.armorplus.GUI.GearSlot;
import com.Morticia.armorplus.item.GearType;
import com.Morticia.armorplus.misc.OffhandSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerScreenHandler.class)
public abstract class  PlayerScreenHandlerMixin extends AbstractRecipeScreenHandler<CraftingInventory> {
    // Redirects addSlot() and only makes a slot if it's not an armor slot
    @Redirect(at = @At(value  = "INVOKE", target = "net/minecraft/screen/PlayerScreenHandler.addSlot(Lnet/minecraft/screen/slot/Slot;)Lnet/minecraft/screen/slot/Slot;", ordinal = 2), method = "<init>")
    private Slot addSlotRedirect1(PlayerScreenHandler playerScreenHandler, Slot slot) {
        /* This was criminally stupid for what I was doing with it but I might use this code later on
        Slot newSlot = new Slot(slot.inventory, slot.index, slot.x, slot.y);

        newSlot.id = this.slots.size();
        this.slots.add(newSlot);
        this.trackedStacks.add(ItemStack.EMPTY);

        return newSlot;
         */
        return slot;
    }

    // Adds extra offhand slots
    @Inject(at = @At(value = "RETURN"), method = "<init>")
    private void addOffhandSlots(PlayerInventory inventory, boolean onServer, PlayerEntity owner, CallbackInfo ci) {
        for(int y = 0; y < 4; ++y) {
            this.addSlot(new OffhandSlot(inventory, 40 + y, 77, 62 - y * 18));
        }

        this.addSlot(new GearSlot(inventory, 46, GUICoords.invX - 69, GUICoords.invY + 7, GearType.CAWL));
    }

    private PlayerScreenHandlerMixin(ScreenHandlerType<?> screenHandlerType, int i) {
        super(screenHandlerType, i);
    }
}
