package top.r3944realms.lib39.util.shape;

import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ShapeUtil {
    /**
     * 创建基于像素的碰撞箱（将像素坐标转换为方块坐标）
     */
    public static VoxelShape createPixelBasedShape(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return Shapes.box(minX / 16.0d, minY / 16.0d, minZ / 16.0d, maxX / 16.0d, maxY / 16.0d, maxZ / 16.0d);
    }
}
