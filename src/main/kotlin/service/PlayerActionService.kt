package service

import entity.SchwimmenCard
import entity.SchwimmenGame
import entity.SchwimmenPlayer

class PlayerActionService(private val root: SchwimmenGameRootService): AbstractRefreshingService() {

    fun swapOneCard(tableCard: SchwimmenCard, playerCard: SchwimmenCard): Unit{
        val game = root.currentGame
        checkNotNull(game)
        val currentPlayer = game.players[game.currentPlayerIndex]
        //checks if card exists in the tableCards or in the playerCards
        if(tableCard !in game.tableCards || playerCard !in currentPlayer.playerCards){
            throw IllegalStateException("The specified card does not exist!")
        }
        //Swaping of cards
        for( i in game.tableCards.indices){
            if( tableCard == game.tableCards[i] ){
                game.tableCards[i] = playerCard
            }
        }
        for( i in currentPlayer.playerCards.indices){
            if( playerCard == currentPlayer.playerCards[i] ){
                currentPlayer.playerCards[i] = tableCard
            }
        }
        //configure passCount/playerPoints, refresh view and go to next player
        game.passCount = 0
        calculatePlayerPoints(currentPlayer)
        onAllRefreshables { refreshAfterSwapCards() }
        root.gameService.nextPlayer()
    }
    fun swapAllCards(): Unit{
        val game = root.currentGame
        checkNotNull(game)
        val currentPlayer = game.players[game.currentPlayerIndex]
        val temporaryList = currentPlayer.playerCards
        //overwrite playerCards with tableCards
        currentPlayer.playerCards.clear()
        currentPlayer.playerCards = game.tableCards
        //overwrite tableCards with content of temporaryList
        game.tableCards.clear()
        game.tableCards.addAll(temporaryList)
        //configure passCount/playerPoints, refresh view and go to next player
        game.passCount = 0
        calculatePlayerPoints(currentPlayer)
        onAllRefreshables { refreshAfterSwapCards() }
        root.gameService.nextPlayer()
    }

    fun pass(): Unit{}

    fun knock(): Unit{}

    private fun calculatePlayerPoints(player: SchwimmenPlayer): Unit{}


}