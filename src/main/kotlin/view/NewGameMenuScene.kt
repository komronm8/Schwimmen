package view

import service.*
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.TextField
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.ImageVisual
import java.awt.Color

class NewGameMenuScene(private val rootService: SchwimmenGameRootService):
    MenuScene(1000, 1000, ImageVisual("Background_wood_cut.png")) {

        private val playerSelectionLabel = Label(
            width = 600,
            height = 70,
            posX = 200,
            posY = 200,
            text = "Player Selection",
            font = Font(color = Color.WHITE, fontWeight = Font.FontWeight.BOLD, size = 60),
        )


        init {
            addComponents(playerSelectionLabel)
        }
}