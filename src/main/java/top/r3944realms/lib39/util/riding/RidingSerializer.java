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

import com.google.gson.Gson;

/**
 * The type Riding serializer.
 */
@SuppressWarnings("unused")
public class RidingSerializer {
    private static final Gson GSON = new Gson();

    /**
     * 序列化骑乘关系
     *
     * @param relationship the relationship
     * @return the string
     */
    public static String serialize(RidingRelationship relationship) {
        return GSON.toJson(relationship);
    }

    /**
     * 反序列化骑乘关系
     *
     * @param json the json
     * @return the riding relationship
     */
    public static RidingRelationship deserialize(String json) {
        return GSON.fromJson(json, RidingRelationship.class);
    }
}
