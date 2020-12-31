package com.Morticia.armorplus.GUI;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class ArmorSlot extends SlotChangingVisibility {
    private static final EquipmentSlot[] EQUIPMENT_SLOT_ORDER;
    private final EquipmentSlot equipmentSlot;

    public ArmorSlot(Inventory inventory, int index, int x, int y, int order) {
        super(inventory, index, x, y, CustomSlotsRegistry.SlotCategory.EQUIPMENT);
        equipmentSlot = EQUIPMENT_SLOT_ORDER[order];
        CustomSlotsRegistry.addSlot(this, CustomSlotsRegistry.SlotCategory.EQUIPMENT);
    }

    public int getMaxItemCount() {
        return 1;
    }

    public boolean canInsert(ItemStack stack) {
        return equipmentSlot == MobEntity.getPreferredEquipmentSlot(stack);
    }

    public boolean canTakeItems(PlayerEntity playerEntity) {
        ItemStack itemStack = this.getStack();
        return (itemStack.isEmpty() || playerEntity.isCreative() || !EnchantmentHelper.hasBindingCurse(itemStack)) && super.canTakeItems(playerEntity);
    }
    static {
        EQUIPMENT_SLOT_ORDER = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    }
}
