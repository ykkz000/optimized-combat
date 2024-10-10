/*
 * Optimized Combat
 * Copyright (C) 2024  ykkz000
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package ykkz000.optimizedcombat.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class EntityUtils {
    public static <T extends LivingEntity> boolean refreshAttributeModifier(T entity, RegistryEntry<EntityAttribute> attribute, Identifier identifier, boolean persistent, double value, EntityAttributeModifier.Operation operation) {
        EntityAttributeInstance entityAttributeInstance = entity.getAttributeInstance(attribute);
        if (entityAttributeInstance == null) {
            return false;
        }
        entityAttributeInstance.removeModifier(identifier);
        if (persistent) {
            entityAttributeInstance.addPersistentModifier(new EntityAttributeModifier(identifier, value, operation));
        } else {
            entityAttributeInstance.addTemporaryModifier(new EntityAttributeModifier(identifier, value, operation));
        }
        return true;
    }
}
