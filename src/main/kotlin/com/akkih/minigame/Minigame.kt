package com.akkih.minigame

import com.akkih.minigame.command.MinigameCommand
import com.akkih.minigame.listener.ConnectListener
import com.akkih.minigame.listener.GameListener
import com.akkih.minigame.manager.ArenaManager
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Minigame : JavaPlugin() {
    override fun onEnable() {
        // Minigame startup logic
        INSTANCE = this
        arenaManager = ArenaManager

        Bukkit.getPluginManager().registerEvents(ConnectListener(), this)
        Bukkit.getPluginManager().registerEvents(GameListener(), this)

        getCommand("minigame")?.setExecutor(MinigameCommand())
    }

    companion object {
        lateinit var INSTANCE: Minigame
            private set
        lateinit var arenaManager: ArenaManager
            private set
    }
}