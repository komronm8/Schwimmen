package entity

data class SchwimmenCard(val suit: CardSuit, val value: CardValue){

    override fun toString() ="$suit$value"

    operator fun compareTo(other: SchwimmenCard) = this.value.ordinal - other.value.ordinal

}
