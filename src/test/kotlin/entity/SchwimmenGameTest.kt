package entity

import kotlin.test.*
import tools.aqua.bgw.util.*

/**
 * Test cases for [SchwimmenGame]
 */

class SchwimmenGameTest{

    private fun createCards(numCards: Int): MutableList<SchwimmenCard> {
        val cards = mutableListOf<SchwimmenCard>()
        var i = 1
        while(i < numCards) {
            cards.add(SchwimmenCard(CardSuit.CLUBS, CardValue.ACE))
            i++
        }
        return cards
    }

    /**
     * Test of creating a game in [SchwimmenGame] with correct parameters
     */
    @Test
    fun test1(){
        val playercards = mutableListOf(
            SchwimmenCard(CardSuit.SPADES, CardValue.ACE),
            SchwimmenCard(CardSuit.CLUBS, CardValue.JACK),
            SchwimmenCard(CardSuit.HEARTS, CardValue.QUEEN))
        val player1 = SchwimmenPlayer(playercards, "John")
        val player2 = SchwimmenPlayer(playercards, "Max")
        val players = listOf( player1, player2)
        val cards = Stack(createCards(20))
        val arrayTableCards = mutableListOf(
            SchwimmenCard(CardSuit.HEARTS, CardValue.QUEEN),
            SchwimmenCard(CardSuit.HEARTS, CardValue.QUEEN),
            SchwimmenCard(CardSuit.DIAMONDS, CardValue.JACK)
        )
        val game = SchwimmenGame(players, arrayTableCards, cards )


        // Test if all attributes are saved and returned correctly
        assertEquals(players, game.players)
        assertEquals(cards, game.cardStack)
        assertEquals(arrayTableCards, game.tableCards)
    }

}