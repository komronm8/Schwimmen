package service

import entity.*
import view.Refreshable
import tools.aqua.bgw.util.Stack


class GameService(private val root: SchwimmenGameRootService): AbstractRefreshingService(){

    fun startNewGame(playerNames: Array<String>){
        val cardStack = generateCards()

    }

    fun nextGame(): Unit{}

    fun endGame(): Unit{}

    fun nextPlayer(): Unit{}

    private fun generateCards(): Stack<SchwimmenCard>{

        val cards = List(32) { index ->
            SchwimmenCard(
                CardSuit.values()[index / 8],
                CardValue.values()[(index % 8) + 5]
            )
        }.shuffled()

        return Stack(cards)
    }

}