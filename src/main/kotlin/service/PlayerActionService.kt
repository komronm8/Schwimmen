package service

import entity.SchwimmenCard
import entity.SchwimmenPlayer

class PlayerActionService(root: SchwimmenGameRootService): AbstractRefreshingService() {

    fun swapOneCard(tablecard: SchwimmenCard, playerCard: SchwimmenCard): Unit{}

    fun swapOneCard(): Unit{}

    fun pass(): Unit{}

    fun knock(): Unit{}

    private fun calculatePlayerPoints(player: SchwimmenPlayer): Unit{}


}