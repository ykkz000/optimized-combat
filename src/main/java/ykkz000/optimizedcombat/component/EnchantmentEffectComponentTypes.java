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

package ykkz000.optimizedcombat.component;

import net.minecraft.component.ComponentType;
import net.minecraft.enchantment.effect.EnchantmentEffectEntry;
import net.minecraft.enchantment.effect.EnchantmentValueEffect;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import ykkz000.optimizedcombat.OptimizedCombat;

import java.util.List;
import java.util.function.UnaryOperator;

public class EnchantmentEffectComponentTypes {
    public static final ComponentType<List<EnchantmentEffectEntry<EnchantmentValueEffect>>> EXPLOSION_LEVEL = register(
            "explosion_level", builder -> builder.codec(EnchantmentEffectEntry.createCodec(EnchantmentValueEffect.CODEC, LootContextTypes.ENCHANTED_ENTITY).listOf())
    );
    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, Identifier.of(OptimizedCombat.MOD_ID, id), builderOperator.apply(ComponentType.builder()).build());
    }

    public static void bootstrap(){}
}
