package io.enderdev.endermodpacktweaks.mixin.astralsorcery;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import hellfirepvp.astralsorcery.common.block.BlockMachine;
import io.enderdev.endermodpacktweaks.config.CfgTweaks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = BlockMachine.class, remap = false)
public abstract class BlockMachineMixin {

    @Shadow
    public abstract boolean handleSpecificActivateEvent(PlayerInteractEvent.RightClickBlock event);

    @WrapOperation(method = "handleSpecificActivateEvent", at = @At(value = "INVOKE", target = "Lhellfirepvp/astralsorcery/common/util/MiscUtils;isPlayerFakeMP(Lnet/minecraft/entity/player/EntityPlayerMP;)Z"))
    private boolean onHandleSpecificActivateEvent(EntityPlayerMP specificPlayerClass, Operation<Boolean> original) {
        return !CfgTweaks.ASTRAL_SORCERY.allowFakePlayer && original.call(specificPlayerClass);
    }

    @WrapMethod(method = "onBlockActivated")
    private boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ, Operation<Boolean> original) {
        if (!CfgTweaks.ASTRAL_SORCERY.allowFakePlayer) {
            return original.call(worldIn, pos, state, player, hand, facing, hitX, hitY, hitZ);
        }
        PlayerInteractEvent.RightClickBlock event = new PlayerInteractEvent.RightClickBlock(player, hand, pos, facing, new Vec3d(hitX, hitY, hitZ));
        handleSpecificActivateEvent(event);
        return false;
    }
}
