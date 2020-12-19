package com.Morticia.armorplus.mixins.playerui;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.options.HotbarStorage;
import net.minecraft.client.options.HotbarStorageEntry;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;

import java.util.List;

@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInventoryScreenMixin extends AbstractInventoryScreen<CreativeInventoryScreen.CreativeScreenHandler> {
    public CreativeInventoryScreenMixin(CreativeInventoryScreen.CreativeScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }
    /*
    @Inject(method = "setSelectedTab", locals = LocalCapture.CAPTURE_FAILSOFT, at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/client/gui/screen/ingame/CreativeInventoryScreen$CreativeSlot;<init>(Lnet/minecraft/screen/slot/Slot;III)V"))
    private void newCreativeSlot(ItemGroup group, CallbackInfo ci, int i, ScreenHandler s, int l) {
        if (l <= 45 && l >= 42) {
            System.out.println("----Mixin Success----");
        }
    }
     */

    // Shadows, please don't judge me ik this is bad code I just hate injects
    @Shadow
    @Final
    @Mutable
    private static final SimpleInventory INVENTORY = new SimpleInventory(46);
    @Shadow
    private TextFieldWidget searchBox;
    @Shadow
    private static int selectedTab;
    @Shadow
    private float scrollPosition;
    @Shadow
    @Nullable
    private List<Slot> slots;
    @Shadow
    @Nullable
    private Slot deleteItemSlot;
    @Shadow
    private void search() {};

    /**
     * @author Morticia
     */
    @Overwrite
    private void setSelectedTab(ItemGroup group) {
        int i = selectedTab;
        selectedTab = group.getIndex();
        this.cursorDragSlots.clear();
        ((CreativeInventoryScreen.CreativeScreenHandler)this.handler).itemList.clear();
        int l;
        int aa;
        if (group == ItemGroup.HOTBAR) {
            HotbarStorage hotbarStorage = this.client.getCreativeHotbarStorage();

            for(l = 0; l < 9; ++l) {
                HotbarStorageEntry hotbarStorageEntry = hotbarStorage.getSavedHotbar(l);
                if (hotbarStorageEntry.isEmpty()) {
                    for(aa = 0; aa < 9; ++aa) {
                        if (aa == l) {
                            ItemStack itemStack = new ItemStack(Items.PAPER);
                            itemStack.getOrCreateSubTag("CustomCreativeLock");
                            Text text = this.client.options.keysHotbar[l].getBoundKeyLocalizedText();
                            Text text2 = this.client.options.keySaveToolbarActivator.getBoundKeyLocalizedText();
                            itemStack.setCustomName(new TranslatableText("inventory.hotbarInfo", new Object[]{text2, text}));
                            ((CreativeInventoryScreen.CreativeScreenHandler)this.handler).itemList.add(itemStack);
                        } else {
                            ((CreativeInventoryScreen.CreativeScreenHandler)this.handler).itemList.add(ItemStack.EMPTY);
                        }
                    }
                } else {
                    ((CreativeInventoryScreen.CreativeScreenHandler)this.handler).itemList.addAll(hotbarStorageEntry);
                }
            }
        } else if (group != ItemGroup.SEARCH) {
            group.appendStacks(((CreativeInventoryScreen.CreativeScreenHandler)this.handler).itemList);
        }

        if (group == ItemGroup.INVENTORY) {
            ScreenHandler screenHandler = this.client.player.playerScreenHandler;
            if (this.slots == null) {
                this.slots = ImmutableList.copyOf(((CreativeInventoryScreen.CreativeScreenHandler)this.handler).slots);
            }

            ((CreativeInventoryScreen.CreativeScreenHandler)this.handler).slots.clear();

            for(l = 0; l < screenHandler.slots.size(); ++l) {
                int t;
                int v;
                int w;
                int x;
                if (l >= 5 && l < 9) {
                    v = l - 9;
                    w = v % 9;
                    x = v / 9;
                    t = 9 + w * 18;
                    aa = 54 + x * 18;
                } else if (l >= 0 && l < 5) {
                    t = -2000;
                    aa = -2000;
                } else if (l == 42) {
                    t = 35;
                    aa = 20;
                }else if (l == 43) {
                    t = 35 - 18;
                    aa = 20;
                } else if (l == 44) {
                    t = 35 - 18 * 2;
                    aa = 20;
                } else if (l == 45) {
                    t = 35 - 18 * 3;
                    aa = 20;
                } else {
                    v = l - 9;
                    w = v % 9;
                    x = v / 9;
                    t = 9 + w * 18;
                    if (l >= 36) {
                        aa = 112;
                    } else {
                        aa = 54 + x * 18;
                    }
                }

                Slot slot = new CreativeInventoryScreen.CreativeSlot((Slot)screenHandler.slots.get(l), l, t, aa);
                ((CreativeInventoryScreen.CreativeScreenHandler)this.handler).slots.add(slot);
            }

            this.deleteItemSlot = new Slot(INVENTORY, 0, 173, 112);
            ((CreativeInventoryScreen.CreativeScreenHandler)this.handler).slots.add(this.deleteItemSlot);
        } else if (i == ItemGroup.INVENTORY.getIndex()) {
            ((CreativeInventoryScreen.CreativeScreenHandler)this.handler).slots.clear();
            ((CreativeInventoryScreen.CreativeScreenHandler)this.handler).slots.addAll(this.slots);
            this.slots = null;
        }

        if (this.searchBox != null) {
            if (group == ItemGroup.SEARCH) {
                this.searchBox.setVisible(true);
                this.searchBox.setFocusUnlocked(false);
                this.searchBox.setSelected(true);
                if (i != group.getIndex()) {
                    this.searchBox.setText("");
                }

                this.search();
            } else {
                this.searchBox.setVisible(false);
                this.searchBox.setFocusUnlocked(true);
                this.searchBox.setSelected(false);
                this.searchBox.setText("");
            }
        }

        this.scrollPosition = 0.0F;
        ((CreativeInventoryScreen.CreativeScreenHandler)this.handler).scrollItems(0.0F);
    }
}
