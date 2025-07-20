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

import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.dynamic.Codecs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import ykkz000.optimizedcombat.Settings;

import java.util.function.UnaryOperator;

@Mixin(DataComponentTypes.class)
public abstract class DataComponentTypesMixin {
    @ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/component/DataComponentTypes;register(Ljava/lang/String;Ljava/util/function/UnaryOperator;)Lnet/minecraft/component/ComponentType;", ordinal = 1), index = 1)
    private static UnaryOperator<ComponentType.Builder<Integer>> modifyMaxStackSize(String id, UnaryOperator<ComponentType.Builder<Integer>> builderOperator) {
        return builder->builder.codec(Codecs.rangedInt(1, Settings.INSTANCE.getItemSettings().getMaxStackSize())).packetCodec(PacketCodecs.VAR_INT);
    }
}
