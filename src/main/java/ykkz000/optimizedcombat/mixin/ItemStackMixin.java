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

package ykkz000.optimizedcombat.mixin;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.ComponentChanges;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.dynamic.Codecs;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import ykkz000.optimizedcombat.config.OptimizedCombatConfiguration;

import java.util.function.Supplier;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Final
    @Shadow
    public static Codec<RegistryEntry<Item>> ITEM_CODEC;
    @ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lcom/mojang/serialization/Codec;lazyInitialized(Ljava/util/function/Supplier;)Lcom/mojang/serialization/Codec;", ordinal = 0))
    private static Supplier<Codec<ItemStack>> modifyMaxStackSize(Supplier<Codec<ItemStack>> delegate) {
        return () -> RecordCodecBuilder.create(
                instance -> instance.group(
                                ITEM_CODEC.fieldOf("id").forGetter(ItemStack::getRegistryEntry),
                                Codecs.rangedInt(1, OptimizedCombatConfiguration.INSTANCE.getItem().getMaxStackSize()).fieldOf("count").orElse(1).forGetter(ItemStack::getCount),
                                ComponentChanges.CODEC.optionalFieldOf("components", ComponentChanges.EMPTY).forGetter(ItemStack::getComponentChanges)
                        )
                        .apply(instance, ItemStack::new)
        );
    }
}
