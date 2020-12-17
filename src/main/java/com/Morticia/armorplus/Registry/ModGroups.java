package com.Morticia.armorplus.Registry;

import com.Morticia.armorplus.ArmorPlus;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class ModGroups {
    public static final ItemGroup ARMOR_PLUS = FabricItemGroupBuilder.build(
            new Identifier(ArmorPlus.MOD_ID, "armor_plus_item_group"),
            () -> new ItemStack(Items.DIAMOND_CHESTPLATE)
    );
}
