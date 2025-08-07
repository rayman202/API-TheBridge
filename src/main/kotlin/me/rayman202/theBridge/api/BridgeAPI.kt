package me.rayman202.theBridge.api

import org.bukkit.entity.Player
import java.util.*

/**
 * The interface that defines the contract for your API.
 * Your main plugin will implement these methods.
 */
interface IBridgeAPI {
    /**
     * Checks if a player is currently inside a Bridge game.
     *
     * @param player The player to check.
     * @return `true` if the player is in a game, `false` otherwise.
     */
    fun isPlayerInGame(player: Player): Boolean

    /**
     * Gets the current game instance for a player.
     *
     * @param player The player whose game to retrieve.
     * @return A [GameInfoDTO] object, or `null` if the player is not in a game.
     */
    fun getPlayerGame(player: Player): GameInfoDTO?

    /**
     * Gets the team of a player in their current game.
     *
     * @param player The player to check.
     * @return The [Team] enum (RED or BLUE), or `null` if the player is not in a game.
     */
    fun getPlayerTeam(player: Player): Team?

    /**
     * Gets the persistent statistics for a player from the database.
     * Note: This is an asynchronous operation and should be handled accordingly.
     *
     * @param playerUUID The UUID of the player.
     * @return A [PlayerStatsDTO] object, or `null` if the player has no recorded stats.
     */
    fun getPlayerStats(playerUUID: UUID): PlayerStatsDTO?

    /**
     * Gets a specific map template by its name.
     *
     * @param name The name of the map.
     * @return A [MapInfoDTO] object, or `null` if not found.
     */
    fun getMap(name: String): MapInfoDTO?

    /**
     * Gets all available cosmetics of a specific type.
     *
     * @param type The [CosmeticType] to fetch (CAGE, ARROW, PORTAL).
     * @return A collection of [CosmeticDTO] objects.
     */
    fun getCosmetics(type: CosmeticType): Collection<CosmeticDTO>

    /**
     * Gets the cosmetic a player has currently selected for a given type.
     *
     * @param player The player whose selection is being checked.
     * @param type The [CosmeticType] to check.
     * @return The selected [CosmeticDTO] object.
     */
    fun getSelectedCosmetic(player: Player, type: CosmeticType): CosmeticDTO
}

/**
 * Static class to access the API from other plugins.
 */
object BridgeAPI {
    private var implementation: IBridgeAPI? = null

    /**
     * Gets the API implementation.
     * @return The API instance.
     * @throws IllegalStateException if the API has not been registered by the main plugin.
     */
    @JvmStatic
    fun get(): IBridgeAPI {
        return implementation ?: throw IllegalStateException("TheBridge API has not been registered. Make sure TheBridge plugin is installed and enabled.")
    }

    /**
     * Method for your main plugin to register its implementation.
     * SHOULD NOT BE USED BY OTHER PLUGINS.
     * @param api The API implementation.
     */
    @JvmStatic
    fun register(api: IBridgeAPI) {
        if (implementation != null) {
            // Optional: You can add a warning here if someone tries to register the API twice.
            return
        }
        implementation = api
    }
}

// --- Enums and Data Transfer Objects (DTOs) that are part of the API ---

/**
 * Represents a team in the game.
 */
enum class Team {
    RED, BLUE
}

/**
 * Represents the type of a cosmetic item.
 */
enum class CosmeticType {
    CAGE, ARROW, PORTAL
}

/**
 * A Data Transfer Object for player statistics.
 */
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

/**
 * A Data Transfer Object for map information.
 */
data class MapInfoDTO(
    val name: String,
    val worldName: String
)

/**
 * A Data Transfer Object for game information.
 */
data class GameInfoDTO(
    val mapName: String,
    val gameMode: String, // We use a String instead of the internal Enum
    val players: List<UUID>
)

/**
 * A Data Transfer Object for cosmetic information.
 */
data class CosmeticDTO(
    val name: String,
    val type: CosmeticType
)
