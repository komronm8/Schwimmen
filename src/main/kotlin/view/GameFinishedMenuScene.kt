package view

import service.SchwimmenGameRootService
import tools.aqua.bgw.core.MenuScene

class GameFinishedMenuScene(private val rootService: SchwimmenGameRootService):
    MenuScene(400,1080), Refreshable {
}