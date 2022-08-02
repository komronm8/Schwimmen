package service

import entity.SchwimmenGame
import view.Refreshable

/**
 * Main class of the service layer for the "Schwimmen" Game. Connect the application layers.
 * Provides access to all other service classes and holds the [currentGame] state for these
 * services to access.
 */
class SchwimmenGameRootService {
    val gameService = GameService(this)
    val playerActionService = PlayerActionService(this)
    var currentGame: SchwimmenGame? = null

    /**
     * Adds the provided [newRefreshable] to all services connected
     * to this root service
     */
    fun addRefreshable(newRefreshable: Refreshable) {
        gameService.addRefreshable(newRefreshable)
        playerActionService.addRefreshable(newRefreshable)
    }

    /**
     * Adds each of the provided [newRefreshables] to all services
     * connected to this root service
     */
    fun addRefreshables(vararg newRefreshables: Refreshable) {
        newRefreshables.forEach { addRefreshable(it) }
    }

}