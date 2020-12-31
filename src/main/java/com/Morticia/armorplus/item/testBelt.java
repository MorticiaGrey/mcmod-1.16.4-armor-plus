package com.Morticia.armorplus.item;

import net.minecraft.item.Item;

public class testBelt extends Item implements PlayerStats {
    public testBelt(Settings settings) {
        super(settings);
    }

    @Override
    public GearType getType() {
        return GearType.BELT;
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
