/*
 *  Super Lead rope mod
 *  Copyright (C)  2025  R3944Realms
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package top.r3944realms.lib39.util.nbt;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class NBTReader {
    private final CompoundTag nbt;

    private NBTReader(CompoundTag nbt) {
        this.nbt = nbt;
    }

    /**
     * 从CompoundTag创建读取器
     */
    @NotNull
    public static NBTReader of(@NotNull CompoundTag nbt) {
        return new NBTReader(nbt);
    }

    // 基本读取方法 - 直接赋值给成员变量
    public NBTReader string(String key, Consumer<String> setter) {
        if (nbt.contains(key)) {
            setter.accept(nbt.getString(key));
        }
        return this;
    }

    public NBTReader string(String key, Consumer<String> setter, String defaultValue) {
        setter.accept(nbt.contains(key) ? nbt.getString(key) : defaultValue);
        return this;
    }

    public NBTReader byteValue(String key, Consumer<Byte> setter) {
        if (nbt.contains(key)) {
            setter.accept(nbt.getByte(key));
        }
        return this;
    }

    public NBTReader byteValue(String key, Consumer<Byte> setter, byte defaultValue) {
        setter.accept(nbt.contains(key) ? nbt.getByte(key) : defaultValue);
        return this;
    }

    public NBTReader shortValue(String key, Consumer<Short> setter) {
        if (nbt.contains(key)) {
            setter.accept(nbt.getShort(key));
        }
        return this;
    }

    public NBTReader shortValue(String key, Consumer<Short> setter, short defaultValue) {
        setter.accept(nbt.contains(key) ? nbt.getShort(key) : defaultValue);
        return this;
    }

    public NBTReader intValue(String key, Consumer<Integer> setter) {
        if (nbt.contains(key)) {
            setter.accept(nbt.getInt(key));
        }
        return this;
    }

    public NBTReader intValue(String key, Consumer<Integer> setter, int defaultValue) {
        setter.accept(nbt.contains(key) ? nbt.getInt(key) : defaultValue);
        return this;
    }

    public NBTReader longValue(String key, Consumer<Long> setter) {
        if (nbt.contains(key)) {
            setter.accept(nbt.getLong(key));
        }
        return this;
    }

    public NBTReader longValue(String key, Consumer<Long> setter, long defaultValue) {
        setter.accept(nbt.contains(key) ? nbt.getLong(key) : defaultValue);
        return this;
    }

    public NBTReader floatValue(String key, Consumer<Float> setter) {
        if (nbt.contains(key)) {
            setter.accept(nbt.getFloat(key));
        }
        return this;
    }

    public NBTReader floatValue(String key, Consumer<Float> setter, float defaultValue) {
        setter.accept(nbt.contains(key) ? nbt.getFloat(key) : defaultValue);
        return this;
    }

    public NBTReader doubleValue(String key, Consumer<Double> setter) {
        if (nbt.contains(key)) {
            setter.accept(nbt.getDouble(key));
        }
        return this;
    }

    public NBTReader doubleValue(String key, Consumer<Double> setter, double defaultValue) {
        setter.accept(nbt.contains(key) ? nbt.getDouble(key) : defaultValue);
        return this;
    }

    public NBTReader booleanValue(String key, Consumer<Boolean> setter) {
        if (nbt.contains(key)) {
            setter.accept(nbt.getBoolean(key));
        }
        return this;
    }

    public NBTReader booleanValue(String key, Consumer<Boolean> setter, boolean defaultValue) {
        setter.accept(nbt.contains(key) ? nbt.getBoolean(key) : defaultValue);
        return this;
    }

    // 数组类型
    public NBTReader byteArray(String key, Consumer<byte[]> setter) {
        if (nbt.contains(key)) {
            setter.accept(nbt.getByteArray(key));
        }
        return this;
    }

    public NBTReader intArray(String key, Consumer<int[]> setter) {
        if (nbt.contains(key)) {
            setter.accept(nbt.getIntArray(key));
        }
        return this;
    }

    public NBTReader longArray(String key, Consumer<long[]> setter) {
        if (nbt.contains(key)) {
            setter.accept(nbt.getLongArray(key));
        }
        return this;
    }

    // UUID
    public NBTReader uuid(String key, Consumer<UUID> setter) {
        if (nbt.hasUUID(key)) {
            setter.accept(nbt.getUUID(key));
        }
        return this;
    }

    public NBTReader uuid(String key, Consumer<UUID> setter, UUID defaultValue) {
        setter.accept(nbt.hasUUID(key) ? nbt.getUUID(key) : defaultValue);
        return this;
    }

    // CompoundTag
    public NBTReader compound(String key, Consumer<CompoundTag> setter) {
        if (nbt.contains(key)) {
            setter.accept(nbt.getCompound(key));
        }
        return this;
    }

    public NBTReader compound(String key, Consumer<CompoundTag> setter, CompoundTag defaultValue) {
        setter.accept(nbt.contains(key) ? nbt.getCompound(key) : defaultValue);
        return this;
    }

    // ListTag
    public NBTReader list(String key, int type, Consumer<ListTag> setter) {
        if (nbt.contains(key)) {
            setter.accept(nbt.getList(key, type));
        }
        return this;
    }

    // Vec3支持
    public NBTReader vec3(String key, Consumer<Vec3> setter) {
        if (nbt.contains(key)) {
            CompoundTag vecTag = nbt.getCompound(key);
            if (vecTag.contains("X") && vecTag.contains("Y") && vecTag.contains("Z")) {
                setter.accept(new Vec3(
                        vecTag.getDouble("X"),
                        vecTag.getDouble("Y"),
                        vecTag.getDouble("Z")
                ));
            }
        }
        return this;
    }

    public NBTReader vec3(String key, Consumer<Vec3> setter, Vec3 defaultValue) {
        if (nbt.contains(key)) {
            CompoundTag vecTag = nbt.getCompound(key);
            if (vecTag.contains("X") && vecTag.contains("Y") && vecTag.contains("Z")) {
                setter.accept(new Vec3(
                        vecTag.getDouble("X"),
                        vecTag.getDouble("Y"),
                        vecTag.getDouble("Z")
                ));
                return this;
            }
        }
        setter.accept(defaultValue);
        return this;
    }

    // 枚举支持
    public <T extends Enum<T>> NBTReader enumValue(String key, Class<T> enumClass, Consumer<T> setter) {
        if (nbt.contains(key)) {
            String value = nbt.getString(key);
            try {
                setter.accept(Enum.valueOf(enumClass, value.toUpperCase()));
            } catch (IllegalArgumentException ignored) {
                // 保持setter的当前值
            }
        }
        return this;
    }

    public <T extends Enum<T>> NBTReader enumValue(String key, Class<T> enumClass, Consumer<T> setter, T defaultValue) {
        if (nbt.contains(key)) {
            String value = nbt.getString(key);
            try {
                setter.accept(Enum.valueOf(enumClass, value.toUpperCase()));
                return this;
            } catch (IllegalArgumentException ignored) {
            }
        }
        setter.accept(defaultValue);
        return this;
    }

    // 嵌套读取支持
    public NBTReader nested(String key, Consumer<NBTReader> consumer) {
        if (nbt.contains(key)) {
            consumer.accept(new NBTReader(nbt.getCompound(key)));
        }
        return this;
    }

    public NBTReader nested(String key, Consumer<NBTReader> consumer, Runnable orElse) {
        if (nbt.contains(key)) {
            consumer.accept(new NBTReader(nbt.getCompound(key)));
        } else {
            orElse.run();
        }
        return this;
    }

    // 条件读取
    public NBTReader ifPresent(String key, Runnable action) {
        if (nbt.contains(key)) {
            action.run();
        }
        return this;
    }

    public NBTReader ifAbsent(String key, Runnable action) {
        if (!nbt.contains(key)) {
            action.run();
        }
        return this;
    }

    // 获取原始NBT
    @NotNull
    public CompoundTag getRaw() {
        return nbt;
    }

    // 便捷的静态方法（保持原有功能）
    @NotNull
    public static Vec3 readVec3(@NotNull CompoundTag nbt) {
        if (nbt.contains("X") && nbt.contains("Y") && nbt.contains("Z")) {
            return new Vec3(
                    nbt.getDouble("X"),
                    nbt.getDouble("Y"),
                    nbt.getDouble("Z")
            );
        } else {
            throw new IllegalArgumentException("NBT is missing X, Y, or Z value for Vec3");
        }
    }

    @Nullable
    public static Vec3 readVec3Safe(@NotNull CompoundTag nbt) {
        if (nbt.contains("X") && nbt.contains("Y") && nbt.contains("Z")) {
            return new Vec3(
                    nbt.getDouble("X"),
                    nbt.getDouble("Y"),
                    nbt.getDouble("Z")
            );
        }
        return null;
    }
}
