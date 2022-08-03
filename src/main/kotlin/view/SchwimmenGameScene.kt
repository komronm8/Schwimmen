package view

import entity.*
import service.CardImageLoader
import service.SchwimmenGameRootService
import tools.aqua.bgw.animation.DelayAnimation
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.ImageVisual
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.components.container.*
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.CheckBox
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.util.Font
import java.awt.Color
import tools.aqua.bgw.util.Stack


class SchwimmenGameScene(private val rootService: SchwimmenGameRootService):
    BoardGameScene(1920, 1080, ImageVisual("Background_green.png")), Refreshable {

    private var playerAmount = rootService.currentGame?.players?.size

    private var layouts = mutableListOf<LinearLayout<CardView>>()

    private var nameLabels = mutableListOf<Label>()

    private var swapPlayerCard = 0

    private var swapTableCard = 0

    private var currentPlayerHandLayout: LinearLayout<CardView> = LinearLayout(
        height = 220,
        width = 600,
        posX = 650,
        posY = 800,
        spacing = 50,
        alignment = Alignment.CENTER,
        visual = ColorVisual(255, 255, 255, 50)
    )

    private var currentPlayerLabel = Label(
        width = 200,
        height = 50,
        posX = 850,
        posY = 740,
        font = Font(fontWeight = Font.FontWeight.BOLD, size = 30),
        visual = ColorVisual(255,255,255, 50)
    )

    private var topPlayerHandLayout: LinearLayout<CardView> = LinearLayout(
        height = 220,
        width = 600,
        posX = 650,
        posY = 50,
        spacing = 50,
        alignment = Alignment.CENTER,
        visual = ColorVisual(255, 255, 255, 50)
    )

    private var topPlayerLabel = Label(
        width = 200,
        height = 50,
        posX = 850,
        posY = 280,
        font = Font(fontWeight = Font.FontWeight.BOLD, size = 30),
        visual = ColorVisual(255,255,255, 50)
    )

    private var leftPlayerHandLayout: LinearLayout<CardView> = LinearLayout(
        height = 220,
        width = 600,
        posX = -100,
        posY = 425,
        spacing = 50,
        alignment = Alignment.CENTER,
        visual = ColorVisual(255, 255, 255, 50)
    )

    private var leftPlayerLabel = Label(
        width = 200,
        height = 50,
        posX = 100,
        posY = 170,
        font = Font(fontWeight = Font.FontWeight.BOLD, size = 30),
        visual = ColorVisual(255,255,255, 50)
    )

    private var rightPlayerHandLayout: LinearLayout<CardView> = LinearLayout(
        height = 220,
        width = 600,
        posX = 1410,
        posY = 425,
        spacing = 50,
        alignment = Alignment.CENTER,
        visual = ColorVisual(255, 255, 255, 50)
    )

    private var rightPlayerLabel = Label(
        width = 200,
        height = 50,
        posX = 1610,
        posY = 170,
        font = Font(fontWeight = Font.FontWeight.BOLD, size = 30),
        visual = ColorVisual(255,255,255, 50)
    )

    private var tableCardsLayout: LinearLayout<CardView> = LinearLayout(
        height = 220,
        width = 600,
        posX = 550,
        posY = 425,
        spacing = 50,
        alignment = Alignment.CENTER,
        visual = ColorVisual(255, 255, 255, 50)
    )

    private var cardStackLayout: CardStack<CardView> = CardStack(
        height = 220,
        width = 150,
        posX = 1200,
        posY = 425,
        alignment = Alignment.CENTER,
        visual = ColorVisual(255, 255, 255, 50)
    )

    private val swapAllButton = Button(
        height = 60,
        width = 200,
        posX = 30,
        posY = 900,
        text = "Swap All",
        font = Font(color = Color.WHITE, fontWeight = Font.FontWeight.BOLD, size = 25),
        visual = ImageVisual("button.png")
    ).apply {
        onMouseClicked = {
            rootService.playerActionService.swapAllCards()
        }
    }

    private val swapOneButton = Button(
        height = 60,
        width = 200,
        posX = 260,
        posY = 900,
        text = "Swap One",
        font = Font(color = Color.WHITE, fontWeight = Font.FontWeight.BOLD, size = 25),
        visual = ImageVisual("button.png")
    ).apply {
        onMouseClicked = {
            removeComponents(this, knockButton, swapAllButton, passButton)
            addComponents(swapPLayerCardButton1, swapPLayerCardButton2, swapPLayerCardButton3)
        }
    }

    private val swapPLayerCardButton1 = Button(
        height = 50,
        width = 120,
        posX = 720,
        posY = 1025,
        text = "Pick",
        font = Font(color = Color.WHITE, fontWeight = Font.FontWeight.BOLD, size = 25),
        visual = ImageVisual("button.png")
    ).apply {
        onMouseClicked = {
            removeHandSwapButtons(0)
        }
    }

    private val swapPLayerCardButton2 = Button(
        height = 50,
        width = 120,
        posX = 890,
        posY = 1025,
        text = "Pick",
        font = Font(color = Color.WHITE, fontWeight = Font.FontWeight.BOLD, size = 25),
        visual = ImageVisual("button.png")
    ).apply {
        onMouseClicked = {
            removeHandSwapButtons(1)
        }
    }

    private val swapPLayerCardButton3 = Button(
        height = 50,
        width = 120,
        posX = 1060,
        posY = 1025,
        text = "Pick",
        font = Font(color = Color.WHITE, fontWeight = Font.FontWeight.BOLD, size = 25),
        visual = ImageVisual("button.png")
    ).apply {
        onMouseClicked = {
            removeHandSwapButtons(2)
        }
    }

    private val swapTableCardButton1 = Button(
        height = 50,
        width = 120,
        posX = 620,
        posY = 650,
        text = "Pick",
        font = Font(color = Color.WHITE, fontWeight = Font.FontWeight.BOLD, size = 25),
        visual = ImageVisual("button.png")
    ).apply {
        onMouseClicked = {
            removeTableSwapButtons( 0 )
        }
    }

    private val swapTableCardButton2 = Button(
        height = 50,
        width = 120,
        posX = 790,
        posY = 650,
        text = "Pick",
        font = Font(color = Color.WHITE, fontWeight = Font.FontWeight.BOLD, size = 25),
        visual = ImageVisual("button.png")
    ).apply {
        onMouseClicked = {
            removeTableSwapButtons( 1 )
        }
    }

    private val swapTableCardButton3 = Button(
        height = 50,
        width = 120,
        posX = 960,
        posY = 650,
        text = "Pick",
        font = Font(color = Color.WHITE, fontWeight = Font.FontWeight.BOLD, size = 25),
        visual = ImageVisual("button.png")
    ).apply {
        onMouseClicked = {
            removeTableSwapButtons( 2 )
        }
    }

    private val knockButton = Button(
        height = 60,
        width = 200,
        posX = 30,
        posY = 980,
        text = "Knock",
        font = Font(color = Color.WHITE, fontWeight = Font.FontWeight.BOLD, size = 25),
        visual = ImageVisual("button.png")
    ).apply {
        onMouseClicked = {
            val game = rootService.currentGame
            checkNotNull(game) {"No started game found."}
            rootService.playerActionService.knock()
            knockedPersonLabel.text = "${game.knocked?.name} has knocked!"
        }
    }

    private val knockedPersonLabel = Label(
        height = 60,
        width = 500,
        posX = 1300,
        posY = 900,
        font = Font(color = Color.WHITE, fontWeight = Font.FontWeight.BOLD, size = 36)
    )

    private val passButton = Button(
        height = 60,
        width = 200,
        posX = 260,
        posY = 980,
        text = "Pass",
        font = Font(color = Color.WHITE, fontWeight = Font.FontWeight.BOLD, size = 25),
        visual = ImageVisual("button.png")
    ).apply {
        onMouseClicked = {
            rootService.playerActionService.pass()
        }
    }

    private val cardStackCountLabel = Label(
        height = 60,
        width = 200,
        posX = 1175,
        posY = 630,
        font = Font(color = Color.WHITE, fontWeight = Font.FontWeight.BOLD, size = 25)
    )

    private fun removeHandSwapButtons( swapCard: Int ){
        swapPlayerCard = swapCard
        removeComponents(swapPLayerCardButton1, swapPLayerCardButton2, swapPLayerCardButton3)
        addComponents(swapTableCardButton1, swapTableCardButton2, swapTableCardButton3)
    }

    private fun removeTableSwapButtons( swapCard: Int ){
        val game = rootService.currentGame
        checkNotNull(game) {"No started game found."}

        swapTableCard = swapCard
        removeComponents(swapTableCardButton1, swapTableCardButton2, swapTableCardButton3)
        addComponents(knockButton, swapAllButton, passButton, swapOneButton)
        rootService.playerActionService.swapOneCard(
            game.tableCards[swapTableCard],
            game.players[game.currentPlayerIndex].playerCards[swapPlayerCard])
    }

    private fun insertCards(cards: List<SchwimmenCard>, layout: LinearLayout<CardView>){
        for(i in cards.indices){
            val cardImageLoader = CardImageLoader()
            val currentCard = CardView(
                height = 200,
                width = 120,
                front = ImageVisual(cardImageLoader.frontImageFor(cards[i].suit, cards[i].value)),
                back = ImageVisual(cardImageLoader.backImage)
            )
            layout.add(currentCard)
        }
    }

    private fun createStack(stack: Stack<SchwimmenCard>, tableStack: CardStack<CardView>){
        for (i in stack.peekAll().indices){
            val cardImageLoader = CardImageLoader()
            val currentCard = CardView(
                height = 200,
                width = 120,
                front = ImageVisual(cardImageLoader.frontImageFor(
                    stack.peekAll()[i].suit, stack.peekAll()[i].value)),
                back = ImageVisual(cardImageLoader.backImage)
            )
            tableStack.add(currentCard)
        }
    }

    init {
        leftPlayerHandLayout.rotate(90)
        rightPlayerHandLayout.rotate(-90)
        background = ColorVisual(108, 168, 59)
        addComponents(tableCardsLayout, swapAllButton, swapOneButton,
        knockButton, passButton, cardStackLayout, knockedPersonLabel)
    }

    override fun refreshAfterStartNewGame() {
        val game = rootService.currentGame
        checkNotNull(game) {"No started game found."}

        playerAmount = rootService.currentGame?.players?.size

        val playerNames = game.players

        when( playerAmount ) {
            4 -> {
                layouts = mutableListOf(
                    currentPlayerHandLayout, leftPlayerHandLayout,
                    topPlayerHandLayout, rightPlayerHandLayout
                )
                nameLabels = mutableListOf(
                    currentPlayerLabel, leftPlayerLabel,
                    topPlayerLabel, rightPlayerLabel
                )
                for (i in 0..3) {
                    nameLabels[i].text = playerNames[i].name
                    addComponents(layouts[i], nameLabels[i])
                    insertCards(game.players[i].playerCards, layouts[i])
                }
                currentPlayerHandLayout.forEach { CardView -> CardView.showFront() }
                insertCards(game.tableCards, tableCardsLayout)
                tableCardsLayout.forEach { CardView -> CardView.showFront() }
            }

            3 -> {
                layouts = mutableListOf(
                    currentPlayerHandLayout, leftPlayerHandLayout,
                    rightPlayerHandLayout
                )
                nameLabels = mutableListOf(currentPlayerLabel, leftPlayerLabel, rightPlayerLabel)
                for (i in 0..2) {
                    nameLabels[i].text = playerNames[i].name
                    addComponents(layouts[i], nameLabels[i])
                    insertCards(game.players[i].playerCards, layouts[i])
                }
                currentPlayerHandLayout.forEach { CardView -> CardView.showFront() }
                insertCards(game.tableCards, tableCardsLayout)
                tableCardsLayout.forEach { CardView -> CardView.showFront() }
            }
            else -> {
                layouts = mutableListOf(currentPlayerHandLayout, topPlayerHandLayout)
                nameLabels = mutableListOf(currentPlayerLabel, topPlayerLabel)
                for (i in 0..1) {
                    nameLabels[i].text = playerNames[i].name
                    addComponents(layouts[i], nameLabels[i])
                    insertCards(game.players[i].playerCards, layouts[i])
                }
                currentPlayerHandLayout.forEach { CardView -> CardView.showFront() }
                insertCards(game.tableCards, tableCardsLayout)
                tableCardsLayout.forEach { CardView -> CardView.showFront() }
            }
        }
        createStack(game.cardStack, cardStackLayout)
        cardStackCountLabel.text = "${rootService.currentGame?.cardStack?.size} cards left"
        addComponents(cardStackCountLabel)
    }

    override fun refreshAfterNextPlayer() {
        val game = rootService.currentGame
        checkNotNull(game) {"No started game found."}

        val delay = DelayAnimation(1000)
        delay.onFinished = {
            changeCurrentPlayer(layouts, nameLabels)
            unlock()
        }
        lock()
        playAnimation(delay)
    }

    private fun changeCurrentPlayer(
        layouts: MutableList<LinearLayout<CardView>>, nameLabels: MutableList<Label>){
        val tempCardsList = mutableListOf<List<CardView>>()
        val tempNameList = mutableListOf<String>()
        for ( i in layouts){
            tempCardsList.add(i.components)
            i.clear()
        }
        for(i in nameLabels){
            tempNameList.add(i.text)
        }
        for( i in 0 until layouts.size ){
            layouts[i].addAll(tempCardsList[(i+1) % layouts.size])
            nameLabels[i].text = tempNameList[(i+1) % layouts.size]
            layouts[i].forEach{ CardView -> CardView.showBack() }
        }
        currentPlayerHandLayout.forEach{ CardView -> CardView.showFront() }
    }

    override fun refreshAfterSwapCards() {
        val game = rootService.currentGame
        checkNotNull(game) {"No started game found."}

        tableCardsLayout.clear()
        insertCards(game.tableCards, tableCardsLayout)
        tableCardsLayout.forEach { CardView -> CardView.showFront() }

        currentPlayerHandLayout.clear()
        insertCards(game.players[game.currentPlayerIndex].playerCards, layouts[0])
        currentPlayerHandLayout.forEach { CardView -> CardView.showFront() }
    }

    override fun refreshAfterAllPass() {
        val game = rootService.currentGame
        checkNotNull(game) {"No started game found."}

        tableCardsLayout.clear()
        insertCards(game.tableCards, tableCardsLayout)
        tableCardsLayout.forEach { CardView -> CardView.showFront() }

        createStack(game.cardStack, cardStackLayout)
        cardStackCountLabel.text = "${rootService.currentGame?.cardStack?.size} cards left"
    }

    override fun refreshAfterGameEnd() {
    }

}