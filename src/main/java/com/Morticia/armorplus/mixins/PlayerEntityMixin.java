package com.Morticia.armorplus.mixins;

import com.Morticia.armorplus.misc.OffhandSlot;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.*;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Mutable
    @Final
    @Shadow
    public final PlayerInventory inventory;

    private PlayerEntityMixin(PlayerInventory inventory) {
        this.inventory = inventory;
    }

    /**
     * This over writes the method which gets the current stack, specifically changing how the off hand slot is selected
     *
     * @author Morticia
     * @since 2020-12-17
     */
    @Overwrite
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            return this.inventory.getMainHandStack();
        } else if (slot == EquipmentSlot.OFFHAND) {
            return (ItemStack)this.inventory.offHand.get(OffhandSlot.activeSlot);
        } else {
            return slot.getType() == EquipmentSlot.Type.ARMOR ? (ItemStack)this.inventory.armor.get(slot.getEntitySlotId()) : ItemStack.EMPTY;
        }
    }
}
