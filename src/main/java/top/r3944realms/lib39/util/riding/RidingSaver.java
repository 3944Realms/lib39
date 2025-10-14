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

package top.r3944realms.lib39.util.riding;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.r3944realms.lib39.util.lang.Pair;

import java.util.*;
import java.util.function.Function;

@SuppressWarnings("unused")
public class RidingSaver {
    /**
     * 保存骑乘关系
     */
    @Contract("null -> new")
    public static @NotNull RidingRelationship save(@Nullable Entity entity) {
        return save(entity, true);
    }

    /**
     * 保存骑乘关系
     */
    @Contract("null, _ -> new")
    public static @NotNull RidingRelationship save(@Nullable Entity entity, boolean findRoot) {
        if (entity == null) {
            return new RidingRelationship(Collections.emptyList(), null, null);
        }

        Entity rootEntity = findRoot ? RidingFinder.findRootVehicle(entity) : entity;
        if (rootEntity == null) {
            return new RidingRelationship(Collections.emptyList(), null, null);
        }

        RidingRelationship rootRelationship = new RidingRelationship();
        rootRelationship.setEntityId(rootEntity.getUUID());
        rootRelationship.setVehicleId(null);
        rootRelationship.setPassengers(new ArrayList<>());

        Queue<Pair<Entity, RidingRelationship>> queue = new LinkedList<>();
        queue.offer(Pair.of(rootEntity, rootRelationship));

        Set<UUID> processedEntities = new HashSet<>();
        processedEntities.add(rootEntity.getUUID());

        while (!queue.isEmpty()) {
            Pair<Entity, RidingRelationship> current = queue.poll();
            Entity currentEntity = current.first;
            RidingRelationship currentRelation = current.second;

            List<Entity> passengers = currentEntity.getPassengers();

            if (!passengers.isEmpty()) {
                for (Entity passenger : passengers) {
                    UUID passengerId = passenger.getUUID();

                    if (!processedEntities.contains(passengerId)) {
                        processedEntities.add(passengerId);

                        // ⬇ 构建子关系
                        RidingRelationship passengerRelation = new RidingRelationship();
                        passengerRelation.setEntityId(passengerId);
                        passengerRelation.setVehicleId(currentEntity.getUUID());
                        passengerRelation.setPassengers(new ArrayList<>());

                        currentRelation.addPassenger(passengerRelation);
                        queue.offer(Pair.of(passenger, passengerRelation));
                    } else {
                        throw new RidingCycleException(
                                passengerId,
                                currentEntity.getUUID()
                        );
                    }
                }
            }
        }

        return rootRelationship;
    }

    // 传入一个实体提供器 Function<UUID, Entity>，通常在服务器侧就是 level::getEntity
    private static Function<UUID, Entity> entityProvider;

    public static void setEntityProvider(Function<UUID, Entity> provider) {
        entityProvider = provider;
    }

    /**
     * 根据UUID获取EntityType
     */
    private static @Nullable EntityType<?> getEntityType(UUID entityId) {
        if (entityProvider == null) return null;
        Entity entity = entityProvider.apply(entityId);
        if (entity == null) return null;
        return entity.getType();
    }
}
