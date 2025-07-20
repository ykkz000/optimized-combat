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

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import ykkz000.cobblestone.api.core.annotation.AutoBootstrap;
import ykkz000.cobblestone.api.event.ServerPlayerTickEvents;
import ykkz000.cobblestone.api.util.EntityUtils;
import ykkz000.optimizedcombat.Settings;

import static ykkz000.optimizedcombat.OptimizedCombat.MOD_ID;

@AutoBootstrap
public class PlayerInitialStateController {
    public static final Identifier BASE_BLOCK_INTERACTION_RANGE_MODIFIER_ID = Identifier.of(MOD_ID, "base_block_interaction_range");
    public static final Identifier BASE_ENTITY_INTERACTION_RANGE_MODIFIER_ID = Identifier.of(MOD_ID, "base_entity_interaction_range");
    public static final Identifier MAX_ABSORPTION_MODIFIER_ID = Identifier.of(MOD_ID, "max_absorption");
    public static final Identifier MAX_HEALTH_MODIFIER_ID = Identifier.of(MOD_ID, "max_health");

    static {
        ServerPlayerTickEvents.START_SERVER_PLAYER_TICK.register(player -> {
            if (player.isSpectator() || player.isCreative() || player.isDead()) return;
            updateInitialState(player);
        });
    }

    private static void updateInitialState(ServerPlayerEntity player) {
        EntityUtils.refreshAttributeModifier(player,
                EntityAttributes.GENERIC_MAX_ABSORPTION,
                true,
                new EntityAttributeModifier(MAX_ABSORPTION_MODIFIER_ID, Settings.INSTANCE.getAbsorptionSettings().getMaxAbsorption(), EntityAttributeModifier.Operation.ADD_VALUE));
        double healthDelta = calcHealthDelta(player);
        EntityUtils.refreshAttributeModifier(player,
                EntityAttributes.GENERIC_MAX_HEALTH,
                true,
                new EntityAttributeModifier(MAX_HEALTH_MODIFIER_ID, healthDelta, EntityAttributeModifier.Operation.ADD_VALUE));
        EntityUtils.refreshAttributeModifier(player,
                EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE,
                true,
                new EntityAttributeModifier(BASE_BLOCK_INTERACTION_RANGE_MODIFIER_ID, Settings.INSTANCE.getInteractionSettings().getBaseBlockDistance(), EntityAttributeModifier.Operation.ADD_VALUE));
        EntityUtils.refreshAttributeModifier(player,
                EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE,
                true,
                new EntityAttributeModifier(BASE_ENTITY_INTERACTION_RANGE_MODIFIER_ID, Settings.INSTANCE.getInteractionSettings().getBaseEntityDistance(), EntityAttributeModifier.Operation.ADD_VALUE));
    }

    private static double calcHealthDelta(ServerPlayerEntity player) {
        double healthDifficultyDelta = -Settings.INSTANCE.getHealthSettings().getDeltaHealthDifficulty() * player.getServerWorld().getDifficulty().getId();
        double healthExperienceLevelDelta = Settings.INSTANCE.getHealthSettings().getDeltaHealthExperienceLevel() * Math.floor((double) player.experienceLevel / Settings.INSTANCE.getHealthSettings().getStepExperienceLevel());
        return Math.min(0.0, healthDifficultyDelta + healthExperienceLevelDelta);
    }
}
