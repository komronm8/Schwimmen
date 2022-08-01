package service

import entity.*
import kotlin.math.max

/**
 * Class responsible for the player actions and provides the logic as well.
 */
class PlayerActionService(private val root: SchwimmenGameRootService): AbstractRefreshingService() {

    /**
     * Swaps a card from the players hand with a table card that the player
     * Then configures the points and pass count, refreshes the application and goes to the next player
     * @param tableCard the table card which will be swapped with the player card
     * @param playerCard the player card which will get swapped with the table card
     * @throws IllegalStateException if no game has started yet
     * @throws IllegalStateException if tableCard is not a real table card or if playerCard is not a real
     * player card
     */
    fun swapOneCard(tableCard: SchwimmenCard, playerCard: SchwimmenCard): Unit{
        val game = root.currentGame
        checkNotNull(game)
        val currentPlayer = game.players[game.currentPlayerIndex]
        //checks if card exists in the tableCards or in the playerCards
        if(tableCard !in game.tableCards || playerCard !in currentPlayer.playerCards){
            throw IllegalStateException("The specified card does not exist!")
        }
        //Swapping of cards
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

    /**
     * Swaps all the cards from the players hand with the table cards
     * @throws IllegalStateException if no game has started yet
     */
    fun swapAllCards(): Unit{
        val game = root.currentGame
        checkNotNull(game)
        val currentPlayer = game.players[game.currentPlayerIndex]
        val temporaryList = mutableListOf<SchwimmenCard>()
        temporaryList.addAll(currentPlayer.playerCards)
        //overwrite playerCards with tableCards
        currentPlayer.playerCards.clear()
        currentPlayer.playerCards.addAll(game.tableCards)
        //overwrite tableCards with content of temporaryList
        game.tableCards.clear()
        game.tableCards.addAll(temporaryList)
        //configure passCount/playerPoints, refresh view and go to next player
        game.passCount = 0
        calculatePlayerPoints(currentPlayer)
        onAllRefreshables { refreshAfterSwapCards() }
        root.gameService.nextPlayer()
    }

    /**
     * Pass function for the players. If everybody has passed the game ends, else pass count gets incremented
     * and passes to the next player
     * @throws IllegalStateException if no game has started yet
     */
    fun pass(): Unit{
        val game = root.currentGame
        checkNotNull(game)
        game.passCount++
        //check if not all players have passed
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

    /**
     * Knock function for the players. When someone knocks from there every other player has one more turn,
     * then the game ends
     * @throws IllegalStateException if no game has started yet
     * @throws IllegalStateException if someone has already knocked
     */
    fun knock(): Unit{
        val game = root.currentGame
        checkNotNull(game)
        if(game.knocked != null){
            throw IllegalStateException("Someone has already knocked!")
        }
        val currentPlayer = game.players[game.currentPlayerIndex]
        game.knocked = currentPlayer
        root.gameService.nextPlayer()
    }

    /**
     * Private function to calculate the points of the player. Checks if the player has the same type of
     * card values, else just calculates as usual by summing the values
     * @param player the [SchwimmenPlayer] that will get its points calculated
     */
    private fun calculatePlayerPoints(player: SchwimmenPlayer): Unit{
        val playerHand = player.playerCards
        var isSameTyped = true
        //check if all the cards are the same type
        for( i in playerHand.indices ){
            if(playerHand[0].value != playerHand[i].value){
                isSameTyped = false
                break
            }
        }
        if(isSameTyped){
            player.points = 30.5f
        }
        //otherwise calculate points by finding the highest sum of the card values
        //which have the same card suit
        var result = 0.0f
        for(suit in CardSuit.values()){
            result = max(result, getSum( suit, playerHand ))
        }
    }

    /**
     * Private function for getting sum of the player hand cards with the same suit
     * @param suit the suit which will be looked at
     * @param playerCards the player's card
     * @return returns the total sum as a Float
     */
    private fun getSum( suit: CardSuit, playerCards: MutableList<SchwimmenCard>): Float{
        var sum = 0.0f
        for(playerCard in playerCards){
            if(suit == playerCard.suit){
                sum += getValue(playerCard)
            }
        }
        return sum
    }

    /**
     * Private function for getting the value of a specific card
     * @param playerCard the card that the value is needed
     * @return an int value of the card
     */
    private fun getValue(playerCard: SchwimmenCard): Int{
       return when (playerCard.value) {
            CardValue.SEVEN -> 7
            CardValue.EIGHT -> 8
            CardValue.NINE -> 9
            CardValue.TEN -> 10
            CardValue.JACK -> 10
            CardValue.QUEEN -> 10
            CardValue.KING -> 10
            CardValue.ACE -> 11
           else -> { throw IllegalArgumentException("The card is invalid!")}
       }
    }
}