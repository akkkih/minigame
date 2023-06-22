package com.akkih.minigame.manager

import com.akkih.minigame.Minigame
import org.bukkit.Bukkit
import org.bukkit.Location

object ConfigManager {
    private val config = Minigame.INSTANCE.config

    init {
        Minigame.INSTANCE.saveDefaultConfig()
    }

    val requiredPlayers = config.getInt("required-players")
    val countdownSeconds = config.getInt("countdown-seconds")
    val lobbySpawn = Location(
        config.getString("lobby-spawn.world")?.let { Bukkit.getWorld(it) } ?: throw IllegalArgumentException("World is invalid"),
        config.getDouble("lobby-spawn.x"),
        config.getDouble("lobby-spawn.y"),
        config.getDouble("lobby-spawn.z"),
        config.getDouble("lobby-spawn.yaw").toFloat(),
        config.getDouble("lobby-spawn.pitch").toFloat()
    )
}