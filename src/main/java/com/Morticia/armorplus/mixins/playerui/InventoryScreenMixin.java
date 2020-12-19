package com.Morticia.armorplus.mixins.playerui;

import com.Morticia.armorplus.GUI.GearSlot;
import com.Morticia.armorplus.GUI.MenuWidget;
import com.Morticia.armorplus.item.GearType;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends AbstractInventoryScreen<PlayerScreenHandler> {
    @Shadow private boolean mouseDown;
    private static final Identifier AUGMENTS_BUTTON_TEXTURE = new Identifier("armorplus", "textures/gui/augments_button.png");
    private static final Identifier LEVEL_BUTTON_TEXTURE = new Identifier("armorplus", "textures/gui/level_button.png");
    private static final Identifier EQUIPMENT_BUTTON_TEXTURE = new Identifier("armorplus", "textures/gui/equipment_button.png");

    private static final Identifier EQUIPMENT_MENU_TEXTURE = new Identifier("armorplus", "textures/gui/container/equipment_menu.png");

    private final MenuWidget EquipmentMenu = new MenuWidget(new TranslatableText("key.armorplus.menu_widget.equipment_menu"), this.playerInventory);

    public InventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        EquipmentMenu.init(EQUIPMENT_MENU_TEXTURE, this.x, this.y, 90, this.client, this.handler);

        //this.addChild(EquipmentMenu);

        assert this.client != null;
        assert this.client.interactionManager != null;
        if (!this.client.interactionManager.hasCreativeInventory()) {
            this.addButton(new TexturedButtonWidget(this.x + 4, this.y + 53, 20, 18, 0, 0, 19, AUGMENTS_BUTTON_TEXTURE, (button) -> {
                System.out.println("Augments Button Pressed");
            }));
            this.addButton(new TexturedButtonWidget(this.x + 4, this.y + 35, 20, 18, 0, 0, 19, LEVEL_BUTTON_TEXTURE, (button) -> {
                System.out.println("Level Button Pressed");
            }));
            this.setInitialFocus(this.EquipmentMenu);
            this.addButton(new TexturedButtonWidget(this.x + 4, this.y + 17, 20, 18, 0, 0, 19, EQUIPMENT_BUTTON_TEXTURE, (button) -> {
                EquipmentMenu.toggleOpen(false);
                this.mouseDown = true;
            }));
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void renderInject(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        EquipmentMenu.render(matrices, mouseX, mouseY, delta);
    }
}
