package service

import entity.SchwimmenGame

/**
 * Main class of the service layer for the "Schwimmen" Game. Connect the application layers.
 */
class SchwimmenGameRootService {
    val gameService = GameService(this)
    val playerActionService = PlayerActionService(this)
    var currentGame: SchwimmenGame? = null
}