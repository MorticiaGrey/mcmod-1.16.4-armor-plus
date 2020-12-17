package com.Morticia.armorplus.misc;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class OffhandSlot extends Slot {
    // For when tiers are added
    public boolean isUsable;
    // Static list of all offhand slots for switching through
    public static List<OffhandSlot> offhandSlots = new ArrayList<>();
    // Currently used slot
    public static int activeSlot = 0;

    public OffhandSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        // Adds to registry
        offhandSlots.add(this);
        // Only active by default if it's the first slot, otherwise needs to be set usable
        isUsable = offhandSlots.size() == 1;
    }

    @Environment(EnvType.CLIENT)
    public Pair<Identifier, Identifier> getBackgroundSprite() {
        return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, PlayerScreenHandler.EMPTY_OFFHAND_ARMOR_SLOT);
    }

    public static void iterateActiveSlot() {
        if (activeSlot >= 3) {
            activeSlot = 0;
        } else if (activeSlot < 0) {
            activeSlot = 0;
        } else {
            activeSlot = getNextFullSlot(activeSlot);
        }
    }

    public static int getNextFullSlot(int current_slot) {
        int slotAmount = offhandSlots.size();
        boolean hasJumped = false;

        // Checks if all slots are empty and returns if they are
        if (!offhandSlots.get(0).hasStack() && !offhandSlots.get(1).hasStack() && !offhandSlots.get(2).hasStack() && !offhandSlots.get(3).hasStack()) {
            return 0;
        }

        for (int i = current_slot; i <= slotAmount; ++i) {
            if (i == 3 && !hasJumped) {
                i = 0;
                hasJumped = true;
            }
            if (i == 3) {
                if (offhandSlots.get(0).hasStack()) {
                    return 0;
                }
            }
            if (offhandSlots.get(i + 1).hasStack()) {

                return i + 1;
            }
        }
        return current_slot;
    }

    public void setUsable(boolean usable) {
        isUsable = usable;
    }

    public boolean toggleUsable() {
        return isUsable = !isUsable;
    }
}
