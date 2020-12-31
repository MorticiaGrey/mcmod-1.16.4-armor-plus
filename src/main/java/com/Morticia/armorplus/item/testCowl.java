package com.Morticia.armorplus.item;

import net.minecraft.item.Item;

public class testCowl extends Item implements PlayerStats {
    public testCowl(Settings settings) {
        super(settings);
    }

    @Override
    public GearType getType() {
        return GearType.COWL;
    }

    @Override
    public int getStaminaIncrease() {
        return 0;
    }

    @Override
    public int getHealthIncrease() {
        return 0;
    }

    @Override
    public int getManaIncrease() {
        return 0;
    }
}
