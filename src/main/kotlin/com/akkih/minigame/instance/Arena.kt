package com.akkih.minigame.instance

import com.akkih.minigame.manager.ConfigManager
import com.akkih.minigame.state.GameState
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.UUID

class Arena(val id: Int, private val spawn: Location) {
    var state: GameState = GameState.RECRUITING
    val players: ArrayList<UUID> = arrayListOf()
    private var countdown: Countdown = Countdown(this)
    var game: Game = Game(this)

    fun start() = game.start()

    fun reset(kickPlayers: Boolean) {
        if (kickPlayers) {
            players.forEach { Bukkit.getPlayer(it)?.teleport(ConfigManager.lobbySpawn) }
        }

        sendTitle("", "")
        countdown.cancel()
        state = GameState.RECRUITING
        countdown = Countdown(this)
        game = Game(this)
    }

    fun addPlayer(player: Player) {
        players.add(player.uniqueId)
        player.teleport(spawn)

        if (state == GameState.RECRUITING && players.size >= ConfigManager.requiredPlayers) {
            countdown.start()
        }
    }

    fun removePlayer(player: Player) {
        players.remove(player.uniqueId)
        player.teleport(ConfigManager.lobbySpawn)
        player.sendTitle("", "")

        if (state == GameState.COUNTDOWN && players.size < ConfigManager.requiredPlayers) {
            sendMessage("${ChatColor.RED}There are not enough players. Countdown has stopped.")
            reset(false)
            return
        }

        if (state == GameState.LIVE && players.size <= 1) {
            sendMessage("${ChatColor.RED}The game has ended as too many players have left.")
            reset(false)
        }
    }

    fun sendMessage(message: String) = players.forEach { Bukkit.getPlayer(it)?.sendMessage(message) }
    fun sendTitle(title: String, subtitle: String) = players.forEach { Bukkit.getPlayer(it)?.sendTitle(title, subtitle) }
}