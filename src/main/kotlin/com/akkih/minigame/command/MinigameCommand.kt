package com.akkih.minigame.command

import com.akkih.minigame.Minigame
import com.akkih.minigame.state.GameState
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class MinigameCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("You must be a player to run this command.")
            return false
        }

        if (args.size == 1) {
            when (args[0].lowercase()) {
                "list" -> {
                    sender.sendMessage("${ChatColor.GREEN}These are the available arenas:")
                    Minigame.arenaManager.arenas.forEach {
                        sender.sendMessage("${ChatColor.GREEN}- ${it.id} (${it.state.name})")
                    }
                }

                "leave" -> {
                    if (Minigame.arenaManager.isPlaying(sender)) {
                        sender.sendMessage("${ChatColor.RED}You left the arena.")
                        Minigame.arenaManager.getArena(sender)?.removePlayer(sender)
                    } else {
                        sender.sendMessage("${ChatColor.RED}You're not in an arena.")
                    }
                }
            }
        }

        if (args.size == 2 && args[0].lowercase() === "join") {
            if (Minigame.arenaManager.isPlaying(sender)) {
                sender.sendMessage("${ChatColor.RED}You're already playing!")
                return false
            }

            val id = runCatching {
                Integer.parseInt(args[1])
            }.onFailure {
                sender.sendMessage("${ChatColor.RED}You specified an invalid arena id.")
                return false
            }.getOrThrow()

            if (id >= 0 && id < Minigame.arenaManager.arenas.size) {
                Minigame.arenaManager.getArena(id).let { arena ->
                    if (arena?.state == GameState.RECRUITING || arena?.state == GameState.COUNTDOWN) {
                        sender.sendMessage("${ChatColor.GREEN}You're now playing in Arena $id.")
                        arena.addPlayer(sender)
                    } else {
                        sender.sendMessage("${ChatColor.RED}You can't join this arena right now.")
                    }
                }
            } else {
                sender.sendMessage("${ChatColor.RED}You specified an invalid arena id.")
                return false
            }
        }

        return true
    }
}
