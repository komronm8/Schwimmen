package entity

/**
 * Data class for the playing cards of the game "Schwimmen"
 * With the parameters [CardSuit] and [CardValue]
 */
data class SchwimmenCard(val suit: CardSuit, val value: CardValue){

    override fun toString(): String{
        return "$suit$value"
    }

    /**
     * compares two [SchwimmenCard]s according to the [Enum.ordinal] value of their [CardSuit]
     * (i.e., the order in which the suits are declared in the enum class)
     */
    operator fun compareTo(other: SchwimmenCard) = this.value.ordinal - other.value.ordinal

}
