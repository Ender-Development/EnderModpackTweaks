package io.enderdev.endermodpacktweaks.features.modpackinfo;

import io.enderdev.endermodpacktweaks.EMTConfig;
import net.minecraftforge.client.event.GuiScreenEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;

public class OptionsButtonHandler {
    private static final Map<SlideButton, BooleanSupplier> slideButtonRegister = new HashMap<SlideButton, BooleanSupplier>() {{
        put(new SlideButton(50, 0, 0, "Changelog").setIcon(0, 1), () -> EMTConfig.MODPACK.OPTIONS_MENU_BUTTONS.CHANGELOG_BUTTON.enable);
        put(new SlideButton(51, 0, 0, "Donation").setIcon(1, 0), () -> EMTConfig.MODPACK.OPTIONS_MENU_BUTTONS.DONATION_BUTTON.enable);
        put(new SlideButton(52, 0, 0, "GitHub").setIcon(0, 0), () -> EMTConfig.MODPACK.OPTIONS_MENU_BUTTONS.GITHUB_BUTTON.enable);
        put(new SlideButton(53, 0, 0, "Discord").setIcon(1, 1), () -> EMTConfig.MODPACK.OPTIONS_MENU_BUTTONS.DISCORD_BUTTON.enable);
        put(new SlideButton(54, 0, 0, "Twitch").setIcon(2, 0), () -> EMTConfig.MODPACK.OPTIONS_MENU_BUTTONS.TWITCH_BUTTON.enable);
        put(new SlideButton(55, 0, 0, "Youtube").setIcon(3, 0), () -> EMTConfig.MODPACK.OPTIONS_MENU_BUTTONS.YOUTUBE_BUTTON.enable);
    }};

    private final ArrayList<SlideButton> slideButtons = new ArrayList<>();
    private final GuiScreenEvent.InitGuiEvent.Post event;
    private int yOffset;

    public OptionsButtonHandler(GuiScreenEvent.InitGuiEvent.Post event) {
        this.event = event;
        int enabledButtons = 0;
        for (Map.Entry<SlideButton, BooleanSupplier> entry : slideButtonRegister.entrySet()) {
            if (entry.getValue().getAsBoolean()) {
                enabledButtons++;
            }
        }
        yOffset = (event.getGui().height / 2) - (enabledButtons * 12) - 20;
    }

    public void clearButtons() {
        slideButtons.clear();
    }

    public void loadButtons() {
        for (Map.Entry<SlideButton, BooleanSupplier> entry : slideButtonRegister.entrySet()) {
            SlideButton button = entry.getKey();
            BooleanSupplier condition = entry.getValue();
            if (condition.getAsBoolean()) {
                slideButtons.add(button);
            }
        }
        slideButtons.sort((b1, b2) -> {
            if (b1.id < b2.id) {
                return -1;
            } else if (b1.id > b2.id) {
                return 1;
            }
            return 0;
        });
    }

    public void addButtons() {
        slideButtons.forEach(button -> {
            button.x = event.getGui().width - button.width + 2;
            button.y = yOffset += button.height + 4;
            event.getButtonList().add(button);
        });
    }
}
