package com.example.rockpaperscissors.manager

import android.content.ContentValues.TAG
import android.util.Log
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

open class RockPaperScissorsGameManager(
    private val listener: GameListener
) : GameManager {
    protected lateinit var playerOne: Player
    protected lateinit var playerTwo: Player
    protected lateinit var state: GameState

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

    protected fun setGameState(newGameState: GameState) {
        state = newGameState
        listener.onGameStateChanged(state)
    }

    override fun chooseCharacter(choice: PlayerChoice) {
        if (state != GameState.FINISHED) {
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

    protected fun setPlayerTwoChoice(
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

    protected fun startGame() {
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
            listener.onGameFinished(state, winner)
        } else {
            val winner = null
            setGameState(GameState.FINISHED)
            listener.onGameFinished(state, winner)
        }
    }

    open fun getPlayerTwoChoice(): PlayerChoice {
        val randomChoice = Random.nextInt(0, until = PlayerChoice.values().size)
        return getPlayerChoiceByOrdinal(randomChoice)
    }

    protected fun restartGame() {
        initGame()
    }

    override fun startOrRestartGame() {
        if (state != GameState.FINISHED) {
            startGame()
        } else {
            restartGame()
        }
    }
}

class MultiPlayerRockPaperScissorsGameManager(listener: GameListener) :
    RockPaperScissorsGameManager(listener) {

    override fun initGame() {
        super.initGame()
        setGameState(GameState.PLAYER_ONE_TURN)
    }

    override fun getPlayerTwoChoice(): PlayerChoice {
        return playerTwo.playerChoice!!
    }

    override fun chooseCharacter(choice: PlayerChoice) {
        if (state == GameState.PLAYER_ONE_TURN) {
            super.chooseCharacter(choice)
        } else if (state == GameState.PLAYER_TWO_TURN) {
            if (choice != null) {
                setPlayerTwoChoice(choice)
            }
        } else return
    }

    override fun startOrRestartGame() {
        when (state) {
            GameState.PLAYER_ONE_TURN -> {
                setGameState(GameState.PLAYER_TWO_TURN)
            }
            GameState.PLAYER_TWO_TURN -> {
                startGame()
            }
            GameState.FINISHED -> {
                restartGame()
            }
            else -> return
        }

    }
}