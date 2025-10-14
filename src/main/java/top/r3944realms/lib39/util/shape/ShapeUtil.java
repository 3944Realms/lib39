package top.r3944realms.lib39.util.shape;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ShapeUtil {

    // ==================== 基础形状创建 ====================

    /**
     * 创建基于像素的碰撞箱（将像素坐标转换为方块坐标）
     */
    public static @NotNull VoxelShape createPixelBasedShape(double minX, double minY, double minZ,
                                                            double maxX, double maxY, double maxZ) {
        return Shapes.box(minX / 16.0d, minY / 16.0d, minZ / 16.0d,
                maxX / 16.0d, maxY / 16.0d, maxZ / 16.0d);
    }

    /**
     * 便捷方法：创建方块碰撞箱
     */
    public static @NotNull VoxelShape createBox(double minX, double minY, double minZ,
                                                double maxX, double maxY, double maxZ) {
        return Shapes.box(minX, minY, minZ, maxX, maxY, maxZ);
    }

    // ==================== 多部分方块形状映射 ====================

    /**
     * 创建单一形状的方向映射
     */
    public static Map<Direction, VoxelShape> createUniformDirectionMap(VoxelShape shape) {
        return createRotatedDirectionMap(shape);
    }

    /**
     * 创建双部分方块的形状映射
     */
    public static Map<BlockPart, Map<Direction, VoxelShape>> createTwoPartShapeMap(VoxelShape headShape, VoxelShape footShape) {
        EnumMap<BlockPart, Map<Direction, VoxelShape>> shapeMap = new EnumMap<>(BlockPart.class);
        shapeMap.put(BlockPart.HEAD, createRotatedDirectionMap(headShape));
        shapeMap.put(BlockPart.FOOT, createRotatedDirectionMap(footShape));
        return shapeMap;
    }

    /**
     * 创建原版双方块的形状映射
     */
    public static Map<DoubleBlockHalf, Map<Direction, VoxelShape>> createDoubleBlockShapeMap(VoxelShape lowerShape, VoxelShape upperShape) {
        EnumMap<DoubleBlockHalf, Map<Direction, VoxelShape>> shapeMap = new EnumMap<>(DoubleBlockHalf.class);
        shapeMap.put(DoubleBlockHalf.LOWER, createRotatedDirectionMap(lowerShape));
        shapeMap.put(DoubleBlockHalf.UPPER, createRotatedDirectionMap(upperShape));
        return shapeMap;
    }

    /**
     * 创建三部分方块的形状映射
     */
    public static Map<BlockSection, Map<Direction, VoxelShape>> createThreePartShapeMap(VoxelShape headShape, VoxelShape centerShape, VoxelShape footShape) {
        EnumMap<BlockSection, Map<Direction, VoxelShape>> shapeMap = new EnumMap<>(BlockSection.class);
        shapeMap.put(BlockSection.HEAD, createRotatedDirectionMap(headShape));
        shapeMap.put(BlockSection.CENTER, createRotatedDirectionMap(centerShape));
        shapeMap.put(BlockSection.FOOT, createRotatedDirectionMap(footShape));
        return shapeMap;
    }

    // ==================== 形状旋转 ====================

    /**
     * 顺时针旋转碰撞箱（Y轴旋转）
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
     */
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
    private static VoxelShape combineShapes(List<VoxelShape> shapes) {
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
     */
    public static @NotNull VoxelShape combineShapes(VoxelShape... shapes) {
        return combineShapes(Arrays.asList(shapes));
    }

    // ==================== 内部工具方法 ====================

    /**
     * 创建旋转方向映射
     */
    private static Map<Direction, VoxelShape> createRotatedDirectionMap(VoxelShape baseShape) {
        EnumMap<Direction, VoxelShape> directionMap = new EnumMap<>(Direction.class);
        directionMap.put(Direction.NORTH, baseShape);
        directionMap.put(Direction.EAST, rotateShape(baseShape, 90));
        directionMap.put(Direction.SOUTH, rotateShape(baseShape, 180));
        directionMap.put(Direction.WEST, rotateShape(baseShape, 270));
        return directionMap;
    }

    // ==================== 枚举定义 ====================

    /**
     * 方块部分枚举（双部分方块）
     */
    public enum BlockPart {
        HEAD,
        FOOT
    }

    /**
     * 方块部分枚举（三部分方块）
     */
    public enum BlockSection {
        HEAD,
        CENTER,
        FOOT
    }

    // ==================== 便捷构建器 ====================

    /**
     * 形状构建器 - 流畅API
     */
    public static class ShapeBuilder {
        private final List<VoxelShape> shapes = new ArrayList<>();

        public ShapeBuilder addPixelBox(double minX, double minY, double minZ,
                                        double maxX, double maxY, double maxZ) {
            shapes.add(createPixelBasedShape(minX, minY, minZ, maxX, maxY, maxZ));
            return this;
        }

        public ShapeBuilder addBox(double minX, double minY, double minZ,
                                   double maxX, double maxY, double maxZ) {
            shapes.add(createBox(minX, minY, minZ, maxX, maxY, maxZ));
            return this;
        }

        public ShapeBuilder addShape(VoxelShape shape) {
            shapes.add(shape);
            return this;
        }

        public VoxelShape build() {
            return combineShapes(shapes);
        }
    }

    /**
     * 创建形状构建器
     */
    public static ShapeBuilder builder() {
        return new ShapeBuilder();
    }
}