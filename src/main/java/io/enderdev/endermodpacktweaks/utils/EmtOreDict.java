package io.enderdev.endermodpacktweaks.utils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;

public final class EmtOreDict {
    public static final String PROOF_ENDER_DRAGON = "proofEnderDragon";
    public static final String PROOF_WITHER = "proofWither";

    public static boolean hasOreDictionary(IBlockState state, String oreDict) {
        try {
            return Arrays.stream(OreDictionary.getOreIDs(new ItemStack(state.getBlock(), 1, state.getBlock().damageDropped(state)))).anyMatch(i -> i == OreDictionary.getOreID(oreDict));
        } catch (Exception ignore) {
        }
        return false;
    }
}
