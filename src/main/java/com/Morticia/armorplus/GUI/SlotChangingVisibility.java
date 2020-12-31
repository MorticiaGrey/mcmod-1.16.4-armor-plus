package com.Morticia.armorplus.GUI;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class SlotChangingVisibility extends Slot {
    public boolean visible = false;

    public SlotChangingVisibility(Inventory inventory, int index, int x, int y, CustomSlotsRegistry.SlotCategory slotCategory) {
        super(inventory, index, x, y);
        CustomSlotsRegistry.addSlot(this, slotCategory);
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return true;
    }

    @Override
    public boolean doDrawHoveringEffect() {
        return visible && super.doDrawHoveringEffect();
    }
}
