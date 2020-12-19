package com.Morticia.armorplus.Registry;

import com.Morticia.armorplus.item.testItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {
    public static final Item TEST_ARMOR_PIECE = new Item(new Item.Settings().group(ModGroups.ARMOR_PLUS));

    public static final testItem TEST_CAWL = new testItem(new Item.Settings().group(ModGroups.ARMOR_PLUS));

    public ModItems() {
        Registry.register(Registry.ITEM, new Identifier("armorplus", "test_cawl"), TEST_CAWL);
    }
}
