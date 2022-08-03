package view

import service.SchwimmenGameRootService
import tools.aqua.bgw.core.BoardGameApplication

class SchwimmenApplication : BoardGameApplication("Schwimmen"), Refreshable {

    private val rootService = SchwimmenGameRootService()

    private val gameScene = SchwimmenGameScene(rootService)

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

    private val endMenuScene = GameFinishedMenuScene(rootService)

    init {
        this.showGameScene(gameScene)
        this.showMenuScene(startMenuScene)
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


}

