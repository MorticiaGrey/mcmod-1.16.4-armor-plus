package com.Morticia.armorplus.GUI;

import com.Morticia.armorplus.item.PlayerStats;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class MenuWidget extends Screen {
    // Texture for the menu
    private Identifier TEXTURE;
    // If this is open
    public boolean isOpen = false;
    // Rendering Stuff
    private final int borderAmount = 4;
    // Location
    int x = 0;
    int y = 0;
    // Does what it says on the tin
    boolean canOpen = true;

    CustomSlotsRegistry.SlotCategory category;


    public MenuWidget(Text title, CustomSlotsRegistry.SlotCategory category) {
        super(title);
        this.category = category;
    }

    public void init(Identifier texture, int parentX, int parentY, int textureWidth, int textureHeight, MinecraftClient client) {
        GUICoords.invX = parentX;
        GUICoords.invY = parentY;

        if (category == CustomSlotsRegistry.SlotCategory.EQUIPMENT) {
            this.y = parentY;
            this.x = (parentX - textureWidth) - borderAmount;
        } else if (category == CustomSlotsRegistry.SlotCategory.BACKPACK) {
            this.y = parentY;
            this.x = (parentX + 179) + borderAmount;
        } else if (category == CustomSlotsRegistry.SlotCategory.AUGMENTS) {
            this.y = parentY;
            this.x = (parentX - textureWidth) - borderAmount;
        }
        this.TEXTURE = texture;
        this.client = client;
        this.width = textureWidth;
        this.height = textureHeight;
    }

    // This is called on button press, it toggles the texture one and off and the slots
    public void toggleOpen(boolean conflictingWidgetOpen) {
        if (!conflictingWidgetOpen) {
            this.isOpen = !this.isOpen && canOpen;
            this.setSlotsVisible();
        }
    }

    public void toggleCanOpen() {
        this.canOpen = !this.canOpen;
    }

    public void setOpen(boolean open) {
        this.isOpen = open && canOpen;
        this.setSlotsVisible();
    }

    // This sets the slots to visible/invisible
    private void setSlotsVisible() {
        if (category == CustomSlotsRegistry.SlotCategory.EQUIPMENT) {
            for (int i = 0; i < CustomSlotsRegistry.equipmentSlots.size(); ++i) {
                ((SlotChangingVisibility) CustomSlotsRegistry.equipmentSlots.get(i)).setVisible(this.isOpen);
            }
        }
        if (category == CustomSlotsRegistry.SlotCategory.AUGMENTS) {
            for (int i = 0; i < CustomSlotsRegistry.augmentSlots.size(); ++i) {
                ((SlotChangingVisibility) CustomSlotsRegistry.augmentSlots.get(i)).setVisible(this.isOpen);
            }
        }
        if (!(CustomSlotsRegistry.equipmentSlots.get(CustomSlotsRegistry.equipmentSlots.size() - 1).getStack().getItem() instanceof PlayerStats.Backpack)) {
            setAllBackpackSlotsVisible(false);
            return;
        }
        if (category == CustomSlotsRegistry.SlotCategory.BACKPACK) {
            int backpackTier = ((PlayerStats.Backpack) CustomSlotsRegistry.equipmentSlots.get(CustomSlotsRegistry.equipmentSlots.size() - 1).getStack().getItem()).getTier();
            if (backpackTier == 4) {
                for (int i = 0; i < CustomSlotsRegistry.backpackSlots.size(); ++i) {
                    ((SlotChangingVisibility) CustomSlotsRegistry.backpackSlots.get(i)).setVisible(this.isOpen);
                }
            } else if (backpackTier == 3) {
                for (int i = 0; i < CustomSlotsRegistry.backpackSlots.size() - 3; ++i) {
                    ((SlotChangingVisibility) CustomSlotsRegistry.backpackSlots.get(i)).setVisible(this.isOpen);
                }
            } else if (backpackTier == 2) {
                for (int i = 0; i < CustomSlotsRegistry.backpackSlots.size() - 6; ++i) {
                    ((SlotChangingVisibility) CustomSlotsRegistry.backpackSlots.get(i)).setVisible(this.isOpen);
                }
            } else if (backpackTier == 1) {
                for (int i = 0; i < CustomSlotsRegistry.backpackSlots.size() - 9; ++i) {
                    ((SlotChangingVisibility) CustomSlotsRegistry.backpackSlots.get(i)).setVisible(this.isOpen);
                }
            }
        }
    }

    public static void setAllBackpackSlotsVisible(boolean visibility) {
        for (int i = 0; i < CustomSlotsRegistry.backpackSlots.size(); ++i) {
            ((SlotChangingVisibility) CustomSlotsRegistry.backpackSlots.get(i)).setVisible(visibility);
        }
    }

    // This is called in the InventoryScreen#render method, it draws the texture 4 px to the the left of the
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        updateBackpackVisibility();
        if (this.isOpen) {
            RenderSystem.pushMatrix();
            RenderSystem.translatef(0.0F, 0.0F, 100.0F);
            assert this.client != null;
            this.client.getTextureManager().bindTexture(TEXTURE);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexture(matrices, this.x, this.y, 1, 1, this.width, this.height);
            RenderSystem.popMatrix();
        }
    }

    private void updateBackpackVisibility() {
        if (this.category == CustomSlotsRegistry.SlotCategory.BACKPACK && CustomSlotsRegistry.equipmentSlots.get(CustomSlotsRegistry.equipmentSlots.size() - 1).getStack().getItem() == ItemStack.EMPTY.getItem()) {
            setOpen(false);
        }
    }
}
