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

package ykkz000.optimizedcombat.config;

import lombok.Data;
import ykkz000.cobblestone.api.core.ConfigurationPreloader;
import ykkz000.cobblestone.api.core.annotation.Configuration;
import ykkz000.cobblestone.api.core.annotation.ConfigurationInstance;

@Configuration(path = {"optimized-combat.json", "classpath:/optimized-combat.json"})
@Data
public class OptimizedCombatConfiguration {
    @ConfigurationInstance
    public static OptimizedCombatConfiguration INSTANCE;

    static {
        ConfigurationPreloader.preLoadConfiguration();
    }

    private ItemSettings item;
    private HealthSettings health;
    private AbsorptionSettings absorption;
    private HungerSettings hunger;
    private InteractionSettings interaction;
    private PlayerSettings player;

    @Data
    public static class ItemSettings {
        private int maxStackSize;
    }

    @Data
    public static class HealthSettings {
        private int deltaHealthDifficulty;
        private int deltaHealthExperienceLevel;
        private int stepExperienceLevel;
    }

    @Data
    public static class AbsorptionSettings {
        private int maxAbsorption;
        private int absorptionFromKill;
    }

    @Data
    public static class HungerSettings {
        private int hungerLevelFromKill;
        private int thresholdHungerLevel;
        private boolean canFoodHealth;
        private double canFoodHealthLimit;
    }

    @Data
    public static class InteractionSettings {
        private int baseBlockDistance;
        private int baseEntityDistance;
        private int swordsBlockDistance;
        private int swordsEntityDistance;
        private int toolsBlockDistance;
        private int toolsEntityDistance;
        private int shieldMaxTicks;
        private int shieldMinCooldownTicks;
        private int shieldMaxCooldownTicks;
    }

    @Data
    public static class PlayerSettings {
        private double minDamageReduce;
        private double maxDamageReduce;
    }
}
