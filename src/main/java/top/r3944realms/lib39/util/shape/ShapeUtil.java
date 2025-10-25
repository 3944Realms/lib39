package top.r3944realms.lib39.util.shape;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * The type Shape util.
 */
@SuppressWarnings("unused")
public class ShapeUtil {

    // ==================== 基础形状创建 ====================

    /**
     * 创建基于像素的碰撞箱（将像素坐标转换为方块坐标）
     *
     * @param minX the min x
     * @param minY the min y
     * @param minZ the min z
     * @param maxX the max x
     * @param maxY the max y
     * @param maxZ the max z
     * @return the voxel shape
     */
    public static @NotNull VoxelShape createPixelBasedShape(double minX, double minY, double minZ,
                                                            double maxX, double maxY, double maxZ) {
        return Shapes.box(minX / 16.0d, minY / 16.0d, minZ / 16.0d,
                maxX / 16.0d, maxY / 16.0d, maxZ / 16.0d);
    }

    /**
     * 便捷方法：创建方块碰撞箱
     *
     * @param minX the min x
     * @param minY the min y
     * @param minZ the min z
     * @param maxX the max x
     * @param maxY the max y
     * @param maxZ the max z
     * @return the voxel shape
     */
    public static @NotNull VoxelShape createBox(double minX, double minY, double minZ,
                                                double maxX, double maxY, double maxZ) {
        return Shapes.box(minX, minY, minZ, maxX, maxY, maxZ);
    }

    // ==================== 多部分方块形状映射 ====================

    /**
     * 创建单一形状的方向映射
     *
     * @param shape the shape
     * @return the map
     */
    public static @NotNull Map<Direction, VoxelShape> createUniformDirectionMap(VoxelShape shape) {
        return createRotatedDirectionMap(shape);
    }

    /**
     * 创建原版双方块的形状映射
     *
     * @param lowerShape the lower shape
     * @param upperShape the upper shape
     * @return the map
     */
    public static @NotNull Map<DoubleBlockHalf, Map<Direction, VoxelShape>> createDoubleBlockShapeMap(VoxelShape lowerShape, VoxelShape upperShape) {
        EnumMap<DoubleBlockHalf, Map<Direction, VoxelShape>> shapeMap = new EnumMap<>(DoubleBlockHalf.class);
        shapeMap.put(DoubleBlockHalf.LOWER, createRotatedDirectionMap(lowerShape));
        shapeMap.put(DoubleBlockHalf.UPPER, createRotatedDirectionMap(upperShape));
        return shapeMap;
    }

    // ==================== 形状旋转 ====================

