package com.akkih.minigame.manager

import com.akkih.minigame.Minigame
import com.akkih.minigame.instance.Arena
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player

object ArenaManager {
    val arenas: MutableList<Arena> = mutableListOf()

    init {
        Minigame.INSTANCE.config.let { config ->
            config.getConfigurationSection("arenas.")?.getKeys(false)?.forEach { string ->
                val id = Integer.parseInt(string)
                arenas.add(Arena(id, Location(
                    config.getString("arenas.$id.world")?.let { name -> Bukkit.getWorld(name) },
                    config.getDouble("arenas.$id.x"),
                    config.getDouble("arenas.$id.y"),
                    config.getDouble("arenas.$id.z"),
                    config.getDouble("arenas.$id.yaw").toFloat(),
                    config.getDouble("arenas.$id.pitch").toFloat()
                )))
            }
        }
    }

    fun isPlaying(player: Player) = getArena(player) != null

    fun getArena(player: Player): Arena? {
        arenas.forEach {
            if (it.players.contains(player.uniqueId)) return it
        }

        return null
    }

    fun getArena(id: Int): Arena? {
        arenas.forEach {
            if (it.id == id) return it
        }

        return null
    }
}