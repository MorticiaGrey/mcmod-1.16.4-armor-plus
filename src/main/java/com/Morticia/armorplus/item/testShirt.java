package com.Morticia.armorplus.item;

import net.minecraft.item.Item;

public class testShirt extends Item implements PlayerStats {
    public testShirt(Settings settings) {
        super(settings);
    }

    @Override
    public GearType getType() {
        return GearType.SHIRT;
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
