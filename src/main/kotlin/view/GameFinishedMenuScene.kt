package view

import service.SchwimmenGameRootService
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ImageVisual
import java.awt.Color

/**
 * [MenuScene] that is displayed when the game is finished. It shows the final result of the game
 * as well as the score. Also, there are two buttons: one for starting a new game with the same players
 * and one for quitting the program.
 */
class GameFinishedMenuScene(private val rootService: SchwimmenGameRootService):
    MenuScene(1000,1000, ImageVisual("Background_wood_cut.png")), Refreshable {

    private val playerWinLabel = Label(
        width = 800,
        height = 70,
        posX = 100,
        posY = 200,
        font = Font(color = Color.WHITE, fontWeight = Font.FontWeight.BOLD, size = 60)
    )

    private val finalResult = Label(
        width = 800,
        height = 70,
        posX = -200,
        posY = 300,
        text = "Final Results:",
        font = Font(color = Color.WHITE, fontWeight = Font.FontWeight.BOLD, size = 36)
    )

    private val player1Points = Label(
        width = 800,
        height = 70,
        posX = 80,
        posY = 360,
        alignment = Alignment.CENTER,
        font = Font(color = Color.WHITE, fontWeight = Font.FontWeight.BOLD, size = 30)
    )
    private val player2Points = Label(
        width = 800,
        height = 70,
        posX = 80,
        posY = 420,
        alignment = Alignment.CENTER,
        font = Font(color = Color.WHITE, fontWeight = Font.FontWeight.BOLD, size = 30)
    )

    private val player3Points = Label(
        width = 800,
        height = 70,
        posX = 80,
        posY = 480,
        alignment = Alignment.CENTER,
        font = Font(color = Color.WHITE, fontWeight = Font.FontWeight.BOLD, size = 30)
    )

    private val player4Points = Label(
        width = 800,
        height = 70,
        posX = 80,
        posY = 540,
        alignment = Alignment.CENTER,
        font = Font(color = Color.WHITE, fontWeight = Font.FontWeight.BOLD, size = 30)
    )

    private val newGameButton = Button(
        width = 350,
        height = 80,
        posX = 125,
        posY = 800,
        text = "NEW GAME",
        font = Font(color = Color.WHITE, fontWeight = Font.FontWeight.BOLD, size = 36),
        visual = ImageVisual("button.png")
    ).apply {
        onMouseClicked = {
            rootService.gameService.nextGame()
        }
    }

    val exitButton = Button(
        width = 350,
        height = 80,
        posX = 525,
        posY = 800,
        text = "EXIT",
        font = Font(color = Color.WHITE, fontWeight = Font.FontWeight.BOLD, size = 36),
        visual = ImageVisual("button.png")
    )

    private fun clearMap(){
        removeComponents(playerWinLabel, finalResult, player1Points, player2Points,
            player3Points, player4Points)
    }

    init {
        addComponents(newGameButton, exitButton)
    }

    private fun showPlayerPoints(){
        val game = rootService.currentGame
        checkNotNull(game) {"No started game found."}

        val playerLabels = listOf(player1Points, player2Points, player3Points, player4Points)
        val playerPoints = mutableListOf<Float>()
        var maxPoints = 0.0f
        var winnerIndex = 0

        for( i in 0 until game.players.size ){
            playerPoints.add(game.players[i].points)
            if(game.players[i].points > maxPoints){
                maxPoints = game.players[i].points
                winnerIndex = i
            }
        }
        playerWinLabel.text = "${game.players[winnerIndex].name.uppercase()} WON!"
        addComponents(playerWinLabel, finalResult)

        when( game.players.size ){
            4 -> {
                for( i in 0 until 4 ){
                    playerLabels[i].text = "Player${i+1} ................." +
                            "...................................................... ${game.players[i].points}"
                    addComponents(playerLabels[i])
                }
            }
            3 -> {
                for( i in 0 until 3 ){
                    playerLabels[i].text = "Player${i+1} ................." +
                            "...................................................... ${game.players[i].points}"
                    addComponents(playerLabels[i])
                }
            }
            else -> {
                for( i in 0 until 2 ){
                    playerLabels[i].text = "Player${i+1} ................." +
                            "...................................................... ${game.players[i].points}"
                    addComponents(playerLabels[i])
                }
            }
        }
    }

    override fun refreshAfterGameEnd() {
        clearMap()
        showPlayerPoints()
    }

}