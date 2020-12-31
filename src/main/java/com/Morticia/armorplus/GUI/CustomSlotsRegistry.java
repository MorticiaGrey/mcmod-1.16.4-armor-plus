package com.Morticia.armorplus.GUI;

import net.minecraft.screen.slot.Slot;

import java.util.ArrayList;
import java.util.List;

public class CustomSlotsRegistry {
    // This is for all the custom slots so I can set them visible/invisible easily

    public static List<Slot> combined = new ArrayList<>();

    public static List<Slot> equipmentSlots = new ArrayList<>();
    public static List<Slot> backpackSlots = new ArrayList<>();
    public static List<Slot> augmentSlots = new ArrayList<>();


    public static <T extends Slot> void addSlot(T slot, SlotCategory category) {
        if (category == SlotCategory.EQUIPMENT) {
            equipmentSlots.add(slot);
        } else if (category == SlotCategory.BACKPACK) {
            backpackSlots.add(slot);
        } else if (category == SlotCategory.AUGMENTS) {
            augmentSlots.add(slot);
        }

        combined.add(slot);
    }

    public static void resetRegistry() {
        combined = new ArrayList<>();
        equipmentSlots = new ArrayList<>();
        backpackSlots = new ArrayList<>();
        augmentSlots = new ArrayList<>();
    }

    public enum SlotCategory {
        EQUIPMENT, BACKPACK, AUGMENTS
    }
}
