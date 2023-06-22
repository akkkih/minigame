package com.akkih.minigame.listener

import com.akkih.minigame.Minigame
import com.akkih.minigame.state.GameState
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class GameListener : Listener {
    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        event.player.let { player ->
            if (Minigame.arenaManager.isPlaying(player)) {
                Minigame.arenaManager.getArena(player).let { arena ->
                    if (arena?.state == GameState.LIVE) {
                        arena.game.addPoint(player)
                    }
                }
            }
        }
    }
}