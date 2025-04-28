package io.enderdev.endermodpacktweaks.patches.mysticallib;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

public class EffectManager {
    public static ArrayList<Effect> effects = new ArrayList<>();
    public static ArrayList<Effect> toAdd = new ArrayList<>();

    public static void addEffect(Effect e) {
        toAdd.add(e);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onClientTick(ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START && !effects.isEmpty()) {
            if (Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().world != null) {
                boolean anyDead = false;
                for (Effect effect : effects) {
                    if (effect.dead) {
                        anyDead = true;
                    } else {
                        effect.update();
                    }
                }
                if (anyDead)
                    effects.removeIf(effect -> effect.dead);
            }
        }
        if (event.phase == TickEvent.Phase.END && !toAdd.isEmpty()) {
            for (Effect effect : toAdd) {
                if (!effect.dead) {
                    effects.add(effect);
                }
            }
            toAdd.clear();
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderLast(RenderWorldLastEvent event) {
        for (Effect effect : effects) {
            effect.renderTotal(event.getPartialTicks());
        }
    }
}