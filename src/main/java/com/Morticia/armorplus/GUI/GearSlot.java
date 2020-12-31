package com.Morticia.armorplus.GUI;

import com.Morticia.armorplus.item.GearType;
import com.Morticia.armorplus.item.PlayerStats;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class GearSlot extends SlotChangingVisibility {
    final GearType acceptedGearType;

    public GearSlot(Inventory inventory, int index, int x, int y, GearType acceptedGearType, CustomSlotsRegistry.SlotCategory slotCategory) {
        super(inventory, index, x, y, slotCategory);
        this.acceptedGearType = acceptedGearType;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        try {
            return ((PlayerStats) stack.getItem()).getType() == acceptedGearType;
        } catch (Exception e) {
            return false;
        }
    }
}
