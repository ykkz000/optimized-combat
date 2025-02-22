/*
 * Optimized Combat
 * Copyright (C) 2025  ykkz000
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

package ykkz000.optimizedcombat.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import ykkz000.optimizedcombat.OptimizedCombatSettings;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @ModifyVariable(method = "damage(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/damage/DamageSource;F)Z", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private float reduceDamage(float damage) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        double rate = player.getHealth() / player.getMaxHealth();
        double damageMaxDelta = OptimizedCombatSettings.INSTANCE.getPlayerSettings().getMaxDamageReduce() - OptimizedCombatSettings.INSTANCE.getPlayerSettings().getMinDamageReduce();
        return damage * (float) (1.0f - (OptimizedCombatSettings.INSTANCE.getPlayerSettings().getMaxDamageReduce() - damageMaxDelta * rate));
    }
}
