package io.enderdev.endermodpacktweaks.mixin.bpopener;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.bluebeaker.bpopener.BPOHandler;
import io.bluebeaker.bpopener.BPOpenerConfig;
import io.bluebeaker.bpopener.BPOpenerMod;
import io.bluebeaker.bpopener.OpenAction;
import io.enderdev.endermodpacktweaks.EMTConfig;
import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.lwjgl.input.Mouse;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@Mixin(value = BPOHandler.class, remap = false)
public abstract class BPOHandlerMixin {
    @Shadow
    static int lastSlot1;

    @Shadow
    static int lastSlot2;

    @Shadow
    static boolean activated;

    @Shadow
    static boolean previousSneaking;

    @Shadow
    private static Minecraft mc;

    @Shadow
    private static boolean isSlotValidToSwap(Slot slot, GuiScreen screen) {
        return false;
    }

    @Shadow
    private static void doSwap(int windowId, int slot1, int slot2) {
    }

    @Shadow
    private static void setPlayerSneakState(boolean sneak) {
    }

    @Unique
    private static HashMap<Item, OpenAction> enderModpackTweaks$bpoMap = new HashMap<Item, OpenAction>() {
        {
            Arrays.asList(EMTConfig.BP_OPENER.validItems).forEach(item -> {
                String[] input = item.split(";");
                if (input.length == 2) {
                    Item item1 = Item.getByNameOrId(input[0]);
                    if (item1 != null) {
                        OpenAction action = Boolean.parseBoolean(input[1]) ? OpenAction.SNEAK_USE : OpenAction.USE;
                        put(item1, action);
                    }
                }
            });
        }
    };

    @WrapMethod(method = "onRightClick")
    private static void onRightClickWrap(GuiScreenEvent.MouseInputEvent.Pre event, Operation<Void> original) {
        if (!activated && !GuiScreen.isShiftKeyDown()) {
            if (Mouse.getEventButtonState() && Mouse.getEventButton() == 1) {
                GuiScreen screen = event.getGui();
                if (screen instanceof GuiContainer) {
                    GuiContainer container = (GuiContainer)screen;
                    Slot slot = container.getSlotUnderMouse();
                    EntityPlayerSP player = mc.player;
                    if (slot != null && slot.inventory == player.inventory) {
                        if (isSlotValidToSwap(slot, screen)) {
                            ItemStack stack = slot.getStack();
                            if (stack != null && stack.getCount() <= 1) {
                                OpenAction action = enderModpackTweaks$bpoMap.get(stack.getItem());
                                if (action != null) {
                                    lastSlot1 = slot.getSlotIndex();
                                    lastSlot2 = player.inventory.currentItem;
                                    if (BPOpenerConfig.debug) {
                                        BPOpenerMod.getLogger().info("Attempt to swap slots {} slotNumber {} with hotbar {} in gui {}", slot.getSlotIndex(), slot.slotNumber, lastSlot2, container.getClass().getName());
                                    }

                                    int index1 = !(screen instanceof GuiInventory) && !(screen instanceof GuiContainerCreative) ? slot.slotNumber : slot.getSlotIndex();
                                    if (lastSlot1 < 9) {
                                        player.inventory.currentItem = lastSlot1;
                                    } else {
                                        doSwap(container.inventorySlots.windowId, index1, player.inventory.currentItem);
                                    }

                                    if (player.inventory.getCurrentItem() != stack) {
                                        doSwap(container.inventorySlots.windowId, index1, player.inventory.currentItem);
                                    } else {
                                        activated = true;
                                        boolean shouldSneak = action.isSneaking();
                                        previousSneaking = player.movementInput.sneak;
                                        if (shouldSneak != previousSneaking) {
                                            setPlayerSneakState(shouldSneak);
                                        }

                                        mc.playerController.processRightClick(player, mc.world, EnumHand.MAIN_HAND);
                                        if (shouldSneak != previousSneaking) {
                                            setPlayerSneakState(previousSneaking);
                                        }

                                        event.setCanceled(true);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @WrapMethod(method = "addTooltip")
    private static void addTooltipWrap(ItemTooltipEvent event, Operation<Void> original) {
        if (!activated && !GuiScreen.isShiftKeyDown()) {
            ItemStack stack = event.getItemStack();
            EntityPlayer player = event.getEntityPlayer();
            if (stack != null && player != null) {
                GuiScreen screen = mc.currentScreen;
                if (screen instanceof GuiContainer) {
                    Slot slot = ((GuiContainer)screen).getSlotUnderMouse();
                    if (slot != null && slot.inventory == player.inventory) {
                        if (isSlotValidToSwap(slot, screen)) {
                            if (enderModpackTweaks$bpoMap.get(stack.getItem()) != null) {
                                event.getToolTip().add((new TextComponentTranslation("tooltip.bpopener.open.name")).getFormattedText());
                            }
                        }
                    }
                }
            }
        }
    }
}
