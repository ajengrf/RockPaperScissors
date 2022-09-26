package com.example.rockpaperscissors.manager

import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.enum.GameState
import com.example.rockpaperscissors.enum.PlayerChoice
import com.example.rockpaperscissors.enum.PlayerSide
import com.example.rockpaperscissors.model.Player
import kotlin.random.Random

interface GameManager {
    fun initGame()
    fun chooseCharacter(choice: PlayerChoice)
    fun startOrRestartGame()
}

interface GameListener {
    fun onPlayerChoiceChanged(player: Player, iconDrawableRes: Int)
    fun onGameStateChanged(gameState: GameState)
    fun onGameFinished(gameState: GameState, winner: Player?)
}

class RockPaperScissorsGameManager(
    private val listener: GameListener
) : GameManager {
    private lateinit var playerOne: Player
    private lateinit var playerTwo: Player
    private lateinit var gameState: GameState

    override fun initGame() {
        setGameState(GameState.STARTED)
        playerOne = Player(PlayerSide.PLAYER_ONE, null)
        playerTwo = Player(PlayerSide.PLAYER_TWO, null)
    }

    private fun getPlayerDrawableByChoice(playerChoice: PlayerChoice): Int {
        return when (playerChoice) {
            PlayerChoice.ROCK -> R.drawable.ic_new_rock
            PlayerChoice.PAPER -> R.drawable.ic_new_paper
            PlayerChoice.SCISSORS -> R.drawable.ic_new_scissors
        }
    }

    private fun setGameState(newGameState: GameState) {
        gameState = newGameState
        listener.onGameStateChanged(gameState)
    }

    override fun chooseCharacter(choice: PlayerChoice) {
        if (gameState != GameState.FINISHED) {
            if (choice != null) {
                setPlayerOneChoice(choice)
            }
        }
    }

    private fun setPlayerOneChoice(
        playerChoice: PlayerChoice? = playerOne.playerChoice,
    ) {
        playerOne.apply {
            this.playerChoice = playerChoice
        }
        listener.onPlayerChoiceChanged(
            playerOne,
            getPlayerDrawableByChoice(playerOne.playerChoice!!)
        )
    }

    private fun setPlayerTwoChoice(
        playerChoice: PlayerChoice? = playerTwo.playerChoice
    ) {
        playerTwo.apply {
            this.playerChoice = playerChoice
        }
        listener.onPlayerChoiceChanged(
            playerTwo,
            getPlayerDrawableByChoice(playerTwo.playerChoice!!)
        )
    }

    private fun getPlayerChoiceByOrdinal(index: Int): PlayerChoice {
        return PlayerChoice.values()[index]
    }

    private fun startGame() {
        if (playerOne.playerChoice != null) {

            var playerTwoChoice = getPlayerTwoChoice()
            playerTwo.apply {
                playerChoice = getPlayerTwoChoice()
            }

            setGameState(GameState.WAITING)
            setPlayerTwoChoice(playerTwoChoice)
            checkPlayerWinner()
        }

    }

    private fun checkPlayerWinner() {
        if (playerOne.playerChoice != null && playerTwo.playerChoice != null) {

            val winner =
                if ((playerOne.playerChoice!!.ordinal + 1) % 3 == playerTwo.playerChoice!!.ordinal) {
                    playerTwo
                } else if (playerOne.playerChoice == playerTwo.playerChoice) {
                    null
                } else {
                    playerOne
                }
            setGameState(GameState.FINISHED)
            listener.onGameFinished(gameState, winner)
        } else {
            val winner = null
            setGameState(GameState.FINISHED)
            listener.onGameFinished(gameState, winner)
        }
    }

    private fun getPlayerTwoChoice(): PlayerChoice {
        val randomChoice = Random.nextInt(0, until = PlayerChoice.values().size)
        return getPlayerChoiceByOrdinal(randomChoice)
    }

    private fun restartGame() {
        initGame()
    }

    override fun startOrRestartGame() {
        if (gameState != GameState.FINISHED) {
            startGame()
        } else {
            restartGame()
        }
    }
}