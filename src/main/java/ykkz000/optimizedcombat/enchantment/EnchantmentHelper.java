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

package ykkz000.optimizedcombat.enchantment;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import org.apache.commons.lang3.mutable.MutableFloat;

import java.util.function.BiConsumer;

public class EnchantmentHelper {
    public static int getExplosionLevel(ServerWorld world, ItemStack weaponStack, ItemStack projectileStack) {
        MutableFloat mutableFloat = new MutableFloat(0.0f);
        forEachEnchantment(weaponStack, (enchantment, level) -> ((ExtendedEnchantment) (Object) enchantment.value()).optimizedCombat$modifyProjectileExplosion(world, level, projectileStack, mutableFloat));
        return Math.max(0, mutableFloat.intValue());
    }

    private static void forEachEnchantment(ItemStack stack, BiConsumer<RegistryEntry<Enchantment>, Integer> consumer) {
        ItemEnchantmentsComponent itemEnchantmentsComponent = stack.getOrDefault(DataComponentTypes.ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT);
        for (Object2IntMap.Entry<RegistryEntry<Enchantment>> entry : itemEnchantmentsComponent.getEnchantmentEntries()) {
            consumer.accept(entry.getKey(), entry.getIntValue());
        }
    }
}
