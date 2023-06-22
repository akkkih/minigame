package com.akkih.minigame.instance

import com.akkih.minigame.Minigame
import com.akkih.minigame.manager.ConfigManager
import com.akkih.minigame.state.GameState
import org.bukkit.ChatColor
import org.bukkit.scheduler.BukkitRunnable

class Countdown(private val arena: Arena) : BukkitRunnable() {
    private var seconds = ConfigManager.countdownSeconds

    fun start() {
        arena.state = GameState.COUNTDOWN
        runTaskTimer(Minigame.INSTANCE, 0, 20)
    }

    override fun run() {
        if (seconds == 0) {
            cancel()
            arena.start()
            arena.sendTitle("", "")
            return
        }

        if (arena.players.size < ConfigManager.requiredPlayers) {
            arena.reset(false)
            arena.sendMessage(
                "${ChatColor.RED}There are not enough players. Countdown has stopped."
            )
            arena.sendTitle("", "")
        }

        if (seconds.div(30) == 0 || seconds <= 10) {
            arena.sendMessage(
                "${ChatColor.GREEN}Game will start in $seconds second${ if (seconds == 1) "" else "s" }."
            )
        }

        arena.sendTitle(
            "${ChatColor.GREEN}$seconds second${ if (seconds == 1) "" else "s" }",
            "${ChatColor.GRAY}until game starts"
        )

        seconds--
    }
}