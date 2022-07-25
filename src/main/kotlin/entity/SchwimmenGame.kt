package entity
import tools.aqua.bgw.util.*

class SchwimmenGame(
    val players: Array<SchwimmenPlayer>,
    val knocked: SchwimmenPlayer?,
    val tableCards: Array<SchwimmenCard>,
    val cardStack: Stack<SchwimmenCard>){

    var currentPlayerIndex: Int = 1

    var passCount: Int = 0

}
