package me.rayman202.theBridge.api

import me.rayman202.theBridge.TheBridge
import me.rayman202.theBridge.cosmetics.Cosmetic
import me.rayman202.theBridge.cosmetics.CosmeticType
import me.rayman202.theBridge.database.PlayerStats
import me.rayman202.theBridge.game.BridgeGame
import me.rayman202.theBridge.game.GameMode
import me.rayman202.theBridge.game.Team
import me.rayman202.theBridge.maps.BridgeMap
import org.bukkit.entity.Player
import java.util.*

/**
 * The official API for TheBridge plugin.
 * This class provides safe and stable methods to interact with the plugin's core functionalities.
 *
 * @author rayman202
 * @version 1.0
 */
class BridgeAPI(private val plugin: TheBridge) {

    /**
     * Checks if a player is currently inside a Bridge game.
     *
     * @param player The player to check.
     * @return `true` if the player is in a game, `false` otherwise.
     */
    fun isPlayerInGame(player: Player): Boolean {
        return plugin.gameManager.isPlayerInGame(player)
    }

    /**
     * Gets the current game instance for a player.
     *
     * @param player The player whose game to retrieve.
     * @return The [BridgeGame] instance, or `null` if the player is not in a game.
     */
    fun getPlayerGame(player: Player): BridgeGame? {
        return plugin.gameManager.getPlayerGame(player)
    }

    /**
     * Gets the team of a player in their current game.
     *
     * @param player The player to check.
     * @return The [Team] enum (RED or BLUE), or `null` if the player is not in a game.
     */
    fun getPlayerTeam(player: Player): Team? {
        return getPlayerGame(player)?.getPlayerTeam(player.uniqueId)
    }

    /**
     * Gets the persistent statistics for a player from the database or YML files.
     * Note: This is an asynchronous operation and should be handled accordingly.
     *
     * @param playerUUID The UUID of the player.
     * @return A [PlayerStats] object, or `null` if the player has no recorded stats.
     */
    fun getPlayerStats(playerUUID: UUID): PlayerStats? {
        return plugin.storageManager.storage.getPlayerStats(playerUUID)
    }

    /**
     * Gets a specific map template by its name.
     *
     * @param name The name of the map.
     * @return A [BridgeMap] object, or `null` if not found.
     */
    fun getMap(name: String): BridgeMap? {
        return plugin.mapManager.getMap(name)
    }

    /**
     * Gets all available cosmetics of a specific type.
     *
     * @param type The [CosmeticType] to fetch (CAGE, ARROW, PORTAL).
     * @return A collection of [Cosmetic] objects.
     */
    fun getCosmetics(type: CosmeticType): Collection<Cosmetic> {
        return plugin.cosmeticsManager.getAllCosmetics(type)
    }

    /**
     * Gets the cosmetic a player has currently selected for a given type.
     *
     * @param player The player whose selection is being checked.
     * @param type The [CosmeticType] to check.
     * @return The selected [Cosmetic] object.
     */
    fun getSelectedCosmetic(player: Player, type: CosmeticType): Cosmetic {
        val playerCosmetics = plugin.cosmeticsManager.getPlayerCosmetics(player)
        return when (type) {
            CosmeticType.CAGE -> playerCosmetics.cage
            CosmeticType.ARROW -> playerCosmetics.arrow
            CosmeticType.PORTAL -> playerCosmetics.portal
        }
    }

    // --- Data Transfer Objects (DTOs) for the API ---

    data class PlayerStatsDTO(
        val uuid: UUID,
        val name: String,
        val wins: Int,
        val losses: Int,
        val winStreak: Int,
        val kills: Int,
        val deaths: Int,
        val goals: Int
    )

    data class MapInfoDTO(
        val name: String,
        val worldName: String
    )
}