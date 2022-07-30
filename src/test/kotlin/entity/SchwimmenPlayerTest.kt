package entity

import kotlin.test.*

/**
 * Test cases for [SchwimmenPlayer]
 */
class SchwimmenPlayerTest {

    /**
     * Testing of a correct case of a player with correct parameters
     */
    @Test
    fun test1(){
        val playerName = "Anton"
        val playercards = mutableListOf(
            SchwimmenCard(CardSuit.SPADES, CardValue.ACE),
            SchwimmenCard(CardSuit.CLUBS, CardValue.JACK),
            SchwimmenCard(CardSuit.HEARTS, CardValue.QUEEN)
        )
        val player = SchwimmenPlayer(playercards, playerName)
        //test to see if the parameters are saved and returned correctly
        assertEquals(playerName, player.name)
        assertEquals(playercards, player.playerCards)
        assertEquals("$playerName: (${player.points})", player.toString())
    }

    /**
     * Testing a wrong case of a player with a empty name
     */
    @Test
    fun test2(){
        val playerName = ""
        val playercards = mutableListOf(
            SchwimmenCard(CardSuit.SPADES, CardValue.ACE),
            SchwimmenCard(CardSuit.CLUBS, CardValue.JACK),
            SchwimmenCard(CardSuit.HEARTS, CardValue.QUEEN)
        )
        //Test should throw IllegalArgumentException because of empty name
        assertFailsWith<IllegalArgumentException>{SchwimmenPlayer(playercards, playerName)}
    }

    /**
     * Test a wrong case of a player with a wrong amount of cards
     */
    @Test
    fun test3(){
        val playerName = "Max"
        val playercards = mutableListOf(
            SchwimmenCard(CardSuit.SPADES, CardValue.ACE),
            SchwimmenCard(CardSuit.CLUBS, CardValue.JACK),
        )
        //Test should throw IllegalArgumentException because of wrong amount of cards
        assertFailsWith<IllegalArgumentException>{SchwimmenPlayer(playercards, playerName)}
    }

    /**
     * Test to see check weather the player points are correct
     */
    @Test
    fun test4(){
        val playerName = "Anton"
        val playercards = mutableListOf(
            SchwimmenCard(CardSuit.SPADES, CardValue.ACE),
            SchwimmenCard(CardSuit.CLUBS, CardValue.JACK),
            SchwimmenCard(CardSuit.HEARTS, CardValue.QUEEN)
        )
        val player = SchwimmenPlayer(playercards, playerName)
        //test to see if the points are 0.0F at the start
        assertEquals(player.points, 0.0F)
        player.points = 10.0F
        //test to see if the points are now 10.0F
        assertEquals(player.points, 10.0F)
        //test should throw an IllegalArgumentException when points are < 0 or > 31
        assertFailsWith<IllegalArgumentException>{player.points = -3.0F}
        assertFailsWith<IllegalArgumentException>{player.points = 33.0F}
    }
}