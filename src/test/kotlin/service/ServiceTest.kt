package service

import kotlin.test.*
import entity.*

/**
 * Class that provides tests for [GameService] and [PlayerActionService] by basically playing through some
 * sample games
 */
class ServiceTest {

    @Test
    fun testStartNewGame(){
        val game = SchwimmenGameRootService()
        val playerNames = arrayOf("Max", "Alex", "Sofia")
        val invalidPlayerAmount = arrayOf("Bob", "Ethan", "Simon", "Tobi", "Josh")
        //test to see if there is no game at the start
        assertTrue(game.currentGame == null)
        //test to see if the game can be created
        game.gameService.startNewGame(playerNames)
        assertTrue(game.currentGame != null)
        //test for when startNewGame is called but there is a game running
        assertFailsWith<IllegalStateException> { game.gameService.startNewGame(playerNames) }
        game.currentGame = null
        //test for when the amount of players is invalid( !in 2..4 )
        assertFailsWith<IllegalArgumentException> { game.gameService.startNewGame(invalidPlayerAmount) }
    }

    @Test
    fun testNextGame(){
        val game = SchwimmenGameRootService()
        val playerNames = arrayOf("Max", "Alex", "Sofia")
        //test for when there is no game and the function nextGame is called
        assertFailsWith<IllegalStateException> { game.gameService.nextGame() }
        game.gameService.startNewGame(playerNames)
        //test to see weather the nextGame has worked
        game.gameService.nextGame()
        assertTrue { game.currentGame != null }
    }

    @Test
    fun testEndGame(){
        val game = SchwimmenGameRootService()
        val playerNames = arrayOf("Max", "Alex", "Sofia")
        //test to see weather game can be ended when there is no game
        assertFailsWith<IllegalStateException> { game.gameService.endGame() }
        //test to see weather game has been ended
        game.gameService.startNewGame(playerNames)
        assertTrue { game.currentGame != null }
        game.gameService.endGame()
        assertTrue { game.currentGame == null }
    }

    @Test
    fun testNextPlayer(){
        val game = SchwimmenGameRootService()
        val playerNames = arrayOf("Max", "Alex", "Sofia")
        //test to see if nextPlayer is called when there is no game
        assertFailsWith<IllegalStateException> { game.gameService.nextPlayer() }
        game.gameService.startNewGame(playerNames)
        //test for when the knocked player is next player, game should be ended
        val secondPlayer = game.currentGame?.players?.get(1)
        game.currentGame?.knocked = secondPlayer
        game.gameService.nextPlayer()
        assertTrue { game.currentGame == null }
    }

    @Test
    fun testSwapOneCard(){
        val game = SchwimmenGameRootService()
        val playerNames = arrayOf("Max", "Alex", "Sofia")
        //test for when testSwapOneCard is called when there is no game
        assertFailsWith<IllegalStateException> { game.playerActionService.swapOneCard(
            SchwimmenCard(CardSuit.SPADES, CardValue.ACE), SchwimmenCard(CardSuit.HEARTS, CardValue.QUEEN))}
        game.gameService.startNewGame(playerNames)
        val tableCards = mutableListOf(
            SchwimmenCard(CardSuit.SPADES, CardValue.ACE),
            SchwimmenCard(CardSuit.CLUBS, CardValue.JACK),
            SchwimmenCard(CardSuit.HEARTS, CardValue.QUEEN))
        val playerCards = mutableListOf(
            SchwimmenCard(CardSuit.DIAMONDS, CardValue.EIGHT),
            SchwimmenCard(CardSuit.CLUBS, CardValue.NINE),
            SchwimmenCard(CardSuit.HEARTS, CardValue.SEVEN))
        game.currentGame?.tableCards?.clear()
        game.currentGame?.tableCards?.addAll(tableCards)
        game.currentGame?.players?.get(0)?.playerCards?.clear()
        game.currentGame?.players?.get(0)?.playerCards?.addAll(playerCards)
        //test for when the player or table card does not exist
        assertFailsWith<IllegalStateException> { game.playerActionService.swapOneCard(
            SchwimmenCard(CardSuit.CLUBS, CardValue.KING),
            SchwimmenCard(CardSuit.CLUBS, CardValue.KING) )
        }
        //test if the method has worked correctly
        game.playerActionService.swapOneCard(
            SchwimmenCard(CardSuit.CLUBS, CardValue.JACK),
            SchwimmenCard(CardSuit.HEARTS, CardValue.SEVEN))
        assertTrue { tableCards[1] == game.currentGame?.players?.get(0)?.playerCards?.get(2) }
        assertTrue { playerCards[2] == game.currentGame?.tableCards?.get(1) }
    }
}