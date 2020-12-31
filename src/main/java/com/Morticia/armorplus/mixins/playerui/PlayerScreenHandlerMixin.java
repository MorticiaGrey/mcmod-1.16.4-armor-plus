package com.Morticia.armorplus.mixins.playerui;

import com.Morticia.armorplus.GUI.*;
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
    private static final int backpackMenuBorder = 4;

    @Redirect(at = @At(value  = "INVOKE", target = "net/minecraft/screen/PlayerScreenHandler.addSlot(Lnet/minecraft/screen/slot/Slot;)Lnet/minecraft/screen/slot/Slot;", ordinal = 2), method = "<init>")
    private Slot addSlotRedirect1(PlayerScreenHandler playerScreenHandler, Slot slot) {
        return slot;
    }

    @Redirect(at = @At(value  = "INVOKE", target = "net/minecraft/screen/PlayerScreenHandler.addSlot(Lnet/minecraft/screen/slot/Slot;)Lnet/minecraft/screen/slot/Slot;", ordinal = 5), method = "<init>")
    private Slot addSlotRedirect2(PlayerScreenHandler playerScreenHandler, Slot slot) {
        return slot;
    }

    // Adds extra offhand slots
    @Inject(at = @At(value = "RETURN"), method = "<init>")
    private void addOffhandSlots(PlayerInventory inventory, boolean onServer, PlayerEntity owner, CallbackInfo ci) {
        // This list is static but things are added every time the player opens their inventory so this needs to be reset
        OffhandSlot.resetList();
        CustomSlotsRegistry.resetRegistry();

        // Offhand slots
        for(int y = 0; y < 4; ++y) {
            this.addSlot(new OffhandSlot(inventory, 64 + y, 77, 62 - y * 18));
        }

        // Armor slots
        for(int y = 0; y < 3; ++y) {
            this.addSlot(new ArmorSlot(inventory, 62 - y, GUICoords.invX - 87, 6 + y * 18, y));
        }

        this.addSlot(new ArmorSlot(inventory, 63, GUICoords.invX - 69, 42, 3));

        // Cowl slot
        this.addSlot(new GearSlot(inventory, 36, GUICoords.invX - 69, GUICoords.invY + 7, GearType.COWL, CustomSlotsRegistry.SlotCategory.EQUIPMENT));
        // Shirt slot
        this.addSlot(new GearSlot(inventory, 37, GUICoords.invX - 69, GUICoords.invY + 25, GearType.SHIRT, CustomSlotsRegistry.SlotCategory.EQUIPMENT));
        // Gloves slot
        this.addSlot(new GearSlot(inventory, 38, GUICoords.invX - 87, 63, GearType.GLOVES, CustomSlotsRegistry.SlotCategory.EQUIPMENT));
        // Ring slots
        this.addSlot(new GearSlot(inventory, 39, GUICoords.invX - 67, 63, GearType.RING, CustomSlotsRegistry.SlotCategory.EQUIPMENT));
        this.addSlot(new GearSlot(inventory, 40, GUICoords.invX - 49, 63, GearType.RING, CustomSlotsRegistry.SlotCategory.EQUIPMENT));
        // Trinket slot
        this.addSlot(new GearSlot(inventory, 41, GUICoords.invX - 29, 63, GearType.TRINKET, CustomSlotsRegistry.SlotCategory.EQUIPMENT));
        // Earings slot
        this.addSlot(new GearSlot(inventory, 42, GUICoords.invX - 49, GUICoords.invY + 7, GearType.EARRINGS, CustomSlotsRegistry.SlotCategory.EQUIPMENT));
        // Amulet slot
        this.addSlot(new GearSlot(inventory, 43, GUICoords.invX - 49, GUICoords.invY + 25, GearType.AMULET, CustomSlotsRegistry.SlotCategory.EQUIPMENT));
        // Belt slot
        this.addSlot(new GearSlot(inventory, 44, GUICoords.invX - 49, GUICoords.invY + 42, GearType.BELT, CustomSlotsRegistry.SlotCategory.EQUIPMENT));
        // Headdress slot
        this.addSlot(new GearSlot(inventory, 45, GUICoords.invX - 29, GUICoords.invY + 7, GearType.HEADDRESS, CustomSlotsRegistry.SlotCategory.EQUIPMENT));
        // Pauldron slot
        this.addSlot(new GearSlot(inventory, 46, GUICoords.invX - 29, GUICoords.invY + 25, GearType.PAULDRON, CustomSlotsRegistry.SlotCategory.EQUIPMENT));
        // Backpack slot
        this.addSlot(new GearSlot(inventory, 47, GUICoords.invX - 29, GUICoords.invY + 42, GearType.BACKPACK, CustomSlotsRegistry.SlotCategory.EQUIPMENT));
        // Backpack slots
        int slotNumber = 0;
        for (int x = 0; x < 4; ++x) {
            for (int y = 0; y < 3; ++y) {
                this.addSlot(new SlotChangingVisibility(inventory, 48 + slotNumber, ((GUICoords.invX + 179 + 6) + backpackMenuBorder) + (x * 18), (GUICoords.invY + 6) + (y * 18), CustomSlotsRegistry.SlotCategory.BACKPACK));
                slotNumber++;
            }
        }
    }

    private PlayerScreenHandlerMixin(ScreenHandlerType<?> screenHandlerType, int i) {
        super(screenHandlerType, i);
    }
}
