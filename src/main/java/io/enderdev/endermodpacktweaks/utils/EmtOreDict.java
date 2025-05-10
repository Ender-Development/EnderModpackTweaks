package io.enderdev.endermodpacktweaks.utils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public final class EmtOreDict {
    public static final String PROOF_ENDER_DRAGON = "proofEnderDragon";
    public static final String PROOF_WITHER = "proofWither";

    public static boolean hasOreDictionary(IBlockState state, String oreDict) {
        int id = OreDictionary.getOreID(oreDict);
        try {
            for (int i : OreDictionary.getOreIDs(new ItemStack(state.getBlock(), 1, state.getBlock().damageDropped(state))))
                if (i == id) return true;
        } catch (Exception ignored) {}
        return false;
    }
}
