package view

interface Refreshable {
    fun refreshAfterStartNewGame(){}
    fun refreshAfterSwapCards(){}
    fun refreshAfterGameEnd(){}
    fun refreshAfterNextPlayer(){}
    fun refreshAfterAllPass(){}
}