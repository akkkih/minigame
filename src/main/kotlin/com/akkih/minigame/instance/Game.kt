package com.akkih.minigame.instance

import com.akkih.minigame.state.GameState
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.util.UUID

class Game(private val arena: Arena) {
    private val points: HashMap<UUID, Int> = hashMapOf()

    fun start() {
        arena.state = GameState.LIVE
        arena.sendMessage(
            "${ChatColor.GREEN}Game has started! First to break 20 blocks wins."
        )

        arena.players.forEach { points[it] = 0 }
    }

    fun addPoint(player: Player) {
        val playerPoints: Int? = points[player.uniqueId]?.plus(1)
        if (playerPoints == 20) {
            arena.sendMessage(
                "${ChatColor.GOLD}${player.name} has won. Thanks for playing."
            )
            arena.reset(true)
            return
        }

        player.sendMessage(
            "${ChatColor.GREEN}Got a point!"
        )
        playerPoints?.let { points.replace(player.uniqueId, it) }
    }
}