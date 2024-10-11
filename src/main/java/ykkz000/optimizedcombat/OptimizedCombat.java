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
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import ykkz000.optimizedcombat.component.EnchantmentEffectComponentTypes;
import ykkz000.optimizedcombat.enchantment.Enchantments;
import ykkz000.optimizedcombat.event.ServerPlayerTickEvents;
import ykkz000.optimizedcombat.util.EntityUtils;

public class OptimizedCombat implements ModInitializer {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "optimized-combat";
    public static final Identifier ATTRIBUTE_MODIFIER_GENERIC_MAX_ABSORPTION_OPTIMIZE = Identifier.of(MOD_ID, "generic_max_absorption_optimize");
    public static final Identifier ATTRIBUTE_MODIFIER_GENERIC_MAX_HEALTH_OPTIMIZE = Identifier.of(MOD_ID, "generic_max_health_optimize");
    public static final Identifier ATTRIBUTE_MODIFIER_PLAYER_BLOCK_INTERACTION_RANGE_OPTIMIZE = Identifier.of(MOD_ID, "player_block_interaction_range_optimize");
    public static final Identifier ATTRIBUTE_MODIFIER_PLAYER_ENTITY_INTERACTION_RANGE_OPTIMIZE = Identifier.of(MOD_ID, "player_entity_interaction_range_optimize");
    public static final Identifier ATTRIBUTE_MODIFIER_GENERIC_ATTACK_DAMAGE_OPTIMIZE = Identifier.of(MOD_ID, "generic_attack_damage_optimize");
    public static final Identifier ATTRIBUTE_MODIFIER_GENERIC_ATTACK_SPEED_OPTIMIZE = Identifier.of(MOD_ID, "generic_attack_speed_optimize");
    public static final Identifier ATTRIBUTE_MODIFIER_GENERIC_MOVEMENT_SPEED_OPTIMIZE = Identifier.of(MOD_ID, "generic_attack_movement_optimize");
    public static final Identifier ATTRIBUTE_MODIFIER_GENERIC_ARMOR_OPTIMIZE = Identifier.of(MOD_ID, "generic_armor_optimize");

