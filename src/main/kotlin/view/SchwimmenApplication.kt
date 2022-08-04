package view

import service.SchwimmenGameRootService
import tools.aqua.bgw.core.BoardGameApplication

/**
 * Implementation of the BGW [BoardGameApplication] for the card game "Schwimmen"
 */
class SchwimmenApplication : BoardGameApplication("Schwimmen"), Refreshable {

    // Central service from which all others are created/accessed
    // also holds the currently active game
    private val rootService = SchwimmenGameRootService()

    // This is where the actual game takes place
    private val gameScene = SchwimmenGameScene(rootService)

    // This menu scene is shown after application start and if the "new game" button
    // is clicked in the gameFinishedMenuScene
    private val startMenuScene = NewGameMenuScene(rootService).apply {
        var playerCount = 2
        quitButton.onMouseClicked = {
            exit()
        }
        addPlayerButton.onMouseClicked = {
            if(playerCount == 2){
                addComponents(player3Input, player3Label)
                playerCount++
            }
            else if(playerCount == 3){
                addComponents(player4Input, player4Label)
                playerCount++
            }
        }
        removePLayerButton.onMouseClicked = {
            if(playerCount == 3){
                removeComponents(player3Input, player3Label)
                player3Input.text = ""
                playerCount--
            }
            else if(playerCount == 4){
                removeComponents(player4Input, player4Label)
                player4Input.text = ""
                playerCount--
            }
        }
    }

    // This menu scene is shown after each finished game (i.e. no more cards to draw)
    private val endMenuScene = GameFinishedMenuScene(rootService).apply {
        exitButton.onMouseClicked = {
            exit()
        }
    }

    init {
        this.showGameScene(gameScene)
        this.showMenuScene(startMenuScene)

        // all scenes and the application itself need too
        // react to changes done in the service layer
        rootService.addRefreshables(
            this,
            startMenuScene,
            gameScene,
            endMenuScene
        )
    }

    override fun refreshAfterStartNewGame() {
        this.hideMenuScene()
    }

    override fun refreshAfterGameEnd() {
        this.showMenuScene(endMenuScene)
    }

}

