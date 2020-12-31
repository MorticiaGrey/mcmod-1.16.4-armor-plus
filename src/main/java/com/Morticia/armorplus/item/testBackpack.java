package com.Morticia.armorplus.item;

import net.minecraft.item.Item;

public class testBackpack extends Item implements PlayerStats.Backpack {
    public testBackpack(Settings settings) {
        super(settings);
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

    @Override
    public int getTier() {
        return 1;
    }
}
