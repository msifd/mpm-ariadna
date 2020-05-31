package noppes.mpm.core;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.util.MathHelper;
import noppes.mpm.constants.EnumAnimation;
import noppes.mpm.data.ModelData;
import noppes.mpm.data.PlayerDataController;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.ListIterator;

public class CameraTransformer implements IClassTransformer, Opcodes {
    private static final String METHOD_NAME_OBF = "h";
    private static final String METHOD_NAME_DEOBF = "orientCamera";
    private static final String METHOD_DESC = "(F)V";

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if ("net.minecraft.client.renderer.EntityRenderer".equals(transformedName))
            return transformEntityRender(basicClass);
        return basicClass;
    }

    public static float camOffset(EntityLivingBase entity) {
        if (!(entity instanceof EntityPlayer))
            return 0;

        final EntityPlayer player = (EntityPlayer) entity;
        if (player.isPlayerSleeping())
            return 0;

        final ModelData data = PlayerDataController.instance.getPlayerData(player);

        float offset = data.offsetY();
        if (data.animation == EnumAnimation.SITTING)
            offset -= -data.getLegsY() + 0.6f;
        if (data.isSleeping() || data.animation == EnumAnimation.CRAWLING)
            offset = -1.18f;
        if (offset > 0 && isBlocked(player, offset + 0.1f))
            offset = 0;

        return -offset;
    }

    private static boolean isBlocked(EntityPlayer player, float offset) {
        int x = MathHelper.floor_double(player.posX);
        int y = MathHelper.floor_double(player.posY + offset);
        int z = MathHelper.floor_double(player.posZ);
        return !player.worldObj.isAirBlock(x, y, z);
    }

//    LINENUMBER 75 L1
//    ALOAD 2
//    GETFIELD net/minecraft/entity/EntityLivingBase.yOffset : F
//    + ALOAD 2
//    + INVOKESTATIC noppes/mpm/core/CameraTransformer.camOffset (Lnet/minecraft/entity/EntityLivingBase;)F
//    + FADD
//    LDC 1.62
//    FSUB
//    FSTORE 3

    private static byte[] transformEntityRender(byte[] basicClass) {
        final ClassReader reader = new ClassReader(basicClass);

        final ClassNode cnode = new ClassNode();
        reader.accept(cnode, ClassReader.EXPAND_FRAMES);

        final String mnName = MpmCorePlugin.isDevEnv ? METHOD_NAME_DEOBF : METHOD_NAME_OBF;
        final MethodNode mn = cnode.methods.stream()
                .filter(m -> m.name.equals(mnName) && m.desc.equals(METHOD_DESC))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Cant find method " + mnName + METHOD_DESC));
        patchOrientCamera(mn);

        final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        cnode.accept(cw);
        return cw.toByteArray();
    }

    private static void patchOrientCamera(MethodNode mn) {
        final ListIterator<AbstractInsnNode> it = mn.instructions.iterator();

        final String ownerName = MpmCorePlugin.isDevEnv ? "net/minecraft/entity/EntityLivingBase" : "sv";
        final String fieldName = MpmCorePlugin.isDevEnv ? "yOffset" : "L";

        while (it.hasNext()) {
            final AbstractInsnNode an = it.next();
            if (an instanceof FieldInsnNode) {
                final FieldInsnNode fin = (FieldInsnNode) an;
                if (fin.getOpcode() == GETFIELD && fin.owner.equals(ownerName) && fin.name.equals(fieldName)) {
                    it.add(new VarInsnNode(ALOAD, 2));
                    it.add(new MethodInsnNode(
                            INVOKESTATIC,
                            "noppes/mpm/core/CameraTransformer",
                            "camOffset", "(L" + ownerName + ";)F",
                            false));
                    it.add(new InsnNode(FADD));
                    break;
                }
            }
        }
    }
}
