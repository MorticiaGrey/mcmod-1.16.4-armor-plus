package com.Morticia.armorplus.GUI;

import com.Morticia.armorplus.item.GearType;
import com.Morticia.armorplus.item.PlayerStats;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class GearSlot extends Slot {
    final GearType acceptedGearType;
    public boolean visible = false;

    public GearSlot(Inventory inventory, int index, int x, int y, GearType acceptedGearType) {
        super(inventory, index, x, y);
        this.acceptedGearType = acceptedGearType;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        if (!visible) {
            return false;
        }

        try {
            return ((PlayerStats) stack.getItem()).getType() == acceptedGearType;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean doDrawHoveringEffect() {
        if (visible) {
            return super.doDrawHoveringEffect();
        }
        return false;
    }
}
