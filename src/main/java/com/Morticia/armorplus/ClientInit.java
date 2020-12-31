package com.Morticia.armorplus;

import com.Morticia.armorplus.FeatureRenderers.ArmorPlusFeatureRenderer;
import com.Morticia.armorplus.misc.OffhandSlot;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ClientInit implements ClientModInitializer {
    boolean settingX = false;

    private static KeyBinding offhandSlotCycleKeyBinding;
    private static KeyBinding setX;
    private static KeyBinding setY;
    private static KeyBinding up;
    private static KeyBinding down;

    @Override
    public void onInitializeClient() {
        KeyBindingRegistry.INSTANCE.addCategory("category.armorplus.keybindings");

        // Adds keybinding and keybinding behavior
        offhandSlotCycleKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.armorplus.cycleoffhand",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                "category.armorplus.keybindings"
        ));

        // For debug, remove later
        setX = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.armorplus.setX",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_J,
                "category.armorplus.keybindings"
        ));
        setY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.armorplus.setY",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_L,
                "category.armorplus.keybindings"
        ));
        up = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.armorplus.up",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_I,
                "category.armorplus.keybindings"
        ));
        down = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.armorplus.down",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_K,
                "category.armorplus.keybindings"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
            while (offhandSlotCycleKeyBinding.wasPressed()) {
                OffhandSlot.iterateActiveSlot();
            }
            while (setX.wasPressed()) {
                settingX = true;
                System.out.println("Setting X: " + settingX);
            }
            while (setY.wasPressed()) {
                settingX = false;
                System.out.println("Setting X: " + settingX);
            }
            while (up.wasPressed()) {
                if (settingX) {
                    ArmorPlusFeatureRenderer.cowlPos[0] += 0.03D;
                } else {
                    ArmorPlusFeatureRenderer.cowlPos[1] += 0.03D;
                }
                System.out.println("X: [" + ArmorPlusFeatureRenderer.cowlPos[0] + "]\nY: [" + ArmorPlusFeatureRenderer.cowlPos[1] + "]");
            }
            while (down.wasPressed()) {
                if (settingX) {
                    ArmorPlusFeatureRenderer.cowlPos[0] -= 0.03D;
                } else {
                    ArmorPlusFeatureRenderer.cowlPos[1] -= 0.03D;
                }
                System.out.println("X: [" + ArmorPlusFeatureRenderer.cowlPos[0] + "]\nY: [" + ArmorPlusFeatureRenderer.cowlPos[1] + "]");
            }
        });
    }
}
