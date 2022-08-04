package view

/**
 * This interface provides a mechanism for the service layer classes to communicate
 * (usually to the view classes) that certain changes have been made to the entity
 * layer, so that the user interface can be updated accordingly.
 */
interface Refreshable {

    /**
     * perform refreshes that are necessary after a new game started
     */
    fun refreshAfterStartNewGame(){}

    /**
     * perform refreshes that are necessary for when the player swaps his cards
     */
    fun refreshAfterSwapCards(){}

    /**
     * perform refreshes that are necessary when the game ends
     */
    fun refreshAfterGameEnd(){}

    /**
     * perform refreshes that are necessary when it is the next players turn
     */
    fun refreshAfterNextPlayer(){}

    /**
     * perform refreshes that are necessary when every player passes
     */
    fun refreshAfterAllPass(){}
}