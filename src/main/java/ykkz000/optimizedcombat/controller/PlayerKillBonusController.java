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

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import ykkz000.cobblestone.api.core.annotation.AutoBootstrap;
import ykkz000.optimizedcombat.config.OptimizedCombatConfiguration;

@AutoBootstrap
public class PlayerKillBonusController {
    static {
        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killedEntity) -> {
            if (entity.isPlayer() && entity instanceof PlayerEntity player) {
                if (player.isSpectator() || player.isCreative() || player.isDead()) return;
                player.setAbsorptionAmount(player.getAbsorptionAmount() + OptimizedCombatConfiguration.INSTANCE.getAbsorption().getAbsorptionFromKill());
                HungerManager hungerManager = player.getHungerManager();
                hungerManager.setFoodLevel(hungerManager.getFoodLevel() + OptimizedCombatConfiguration.INSTANCE.getHunger().getHungerLevelFromKill());
            }
        });
    }
}
