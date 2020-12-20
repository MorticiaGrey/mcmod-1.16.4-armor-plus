package com.Morticia.armorplus.GUI;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class MenuWidget extends Screen {
    // Texture for the menu
    private Identifier TEXTURE;
    // If this is open
    public boolean isOpen = false;
    // Rendering Stuff
    private final int borderAmount = 4;
    // For adding slots
    private PlayerScreenHandler playerScreenHandler;
    // Location
    int x = 0;
    int y = 0;


    public MenuWidget(Text title) {
        super(title);
        //super(new MenuWidgetHandler((ScreenHandlerType) null, 0, playerInventory), playerInventory, title);
    }

    public void init(Identifier texture, int parentX, int parentY, int textureWidth, int textureHeight, MinecraftClient client, PlayerScreenHandler playerScreenHandler) {
        GUICoords.invX = parentX;
        GUICoords.invY = parentY;

        this.y = parentY;
        this.x = (parentX - textureWidth) - borderAmount;
        this.TEXTURE = texture;
        this.playerScreenHandler = playerScreenHandler;
        this.client = client;
        this.width = textureWidth;
        this.height = textureHeight;
    }

    // This is called on button press, it toggles the texture one and off and the slots
    public void toggleOpen(boolean conflictingWidgetOpen) {
        if (!conflictingWidgetOpen) {
            this.isOpen = !this.isOpen;
            this.setSlotsVisible();
        }
    }

    // This sets the slots to visible/invisible
    public void setSlotsVisible() {
        ((GearSlot) this.playerScreenHandler.slots.get(this.playerScreenHandler.slots.size() - 1)).setVisible(this.isOpen);
    }

    // This is called in the InventoryScreen#render method, it draws the texture 4 px to the the left of the
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (this.isOpen) {
            try {
                //drawMouseoverTooltip(matrices, mouseX, mouseY);
            } catch (Exception ignored) {

            }
            RenderSystem.pushMatrix();
            RenderSystem.translatef(0.0F, 0.0F, 100.0F);
            this.client.getTextureManager().bindTexture(TEXTURE);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexture(matrices, this.x, this.y, 1, 1, this.width, this.height);
            RenderSystem.popMatrix();
        }
    }


    // No Background or foreground to draw
    //@Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {

    }

    //@Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {

    }
}
