package entity
import tools.aqua.bgw.util.*

class SchwimmenGame(
    val players: Array<SchwimmenPlayer>,
    val tableCards: Array<SchwimmenCard>,
    val cardStack: Stack<SchwimmenCard>){

    var knocked: SchwimmenPlayer? = null
        set(kPlayer){
            //knocked must be null to ensure that no one has knocked before
            if(knocked != null){
                throw IllegalArgumentException("There is a player who has already knocked!")
            }
            field = kPlayer
        }

    var passCount: Int = 0
        set(pCount){
            //the amount of passes cannot exceed the amount of players
            if(pCount < 0 || pCount > players.size){
                throw IllegalArgumentException("Count number must be between 0 and 4!")
            }
            field = pCount
        }

    var currentPlayerIndex: Int = 0
        set(index){
            //the index has to be the same with the amount of players
            if(index < 0 || index >= players.size){
                throw IllegalArgumentException("Player index has exceeded!")
            }
            field = index
        }

    init{
        //the amount of players should be 2-4
        if(players.size < 2 || players.size > 4) {
            throw IllegalArgumentException("There must be between 2 to 4 PLayers!")
        }
        //the amount of cards on the table have to be 3
        if(tableCards.size != 3){
            throw IllegalArgumentException("There must be 3 cards on the table!")
        }
        //card stack on the table has to have cards
        if(cardStack.isEmpty()){
            throw IllegalArgumentException("The card stack must not be empty!")
        }
        //card stack must be less than all the cards that are available
        if(cardStack.size > 32){
            throw IllegalArgumentException("The card stack must be less than 32!")
        }
    }

}
