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

package ykkz000.optimizedcombat.world.explosion;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;

import java.util.UUID;

public class NotDamageOwnerExplosionBehavior extends ExplosionBehavior {
    private final UUID ownerUUID;

    public NotDamageOwnerExplosionBehavior(Entity owner) {
        this.ownerUUID = owner == null ? null : owner.getUuid();
    }

    @Override
    public boolean shouldDamage(Explosion explosion, Entity entity) {
        if (ownerUUID != null) {
            if (ownerUUID.equals(entity.getUuid())) {
                return false;
            }
            PlayerEntity owner = entity.getEntityWorld().getPlayerByUuid(ownerUUID);
            if (owner != null && owner.isTeammate(entity)) {
                return false;
            }
        }
        return super.shouldDamage(explosion, entity);
    }
}
