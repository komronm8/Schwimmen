package view

import service.SchwimmenGameRootService
import tools.aqua.bgw.core.BoardGameApplication

class SchwimmenApplication : BoardGameApplication("Schwimmen") {

    private val rootService = SchwimmenGameRootService()

    private val gameScene = SchwimmenGameScene()

    private val startMenuScene = NewGameMenuScene()

    private val endMenuScene = GameFinishedMenuScene()

    init {
        this.showGameScene(gameScene)
    }

}

