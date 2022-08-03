package service

import entity.*
import view.Refreshable
import tools.aqua.bgw.util.Stack

/**
 * Service layer class that provides the logic which are not related to the player
 */
class GameService(private val root: SchwimmenGameRootService): AbstractRefreshingService(){

    /**
     * Method for starting the game. It will be called whenever the game is started
     * this method will call [SchwimmenGame] with the right parameters and then assign it to the
     * [SchwimmenGameRootService]s currentGame
     * @param playerNames an array of the player names that will be given to start the game
     * @throws IllegalStateException if there is a game currently running
     * @throws IllegalArgumentException if [playerNames] is an invalid amount
     */
    fun startNewGame(playerNames: Array<String>){
        //check if there is no game running
        if(root.currentGame != null){
            throw IllegalStateException("There is a game running already!")
        }
        for( i in playerNames){
            if( i == "" ){
                throw IllegalArgumentException("Name should not be empty!")
            }
        }
        val stackOfCards = generateCards()
        val tableCards = stackOfCards.popAll(3).toMutableList()
        // checks if amount of playerNames are valid
        require(playerNames.size in 2..4) {"Number of players are not between 2 and 4!"}
        val players = mutableListOf<SchwimmenPlayer>()
        for ( i in playerNames.indices ){
            players.add(SchwimmenPlayer(stackOfCards.popAll(3).toMutableList(),playerNames[i]))
        }
        root.currentGame = SchwimmenGame(players, tableCards, stackOfCards)
        onAllRefreshables{ refreshAfterStartNewGame() }
    }

    /**
     * method used to start a new game with the same players
     * @throws IllegalStateException if there is no game for starting a new one
     */
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

    /**
     * method to end the game
     * @throws IllegalStateException if there is no game to end
     */
    fun endGame(): Unit{
        val game = root.currentGame
        checkNotNull(game){"There is no game!"}
        root.currentGame = null
        onAllRefreshables { refreshAfterGameEnd() }
    }

    /**
     * Method to go to the next player. If someone has already knocked and the next player is that person,
     * the game will end by calling endGame(), otherwise it just goes to the next player
     * @throws IllegalStateException if no game exists
     */
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

    /**
     * private method for generating the stack of cards, which will be shuffled as well and returned
     * @return returns stack of [SchwimmenCard]s
     */
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