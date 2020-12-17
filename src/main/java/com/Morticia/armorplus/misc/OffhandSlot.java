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
            activeSlot += 1;
        }
    }

    public void setUsable(boolean usable) {
        isUsable = usable;
    }

    public boolean toggleUsable() {
        return isUsable = !isUsable;
    }
}
