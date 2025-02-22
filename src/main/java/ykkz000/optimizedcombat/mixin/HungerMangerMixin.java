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

package ykkz000.optimizedcombat.mixin;

import net.minecraft.entity.player.HungerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import ykkz000.optimizedcombat.OptimizedCombatSettings;

@Mixin(HungerManager.class)
public abstract class HungerMangerMixin {
    @Redirect(method = "update(Lnet/minecraft/server/network/ServerPlayerEntity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;canFoodHeal()Z"))
    private boolean canFoodHeal(ServerPlayerEntity instance) {
        return OptimizedCombatSettings.INSTANCE.getHungerSettings().isCanFoodHealth() && instance.canFoodHeal();
    }
}
