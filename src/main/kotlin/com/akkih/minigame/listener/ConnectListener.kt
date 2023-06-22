package com.akkih.minigame.listener

import com.akkih.minigame.Minigame
import com.akkih.minigame.manager.ConfigManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class ConnectListener : Listener {
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) = event.player.teleport(ConfigManager.lobbySpawn)

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        val player = event.player

        if (Minigame.arenaManager.isPlaying(player)) {
            Minigame.arenaManager.getArena(player)?.removePlayer(player)
        }
    }
}