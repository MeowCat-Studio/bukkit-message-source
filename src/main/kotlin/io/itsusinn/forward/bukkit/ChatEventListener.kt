/**
 * Copyright 2020-2021 Itsusinn and contributors.
 *
 * Licensed under the GNU Lesser General Public License version 3,
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *       https://opensource.org/licenses/LGPL-3.0
 */

@file:Suppress("NOTHING_TO_INLINE")
package io.itsusinn.forward.bukkit

import mu.KotlinLogging
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

private val logger = KotlinLogging.logger { }

internal object ChatEventListener : Listener {
   @EventHandler
   suspend fun onChat(event: AsyncPlayerChatEvent) {
      try {
         chatEventHandler?.invoke(event)
      } catch (e: Throwable) {
         logger.error(e) { "Uncaught Exception \n${e.stackTrace}" }
      }
   }
   var chatEventHandler: (suspend (AsyncPlayerChatEvent) -> Unit)? = null
   inline fun chatEventHandler(
      noinline handler: (suspend (AsyncPlayerChatEvent) -> Unit)
   ) {
      chatEventHandler = handler
   }
}
