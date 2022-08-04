package service

import kotlin.test.*
import entity.*
import org.junit.jupiter.api.assertDoesNotThrow

/**
 * Class that provides tests for [GameService] and [PlayerActionService] by basically playing through some
 * sample games
 */
class ServiceTest {

    /**
     * Tests for the method startNewGame() from the class [GameService]
     */
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

    /**
     * Tests for the method nextGame() from the class [GameService]
     */
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

    /**
     * Tests for the method endGame() from the class [GameService]
     */
    @Test
    fun testEndGame(){
        val game = SchwimmenGameRootService()
        val playerNames = arrayOf("Max", "Alex", "Sofia")
        //test to see weather game can be ended when there is no game
        assertFailsWith<IllegalStateException> { game.gameService.endGame() }
    }

    /**
     * Tests for the method nextPlayer() from the class [GameService]
     */
    @Test
    fun testNextPlayer(){
        val game = SchwimmenGameRootService()
        val playerNames = arrayOf("Max", "Alex", "Sofia")
        //test to see if nextPlayer is called when there is no game
        assertFailsWith<IllegalStateException> { game.gameService.nextPlayer() }
        game.gameService.startNewGame(playerNames)
    }

    /**
     * Tests for the method swapOneCard() from the class [PlayerActionService]
     */
    @Test
    fun testSwapOneCard(){
        val game = SchwimmenGameRootService()
        val playerNames = arrayOf("Max", "Alex", "Sofia")
        //test for when SwapOneCard is called and there is no game
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

    /**
     * Tests for the method swapAllCards() from the class [PlayerActionService]
     */
    @Test
    fun testSwapAllCards(){
        val game = SchwimmenGameRootService()
        val playerNames = arrayOf("Max", "Alex", "Sofia")
        //test for when SwapAllCards is called and there is no game
        assertFailsWith<IllegalStateException> { game.playerActionService.swapAllCards() }
        game.gameService.startNewGame(playerNames)
        //test if the method has worked correctly
        assertDoesNotThrow { game.playerActionService.swapAllCards() }
    }

    /**
     * Tests for the method pass() from the class [PlayerActionService]
     */
    @Test
    fun testPass(){
        val game = SchwimmenGameRootService()
        val playerNames = arrayOf("Max", "Alex", "Sofia")
        //test for when SwapAllCards is called and there is no game
        assertFailsWith<IllegalStateException> { game.playerActionService.pass() }
        game.gameService.startNewGame(playerNames)
        //test for when not all players have passes
        game.playerActionService.pass()
        assertTrue { game.currentGame?.currentPlayerIndex == 1 }
        //test for when everybody has passed and there are cards in the stack
        //card stack is equal to 20 at the start should be 17 after calling pass
        game.currentGame?.passCount = 2
        game.playerActionService.pass()
        assertTrue { game.currentGame?.cardStack?.size == 17 }
    }

    /**
     * Tests for the method knock() from the class [PlayerActionService]
     */
    @Test
    fun testKnock(){
        val game = SchwimmenGameRootService()
        val playerNames = arrayOf("Max", "Alex", "Sofia")
        //test for when knock() is called and there is no game
        assertFailsWith<IllegalStateException> { game.playerActionService.knock() }
        game.gameService.startNewGame(playerNames)
        //test if someone has knocked already but someone else knocks
        game.currentGame?.knocked = game.currentGame?.players?.get(0)
        assertFailsWith<IllegalStateException> { game.playerActionService.knock() }
        //test if someone knocks and nobody has knocked
        game.currentGame = null
        game.gameService.startNewGame(playerNames)
        game.playerActionService.knock()
        assertTrue { game.currentGame?.knocked == game.currentGame?.players?.get(0) }
    }
}