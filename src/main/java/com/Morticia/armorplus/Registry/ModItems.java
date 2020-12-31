package com.Morticia.armorplus.Registry;

import com.Morticia.armorplus.ArmorPlus;
import com.Morticia.armorplus.item.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {
    public static final testCowl TEST_COWL = new testCowl(new FabricItemSettings().group(ModGroups.ARMOR_PLUS));
    public static final testBelt TEST_BELT = new testBelt(new FabricItemSettings().group(ModGroups.ARMOR_PLUS));
    public static final testBackpack TEST_BACKPACK = new testBackpack(new FabricItemSettings().group(ModGroups.ARMOR_PLUS));
    public static final testGloves TEST_GLOVES = new testGloves(new FabricItemSettings().group(ModGroups.ARMOR_PLUS));
    public static final testShirt TEST_SHIRT = new testShirt(new FabricItemSettings().group(ModGroups.ARMOR_PLUS));
    public static final testShirt TEST_SLEEVE = new testShirt(new FabricItemSettings().group(ModGroups.ARMOR_PLUS));

    public ModItems() {
        Registry.register(Registry.ITEM, new Identifier(ArmorPlus.MOD_ID, "test_cowl"), TEST_COWL);
        Registry.register(Registry.ITEM, new Identifier(ArmorPlus.MOD_ID, "test_belt"), TEST_BELT);
        Registry.register(Registry.ITEM, new Identifier(ArmorPlus.MOD_ID, "test_backpack"), TEST_BACKPACK);
        Registry.register(Registry.ITEM, new Identifier(ArmorPlus.MOD_ID, "test_gloves"), TEST_GLOVES);
        Registry.register(Registry.ITEM, new Identifier(ArmorPlus.MOD_ID, "test_shirt"), TEST_SHIRT);
        Registry.register(Registry.ITEM, new Identifier(ArmorPlus.MOD_ID, "test_sleeve"), TEST_SLEEVE);
    }
}
