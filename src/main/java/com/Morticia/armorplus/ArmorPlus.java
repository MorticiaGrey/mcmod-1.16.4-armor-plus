package com.Morticia.armorplus;

import com.Morticia.armorplus.Registry.ModItems;
import net.fabricmc.api.ModInitializer;

public class ArmorPlus implements ModInitializer {
    public static final String MOD_ID = "armorplus";

    @Override
    public void onInitialize() {
        new ModItems();
    }
}
