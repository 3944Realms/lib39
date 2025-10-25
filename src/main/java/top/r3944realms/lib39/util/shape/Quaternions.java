package top.r3944realms.lib39.util.shape;


import com.mojang.math.Axis;
import org.joml.Quaternionf;

/**
 * The type Quaternions.
 */
public final class Quaternions {
    /**
     * The constant XP_90.
     */
    public static final Quaternionf XP_90 = Axis.XP.rotationDegrees(90);
    /**
     * The constant XP_180.
     */
    public static final Quaternionf XP_180 = Axis.XP.rotationDegrees(180);
    /**
     * The constant XN_90.
     */
    public static final Quaternionf XN_90 = Axis.XN.rotationDegrees(90);

    /**
     * The constant YP_90.
     */
    public static final Quaternionf YP_90 = Axis.YP.rotationDegrees(90);
    /**
     * The constant YN_90.
     */
    public static final Quaternionf YN_90 = Axis.YN.rotationDegrees(90);

    /**
     * The constant ZP_90.
     */
    public static final Quaternionf ZP_90 = Axis.ZP.rotationDegrees(90);
    /**
     * The constant ZP_180.
     */
    public static final Quaternionf ZP_180 = Axis.ZP.rotationDegrees(180);
    /**
     * The constant ZN_90.
     */
    public static final Quaternionf ZN_90 = Axis.ZN.rotationDegrees(90);



    private Quaternions() { }
}