package service

import entity.SchwimmenGame

/**
 * Main class of the service layer for the "Schwimmen" Game. Connect the application layers.
 * Provides access to all other service classes and holds the [currentGame] state for these
 * services to access.
 */
class SchwimmenGameRootService {
    val gameService = GameService(this)
    val playerActionService = PlayerActionService(this)
    var currentGame: SchwimmenGame? = null
}