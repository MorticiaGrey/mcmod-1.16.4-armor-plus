package com.Morticia.armorplus.item;

import net.minecraft.item.Item;

public class testGloves extends Item implements PlayerStats {
    public testGloves(Settings settings) {
        super(settings);
    }

    @Override
    public GearType getType() {
        return GearType.GLOVES;
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