    /**
     * 顺时针旋转碰撞箱（Y轴旋转）
     *
     * @param shape the shape
     * @return the voxel shape
     */
    public static @NotNull VoxelShape rotateVoxelShapeClockwise(@NotNull VoxelShape shape) {
        final List<VoxelShape> generatedShapes = new ArrayList<>();
        shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
            VoxelShape rotated = Shapes.box(1.0 - maxZ, minY, minX, 1.0 - minZ, maxY, maxX);
            generatedShapes.add(rotated);
        });
        return combineShapes(generatedShapes);
    }

    /**
     * 绕X轴旋转碰撞箱
     *
     * @param shape the shape
     * @return the voxel shape
     */
    public static @NotNull VoxelShape rotateVoxelShapeXAxis(@NotNull VoxelShape shape) {
        final List<VoxelShape> generatedShapes = new ArrayList<>();
        shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
            VoxelShape rotated = Shapes.box(minX, 1.0 - maxZ, minY, maxX, 1.0 - minZ, maxY);
            generatedShapes.add(rotated);
        });
        return combineShapes(generatedShapes);
    }

    /**
     * 绕Z轴旋转碰撞箱
     *
     * @param shape the shape
     * @return the voxel shape
     */
    @SuppressWarnings("SuspiciousNameCombination")
    public static @NotNull VoxelShape rotateVoxelShapeZAxis(@NotNull VoxelShape shape) {
        final List<VoxelShape> generatedShapes = new ArrayList<>();
        shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
            VoxelShape rotated = Shapes.box(minY, minX, minZ, maxY, maxX, maxZ);
            generatedShapes.add(rotated);
        });
        return combineShapes(generatedShapes);
    }

    /**
     * 按指定角度旋转碰撞箱
     *
     * @param shape   the shape
     * @param degrees the degrees
     * @return the voxel shape
     */
    public static @NotNull VoxelShape rotateShape(@NotNull VoxelShape shape, int degrees) {
        int rotations = (degrees / 90) % 4;
        VoxelShape result = shape;

        for (int i = 0; i < rotations; i++) {
            result = rotateVoxelShapeClockwise(result);
        }
        return result;
    }

    // ==================== 形状组合与优化 ====================

    /**
     * 组合多个形状列表
     */
    @NotNull
    private static VoxelShape combineShapes(@NotNull List<VoxelShape> shapes) {
        if (shapes.isEmpty()) {
            return Shapes.block();
        }

        VoxelShape result = shapes.get(0);
        for (int i = 1; i < shapes.size(); i++) {
            result = Shapes.or(result, shapes.get(i));
        }

        return result.optimize();
    }

    /**
     * 组合多个形状
     *
     * @param shapes the shapes
     * @return the voxel shape
     */
    public static @NotNull VoxelShape combineShapes(VoxelShape... shapes) {
        return combineShapes(Arrays.asList(shapes));
    }

    // ==================== 内部工具方法 ====================

    /**
     * 创建旋转方向映射
     */
    private static @NotNull Map<Direction, VoxelShape> createRotatedDirectionMap(VoxelShape baseShape) {
        EnumMap<Direction, VoxelShape> directionMap = new EnumMap<>(Direction.class);
        directionMap.put(Direction.NORTH, baseShape);
        directionMap.put(Direction.EAST, rotateShape(baseShape, 90));
        directionMap.put(Direction.SOUTH, rotateShape(baseShape, 180));
        directionMap.put(Direction.WEST, rotateShape(baseShape, 270));
        return directionMap;
    }


    // ==================== 便捷构建器 ====================

    /**
     * 形状构建器 - 流畅API
     */
    public static class ShapeBuilder {
        private final List<VoxelShape> shapes = new ArrayList<>();

        /**
         * Add pixel box shape builder.
         *
         * @param minX the min x
         * @param minY the min y
         * @param minZ the min z
         * @param maxX the max x
         * @param maxY the max y
         * @param maxZ the max z
         * @return the shape builder
         */
        public ShapeBuilder addPixelBox(double minX, double minY, double minZ,
                                        double maxX, double maxY, double maxZ) {
            shapes.add(createPixelBasedShape(minX, minY, minZ, maxX, maxY, maxZ));
            return this;
        }

        /**
         * Add box shape builder.
         *
         * @param minX the min x
         * @param minY the min y
         * @param minZ the min z
         * @param maxX the max x
         * @param maxY the max y
         * @param maxZ the max z
         * @return the shape builder
         */
        public ShapeBuilder addBox(double minX, double minY, double minZ,
                                   double maxX, double maxY, double maxZ) {
            shapes.add(createBox(minX, minY, minZ, maxX, maxY, maxZ));
            return this;
        }

        /**
         * Add shape shape builder.
         *
         * @param shape the shape
         * @return the shape builder
         */
        public ShapeBuilder addShape(VoxelShape shape) {
            shapes.add(shape);
            return this;
        }

        /**
         * Build voxel shape.
         *
         * @return the voxel shape
         */
        public VoxelShape build() {
            return combineShapes(shapes);
        }
    }

    /**
     * 创建形状构建器
     *
     * @return the shape builder
     */
    @Contract(" -> new")
    public static @NotNull ShapeBuilder builder() {
        return new ShapeBuilder();
    }
}