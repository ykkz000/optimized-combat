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

package ykkz000.optimizedcombat.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;

public interface ServerPlayerTickEvents {
    /**
     * ServerPlayerEntity start ticking
     */
    Event<TickListener> START_TICK = EventFactory.createArrayBacked(TickListener.class, (listeners) -> (player) -> {
        for (TickListener listener : listeners) {
            ActionResult result = listener.run(player);
            if (result != ActionResult.PASS) {
                return result;
            }
        }
        return ActionResult.PASS;
    });
    /**
     * ServerPlayerEntity end ticking
     */
    Event<TickListener> END_TICK = EventFactory.createArrayBacked(TickListener.class, (listeners) -> (player) -> {
        for (TickListener listener : listeners) {
            ActionResult result = listener.run(player);
            if (result != ActionResult.PASS) {
                return result;
            }
        }
        return ActionResult.PASS;
    });

    interface TickListener {
        /**
         * Triggered when ServerPlayerEntity ticks
         *
         * @param player ServerPlayerEntity
         * @return ActionResult, SUCCESS and FAIL will be the same
         */
        ActionResult run(ServerPlayerEntity player);
    }
}
