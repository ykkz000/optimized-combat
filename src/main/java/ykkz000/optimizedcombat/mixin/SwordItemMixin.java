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

import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import ykkz000.optimizedcombat.OptimizedCombat;
import ykkz000.optimizedcombat.OptimizedCombatSettings;

@Mixin(SwordItem.class)
public abstract class SwordItemMixin {
    @Unique
    private static final Identifier SWORDS_BLOCK_INTERACTION_RANGE_MODIFIER_ID = Identifier.of(OptimizedCombat.MOD_ID, "swords_block_interaction_range");
    @Unique
    private static final Identifier SWORDS_ENTITY_INTERACTION_RANGE_MODIFIER_ID = Identifier.of(OptimizedCombat.MOD_ID, "swords_entity_interaction_range");

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;<init>(Lnet/minecraft/item/Item$Settings;)V"), index = 0)
    private static Item.Settings createExtendedAttributeModifiers(Item.Settings settings) {
        return settings.attributeModifiers(AttributeModifiersComponent.builder()
                .add(EntityAttributes.BLOCK_INTERACTION_RANGE,
                        new EntityAttributeModifier(
                                SWORDS_BLOCK_INTERACTION_RANGE_MODIFIER_ID, OptimizedCombatSettings.INSTANCE.getInteractionSettings().getSwordsBlockDistance(), EntityAttributeModifier.Operation.ADD_VALUE
                        ),
                        AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.ENTITY_INTERACTION_RANGE,
                        new EntityAttributeModifier(
                                SWORDS_ENTITY_INTERACTION_RANGE_MODIFIER_ID, OptimizedCombatSettings.INSTANCE.getInteractionSettings().getSwordsEntityDistance(), EntityAttributeModifier.Operation.ADD_VALUE
                        ),
                        AttributeModifierSlot.MAINHAND).build());
    }
}
