package io.enderdev.endermodpacktweaks.features.credits;

import com.google.common.collect.Lists;
import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import io.enderdev.endermodpacktweaks.config.CfgFeatures;
import io.enderdev.endermodpacktweaks.utils.EmtFile;
import net.minecraft.client.gui.GuiWinGame;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class ImprovedCreditsGui extends GuiWinGame {
    private final float speed;

    public ImprovedCreditsGui(boolean poemIn, Runnable onFinishedIn) {
        super(poemIn, onFinishedIn);
        this.speed = poemIn ? 0.5F : 0.75F;
    }

    @Override
    public void initGui() {
        if (this.lines == null) {
            this.lines = Lists.newArrayList();
            try {
                String s = "" + TextFormatting.WHITE + TextFormatting.OBFUSCATED + TextFormatting.GREEN + TextFormatting.AQUA;
                if (this.poem) {
                    File poemFile = EmtFile.getFile(CfgFeatures.CUSTOM_CREDITS.pathPoem);
                    InputStream inputstream = poemFile.exists() ? EmtFile.getInputStream(poemFile) : this.mc.getResourceManager().getResource(new ResourceLocation("texts/end.txt")).getInputStream();
                    BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream, StandardCharsets.UTF_8));
                    Random random = new Random(8124371L);
                    String s1;

                    while ((s1 = bufferedreader.readLine()) != null) {
                        String s2;
                        String s3;

                        for (s1 = s1.replaceAll("PLAYERNAME", this.mc.getSession().getUsername()); s1.contains(s); s1 = s2 + TextFormatting.WHITE + TextFormatting.OBFUSCATED + "XXXXXXXX".substring(0, random.nextInt(4) + 3) + s3) {
                            int j = s1.indexOf(s);
                            s2 = s1.substring(0, j);
                            s3 = s1.substring(j + s.length());
                        }

                        this.lines.addAll(this.mc.fontRenderer.listFormattedStringToWidth(s1, 274));
                        this.lines.add("");
                    }

                    inputstream.close();

                    for (int k = 0; k < 8; ++k) {
                        this.lines.add("");
                    }
                }

                File creditsFile = EmtFile.getFile(CfgFeatures.CUSTOM_CREDITS.pathCredit);
                InputStream inputstream = creditsFile.exists() ? EmtFile.getInputStream(creditsFile) : this.mc.getResourceManager().getResource(new ResourceLocation("texts/credits.txt")).getInputStream();
                BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream, StandardCharsets.UTF_8));
                String s4;

                while ((s4 = bufferedreader.readLine()) != null) {
                    s4 = s4.replaceAll("PLAYERNAME", this.mc.getSession().getUsername());
                    s4 = s4.replaceAll("\t", "    ");
                    this.lines.addAll(this.mc.fontRenderer.listFormattedStringToWidth(s4, 274));
                    this.lines.add("");
                }

                inputstream.close();
                this.totalScrollLength = this.lines.size() * 12;
            } catch (Exception exception) {
                EnderModpackTweaks.LOGGER.error("Couldn't load credits", exception);
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawWinGameScreen(mouseX, mouseY, partialTicks);
        time += partialTicks;

        this.mc.getTextureManager().bindTexture(new ResourceLocation(CfgFeatures.CUSTOM_CREDITS.resourceLocationLogo));
        int textureWidth = GlStateManager.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_WIDTH);
        int textureHeight = GlStateManager.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_HEIGHT);

        float f = -time * speed;
        int x = (this.width / 2) - (textureWidth / 2);
        int y = this.height + 50;

        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, f, 0.0F);
        drawLogo(x, y, textureWidth, textureHeight);
        drawCredits(this.width / 2 - 137, y + textureHeight + 10, f);
        GlStateManager.popMatrix();
        drawVignette();
    }

    private void drawLogo(int x, int y, int textureWidth, int textureHeight) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableAlpha();
        drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, textureWidth, textureHeight, textureWidth, textureHeight);
        GlStateManager.disableAlpha();
    }

    private void drawCredits(int j, int l, float f) {
        for (int i1 = 0; i1 < this.lines.size(); ++i1) {
            if (i1 == this.lines.size() - 1) {
                float f1 = (float) l + f - (float) (this.height / 2 - 6);
                if (f1 < 0.0F) {
                    GlStateManager.translate(0.0F, -f1, 0.0F);
                }
            }
            if ((float) l + f + 12.0F + 8.0F > 0.0F && (float) l + f < (float) this.height) {
                String s = this.lines.get(i1);
                if (s.startsWith("[C]")) {
                    this.fontRenderer.drawStringWithShadow(s.substring(3), (float) (j + (274 - this.fontRenderer.getStringWidth(s.substring(3))) / 2), (float) l, 16777215);
                } else {
                    this.fontRenderer.fontRandom.setSeed((long) ((float) ((long) i1 * 4238972211L) + time / 4.0F));
                    this.fontRenderer.drawStringWithShadow(s, (float) j, (float) l, 16777215);
                }
            }
            l += 12;
        }
    }

    private void drawVignette() {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        this.mc.getTextureManager().bindTexture(new ResourceLocation("minecraft", "textures/misc/vignette.png"));
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR);
        int j1 = this.width;
        int k1 = this.height;
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferbuilder.pos(0.0D, k1, this.zLevel).tex(0.0D, 1.0D).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        bufferbuilder.pos(j1, k1, this.zLevel).tex(1.0D, 1.0D).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        bufferbuilder.pos(j1, 0.0D, this.zLevel).tex(1.0D, 0.0D).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        bufferbuilder.pos(0.0D, 0.0D, this.zLevel).tex(0.0D, 0.0D).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        tessellator.draw();
        GlStateManager.disableBlend();
    }
}
