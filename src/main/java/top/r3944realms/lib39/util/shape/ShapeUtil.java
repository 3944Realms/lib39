package top.r3944realms.lib39.util.shape;

import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ShapeUtil {
    /**
     * 创建基于像素的碰撞箱（将像素坐标转换为方块坐标）
     */
    public static @NotNull VoxelShape createPixelBasedShape(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return Shapes.box(minX / 16.0d, minY / 16.0d, minZ / 16.0d, maxX / 16.0d, maxY / 16.0d, maxZ / 16.0d);
    }
    public static @NotNull VoxelShape rotateVoxelShapeClockwise(@NotNull VoxelShape in) {
        final List<VoxelShape> generatedShapes = new ArrayList<>();
        in.forAllBoxes((arg0, arg1, arg2, arg3, arg4, arg5) -> {
            VoxelShape shape = Shapes.box(1.0 - arg5, arg1, arg0, 1.0 - arg2, arg4, arg3);
            generatedShapes.add(shape);
        });
        return getVoxelShape(generatedShapes);
    }
    public static VoxelShape RotateVoxelShapeXAxis(VoxelShape in) {
        final List<VoxelShape> generatedShapes = new ArrayList<>();
        in.forAllBoxes((arg0, arg1, arg2, arg3, arg4, arg5) -> {
            VoxelShape shape = Shapes.box(arg0, 1.0 - arg5, arg1, arg3, 1.0 - arg2, arg4);
            generatedShapes.add(shape);
        });
        return getVoxelShape(generatedShapes);
    }

    @NotNull
    private static VoxelShape getVoxelShape(List<VoxelShape> generatedShapes) {
        if (generatedShapes.isEmpty()) {
            return Shapes.block();
        } else {
            VoxelShape out = generatedShapes.get(0);

            for(int i = 1; i < generatedShapes.size(); ++i) {
                VoxelShape shape = generatedShapes.get(i);
                out = Shapes.or(out, shape);
            }

            out.optimize();
            return out;
        }
    }
}
