package view

import service.*
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.ImageVisual
import java.awt.Color
import tools.aqua.bgw.components.uicomponents.TextField

class NewGameMenuScene(private val rootService: SchwimmenGameRootService):
    MenuScene(1000, 1000, ImageVisual("Background_wood_cut.png")) {

    private val playerSelectionLabel = Label(
        width = 800,
        height = 70,
        posX = 100,
        posY = 200,
        text = "PLAYER SELECTION",
        font = Font(color = Color.WHITE, fontWeight = Font.FontWeight.BOLD, size = 60),
    )

    private val player1Label = Label(
        width = 200,
        height = 60,
        posX = 100,
        posY = 350,
        text = "Player 1",
        font = Font(fontWeight = Font.FontWeight.BOLD, size = 30),
        visual = ColorVisual(255,255,255)
    )

    private val player1Input = TextField(
        width =600,
        height = 60,
        posX = 325,
        posY = 350,
        prompt = "Enter player1's name!",
        font = Font(fontWeight = Font.FontWeight.BOLD, size = 30)
    )

    private val player2Label = Label(
        width = 200,
        height = 60,
        posX = 100,
        posY = 430,
        text = "Player 2",
        font = Font(fontWeight = Font.FontWeight.BOLD, size = 30),
        visual = ColorVisual(255,255,255)
    )

    private val player2Input = TextField(
        width =600,
        height = 60,
        posX = 325,
        posY = 430,
        prompt = "Enter player2's name!",
        font = Font(fontWeight = Font.FontWeight.BOLD, size = 30)
    )

    val player3Label = Label(
        width = 200,
        height = 60,
        posX = 100,
        posY = 510,
        text = "Player 3",
        font = Font(fontWeight = Font.FontWeight.BOLD, size = 30),
        visual = ColorVisual(255,255,255)
    )

    val player3Input = TextField(
        width =600,
        height = 60,
        posX = 325,
        posY = 510,
        prompt = "Enter player3's name!",
        font = Font(fontWeight = Font.FontWeight.BOLD, size = 30)
    )

    val player4Label = Label(
        width = 200,
        height = 60,
        posX = 100,
        posY = 590,
        text = "Player 3",
        font = Font(fontWeight = Font.FontWeight.BOLD, size = 30),
        visual = ColorVisual(255,255,255)
    )

    val player4Input = TextField(
        width =600,
        height = 60,
        posX = 325,
        posY = 590,
        prompt = "Enter player4's name!",
        font = Font(fontWeight = Font.FontWeight.BOLD, size = 30)
    )

    private val startGameButton = Button(
        width = 350,
        height = 80,
        posX = 100,
        posY = 800,
        text = "START GAME",
        font = Font(color = Color.WHITE, fontWeight = Font.FontWeight.BOLD, size = 36),
        visual = ImageVisual("button.png")
    )

    val quitButton = Button(
        width = 150,
        height = 80,
        posX = 500,
        posY = 800,
        text = "QUIT",
        font = Font(color = Color.WHITE, fontWeight = Font.FontWeight.BOLD, size = 36),
        visual = ImageVisual("button.png")
    )

    val addPlayerButton = Button(
        width = 80,
        height = 80,
        posX = 800,
        posY = 800,
        visual = ImageVisual("plus.png")
    )

    val removePLayerButton = Button(
        width = 80,
        height = 80,
        posX = 700,
        posY = 800,
        visual = ImageVisual("minus.png")
    )

    init {
        addComponents(playerSelectionLabel,
            player1Label,
            player2Label,
            player1Input,
            player2Input,
            startGameButton,
            quitButton,
            addPlayerButton,
            removePLayerButton,
        )
    }

}