package com.example.rockpaperscissors.ui.main

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.databinding.ActivityMainBinding
import com.example.rockpaperscissors.enum.GameState
import com.example.rockpaperscissors.enum.PlayerChoice
import com.example.rockpaperscissors.enum.PlayerSide
import com.example.rockpaperscissors.manager.GameListener
import com.example.rockpaperscissors.manager.GameManager
import com.example.rockpaperscissors.manager.MultiPlayerRockPaperScissorsGameManager
import com.example.rockpaperscissors.manager.RockPaperScissorsGameManager
import com.example.rockpaperscissors.model.Player

class MainActivity : AppCompatActivity(), GameListener {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val isMultiPlayerMode: Boolean by lazy {
        intent.getBooleanExtra(MULTI_PLAYER_MODE, false)
    }

    private val gameManager: GameManager by lazy {
        if (isMultiPlayerMode) {
            MultiPlayerRockPaperScissorsGameManager(this)
        } else {
            RockPaperScissorsGameManager(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
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
        binding.ivPaperPlayer2.setOnClickListener {
            gameManager.chooseCharacter(PlayerChoice.PAPER)
        }
        binding.ivRockPlayer2.setOnClickListener {
            gameManager.chooseCharacter(PlayerChoice.ROCK)
        }
        binding.ivScissorsPlayer2.setOnClickListener {
            gameManager.chooseCharacter(PlayerChoice.SCISSORS)
        }
        binding.ivRestart.setOnClickListener {
            showAllChoice()
            gameManager.startOrRestartGame()
        }
    }

    override fun onPlayerChoiceChanged(player: Player, iconDrawableRes: Int) {
        setPlayerChoice(player, iconDrawableRes)
        if (player.playerSide == PlayerSide.PLAYER_ONE) {
            gameManager.startOrRestartGame()
        }
        if (isMultiPlayerMode && player.playerSide == PlayerSide.PLAYER_TWO) {
            gameManager.startOrRestartGame()
        }
    }

    override fun onGameStateChanged(gameState: GameState) {
        when (gameState) {
            GameState.STARTED -> {
                binding.tvTitle.text = getString(R.string.text_your_turn)
                setPlayerVisibility(true, true)
            }
            GameState.WAITING -> {
                binding.tvTitle.text = getString(R.string.text_com_turn)
                setPlayerVisibility(true, true)
            }
            GameState.FINISHED -> {
                binding.tvTitle.text = getString(R.string.text_play_again)
                setPlayerVisibility(true, true)
            }
            GameState.PLAYER_ONE_TURN -> {
                binding.tvTitle.text = getString(R.string.text_player1_turn)
                setPlayerVisibility(true, false)
            }
            GameState.PLAYER_TWO_TURN -> {
                binding.tvTitle.text = getString(R.string.text_player2_turn)
                binding.tvPlayer2.text = getString(R.string.text_label_player_2)
                setPlayerVisibility(false, true)
            }
        }
    }

    private fun setPlayerVisibility(isPlayerOneVisible: Boolean, isPlayerTwoVisible: Boolean) {
        binding.llPlayerOne.isVisible = isPlayerOneVisible
        binding.llPlayerTwo.isVisible = isPlayerTwoVisible
    }

    override fun onGameFinished(gameState: GameState, winner: Player?) {
        if (winner == null) {
            Log.d(TAG, "onGameFinished: DRAW")
            binding.tvVs.text = getString(R.string.text_draw)
        } else if (winner.playerSide == PlayerSide.PLAYER_ONE) {
            Log.d(TAG, "onGameFinished: PLAYER 1 WIN")
            binding.tvVs.text = getString(R.string.text_you_win)
        } else {
            Log.d(TAG, "onGameFinished: PLAYER 1 LOSE")
            binding.tvVs.text = getString(R.string.text_you_lose)
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

        binding.tvVs.text = getString(R.string.text_vs_capital)

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

    companion object {
        private const val MULTI_PLAYER_MODE = "MULTI_PLAYER_MODE"
        fun startActivity(context: Context, isMultiPlayerMode: Boolean) {
            context.startActivity(Intent(context, MainActivity::class.java).apply {
                putExtra(MULTI_PLAYER_MODE, isMultiPlayerMode)
            })
        }
    }
}