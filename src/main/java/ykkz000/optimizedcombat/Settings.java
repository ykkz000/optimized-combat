/*
 * Optimized Combat
 * Copyright (C) 2025  ykkz000
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General private License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General private License for more details.
 *
 * You should have received a copy of the GNU General private License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package ykkz000.optimizedcombat;

import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Data
public final class Settings {
    public static final Settings INSTANCE = new Settings();
    private final ItemSettings itemSettings;
    private final HealthSettings healthSettings;
    private final AbsorptionSettings absorptionSettings;
    private final HungerSettings hungerSettings;
    private final InteractionSettings interactionSettings;
    private final PlayerSettings playerSettings;

    private Settings() {
        this(Settings.class.getResourceAsStream("/optimized-combat.properties"));
    }

    private Settings(InputStream inputStream) {
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.itemSettings = new ItemSettings.Builder()
                .maxStackSize(Integer.parseInt(properties.getProperty("optimized-combat.item.max-stack-size", "256")))
                .build();

        this.healthSettings = new HealthSettings.Builder()
                .deltaHealthDifficulty(Integer.parseInt(properties.getProperty("optimized-combat.health.delta-health-difficulty", "4")))
                .deltaHealthExperienceLevel(Integer.parseInt(properties.getProperty("optimized-combat.health.delta-health-experience-level", "2")))
                .stepExperienceLevel(Integer.parseInt(properties.getProperty("optimized-combat.health.step-experience-level", "25")))
                .build();

        this.absorptionSettings = new AbsorptionSettings.Builder()
                .maxAbsorption(Integer.parseInt(properties.getProperty("optimized-combat.absorption.max-absorption", "4")))
                .absorptionFromKill(Integer.parseInt(properties.getProperty("optimized-combat.absorption.absorption-from-kill", "1")))
                .build();

        this.hungerSettings = new HungerSettings.Builder()
                .hungerLevelFromKill(Integer.parseInt(properties.getProperty("optimized-combat.hunger.hunger-level-from-kill", "2")))
                .thresholdHungerLevel(Integer.parseInt(properties.getProperty("optimized-combat.hunger.threshold-hunger-level", "6")))
                .canFoodHealth(Boolean.parseBoolean(properties.getProperty("optimized-combat.hunger.can-food-health", "false")))
                .canFoodHealthHealthLimit(Double.parseDouble(properties.getProperty("optimized-combat.hunger.can-food-health-health-limit", "0.3")))
                .build();

        this.interactionSettings = new InteractionSettings.Builder()
                .baseBlockDistance(Integer.parseInt(properties.getProperty("optimized-combat.interaction.base-block-distance", "-1")))
                .baseEntityDistance(Integer.parseInt(properties.getProperty("optimized-combat.interaction.base-entity-distance", "-1")))
                .swordsBlockDistance(Integer.parseInt(properties.getProperty("optimized-combat.interaction.swords-block-distance", "1")))
                .swordsEntityDistance(Integer.parseInt(properties.getProperty("optimized-combat.interaction.swords-entity-distance", "2")))
                .toolsBlockDistance(Integer.parseInt(properties.getProperty("optimized-combat.interaction.tools-block-distance", "2")))
                .toolsEntityDistance(Integer.parseInt(properties.getProperty("optimized-combat.interaction.tools-entity-distance", "1")))
                .shieldMaxTicks(Integer.parseInt(properties.getProperty("optimized-combat.interaction.shield-max-ticks", "40")))
                .shieldMinCooldownTicks(Integer.parseInt(properties.getProperty("optimized-combat.interaction.shield-min-cooldown-ticks", "15")))
                .shieldMaxCooldownTicks(Integer.parseInt(properties.getProperty("optimized-combat.interaction.shield-max-cooldown-ticks", "30")))
                .build();

        this.playerSettings = new PlayerSettings.Builder()
                .minDamageReduce(Double.parseDouble(properties.getProperty("optimized-combat.player.min-damage-reduce", "0.5")))
                .maxDamageReduce(Double.parseDouble(properties.getProperty("optimized-combat.player.max-damage-reduce", "0.5")))
                .build();
    }

    @Data
    public static class ItemSettings {
        private final int maxStackSize;

        private ItemSettings(Builder builder) {
            this.maxStackSize = builder.maxStackSize;
        }

        private static class Builder {
            private int maxStackSize;

            private Builder() {
                this.maxStackSize = 256;
            }

            private Builder maxStackSize(int maxStackSize) {
                this.maxStackSize = maxStackSize;
                return this;
            }

            private ItemSettings build() {
                return new ItemSettings(this);
            }
        }
    }

    @Data
    public static class HealthSettings {
        private final int deltaHealthDifficulty;
        private final int deltaHealthExperienceLevel;
        private final int stepExperienceLevel;

        private HealthSettings(Builder builder) {
            this.deltaHealthDifficulty = builder.deltaHealthDifficulty;
            this.deltaHealthExperienceLevel = builder.deltaHealthExperienceLevel;
            this.stepExperienceLevel = builder.stepExperienceLevel;
        }

        private static class Builder {
            private int deltaHealthDifficulty;
            private int deltaHealthExperienceLevel;
            private int stepExperienceLevel;

            private Builder() {
                this.deltaHealthDifficulty = 4;
                this.deltaHealthExperienceLevel = 2;
                this.stepExperienceLevel = 25;
            }

            private Builder deltaHealthDifficulty(int deltaHealthDifficulty) {
                this.deltaHealthDifficulty = deltaHealthDifficulty;
                return this;
            }

            private Builder deltaHealthExperienceLevel(int deltaHealthExperienceLevel) {
                this.deltaHealthExperienceLevel = deltaHealthExperienceLevel;
                return this;
            }

            private Builder stepExperienceLevel(int stepExperienceLevel) {
                this.stepExperienceLevel = stepExperienceLevel;
                return this;
            }

            private HealthSettings build() {
                return new HealthSettings(this);
            }
        }
    }

    @Data
    public static class AbsorptionSettings {
        private final int maxAbsorption;
        private final int absorptionFromKill;

        private AbsorptionSettings(Builder builder) {
            this.maxAbsorption = builder.maxAbsorption;
            this.absorptionFromKill = builder.absorptionFromKill;
        }

        private static class Builder {
            private int maxAbsorption;
            private int absorptionFromKill;

            private Builder() {
                this.maxAbsorption = 4;
                this.absorptionFromKill = 1;
            }

            private Builder maxAbsorption(int maxAbsorption) {
                this.maxAbsorption = maxAbsorption;
                return this;
            }

            private Builder absorptionFromKill(int absorptionFromKill) {
                this.absorptionFromKill = absorptionFromKill;
                return this;
            }

            private AbsorptionSettings build() {
                return new AbsorptionSettings(this);
            }
        }
    }

    @Data
    public static class HungerSettings {
        private final int hungerLevelFromKill;
        private final int thresholdHungerLevel;
        private final boolean canFoodHealth;
        private final double canFoodHealthHealthLimit;

        private HungerSettings(Builder builder) {
            this.hungerLevelFromKill = builder.hungerLevelFromKill;
            this.thresholdHungerLevel = builder.thresholdHungerLevel;
            this.canFoodHealth = builder.canFoodHealth;
            this.canFoodHealthHealthLimit = builder.canFoodHealthHealthLimit;
        }

        private static class Builder {
            private int hungerLevelFromKill;
            private int thresholdHungerLevel;
            private boolean canFoodHealth;
            private double canFoodHealthHealthLimit;

            private Builder() {
                this.hungerLevelFromKill = 2;
                this.thresholdHungerLevel = 6;
                this.canFoodHealth = false;
            }

            private Builder hungerLevelFromKill(int hungerLevelFromKill) {
                this.hungerLevelFromKill = hungerLevelFromKill;
                return this;
            }

            private Builder thresholdHungerLevel(int thresholdHungerLevel) {
                this.thresholdHungerLevel = thresholdHungerLevel;
                return this;
            }

            private Builder canFoodHealth(boolean canFoodHealth) {
                this.canFoodHealth = canFoodHealth;
                return this;
            }

            private Builder canFoodHealthHealthLimit(double canFoodHealthHealthLimit) {
                this.canFoodHealthHealthLimit = canFoodHealthHealthLimit;
                return this;
            }

            private HungerSettings build() {
                return new HungerSettings(this);
            }
        }
    }

    @Data
    public static class InteractionSettings {
        private final int baseBlockDistance;
        private final int baseEntityDistance;
        private final int swordsBlockDistance;
        private final int swordsEntityDistance;
        private final int toolsBlockDistance;
        private final int toolsEntityDistance;
        private final int shieldMaxTicks;
        private final int shieldMinCooldownTicks;
        private final int shieldMaxCooldownTicks;

        private InteractionSettings(Builder builder) {
            this.baseBlockDistance = builder.baseBlockDistance;
            this.baseEntityDistance = builder.baseEntityDistance;
            this.swordsBlockDistance = builder.swordsBlockDistance;
            this.swordsEntityDistance = builder.swordsEntityDistance;
            this.toolsBlockDistance = builder.toolsBlockDistance;
            this.toolsEntityDistance = builder.toolsEntityDistance;
            this.shieldMaxTicks = builder.shieldMaxTicks;
            this.shieldMinCooldownTicks = builder.shieldMinCooldownTicks;
            this.shieldMaxCooldownTicks = builder.shieldMaxCooldownTicks;
        }

        private static class Builder {
            private int baseBlockDistance;
            private int baseEntityDistance;
            private int swordsBlockDistance;
            private int swordsEntityDistance;
            private int toolsBlockDistance;
            private int toolsEntityDistance;
            private int shieldMaxTicks;
            private int shieldMinCooldownTicks;
            private int shieldMaxCooldownTicks;

            private Builder() {
                this.baseBlockDistance = -1;
                this.baseEntityDistance = -1;
                this.swordsBlockDistance = 1;
                this.swordsEntityDistance = 2;
                this.toolsBlockDistance = 2;
                this.toolsEntityDistance = 1;
                this.shieldMaxTicks = 40;
                this.shieldMinCooldownTicks = 15;
                this.shieldMaxCooldownTicks = 30;
            }

            private Builder baseBlockDistance(int baseBlockDistance) {
                this.baseBlockDistance = baseBlockDistance;
                return this;
            }

            private Builder baseEntityDistance(int baseEntityDistance) {
                this.baseEntityDistance = baseEntityDistance;
                return this;
            }

            private Builder swordsBlockDistance(int swordsBlockDistance) {
                this.swordsBlockDistance = swordsBlockDistance;
                return this;
            }

            private Builder swordsEntityDistance(int swordsEntityDistance) {
                this.swordsEntityDistance = swordsEntityDistance;
                return this;
            }

            private Builder toolsBlockDistance(int toolsBlockDistance) {
                this.toolsBlockDistance = toolsBlockDistance;
                return this;
            }

            private Builder toolsEntityDistance(int toolsEntityDistance) {
                this.toolsEntityDistance = toolsEntityDistance;
                return this;
            }

            private Builder shieldMaxTicks(int shieldMaxTicks) {
                this.shieldMaxTicks = shieldMaxTicks;
                return this;
            }

            private Builder shieldMinCooldownTicks(int shieldMinCooldownTicks) {
                this.shieldMinCooldownTicks = shieldMinCooldownTicks;
                return this;
            }

            private Builder shieldMaxCooldownTicks(int shieldMaxCooldownTicks) {
                this.shieldMaxCooldownTicks = shieldMaxCooldownTicks;
                return this;
            }

            private InteractionSettings build() {
                return new InteractionSettings(this);
            }
        }
    }

    @Data
    public static class PlayerSettings {
        private final double minDamageReduce;
        private final double maxDamageReduce;

        private PlayerSettings(Builder builder) {
            this.minDamageReduce = builder.minDamageReduce;
            this.maxDamageReduce = builder.maxDamageReduce;
        }

        private static class Builder {
            private double minDamageReduce;
            private double maxDamageReduce;

            private Builder() {
                this.minDamageReduce = 0.5;
                this.maxDamageReduce = 0.5;
            }

            private Builder minDamageReduce(double minDamageReduce) {
                this.minDamageReduce = minDamageReduce;
                return this;
            }

            private Builder maxDamageReduce(double maxDamageReduce) {
                this.maxDamageReduce = maxDamageReduce;
                return this;
            }

            private PlayerSettings build() {
                return new PlayerSettings(this);
            }
        }
    }
}
