package io.enderdev.endermodpacktweaks.features.keybinds;

import com.google.common.collect.Lists;
import com.wynprice.noctrl.GuiSelectList;
import com.wynprice.noctrl.KeyBindSet;
import com.wynprice.noctrl.NoCtrl;
import io.enderdev.endermodpacktweaks.config.CfgFeatures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class NoCtrlIntegration extends GuiNewControls{
    private GuiSelectList dropDown;
    private GuiTextField inputField;
    private GuiButton addFolder;
    private GuiButton removeFolder;
    private GuiButton renameFolder;
    private GuiButton editIcon;
    private GuiButton yesAction;
    private GuiButton noAction;
    private boolean showInputField;
    private boolean yesNoScreen;
    @Nullable
    private Consumer<String> output;
    private String currentTask = "";

    public NoCtrlIntegration(GuiScreen screen, GameSettings settings) {
        super(screen, settings);
    }

    public void initGui() {
        super.initGui();
        GuiKeyBindingList var10000 = this.keyBindingList;
        var10000.top += 27;
        this.dropDown = new GuiSelectList(this.width / 2 - 155, 66);
        this.inputField = new GuiTextField(5000, this.mc.fontRenderer, this.width / 2 - 155, 66, 150, 20);
        this.inputField.setCanLoseFocus(false);
        this.inputField.setFocused(true);
        this.inputField.setMaxStringLength(12);
        this.addFolder = this.addButton(new GuiTypeButton(5001, this.width / 2 + 5, 66, 20, 20, TextFormatting.GREEN + "+", "add", (s) -> NoCtrl.addAndSetCurrent(NoCtrl.ACTIVE.copy().rename(s)), new String[]{I18n.format("noctrl.gui.add.desc1", new Object[0]), I18n.format("noctrl.gui.add.desc2", new Object[0])}));
        this.removeFolder = this.addButton(new GuiTypeButton(5002, this.width / 2 + 30, 66, 20, 20, TextFormatting.RED + "-", "", (s) -> NoCtrl.ACTIVE.delete(), new String[]{I18n.format("noctrl.gui.delete.desc", new Object[0])}));
        this.renameFolder = this.addButton(new GuiTypeButton(5003, this.width / 2 + 55, 66, 20, 20, TextFormatting.YELLOW + I18n.format("noctrl.gui.rename", new Object[0]), "rename", (s) -> NoCtrl.addAndSetCurrent(NoCtrl.ACTIVE.rename(s)), new String[]{I18n.format("noctrl.gui.rename.desc", new Object[0])}));
        this.editIcon = this.addButton(new GuiTypeButton(5003, this.width / 2 + 80, 66, 20, 20, TextFormatting.BLUE + I18n.format("noctrl.gui.icon", new Object[0]), "icon", (s) -> NoCtrl.ACTIVE.setModel((Item) ForgeRegistries.ITEMS.getValue(new ResourceLocation(s))), new String[]{I18n.format("noctrl.gui.icon.desc", new Object[0])}));
        this.yesAction = this.addButton(new GuiTooltipButton(5101, this.width / 2 + 5, 66, 20, 20, TextFormatting.GREEN + I18n.format("noctrl.gui.yes", new Object[0]), new String[]{I18n.format("noctrl.gui.yes.desc", new Object[0])}));
        this.noAction = this.addButton(new GuiTooltipButton(5102, this.width / 2 + 30, 66, 20, 20, TextFormatting.RED + I18n.format("noctrl.gui.no", new Object[0]), new String[]{I18n.format("noctrl.gui.no.desc", new Object[0])}));
        this.yesAction.visible = this.yesAction.enabled = false;
        this.noAction.visible = this.noAction.enabled = false;
    }

    protected void actionPerformed(GuiButton button) {
        super.actionPerformed(button);
        if (button.id > 5100) {
            if (button.id == 5101 && this.output != null && this.yesNoScreen && (!this.showInputField || !this.inputField.getText().trim().isEmpty())) {
                this.output.accept(this.inputField.getText().trim());
                this.inputField.setText("");
            }

            this.currentTask = "";
            this.output = null;
            this.showInputField = false;
            this.yesNoScreen = false;
        } else if (button.id > 5000 && button instanceof GuiTypeButton) {
            this.showInputField = button.id != 5002;
            this.yesNoScreen = true;
            this.output = ((GuiTypeButton)button).out;
            this.currentTask = ((GuiTypeButton)button).taskKey;
        }

    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (this.showInputField) {
            this.inputField.drawTextBox();
        } else {
            this.dropDown.render(mouseX, mouseY);
        }

        if (this.showInputField && mouseX >= this.inputField.x && mouseY >= this.inputField.y && mouseX < this.inputField.x + this.inputField.width && mouseY < this.inputField.y + this.inputField.height && !this.currentTask.isEmpty()) {
            this.drawHoveringText(I18n.format("noctrl.gui.task." + this.currentTask, new Object[0]), mouseX, mouseY);
        } else {
            for(GuiButton guiButton : this.buttonList) {
                if (guiButton.enabled && guiButton instanceof GuiTooltipButton && guiButton.isMouseOver()) {
                    this.drawHoveringText(((GuiTooltipButton)guiButton).tooltips, mouseX, mouseY);
                }
            }
        }

    }

    public void updateScreen() {
        this.yesAction.visible = this.yesAction.enabled = this.yesNoScreen;
        this.noAction.visible = this.noAction.enabled = this.yesNoScreen;
        this.addFolder.visible = this.addFolder.enabled = !this.yesNoScreen;
        this.removeFolder.visible = this.removeFolder.enabled = !this.yesNoScreen;
        this.renameFolder.visible = this.renameFolder.enabled = !this.yesNoScreen;
        this.editIcon.visible = this.editIcon.enabled = !this.yesNoScreen;
        if (NoCtrl.ACTIVE == KeyBindSet.DEFAULT) {
            this.removeFolder.enabled = this.renameFolder.enabled = false;
        }

        super.updateScreen();
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        boolean flag = this.buttonId != null;
        if (!flag) {
            LinkedList<GuiListExtended.IGuiListEntry> listEntries = (LinkedList) ReflectionHelper.getPrivateValue(GuiNewKeyBindingList.class, (GuiNewKeyBindingList)this.keyBindingList, new String[]{"listEntries"});

            for(int i = 0; i < listEntries.size(); ++i) {
                GuiListExtended.IGuiListEntry entry = this.keyBindingList.getListEntry(i);
                if (entry instanceof GuiNewKeyBindingList.KeyEntry && ((GuiButton)ReflectionHelper.getPrivateValue(GuiNewKeyBindingList.KeyEntry.class, (GuiNewKeyBindingList.KeyEntry)entry, new String[]{"btnReset"})).mousePressed(this.mc, mouseX, mouseY)) {
                    flag = true;
                    break;
                }
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.showInputField) {
            this.inputField.mouseClicked(mouseX, mouseY, mouseButton);
        } else {
            this.dropDown.mouseClicked(mouseX, mouseY, mouseButton);
        }

        if (flag) {
            this.saveKeyBinds();
        }

    }

    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == 28 && this.output != null) {
            this.output.accept(this.inputField.getText().trim());
            this.inputField.setText("");
            this.showInputField = false;
            this.yesNoScreen = false;
        } else {
            boolean flag = this.buttonId != null;
            super.keyTyped(typedChar, keyCode);
            if (this.showInputField) {
                this.inputField.textboxKeyTyped(typedChar, keyCode);
            }

            if (flag) {
                this.saveKeyBinds();
            }
        }

    }

    private void saveKeyBinds() {
        for(KeyBinding keyBinding : this.mc.gameSettings.keyBindings) {
            NoCtrl.ACTIVE.putOverride(keyBinding, keyBinding.getKeyCode());
        }

    }

    @SubscribeEvent(
        priority = EventPriority.LOWEST
    )
    public static void applyControlling(GuiOpenEvent event) {
        if (event.getGui() != null && event.getGui() instanceof GuiControls && CfgFeatures.IMPROVED_KEYBINDS.enable) {
            event.setGui(new NoCtrlIntegration(((GuiControls)event.getGui()).parentScreen, Minecraft.getMinecraft().gameSettings));
        }

    }

    private static class GuiTooltipButton extends GuiButtonExt {
        private final List<String> tooltips;

        public GuiTooltipButton(int id, int xPos, int yPos, int width, int height, String displayString, String... tooltips) {
            super(id, xPos, yPos, width, height, displayString);
            this.tooltips = Lists.newArrayList(tooltips);
        }
    }

    private static class GuiTypeButton extends GuiTooltipButton {
        private final String taskKey;
        private final Consumer<String> out;

        public GuiTypeButton(int id, int xPos, int yPos, int width, int height, String displayString, String taskKey, Consumer<String> out, String... tooltips) {
            super(id, xPos, yPos, width, height, displayString, tooltips);
            this.taskKey = taskKey;
            this.out = out;
        }
    }
}
