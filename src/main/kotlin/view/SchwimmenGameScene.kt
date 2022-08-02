package view

import service.SchwimmenGameRootService
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.ImageVisual

class SchwimmenGameScene(private val rootService: SchwimmenGameRootService):
    BoardGameScene(1920, 1080, ImageVisual("Background_green.png")) {

    private val helloLabel = Label(
        width = 500,
        height = 500,
        posX = 0,
        posY = 0,
        text = "Hello, SoPra!",
        font = Font(size = 20)
    )

    init {
        background = ColorVisual(108, 168, 59)
        addComponents(helloLabel)
    }

}