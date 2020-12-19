package com.Morticia.armorplus.GUI;

import com.Morticia.armorplus.item.GearType;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class MenuWidget extends AbstractInventoryScreen<MenuWidgetHandler> {
    // Texture for the menu
    private Identifier TEXTURE;
    // If this is open
    private boolean isOpen = false;
    // Rendering Stuff
    private final int borderAmount = 4;
    // For adding slots
    private PlayerScreenHandler playerScreenHandler;
    // For settings slot visibility
    private List<GearSlot> gearSlots = new ArrayList<>();


    public MenuWidget(Text title, PlayerInventory playerInventory) {
        super(new MenuWidgetHandler((ScreenHandlerType) null, 0, playerInventory), playerInventory, title);
    }

    public void init(Identifier texture, int parentX, int parentY, int textureWidth, MinecraftClient client, PlayerScreenHandler playerScreenHandler) {
        gearSlots = new ArrayList<>();

        this.y = parentY;
        this.x = (parentX - textureWidth) - borderAmount;
        this.TEXTURE = texture;
        this.client = client;
        this.playerScreenHandler = playerScreenHandler;

        this.playerScreenHandler.addSlot(new GearSlot(this.playerInventory, 46, this.x - 8, this.y + 8, GearType.CAWL));

        super.init();
    }

    public void toggleOpen(boolean conflictableWidgetOpen) {
        if (!conflictableWidgetOpen) {
            this.isOpen = !this.isOpen;
            this.setSlotsVisible();
        }
    }

    public void setSlotsVisible() {
        ((GearSlot) this.playerScreenHandler.slots.get(this.playerScreenHandler.slots.size() - 1)).setVisible(this.isOpen);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (this.isOpen) {
            try {
                //super.render(matrices, mouseX, mouseY, delta);
                drawMouseoverTooltip(matrices, mouseX, mouseY);
                //super.render(matrices, mouseX, mouseY, delta);
            } catch (Exception e) {

            }
            RenderSystem.pushMatrix();
            RenderSystem.translatef(0.0F, 0.0F, 100.0F);
            this.client.getTextureManager().bindTexture(TEXTURE);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexture(matrices, this.x, this.y, 1, 1, 90, 89);
            RenderSystem.popMatrix();
        }
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {

    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {

    }

    @Override
    protected void applyStatusEffectOffset() {

    }
}
