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

package ykkz000.optimizedcombat.mixin.extend;

import net.minecraft.component.ComponentType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEffectEntry;
import net.minecraft.enchantment.effect.EnchantmentValueEffect;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import ykkz000.optimizedcombat.component.EnchantmentEffectComponentTypes;
import ykkz000.optimizedcombat.enchantment.ExtendedEnchantment;

import java.util.List;

@Mixin(Enchantment.class)
public abstract class ExtendedEnchantmentImpl implements ExtendedEnchantment {
    @Shadow
    protected abstract void modifyValue(ComponentType<List<EnchantmentEffectEntry<EnchantmentValueEffect>>> type, ServerWorld world, int level, ItemStack stack, MutableFloat value);

    public void optimizedCombat$modifyProjectileExplosion(ServerWorld world, int level, ItemStack stack, MutableFloat projectileExplosion) {
        this.modifyValue(EnchantmentEffectComponentTypes.EXPLOSION_LEVEL, world, level, stack, projectileExplosion);
    }
}
