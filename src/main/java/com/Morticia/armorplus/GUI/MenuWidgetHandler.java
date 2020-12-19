package com.Morticia.armorplus.GUI;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MenuWidgetHandler extends ScreenHandler {
    int x = 0;
    int y = 0;
    public MenuInventory inventory = new MenuInventory();

    public MenuWidgetHandler(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory player_inventory) {
        super(type, syncId);
    }

    public MenuWidgetHandler setCoords(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
