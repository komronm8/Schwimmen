package entity

/**
 * Class for the player of the game itself. The [name] of the player, the [playerCards]
 * and their total points that they have, are all contained in this class
 *
 * @param playerCards list of the cards that the player has
 * @throws IllegalArgumentException if [name] is empty or [playerCards] is not equal to 3
 */
class SchwimmenPlayer(val playerCards: MutableList<SchwimmenCard>, val name: String){

    var points: Float = 0.0F
        set(value){
            //Ensures that the points are in the right range
            if(value < 0 || value > 31){
                throw IllegalArgumentException("The points must be between 0 and 31!")
            }
            field = value
        }

    init{
        //Checks name if it is empty
        if(name.isEmpty()){
            throw IllegalArgumentException("Name must not be empty!")
        }
        //Ensures that the player cards size is always 3
        if(playerCards.size != 3){
            throw IllegalArgumentException("PLayer must have 3 playing cards!")
        }
    }

    override fun toString(): String{
        return "$name: ($points)"
    }

}
