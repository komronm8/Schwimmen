package service

import entity.*
import view.Refreshable
import tools.aqua.bgw.util.Stack


class GameService(private val root: SchwimmenGameRootService): AbstractRefreshingService(){

    fun startNewGame(playerNames: Array<String>){
        //check if there is no game running
        if(root.currentGame != null){
            throw IllegalStateException("There is a game running already!")
        }
        val stackOfCards = generateCards()
        val tableCards = stackOfCards.popAll(3).toMutableList()
        // checks if amount of playerNames are valid
        if (playerNames.size !in 2..4) {
            throw IllegalArgumentException("Number of players are not between 2 and 4!")
        }
        val players = mutableListOf<SchwimmenPlayer>()
        for ( i in playerNames.indices ){
            players.add(SchwimmenPlayer(stackOfCards.popAll(3).toMutableList(),playerNames[i]))
        }
        root.currentGame = SchwimmenGame(players, tableCards, stackOfCards)
        onAllRefreshables(Refreshable::refreshAfterStartNewGame)
    }

    fun nextGame(): Unit{
        val game = root.currentGame
        checkNotNull(game){"There is no game!"}
        val players = mutableListOf<String>()
        for ( i in game.players.indices ){
            players.add(game.players[i].name)
        }
        root.currentGame = null
        startNewGame(players.toTypedArray())
    }

    fun endGame(): Unit{}

    fun nextPlayer(): Unit{}

    private fun generateCards(): Stack<SchwimmenCard>{

        val cardList = List(32) { index ->
            SchwimmenCard(
                CardSuit.values()[index / 8],
                CardValue.values()[(index % 8) + 5]
            )
        }.shuffled()

        return Stack(cardList)
    }

}