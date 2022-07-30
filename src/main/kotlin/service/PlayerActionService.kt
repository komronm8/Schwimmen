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

    fun pass(): Unit{
        val game = root.currentGame
        checkNotNull(game)
        game.passCount++
        //check if all players have passed
        if( game.passCount < game.players.size){
            root.gameService.nextPlayer()
        }
        else{
            //change tableCards if there are more than 3 cards in the stack
            if( game.cardStack.size >= 3 ){
                //clear the current tableCards and add the another 3 from the cardStack
                game.tableCards.clear()
                game.tableCards.addAll(game.cardStack.popAll(3))
                //configure passCount, refresh view and go to next player
                game.passCount = 0
                onAllRefreshables { refreshAfterAllPass() }
                root.gameService.nextPlayer()
            }
            //else end the game
            else{
                root.gameService.endGame()
            }
        }
    }

    fun knock(): Unit{}

    private fun calculatePlayerPoints(player: SchwimmenPlayer): Unit{}


}