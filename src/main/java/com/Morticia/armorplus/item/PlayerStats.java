package com.Morticia.armorplus.item;

// Originally written by Morticia

public interface PlayerStats {
    // THIS IS FOR ITEMS

    // For determining if the item is stat enabled
    boolean isStatEnabled = true;
    // Type of item
    GearType getType();
    // Returns individual stats
    int getStaminaIncrease();
    int getHealthIncrease();
    int getManaIncrease();
}
