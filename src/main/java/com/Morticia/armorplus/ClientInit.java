package com.Morticia.armorplus;

import com.Morticia.armorplus.misc.OffhandSlot;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ClientInit implements ClientModInitializer {
    private static KeyBinding offhandSlotCycleKeyBinding;

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

        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
            while (offhandSlotCycleKeyBinding.wasPressed()) {
                OffhandSlot.iterateActiveSlot();
            }
        });
    }
}
