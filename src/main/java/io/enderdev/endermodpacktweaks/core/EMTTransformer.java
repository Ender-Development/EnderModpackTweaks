package io.enderdev.endermodpacktweaks.core;

import io.enderdev.endermodpacktweaks.config.CfgModpack;
import io.enderdev.endermodpacktweaks.features.modpackinfo.IconHandler;
import io.enderdev.endermodpacktweaks.features.modpackinfo.TitleHandler;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.io.FileNotFoundException;
import java.util.Iterator;

@SuppressWarnings("unused") // Used by EMTLoadingPlugin
public class EMTTransformer implements IClassTransformer, Opcodes {

    private static final String HOOKS = "io/enderdev/endermodpacktweaks/core/EMTTransformer$Hooks";

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        // This is currently solved via mixin, because for whatever reason the ASM approach didn't work
        // Keeping this here for reference or in case someone wants to try and fix it
        // if (transformedName.equals("de.keksuccino.fancymenu.mainwindow.MainWindowHandler")) return transformMainWindowHandler(basicClass);
        return basicClass;
    }

    private static byte[] transformMainWindowHandler(byte[] basicClass) {
        ClassReader reader = new ClassReader(basicClass);
        ClassNode cls = new ClassNode();
        reader.accept(cls, 0);
        for (MethodNode method : cls.methods) {
            if (method.name.equals("updateWindowIcon")) {
                AbstractInsnNode node = method.instructions.getFirst();
                while (node.getOpcode() != GETSTATIC) node = node.getNext();
                InsnList list = new InsnList();
                list.add(new MethodInsnNode(INVOKESTATIC, HOOKS, "FancyMenu$updateWindowIcon", "()Z", false));
                LabelNode l_con = new LabelNode();
                list.add(new JumpInsnNode(IFEQ, l_con));
                list.add(new InsnNode(RETURN));
                list.add(l_con);
                list.add(new FrameNode(F_SAME, 0, null, 0, null));
                method.instructions.insertBefore(node, list);
            }
            else if (method.name.equals("getCustomWindowTitle")) {
                Iterator<AbstractInsnNode> iterator = method.instructions.iterator();
                while (iterator.hasNext()) {
                    AbstractInsnNode node = iterator.next();
                    if (node.getOpcode() == ARETURN) {
                        method.instructions.insertBefore(node, new MethodInsnNode(INVOKESTATIC, HOOKS, "FancyMenu$getCustomWindowTitle", "(Ljava/lang/String;)Ljava/lang/String;", false));
                        break;
                    }
                }
            }
        }
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        cls.accept(writer);
        return writer.toByteArray();
    }

    @SuppressWarnings("unused") // Used by transformed classes
    public static class Hooks {

        public static String FancyMenu$getCustomWindowTitle(String original) {
            return CfgModpack.CUSTOMIZATION.windowTitle ? TitleHandler.getTitle() : original;
        }

        public static boolean FancyMenu$updateWindowIcon() {
            if (CfgModpack.CUSTOMIZATION.windowIcon) {
                try {
                    IconHandler.changeIcon();
                    return true;
                } catch (FileNotFoundException ignored) {}
            }
            return false;
        }
    }
}
