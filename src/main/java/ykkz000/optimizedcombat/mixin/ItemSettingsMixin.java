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

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ykkz000.optimizedcombat.config.OptimizedCombatConfiguration;

@Mixin(Item.Settings.class)
public abstract class ItemSettingsMixin {
    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(CallbackInfo ci) {
        ((Item.Settings) (Object) this).component(DataComponentTypes.MAX_STACK_SIZE, OptimizedCombatConfiguration.INSTANCE.getItem().getMaxStackSize());
    }

    @Inject(method = "maxCount(I)Lnet/minecraft/item/Item$Settings;", at = @At("HEAD"), cancellable = true)
    private void maxCount(int maxCount, CallbackInfoReturnable<Item.Settings> cir) {
        cir.setReturnValue(((Item.Settings) (Object) this).component(DataComponentTypes.MAX_STACK_SIZE, OptimizedCombatConfiguration.INSTANCE.getItem().getMaxStackSize()));
    }
}
