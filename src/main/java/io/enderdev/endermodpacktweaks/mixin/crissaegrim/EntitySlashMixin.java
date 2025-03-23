package io.enderdev.endermodpacktweaks.mixin.crissaegrim;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import epicsquid.crissaegrim.entity.EntitySlash;
import epicsquid.mysticallib.network.PacketHandler;
import io.enderdev.endermodpacktweaks.patches.mysticallib.Effect;
import io.enderdev.endermodpacktweaks.patches.mysticallib.EffectSlash;
import io.enderdev.endermodpacktweaks.patches.mysticallib.EffectCut;
import io.enderdev.endermodpacktweaks.patches.mysticallib.MessageEffect;
import io.enderdev.endermodpacktweaks.patches.mysticallib.FXHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.util.math.AxisAlignedBB;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import epicsquid.crissaegrim.RegistryManager;
import epicsquid.mysticallib.util.Util;
import net.minecraft.util.DamageSource;

@Mixin(value = EntitySlash.class, remap = false)
public class EntitySlashMixin {
    @Shadow
    public EntityPlayer player;

    @Final
    @Shadow
    public static DataParameter<Integer> lifetime;

    @WrapMethod(method = "onUpdate", remap = true)
    public void onUpdate(Operation<Void> original) {
        Entity slashEntity = (Entity) (Object) this;
        if (slashEntity.getDataManager().get(lifetime) % 2 == 0 && !slashEntity.world.isRemote) {
            float offX = 0.5F * (float) Math.sin(Math.toRadians(-90.0F - this.player.rotationYaw));
            float offZ = 0.5F * (float) Math.cos(Math.toRadians(-90.0F - this.player.rotationYaw));
            double x1 = this.player.posX + this.player.getLookVec().x * (double) 0.5F + (double) offX;
            double y1 = this.player.posY + this.player.getLookVec().y * (double) 0.5F + (double) this.player.getEyeHeight();
            double z1 = this.player.posZ + this.player.getLookVec().z * (double) 0.5F + (double) offZ;
            double x2 = this.player.posX + this.player.getLookVec().x * (double) 4.0F;
            double y2 = this.player.posY + (double) this.player.getEyeHeight() + this.player.getLookVec().y * (double) 4.0F;
            double z2 = this.player.posZ + this.player.getLookVec().z * (double) 4.0F;
            slashEntity.posX = this.player.posX;
            slashEntity.posY = this.player.posY;
            slashEntity.posZ = this.player.posZ;
            Effect slash = (new EffectSlash(this.player.world.provider.getDimension())).setSlashProperties(this.player.rotationYaw, this.player.rotationPitch, 30.0F + Util.rand.nextFloat() * 120.0F, 2.0F, 1.5F, 120.0F).setPosition(x1, y1, z1).setMotion((x2 - x1) / (double) 5.0F, (y2 - y1) / (double) 5.0F, (z2 - z1) / (double) 5.0F).setLife(5).setAdditive(true).setColor(0.35F, 0.35F, 1.0F, 1.0F);
            PacketHandler.INSTANCE.sendToAll(new MessageEffect(FXHandler.FX_SLASH, slash.write()));
            double lx = this.player.posX + this.player.getLookVec().x * (double) 2.0F;
            double ly = this.player.posY + (double) this.player.getEyeHeight() + this.player.getLookVec().y * (double) 2.0F;
            double lz = this.player.posZ + this.player.getLookVec().z * (double) 2.0F;

            for (EntityLivingBase e : slashEntity.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(lx - (double) 2.0F, ly - (double) 2.0F, lz - (double) 2.0F, lx + (double) 2.0F, ly + (double) 2.0F, lz + (double) 2.0F))) {
                if (e.getUniqueID().compareTo(this.player.getUniqueID()) != 0) {
                    e.hurtResistantTime = 0;
                    e.setRevengeTarget(this.player);
                    if (e.getHealth() > 0.0F) {
                        Effect cut = (new EffectCut(slashEntity.world.provider.getDimension())).setSlashProperties(this.player.rotationYaw, this.player.rotationPitch, Util.rand.nextFloat() * 360.0F).setColor(0.35F, 0.35F, 1.0F, 1.0F).setPosition(e.posX, e.posY + (double) (e.height / 2.0F), e.posZ).setAdditive(true).setLife(10);
                        PacketHandler.INSTANCE.sendToAll(new MessageEffect(RegistryManager.FX_CUT, cut.write()));
                    }

                    e.attackEntityFrom(DamageSource.causePlayerDamage(this.player), 6.0F);
                }
            }
        }

        slashEntity.getDataManager().set(lifetime, slashEntity.getDataManager().get(lifetime) - 1);
        if (slashEntity.getDataManager().get(lifetime) <= 0) {
            slashEntity.setDead();
        }
    }
}
