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

package ykkz000.optimizedcombat.controller;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import ykkz000.cobblestone.api.core.annotation.AutoBootstrap;
import ykkz000.cobblestone.api.event.ServerPlayerTickEvents;
import ykkz000.optimizedcombat.config.OptimizedCombatConfiguration;

@AutoBootstrap
public class PlayerHungerPenaltyController {
    static {
        ServerPlayerTickEvents.END_SERVER_PLAYER_TICK.register(player -> {
            if (player.isSpectator() || player.isCreative() || player.isDead()) {
                return;
            }
            updateHungryPenalty(player);
        });
    }

    private static void updateHungryPenalty(PlayerEntity player) {
        if (player.getHungerManager().getFoodLevel() < OptimizedCombatConfiguration.INSTANCE.getHunger().getThresholdHungerLevel()) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 10, 0));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 10, 0));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 10, 0));
        }
    }
}
