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

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ykkz000.optimizedcombat.enchantment.EnchantmentHelper;
import ykkz000.optimizedcombat.entity.ExtendedProjectileEntity;

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityMixin {
    @Shadow private ItemStack stack;

    @Inject(method = "<init>(Lnet/minecraft/entity/EntityType;DDDLnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)V", at = @At("RETURN"))
    private void onConstruct(EntityType<? extends Entity> type, double x, double y, double z, World world, ItemStack stack, ItemStack weapon, CallbackInfo ci) {
        if (weapon != null && world instanceof ServerWorld serverWorld) {
            int i = EnchantmentHelper.getExplosionLevel(serverWorld, weapon, this.stack);
            if (i > 0) {
                ((ExtendedProjectileEntity) this).optimizedCombat$setExplosionLevel((byte) i);
            }
        }
    }
}
