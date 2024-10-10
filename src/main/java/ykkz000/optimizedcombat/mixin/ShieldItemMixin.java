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

package ykkz000.optimizedcombat.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShieldItem.class)
public abstract class ShieldItemMixin extends Item {
    @Unique
    private static final int MAX_SHIELD_USING_TIME = 40;
    public ShieldItemMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "getMaxUseTime(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/LivingEntity;)I", at = @At("HEAD"), cancellable = true)
    public void getMaxUseTime(ItemStack stack, LivingEntity user, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(MAX_SHIELD_USING_TIME);
    }

    @Override
    @Intrinsic(displace = true)
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof PlayerEntity player) {
            player.getItemCooldownManager().set((ShieldItem) (Object) this, getCoolDownTick(0));
        }
        return stack;
    }

    @Override
    @Intrinsic(displace = true)
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity player) {
            player.getItemCooldownManager().set((ShieldItem) (Object) this, getCoolDownTick(remainingUseTicks));
        }
    }

    @Unique
    private static int getCoolDownTick(int remainingUseTicks){
        return 30 - 15 * remainingUseTicks / MAX_SHIELD_USING_TIME;
    }
}
