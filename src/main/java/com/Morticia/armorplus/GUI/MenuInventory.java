package com.Morticia.armorplus.GUI;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

import java.util.Iterator;
import java.util.List;

public class MenuInventory implements Inventory {
    DefaultedList<ItemStack> main;
    int changeCount = 0;

    @Override
    public int size() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return main.isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return main.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        List<ItemStack> list = null;

        DefaultedList defaultedList;
        for(Iterator var4 = this.main.iterator(); var4.hasNext(); slot -= defaultedList.size()) {
            defaultedList = (DefaultedList)var4.next();
            if (slot < defaultedList.size()) {
                list = defaultedList;
                break;
            }
        }

        return list != null && !((ItemStack)list.get(slot)).isEmpty() ? Inventories.splitStack(list, slot, amount) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStack(int slot) {
        DefaultedList<ItemStack> defaultedList = null;

        DefaultedList defaultedList2;
        for(Iterator var3 = this.main.iterator(); var3.hasNext(); slot -= defaultedList2.size()) {
            defaultedList2 = (DefaultedList)var3.next();
            if (slot < defaultedList2.size()) {
                defaultedList = defaultedList2;
                break;
            }
        }

        if (defaultedList != null && !((ItemStack)defaultedList.get(slot)).isEmpty()) {
            ItemStack itemStack = (ItemStack)defaultedList.get(slot);
            defaultedList.set(slot, ItemStack.EMPTY);
            return itemStack;
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        main.set(slot, stack);
    }

    @Override
    public void markDirty() {
        ++this.changeCount;
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        for (int i = 0; i <= main.size() - 1; ++i) {
            main.set(i, ItemStack.EMPTY);
        }
    }
}
