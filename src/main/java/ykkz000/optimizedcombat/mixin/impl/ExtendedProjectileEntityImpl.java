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

package ykkz000.optimizedcombat.mixin.impl;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ykkz000.optimizedcombat.entity.ExtendedProjectileEntity;
import ykkz000.optimizedcombat.world.explosion.ExplosionEnchantmentExplosionBehavior;

@Mixin(ProjectileEntity.class)
public abstract class ExtendedProjectileEntityImpl extends Entity implements ExtendedProjectileEntity {
    @Shadow public abstract @Nullable Entity getOwner();

    @Unique
    private int explosionLevel = 0;

    protected ExtendedProjectileEntityImpl(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    @Unique
    public int optimizedCombat$getExplosionLevel() {
        return explosionLevel;
    }

    @Override
    @Unique
    public void optimizedCombat$setExplosionLevel(int level) {
        explosionLevel = level;
    }

    @Inject(method = "writeCustomDataToNbt(Lnet/minecraft/nbt/NbtCompound;)V", at = @At("RETURN"))
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        explosionLevel = nbt.getInt("OptimizedCombat$ExplosionLevel");
    }

    @Inject(method = "readCustomDataFromNbt(Lnet/minecraft/nbt/NbtCompound;)V", at = @At("RETURN"))
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt("OptimizedCombat$ExplosionLevel", explosionLevel);
    }

    @Inject(method = "onCollision(Lnet/minecraft/util/hit/HitResult;)V", at = @At("RETURN"))
    protected void onCollision(HitResult hitResult, CallbackInfo ci) {
        if (hitResult.getType() != HitResult.Type.MISS) {
            if (explosionLevel > 0) {
                this.getWorld().createExplosion(this, Explosion.createDamageSource(this.getWorld(), this.getOwner()), new ExplosionEnchantmentExplosionBehavior(), hitResult.getPos().x, hitResult.getPos().y, hitResult.getPos().z, optimizedCombat$getExplosionLevel() * 1.0f, false, World.ExplosionSourceType.TNT);
                if (!this.isRemoved())
                {
                    this.remove(RemovalReason.KILLED);
                }
            }
        }
    }
}
