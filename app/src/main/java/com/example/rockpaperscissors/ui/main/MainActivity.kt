package com.example.rockpaperscissors.ui.main

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.databinding.ActivityMainBinding
import com.example.rockpaperscissors.enum.GameState
import com.example.rockpaperscissors.enum.PlayerChoice
import com.example.rockpaperscissors.enum.PlayerSide
import com.example.rockpaperscissors.manager.GameListener
import com.example.rockpaperscissors.manager.GameManager
import com.example.rockpaperscissors.manager.RockPaperScissorsGameManager
import com.example.rockpaperscissors.model.Player

class MainActivity : AppCompatActivity(), GameListener {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val gameManager: GameManager by lazy {
        RockPaperScissorsGameManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        gameManager.initGame()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.ivPaper.setOnClickListener {
            gameManager.chooseCharacter(PlayerChoice.PAPER)
        }
        binding.ivRock.setOnClickListener {
            gameManager.chooseCharacter(PlayerChoice.ROCK)
        }
        binding.ivScissors.setOnClickListener {
            gameManager.chooseCharacter(PlayerChoice.SCISSORS)
        }
        binding.ivRestart.setOnClickListener {
            showAllChoice()
            gameManager.startOrRestartGame()
        }
    }

    override fun onPlayerChoiceChanged(player: Player, iconDrawableRes: Int) {
        Log.d(TAG, "onPlayerChoiceChanged: ${player.playerSide} ${player.playerChoice}")
        setPlayerChoice(player, iconDrawableRes)
        if (player.playerSide == PlayerSide.PLAYER_ONE) {
            gameManager.startOrRestartGame()
        }
    }

    override fun onGameStateChanged(gameState: GameState) {
        binding.tvTitle.text = when (gameState) {
            GameState.STARTED -> "Your Turn"
            GameState.WAITING -> "Computer Turn!"
            GameState.FINISHED -> "Play Again?"
        }

    }

    override fun onGameFinished(gameState: GameState, winner: Player?) {
        var tes = winner == null
        if (winner == null) {
            Log.d(TAG, "onGameFinished: DRAW")
            binding.tvVs.text = "DRAW"
        } else if (winner.playerSide == PlayerSide.PLAYER_ONE) {
            Log.d(TAG, "onGameFinished: PLAYER 1 WIN")
            binding.tvVs.text = "You WIN"
        } else {
            Log.d(TAG, "onGameFinished: PLAYER 1 LOSE")
            binding.tvVs.text = "You LOSE"
        }
    }

    private fun showAllChoice() {
        val ivRock: ImageView?
        val ivPaper: ImageView?
        val ivScissors: ImageView?
        val ivRock2: ImageView?
        val ivPaper2: ImageView?
        val ivScissors2: ImageView?

        ivRock = binding.ivRock
        ivPaper = binding.ivPaper
        ivScissors = binding.ivScissors
        ivRock2 = binding.ivRockPlayer2
        ivPaper2 = binding.ivPaperPlayer2
        ivScissors2 = binding.ivScissorsPlayer2

        ivRock.visibility = View.VISIBLE
        ivPaper.visibility = View.VISIBLE
        ivScissors.visibility = View.VISIBLE
        ivRock2.visibility = View.VISIBLE
        ivPaper2.visibility = View.VISIBLE
        ivScissors2.visibility = View.VISIBLE

        binding.tvVs.text = "VS"

    }

    private fun setPlayerChoice(player: Player, iconDrawableRes: Int) {
        val ivRock: ImageView?
        val ivPaper: ImageView?
        val ivScissors: ImageView?
        val drawable = ContextCompat.getDrawable(this, iconDrawableRes)

        if (player.playerSide == PlayerSide.PLAYER_ONE) {
            ivRock = binding.ivRock
            ivPaper = binding.ivPaper
            ivScissors = binding.ivScissors
        } else {
            ivRock = binding.ivRockPlayer2
            ivPaper = binding.ivPaperPlayer2
            ivScissors = binding.ivScissorsPlayer2
        }

        when (player.playerChoice) {
            PlayerChoice.ROCK -> {
                ivRock.visibility = View.VISIBLE
                ivPaper.visibility = View.INVISIBLE
                ivScissors.visibility = View.INVISIBLE
                ivRock.setImageDrawable(drawable)
            }
            PlayerChoice.PAPER -> {
                ivRock.visibility = View.INVISIBLE
                ivPaper.visibility = View.VISIBLE
                ivScissors.visibility = View.INVISIBLE
                ivPaper.setImageDrawable(drawable)
            }
            PlayerChoice.SCISSORS -> {
                ivRock.visibility = View.INVISIBLE
                ivPaper.visibility = View.INVISIBLE
                ivScissors.visibility = View.VISIBLE
                ivScissors.setImageDrawable(drawable)
            }
        }
    }
}