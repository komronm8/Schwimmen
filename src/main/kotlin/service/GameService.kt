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
        checkNotNull(game){"There is no game to start new round!"}
        val players = mutableListOf<String>()
        for ( i in game.players.indices ){
            players.add(game.players[i].name)
        }
        root.currentGame = null
        startNewGame(players.toTypedArray())
    }

    fun endGame(): Unit{
        val game = root.currentGame
        checkNotNull(game){"There is no game!"}
        root.currentGame = null
        onAllRefreshables { refreshAfterGameEnd() }
    }

    fun nextPlayer(): Unit{
        val game = root.currentGame
        checkNotNull(game){"There is no game!"}
        //find next player
        val nextPlayer: SchwimmenPlayer
        if(game.currentPlayerIndex == game.players.lastIndex){
            nextPlayer = game.players[0]
            game.currentPlayerIndex = 0
        } else {
            nextPlayer = game.players[game.currentPlayerIndex + 1]
            game.currentPlayerIndex ++
        }
        //check if next player has knocked
        if(nextPlayer == game.knocked){
            endGame()
        }
        onAllRefreshables { refreshAfterNextPlayer() }
    }

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