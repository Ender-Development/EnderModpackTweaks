package io.enderdev.endermodpacktweaks.patches.mysticallib;


import epicsquid.mysticallib.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.nbt.NBTTagCompound;

public class EffectCut extends Effect {
    public float yaw = 0.0F;
    public float pitch = 0.0F;
    public float slashAngle = 0.0F;

    public EffectCut(int worldIn) {
        super(worldIn);
    }

    public EffectCut setSlashProperties(float yaw, float pitch, float angle) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.slashAngle = angle;
        return this;
    }

    public void read(NBTTagCompound tag) {
        super.read(tag);
        this.yaw = tag.getFloat("yaw");
        this.pitch = tag.getFloat("pitch");
        this.slashAngle = tag.getFloat("slashAngle");
    }

    public NBTTagCompound write() {
        NBTTagCompound tag = super.write();
        tag.setFloat("yaw", this.yaw);
        tag.setFloat("pitch", this.pitch);
        tag.setFloat("slashAngle", this.slashAngle);
        return tag;
    }

    public void render(float pTicks) {
        Minecraft.getMinecraft().renderEngine.bindTexture(RenderUtil.beam_texture);
        GlStateManager.translate(this.getInterpX(pTicks), this.getInterpY(pTicks), this.getInterpZ(pTicks));
        GlStateManager.rotate(-this.yaw, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(this.pitch, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(-this.slashAngle, 0.0F, 0.0F, 1.0F);
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buffer = tess.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);
        RenderUtil.renderBeam(buffer, -5.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, this.r, this.g, this.b, 0.0F, this.r, this.g, this.b, this.a * this.getLifeCoeff(pTicks), 0.75F * this.getLifeCoeff(pTicks), 0.75F * this.getLifeCoeff(pTicks));
        RenderUtil.renderBeam(buffer, 0.0F, 0.0F, 0.0F, 5.0F, 0.0F, 0.0F, this.r, this.g, this.b, this.a * this.getLifeCoeff(pTicks), this.r, this.g, this.b, 0.0F, 0.75F * this.getLifeCoeff(pTicks), 0.75F * this.getLifeCoeff(pTicks));
        tess.draw();
    }
}