    @Override
    public void onInitialize() {
        EnchantmentEffectComponentTypes.bootstrap();
        Enchantments.bootstrap();
        ServerPlayerTickEvents.START_TICK.register(player -> {
            if (player.isSpectator() || player.isCreative() || player.isDead()) return ActionResult.PASS;
            boolean success = EntityUtils.refreshAttributeModifier(player,
                    EntityAttributes.GENERIC_MAX_ABSORPTION,
                    ATTRIBUTE_MODIFIER_GENERIC_MAX_ABSORPTION_OPTIMIZE,
                    true,
                    2.0,
                    EntityAttributeModifier.Operation.ADD_VALUE);
            if (!success) {
                LOGGER.error("Failed to modify player {}'s attribute EntityAttributes.GENERIC_MAX_ABSORPTION.", player.getName());
            }
            double healthDelta = Math.min(0.0, -4.0 * player.getServerWorld().getDifficulty().getId() + 2.0 * (double) (player.experienceLevel / 5));
            success = EntityUtils.refreshAttributeModifier(player,
                    EntityAttributes.GENERIC_MAX_HEALTH,
                    ATTRIBUTE_MODIFIER_GENERIC_MAX_HEALTH_OPTIMIZE,
                    true,
                    healthDelta,
                    EntityAttributeModifier.Operation.ADD_VALUE);
            if (!success) {
                LOGGER.error("Failed to modify player {}'s attribute EntityAttributes.GENERIC_MAX_HEALTH.", player.getName());
            }
            double interactionRange = getInteractionRange(player);
            double deltaBlockInteractionRange = interactionRange - player.getBlockInteractionRange();
            double deltaEntityInteractionRange = interactionRange - player.getEntityInteractionRange();
            success = EntityUtils.refreshAttributeModifier(player,
                    EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE,
                    ATTRIBUTE_MODIFIER_PLAYER_BLOCK_INTERACTION_RANGE_OPTIMIZE,
                    true,
                    deltaBlockInteractionRange,
                    EntityAttributeModifier.Operation.ADD_VALUE);
            if (!success) {
                LOGGER.error("Failed to modify player {}'s attribute EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE.", player.getName());
            }
            success = EntityUtils.refreshAttributeModifier(player,
                    EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE,
                    ATTRIBUTE_MODIFIER_PLAYER_ENTITY_INTERACTION_RANGE_OPTIMIZE,
                    true,
                    deltaEntityInteractionRange,
                    EntityAttributeModifier.Operation.ADD_VALUE);
            if (!success) {
                LOGGER.error("Failed to modify player {}'s attribute EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE.", player.getName());
            }
            return ActionResult.PASS;
        });
        ServerPlayerTickEvents.END_TICK.register(player -> {
            if (player.isSpectator() || player.isCreative() || player.isDead()) return ActionResult.PASS;
            if (player.getHungerManager().getFoodLevel() < 0.3 * 20) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 1, 1));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 1, 1));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 1, 1));
            }
            double percentageOfHealthLost = 1.0 - player.getHealth() / player.getMaxHealth();
            boolean success = EntityUtils.refreshAttributeModifier(player,
                    EntityAttributes.GENERIC_ATTACK_DAMAGE,
                    ATTRIBUTE_MODIFIER_GENERIC_ATTACK_DAMAGE_OPTIMIZE,
                    true,
                    percentageOfHealthLost * 0.5,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
            if (!success) {
                LOGGER.error("Failed to modify player {}'s attribute EntityAttributes.GENERIC_ATTACK_DAMAGE.", player.getName());
            }
            success = EntityUtils.refreshAttributeModifier(player,
                    EntityAttributes.GENERIC_ATTACK_SPEED,
                    ATTRIBUTE_MODIFIER_GENERIC_ATTACK_SPEED_OPTIMIZE,
                    true,
                    percentageOfHealthLost * 0.5,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
            if (!success) {
                LOGGER.error("Failed to modify player {}'s attribute EntityAttributes.GENERIC_ATTACK_SPEED.", player.getName());
            }
            success = EntityUtils.refreshAttributeModifier(player,
                    EntityAttributes.GENERIC_MOVEMENT_SPEED,
                    ATTRIBUTE_MODIFIER_GENERIC_MOVEMENT_SPEED_OPTIMIZE,
                    true,
                    percentageOfHealthLost * 0.5,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
            if (!success) {
                LOGGER.error("Failed to modify player {}'s attribute EntityAttributes.GENERIC_MOVEMENT_SPEED.", player.getName());
            }
            success = EntityUtils.refreshAttributeModifier(player,
                    EntityAttributes.GENERIC_ARMOR,
                    ATTRIBUTE_MODIFIER_GENERIC_ARMOR_OPTIMIZE,
                    true,
                    percentageOfHealthLost * 0.5,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
            if (!success) {
                LOGGER.error("Failed to modify player {}'s attribute EntityAttributes.GENERIC_ARMOR.", player.getName());
            }
            return ActionResult.PASS;
        });
        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killedEntity) -> {
            if (entity.isPlayer() && entity instanceof PlayerEntity player) {
                if (player.isSpectator() || player.isCreative() || player.isDead()) return;
                player.setAbsorptionAmount(player.getAbsorptionAmount() + 1.0f);
                HungerManager hungerManager = player.getHungerManager();
                hungerManager.setFoodLevel(hungerManager.getFoodLevel() + 2);
            }
        });
    }

    private static double getInteractionRange(ServerPlayerEntity player) {
        double weaponDistance = 0.0;
        ItemStack mainHandItemStack = player.getMainHandStack();
        Item mainHandItem = mainHandItemStack.getItem();
        if (mainHandItem instanceof SwordItem) {
            weaponDistance = 2.0;
        } else if (mainHandItem instanceof AxeItem) {
            weaponDistance = 1.5;
        } else if (mainHandItem instanceof PickaxeItem || mainHandItem instanceof ShovelItem || mainHandItem instanceof HoeItem) {
            weaponDistance = 1.0;
        } else if (!mainHandItemStack.isEmpty()) {
            weaponDistance = 0.5;
        }
        return 3.5f + weaponDistance + (player.isCreative() ? 0.5f : 0.0f);
    }
}
