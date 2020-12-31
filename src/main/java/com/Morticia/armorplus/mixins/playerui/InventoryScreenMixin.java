package com.Morticia.armorplus.mixins.playerui;

import com.Morticia.armorplus.GUI.CustomSlotsRegistry;
import com.Morticia.armorplus.GUI.MenuWidget;
import com.Morticia.armorplus.item.PlayerStats;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.atomic.AtomicReference;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends AbstractInventoryScreen<PlayerScreenHandler> {
    @Shadow private boolean mouseDown;
    private static final Identifier AUGMENTS_BUTTON_TEXTURE = new Identifier("armorplus", "textures/gui/augments_button.png");
    private static final Identifier LEVEL_BUTTON_TEXTURE = new Identifier("armorplus", "textures/gui/level_button.png");
    private static final Identifier EQUIPMENT_BUTTON_TEXTURE = new Identifier("armorplus", "textures/gui/equipment_button.png");

    private static final Identifier EQUIPMENT_MENU_TEXTURE = new Identifier("armorplus", "textures/gui/container/equipment_menu.png");
    private static final Identifier AUGMENTS_MENU_TEXTURE = new Identifier("armorplus", "textures/gui/container/augments_menu.png");

    private static final Identifier BACKPACK1_MENU_TEXTURE = new Identifier("armorplus", "textures/gui/container/backpack_inventory_1.png");
    private static final Identifier BACKPACK2_MENU_TEXTURE = new Identifier("armorplus", "textures/gui/container/backpack_inventory_2.png");
    private static final Identifier BACKPACK3_MENU_TEXTURE = new Identifier("armorplus", "textures/gui/container/backpack_inventory_3.png");
    private static final Identifier BACKPACK4_MENU_TEXTURE = new Identifier("armorplus", "textures/gui/container/backpack_inventory_4.png");

    private final MenuWidget EquipmentMenu = new MenuWidget(new TranslatableText("key.armorplus.menu_widget.equipment_menu"), CustomSlotsRegistry.SlotCategory.EQUIPMENT);
    private final MenuWidget AugmentsMenu = new MenuWidget(new TranslatableText("key.armorplus.menu_widget.augments_menu"), CustomSlotsRegistry.SlotCategory.AUGMENTS);

    private final MenuWidget BackpackMenu1 = new MenuWidget(new TranslatableText("key.armorplus.menu_widget.backpack_menu_1"), CustomSlotsRegistry.SlotCategory.BACKPACK);
    private final MenuWidget BackpackMenu2 = new MenuWidget(new TranslatableText("key.armorplus.menu_widget.backpack_menu_2"), CustomSlotsRegistry.SlotCategory.BACKPACK);
    private final MenuWidget BackpackMenu3 = new MenuWidget(new TranslatableText("key.armorplus.menu_widget.backpack_menu_3"), CustomSlotsRegistry.SlotCategory.BACKPACK);
    private final MenuWidget BackpackMenu4 = new MenuWidget(new TranslatableText("key.armorplus.menu_widget.backpack_menu_4"), CustomSlotsRegistry.SlotCategory.BACKPACK);

    private int xOffset = 104;
    private boolean craftingBookOpen = false;

    private TexturedButtonWidget augmentButton;
    private TexturedButtonWidget equipmentButton;
    private TexturedButtonWidget levelButton;

    public InventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        augmentButton = new TexturedButtonWidget(this.x + 4, this.y + 53, 20, 18, 0, 0, 19, AUGMENTS_BUTTON_TEXTURE, (button) -> {
            this.AugmentsMenu.toggleOpen(this.EquipmentMenu.isOpen);
            this.mouseDown = true;
        });

        equipmentButton = new TexturedButtonWidget(this.x + 4, this.y + 17, 20, 18, 0, 0, 19, EQUIPMENT_BUTTON_TEXTURE, (button) -> {
            this.EquipmentMenu.toggleOpen(this.AugmentsMenu.isOpen);
            this.mouseDown = true;
        });

        levelButton = new TexturedButtonWidget(this.x + 4, this.y + 35, 20, 18, 0, 0, 19, LEVEL_BUTTON_TEXTURE, (button) -> {
            System.out.println("Level Button Pressed");
        });

        this.EquipmentMenu.init(EQUIPMENT_MENU_TEXTURE, this.x, this.y, 90, 89, this.client);
        this.AugmentsMenu.init(AUGMENTS_MENU_TEXTURE, this.x, this.y, 31, 67, this.client);

        this.BackpackMenu1.init(BACKPACK1_MENU_TEXTURE, this.x, this.y, 31, 66, this.client);
        this.BackpackMenu2.init(BACKPACK2_MENU_TEXTURE, this.x, this.y, 49, 66, this.client);
        this.BackpackMenu3.init(BACKPACK3_MENU_TEXTURE, this.x, this.y, 66, 66, this.client);
        this.BackpackMenu4.init(BACKPACK4_MENU_TEXTURE, this.x, this.y, 85, 67, this.client);

        assert this.client != null;
        assert this.client.interactionManager != null;
        if (!this.client.interactionManager.hasCreativeInventory()) {
            this.addButton(augmentButton);
            this.addButton(equipmentButton);
            this.addButton(levelButton);
        }
    }

    // Moves the buttons when the crafting book is opened
    @Inject(method = "method_19891", at = @At(value = "TAIL"))
    public void initInject2(CallbackInfo ci) {
        craftingBookOpen = this.recipeBook.isOpen();

        if (craftingBookOpen) {
            augmentButton.x = augmentButton.x + 77;
            equipmentButton.x = equipmentButton.x + 77;
            levelButton.x = levelButton.x + 77;
        } else {
            augmentButton.x = augmentButton.x - 77;
            equipmentButton.x = equipmentButton.x - 77;
            levelButton.x = levelButton.x - 77;
        }


        toggleMenusCanOpen();
        if (this.EquipmentMenu.isOpen) {
            EquipmentMenu.toggleOpen(false);
        }
        if (this.AugmentsMenu.isOpen) {
            AugmentsMenu.toggleOpen(false);
        }
        if (this.BackpackMenu1.isOpen) {
            BackpackMenu1.toggleOpen(false);
        }
        if (this.BackpackMenu2.isOpen) {
            BackpackMenu2.toggleOpen(false);
        }
        if (this.BackpackMenu3.isOpen) {
            BackpackMenu3.toggleOpen(false);
        }
        if (this.BackpackMenu4.isOpen) {
            BackpackMenu4.toggleOpen(false);
        }
    }

    private void toggleMenusCanOpen() {
        this.AugmentsMenu.toggleCanOpen();
        this.EquipmentMenu.toggleCanOpen();
        this.BackpackMenu1.toggleCanOpen();
        this.BackpackMenu2.toggleCanOpen();
        this.BackpackMenu3.toggleCanOpen();
        this.BackpackMenu4.toggleCanOpen();
    }

    public void setAllBackpackVisibility(boolean visibility) {
        BackpackMenu1.setOpen(visibility);
        BackpackMenu2.setOpen(visibility);
        BackpackMenu3.setOpen(visibility);
        BackpackMenu4.setOpen(visibility);
    }

    // This is injected here and not the render function so it draws in the correct order, on top of the background but under the slots
    @Inject(method = "drawBackground", at = @At("HEAD"))
    private void drawBackgroundInject(MatrixStack matrices, float delta, int mouseX, int mouseY, CallbackInfo ci) {
        switch (getCurrentBackpackTier()) {
            case 1:
                setAllBackpackVisibility(false);
                BackpackMenu1.setOpen(true);
                break;
            case 2:
                setAllBackpackVisibility(false);
                BackpackMenu2.setOpen(true);
                break;
            case 3:
                setAllBackpackVisibility(false);
                BackpackMenu3.setOpen(true);
                break;
            case 4:
                setAllBackpackVisibility(false);
                BackpackMenu4.setOpen(true);
                break;
            case 0:
                setAllBackpackVisibility(false);
                break;
        }

        EquipmentMenu.render(matrices, mouseX, mouseY, delta);
        AugmentsMenu.render(matrices, mouseX, mouseY, delta);
        BackpackMenu1.render(matrices, mouseX, mouseY, delta);
        BackpackMenu2.render(matrices, mouseX, mouseY, delta);
        BackpackMenu3.render(matrices, mouseX, mouseY, delta);
        BackpackMenu4.render(matrices, mouseX, mouseY, delta);
    }

    private int getCurrentBackpackTier() {
        if (CustomSlotsRegistry.equipmentSlots.get(CustomSlotsRegistry.equipmentSlots.size() - 1).getStack().getItem() != ItemStack.EMPTY.getItem()) {
            return ((PlayerStats.Backpack) CustomSlotsRegistry.equipmentSlots.get(CustomSlotsRegistry.equipmentSlots.size() - 1).getStack().getItem()).getTier();
        } else {
            return 0;
        }
    }

    // Cleans up the custom UI when inv is closed
    @Inject(method = "removed", at = @At("HEAD"))
    private void closeInject(CallbackInfo ci) {
        if (this.EquipmentMenu.isOpen) {
            EquipmentMenu.toggleOpen(false);
        }
        if (this.AugmentsMenu.isOpen) {
            AugmentsMenu.toggleOpen(false);
        }
        if (this.BackpackMenu1.isOpen) {
            BackpackMenu1.toggleOpen(false);
        }
        if (this.BackpackMenu2.isOpen) {
            BackpackMenu2.toggleOpen(false);
        }
        if (this.BackpackMenu3.isOpen) {
            BackpackMenu3.toggleOpen(false);
        }
        if (this.BackpackMenu4.isOpen) {
            BackpackMenu4.toggleOpen(false);
        }

        this.EquipmentMenu.removed();
        this.AugmentsMenu.removed();
        this.BackpackMenu1.removed();
        this.BackpackMenu2.removed();
        this.BackpackMenu3.removed();
        this.BackpackMenu4.removed();
    }

    @Shadow
    @Final
    private final RecipeBookWidget recipeBook = new RecipeBookWidget();

    /**
     * Changes isClickOutsideBound so it also includes the custom ui
     * @author Morticia
     */
    @Overwrite
    public boolean isClickOutsideBounds(double mouseX, double mouseY, int left, int top, int button) {
        boolean bl = mouseX < (double) left || mouseY < (double) top || mouseX >= (double) (left + this.backgroundWidth) || mouseY >= (double) (top + this.backgroundHeight);
        if (AugmentsMenu.isOpen) {
            bl = mouseX < (double) left - this.AugmentsMenu.width || mouseY < (double) top || mouseX >= (double) (left + this.backgroundWidth) || mouseY >= (double) (top + this.backgroundHeight);
        } else if (EquipmentMenu.isOpen) {
            bl = mouseX < (double) left - this.EquipmentMenu.width || mouseY < (double) top || mouseX >= (double) (left + this.backgroundWidth) || mouseY >= (double) (top + this.backgroundHeight);
        } else if (BackpackMenu1.isOpen) {
            bl = mouseX < (double) left - this.EquipmentMenu.width || mouseY < (double) top || mouseX >= (double) (left + this.backgroundWidth + this.BackpackMenu1.width) || mouseY >= (double) (top + this.backgroundHeight);
        } else if (BackpackMenu2.isOpen) {
            bl = mouseX < (double) left - this.EquipmentMenu.width || mouseY < (double) top || mouseX >= (double) (left + this.backgroundWidth + this.BackpackMenu2.width) || mouseY >= (double) (top + this.backgroundHeight);
        } else if (BackpackMenu3.isOpen) {
            bl = mouseX < (double) left - this.EquipmentMenu.width || mouseY < (double) top || mouseX >= (double) (left + this.backgroundWidth + this.BackpackMenu3.width) || mouseY >= (double) (top + this.backgroundHeight);
        } else if (BackpackMenu4.isOpen) {
            bl = mouseX < (double) left - this.EquipmentMenu.width || mouseY < (double) top || mouseX >= (double) (left + this.backgroundWidth + this.BackpackMenu4.width) || mouseY >= (double) (top + this.backgroundHeight);
        }
        return this.recipeBook.isClickOutsideBounds(mouseX, mouseY, this.x, this.y, this.backgroundWidth, this.backgroundHeight, button) && bl;
    }
}
