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
package ykkz000.optimizedcombat;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import ykkz000.optimizedcombat.event.ServerPlayerTickEvents;
import ykkz000.optimizedcombat.util.EntityUtils;

public class OptimizedCombat implements ModInitializer {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "optimized-combat";
    public static final Identifier PLAYER_BASE_BLOCK_INTERACTION_RANGE_MODIFIER_ID = Identifier.of(OptimizedCombat.MOD_ID, "player_block_interaction_range");
    public static final Identifier PLAYER_BASE_ENTITY_INTERACTION_RANGE_MODIFIER_ID = Identifier.of(OptimizedCombat.MOD_ID, "player_entity_interaction_range");
    public static final Identifier PLAYER_SWORDS_BLOCK_INTERACTION_RANGE_MODIFIER_ID = Identifier.of(OptimizedCombat.MOD_ID, "player_swords_block_interaction_range");
    public static final Identifier PLAYER_SWORDS_ENTITY_INTERACTION_RANGE_MODIFIER_ID = Identifier.of(OptimizedCombat.MOD_ID, "player_swords_entity_interaction_range");
    public static final Identifier PLAYER_TOOLS_BLOCK_INTERACTION_RANGE_MODIFIER_ID = Identifier.of(OptimizedCombat.MOD_ID, "player_tools_block_interaction_range");
    public static final Identifier PLAYER_TOOLS_ENTITY_INTERACTION_RANGE_MODIFIER_ID = Identifier.of(OptimizedCombat.MOD_ID, "player_tools_entity_interaction_range");
    public static final Identifier ATTRIBUTE_MODIFIER_GENERIC_MAX_ABSORPTION_OPTIMIZE = Identifier.of(MOD_ID, "generic_max_absorption_optimize");
    public static final Identifier ATTRIBUTE_MODIFIER_GENERIC_MAX_HEALTH_OPTIMIZE = Identifier.of(MOD_ID, "generic_max_health_optimize");

    @Override
    public void onInitialize() {
        ServerPlayerTickEvents.START_TICK.register(player -> {
            if (player.isSpectator() || player.isCreative() || player.isDead()) return ActionResult.PASS;
            updateInitialState(player);
            return ActionResult.PASS;
        });
        ServerPlayerTickEvents.END_TICK.register(player -> {
            if (player.isSpectator() || player.isCreative() || player.isDead()) return ActionResult.PASS;
            updateHungryPenalty(player);
            return ActionResult.PASS;
        });
        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killedEntity) -> {
            if (entity.isPlayer() && entity instanceof PlayerEntity player) {
                if (player.isSpectator() || player.isCreative() || player.isDead()) return;
                player.setAbsorptionAmount(player.getAbsorptionAmount() + OptimizedCombatSettings.INSTANCE.getAbsorptionSettings().getAbsorptionFromKill());
                HungerManager hungerManager = player.getHungerManager();
                hungerManager.setFoodLevel(hungerManager.getFoodLevel() + OptimizedCombatSettings.INSTANCE.getHungerSettings().getHungerLevelFromKill());
            }
        });
    }

    private void updateInitialState(ServerPlayerEntity player) {
        boolean success = EntityUtils.refreshAttributeModifier(player,
                EntityAttributes.GENERIC_MAX_ABSORPTION,
                ATTRIBUTE_MODIFIER_GENERIC_MAX_ABSORPTION_OPTIMIZE,
                true,
                OptimizedCombatSettings.INSTANCE.getAbsorptionSettings().getMaxAbsorption(),
                EntityAttributeModifier.Operation.ADD_VALUE);
        if (!success) {
            LOGGER.error("Failed to modify player {}'s attribute EntityAttributes.GENERIC_MAX_ABSORPTION.", player.getName());
        }
        double healthDifficultyDelta = -1.0 * OptimizedCombatSettings.INSTANCE.getHealthSettings().getDeltaHealthDifficulty() * player.getServerWorld().getDifficulty().getId();
        double healthExperienceLevelDelta = OptimizedCombatSettings.INSTANCE.getHealthSettings().getDeltaHealthExperienceLevel() * Math.floor((double) player.experienceLevel / OptimizedCombatSettings.INSTANCE.getHealthSettings().getStepExperienceLevel());
        double healthDelta = Math.min(0.0, healthDifficultyDelta + healthExperienceLevelDelta);
        success = EntityUtils.refreshAttributeModifier(player,
                EntityAttributes.GENERIC_MAX_HEALTH,
                ATTRIBUTE_MODIFIER_GENERIC_MAX_HEALTH_OPTIMIZE,
                true,
                healthDelta,
                EntityAttributeModifier.Operation.ADD_VALUE);
        if (!success) {
            LOGGER.error("Failed to modify player {}'s attribute EntityAttributes.GENERIC_MAX_HEALTH.", player.getName());
        }
        success = EntityUtils.refreshAttributeModifier(player,
                EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE,
                PLAYER_BASE_BLOCK_INTERACTION_RANGE_MODIFIER_ID,
                true,
                OptimizedCombatSettings.INSTANCE.getInteractionSettings().getBaseBlockDistance(),
                EntityAttributeModifier.Operation.ADD_VALUE);
        if (!success) {
            LOGGER.error("Failed to modify player {}'s attribute EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE.", player.getName());
        }
        success = EntityUtils.refreshAttributeModifier(player,
                EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE,
                PLAYER_BASE_ENTITY_INTERACTION_RANGE_MODIFIER_ID,
                true,
                OptimizedCombatSettings.INSTANCE.getInteractionSettings().getBaseEntityDistance(),
                EntityAttributeModifier.Operation.ADD_VALUE);
        if (!success) {
            LOGGER.error("Failed to modify player {}'s attribute EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE.", player.getName());
        }
    }

    private void updateHungryPenalty(PlayerEntity player) {
        if (player.getHungerManager().getFoodLevel() < OptimizedCombatSettings.INSTANCE.getHungerSettings().getThresholdHungerLevel()) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 10, 0));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 10, 0));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 10, 0));
        }
    }
}
