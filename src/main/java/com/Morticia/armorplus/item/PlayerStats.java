package com.Morticia.armorplus.item;

// Originally written by Morticia

public interface PlayerStats {
    // For determining if the item is stat enabled
    boolean isStatEnabled = true;
    // Type of item
    GearType getType();
    // Returns individual stats
    int getStaminaIncrease();
    int getHealthIncrease();
    int getManaIncrease();

    interface Backpack extends PlayerStats {
        int getTier();

        default GearType getType() {
            return GearType.BACKPACK;
        }
    }
}

